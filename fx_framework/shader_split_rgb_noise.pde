/**
* split rgb noise
* @see @stanlepunk
* @see https://github.com/StanLepunK/Shader
* v 0.0.6
* 2019-2019
*/

// use setting
PGraphics fx_split_rgb_noise(PImage source, FX fx) {
	return fx_split_rgb_noise(source,fx.on_g(),vec2(fx.get_offset()));
}



// main
PShader fx_split_rgb_noise;
PGraphics pg_split_rgb_noise;
PGraphics fx_split_rgb_noise(PImage source, boolean on_g, vec2 offset) {
	if(!on_g && (pg_split_rgb_noise == null 
								|| (source.width != pg_split_rgb_noise.width 
								&& source.height != pg_split_rgb_noise.height))) {
		pg_split_rgb_noise = createGraphics(source.width,source.height,get_renderer());
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
		boolean pg_filter_is = true;
    render_shader(fx_split_rgb_noise,pg_split_rgb_noise,source,on_g,pg_filter_is);
	}

	// end
	reset_reverse_g(false);
	if(on_g) {
		return null;
	} else {
		return pg_split_rgb_noise; 
	}
}