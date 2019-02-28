## Shader
This project is a processing framework to customize your background via the glsl shader or to filtering your rendering.
there is a collection of background shader and more important collection of post fx filtering.

For POST FX the shader available is gaussian blur, circular blur, radial blur, reaction diffusion, change spectrum HSB, RGB slit, dithering, grain, halftone, halftone line, scale,warp procedurale, warp texture, pixelate...
All shaders can be use on a specific PGraphics or on the main rendering, obviously it's faster to use them on main render 'g'.

## rope library
to use filter and all the tap of rope method you need to download Rope library
[download](https://github.com/StanLepunK/Rope/blob/master/build_rope/Rope.zip)

## link and source shader and glsl

[Wagner shader](https://github.com/spite/Wagner/tree/master/fragment-shaders),[David Guan](https://medium.com/david-guan/webgl-and-image-filter-101-5017b290d02f),[Raphaël de Courville](https://github.com/SableRaf/Filters4Processing)
and always [shader toy](https://github.com/SableRaf/Filters4Processing),[sand box](http://glslsandbox.com/),[ISF: interactive Shader Format](https://www.interactiveshaderformat.com/)

## setup
In `setup()` select your mode 0, 1 or 2 depend if you want work on background shader, on post FX on `g` or on your own PGgraphics

## movie command
forward `ARROW RIGHT`

rewind `ARROW LEFT`

to increment read speed, use mutti press on `ARROW RIGHT` or `ARROW LEFT`

sound up `ARROW UP`

sound down `ARROW DOWN`

movie pause `SPACE`


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
![cellular](https://github.com/StanLepunK/Shader/blob/master/img_link/IM%20185809.jpg)
![heart](https://github.com/StanLepunK/Shader/blob/master/img_link/IM%20185810.jpg)
![voronoi hex](https://github.com/StanLepunK/Shader/blob/master/img_link/IM%20185811.jpg)
