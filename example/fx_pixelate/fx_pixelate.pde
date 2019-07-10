/**
* Shader FX laboratory
* v 0.0.1
* Copyleft (c) 2019-2019
* @author @stanlepunk
* @see https://github.com/StanLepunK/Shader
* 
* note:
* Processing 3.5.3
* Rope library 0.8.3.28 
*/

PImage img_a;
void setup() {
	size(640,480,P2D);
	// img_a = loadImage("damier_medium.jpg");
	img_a = loadImage("medium_puros_girl.jpg");
	surface.setSize(img_a.width,img_a.height);
}



void draw() {
	background(r.WHITE);

	image(img_a);
	
	if(mousePressed) {
	  ivec2 pix_size = ivec2(5);
	  int num_colour = 5;
	  vec3 level = abs(vec3().sin_wave(frameCount,0.01,0.02,0.03));
	  boolean effect = true ;
	  if(keyPressed) effect = false;
	  fx_pixel(g,true,true,pix_size,num_colour,level,effect);
	}
}
