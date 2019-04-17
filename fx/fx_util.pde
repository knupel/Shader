/**
UTILS FILTER SHADER
/**
* @see http://stanlepunk.xyz
* @see https://github.com/StanLepunK/Filter
* v 0.0.5
* 2019-2019
*
*/



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
set img from path
*/
PImage img_input;
void set_img(String path) {
	if(img_input == null && path != null && !media_is) {
		println(path);
		if(extension_is(path,"jpg","jpeg")) {
			println("method set_img():",path);
			img_input = loadImage(path);
			media_is = true;
		} else {
			printErr("method set_img(): [",path,"] don't match for class PImage");
		}

	} else if(img_input != null && media_is) {
	  if(window_ref == null || !window_ref.equals(ivec2(img_input.width,img_input.height))) {
	  	surface.setSize(img_input.width,img_input.height);
	  	window_ref = ivec2(img_input.width,img_input.height);
	  }
	}
}

/**
set movie from path
*/
import processing.video.Movie;
Movie movie_input;
void set_movie(String path) {
	if(movie_input == null && path != null && !media_is) {
		if(extension_is(path,"avi","mov","mp4","mpg")) {
			println("method set_movie():",path);
			movie_input = new Movie(this,path);
			movie_input.loop();
			media_is = true;
		} else {
			printErr("method set_movie(): [",path,"] don't match for class Movie");
		}
	} else if(movie_input != null && media_is) {
	  if(window_ref == null || !window_ref.equals(ivec2(movie_input.width,movie_input.height))) {
	  	surface.setSize(movie_input.width,movie_input.height);
	  	window_ref = ivec2(movie_input.width,movie_input.height);
	  }
	}
}


boolean reset_movie_speep_is;
boolean go_left_is;
boolean go_right_is;
boolean go_left_is_ref;
boolean go_right_is_ref;
void remote_command_movie() {
	if(movie_input != null) {
		if(key_up) set_movie_volume(0.01);
		if(key_down) set_movie_volume(-0.01);

		if(key_right) {
			go_left_is = false;
			go_right_is = true;
			set_movie_direction(0.5);
		}
		if(key_left) {
			go_left_is = true;
			go_right_is = false;
			set_movie_direction(-0.5);
		}

		if(key_space) {
			reset_movie_speep_is = true;
			movie_input.pause(); 
		} else {
			movie_input.loop();
		}

		if(reset_movie_speep_is || go_left_is != go_left_is_ref || go_right_is != go_right_is_ref) {
			go_left_is_ref = go_left_is;
			go_right_is_ref = go_right_is;
			mult_speed_movie = 1;
			reset_movie_speep_is = false;
		}
	}
}


float movie_volume = 1;
void set_movie_volume(float inc) {
	movie_volume += inc;
	if(movie_volume < 0) {
		movie_volume = 0;
	} else if(movie_volume > 1) {
		movie_volume = 1;
	}
	if(movie_input != null) {
		movie_input.volume(movie_volume);
	}
}


int mult_speed_movie = 1;
void set_movie_speed(int inc) {
	mult_speed_movie += inc;
	if(mult_speed_movie == 0 && inc > 0) mult_speed_movie = 1;
	if(mult_speed_movie == 0 && inc < 0) mult_speed_movie = -1;

}


void set_movie_direction(float inc) {
	float speed = inc*abs(mult_speed_movie);
	println(speed);
	if(speed > 0) {
		movie_input.speed(speed);
	} else {
		float jump = movie_input.time() + speed;
		if(jump < 0) jump = 0;
		if(jump > movie_input.duration()) jump = movie_input.duration();
		movie_input.jump(jump);
	}
}






/**
PGraphics converter for PImage or Movie
2018-2018
v 0.0.4
*/
import processing.video.Movie;
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

ArrayList <PGraphics> pg_temp_list;

PGraphics set_pgraphics_temp(PGraphics pg, int w, int h) {
	if(pg == null || pg.width != w || pg.height != h) {
		pg = createGraphics(w,h,get_renderer());
	}
	return pg; 
}

