/**
* warp texture by Stan le Punk 
* @see https://github.com/StanLepunK/Filter
* v 0.0.6
* 2018-2019
* collection of shader filter and example to use easily
* Enjoy, share, copy, paste and blahblah
* Processing 3.5.3
*/
PGraphics render;

ArrayList<FX> fx_manager = new ArrayList<FX>();
ArrayList<FX> fx_bg_manager = new ArrayList<FX>();

PImage img_a,img_b;

void load_media() {
	img_a = loadImage("medium_leticia.jpg");
	img_b = loadImage("medium_puros_girl.jpg");
}

// int mode_shader = 0; // POST FX on PGrahics > texture
// int mode_shader = 1; // POST FX on G
int mode_shader = 2; // FX BACKGROUND
void setup() {
	// if you change the shader/fx folder, utili in case you export your sketch, the shader folder is not exported.
	// set_fx_path(sketchPath(2)+"/shader/fx/"); 
	
	size(1200,700,P3D);
	load_media();
	set_pattern(16,16,RGB,true);
	if(mode_shader == 0) {
		setup_fx_post_on_tex();
	} else if(mode_shader == 1){
		setup_fx_post_on_g();
	} 
}

boolean media_is;
void draw() {	
	set_pattern(16,16,RGB,reset_is());

	String rate = ""+frameRate;
	surface.setTitle(rate);
  if(mode_shader == 0) {
  	setting_fx_post(fx_manager);
		draw_fx_post_on_tex();
	} else if(mode_shader == 1) {
		setting_fx_post(fx_manager);
		draw_fx_post_on_g(img_a,img_b,img_noise_1,img_noise_2);
	} else if(mode_shader == 2) {
		setting_fx_bg(fx_bg_manager);
		draw_fx_bg();

	}

	remote_command_movie();
	reset(false);
	
}













boolean reset = false;
void reset(boolean reset){
	this.reset = reset;
}

boolean reset_is() {
	return reset;
}



// EVENT
void movieEvent(Movie m) {
	m.read();
}

// scale from wheel
int wheel_click;
float get_wheel_scale() {
	float inc = .1;
	if(wheel_click < 0) {
		wheel_click += inc;
	} else if (wheel_click > 0) {
		wheel_click -= inc;
	}
	return wheel_click;
}

void mouseWheel(MouseEvent e) {
	wheel_click = e.getCount();
}


void mousePressed() {
	
}

boolean key_up = false;
boolean key_down = false;
boolean key_left = false;
boolean key_right = false;
boolean key_space = false;
void keyPressed() {
	if(key == CODED) {
		if(keyCode == UP) {
			key_up = true;
		}

		if(keyCode == DOWN) {
			key_down = true;
		}

		if(keyCode == LEFT) {
			key_left = true;
			set_movie_speed(-1);
		}

		if(keyCode == RIGHT) {
			key_right = true;
			set_movie_speed(1);
		}
	}

	if(key == ' ') {
		if(key_space) key_space = false ; else key_space = true;
	}

	if(key == 'n') {
		reset(true);
	}
}

void keyReleased() {
	key_up = false;
	key_down = false;
	key_left = false;
	key_right = false;
}

