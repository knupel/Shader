/**
* fx gui
* 2021-2021
* v 0.0.1
*/
void instruction(boolean show_is) {
	if(show_is) {
		vec2 pos = vec2(width/3, height/4);
		int step = 25;
		background(r.BLOOD);
		fill(r.BLACK);
		textAlign(LEFT);
		textSize(36);
		text(app_name + " " +version, pos);
		textSize(18);
		text("shortcuts", pos.add_y(step));
		text("o > import medias from folder", pos.add_y(step));
		text("n > reset", pos.add_y(step));
		text("m > load movie", pos.add_y(step));
		text("i > display instructions", pos.add_y(step));
		text("DELETE / BACKSPACE > vide la librairie des images", pos.add_y(step));
	}
}



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

// arrow
boolean up_is;
boolean down_is;
boolean left_is;
boolean right_is;

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

// space
boolean space_is;
boolean space_is() {
	return space_is;
}

void space_is(boolean is) {
	space_is = is;
}

void space_switch() {
  space_is = space_is ? false : true;
}

// media
boolean media_is;
boolean media_is() {
	return media_is;
}

void media_is(boolean is) {
	media_is = is;
}


boolean instruction_is;
boolean instruction_is() {
	return instruction_is;
}

void instruction_is(boolean is) {
	instruction_is = is;
}

void instruction_switch() {
  instruction_is = instruction_is ? false : true;
}


// folder_is
boolean folder_is = false;
boolean folder_is() {
  return folder_is;
}

void folder_is(boolean is) {
  folder_is = is;
}

// input_is
boolean input_is = false;
boolean input_is() {
  return input_is;
}

void input_is(boolean is) {
  input_is = is;
}

// display_movie_is
boolean display_movie_is = false;
boolean display_movie_is() {
  return display_movie_is;
}

void display_movie_is(boolean is) {
  display_movie_is = is;
}

// display_img_is
boolean display_img_is = false;
boolean display_img_is() {
  return display_img_is;
}

void display_img_is(boolean is) {
  display_img_is = is;
}