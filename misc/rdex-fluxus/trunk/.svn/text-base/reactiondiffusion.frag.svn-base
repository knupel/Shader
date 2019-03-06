/* =====================================================================
rdex -- reaction-diffusion explorer
Copyright (C) 2008,2009  Claude Heiland-Allen <claudiusmaximus@goto10.org>
===================================================================== */

uniform sampler2D texture; // U := r, V := g, other channels ignored
uniform float dx;          // horizontal distance between texels
uniform float dy;          // vertical distance between texels
uniform float ru;          // rate of diffusion of U
uniform float rv;          // rate of diffusion of V
uniform float f;           // some coupling parameter
uniform float k;           // another coupling parameter

void main(void) {
  float center = -(4.0+4.0/sqrt(2.0));  // -1 * other weights
  float diag   = 1.0/sqrt(2.0);       // weight for diagonals
  vec2 p = gl_TexCoord[0].st;             // center coordinates
  vec2 c = texture2D(texture, p).rg;      // center value
  vec2 l                                  // compute Laplacian
    =(texture2D(texture, p + vec2(-dx,-dy)).rg
    + texture2D(texture, p + vec2( dx,-dy)).rg
    + texture2D(texture, p + vec2(-dx, dy)).rg
    + texture2D(texture, p + vec2( dx, dy)).rg) * diag
    + texture2D(texture, p + vec2(-dx, 0.0)).rg
    + texture2D(texture, p + vec2( dx, 0.0)).rg
    + texture2D(texture, p + vec2(0.0,-dy)).rg
    + texture2D(texture, p + vec2(0.0, dy)).rg
    + c.rg * center;
  float u = c.r;                          // compute some temporary
  float v = c.g;                          // values which might save
  float lu = l.r;                         // a few GPU cycles
  float lv = l.g;
  float uvv = u * v * v;
  //============================================================
  float du = ru * lu - uvv + f * (1.0 - u); // Gray-Scott equation
  float dv = rv * lv + uvv - (f + k) * v; // diffusion+-reaction
  //============================================================
  u += 0.6*du; // semi-arbitrary scaling (reduces blow-ups?)
  v += 0.6*dv;
  gl_FragColor = vec4(clamp(u, 0.0, 1.0), clamp(v, 0.0, 1.0), 0.0, 1.0);        // output new (U,V)
}
