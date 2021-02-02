/**
UTILS FILTER SHADER
/**
* v 0.1.0
* 2019-2021
*
*/
R_Image_Manager lib_img = new R_Image_Manager();

void add_media() {
	explore_folder(folder(),false,"jpeg","jpg"); 
	// don't need to add in MAJ extension, the algo check for the UPPERCASE
	if(folder_is() && get_files() != null && get_files().size() > 0) {
  	for(File f : get_files()) {
	  	println("file:",f);
			lib_img.load(f.getAbsolutePath());
	  }
		folder_is(false);
  }
}


PImage img_a,img_b;
void render_mode_shader(int mode) {
	int index_img_0 = 0;
	int index_img_1 = 1;

	if(mode == 0) {
  	background(r.PINK);
  	setting_fx_post(fx_manager,false);
		draw_fx_post_on_tex(index_img_0);
	} else if(mode == 1) {
		setting_fx_post(fx_manager,true);
		draw_fx_post_on_g(index_img_0, index_img_1, img_noise_1, img_noise_2);
		// draw_fx_post_on_g(img_a,img_b,img_noise_1,img_noise_2);
	} else if(mode == 2) {
		setting_fx_bg(fx_bg_manager);
		draw_fx_bg();
	}
}

/**
* background fx
* 2019-2019
*/
void draw_fx_bg() {
  // select_fx_background(get_fx(fx_bg_manager,set_template_fx_bg));
	// select_fx_background(get_fx(fx_bg_manager,set_cellular_fx_bg));
	// select_fx_background(get_fx(fx_bg_manager,set_heart_fx_bg));
	// select_fx_background(get_fx(fx_bg_manager,set_necklace_fx_bg));
	// select_fx_background(get_fx(fx_bg_manager,set_neon_fx_bg));
	// select_fx_background(get_fx(fx_bg_manager,set_psy_fx_bg));
	// select_fx_background(get_fx(fx_bg_manager,set_snow_fx_bg));
	select_fx_background(get_fx(fx_bg_manager,set_voronoi_hex_fx_bg));
}


float rot_x; 
float rot_y;
void other_stuff() {
	translate(width/2,height/2);
	rotateX(rot_x += .01);
	rotateY(rot_y += .02);
	box(600,100,100);
}





PImage img_noise_1, img_noise_2;
void set_pattern(int w, int h, int mode, boolean event) {
	if(event || img_noise_1 == null && img_noise_2 == null) {
		if(mode == RGB) {
			img_noise_1 = generate_pattern(3,w,h); // warp RGB
			img_noise_2 = generate_pattern(3,w,h); // warp RGB
		} else if(mode == GRAY) {
			img_noise_1 = generate_pattern(0,w,h); // warp GRAY
			img_noise_2 = generate_pattern(0,w,h); // warp GRAY
		}	
	} 
}

PGraphics generate_pattern(int mode, int sx, int sw) {
	vec3 inc = vec3(random(1)*random(1),random(1)*random(1),random(1)*random(1));
	if(mode == 0) {
		// black and white
		return pattern_noise(sx,sw,inc.x);
	} else if(mode == 3) {
		// rgb
		return pattern_noise(sx,sw,inc.array());
	}	else return null;
}

/**
* set img from path
*/
PImage img_input;
void set_img(PImage img) {
	if(img_input == null && img != null && !media_is()) {
		println("PImage img size", img.width, img.height);
		// img_input = img.copy();
		img_input = img;
		media_is(true);
	} else if(img_input != null && media_is()) {
	  if(window_ref == null || !window_ref.equals(ivec2(img_input.width,img_input.height))) {
	  	surface.setSize(img_input.width,img_input.height);
	  	window_ref = ivec2(img_input.width,img_input.height);
	  }
	}
}

/**
* PGraphics converter for PImage or Movie
* 2018-2018
* v 0.0.4
*/
import processing.video.Movie;
ArrayList <PGraphics> pg_temp_list;

PGraphics to_pgraphics(Object obj) {
	return to_pgraphics(obj,0);
}

PGraphics to_pgraphics(Object obj, int target) {
	// manage list of PGraphics
	if(pg_temp_list == null) {
		pg_temp_list = new ArrayList<PGraphics>();
	}
	if(pg_temp_list.size() < target+1) {
		PGraphics pg = createGraphics(0,0,get_renderer());
		pg_temp_list.add(pg);
	} 

	PGraphics pg_temp = pg_temp_list.get(target);
	if(obj instanceof Movie) {

		int w = ((Movie)obj).width;
		int h = ((Movie)obj).height;
		pg_temp = set_pgraphics_temp(pg_temp,w,h);
		if(obj != null && pg_temp != null && pg_temp.width > 1 && pg_temp.height > 1) {
			pg_temp.beginDraw();
			pg_temp.image((Movie)obj,0,0,w,h);
			pg_temp.endDraw();
		}
	} else if(obj instanceof PImage) {
		int w = ((PImage)obj).width;
		int h = ((PImage)obj).height;
		pg_temp = set_pgraphics_temp(pg_temp,w,h);
		// pg_temp = createGraphics(w,h,get_renderer());
		if(obj != null && pg_temp != null && pg_temp.width > 1 && pg_temp.height > 1) {
			//pass_render_to_pgraphics((PImage)obj);
			pg_temp.beginDraw();
			pg_temp.image((PImage)obj,0,0,w,h);
			pg_temp.endDraw();	
		}
	} else {
		printErr("method to_pgraphics() wait for Object from class Movie, PImage or PGraphics");
	}

	// image(pg_temp);
	return pg_temp;
}

PGraphics set_pgraphics_temp(PGraphics pg, int w, int h) {
	if(pg == null || pg.width != w || pg.height != h) {
		pg = createGraphics(w,h,get_renderer());
	}
	return pg; 
}

