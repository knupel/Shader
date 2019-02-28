/**
Stan le punk version
* @see http://stanlepunk.xyz
* @see https://github.com/StanLepunK/Shader
v 0.1.0
2018-2018
based on work of Raphaël de Courville 
@see https://github.com/SableRaf/Filters4Processing
*/
// Processing implementation
#ifdef GL_ES
precision highp float;
#endif
#define PROCESSING_TEXTURE_SHADER
varying vec4 vertColor;
varying vec4 vertTexCoord;

// sketch implementation
// sketch implementation template, uniform use by most of filter Romanesco shader
uniform sampler2D texture_source;
uniform bvec2 flip_source; // can be use to flip texture
uniform vec2 resolution_source;

// sketch implementation template, uniform use by most of filter Romanesco shader
uniform sampler2D texture_layer;
uniform bvec2 flip_layer; // can be use to flip texture
uniform vec2 resolution_layer;

uniform int mode;
uniform vec3 level_source;

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




void main() {	
	vec2 uv_source = set_uv(flip_source,resolution_source);
	vec2 uv_layer = set_uv(flip_layer,resolution_layer);
	uv_source.y = uv_source.y;
	
	vec3 pixel_src = texture2D(texture_source, uv_source).rgb;
	float gray_scale = length(pixel_src *vec3(0.2126,0.7152,0.0722));
	
	vec3 dither_pix = texture2D(texture_layer, vec2(mod(uv_layer,1.0))).xyz;
	float dither_average = (dither_pix.x + dither_pix.y + dither_pix.z) / 3.0;
	
	float dithered_result = gray_scale + dither_average;
	if(mode == 0) {
		float bit_gray = dithered_result >= level_source.x ? 1.0 : 0.0;
		gl_FragColor = vec4(bit_gray,bit_gray,bit_gray,1);
	} else if(mode == 1) {
		float bit_red = dithered_result >= level_source.x ? 1.0 : 0.0;
		float bit_green = dithered_result >= level_source.y ? 1.0 : 0.0;
		float bit_blue = dithered_result >= level_source.z ? 1.0 : 0.0;
		gl_FragColor = vec4(bit_red,bit_green,bit_blue,1);
	} else {
		float bit_gray = dithered_result >= level_source.x ? 1.0 : 0.0;
		gl_FragColor = vec4(bit_gray,bit_gray,bit_gray,1);
	}
}




