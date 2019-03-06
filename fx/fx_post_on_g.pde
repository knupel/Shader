/**
Work around filter g it( little hard,
so the option to copy PGraphics g is used, but not sure that's be good with huge rendering sinze
*/
void setup_fx_post_on_g() {
	select_input();
}

void draw_fx_post_on_g(PImage img_1, PImage img_2, PImage pattern_1, PImage pattern_2) {
	set_movie(input());
	set_img(input());
	filter_g(img_1,img_2,pattern_1,pattern_2);
}


PImage temp ;
float angle_g;

void filter_g(PImage img_1, PImage img_2, PImage pattern_1, PImage pattern_2) {
	/*
	background_rope(255,0,0,50);
	fill(0,255,255);
	noStroke();
	rectMode(CENTER);
	angle_g += .01;
	pushMatrix();
	translate(mouseX,mouseY);
	rotate(angle_g);
	rect(0,0,width,height/4);
	popMatrix();
	*/
	
	// if(!window_change_is()) fx_mix_order_media(img_1,img_2,mode_mix);
	// if(!window_change_is()) fx_template(img_1);
	
	boolean with_g = true;
	if((movie_input != null || img_input != null) && !window_change_is()) {
		if(img_input != null) {
			if(with_g) {
				background(img_input,CENTER);
				render_post_fx(g,img_1,img_2,pattern_1,pattern_2);
			} else {
				render_post_fx(img_input,img_1,img_2,pattern_1,pattern_2);
			}
		} else if(movie_input != null) {
			if(with_g) {
				background(movie_input,CENTER);
				render_post_fx(g,img_1,img_2,pattern_1,pattern_2);
			} else {
				render_post_fx(movie_input,img_1,img_2,pattern_1,pattern_2);
			}
		} 
		
		


	} 
	if(img_input != null && !window_change_is()) {
		// fx(img_input,img_input, mode_mix);
	}

}


void render_post_fx(PImage src, PImage img_1, PImage img_2, PImage pattern_1, PImage pattern_2) {
	//select_filter(src,movie_input,null,FX_HALFTONE_DOT,FX_SCREEN);
	// select_filter(src,null,null,FX_SPLIT_RGB);
	//select_filter(src,null,null,FX_SPLIT_RGB);
	// println(get_fx(0).get_name());
	// select_filter(src,null,null,get_fx(0));

	// println(get_fx(3).get_name());

	select_fx_post(src,null,null,get_fx(fx_manager,set_blur_gaussian));
	// select_fx_post(src,null,null,get_fx(fx_manager,set_blur_circular));
	// select_fx_post(src,null,null,get_fx(fx_manager,set_blur_radial));

	// select_fx_post(src,null,null,get_fx(fx_manager,set_colour_change_a));
	// select_fx_post(src,null,null,get_fx(fx_manager,set_colour_change_b));

	// select_fx_post(src,null,null,get_fx(fx_manager,set_dither_bayer_8));

	// select_fx_post(src,null,null,get_fx(fx_manager,set_grain));
	// select_fx_post(src,null,null,get_fx(fx_manager,set_grain_scatter));

	// select_fx_post(src,null,null,get_fx(fx_manager,set_halftone_dot));
	// select_fx_post(src,null,null,get_fx(fx_manager,set_halftone_line));
	// select_fx_post(src,null,null,get_fx(fx_manager,set_halftone_multi));

	// select_fx_post(src,null,null,get_fx(fx_manager,set_image));

	// select_fx_post(src,null,null,get_fx(fx_manager,set_level));

	// select_fx_post(src,src,null,get_fx(fx_manager,set_mix));

	// select_fx_post(src,null,null,get_fx(fx_manager,set_pixel));

	// select_fx_post(src,null,null,get_fx(fx_manager,set_reac_diff));
	// select_fx_post(src,null,null,get_fx(fx_manager,set_split));

	// select_fx_post(src,src,null,get_fx(fx_manager,set_threshold));

	// select_fx_post(src,null,null,get_fx(fx_manager,set_warp_proc));

	// select_fx_post(src,img_1,img_1,get_fx(fx_manager,set_warp_tex)); 
	// select_fx_post(src,noise_nb,noise_nb,get_fx(fx_manager,set_warp_tex)); 
	//select_fx_post(src,pattern_1,pattern_2,get_fx(fx_manager,set_warp_tex_a)); 
	// select_fx_post(src,src,null,get_fx(fx_manager,set_warp_tex_b)); 

	// select_fx_post(src,src,get_fx(fx_manager,set_scale));

	//select_fx_post(src,src,FX_HALFTONE_DOT,FX_WARP_PROC,FX_SPLIT_RGB);
}


/*
void select_filter(int... type) {
	select_filter(g,g,type);
}
*/




/**
FILTER SELECTOR
v 0.0.3
2019-2019
*/
/*
void select_filter(PImage main, PImage layer_a, PImage layer_b, int... type)  {
	FX [] fx = new FX[type.length];
	for(int i = 0 ; i < fx.length ; i++) {
		fx[0] = new FX(null,type[i]);
	}
	if(fx != null) select_filter(main,layer_a,layer_b,fx);
}
*/
















