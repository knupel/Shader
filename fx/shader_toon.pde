/**
* TOON by Stan le punk
* @see http://stanlepunk.xyz
* @see https://github.com/StanLepunK/Filter
* v 0.0.6
* 2018-2019
*/

/**
DONT WORK
*/


PGraphics fx_toon(PImage source) {
	return fx_toon(source,true);
}


PShader fx_toon;
PGraphics result_toon;
PGraphics fx_toon(PImage source, boolean on_g) {
	if(!on_g && (result_toon == null 
								|| (source.width != result_toon.width 
								&& source.height != result_toon.height))) {
		result_toon = createGraphics(source.width,source.height,get_renderer());
	}
	
	if(fx_toon == null) {
		String path = get_fx_post_path()+"toon.glsl";
		if(fx_post_rope_path_exists) {
			fx_toon = loadShader(path);
			println("load shader: toon.glsl");
		}
	} else {
		if(on_g) set_shader_flip(fx_toon,source);

		fx_toon.set("texture_source",source);
		fx_toon.set("resolution_source",source);
		float x = map(mouseX,0,width,0,1);
		float y = map(mouseY,0,height,0,1);
		fx_toon.set("offset",x,y);

		// rendering
		render_shader(fx_toon,result_toon,source,on_g);
	}

	// return
	reset_reverse_g(false);
	if(on_g) {
		return null;
	} else {
		return result_toon; 
	}
}