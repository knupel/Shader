/**
* Datamosh POST FX
* refactoring shader
* @see @stanlepunk
* @see https://github.com/StanLepunK/Shader
* original shader from Alexandre Rivaux
* @see https://github.com/alexr4/datamoshing-GLSL
* v 0.0.1
* 2019-2019
*/

#ifdef GL_ES
precision mediump float;
precision mediump int;
#endif
#define PROCESSING_TEXTURE_SHADER

#define PI 3.14159265359

uniform sampler2D texture;
uniform vec2 resolution_source;
uniform ivec2 flip_source; // can be use to flip texture source

uniform sampler2D texture_layer;


// uniform sampler2D texture_source;
uniform vec2 resolution;




//optical flow variables
uniform float minVel= 0.01; 
uniform float maxVel = 0.5; 
uniform float offsetInc = 0.1;
uniform vec2 offset = vec2(1.0, 1.0);
uniform float lambda = 1.0;

//datamoshing variables
uniform float time;
uniform float threshold = 0.15;
uniform float intensity = 5.0;
uniform float offsetRGB = 0.025;

in vec4 vertTexCoord;
in vec4 vertColor;
out vec4 fragColor;



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






vec4 packFlowAsColor(float fx ,float fy, vec2 scale){
  vec2 flowX = vec2(max(fx, 0.0), abs(min(fx, 0.0))) * scale.x;
  vec2 flowY = vec2(max(fy, 0.0), abs(min(fy, 0.0))) * scale.y;
  float dirY = 1.0;
  if(flowY.x > flowY.y){
    dirY = 0.9;
  }
  vec4 rgbaPacked = vec4(flowX.x, flowX.y, max(flowY.x, flowY.y), dirY);

  return rgbaPacked;
}

vec2 getFlowVector(float fx ,float fy, vec2 scale){
  vec2 flowX = vec2(max(fx, 0.0), abs(min(fx, 0.0))) * scale.x;
  vec2 flowY = vec2(max(fy, 0.0), abs(min(fy, 0.0))) * scale.y;
  float dirY = 1.0;
  if(flowY.x > flowY.y){
    dirY = -1.0;
  }
  //vec4 rgbaPacked = vec4(flowX.x, flowX.y, max(flowY.x, flowY.y), dirY);

  float x = flowX.x + flowX.y * -1.0;
  float y = max(flowY.x, flowY.y) * dirY;

  return vec2(x, y);
}

vec4 getGray(vec4 inputPix){
  float gray = dot(vec3(inputPix.x, inputPix.y, inputPix.z), vec3(0.3, 0.59, 0.11));
  return vec4(gray, gray, gray, 1.0);
}

vec4 getGrayTexture(sampler2D tex, vec2 texCoord){
  return getGray(texture2D(tex, texCoord));
}

vec4 getGradientAt(sampler2D tex_src, sampler2D tex_layer, vec2 texCoord, vec2 offset){
  vec4 gradient = getGrayTexture(tex_layer, texCoord + offset) - getGrayTexture(tex_layer, texCoord - offset);
  gradient += getGrayTexture(tex_src, texCoord + offset) - getGrayTexture(tex_src, texCoord - offset);
  return gradient;
}

vec2 getFlow(vec2 uv, sampler2D tex_src, sampler2D tex_layer) {
  vec2 flow = vec2(0.0);
  
  vec4 current = texture(tex_src, uv);
  vec4 prev = texture(tex_layer, uv);
  
  vec2 offsetX = vec2(offset.x * offsetInc, 0.0);
  vec2 offsetY = vec2(0.0, offset.y * offsetInc);

  //Frame Differencing (dT)
  vec4 differencing = prev - current;
  float vel = (differencing.r + differencing.g + differencing.b)/3;
  float movement = smoothstep(minVel, maxVel, vel);
  vec4 newDifferencing = vec4(movement);
  //movement = pow(movement, 1.0);


  //Compute the gradient (movement Per Axis) (look alike sobel Operation)
  vec4 gradX = getGradientAt(tex_src, tex_layer, uv, offsetX);
  vec4 gradY = getGradientAt(tex_src, tex_layer, uv, offsetY);

  //Compute gradMagnitude
  vec4 gradMag = sqrt((gradX * gradX) + (gradY * gradY) + vec4(lambda));

  //compute Flow
  vec4 vx = newDifferencing * (gradX / gradMag);
  vec4 vy = newDifferencing * (gradY / gradMag);

  //vec4 flowCoded = packFlowAsColor(vx.r, vy.r, scale);
  flow += getFlowVector(vx.x, vy.x, vec2(intensity));   

  return flow;
}

/*
vec2 getFlow3x3(vec2 uv){
  vec2 texel = vec2(1.0) /resolution;
  vec2 flow  = getFlow(uv.xy + vec2(-1.0, -1.0) * texel) * 0.25;
  flow += getFlow(uv.xy + vec2( 0.0, -1.0) * texel) * 0.5;
  flow += getFlow(uv.xy + vec2( 1.0, -1.0) * texel) * 0.25;
  flow += getFlow(uv.xy + vec2(-1.0,  0.0) * texel) * 0.5;
  flow += getFlow(uv.xy + vec2( 0.0,  0.0) * texel) * 1.0;
  flow += getFlow(uv.xy + vec2( 1.0,  0.0) * texel) * 0.5;
  flow += getFlow(uv.xy + vec2(-1.0,  1.0) * texel) * 0.25;
  flow += getFlow(uv.xy + vec2( 0.0,  1.0) * texel) * 0.5;
  flow += getFlow(uv.xy + vec2( 1.0,  1.0) * texel) * 0.25;
  return flow * 0.125;
}
*/

vec2 getFlow5x5(vec2 uv, sampler2D tex_src, sampler2D tex_layer) {
  vec2 texel = vec2(1.0) /resolution;
  vec2 flow;

  flow += getFlow(uv.xy + vec2(-2.0, -2.0) * texel,tex_src,tex_layer) * 0.125;
  flow += getFlow(uv.xy + vec2(-1.0, -2.0) * texel,tex_src,tex_layer) * 0.25;
  flow += getFlow(uv.xy + vec2( 0.0, -2.0) * texel,tex_src,tex_layer) * 0.5;
  flow += getFlow(uv.xy + vec2( 1.0, -2.0) * texel,tex_src,tex_layer) * 0.25;
  flow += getFlow(uv.xy + vec2( 2.0, -2.0) * texel,tex_src,tex_layer) * 0.125;
  flow += getFlow(uv.xy + vec2(-2.0, -1.0) * texel,tex_src,tex_layer) * 0.25;
  flow += getFlow(uv.xy + vec2(-1.0, -1.0) * texel,tex_src,tex_layer) * 0.5;
  flow += getFlow(uv.xy + vec2( 0.0, -1.0) * texel,tex_src,tex_layer) * 0.75;
  flow += getFlow(uv.xy + vec2( 1.0, -1.0) * texel,tex_src,tex_layer) * 0.5;
  flow += getFlow(uv.xy + vec2( 2.0, -1.0) * texel,tex_src,tex_layer) * 0.25;
  flow += getFlow(uv.xy + vec2(-2.0,  0.0) * texel,tex_src,tex_layer) * 0.5;
  flow += getFlow(uv.xy + vec2(-1.0,  0.0) * texel,tex_src,tex_layer) * 0.75;
  flow += getFlow(uv.xy + vec2( 0.0,  0.0) * texel,tex_src,tex_layer) * 1.0;
  flow += getFlow(uv.xy + vec2( 1.0,  0.0) * texel,tex_src,tex_layer) * 0.75;
  flow += getFlow(uv.xy + vec2( 2.0,  0.0) * texel,tex_src,tex_layer) * 0.5;
  flow += getFlow(uv.xy + vec2(-2.0,  1.0) * texel,tex_src,tex_layer) * 0.25;
  flow += getFlow(uv.xy + vec2(-1.0,  1.0) * texel,tex_src,tex_layer) * 0.5;
  flow += getFlow(uv.xy + vec2( 0.0,  1.0) * texel,tex_src,tex_layer) * 0.75;
  flow += getFlow(uv.xy + vec2( 1.0,  1.0) * texel,tex_src,tex_layer) * 0.5;
  flow += getFlow(uv.xy + vec2( 2.0,  1.0) * texel,tex_src,tex_layer) * 0.25;
  flow += getFlow(uv.xy + vec2(-2.0,  2.0) * texel,tex_src,tex_layer) * 0.125;
  flow += getFlow(uv.xy + vec2(-1.0,  2.0) * texel,tex_src,tex_layer) * 0.25;
  flow += getFlow(uv.xy + vec2( 0.0,  2.0) * texel,tex_src,tex_layer) * 0.5;
  flow += getFlow(uv.xy + vec2( 1.0,  2.0) * texel,tex_src,tex_layer) * 0.25;
  flow += getFlow(uv.xy + vec2( 2.0,  2.0) * texel,tex_src,tex_layer) * 0.125;
  return flow * (1.0/24.0);
}

void main() {
  vec2 uv = set_uv(flip_source,resolution_source);

  vec2 flow = getFlow5x5(uv, texture, texture_layer);
  float flowMag = length(flow.xy);

  float stepper = step(threshold, flowMag);
  flow.x = clamp(flow.x, -1.0, 1.0);
  flow.y = clamp(flow.y, -1.0, 1.0);

  vec2 st;
  vec2 texel = vec2(1.0) / resolution;
  st.x = uv.x + flow.x * texel.x * intensity;
  st.y = uv.y + flow.y * texel.y * intensity;

  //shift rgb
  vec2 shift = vec2(cos(flow.x * PI + time * 0.1), sin(flow.y * PI + time * 0.1)) * offsetRGB;
  float r = texture(texture_layer, st + shift).r;
  float g = texture(texture_layer, st ).g;
  float b = texture(texture_layer, st - shift).b;

  vec4 datamosh = texture(texture_layer, st);
  datamosh.rgb = vec3(r, g, b) * stepper;

  vec4 color = texture(texture, uv.st);
  
  fragColor =  color * (1.0 - stepper) + datamosh * stepper;;
}














