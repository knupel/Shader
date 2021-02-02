/**
* fx gui
* 2021-2021
* v 0.0.1
*/

boolean incrust_is = false;
void incrust_is(boolean is) {
	incrust_is = is;
}

boolean incrust_is() {
	return incrust_is;
}

boolean reset = false;
void reset(boolean reset){
	this.reset = reset;
}

boolean reset_is() {
	return reset;
}



void keyPressed_arrow() {
	if(key == CODED) {
		if(keyCode == UP) {
			up_is(true);
		}

		if(keyCode == DOWN) {
			down_is(true);
		}

		if(keyCode == LEFT) {
			left_is(true);
			set_movie_speed(-1);
		}

		if(keyCode == RIGHT) {
			right_is(true);
			set_movie_speed(1);
		}
	}
}

void keyReleased_arrow_false() {
	up_is(false);
	down_is(false);
	left_is(false);
	right_is(false);
}


boolean up_is;
boolean down_is;
boolean left_is;
boolean right_is;
boolean space_is;

boolean up_is() {
	return up_is;
}

void up_is(boolean is) {
	up_is = is;
}

boolean down_is() {
	return down_is;
}

void down_is(boolean is) {
	down_is = is;
}

boolean left_is() {
	return left_is;
}

void left_is(boolean is) {
	left_is = is;
}

boolean right_is() {
	return right_is;
}

void right_is(boolean is) {
	right_is = is;
}

boolean space_is() {
	return space_is;
}

void space_is(boolean is) {
	space_is = is;
}

void space_switch() {
  space_is = space_is ? false : true;
}