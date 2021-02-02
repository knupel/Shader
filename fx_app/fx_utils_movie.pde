/**
* fx utils movie
* 2021-2021
* set movie from path
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
		if(up_is()) set_movie_volume(0.01);
		if(down_is()) set_movie_volume(-0.01);

		if(right_is()) {
			go_left_is = false;
			go_right_is = true;
			set_movie_direction(0.5);
		}
		if(left_is()) {
			go_left_is = true;
			go_right_is = false;
			set_movie_direction(-0.5);
		}

		if(space_is()) {
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


