/**
* split rgb noise
* @see http://stanlepunk.xyz
* @see https://github.com/StanLepunK/Shader
* v 0.0.5
* 2019-2019
*/

// use setting
PGraphics fx_split_rgb_noise(PImage source, FX fx) {
	return fx_split_rgb_noise(source,fx.on_g(),vec2(fx.get_offset()));
}




// test
PGraphics fx_split_rgb_noise(PImage source, boolean on_g) {
	float ox = sin(frameCount *.001) *(width/10);
	float oy = cos(frameCount *.001) *(height/10);
	vec2 offset = vec2(ox,oy);
	return fx_split_rgb_noise(source,on_g,offset);
}


// main
PShader fx_split_rgb_noise;
PGraphics result_split_rgb_noise;
PGraphics fx_split_rgb_noise(PImage source, boolean on_g, vec2 offset) {
	if(!on_g && (result_split_rgb_noise == null 
								|| (source.width != result_split_rgb_noise.width 
								&& source.height != result_split_rgb_noise.height))) {
		result_split_rgb_noise = createGraphics(source.width,source.height,get_renderer());
	}

	if(fx_split_rgb_noise == null) {
		String path = get_fx_post_path()+"split_rgb_noise.glsl";
		if(fx_post_rope_path_exists) {
			fx_split_rgb_noise = loadShader(path);
			println("load shader:",path);
		}
	} else {
    if(on_g) set_shader_flip(fx_split_rgb_noise,source);
		fx_split_rgb_noise.set("texture_source",source);
		fx_split_rgb_noise.set("resolution_source",source.width,source.height);

		// external param
		fx_split_rgb_noise.set("offset",offset.x,offset.y);

		 // rendering
    render_shader(fx_split_rgb_noise,result_split_rgb_noise,source,on_g);
	}

	// end
	reset_reverse_g(false);
	if(on_g) {
		return null;
	} else {
		return result_split_rgb_noise; 
	}
}