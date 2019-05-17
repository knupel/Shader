





void setup_fx_post_on_tex() {
	select_input();
}

void draw_fx_post_on_tex() {
	set_movie(input());
	set_img(input());
  if(img_input != null) {
  	render = to_pgraphics(img_input);
  } else if(movie_input != null) {
  	render = to_pgraphics(movie_input);
  }
	// set_shape();
	filtering_render();
}




ivec2 window_ref;
PImage layer;
void filtering_render() {
	if(render != null && render.width > 0 && render.height > 0) {
		if(layer == null) {
			layer = createImage(render.width,render.height,ARGB);
			layer.loadPixels();
			int c = color(255,0,0);
			for(int i = 0 ; i < layer.pixels.length ; i++) {
				layer.pixels[i] = c;
			}
			layer.updatePixels();
		}
		boolean on_g = false;
		boolean filter_is = true;

		// pass_render(fx_template(render,on_g));
		// pass_render(fx_blur_circular(render,on_g)); 
		// pass_render(fx_grain(render,on_g,random(1),1)); 


    float threshold = noise(millis() * 0.0001, frameCount * 0.01) * 0.15;
    float strength = abs(sin(frameCount *.01))*10;
    vec2 offset_red = vec2().sin_wave(frameCount,.01,.02);
  	vec2 offset_green = vec2().cos_wave(frameCount,.001,.01);
  	vec2 offset_blue = vec2().sin_wave(frameCount,.005,.002);
		// pass_render(fx_datamosh(render,on_g,threshold,strength,offset_red,offset_green,offset_blue));
    
    //pass_render(fx_mask(img_a,img_b,on_g));
		pass_render(fx_mask(render,render,on_g,filter_is));
		
		// pass_render(fx_reaction_diffusion(render,on_g));
		// pass_render(fx_halftone_line(render,on_g,20));
		// pass_render(fx_scale(render,on_g,ivec2(mouseX,mouseY)));

		// pass_render(fx_pixel(render,on_g));

		// 
		
		// pass_render(fx_mix(render,img_a,on_g));
		//pass_render(fx_mix(render,render,on_g));
		// pass_render(fx_mix(render,img_b,on_g,FX_MULTIPLY));

		image(render);
	  
	}
}

void pass_render(PGraphics temp) {
	render.loadPixels();
	temp.loadPixels();
	render.pixels = temp.pixels;
	render.updatePixels();
}



float angle_shape;
void set_shape() {
	if(render == null) {
		render = createGraphics(g.width,g.height,get_renderer());
	} else {
		render.beginDraw();
		render.background(255,0,0,100);
		render.rectMode(CENTER);
	  angle_shape += .01;
		render.pushMatrix();
		render.translate(width/2,height/2);
		render.rotate(angle_shape);
	  render.rect(0,0,150,150);
	  render.popMatrix();
	  render.endDraw();
	}
}

