/**
* FX framework by Stan le Punk 
* the framework attempt to use a collection of shader FX for background or POST FX
* by class FX to manage and set all shaders.

* You can use easily the class and method FX without the framework part, just use the tab start by Y_ and Z_ and make your own shader world.
* @see https://github.com/StanLepunK/Shader
* v 0.0.6
* 2018-2019
* This little framework use Rope Library and Rope framework.
* More about Rope Library 
* @see https://github.com/StanLepunK/Rope
* More about Rope framework
* @see https://github.com/StanLepunK/Rope_method

* 
* Enjoy, share, copy, paste, improve and blahblah
* Processing 3.5.3.269
* Rope Library 0.7.1.25
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
int mode_shader = 1; // POST FX on G
// int mode_shader = 2; // FX BACKGROUND


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

float total_fps;
float avg_fps;
void draw() {	
	set_pattern(16,16,RGB,reset_is());

	String rate = "instant rate"+(int)frameRate + " | average rate:" + (int)avg_fps;
	surface.setTitle(rate);
	total_fps += frameRate;
	avg_fps = total_fps/frameCount;

  

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
	//other_stuff();
  
	remote_command_movie();
	reset(false);
}



float rot_x; 
float rot_y;
void other_stuff() {
	translate(width/2,height/2);
	rotateX(rot_x += .01);
	rotateY(rot_y += .02);
	box(600,100,100);
}












boolean incrust_is = true;
void incrust_is(boolean is) {
	incrust_is = is;
}

boolean incrust_is() {
	return incrust_is;
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

	if(key == 'i') {
		incrust_is =  incrust_is? false:true;
	}
}

void keyReleased() {
	key_up = false;
	key_down = false;
	key_left = false;
	key_right = false;
}

