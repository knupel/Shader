/**
* FX framework by Stan le Punk 
* the framework attempt to use a collection of shader FX for background or POST FX
* by class FX to manage and set all shaders.

* You can use easily the class and method FX without the framework part, just use the tab start by Y_ and Z_ and make your own shader world.
* @see https://github.com/StanLepunK/Shader
* v 0.1.0
* 2018-2021
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
	rope_version();
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
	//println(width,height);

	String rate = "instant rate"+(int)frameRate + " | average rate:" + (int)avg_fps;
	surface.setTitle(rate);
	total_fps += frameRate;
	avg_fps = total_fps/frameCount;

  
  

  if(mode_shader == 0) {
  	background(r.PINK);
  	setting_fx_post(fx_manager,false);
		draw_fx_post_on_tex();
	} else if(mode_shader == 1) {
		setting_fx_post(fx_manager,true);
		draw_fx_post_on_g(img_a,img_b,img_noise_1,img_noise_2);
	} else if(mode_shader == 2) {
		setting_fx_bg(fx_bg_manager);
		draw_fx_bg();
	}

	//other_stuff();
	
  
	remote_command_movie();
	reset(false);
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


void keyPressed() {
	keyPressed_arrow();

	if(key == 'n') {
		reset(true);
	}

	if(key == 'i') {
		incrust_is =  incrust_is ? false:true;
	}

	if(key == ' ') {
		space_switch();
	}
}

void keyReleased() {
	keyReleased_arrow_false();
}

