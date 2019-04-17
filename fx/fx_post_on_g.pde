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
	if((movie_input != null || img_input != null) && !window_change_is()) {
    if(movie_input != null) {
    	filter_g(movie_input, img_1,img_2, pattern_1,pattern_2);
    }
    if(img_input != null) {
    	filter_g(img_input, img_1,img_2, pattern_1,pattern_2);
    }
	}
}

PImage temp ;
float angle_g;

void filter_g(PImage input, PImage img_1, PImage img_2, PImage pattern_1, PImage pattern_2) {

	boolean with_g = true;
  if(with_g) {
  	// image(movie_input,CENTER);
  	background(input,CENTER);
  	if(incrust_is()) fx_inc_copy(g);
  	render_post_fx(g,input,img_1,img_2,pattern_1,pattern_2);
  } else {
  	render_post_fx(input,null,img_1,img_2,pattern_1,pattern_2);
	}
	if(incrust_is()) fx_inc(g);
}


void render_post_fx(PImage src_1, PImage src_2, PImage img_1, PImage img_2, PImage pattern_1, PImage pattern_2) {
	//select_filter(src_1,movie_input,null,FX_HALFTONE_DOT,FX_SCREEN);
	// select_filter(src,null,null,FX_SPLIT_RGB);
	//select_filter(src_1,null,null,FX_SPLIT_RGB);
	// println(get_fx(0).get_name());
	// select_filter(src,null,null,get_fx(0));

	// println(get_fx(3).get_name());

	// select_fx_post(src_1,null,null,get_fx(fx_manager,set_blur_gaussian));
	// select_fx_post(src_1,null,null,get_fx(fx_manager,set_blur_circular));
	// select_fx_post(src_1,null,null,get_fx(fx_manager,set_blur_radial));

	// select_fx_post(src_1,null,null,get_fx(fx_manager,set_colour_change_a));
	// select_fx_post(src_1,null,null,get_fx(fx_manager,set_colour_change_b));
  
  // select_fx_post(src_2,null,null,get_fx(fx_manager,set_datamosh)); // we must pass the movie

	// select_fx_post(src_1,null,null,get_fx(fx_manager,set_dither_bayer_8));

	// select_fx_post(src_1,null,null,get_fx(fx_manager,set_flip));

	// select_fx_post(src_1,null,null,get_fx(fx_manager,set_grain));
	// select_fx_post(src_1,null,null,get_fx(fx_manager,set_grain_scatter));

	// select_fx_post(src_1,null,null,get_fx(fx_manager,set_halftone_dot));
	// select_fx_post(src_1,null,null,get_fx(fx_manager,set_halftone_line));
	select_fx_post(src_1,null,null,get_fx(fx_manager,set_halftone_multi));

	// select_fx_post(src_1,null,null,get_fx(fx_manager,set_image));

	// select_fx_post(src_1,null,null,get_fx(fx_manager,set_level));

	// select_fx_post(src_1,src_1,null,get_fx(fx_manager,set_mix));

	// select_fx_post(src_1,null,null,get_fx(fx_manager,set_pixel));

	// select_fx_post(src_1,null,null,get_fx(fx_manager,set_reac_diff));
	// select_fx_post(src_1,null,null,get_fx(fx_manager,set_split));

	// select_fx_post(src_1,src_1,null,get_fx(fx_manager,set_threshold));

	// select_fx_post(src_1,null,null,get_fx(fx_manager,set_warp_proc));

	// select_fx_post(src_1,img_1,img_1,get_fx(fx_manager,set_warp_tex)); 
	// select_fx_post(src_1,noise_nb,noise_nb,get_fx(fx_manager,set_warp_tex)); 
	//select_fx_post(src_1,pattern_1,pattern_2,get_fx(fx_manager,set_warp_tex_a)); 
	// select_fx_post(src_1,src_1,null,get_fx(fx_manager,set_warp_tex_b)); 

	// select_fx_post(src_1,src_1,get_fx(fx_manager,set_scale));

	//select_fx_post(src_1,src_1,FX_HALFTONE_DOT,FX_WARP_PROC,FX_SPLIT_RGB);
}








PImage inc_fx;
void fx_inc_copy(PImage src) {
	if(inc_fx == null || inc_fx.width != src.width || inc_fx.height != src.height) {
		inc_fx = createImage(src.width,src.height,RGB);
	}
	inc_fx.copy(src,0,0,src.width,src.height, 0,0,src.width,src.height);
}






