/* =====================================================================
rdex -- reaction-diffusion explorer
Copyright (C) 2008,2009  Claude Heiland-Allen <claudiusmaximus@goto10.org>
===================================================================== */

uniform sampler2D texture; // U := r, V := g, other channels ignored

void main(void) {
  mat3 lcc2rgb = mat3(                    // LC1C2 => RGB
     1.0,  1.407,  0.0,                         // colour space
     1.0, -0.677, -0.236,                       // conversion matrix
     1.0,  0.0,    1.848
  );
  vec2 p = gl_TexCoord[0].st;                   // texel coordinates
  vec2 q = texture2D(texture, p).rg;            // texel value
  float u = 1.0 - q.r;                            // reflect in U = 0.5
  float v = q.g;
  float h = 16.0*atan(v, u);                      // hue   <= angle
  float l = clamp(cos(16.0*(v*v + u*u)), 0.0, 1.0);   // light <= distance
  vec3 lcc = vec3(l, 0.5*cos(h), 0.5*sin(h));   // LC1C2 colour
  gl_FragColor = vec4(lcc * lcc2rgb, 1.0);        // RGB colour
}
