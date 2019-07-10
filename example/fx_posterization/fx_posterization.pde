/**
* Shader FX posterization
* v 0.0.1
* Copyleft (c) 2019-2019
* @author @stanlepunk
* @see https://github.com/StanLepunK/Shader
*/

PImage img_a;
void setup() {
	size(640,480,P2D);
	// img_a = loadImage("damier_medium.jpg");
	img_a = loadImage("medium_puros_girl.jpg");
	surface.setSize(img_a.width,img_a.height);
}


void draw() {
	image(img_a);
  vec3 threshold = vec3().sin_wave(frameCount,0.001,0.005,0.002);
  int num = 10;
	fx_posterization(g,true,true,threshold,num);
}
