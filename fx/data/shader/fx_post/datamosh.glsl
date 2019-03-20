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

uniform sampler2D texture_source;
uniform vec2 resolution_source;
uniform bvec2 flip_source; // can be use to flip texture source



uniform float time;

uniform vec2 resolution;
uniform sampler2D previous;
uniform sampler2D texture;
//optical flow variables
uniform float minVel= 0.01; 
uniform float maxVel = 0.5; 
uniform float offsetInc = 0.1;
uniform vec2 offset = vec2(1.0, 1.0);
uniform float lambda = 1.0;

//datamoshing variables
uniform float threshold = 0.15;
uniform float intensity = 5.0;
uniform float offsetRGB = 0.025;

in vec4 vertTexCoord;
in vec4 vertColor;
out vec4 fragColor;



// UTIL TEMPLATE
vec2 set_uv(bool flip_vertical, bool flip_horizontal, vec2 res) {
  vec2 uv;
  if(all(equal(vec2(0),res))) {
    uv = vertTexCoord.st;
  } else if(all(greaterThan(res,vertTexCoord.st))) {
    uv = vertTexCoord.st;
  } else {
    uv = res;
  }
  // flip 
  if(!flip_vertical && !flip_horizontal) {
    return uv;
  } else if(flip_vertical && !flip_horizontal) {
    uv.y = 1 -uv.y;
    return uv;
  } else if(!flip_vertical && flip_horizontal) {
    uv.x = 1 -uv.x;
    return uv;
  } else if(flip_vertical && flip_horizontal) {
    return vec2(1) -uv;
  } return uv;
}

vec2 set_uv(bvec2 flip, vec2 res) {
  return set_uv(flip.x,flip.y,res);
}

vec2 set_uv() {
  return set_uv(false,false,vec2(0));
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

vec4 getGradientAt(sampler2D current, sampler2D previous, vec2 texCoord, vec2 offset){
  vec4 gradient = getGrayTexture(previous, texCoord + offset) - getGrayTexture(previous, texCoord - offset);
  gradient += getGrayTexture(current, texCoord + offset) - getGrayTexture(current, texCoord - offset);
  return gradient;
}

vec2 getFlow(vec2 uv){
  vec2 flow = vec2(0.0);
  
  vec4 current = texture(texture, uv);
  vec4 prev = texture(previous, uv);
  
  vec2 offsetX = vec2(offset.x * offsetInc, 0.0);
  vec2 offsetY = vec2(0.0, offset.y * offsetInc);

  //Frame Differencing (dT)
  vec4 differencing = prev - current;
  float vel = (differencing.r + differencing.g + differencing.b)/3;
  float movement = smoothstep(minVel, maxVel, vel);
  vec4 newDifferencing = vec4(movement);
  //movement = pow(movement, 1.0);


  //Compute the gradient (movement Per Axis) (look alike sobel Operation)
  vec4 gradX = getGradientAt(texture, previous, uv, offsetX);
  vec4 gradY = getGradientAt(texture, previous, uv, offsetY);

  //Compute gradMagnitude
  vec4 gradMag = sqrt((gradX * gradX) + (gradY * gradY) + vec4(lambda));

  //compute Flow
  vec4 vx = newDifferencing * (gradX / gradMag);
  vec4 vy = newDifferencing * (gradY / gradMag);

  //vec4 flowCoded = packFlowAsColor(vx.r, vy.r, scale);
  flow += getFlowVector(vx.x, vy.x, vec2(intensity));   

  return flow;
}


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

vec2 getFlow5x5(vec2 uv){
  vec2 texel = vec2(1.0) /resolution;
  vec2 flow;

  flow += getFlow(uv.xy + vec2(-2.0, -2.0) * texel) * 0.125;
  flow += getFlow(uv.xy + vec2(-1.0, -2.0) * texel) * 0.25;
  flow += getFlow(uv.xy + vec2( 0.0, -2.0) * texel) * 0.5;
  flow += getFlow(uv.xy + vec2( 1.0, -2.0) * texel) * 0.25;
  flow += getFlow(uv.xy + vec2( 2.0, -2.0) * texel) * 0.125;
  flow += getFlow(uv.xy + vec2(-2.0, -1.0) * texel) * 0.25;
  flow += getFlow(uv.xy + vec2(-1.0, -1.0) * texel) * 0.5;
  flow += getFlow(uv.xy + vec2( 0.0, -1.0) * texel) * 0.75;
  flow += getFlow(uv.xy + vec2( 1.0, -1.0) * texel) * 0.5;
  flow += getFlow(uv.xy + vec2( 2.0, -1.0) * texel) * 0.25;
  flow += getFlow(uv.xy + vec2(-2.0,  0.0) * texel) * 0.5;
  flow += getFlow(uv.xy + vec2(-1.0,  0.0) * texel) * 0.75;
  flow += getFlow(uv.xy + vec2( 0.0,  0.0) * texel) * 1.0;
  flow += getFlow(uv.xy + vec2( 1.0,  0.0) * texel) * 0.75;
  flow += getFlow(uv.xy + vec2( 2.0,  0.0) * texel) * 0.5;
  flow += getFlow(uv.xy + vec2(-2.0,  1.0) * texel) * 0.25;
  flow += getFlow(uv.xy + vec2(-1.0,  1.0) * texel) * 0.5;
  flow += getFlow(uv.xy + vec2( 0.0,  1.0) * texel) * 0.75;
  flow += getFlow(uv.xy + vec2( 1.0,  1.0) * texel) * 0.5;
  flow += getFlow(uv.xy + vec2( 2.0,  1.0) * texel) * 0.25;
  flow += getFlow(uv.xy + vec2(-2.0,  2.0) * texel) * 0.125;
  flow += getFlow(uv.xy + vec2(-1.0,  2.0) * texel) * 0.25;
  flow += getFlow(uv.xy + vec2( 0.0,  2.0) * texel) * 0.5;
  flow += getFlow(uv.xy + vec2( 1.0,  2.0) * texel) * 0.25;
  flow += getFlow(uv.xy + vec2( 2.0,  2.0) * texel) * 0.125;


  return flow * (1.0/24.0);
}

void main() {
  vec2 uv = set_uv(flip_source,resolution_source);

  vec2 flow = getFlow5x5(uv);
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
  float r = texture(previous, st + shift).r;
  float g = texture(previous, st ).g;
  float b = texture(previous, st - shift).b;

  vec4 datamosh = texture(previous, st);
  datamosh.rgb = vec3(r, g, b) * stepper;

  vec4 color = texture(texture, uv.st);
  
  fragColor =  color * (1.0 - stepper) + datamosh * stepper;;
}


















/*
// Processing implementation
#ifdef GL_ES
precision highp float;
precision highp int;
#endif
#define PROCESSING_TEXTURE_SHADER
varying vec4 vertColor;
varying vec4 vertTexCoord;
uniform vec2 resolution; // WARNING VERY IMPORTANT // need this name for unknow reason :( here your pass your resolution texture
// vec2 texOffset   = vec2(1) / resolution; // only work with uniform resolution

// Rope implementation
uniform sampler2D texture_source;
uniform vec2 resolution_source;
uniform bvec2 flip_source; // can be use to flip texture source
// uniform vec4 level_source;

uniform sampler2D texture_layer;
uniform vec2 resolution_layer;
uniform bvec2 flip_layer; // can be use to flip texture layer
// uniform vec4 level_layer;

// uniform vec2 position; // maneep to receive a normal information 0 > 1

// uniform float time;
// uniform int mode;

// uniform int color_mode; // 0 is RGB / 3 is HSB

// uniform int num;
// uniform ivec3 size;


// uniform float angle;

// uniform float quality;
// uniform float offset;
// uniform float scale;

// uniform int rows;
// uniform int cols;x

// uniform bool use_fx_color;
// uniform bool use_fx;




//optical flow variables
uniform float minVel = 0.01; 
uniform float maxVel = 0.5; 
uniform float offsetInc = 0.1;
uniform vec2 offset = vec2(1.0, 1.0);
uniform float lambda = 1.0;

//datamoshing variables from Processing
uniform float time;
uniform float threshold;
// uniform float threshold = 0.15;
uniform float strength; // > is intensity in the original shader
// uniform float intensity = 5.0;
uniform float offsetRGB = 0.025;


#define PI 3.14159265359



// UTIL TEMPLATE
vec2 set_uv(bool flip_vertical, bool flip_horizontal, vec2 res) {
  vec2 uv;
  if(all(equal(vec2(0),res))) {
    uv = vertTexCoord.st;
  } else if(all(greaterThan(res,vertTexCoord.st))) {
    uv = vertTexCoord.st;
  } else {
    uv = res;
  }
  // flip 
  if(!flip_vertical && !flip_horizontal) {
    return uv;
  } else if(flip_vertical && !flip_horizontal) {
    uv.y = 1 -uv.y;
    return uv;
  } else if(!flip_vertical && flip_horizontal) {
    uv.x = 1 -uv.x;
    return uv;
  } else if(flip_vertical && flip_horizontal) {
    return vec2(1) -uv;
  } return uv;
}

vec2 set_uv(bvec2 flip, vec2 res) {
  return set_uv(flip.x,flip.y,res);
}

vec2 set_uv() {
  return set_uv(false,false,vec2(0));
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

vec4 getGradientAt(sampler2D current, sampler2D previous, vec2 texCoord, vec2 offset){
  vec4 gradient = getGrayTexture(previous, texCoord + offset) - getGrayTexture(previous, texCoord - offset);
  gradient += getGrayTexture(current, texCoord + offset) - getGrayTexture(current, texCoord - offset);
  return gradient;
}


vec2 getFlow(vec2 uv){
  vec2 flow = vec2(0.0);
  
  vec4 current = texture(texture_source, uv);
  vec4 prev = texture(texture_layer, uv);

  //Frame Differencing (dT)
  vec4 differencing = prev - current;
  float vel = (differencing.r + differencing.g + differencing.b)/3;
  float movement = smoothstep(minVel, maxVel, vel);
  vec4 newDifferencing = vec4(movement);
  //movement = pow(movement, 1.0);

  vec2 offsetX = vec2(offset.x * offsetInc, 0.0);
  vec2 offsetY = vec2(0.0, offset.y * offsetInc);
  //Compute the gradient (movement Per Axis) (look alike sobel Operation)
  vec4 gradX = getGradientAt(texture_source, texture_layer, uv, offsetX);
  vec4 gradY = getGradientAt(texture_source, texture_layer, uv, offsetY);

  //Compute gradMagnitude
  vec4 gradMag = sqrt((gradX * gradX) + (gradY * gradY) + vec4(lambda));

  //compute Flow
  vec4 vx = newDifferencing * (gradX / gradMag);
  vec4 vy = newDifferencing * (gradY / gradMag);

  //vec4 flowCoded = packFlowAsColor(vx.r, vy.r, scale);
  flow += getFlowVector(vx.x, vy.x, vec2(strength));   

  return flow;
}


// vec2 getFlow3x3(vec2 uv, vec2 res){
//   vec2 texel = vec2(1.0) /res;
//   vec2 flow  = getFlow(uv.xy + vec2(-1.0, -1.0) * texel) * 0.25;
//   flow += getFlow(uv.xy + vec2( 0.0, -1.0) * texel) * 0.5;
//   flow += getFlow(uv.xy + vec2( 1.0, -1.0) * texel) * 0.25;
//   flow += getFlow(uv.xy + vec2(-1.0,  0.0) * texel) * 0.5;
//   flow += getFlow(uv.xy + vec2( 0.0,  0.0) * texel) * 1.0;
//   flow += getFlow(uv.xy + vec2( 1.0,  0.0) * texel) * 0.5;
//   flow += getFlow(uv.xy + vec2(-1.0,  1.0) * texel) * 0.25;
//   flow += getFlow(uv.xy + vec2( 0.0,  1.0) * texel) * 0.5;
//   flow += getFlow(uv.xy + vec2( 1.0,  1.0) * texel) * 0.25;
//   return flow * 0.125;
// }


vec2 getFlow5x5(vec2 uv, vec2 res){
  vec2 texel = vec2(1.0) /res;
  vec2 flow;
  flow += getFlow(uv.xy + vec2(-2.0, -2.0) * texel) * 0.125;
  flow += getFlow(uv.xy + vec2(-1.0, -2.0) * texel) * 0.25;
  flow += getFlow(uv.xy + vec2( 0.0, -2.0) * texel) * 0.5;
  flow += getFlow(uv.xy + vec2( 1.0, -2.0) * texel) * 0.25;
  flow += getFlow(uv.xy + vec2( 2.0, -2.0) * texel) * 0.125;
  flow += getFlow(uv.xy + vec2(-2.0, -1.0) * texel) * 0.25;
  flow += getFlow(uv.xy + vec2(-1.0, -1.0) * texel) * 0.5;
  flow += getFlow(uv.xy + vec2( 0.0, -1.0) * texel) * 0.75;
  flow += getFlow(uv.xy + vec2( 1.0, -1.0) * texel) * 0.5;
  flow += getFlow(uv.xy + vec2( 2.0, -1.0) * texel) * 0.25;
  flow += getFlow(uv.xy + vec2(-2.0,  0.0) * texel) * 0.5;
  flow += getFlow(uv.xy + vec2(-1.0,  0.0) * texel) * 0.75;
  flow += getFlow(uv.xy + vec2( 0.0,  0.0) * texel) * 1.0;
  flow += getFlow(uv.xy + vec2( 1.0,  0.0) * texel) * 0.75;
  flow += getFlow(uv.xy + vec2( 2.0,  0.0) * texel) * 0.5;
  flow += getFlow(uv.xy + vec2(-2.0,  1.0) * texel) * 0.25;
  flow += getFlow(uv.xy + vec2(-1.0,  1.0) * texel) * 0.5;
  flow += getFlow(uv.xy + vec2( 0.0,  1.0) * texel) * 0.75;
  flow += getFlow(uv.xy + vec2( 1.0,  1.0) * texel) * 0.5;
  flow += getFlow(uv.xy + vec2( 2.0,  1.0) * texel) * 0.25;
  flow += getFlow(uv.xy + vec2(-2.0,  2.0) * texel) * 0.125;
  flow += getFlow(uv.xy + vec2(-1.0,  2.0) * texel) * 0.25;
  flow += getFlow(uv.xy + vec2( 0.0,  2.0) * texel) * 0.5;
  flow += getFlow(uv.xy + vec2( 1.0,  2.0) * texel) * 0.25;
  flow += getFlow(uv.xy + vec2( 2.0,  2.0) * texel) * 0.125;
  return flow * (1.0/24.0);
}

void main() {
  vec2 uv = set_uv(flip_source,resolution_source);

  vec2 flow = getFlow5x5(uv,resolution);
  float flowMag = length(flow.xy);

  float stepper = step(threshold, flowMag);
  flow.x = clamp(flow.x, -1.0, 1.0);
  flow.y = clamp(flow.y, -1.0, 1.0);

  vec2 st;
  vec2 texel = vec2(1.0) / resolution;
  st.x = uv.x + flow.x * texel.x * strength;
  st.y = uv.y + flow.y * texel.y * strength;

  //shift rgb
  vec2 shift = vec2(cos(flow.x * PI + time * 0.1), sin(flow.y * PI + time * 0.1)) * offsetRGB;
  float r = texture(texture_layer, st + shift).r;
  float g = texture(texture_layer, st ).g;
  float b = texture(texture_layer, st - shift).b;

  vec4 datamosh = texture(texture_layer, st);
  datamosh.rgb = vec3(r, g, b) * stepper;

  vec4 color = texture(texture_source,uv.st);
  
  gl_FragColor =  color * (1.0 - stepper) + datamosh * stepper;;
}
*/



