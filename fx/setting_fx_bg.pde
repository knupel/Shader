/**
* SETTING FX BACKGROUND
* v 0.0.1
* 2019-2019
*/

void setting_fx_bg(ArrayList<FX> fx_list) {
  setting_template_fx_bg(fx_list);
  setting_cellular_fx_bg(fx_list);
  setting_heart_fx_bg(fx_list);
  setting_necklace_fx_bg(fx_list);
  setting_neon_fx_bg(fx_list);
  setting_psy_fx_bg(fx_list);
  setting_snow_fx_bg(fx_list);
  setting_voronoi_hex_fx_bg(fx_list);
}



// template
String set_template_fx_bg = "template fx background";
void setting_template_fx_bg(ArrayList<FX> fx_list) {
  init_fx(fx_list,set_template_fx_bg,FX_BG_TEMPLATE);
  vec3 colour = abs(vec3().wave_sin(frameCount,.1,.02,.03));
  fx_set_colour(fx_list,set_template_fx_bg,colour.array());
}



// cellular
String set_cellular_fx_bg = "cellular fx background";
void setting_cellular_fx_bg(ArrayList<FX> fx_list) {
  init_fx(fx_list,set_cellular_fx_bg,FX_BG_CELLULAR);
  vec4 colour = abs(vec4().wave_sin(frameCount,.1,.02,.03,.04));
  fx_set_colour(fx_list,set_cellular_fx_bg,colour.array());

  float sx = map(mouseX,0,width,0,1);
  float sy = map(mouseY,0,height,0,1);
  fx_set_speed(fx_list,set_cellular_fx_bg,sx,sy);

  int num = int(map(sin(frameCount*.01),-1,1,1,20));
  fx_set_num(fx_list,set_cellular_fx_bg,num);
  fx_set_quality(fx_list,set_cellular_fx_bg,.5);
}




// heart
String set_heart_fx_bg = "heart fx background";
void setting_heart_fx_bg(ArrayList<FX> fx_list) {
  init_fx(fx_list,set_heart_fx_bg,FX_BG_HEART);
  vec4 colour = abs(vec4().wave_sin(frameCount,.1,.02,.03,.04));
  fx_set_colour(fx_list,set_heart_fx_bg,colour.array());

  float speed = map(mouseX,0,width,0,1);
  fx_set_speed(fx_list,set_heart_fx_bg,speed);

  int num = int(map(sin(frameCount*.01),-1,1,1,20));
  fx_set_num(fx_list,set_heart_fx_bg,num);

  fx_set_quality(fx_list,set_heart_fx_bg,.5); // 0 to 1

  fx_set_strength(fx_list,set_heart_fx_bg,10);
}


// necklace
String set_necklace_fx_bg = "necklace fx background";
void setting_necklace_fx_bg(ArrayList<FX> fx_list) {
  init_fx(fx_list,set_necklace_fx_bg,FX_BG_NECKLACE);



  if(mousePressed) {
    float sx = map(mouseX,0,width,0,1);
    float sy = map(mouseY,0,height,0,1);
    fx_set_size(fx_list,set_necklace_fx_bg,sx,sy);


    float px = map(mouseX,0,width,0,1);
    float py = map(mouseY,0,height,0,1);
    // fx_set_pos(fx_list,set_necklace_fx_bg,px,py);
    fx_set_pos(fx_list,set_necklace_fx_bg,.5,.5);
  } 

  //float alpha = abs(sin(frameCount*.01));
  float alpha = 1;
  fx_set_colour(fx_list,set_necklace_fx_bg,alpha);

  //float speed = map(mouseX,0,width,0,1);
  float speed = .01;
  fx_set_speed(fx_list,set_necklace_fx_bg,speed);

  int num = int(map(sin(frameCount*.001),-1,1,10,80));
  // fx_set_num(fx_list,set_necklace_fx_bg,num);
  fx_set_num(fx_list,set_necklace_fx_bg,50);
}



// neon
String set_neon_fx_bg = "neon fx background";
void setting_neon_fx_bg(ArrayList<FX> fx_list) {
  init_fx(fx_list,set_neon_fx_bg,FX_BG_NEON);

  if(mousePressed) {
    float px = map(mouseX,0,width,0,1);
    float py = map(mouseY,0,height,0,1);
    fx_set_pos(fx_list,set_neon_fx_bg,px,py);
  } 
  
  float speed = abs(sin(frameCount *.01));
  speed = map(speed,0,1,.001,.05);
  fx_set_speed(fx_list,set_neon_fx_bg,speed);
}





// psy
String set_psy_fx_bg = "psy fx background";
void setting_psy_fx_bg(ArrayList<FX> fx_list) {
  init_fx(fx_list,set_psy_fx_bg,FX_BG_PSY);

  if(mousePressed) {
    int num = int(map(mouseX,0,width,2,3));
    fx_set_num(fx_list,set_psy_fx_bg,num);
  } 
  
  float speed = abs(sin(frameCount *.01));
  speed = map(speed,0,1,.0001,.005);
  fx_set_speed(fx_list,set_psy_fx_bg,speed);
}









// snow
String set_snow_fx_bg = "snow fx background";
void setting_snow_fx_bg(ArrayList<FX> fx_list) {
  init_fx(fx_list,set_snow_fx_bg,FX_BG_SNOW);

  if(mousePressed) {
    vec3 colour = abs(vec3().wave_sin(frameCount,.01,.02,.03));
    fx_set_colour(fx_list,set_snow_fx_bg,colour.array());

    float px = map(mouseX,0,width,0,1);
    float py = map(mouseY,0,height,0,1);
    fx_set_pos(fx_list,set_snow_fx_bg,px,py);
  } 
  
  float speed = map(mouseX,0,width,0,1);
  fx_set_speed(fx_list,set_snow_fx_bg,speed);
  

  float quality = abs(sin(frameCount *.01));
  fx_set_quality(fx_list,set_snow_fx_bg,quality);
}










// voronoi three p
String set_voronoi_hex_fx_bg = "voronoi hex fx background";
void setting_voronoi_hex_fx_bg(ArrayList<FX> fx_list) {
  init_fx(fx_list,set_voronoi_hex_fx_bg,FX_BG_VORONOI_HEX);

  if(mousePressed) {


    float size = map(mouseX,0,width,0,10);
    fx_set_size(fx_list,set_voronoi_hex_fx_bg,size);

    float threshold = map(sin(frameCount *.001),-1,1,.01,0.3);
    fx_set_threshold(fx_list,set_voronoi_hex_fx_bg,threshold);
  }

  vec3 colour = abs(vec3().wave_sin(frameCount,.01,.02,.03));
  fx_set_colour(fx_list,set_voronoi_hex_fx_bg,colour.array());

  float speed_mutation = .05;
  float speed_colour = 1.;
  // float speed = map(mouseX,0,width,0,1);
  fx_set_speed(fx_list,set_voronoi_hex_fx_bg,speed_mutation,speed_colour);

  float strength = map(sin(frameCount *.001),-1,1,-0.05,0.05);
  fx_set_strength(fx_list,set_voronoi_hex_fx_bg,strength);

  fx_set_mode(fx_list,set_voronoi_hex_fx_bg,0); // two mode available for the moment 0 or 1
}




