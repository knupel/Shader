/**
* SETTING FX POST method
* v 0.2.3
* 2019-2019
*/

void setting_fx_post(ArrayList<FX> fx_list) {
  setting_blur_circular(fx_list);
  setting_blur_gaussian(fx_list);
  setting_blur_radial(fx_list);

  setting_colour_change_a(fx_list);
  setting_colour_change_b(fx_list);

  setting_dither_bayer_8(fx_list);

  setting_grain(fx_list);
  setting_grain_scatter(fx_list);

  setting_haltone_dot(fx_list);
  setting_haltone_line(fx_list);
  setting_haltone_multi(fx_list);

  setting_image_mapping(fx_list);

  setting_level(fx_list);

  setting_mix(fx_list);

  setting_pixel(fx_list);

  setting_reac_diff(fx_list);

  setting_scale(fx_list);

  setting_split(fx_list);

  setting_threshold(fx_list);

  setting_warp_proc(fx_list);
  setting_warp_tex_a(fx_list);
  setting_warp_tex_b(fx_list);
}



// blur circular
String set_blur_circular = "blur circular";
void setting_blur_circular(ArrayList<FX> fx_list) {
  init_fx(fx_list,set_blur_circular,FX_BLUR_CIRCULAR);
  fx_set_num(fx_list,set_blur_circular,36);
  float strength = map(mouseX,0,width,0,100);
  fx_set_strength(fx_list,set_blur_circular,strength);
}


// blur gaussian
String set_blur_gaussian = "blur gaussian";
void setting_blur_gaussian(ArrayList<FX> fx_list) {
  init_fx(fx_list,set_blur_gaussian,FX_BLUR_GAUSSIAN);
  float x = mouseX -(width/2);
  int max_blur = 40;
  float size = map(abs(x),0,width/2,0,max_blur);
  fx_set_strength(fx_list,set_blur_gaussian,size);
}


// blur radial
String set_blur_radial = "blur radial";
float current_scale_blur_radial;
void setting_blur_radial(ArrayList<FX> fx_list) {
  init_fx(fx_list,set_blur_radial,FX_BLUR_RADIAL);
  
  vec2 pos = vec2();
  if(get_fx(fx_list,set_blur_radial).get_pos() != null) pos = vec2(get_fx(fx_list,set_blur_radial).get_pos()).copy();
  if(mousePressed) {
    pos.x(map(mouseX,0,width,0,1));
    pos.y(map(mouseY,0,height,0,1));
  }
  fx_set_pos(fx_list,set_blur_radial,pos.x,pos.y);

  // scale
  float var = get_wheel_scale();
  int range_blur = 10;
  current_scale_blur_radial += var *.01;
  if(current_scale_blur_radial < -range_blur) {
    current_scale_blur_radial = -range_blur;
  } else if(current_scale_blur_radial > range_blur) {
    current_scale_blur_radial = range_blur;
  }
  fx_set_scale(fx_list,set_blur_radial,current_scale_blur_radial);

  float strength = map(sin(frameCount *.01),-1,1,5,100);
  fx_set_strength(fx_list,set_blur_radial,strength);
}









// colour change
String set_colour_change_a = "colour change A";
void setting_colour_change_a(ArrayList<FX> fx_list) {
  init_fx(fx_list,set_colour_change_a,FX_COLOUR_CHANGE_A);
  
  if(mousePressed) {
    vec3 m0 = vec3().wave_sin(frameCount,.001,.02,.005).mult(10);
    vec3 m1 = vec3().wave_cos(frameCount,.001,.02,.005).mult(10);
    vec3 m2 = vec3().wave_sin(frameCount,.01,.002,.002).mult(10);
    //  vec3 m0 = vec3(-1,0,1);
    // vec3 m1 = vec3(1,0,-1);
    // vec3 m2 = vec3(-1,0,1); 
    fx_set_matrix(fx_list,set_colour_change_a,0,m0.array());
    fx_set_matrix(fx_list,set_colour_change_a,1,m1.array());
    fx_set_matrix(fx_list,set_colour_change_a,2,m2.array());
    
    int num = (int)map(mouseX,0,width,1,32);
    fx_set_num(fx_list,set_colour_change_a,num);
  }
}


// line
String set_colour_change_b = "colour change B";
void setting_colour_change_b(ArrayList<FX> fx_list) {
  init_fx(fx_list,set_colour_change_b,FX_COLOUR_CHANGE_B);
  if(mousePressed) {
    float angle = map(mouseX,0,width,-PI,PI);
    fx_set_angle(fx_list,set_colour_change_b,angle);
    float strength = map(mouseY,0,height,1,10);
    fx_set_strength(fx_list,set_colour_change_b,strength);
  }
}








// dither bayer 8
String set_dither_bayer_8 = "dither bayer 8";
void setting_dither_bayer_8(ArrayList<FX> fx_list) {
  init_fx(fx_list,set_dither_bayer_8,FX_DITHER_BAYER_8);

  if(keyPressed) {
    fx_set_mode(fx_list,set_dither_bayer_8,0); // gray dither
  } else {
    fx_set_mode(fx_list,set_dither_bayer_8,1); // rgb dither
  }

  if(mousePressed) {
    float level_x = abs(sin(frameCount*.002)); // for gray and rgb model
    float level_y = abs(sin(frameCount*.001)); // for the rgb model
    float level_z = abs(sin(frameCount*.005)); // for the rgb model
    fx_set_level_source(fx_list,set_dither_bayer_8,level_x,level_y,level_z);
  }
}











// grain
String set_grain = "grain";
void setting_grain(ArrayList<FX> fx_list) {
  float grain = random(1);
  fx_set_offset(fx_list,set_grain,grain);
  int mode = 1;
  fx_set_mode(fx_list,set_grain,mode);
  init_fx(fx_list,set_grain,FX_GRAIN);
 
}






// grain scatter
String set_grain_scatter = "grain scatter";
void setting_grain_scatter(ArrayList<FX> fx_list) {
  init_fx(fx_list,set_grain_scatter,FX_GRAIN_SCATTER);
  if(mousePressed) {
    float strength = map(mouseX,0,width,-100,100);
    fx_set_strength(fx_list,set_grain_scatter,strength);
  }
}






// halftone dot
String set_halftone_dot = "haltone dot";
void setting_haltone_dot(ArrayList<FX> fx_list) {
  init_fx(fx_list,set_halftone_dot,FX_HALFTONE_DOT);

  if(mousePressed) {
    fx_set_threshold(fx_list,set_halftone_dot,sin(frameCount *.01));
    fx_set_pos(fx_list,set_halftone_dot,mouseX,mouseY);
    fx_set_size(fx_list,set_halftone_dot,(abs(sin(frameCount *.01))) *30 +1);
    fx_set_angle(fx_list,set_halftone_dot,sin(frameCount *.001) *TAU);
  }
}






// halftone line
String set_halftone_line = "halftone line";
void setting_haltone_line(ArrayList<FX> fx_list) {
  init_fx(fx_list,set_halftone_line,FX_HALFTONE_LINE);

  if(mousePressed) {
    fx_set_mode(fx_list,set_halftone_line,0); 
    int num_line = (int)map(mouseY,0,height,20,100); 
    fx_set_num(fx_list,set_halftone_line,num_line);  
    fx_set_quality(fx_list,set_halftone_line,.2);

    float threshold = map(mouseY,0,height,0,1);
    fx_set_threshold(fx_list,set_halftone_line,threshold);
    // float angle = sin(frameCount *.001) * PI;

    float angle = map(mouseX,0,width,-TAU,TAU);
    fx_set_angle(fx_list,set_halftone_line,angle);

    float px = map(width/2,0,width,0,1); // normal position
    float py = map(height/2,0,height,0,1); // normal position
    fx_set_pos(fx_list,set_halftone_line,px,py);
  }
}





// halftone multi
String set_halftone_multi = "halftone multi";
void setting_haltone_multi(ArrayList<FX> fx_list) {
  init_fx(fx_list,set_halftone_multi,FX_HALFTONE_MULTI);

  if(mousePressed) {
    float px = map(mouseX,0,width,0,1);
    float py = map(mouseY,0,height,0,1);
    fx_set_pos(fx_list,set_halftone_multi,px,py); // 0 to 1
    float quality = 2;
    // float quality = map(mouseX,0,width,0,32);
    fx_set_quality(fx_list,set_halftone_multi,quality); // 1 to 16++

    // float size =.9;
    float size = 1 + abs(sin(frameCount *.01))*1;
    fx_set_size(fx_list,set_halftone_multi,size); // 0 to 2++

    // float angle = 0;
    float angle = sin(frameCount *.001) *TAU;
    fx_set_angle(fx_list,set_halftone_multi,angle); // in radians
    
    float threshold = .2;
    // float threshold = map(mouseY,0,height,0,2);
    fx_set_threshold(fx_list,set_halftone_multi,threshold); // from 0 to 2++
    
    float saturation = abs(sin(frameCount *.01));
    // float saturation = .5;
    fx_set_saturation(fx_list,set_halftone_multi,saturation); // from 0 to 1
    // fx_set_pos(fx_list,set_halftone_multi,mouseX,mouseY);
    fx_set_mode(fx_list,set_halftone_multi,0); // from 0 to 2
  }
}








// halftone dot
String set_image_mapping = "image mapping";
void setting_image_mapping(ArrayList<FX> fx_list) {
  init_fx(fx_list,set_image_mapping,FX_IMAGE);

  if(mousePressed) {
    // fx_set_size(fx_list,set_image_mapping,(abs(sin(frameCount *.01))) *30 +1);
    vec4 rgba = abs(vec4().wave_sin(frameCount,.01,.02,.03,.01));
    //fx_set_level_source(fx_list,set_image_mapping,rgba);
  }
}







// level
String set_level = "level";
void setting_level(ArrayList<FX> fx_list) {
  init_fx(fx_list,set_level,FX_LEVEL);
  fx_set_mode(fx_list,set_level,0);
  if(mousePressed) {
    vec3 level = abs(vec3().wave_sin(frameCount,.01,.02,.04));
    fx_set_level_source(fx_list,set_level,level.array());
  }
}


// mix
/*
* 1 multiply
* 2 screen
* 3 exclusion
* 4 overlay
* 5 hard_light
* 6 soft_light
* 7 color_dodge
* 8 color_burn
* 9 linear_dodge
* 10 linear_burn
* 11 vivid_light
* 12 linear_light
* 13 pin_light
* 14 hard_mix
* 15 subtract
* 16 divide
* 17 addition
* 18 difference
* 19 darken
* 20 lighten
* 21 invert
* 22 invert_rgb
* 23 main
* 24 layer
*/
String set_mix = "mix";
void setting_mix(ArrayList<FX> fx_list) {
  init_fx(fx_list,set_mix,FX_MIX);
  // fx_set_mode(set_mix,1); // produit - multiply
  fx_set_mode(fx_list,set_mix,8); 
  // if(mousePressed) {
    vec3 level_source = abs(vec3().wave_sin(frameCount,.001,.003,.005));
    vec3 level_layer = abs(vec3().wave_cos(frameCount,.002,.001,.001));
    fx_set_level_source(fx_list,set_mix,level_source.array());
    fx_set_level_layer(fx_list,set_mix,level_layer.array());
 // }
}


// pixel
String set_pixel = "pixel";
boolean effect_pixel_is;
void setting_pixel(ArrayList<FX> fx_list) {
  init_fx(fx_list,set_pixel,FX_PIXEL);

  if(reset_is()) {
    effect_pixel_is = !!((effect_pixel_is == false));
  }
  fx_set_event(fx_list,set_pixel,0,effect_pixel_is);

  if(mousePressed) {
    if(frameCount%10 == 0) {
      vec2 pix_size = vec2().rand(vec2(1,width/10),vec2(1,height/10));
      fx_set_size(fx_list,set_pixel,pix_size.array());
      int num = ceil(random(2,16));
      fx_set_num(fx_list,set_pixel,num);
    }
    
    float h = abs(sin(frameCount *.01)); // from 0 to 1 where
    float s = map(mouseY,0,height,0,1); // from 0 to 1 where
    float b = map(mouseX,0,width,0,1); // from 0 to 1 where
    if(s < 0) s = 0; else if (s > 1) s = 1;
    if(b < 0) b = 0; else if (b > 1) s = 1;
    fx_set_level_source(fx_list,set_pixel,h,s,b);
  }
}








String set_reac_diff = "reaction diffusion";
void setting_reac_diff(ArrayList<FX> fx_list) {
  init_fx(fx_list,set_reac_diff,FX_REAC_DIFF);
    float ru = 0.25f;
  float rv = 0.04f;
  fx_set_pair(fx_list,set_reac_diff,0,ru,rv);
  
  float k = 0.047f;
  float f = 0.1f;
  fx_set_pair(fx_list,set_reac_diff,1,k,f);
  
  // color part not use now
  float r = 0;
  float g = 0;
  float b = 0;
  fx_set_colour(fx_list,set_reac_diff,r,g,b);

  boolean event = mousePressed;
  fx_set_event(fx_list,set_reac_diff,0,event);

   vec2 scale = vec2(.6);
   fx_set_scale(fx_list,set_reac_diff,scale.array());

  int rd_iteration = 20;
  fx_set_num(fx_list,set_reac_diff,rd_iteration);
  
}





// scale
String set_scale = "scale";
void setting_scale(ArrayList<FX> fx_list) {
  init_fx(fx_list,set_scale,FX_SCALE);
  if(mousePressed) {
    fx_set_resolution(fx_list,set_scale,mouseX,mouseY);
  }
}





String set_split = "split rgb";
void setting_split(ArrayList<FX> fx_list) {
  init_fx(fx_list,set_split,FX_SPLIT_RGB);
  if(mousePressed) {
    /*
    float ox_red = map(mouseX,0,width,0,1);
    float oy_red = map(mouseY,0,height,0,1);
    */
    vec2 offset_red = vec2().wave_cos(frameCount,.01,.02);
    vec2 offset_green = vec2().wave_sin(frameCount,.01,.02);
    vec2 offset_blue = vec2().wave_cos(frameCount,.004,.01);
    fx_set_pair(fx_list,set_split,0,offset_red.array());
    fx_set_pair(fx_list,set_split,1,offset_green.array());
    fx_set_pair(fx_list,set_split,2,offset_blue.array());
  }
}



// dither
String set_threshold = "threshold";
void setting_threshold(ArrayList<FX> fx_list) {
  init_fx(fx_list,set_threshold,FX_THRESHOLD);

  if(keyPressed) {
    fx_set_mode(fx_list,set_threshold,0); // gray threshold
  } else {
    fx_set_mode(fx_list,set_threshold,1); // rgb threshold
  }

  if(mousePressed) {
    float level_x = map(mouseX,0,width,0,1); // for gray and rgb model
    float level_y = abs(sin(frameCount*.01)); // for the rgb model
    float level_z = abs(sin(frameCount*.004)); // for the rgb model
    fx_set_level_source(fx_list,set_threshold,level_x,level_y,level_z);
  }
}



// WARP setting
String set_warp_proc = "warp procedural";
void setting_warp_proc(ArrayList<FX> fx_list) {
  init_fx(fx_list,set_warp_proc,FX_WARP_PROC);
  if(mousePressed) {
    fx_set_strength(fx_list,set_warp_proc,map(mouseX,0,width,5,20));
  }
}


String set_warp_tex_a = "warp textural A";
void setting_warp_tex_a(ArrayList<FX> fx_list) {
  init_fx(fx_list,set_warp_tex_a,FX_WARP_TEX_A);
  fx_set_mode(fx_list,set_warp_tex_a,0);
  if(mousePressed) {
    fx_set_strength(fx_list,set_warp_tex_a,map(mouseX,0,width,-1,1));
  }
}





String set_warp_tex_b = "warp textural B";
void setting_warp_tex_b(ArrayList<FX> fx_list) {
  init_fx(fx_list,set_warp_tex_b,FX_WARP_TEX_B);
  
  if(mousePressed) {
    float strength = map(mouseX,0,width,-.2,.2);
    fx_set_strength(fx_list,set_warp_tex_b,strength);
  }
}



