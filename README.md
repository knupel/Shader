### Shader
IS my shader collection refactoring for Processing.
You find for the shader project, three folder : 'example', 'fx_lab' and 'fx_framework'.
all the last research is in 'fx_lab'. In 'fx_framework' is the work around a class FX to manage the shader see below for more information...


```
shader post fx:
antialiasing fxaa, blur circular, blue gaussian, blur radial, color change, datamosh, derivative, dither bayer, flip, grain, grain scatter, haltone dot, halftone line, halftone multi, image, level, mask, mix > like photoshop, pixelate, posterization, reaction diffusion, split rgb, threshold, warp procedural, warp textural
```

```
shader background:
cellular, heart, voronoi, snow... and few other
```


### FX FRAMEWORK
fx_framework can be used to customize background via the glsl shader or to filtering your rendering.
there is a collection of background shader and more important collection of post fx filtering.
Plus a method to catch the background before the post rendering to incrust it after the filter.

For POST FX the shader available is gaussian blur, circular blur, radial blur, reaction diffusion, change spectrum HSB, RGB slit, dithering, datamosh, grain, halftone, halftone line, scale,warp procedural, warp texture, pixelate...
All shaders can be use on a specific PGraphics or on the main rendering, obviously it's faster to use them on main render `g`.

## WARNING
Be careful it's my lab, that can be a mess... so you need to check your brain to come in mine, but i'm pretty you can find a lot stuffes helpful in this BAZAR!


### Select target PGraphics
on the first tab select which mode
```
// int mode_shader = 0; // POST FX on PGrahics > texture
int mode_shader = 1; // POST FX on G
// int mode_shader = 2; // FX BACKGROUND
```
After you need to activate which filter you want, if you work on `g` go on tab `fx_post_on_g.pde` if you want work on an other PGraphics go on tab `fx_post_on_tex.pde`

for now if you work on `g` you can inscrust `g` on your filtering result to do that, make `boolean incrust_is = true;`laboratory
```
boolean incrust_is = true;
void filter_g(PImage input, PImage img_1, PImage img_2, PImage pattern_1, PImage pattern_2) {

	boolean with_g = true;
  if(with_g) {
  	// image(movie_input,CENTER);
  	background(input,CENTER);
  	if(incrust_is) fx_inc_copy(g);
  	render_post_fx(g,input,img_1,img_2,pattern_1,pattern_2);
  } else {
  	render_post_fx(input,null,img_1,img_2,pattern_1,pattern_2);
	}
	if(incrust_is) fx_inc(g);
}
```

### post fx
tab `setting_fx_post.pde`

### background fx 
on the tab `setting_fx_bg.pde`

## rope library
to use filter and all the tap of rope method you need to download Rope library
[download](https://github.com/StanLepunK/Rope/blob/master/build_rope/Rope.zip)

## link and source shader and glsl

[Wagner shader](https://github.com/spite/Wagner/tree/master/fragment-shaders),[David Guan](https://medium.com/david-guan/webgl-and-image-filter-101-5017b290d02f),[Raphaël de Courville](https://github.com/SableRaf/Filters4Processing)
and always [shader toy](https://github.com/SableRaf/Filters4Processing),[sand box](http://glslsandbox.com/),[ISF: interactive Shader Format](https://www.interactiveshaderformat.com/), [Alexandre Rivaux](https://github.com/alexr4/datamoshing-GLSL).

## setup
In `setup()` select your mode 0, 1 or 2 depend if you want work on `background shader` or on `post fx` on `g` (g is the main `PGraphics` of Processing) or on your own `PGgraphics`

## movie command
forward `ARROW RIGHT`

rewind `ARROW LEFT`

to increment read speed, use mutti press on `ARROW RIGHT` or `ARROW LEFT`

sound up `ARROW UP`

sound down `ARROW DOWN`

movie pause `SPACE`

## command
enable incrustation : `i` only with `fx_pos_on_g`


## result
![blur radial](https://github.com/StanLepunK/Shader/blob/master/img_link/IM%20184796.jpg)
![blur gaussian](https://github.com/StanLepunK/Shader/blob/master/img_link/IM%20184797.jpg)
![blur circular](https://github.com/StanLepunK/Shader/blob/master/img_link/IM%20184798.jpg)
![grain scatter](https://github.com/StanLepunK/Shader/blob/master/img_link/IM%20184799.jpg)
![pixelate](https://github.com/StanLepunK/Shader/blob/master/img_link/IM%20184800.jpg)
![warp procedural split rgb](https://github.com/StanLepunK/Shader/blob/master/img_link/IM%20184801.jpg)
![split rgb](https://github.com/StanLepunK/Shader/blob/master/img_link/IM%20184802.jpg)
![warp texture split rgb](https://github.com/StanLepunK/Shader/blob/master/img_link/IM%20184803.jpg)
![warp procedural](https://github.com/StanLepunK/Shader/blob/master/img_link/IM%20184804.jpg)
![halftone dot](https://github.com/StanLepunK/Shader/blob/master/img_link/IM%20184805.jpg)
![halftone multi](https://github.com/StanLepunK/Shader/blob/master/img_link/IM%20184809.jpg)
![change colour B](https://github.com/StanLepunK/Shader/blob/master/img_link/IM%20184806.jpg)
![change colour A](https://github.com/StanLepunK/Shader/blob/master/img_link/IM%20184807.jpg)
![reaction diffusion](https://github.com/StanLepunK/Shader/blob/master/img_link/IM%20184808.jpg)
![dither bayer 8](https://github.com/StanLepunK/Shader/blob/master/img_link/IM%20184810.jpg)
![threshold](https://github.com/StanLepunK/Shader/blob/master/img_link/IM%20184811.jpg)
![datamosh](https://github.com/StanLepunK/Shader/blob/master/img_link/IM%20184812.jpg)
![post incrustation](https://github.com/StanLepunK/Shader/blob/master/img_link/IM%20184813.jpg)
![mask](https://github.com/StanLepunK/Shader/blob/master/img_link/IM%20184814.jpg)
![posterization](https://github.com/StanLepunK/Shader/blob/master/img_link/IM%20184815.jpg)
![cellular](https://github.com/StanLepunK/Shader/blob/master/img_link/BG%20300809.jpg)
![heart](https://github.com/StanLepunK/Shader/blob/master/img_link/BG%20300810.jpg)
![voronoi hex](https://github.com/StanLepunK/Shader/blob/master/img_link/BG%20300811.jpg)
