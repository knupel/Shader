/**
* Antialiasing FXAA
* @see @stanlepunk
* @see https://github.com/StanLepunK/Shader
* v 0.0.1
* 2018-2019
*/




// from https://www.shadertoy.com/view/ltfXWS PERMUTATOR
/* There are a few shaders on this site already that attempt to do this (most notably
 * <https://www.shadertoy.com/view/ldlSzS>), but none of them were quite what I wanted,
*/



// Processing implementation
#ifdef GL_ES
precision highp float;
#endif
#define PROCESSING_TEXTURE_SHADER
varying vec4 vertColor;
varying vec4 vertTexCoord;
uniform vec2 resolution; // WARNING VERY IMPORTANT // need this name for unknow reason :( here your pass your resolution texture
// vec2 texOffset   = vec2(1) / resolution; // only work with uniform resolution

// Rope implementation
uniform sampler2D texture_source;
uniform vec2 resolution_source;
uniform ivec2 flip_source; // can be use to flip texture source
uniform vec4 level_source;


uniform vec2 nw;
uniform vec2 ne;
uniform vec2 sw;
uniform vec2 se;

// uniform sampler2D texture_layer;
// uniform vec2 resolution_layer;
// uniform ivec2 flip_layer; // can be use to flip texture layer
// uniform vec4 level_layer;

// uniform vec2 position; // maneep to receive a normal information 0 > 1

// uniform float time;


// 0: antialiased blocky interpolation
// 1: linear interpolation
// 2: aliased blocky interpolation
// uniform int mode;

// uniform int color_mode; // 0 is RGB / 3 is HSB

// uniform int num;
// uniform ivec3 size;
// uniform float strength;

// uniform float angle;
// uniform float threshold;
// uniform float quality;
// uniform vec2 offset;
// uniform float scale;

// uniform int rows;
// uniform int cols;x

// uniform bool use_fx_color;
// uniform bool use_fx;



// UTIL TEMPLATE
vec2 set_uv(int flip_vertical, int flip_horizontal, vec2 res) {
  vec2 uv;
  if(all(equal(vec2(0),res))) {
    uv = vertTexCoord.st;
  } else if(all(greaterThan(res,vertTexCoord.st))) {
    uv = vertTexCoord.st;
  } else {
    uv = res;
  }
  // flip 
  float condition_y = step(0.1, flip_vertical);
  uv.y = 1.0 -(uv.y *condition_y +(1.0 -uv.y) *(1.0 -condition_y));

  float condition_x = step(0.1, flip_horizontal);
  uv.x = 1.0 -(uv.x *condition_x +(1.0 -uv.x) *(1.0 -condition_x));

  return uv;
}

vec2 set_uv(ivec2 flip, vec2 res) {
  return set_uv(flip.x,flip.y,res);
}

vec2 set_uv() {
  return set_uv(0,0,vec2(0));
}





#define FXAA_REDUCE_MIN   (1.0/ 128.0)
#define FXAA_REDUCE_MUL   (1.0 / 8.0)
#define FXAA_SPAN_MAX 8.0

vec4 fxaa(sampler2D tex, vec2 uv, vec2 res) {  
  // vec2 nw = vec2(-1,-1);
  // vec2 ne = vec2(1,-1);
  // vec2 sw = vec2(-1,1);
  // vec2 se = vec2(1,1);
  
  // vec2 nw = vec2(0,0);
  // vec2 ne = vec2(1,0);
  // vec2 sw = vec2(0,1);
  // vec2 se = vec2(1,1);

  // vec2 nw = vec2(0,0);
  // vec2 ne = vec2(-1,0);
  // vec2 sw = vec2(0,-1);
  // vec2 se = vec2(-1,-1);




  // vec2 inverseVP = vec2(1.0 / res.x, 1.0 / res.y);
  vec2 inverseVP = vec2(1.0,1.0);

  
  

    vec3 rgbNW = texture2D(tex, uv + nw * inverseVP).xyz;
    vec3 rgbNE = texture2D(tex, uv + ne * inverseVP).xyz;
    vec3 rgbSW = texture2D(tex, uv + sw * inverseVP).xyz;
    vec3 rgbSE = texture2D(tex, uv + se * inverseVP).xyz;

    vec4 texColor = texture2D(tex, uv * inverseVP);


    vec3 rgbM  = texColor.xyz;
    vec3 luma = vec3(0.299, 0.587, 0.114);
    float lumaNW = dot(rgbNW, luma);
    float lumaNE = dot(rgbNE, luma);
    float lumaSW = dot(rgbSW, luma);
    float lumaSE = dot(rgbSE, luma);
    float lumaM  = dot(rgbM,  luma);
    float lumaMin = min(lumaM, min(min(lumaNW, lumaNE), min(lumaSW, lumaSE)));
    float lumaMax = max(lumaM, max(max(lumaNW, lumaNE), max(lumaSW, lumaSE)));
    
    mediump vec2 dir;
    dir.x = -((lumaNW + lumaNE) - (lumaSW + lumaSE));
    dir.y =  ((lumaNW + lumaSW) - (lumaNE + lumaSE));
    
    float dirReduce = max((lumaNW + lumaNE + lumaSW + lumaSE) *
                          (0.25 * FXAA_REDUCE_MUL), FXAA_REDUCE_MIN);
    
    float rcpDirMin = 1.0 / (min(abs(dir.x), abs(dir.y)) + dirReduce);
    dir = min(vec2(FXAA_SPAN_MAX, FXAA_SPAN_MAX),
              max(vec2(-FXAA_SPAN_MAX, -FXAA_SPAN_MAX),
              dir * rcpDirMin)) * inverseVP;
    
    vec3 rgbA = 0.5 * (
        texture2D(tex, uv * inverseVP + dir * (1.0 / 3.0 - 0.5)).xyz +
        texture2D(tex, uv * inverseVP + dir * (2.0 / 3.0 - 0.5)).xyz);
    vec3 rgbB = rgbA * 0.5 + 0.25 * (
        texture2D(tex, uv * inverseVP + dir * -0.5).xyz +
        texture2D(tex, uv * inverseVP + dir * 0.5).xyz);

    float lumaB = dot(rgbB, luma);
    if ((lumaB < lumaMin) || (lumaB > lumaMax))
        return vec4(rgbA, texColor.a);
    else
        return vec4(rgbB, texColor.a);
    //return color;
}


void main()  { 
  vec2 uv = set_uv(flip_source,resolution_source);
  // vec2 uv = vertTexCoord.st;
  gl_FragColor = fxaa(texture_source, uv, resolution_source);
}
































/*
uniform float vx_offset;
uniform float rt_w; // GeeXLab built-in
uniform float rt_h; // GeeXLab built-in
uniform float FXAA_SPAN_MAX = 8.0;
uniform float FXAA_REDUCE_MUL = 1.0/8.0;

#define FxaaInt2 ivec2
#define FxaaFloat2 vec2
//#define FxaaTexLod0(t, p) texture2DLod(t, p, 0.0)


// #define FxaaTexOff(t, p, o, r) texture2DLodOffset(t, p, 0.0, o)
#define FxaaTexOff(t, p, o, r) textureOffset(t, p, o)


vec4 FxaaTexLod0(sampler2D t, vec2 p) {
  //return texture2DLod(tex, p, 0.0);
  //return textureLod(t, p, time);
  return textureLod(t, p, 0.0);
}



// vec4 posPos, // Output of FxaaVertexShader interpolated across screen.
// sampler2D tex, // Input texture.
// vec2 rcpFrame) // Constant {1.0/frameWidth, 1.0/frameHeight}.
vec3 FxaaPixelShader(vec4 posPos, sampler2D tex, vec2 rcpFrame) {   
  // ---------------------------------------------------------
  #define FXAA_REDUCE_MIN   (1.0/128.0)
  //#define FXAA_REDUCE_MUL   (1.0/8.0)
  //#define FXAA_SPAN_MAX     8.0
  // ---------------------------------------------------------
  vec3 rgbNW = FxaaTexLod0(tex, posPos.zw).xyz;
  //vec3 rgbNW = texture2DLod(tex, posPos.zw, 0.0).xyz;
  vec3 rgbNE = FxaaTexOff(tex, posPos.zw, FxaaInt2(1,0), rcpFrame.xy).xyz;
  vec3 rgbSW = FxaaTexOff(tex, posPos.zw, FxaaInt2(0,1), rcpFrame.xy).xyz;
  vec3 rgbSE = FxaaTexOff(tex, posPos.zw, FxaaInt2(1,1), rcpFrame.xy).xyz;

  vec3 rgbM  = FxaaTexLod0(tex, posPos.xy).xyz;
  //vec3 rgbM = texture2DLod(tex, posPos.xy, 0.0).xyz;
  // ---------------------------------------------------------
  vec3 luma = vec3(0.299, 0.587, 0.114);
  float lumaNW = dot(rgbNW, luma);
  float lumaNE = dot(rgbNE, luma);
  float lumaSW = dot(rgbSW, luma);
  float lumaSE = dot(rgbSE, luma);
  float lumaM  = dot(rgbM,  luma);
  // ---------------------------------------------------------
  float lumaMin = min(lumaM, min(min(lumaNW, lumaNE), min(lumaSW, lumaSE)));
  float lumaMax = max(lumaM, max(max(lumaNW, lumaNE), max(lumaSW, lumaSE)));
  // ---------------------------------------------------------
  vec2 dir; 
  dir.x = -((lumaNW + lumaNE) - (lumaSW + lumaSE));
  dir.y =  ((lumaNW + lumaSW) - (lumaNE + lumaSE));
  // ---------------------------------------------------------
  float dirReduce = max(
        (lumaNW + lumaNE + lumaSW + lumaSE) * (0.25 * FXAA_REDUCE_MUL),
        FXAA_REDUCE_MIN);
  float rcpDirMin = 1.0/(min(abs(dir.x), abs(dir.y)) + dirReduce);
  dir = min(FxaaFloat2( FXAA_SPAN_MAX,  FXAA_SPAN_MAX), 
        max(FxaaFloat2(-FXAA_SPAN_MAX, -FXAA_SPAN_MAX), 
        dir * rcpDirMin)) * rcpFrame.xy;
  // --------------------------------------------------------
  vec3 rgbA = (1.0/2.0) * (
        FxaaTexLod0(tex, posPos.xy + dir * (1.0/3.0 - 0.5)).xyz +
        FxaaTexLod0(tex, posPos.xy + dir * (2.0/3.0 - 0.5)).xyz);
  vec3 rgbB = rgbA * (1.0/2.0) + (1.0/4.0) * (
        FxaaTexLod0(tex, posPos.xy + dir * (0.0/3.0 - 0.5)).xyz +
        FxaaTexLod0(tex, posPos.xy + dir * (3.0/3.0 - 0.5)).xyz);
  float lumaB = dot(rgbB, luma);
  if((lumaB < lumaMin) || (lumaB > lumaMax)) {
    return rgbA;
  } else {
    return rgbB; 
  } 
}






vec4 PostFX(sampler2D tex, vec2 uv, float time) {
  vec4 c = vec4(0.0);
  vec2 rcpFrame = vec2(1.0/rt_w, 1.0/rt_h);
  vec4 posPos = vertTexCoord;
  c.rgb = FxaaPixelShader(posPos, tex, rcpFrame);
  //c.rgb = 1.0 - texture2D(tex, posPos.xy).rgb;
  c.a = 1.0;
  return c;
}


void main()  { 
  vec2 uv = set_uv(flip_source,resolution_source);
  gl_FragColor = PostFX(texture_source, uv, 0.0);
}


*/

































/*

// basically calculates the lengths of (a.x, b.x) and (a.y, b.y) at the same time
vec2 v2len(in vec2 a, in vec2 b) {
    return sqrt(a*a+b*b);
}


// samples from a linearly-interpolated texture to produce an appearance similar to
// nearest-neighbor interpolation, but with resolution-dependent antialiasing
// 
// this function's interface is exactly the same as texture's, aside from the 'res'
// parameter, which represents the resolution of the texture 'tex'.
vec4 textureBlocky(in sampler2D tex, in vec2 uv, in vec2 res) {
    uv *= res; // enter texel coordinate space.
    
    
    vec2 seam = floor(uv+.5); // find the nearest seam between texels.
    
    // here's where the magic happens. scale up the distance to the seam so that all
    // interpolation happens in a one-pixel-wide space.
    uv = (uv-seam)/v2len(dFdx(uv),dFdy(uv))+seam;
    
    uv = clamp(uv, seam-.5, seam+.5); // clamp to the center of a texel.
    
    
    return texture(tex, uv/res, -1000.).xxxx; // convert back to 0..1 coordinate space.
}



// simulates nearest-neighbor interpolation on a linearly-interpolated texture
// 
// this function's interface is exactly the same as textureBlocky's.
vec4 textureUgly(in sampler2D tex, in vec2 uv, in vec2 res) {
    return textureLod(tex, (floor(uv*res)+.5)/res, 0.0).xxxx;
}



vec2 uv_aa_smoothstep(vec2 uv, vec2 res, float width ) {
    uv = uv * res;
    vec2 uv_floor = floor(uv + 0.5);
    vec2 uv_fract = fract(uv + 0.5);
    vec2 uv_aa = fwidth(uv) * width * 0.5;
    uv_fract = smoothstep(
        vec2(0.5) - uv_aa,
        vec2(0.5) + uv_aa,
        uv_fract
        );
    
    return (uv_floor + uv_fract - 0.5) / res;
}



vec2 saturate(vec2 x) {
  return clamp(x, 0.0, 1.0);   
}

vec2 magnify(vec2 uv) {
    uv *= resolution_source; 
    return (saturate(fract(uv) / saturate(fwidth(uv))) + floor(uv) - 0.5) / resolution_source.xy;
}

vec2 quantize(vec2 uv) {
    return (floor(uv * resolution_source) + 0.5) / resolution_source.xy;
}


float FXAA_SPAN_MAX = 8.0f;
float FXAA_REDUCE_MUL = 1.0f/8.0f;
float FXAA_REDUCE_MIN = 1.0f/128.0f;
vec3 computeFxaa(sampler2D tex, vec2 uv, vec2 res) {
    vec2 screenTextureOffset = res;
    vec3 luma = vec3(0.299f, 0.587f, 0.114f);

    vec3 offsetNW = texture(tex, uv + (vec2(-1.0f, -1.0f) * screenTextureOffset)).xyz;
    vec3 offsetNE = texture(tex, uv + (vec2(1.0f, -1.0f) * screenTextureOffset)).xyz;
    vec3 offsetSW = texture(tex, uv + (vec2(-1.0f, 1.0f) * screenTextureOffset)).xyz;
    vec3 offsetSE = texture(tex, uv + (vec2(1.0f, 1.0f) * screenTextureOffset)).xyz;
    vec3 offsetM  = texture(tex, uv).xyz;

    float lumaNW = dot(luma, offsetNW);
    float lumaNE = dot(luma, offsetNE);
    float lumaSW = dot(luma, offsetSW);
    float lumaSE = dot(luma, offsetSE);
    float lumaM  = dot(luma, offsetNW);

    vec2 dir = vec2(-((lumaNW + lumaNE) - (lumaSW + lumaSE)),
                     ((lumaNW + lumaSW) - (lumaNE + lumaSE)));

    float dirReduce = max((lumaNW + lumaNE + lumaSW + lumaSE) * (FXAA_REDUCE_MUL * 0.25f), FXAA_REDUCE_MIN);
    float dirCorrection = 1.0f / (min(abs(dir.x), abs(dir.y)) + dirReduce);

    dir = min(vec2(FXAA_SPAN_MAX), max(vec2(-FXAA_SPAN_MAX), dir * dirCorrection)) * screenTextureOffset;

    vec3 resultA = 0.5f * (texture(tex, uv + (dir * vec2(1.0f / 3.0f - 0.5f))).xyz +
                                    texture(tex, uv + (dir * vec2(2.0f / 3.0f - 0.5f))).xyz);

    vec3 resultB = resultA * 0.5f + 0.25f * (texture(tex, uv + (dir * vec2(0.0f / 3.0f - 0.5f))).xyz +
                                             texture(tex, uv + (dir * vec2(3.0f / 3.0f - 0.5f))).xyz);

    float lumaMin = min(lumaM, min(min(lumaNW, lumaNE), min(lumaSW, lumaSE)));
    float lumaMax = max(lumaM, max(max(lumaNW, lumaNE), max(lumaSW, lumaSE)));
    float lumaResultB = dot(luma, resultB);

    if(lumaResultB < lumaMin || lumaResultB > lumaMax)
        return vec3(resultA);
    else
        return vec3(resultB);
}



void main() {
  vec2 uv = set_uv(flip_source,resolution_source);
  uv.y = 1.0 - uv.y;
  // vec2 uv = fragCoord.xy / iResolution.xy;
  
  vec3  col = texture2D(texture_source, vec2(uv.x,1.0-uv.y) ).xyz;
  float lum = dot(col,vec3(0.333));
  vec3 ocol = col;
  
  if( uv.x>0.5 )
  {
    // right side: changes in luminance
        float f = fwidth( lum );
        col *= 1.5*vec3( clamp(1.0-8.0*f,0.0,1.0) );
  }
    else
  {
    // bottom left: emboss
        vec3  nor = normalize( vec3( dFdx(lum), 64.0/resolution_source.x, dFdy(lum) ) );
    if( uv.y<0.5 )
    {
      float lig = 0.5 + dot(nor,vec3(0.7,0.2,-0.7));
            col = vec3(lig);
    }
    // top left: bump
        else
    {
            float lig = clamp( 0.5 + 1.5*dot(nor,vec3(0.7,0.2,-0.7)), 0.0, 1.0 );
            col *= vec3(lig);
    }
  }

    col *= smoothstep( 0.003, 0.004, abs(uv.x-0.5) );
  col *= 1.0 - (1.0-smoothstep( 0.007, 0.008, abs(uv.y-0.5) ))*(1.0-smoothstep(0.49,0.5,uv.x));
    col = mix( col, ocol, pow( 0.5 + 0.5*sin(time), 4.0 ) );
  
  gl_FragColor = vec4( col, 1.0 );
}
*/

/*
void main() {
  vec2 uv = set_uv(flip_source,resolution_source);
  uv -= 0.5;
  uv.x *= resolution_source.x / resolution_source.y;
  //uv.y *= resolution_source.y / resolution_source.x;
  gl_FragColor = texture2D(texture_source,uv);
}
*/

/*
void main()
{
  vec2 sc = set_uv(flip_source,resolution_source);
  // vec2 sc = vertTexCoord.st / resolution_source.xy;
    
  vec2 uv = vec2(sc.x * 0.1 - 0.05, 0.1) / (sc.y - 1.0);
  uv *= mat2(sin(time * 0.1), cos(time * 0.1), -cos(time * 0.1), sin(time * 0.1));
      
  vec2 uvMod = sc.x < 0.33 ? uv : sc.x < 0.66 ? magnify(uv) : quantize(uv);

  gl_FragColor = textureGrad(texture_source, uvMod, dFdx(uv), dFdy(uv));
}
*/



/*
void main() {
  // vec2 sc = set_uv(flip_source,resolution_source);
  // vec2 sc = vertTexCoord.st / resolution_source.xy;
    vec2 uv = set_uv(flip_source,resolution_source);
  // vec2 uv = vec2(sc.x * 0.1 - 0.05, 0.1) / (sc.y - 1.0);
  uv *= mat2(sin(time * 0.1), cos(time * 0.1), -cos(time * 0.1), sin(time * 0.1));
      
 //  vec2 uvMod = sc.x < 0.33 ? uv : sc.x < 0.66 ? magnify(uv) : quantize(uv);
gl_FragColor = textureGrad(texture_source, uv, dFdx(uv), dFdy(uv));
 // gl_FragColor = textureGrad(texture_source, uvMod, dFdx(uv), dFdy(uv));
  
    // vec2 uv = set_uv(flip_source,resolution_source);
    // float width = 1.0;
    // vec2 p = uv_aa_smoothstep(uv,resolution_source, width);
    // float detail = 10;
 


   // float theta = time / 12.;
    // vec2 trig = vec2(sin(theta), cos(theta));
    // vec2 p = vec2(uv.x,1.0-uv.y);
    // float detail = (5.0 + 5.0*sin( time ))*step( uv.x, 0.5 );
    // R = iResolution.xy;
    // ivec2 U = ivec2( u / uv * 8. ) % 2;
    // vec3 r = vec3( 2.* U / uv - 1. , 1 );                         // ray
    // vec2 p = - r.xz / r.y + .1 * time;
// gl_FragColor = texture2D(texture_source,p,detail);
    //gl_FragColor = textureLod(texture_source,p,detail);
    // vec2 uv = (fragCoord.xy * 2. - iResolution.xy) / min(iResolution.x, iResolution.y);
    /*float theta = time / 12.;
    vec2 trig = vec2(sin(theta), cos(theta));
    uv *= mat2(trig.y, -trig.x, trig.x, trig.y) / 8.;

    

    
    // if (mode == 0) {
    //   gl_FragColor = textureBlocky(texture_source, uv, resolution_source);
    // } else if (mode == 1) {
    //   gl_FragColor = textureUgly(texture_source, uv, resolution_source);
    // } else {
    //   gl_FragColor = texture(texture_source, uv);
    // }
    
}
*/
