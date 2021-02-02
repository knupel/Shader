/**
* FX framework by Stan le Punk 
* the framework attempt to use a collection of shader FX for background or POST FX
* by class FX to manage and set all shaders.

* You can use easily the class and method FX without the framework part, just use the tab start by Y_ and Z_ and make your own shader world.
* @see https://github.com/StanLepunK/Shader
*
* WARNING
* if you change the shader/fx folder, utilies in case you export your sketch, the shader folder is not exported.
* in void setup() 
* set_fx_path(sketchPath(2)+"/shader/fx/");

*/
String app_name = "SHADER FX";
String version ="2018-2021 | v 0.1.0";
PGraphics render;
ArrayList<FX> fx_manager = new ArrayList<FX>();
ArrayList<FX> fx_bg_manager = new ArrayList<FX>();


// int MODE_SHADER = 0; // POST FX on PGrahics > texture
int MODE_SHADER = 1; // POST FX on "g". "g" is the main rendering of Processing
// int MODE_SHADER = 2; // create shader pure fx on the background, is not a post fx on specific media.


void setup() {
	rope_version();
	size(1200,700,P3D);
	img_a = loadImage("medium_leticia.jpg");
	img_b = loadImage("medium_puros_girl.jpg");
	set_pattern(16,16,RGB,true);
	// select_mode_shader(MODE_SHADER);
}

float total_fps;
float avg_fps;

void draw() {	
	set_pattern(16,16,RGB,reset_is());
	String rate = "instant rate"+(int)frameRate + " | average rate:" + (int)avg_fps;
	surface.setTitle(rate);
	total_fps += frameRate;
	avg_fps = total_fps/frameCount;

	render_mode_shader(MODE_SHADER);
	//other_stuff();
	remote_command_movie();
	reset(false);
	instruction(instruction_is());
	add_media();

	// println("display_movie_is()",display_movie_is());
	// println("display_img_is()",display_img_is());
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

	if(key == 'i') {
		instruction_switch();
	}

	if(key == 'o') {
		folder_is(true);
		select_folder();
		instruction_is(false);
		display_movie_is(false);
		display_img_is(true);
	}

	if(key == 'm') {
		input_is(true);
		select_input();
		instruction_is(false);
		display_movie_is(true);
		display_img_is(false);
		media_is(false);
	}

	if(key == DELETE || key == BACKSPACE) {
		lib_img.clear();
		println("clear librarie image:", lib_img.size());
	}
}

void keyReleased() {
	keyReleased_arrow_false();
}

