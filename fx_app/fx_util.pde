/**
UTILS FILTER SHADER
/**
* v 0.1.0
* 2019-2021
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
* set img from path
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

