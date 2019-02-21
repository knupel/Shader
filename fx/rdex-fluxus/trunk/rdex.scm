;--- rdex-fluxus.scm
;--- rdex is by claudius maximus - http://rdex.goto10.org
;--- this sketchy interpretation is by evan raskob (pixelpusher)
;--- http://pixelist.info
;--- licensed under the GPLv3 (available online)

(clear)

(define pix-dims 1024)

(define hscale
    15
    ;   (let [(s (get-screen-size))]
        ;       (* 20 (/ (vector-ref s 1) (vector-ref s 0)))
        ;       )
    )

(display hscale)(newline)

(define (false-colour-shader) 
    (shader "passthru.vert.glsl" "falsecolour.frag"))


(define (reactiondiffusion-shader) 
    (shader "passthru.vert.glsl" "reactiondiffusion.frag"))

; uniform sampler2D texture; // U := r, V := g, other channels ignored
; uniform float dx;          // horizontal distance between texels
; uniform float dy;          // vertical distance between texels
; uniform float ru;          // rate of diffusion of U
; uniform float rv;          // rate of diffusion of V
; uniform float f;           // some coupling parameter
; uniform float k;           // another coupling parameter


(define (rd-shader-params dx dy ru rv f k)
    (shader-set! (list "dx" dx
            "dy" dy
            "ru" ru
            "rv" rv
            "f"  f
            "k"  k)))

(define (set-default-rd-shader-params)
(let [(dxy (/ 1 pix-dims))
        (ru (+ (* (rndf) 0.3) 0.001))
        (rv (+ (* (rndf) 0.3) 0.001))
        (f (+ (* (rndf) 0.1) 0.001))
        (k (+ (* (rndf) 0.1) 0.001))]

    (printf "dxy:~s ru:~s rv:~s f:~s k:~s" dxy ru rv f k)
    (rd-shader-params dxy dxy ru rv f k)
    )
)

; logic - first 

;-- create 2 renderer buffers
(define renderers 
    (list
        (build-pixels pix-dims pix-dims #t)
        (build-pixels pix-dims pix-dims #t)
        )
    )



;-- initialize the renderer with n random shapes
(define (init-renderer r n)
    (cond [(< n 1) #f]
        [else
            (with-pixels-renderer r
                (hint-unlit)
                (colour (vector (rndf) (rndf) 0 1 ))
                (with-state
                    (rotate (vector 0 0 (* 45 (crndf))))
                    (translate (vmul (crndvec) 6))
                    (scale (+ 0.1 (* (rndf) 0.1)))
                    (draw-plane)
                    )
                )
            (init-renderer r (- n 1))
            ]
        )
    )



; initialize with some random shapes    
(map (lambda (r)
        (with-primitive r
            (clear-colour #(0 1)))
        (with-pixels-renderer r
            (with-state
                (scale (vector 20 hscale 1))
                (colour #(0 1))
                (draw-plane)))
        (init-renderer r 500)
        (with-primitive r
            (scale 0))) 
    renderers)


;-- note - texture must be set inside pixels-render
;-- but primitive id DOESN'T exist inside it



    (let* [(r1 (car renderers))
            (r2 (car (cdr renderers)))
            (t2 (pixels->texture r2))]
        
        
        (with-pixels-renderer r1
            (hint-unlit)            
            (with-state
                (colour #(1 1))
                (reactiondiffusion-shader)
                (set-default-rd-shader-params)                 
                (texture t2)
                (scale (vector 20 hscale 1))
                (build-plane)
                )
            )
        
        (let [(t1 (pixels->texture r1))]
            
            
            (with-pixels-renderer r2                
                    (hint-unlit)
                (with-state
                    (colour #( 1 1))
                    (reactiondiffusion-shader)
                    (set-default-rd-shader-params)                 
                    (texture t1)
                    (scale (vector 20 hscale 1))
                    (build-plane)
                    )
                )
        ) 
    )


(define screen
            (with-state
                (identity)
                (hint-unlit)
                (colour #(1 1))
                (false-colour-shader)
                (texture (pixels->texture (car renderers)))
                     (scale (vector 20 hscale 1))
                (build-plane)
                )        
    )

