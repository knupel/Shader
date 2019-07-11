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
	multi_pix();
	
	// image(img_a);
	
	if(mousePressed) {
		float sub_pix_cap = 0.75;
		float sub_pix_trim = -0.2;
	  fx_fxaa(g,true,true,sub_pix_cap,sub_pix_trim);
	}


	in_progress();
}


void in_progress() {
	// fx_glitch_fxaa(g,true,true,vec4().sin_wave(frameCount,0.01,0.04,0.03,0.02));
	// fx_derivative(g,true,true);
}


void img(PImage img, float rot) {
	push();
	translate(width/2,height/2);
	rotate(rot);
	image(img);
	pop();
}



float rot;
void multi_pix() {
//rot += 0.001;
int num = 77;
float step = TAU / num ;
for(int i = 0 ; i < num ; i++) {
	pix(rot + (i*step));
	 }
}
void pix(float rot) {
	ivec2 src = ivec2(width/2,height/2);
	float radius = width/3;
	float dx = sin(rot);
	float dy = cos(rot);
	g.loadPixels();
	for(int i = 0; i < radius ; i++) {
		vec2 dst = vec2(dx,dy).mult(i).add(src);
		dst.constrain(vec2(0),vec2(width,height));
		int target = 0;
		target = (int)dst.y() * width + (int)dst.x();

		if(target >= g.pixels.length) {
			target = 0;
		}
		g.pixels[target] = r.BLOOD;
	}
	g.updatePixels();
}