import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener; 
import javax.swing.JFrame; 
import javax.swing.JMenu; 
import javax.swing.JMenuBar; 
import javax.swing.JMenuItem; 
import rope.core.R_Image; 
import rope.costume.R_Shape; 
import geomerative.*; 
import java.lang.reflect.Method; 
import java.awt.FileDialog; 
import java.awt.Frame; 
import java.io.FilenameFilter; 
import java.nio.ByteBuffer; 
import java.nio.ByteOrder; 
import processing.pdf.*; 
import rope.core.*; 
import rope.vector.*; 
import java.util.Arrays; 
import java.util.Iterator; 
import java.util.Random; 
import java.awt.image.BufferedImage; 
import java.awt.Color; 
import java.awt.Font; 
import java.awt.image.BufferedImage; 
import java.awt.FontMetrics; 
import java.io.FileNotFoundException; 
import java.io.FileOutputStream; 
import javax.imageio.ImageIO; 
import javax.imageio.IIOImage; 
import javax.imageio.ImageWriter; 
import javax.imageio.ImageWriteParam; 
import javax.imageio.metadata.IIOMetadata; 
import java.lang.reflect.Field; 
import java.awt.Graphics; 
import java.awt.GraphicsEnvironment; 
import java.awt.GraphicsDevice; 
import java.awt.Rectangle; 
import rope.costume.R_Primitive; 
import rope.costume.R_Circle; 
import rope.costume.R_Bezier; 
import rope.costume.R_Star; 
import rope.costume.R_Virus; 
import rope.costume.R_Line2D; 
import processing.video.Movie; 
import processing.video.Movie; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class fx_app extends PApplet {

/**
* FX framework by Stan le Punk 
* the framework attempt to use a collection of shader FX for background or POST FX
* by class FX to manage and set all shaders.

* You can use easily the class and method FX without the framework part, just use the tab start by Y_ and Z_ and make your own shader world.
* @see https://github.com/StanLepunK/Shader
*
* WARNING
* if you change the shader/fx folder, utilies in case you export your sketch, the shader folder is not exported.
* in void setup() 
* set_fx_path(sketchPath(2)+"/shader/fx/");

*/
String app_name = "SHADER FX";
String version ="2018-2021 | v 0.1.0";
PGraphics render;
ArrayList<FX> fx_manager = new ArrayList<FX>();
ArrayList<FX> fx_bg_manager = new ArrayList<FX>();


// int MODE_SHADER = 0; // POST FX on PGrahics > texture
int MODE_SHADER = 1; // POST FX on "g". "g" is the main rendering of Processing
// int MODE_SHADER = 2; // create shader pure fx on the background, is not a post fx on specific media.


public void setup() {
	rope_version();
	
	img_a = loadImage("medium_leticia.jpg");
	img_b = loadImage("medium_puros_girl.jpg");
	set_pattern(16,16,RGB,true);
	// select_mode_shader(MODE_SHADER);
}

float total_fps;
float avg_fps;

public void draw() {	
	set_pattern(16,16,RGB,reset_is());
	String rate = "instant rate"+(int)frameRate + " | average rate:" + (int)avg_fps;
	surface.setTitle(rate);
	total_fps += frameRate;
	avg_fps = total_fps/frameCount;

	render_mode_shader(MODE_SHADER);
	//other_stuff();
	remote_command_movie();
	reset(false);
	instruction(instruction_is());
	add_media();

	// println("display_movie_is()",display_movie_is());
	// println("display_img_is()",display_img_is());
}


// EVENT
public void movieEvent(Movie m) {
	m.read();
}

// scale from wheel
int wheel_click;
public float get_wheel_scale() {
	float inc = .1f;
	if(wheel_click < 0) {
		wheel_click += inc;
	} else if (wheel_click > 0) {
		wheel_click -= inc;
	}
	return wheel_click;
}

public void mouseWheel(MouseEvent e) {
	wheel_click = e.getCount();
}


public void keyPressed() {
	keyPressed_arrow();

	if(key == 'n') {
		reset(true);
	}

	if(key == 'i') {
		incrust_is =  incrust_is ? false:true;
	}

	if(key == ' ') {
		space_switch();
	}

	if(key == 'i') {
		instruction_switch();
	}

	if(key == 'o') {
		folder_is(true);
		select_folder();
		instruction_is(false);
		display_movie_is(false);
		display_img_is(true);
	}

	if(key == 'm') {
		input_is(true);
		select_input();
		instruction_is(false);
		display_movie_is(true);
		display_img_is(false);
		media_is(false);
	}

	if(key == DELETE || key == BACKSPACE) {
		lib_img.clear();
		println("clear librarie image:", lib_img.size());
	}
}

public void keyReleased() {
	keyReleased_arrow_false();
}

/**
* CROPE BAR
* Control ROmanesco Processing Environment
* v 0.0.9
* Copyleft (c) 2019-2019
* Processing 3.5.3
* Rope library 0.5.1
* @author @stanlepunk
* @see https://github.com/StanLepunK/Crope
*/







 
public class Crope_Bar {
	JFrame frame;
	JMenuBar menu_bar;

	public Crope_Bar(PApplet app) {
		if(get_os_family().equals("mac")) {
			System.setProperty("apple.laf.useScreenMenuBar","true");
		}
		
		frame = (JFrame) ((processing.awt.PSurfaceAWT.SmoothCanvas)app.getSurface().getNative()).getFrame();
		menu_bar = new JMenuBar();
		frame.setJMenuBar(menu_bar);
	}

  // ArrayList<JMenuItem> menu_item_list = new ArrayList<JMenuItem>();
  ArrayList<C_Menu_Item> menu_item_list = new ArrayList<C_Menu_Item>();
	public void set(String... data) {
		menu_item_list.clear();
		build(data);
		set_listener();
	}
  
	public void build(String... data) {
		JMenu [] menu = new JMenu [data.length];
		int count = 0;
		for(int i = 0 ; i < data.length ; i++) {
			// println(data[i]);
			String[] content = split(data[i],",");
			menu[i] = new JMenu(content[0]);
			menu_bar.add(menu[i]);
			if(content.length > 1) {
				for(int k = 1 ; k < content.length ; k++) {
					if(content[k].equals("|")) {
						menu[i].addSeparator();
					} else {
						count++;
						// println(count);
						JMenuItem menu_item = new JMenuItem(content[k]);
						// menu_item_list.add(menu_item);
						menu[i].add(menu_item);

						C_Menu_Item cmi = new C_Menu_Item(content[k],count,menu_item);
						menu_item_list.add(cmi);
						
					}
				}
			}
		}
	}

	String import_image = "import image";
	String import_media = "import media";
	String import_shape = "import shape";
	String import_text = "import text";
	String import_video = "import video";

	String import_folder = "import folder";
	String load_file = "load";
	String load_recent_file = "load recent";
	String save_file = "save";
	String save_as_file = "save as";

	public void set_listener() {
		if(menu_item_list.size() > 0) {
			// for(JMenuItem mi : menu_item_list) {
			for(C_Menu_Item cmi : menu_item_list) {
				// println("setting",cmi.get_name(),cmi.get_id());
				if(cmi.get_name().toLowerCase().equals(import_image)) {
					cmi.get_menu().addActionListener(new ActionListener() { 
						public void actionPerformed(ActionEvent ae) {
							select_input("image");
							println("action event:",import_image);
						}
			    });
				} else if(cmi.get_name().toLowerCase().equals(import_media)) {
					cmi.get_menu().addActionListener(new ActionListener() { 
						public void actionPerformed(ActionEvent ae) {
							select_input("media");
							println("action event:",import_image);
						}
			    });
				} else if(cmi.get_name().toLowerCase().equals(import_shape)) {
					cmi.get_menu().addActionListener(new ActionListener() { 
						public void actionPerformed(ActionEvent ae) {
							select_input("shape");
							println("action event:",import_shape);
						}
			    });
				} else if(cmi.get_name().toLowerCase().equals(import_text)) {
					cmi.get_menu().addActionListener(new ActionListener() { 
						public void actionPerformed(ActionEvent ae) {
							select_input("text");
							println("action event:",import_text);
						}
			    });
				} else if(cmi.get_name().toLowerCase().equals(import_video)) {
					cmi.get_menu().addActionListener(new ActionListener() { 
						public void actionPerformed(ActionEvent ae) {
							select_input("video");
							println("action event:",import_video);
						}
			    });
				} else if(cmi.get_name().toLowerCase().equals(import_folder)) {
					cmi.get_menu().addActionListener(new ActionListener() { 
						public void actionPerformed(ActionEvent ae) {
							select_folder();
							println("action event:",import_folder);
						}
			    });
				} else if(cmi.get_name().toLowerCase().equals(load_file)) {
					cmi.get_menu().addActionListener(new ActionListener() { 
						public void actionPerformed(ActionEvent ae) { 
							select_input("load");
							println("action event:",load_file);
						}
			    });
				} else if(cmi.get_name().toLowerCase().equals(load_recent_file)) {
					cmi.get_menu().addActionListener(new ActionListener() { 
						public void actionPerformed(ActionEvent ae) {
							select_input("load");
							println("action event:",load_recent_file);
						}
			    });
				} else if(cmi.get_name().toLowerCase().equals(save_file)) {
					cmi.get_menu().addActionListener(new ActionListener() { 
						public void actionPerformed(ActionEvent ae) { 
							println("action event:",save_file);
						}
			    });
				} else if(cmi.get_name().toLowerCase().equals(save_as_file)) {
					cmi.get_menu().addActionListener(new ActionListener() { 
						public void actionPerformed(ActionEvent ae) { 
							println("action event:",save_as_file);
						}
			    });
				} else {
					cmi.get_menu().addActionListener(new ActionListener() { 
						public void actionPerformed(ActionEvent ae) { 
							println("action event: unknow action, check catalogue action > void name.action_event()");
						}
			    });;
				}
			}
		}
	}

 
	public void action_event() {
		println(import_image,import_media,import_shape,import_text,import_video,
						import_folder,
						load_file,load_recent_file,
						save_file,save_as_file);
	}




	private class C_Menu_Item {
		protected int id;
		protected String name;
		protected JMenuItem j_menu_item;
		C_Menu_Item(String name, int id, JMenuItem j_menu_item) {
			this.name = name;
			this.id = id;
			this.j_menu_item = j_menu_item;
		}

		protected String get_name() {
			return name;
		}

		protected int get_id() {
			return id;
		}

		protected JMenuItem get_menu() {
			return j_menu_item;
		}
	}
  





	public void watch() {
		if(menu_item_list.size() > 0) {
			/*
			for(JMenuItem mi : menu_item_list) {
				// mi.get_something_but_what();
			}
			*/
		}

	}




	public void show() {
		frame.setVisible(true);
	}
  
	public void info_item() {
		if(menu_item_list.size() > 0) {
			// println("frameCount",frameCount);
			for(C_Menu_Item cmi : menu_item_list) {
				JMenuItem mi = cmi.get_menu();
			//for(JMenuItem mi : menu_item_list) {
				// println("getUIClassID()",mi.getUIClassID());
				//println("getText()",mi.getText());
				// println("getActionCommand()",mi.getActionCommand());
				// println("getAction()",mi.getAction());
				// println("getAction()");
				// printArray(mi.getActionListeners());
			}
		}
	}

	public void help() {
		println("\npass all menus in differents String to method set(String... menu)");
		println("The menu must have a content separate by coma ','");
		println("the menu content represent the menu title");
		println("for the separor use '|' between two comas\n");
	}
	
 
}

/**
* Rope COLOUR
*v 0.11.5
* Copyleft (c) 2016-2021
* Stan le Punk > http://stanlepunk.xyz/
* @author @stanlepunk
* @see https://github.com/StanLepunK/Rope_framework
*
* Pack of method to use colour, palette and method conversion
*
*/





/**
* palette
* v 0.0.2
*/
R_Colour palette_colour_rope;
public void palette(int... list_colour) {
	if(palette_colour_rope == null) {
		palette_colour_rope = new R_Colour(this,list_colour);
	} else {
		palette_colour_rope.clear();
		palette_colour_rope.add(0,list_colour);
	}
}

public int [] get_palette() {
	if(palette_colour_rope != null) {
		return palette_colour_rope.get();
	} else return null;
}





/**
* COLOUR LIST class
* v 0.4.1
* 2017-2019
*/
public class R_Colour implements rope.core.R_Constants, rope.core.R_Constants_Colour {
	ArrayList<Palette> list;
	PApplet pa;
	public R_Colour(PApplet pa) {
		this.list = new ArrayList<Palette>();
		this.pa = pa;
		Palette p = new Palette();
		list.add(p);
	}

	public R_Colour(PApplet pa, int... list_colour) {
		this.list = new ArrayList<Palette>();
		this.pa = pa;
		Palette p = new Palette(list_colour);
		list.add(p);
	}

	public void add(int group, int [] colour) {
		if(group >= 0) {
			if(group >= size_group()) {
				String s = "class R_Colour method add(int group, int [] colour) the group: " + group + " don't exist yet, add group before use this method";
				System.err.println(s);
			} else {
				list.get(group).add(colour);
			}
		}
	}

	public void add(int group, int colour) {
		if(group >= 0) {
			if(group >= size_group()) {
				String s = "class R_Colour method add(int group, int colour): the group " + group + " don't exist yet, add group before use this method";
				System.err.println(s);
			} else {
				list.get(group).add(colour);
			}   
		}
	}

	public void add(int colour) {
		list.get(0).add(colour);
	}

	public void add_group() {
		Palette p = new Palette();
		list.add(p);
	}

	public void add_group(int num) {
		for(int i = 0 ; i < num ; i++) {
			Palette p = new Palette();
			list.add(p);
		}
	}

	public void set(int index, int colour) {
		set(0, index, colour);
	}

	public void set(int group, int index, int colour) {
		if(group >= 0 && group <= size_group() && index >= 0 && index < list.get(group).size()) {
			list.get(group).set(index,colour);
		}
	}



 
	// clear
	public void clear() {
		for(Palette p : list) {
			p.clear();
		}
	}

	public void clear(int group) {
		if(group >= 0 && group < size_group()) {
			list.get(group).clear();
		} else {
			printErr("class R_Colour method clear(",group,") this group don't match with any group");
		}
	}


	public void remove(int group, int index) {
		if(group >= 0 && group < size_group()) {
			list.get(group).remove(index);
		} else {
			printErr("class R_Colour method remove(",group,") this group don't match with any group");
		}
	}
	

	// GET
	// get size
	public int size_group() {
		return list.size();
	}

	public int size() {
		return size(0);
	}

	public int size(int group) {
		if(group >= 0 && group < size_group()) {
			return list.get(group).size();
		} else {
			String s = "class R_Colour method size(int group) the group: " + group + " don't exist yet, add group before use this method, instead '-1' is return";
			System.err.println(s);
			return -1;
		}
	}
	
	// get content
	public int [] get() {
		return get(0);
	}

	public int [] get(int group) {
		if(group >= 0 && group < size_group()) {
			return list.get(group).array(); 
		} else {
			String s = "class R_Colour method get(int group) the group: " + group + " don't exist yet, add group before use this method";
			System.err.println(s);
			return null;
		}
	}


	// get colour
	public int rand() {
		int group = floor(random(size_group()));
		int target = floor(random(list.get(group).array().length));
		return get_colour(group,target);
	}

	public int rand(int group) {
		int target = 0;
		if(group < list.size()) {
			target = floor(random(list.get(group).array().length));
		} else {
			group = 0;
			target = floor(random(list.get(group).array().length));
		}
		return get_colour(group,target);
	}


	public int get_colour(int group, int target) {
		if(target >= 0 && group >= 0 && group < size_group() && target < list.get(group).array().length) {
			return list.get(group).array()[target];
		} else {
			System.err.println("class R_Colour method get_colour() no target match with your demand, instead '0' is return");
			return 0;
		}
	}


	public float get_hue(int group, int target) {
		if(group >= 0 && group < size_group()) {
			return pa.hue(list.get(group).get(target));
		} else {
			printErr("class R_Color method get_hue(",group,") no group match with your demand, instead '0' is return");
			return 0;
		}
	}


	public float get_saturation(int group, int target) {
		if(group >= 0 && group < size_group()) {
			return pa.saturation(list.get(group).get(target));
		} else {
			printErr("class R_Color method get_saturation(",group,") no group match with your demand, instead '0' is return");
			return 0;
		}
	}
	

	public float get_brightness(int group, int target) {
		if(group >= 0 && group < size_group()) {
			return pa.brightness(list.get(group).get(target));
		} else {
			printErr("class R_Color method get_brightness(",group,") no group match with your demand, instead '0' is return");
			return 0;
		}
	}

	public float get_red(int group, int target) {
		if(group >= 0 && group < size_group()) {
			return pa.red(list.get(group).get(target));
		} else {
			printErr("class R_Color method get_red(",group,") no group match with your demand, instead '0' is return");
			return 0;
		}
	}
	

	public float get_green(int group, int target) {
		if(group >= 0 && group < size_group()) {
			return pa.green(list.get(group).get(target));
		} else {
			printErr("class R_Color method get_green(",group,") no group match with your demand, instead '0' is return");
			return 0;
		}
	}

	public float get_blue(int group, int target) {
		if(group >= 0 && group < size_group()) {
		 return pa.blue(list.get(group).get(target));
		} else {
			printErr("class R_Color method get_blue(",group,") no group match with your demand, instead '0' is return");
			return 0;
		}
	}


	public float get_alpha(int group, int target) {
		if(group >= 0 && group < size_group()) {
			return pa.alpha(list.get(group).get(target));
		} else {
			printErr("class R_Color method get_alpha(",group,") no group match with your demand, instead '0' is return");
			return 0;
		}
	}


	public vec3 get_hsb(int group, int target) {
		if(group >= 0 && group < size_group()) {
			int c = list.get(group).get(target);
			return vec3(pa.hue(c),pa.saturation(c),pa.brightness(c));
		} else {
			printErr("class R_Color method get_hsb(",group,") no group match with your demand, instead 'null' is return");
			return null;
		}
	}


	public vec4 get_hsba(int group, int target) {
		if(group >= 0 && group < size_group()) {
			int c = list.get(group).get(target);
			return vec4(pa.hue(c),pa.saturation(c),pa.brightness(c),pa.alpha(c));
		} else {
			printErr("class R_Color method get_hsba(",group,") no group match with your demand, instead 'null' is return");
			return null;
		}
	}


	public vec3 get_rgb(int group, int target) {
		if(group >= 0 && group < size_group()) {
			int c = list.get(group).get(target);
			return vec3(pa.red(c),pa.green(c),pa.blue(c));
		} else {
			printErr("class R_Color method get_rgb(",group,") no group match with your demand, instead 'null' is return");
			return null;
		}
	}
	

	public vec4 get_rgba(int group, int target) {
		if(group >= 0 && group < size_group()) {
			int c = list.get(group).get(target);
			return vec4(pa.red(c),pa.green(c),pa.blue(c),pa.alpha(c));
		} else {
			printErr("class R_Color method get_rgba(",group,") no group match with your demand, instead 'null' is return");
			return null;
		}
	}
	

	public float [] hue() {
		return hue(0);
	}

	public float [] hue(int group) {
		if(group >= 0 && group < size_group()) {
			float[] component = new float[list.get(group).size()];
			for(int i = 0 ; i < component.length ; i++) {
				int c = list.get(group).get(i);
				component[i] = pa.hue(c);
			}
			return component;
		} else {
			printErr("class R_Color method hue(",group,") no group match with your demand, instead 'null' is return");
			return null; 
		}
	}


	
	public float [] saturation() {
		return saturation(0);
	}

	public float [] saturation(int group) {
		if(group >= 0 && group < size_group()) {
			float[] component = new float[list.get(group).size()];
			for(int i = 0 ; i < component.length ; i++) {
				int c = list.get(group).get(i);
				component[i] = pa.saturation(c);
			}
			return component;
		} else {
			printErr("class R_Color method saturation(",group,") no group match with your demand, instead 'null' is return");
			return null; 
		}
	}



	public float [] brightness() {
		return brightness(0);
	}
	
	public float [] brightness(int group) {
		if(group >= 0 && group < size_group()) {
			float[] component = new float[list.get(group).size()];
			for(int i = 0 ; i < component.length ; i++) {
				int c = list.get(group).get(i);
				component[i] = pa.brightness(c);
			}
			return component;
		} else {
			printErr("class R_Color method brightness(",group,") no group match with your demand, instead 'null' is return");
			return null; 
		}
	}


	public float [] red() {
		return red(0);
	}

	public float [] red(int group) {
		if(group >= 0 && group < size_group()) {
			float[] component = new float[list.get(group).size()];
			for(int i = 0 ; i < component.length ; i++) {
				int c = list.get(group).get(i);
				component[i] = pa.red(c);
			}
			return component;
		} else {
			printErr("class R_Color method red(",group,") no group match with your demand, instead 'null' is return");
			return null; 
		}
	}

	
	public float [] green() {
		return green(0);
	}

	public float [] green(int group) {
		if(group >= 0 && group < size_group()) {
			float[] component = new float[list.get(group).size()];
			for(int i = 0 ; i < component.length ; i++) {
				int c = list.get(group).get(i);
				component[i] = pa.green(c);
			}
			return component;
		} else {
			printErr("class R_Color method green(",group,") no group match with your demand, instead 'null' is return");
			return null; 
		}
	}


	public float [] blue() {
		return blue(0);
	}
	
	public float [] blue(int group) {
		if(group >= 0 && group < size_group()) {
			float[] component = new float[list.get(group).size()];
			for(int i = 0 ; i < component.length ; i++) {
				int c = list.get(group).get(i);
				component[i] = pa.blue(c);
			}
			return component;
		} else {
			printErr("class R_Color method blue(",group,") no group match with your demand, instead 'null' is return");
			return null;
		}
	}


	public float [] alpha() {
		return alpha(0);
	}

	public float [] alpha(int group) {
		if(group >= 0 && group < size_group()) {
			float[] component = new float[list.get(0).size()];
			for(int i = 0 ; i < component.length ; i++) {
				int c = list.get(group).get(i);
				component[i] = pa.blue(c);
			}
			return component;
		} else {
			printErr("class R_Color method alpha(",group,") no group match with your demand, instead 'null' is return");
			return null;
		}
	}

	

	public vec3 [] hsb() {
		return hsb(0);
	}

	public vec3 [] hsb(int group) {
		if(group >= 0 && group < size_group()) {
			vec3[] component = new vec3[list.get(group).size()];
			for(int i = 0 ; i < component.length ; i++) {
				int c = list.get(group).get(i);
				component[i] = vec3(pa.hue(c),pa.saturation(c),pa.brightness(c));
			}
			return component;
		} else {
			printErr("class R_Color method hsb(",group,") no group match with your demand, instead 'null' is return");
			return null;
		}
	}


	public vec3 [] rgb() {
		return rgb(0);
	}

	public vec3 [] rgb(int group) {
		if(group >= 0 && group < size_group()) {
			vec3[] component = new vec3[list.get(group).size()];
			for(int i = 0 ; i < component.length ; i++) {
				int c = list.get(group).get(i);
				component[i] = vec3(pa.red(c),pa.green(c),pa.blue(c));
			}
			return component;
		} else {
			printErr("class R_Color method rgb(",group,") no group match with your demand, instead 'null' is return");
			return null;
		}
	}


	public vec4 [] hsba() {
		return hsba(0);
	}

	public vec4 [] hsba(int group) {
		if(group >= 0 && group < size_group()) {
			vec4[] component = new vec4[list.get(0).size()];
			for(int i = 0 ; i < component.length ; i++) {
				int c = list.get(0).get(i);
				component[i] = vec4(pa.hue(c),pa.saturation(c),pa.brightness(c),pa.alpha(c));
			}
			return component;
		} else {
			printErr("class R_Color method hsba(",group,") no group match with your demand, instead 'null' is return");
			return null;
		}
	}


	public vec4 [] rgba() {
		return rgba(0);
	}

	public vec4 [] rgba(int group) {
		if(group >= 0 && group < size_group()) {
			vec4[] component = new vec4[list.get(group).size()];
			for(int i = 0 ; i < component.length ; i++) {
				int c = list.get(group).get(i);
				component[i] = vec4(pa.red(c),pa.green(c),pa.blue(c),pa.alpha(c));
			}
			return component;
		} else {
			printErr("class R_Color method rgba(",group,") no group match with your demand, instead 'null' is return");
			return null;
		}
	}


	


	/**
	* Palette
	* v 0.2.1
	* 2019-2019
	*/
	private class Palette {
		private ArrayList<Integer> list;
		private Palette() {
			list = new ArrayList<Integer>();
		}

		private Palette(int... colour) {
			list = new ArrayList<Integer>();
			add(colour);
		}

		private void add(int... colour) {
			for(int i = 0 ; i < colour.length ; i++) {
				list.add(colour[i]);
			}
		}

		private void set(int index, int colour) {
			if(index >= 0 && index < list.size()) {
				list.set(index,colour);
			}
		}

		private void clear() {
			list.clear();
		}

		private void remove(int target) {
			if(target >=0 && target < list.size()) {
				list.remove(target);
			}
		}

		private int size() {
			return list.size();
		}

		private int get(int target) {
			if(target >= 0 && target < list.size()) {
				return list.get(target);
			} else {
				printErr("class R_Colour > private class Palette > method get(",target,") don't match with any element of list instead '0 is return");
				return 0;
			}

		}

		private int [] array() {
			// May be in the next Processing version when Lambda expression can be use
			// int[] array = list.stream().mapToInt(i->i).toArray(); 
			int[] array = new int[list.size()];
			for(int i = 0 ; i < array.length ; i++) {
				array[i] = list.get(i);
			}
			return array;
		}
	}
}








/**
GET COLORMODE
v 0.0.2
*/
/**
* getColorMode()
* @return float array of your color environment.
* @param boolean print_info_is retrun a print about the color environment
*/
public float [] getColorMode(boolean print_info_is) {
	float colorMode = g.colorMode ;
	float x = g.colorModeX;
	float y = g.colorModeY;
	float z = g.colorModeZ;
	float a = g.colorModeA;
	float array[] = {colorMode,x,y,z,a};
	if (print_info_is && g.colorMode == HSB) {
		println("HSB",x,y,z,a);
	} else if(print_info_is && g.colorMode == RGB) {
		println("RGB",x,y,z,a);
	}
	return array;
}

public float [] getColorMode() {
	return getColorMode(false);
}










/**
camaieu 
v 0.1.2
*/
// return hue or other data in range of specific data float
public float camaieu(float max, float reference, float range) {
	float camaieu = 0 ;
	float choice = random(-range,range);
	camaieu = reference + choice;
	if(camaieu < 0 ) camaieu = max +camaieu;
	if(camaieu > max) camaieu = camaieu -max;
	return camaieu;
}






/**
* mixer
* v 0.0.1
* mix color together with the normal threshold variation
*/
public int mixer(int o, int d, float threshold) {
	if(g.colorMode == 1) {
		float x = gradient_value(red(o),red(d),threshold);
		float y = gradient_value(green(o),green(d),threshold);
		float z = gradient_value(blue(o),blue(d),threshold);
		return color(x,y,z);
	} else {
		float x = gradient_value(hue(o),hue(d),threshold);
		float y = gradient_value(saturation(o),saturation(d),threshold);
		float z = gradient_value(brightness(o),brightness(d),threshold);
		return color(x,y,z);
	}
}

public float gradient_value(float origin, float destination, float threshold) {
	float gradient = origin;
	float range = origin - destination;
	float power = range * threshold;
	return gradient - power;
}








/**
* plot
* v 0.1.3
* set pixel color with alpha and PGraphics management 
*/
boolean use_plot_x2_is = false;
public void use_plot_x2(boolean is) {
	use_plot_x2_is = is;
}

public void plot(int x, int y, int colour) {
	plot(x, y, colour, 1.0f, g);
}

public void plot(int x, int y, int colour, PGraphics pg) {
	plot(x, y, colour, 1.0f, pg);
}

public void plot(int x, int y, int colour, float alpha) {
	plot(x, y, colour, alpha, g);
}

public void plot(int x, int y, int colour, float alpha, PGraphics pg) {
	int index = index_pixel_array(x, y, pg.width);
	if(index >= 0 && index < pg.pixels.length && x >= 0 && x < pg.width) {
		int bg = pg.pixels[index];
		int col = colour;
		if(alpha < 1) {
			col = mixer(bg,colour,alpha);
		} 
		pg.pixels[index] = col;
		if(use_plot_x2_is) {
			Integer [] arr = new Integer[calc_plot_neighbourhood(index, x, y, pg.width, pg.height).size()];
			arr = calc_plot_neighbourhood(index, x, y, pg.width, pg.height).toArray(arr);
			for(int which_one : arr) {
				pg.pixels[which_one] = col;
			}
		}
	}
}

public int index_pixel_array(int x, int y, int w) {
	return (x + y * w);
}
public ArrayList<Integer> calc_plot_neighbourhood(int index_base, int x, int y, int w, int h) {
	ArrayList<Integer> arr = new ArrayList<Integer>();
	int index, tx, ty = 0;

	if(x < w -1) {
		index = index_base + 1;
		arr.add(index);
	}
	if(x > 0) {
		index = index_base - 1;
		arr.add(index);
	}
	if(y < h -1) {
		index = index_base + w;
		arr.add(index);
	}
	if(y > 0) {
		index = index_base - w;
		arr.add(index);
	}
	return arr;
}














/**
* simple color pool
* v 0.1.2
*/
public int [] hue_palette(int master_colour, int num_group, int num_colour, float spectrum) {
	if(num_group > num_colour) num_group = num_colour;
	float div = 1.0f / num_group;
	float hue_range = spectrum*div; 
	float hue_key = hue(master_colour);
	vec2 range_sat = vec2(saturation(master_colour));
	vec2 range_bri = vec2(brightness(master_colour));
	vec2 range_alp = vec2(100.0f);
	return color_pool(num_colour, num_group, hue_key,hue_range, range_sat,range_bri,range_alp);
}

/**
color pool 
v 0.5.2
*/
public int [] color_pool(int num) {
	float hue_range = -1;
	int num_group = 1;
	float key_hue = -1;
	return color_pool(num,num_group, key_hue, hue_range,null,null,null) ;
}

public int [] color_pool(int num, float key_hue, float hue_range) {
	int num_group = 1;
	return color_pool(num,num_group, key_hue, hue_range,null,null,null) ;
}

public int [] color_pool(int num, int num_group, float key_hue, float hue_range) {
	return color_pool(num,num_group,  key_hue, hue_range,null,null,null);
}

public int [] color_pool(int num, int num_group, float key_hue, float hue_range, vec2 sat_range, vec2 bright_range) {
	return color_pool(num,num_group,  key_hue, hue_range,sat_range,bright_range,null);
}

public int [] color_pool(int num, int colour, float hue_range, float sat_range, float bri_range) {
	return color_pool(1, num, colour, hue_range,sat_range,bri_range);
}

public int [] color_pool(int num, int num_group, int colour, float hue_range, float sat_range, float bri_range) {
	int ref = g.colorMode;
	float x = g.colorModeX;
	float y = g.colorModeY;
	float z = g.colorModeZ;
	float a = g.colorModeA;
	colorMode(HSB,360,100,100,100);

	float h = hue(colour);
	float s = saturation(colour);
	float s_min = s - sat_range;
	if(s_min < 0) s_min = 0;
	if(s_min > g.colorModeY) s_min = g.colorModeY;
	float s_max = s + sat_range;
	if(s_max < 0) s_max = 0;
	if(s_max > g.colorModeY) s_max = g.colorModeY;

	float b = brightness(colour);
	float b_min = b - bri_range;
	if(b_min < 0) b_min = 0;
	if(b_min > g.colorModeZ) b_min = g.colorModeZ;
	float b_max = b + bri_range;
	if(b_max < 0) b_max = 0;
	if(b_max > g.colorModeZ) b_max = g.colorModeZ;

	colorMode(ref,x,y,z,a);
	return color_pool(num,num_group, h, hue_range,vec2(s_min,s_max),vec2(b_min,b_max),null);
}

// color pool by group
public int [] color_pool(int num, int num_group, float key_hue, float hue_range, vec2 sat_range, vec2 bright_range, vec2 alpha_range) {
	int ref = g.colorMode;
	float x = g.colorModeX;
	float y = g.colorModeY;
	float z = g.colorModeZ;
	float a = g.colorModeA;
	float ratio_h = 360.0f / g.colorModeX;
	float ratio_s = 100.0f / g.colorModeY;
	float ratio_b = 100.0f / g.colorModeZ;
	float ratio_a = 100.0f / g.colorModeA;

	// create range if necessary
	if(hue_range < 0) {
		hue_range = g.colorModeX *.5f;
	}
	if(sat_range == null) {
		sat_range = vec2(g.colorModeY);
	}
	if(bright_range == null) {
		bright_range = vec2(g.colorModeZ);
	}
	if(alpha_range == null) {
		alpha_range = vec2(g.colorModeA);
	}

	hue_range *= ratio_h;
	sat_range.mult(ratio_s);
	bright_range.mult(ratio_b);
	alpha_range.mult(ratio_a);
	colorMode(HSB,360,100,100,100);
	

	

	// create ref
	float [] color_ref = new float[num_group];
	if(key_hue >= 0 ) {
		color_ref[0] = key_hue;
	} else {
		color_ref[0] = random(g.colorModeX);
	}
	if(num_group > 1) {
		float step = g.colorModeX / num_group;
		for(int i = 1 ; i < num_group ; i++) {
			color_ref[i] = color_ref[i -1] + step;
			if(color_ref[i] > g.colorModeX) {
				color_ref[i] = color_ref[i] - g.colorModeX;
			}      
		}
	}

	int [] list = new int[num];
	int count = 0;
	int step = num / num_group;
	int next_stop = step;
	for(int i = 0 ; i < list.length ; i++) {
		if(i > next_stop) {
			next_stop += step;
		}
		float saturation = random(sat_range);
		float brightness = random(bright_range);
		float alpha = random(alpha_range);
		float hue = camaieu(g.colorModeX, color_ref[count], hue_range);
		list[i] = color(hue, saturation,brightness, alpha);
		count++;
		if(count >= color_ref.length) count = 0;

	}
	// back to original colorMode
	colorMode(ref,x,y,z,a);
	return list ;
}
























/**
component range
*/
public boolean alpha_range(float min, float max, int colour) {
	float alpha = alpha(colour) ;
	return in_range(min, max, alpha) ;
}

public boolean red_range(float min, float max, int colour) {
	float  r = red(colour) ;
	return in_range(min, max, r) ;
}

public boolean green_range(float min, float max, int colour) {
	float  g = green(colour) ;
	return in_range(min, max, g) ;
}

public boolean blue_range(float min, float max, int colour) {
	float  b = blue(colour) ;
	return in_range(min, max, b) ;
}

public boolean saturation_range(float min, float max, int colour) {
	float  s = saturation(colour) ;
	return in_range(min, max, s) ;
}

public boolean brightness_range(float min, float max, int colour) {
	float  b = brightness(colour) ;
	return in_range(min, max, b) ;
}


public boolean hue_range(float min, float max, int colour) {
	int c_m = g.colorMode ;
	float c_x = g.colorModeX ;
	float c_y = g.colorModeY ;
	float c_z = g.colorModeZ ;
	float c_a = g.colorModeA ;
	colorMode(HSB, c_x, c_y, c_z, c_a) ;
	float  h = hue(colour) ;

	boolean result = false ;
	// test for the wheel value, hue is one of them ;
	result = in_range_wheel(min, max, c_x, h) ;
	// return to the current colorMode
	colorMode(c_m, c_x, c_y, c_z, c_a) ;
	return result ;
}












/**
convert color 0.3.0
*/
public vec3 hsb_to_rgb(float hue, float saturation, float brightness) {
	vec4 ref = vec4(g.colorModeX, g.colorModeY, g.colorModeY, g.colorModeA);
	int c = color(hue,saturation,brightness);

	colorMode(RGB,255) ;
	vec3 rgb = vec3(red(c),green(c),blue(c)) ;
	// return to the previous colorMode
	colorMode(HSB,ref.x,ref.y,ref.z,ref.w) ;
	return rgb;
}

public vec4 to_cmyk(int c) {
	return rgb_to_cmyk(red(c),green(c),blue(c));
}


public vec3 to_rgb(int c) {
	return vec3(red(c),green(c),blue(c));
}

public vec4 to_rgba(int c) {
	return vec4(red(c),green(c),blue(c),alpha(c));
}

public vec3 to_hsb(int c) {
	return vec3(hue(c),saturation(c),brightness(c));
}

public vec4 to_hsba(int c) {
	return vec4(hue(c),saturation(c),brightness(c),alpha(c));
}





public vec4 rgb_to_cmyk(float r, float g, float b) {
	// convert to 0 > 1 value
	r = r/this.g.colorModeX;
	g = g/this.g.colorModeY;
	b = b/this.g.colorModeZ;
	// RGB to CMY
	float c = 1.f-r;
	float m = 1.f-g;
	float y = 1.f-b;
	// CMY to CMYK
	float var_k = 1;
	if (c < var_k) var_k = c;
	if (m < var_k) var_k = m;
	if (y < var_k) var_k = y;
	// black only
	if (var_k == 1) { 
		c = 0;
		m = 0;
		y = 0;
	} else {
		c = (c-var_k)/(1-var_k);
		m = (m-var_k)/(1-var_k);
		y = (y-var_k)/(1-var_k);
	}
	float k = var_k; 
	return vec4(c,m,y,k);
}







public vec3 cmyk_to_rgb(float c, float m, float y, float k) {
	vec3 rgb = null;
	 // cmyk value must be from 0 to 1
	if(colour_normal_range_is(c) && colour_normal_range_is(m) && colour_normal_range_is(y) && colour_normal_range_is(k)) {
		// CMYK > CMY
		c = (c *(1.0f -k) +k);
		m = (m *(1.0f -k) +k);
		y = (y *(1.0f -k) +k);
		//CMY > RGB
		float red = (1.0f -c) *g.colorModeX;
		float green = (1.0f -m) *g.colorModeY;
		float blue = (1.0f -y) *g.colorModeZ;
		rgb = vec3(red,green,blue);

	} else {
		printErr("method cmyk_to_rgb(): the values c,m,y,k must have value from 0 to 1.\n","yours values is cyan",c,"magenta",m,"yellow",y,"black",k);
	}
	return rgb;
}

public boolean colour_normal_range_is(float v) {
	if(v >= 0.0f && v <= 1.0f) return true; else return false;
}










// color context
/*
* good when the text is on different background
*/
public int color_context(int color_ref, int threshold, int clear, int dark) {
	int new_color ;
	if(brightness(color_ref) < threshold ) {
		new_color = clear;
	} else {
		new_color = dark ;
	}
	return new_color ;
}
/**
* BACKGROUND FX
* Rope background shader fx collection
* 2019-2019
* v 0.1.7
* all filter bellow has been tested.
* @see http://stanlepunk.xyz
* @see https://github.com/StanLepunK/Shader
*/



/**
* Template fx backgournd by Stan le punk
* this template can be used for texture or direct filtering
* v 0.0.1
* 2019-2019
*/
// setting by class FX
public PGraphics fx_bg_template(FX fx) {
	return fx_bg_template(fx.get_canvas(),fx.on_g(),vec3(fx.get_colour()));
}


// test setting
public PGraphics fx_bg_template() {
  vec3 colour = abs(vec3().sin_wave(frameCount,.01f,.02f,.03f));
	return fx_bg_template(null,true,colour);
}


// main
PShader fx_bg_template;
PGraphics pg_template_fx_bg;
public PGraphics fx_bg_template(ivec2 canvas, boolean on_g, vec3 colour) {
	
	if(!on_g && (pg_template_fx_bg == null
								|| (canvas.x != pg_template_fx_bg.width 
								&& canvas.y != pg_template_fx_bg.height))) {
		pg_template_fx_bg = createGraphics(canvas.x,canvas.y,get_renderer());
	}
  // setting
	if(fx_bg_template == null) {
		String path = get_fx_bg_path()+"template_fx_bg.glsl";
		if(fx_bg_rope_path_exists) {
			fx_bg_template = loadShader(path);
			println("load shader: template_fx_bg.glsl");
		} 
	} else {
		set_shader_resolution(fx_bg_template,canvas,on_g);

		// render
		fx_bg_template.set("rgb",colour.x,colour.y,colour.z); // value from 0 to 1

		if(on_g) {
			filter(fx_bg_template);
		} else {
			pg_template_fx_bg.shader(fx_bg_template);
		}
	}


	if(on_g) {
		return null;
	} else {
		return pg_template_fx_bg; 
	}
}









/**
* Cellular fx backgournd by Stan le punk
* this template can be used for texture or direct filtering
* v 0.0.3
* 2019-2019
*/
// setting by class FX
public PGraphics fx_bg_cellular(FX fx) {
	return fx_bg_cellular(fx.get_canvas(),fx.on_g(),fx.get_colour(),fx.get_num(),vec2(fx.get_speed()),fx.get_quality());
}




// main
PShader fx_bg_cellular;
PGraphics pg_cellular_fx_bg;
public PGraphics fx_bg_cellular(ivec2 canvas, boolean on_g, vec4 colour, int num, vec2 speed, float quality) {
	if(!on_g && (pg_cellular_fx_bg == null
								|| (canvas.x != pg_cellular_fx_bg.width 
								&& canvas.y != pg_cellular_fx_bg.height))) {
		pg_cellular_fx_bg = createGraphics(canvas.x,canvas.y,get_renderer());
	}
  // setting
	if(fx_bg_cellular == null) {
		String path = get_fx_bg_path()+"cellular.glsl";
		if(fx_bg_rope_path_exists) {
			fx_bg_cellular = loadShader(path);
			println("load shader: cellular.glsl");
		}	
	} else {
		set_shader_resolution(fx_bg_cellular,canvas,on_g);

		fx_bg_cellular.set("time",frameCount *.1f); 


    // external paramter
		fx_bg_cellular.set("rgba",colour.x,colour.y,colour.z,colour.w); // value from 0 to 1
		fx_bg_cellular.set("num",num); // value from 0 to 1
		fx_bg_cellular.set("speed",speed.x,speed.y); // value from 0 to 1
		fx_bg_cellular.set("quality",quality); // value from 0 to 1

		if(on_g) {
			filter(fx_bg_cellular);
		} else {
			pg_cellular_fx_bg.shader(fx_bg_cellular);
		}
	}

	if(on_g) {
		return null;
	} else {
		return pg_cellular_fx_bg; 
	}
}








/**
* Heart fx backgournd by Stan le punk
* this template can be used for texture or direct filtering
* v 0.0.1
* 2019-2019
*/
// setting by class FX
public PGraphics fx_bg_heart(FX fx) {
	return fx_bg_heart(fx.get_canvas(),fx.on_g(),vec3(fx.get_colour()),fx.get_num(),fx.get_speed().x,fx.get_quality(),fx.get_strength().x);
}




// main
PShader fx_bg_heart;
PGraphics pg_heart_fx_bg;
public PGraphics fx_bg_heart(ivec2 canvas, boolean on_g, vec3 colour, int num, float speed, float quality, float strength) {
	if(!on_g && (pg_heart_fx_bg == null
								|| (canvas.x != pg_heart_fx_bg.width 
								&& canvas.y != pg_heart_fx_bg.height))) {
		pg_heart_fx_bg = createGraphics(canvas.x,canvas.y,get_renderer());
	}
  // setting
	if(fx_bg_heart == null) {
		String path = get_fx_bg_path()+"heart.glsl";
		if(fx_bg_rope_path_exists) {
			fx_bg_heart = loadShader(path);
			println("load shader: heart.glsl");
		}
	} else {
		set_shader_resolution(fx_bg_heart,canvas,on_g);

		fx_bg_heart.set("time",frameCount *.1f); 


    // external paramter
		fx_bg_heart.set("rgb",colour.x,colour.y,colour.z); // value from 0 to 1
		fx_bg_heart.set("num",num); // value from 0 to 1
		fx_bg_heart.set("speed",speed); // value from 0 to 1
		fx_bg_heart.set("quality",quality); // value from 0 to 1

		fx_bg_heart.set("strength",strength); // value from 1 to ++
		if(on_g) {
			filter(fx_bg_heart);
		} else {
			pg_heart_fx_bg.shader(fx_bg_heart);
		}
	}

	if(on_g) {
		return null;
	} else {
		return pg_heart_fx_bg; 
	}
}








/**
* Necklace fx backgournd by Stan le punk
* this template can be used for texture or direct filtering
* v 0.0.1
* 2019-2019
*/
// setting by class FX
public PGraphics fx_bg_necklace(FX fx) {
	return fx_bg_necklace(fx.get_canvas(),fx.on_g(),vec2(fx.get_pos()),vec2(fx.get_size()),fx.get_colour().x,fx.get_num(),fx.get_speed().x);
}




// main
PShader fx_bg_necklace;
PGraphics pg_necklace_fx_bg;
public PGraphics fx_bg_necklace(ivec2 canvas, boolean on_g, vec2 pos, vec2 size, float alpha, int num, float speed) {
	if(!on_g && (pg_necklace_fx_bg == null
								|| (canvas.x != pg_necklace_fx_bg.width 
								&& canvas.y != pg_necklace_fx_bg.height))) {
		pg_necklace_fx_bg = createGraphics(canvas.x,canvas.y,get_renderer());
	}
  // setting
	if(fx_bg_necklace == null) {
		String path = get_fx_bg_path()+"necklace.glsl";
		if(fx_bg_rope_path_exists) {
			fx_bg_necklace = loadShader(path);
			println("load shader: necklace.glsl");
		}
	} else {
		set_shader_resolution(fx_bg_necklace,canvas,on_g);


		fx_bg_necklace.set("time",frameCount); 
    // external paramter
    fx_bg_necklace.set("position",pos.x,pos.y); // value from 0 to 1
    fx_bg_necklace.set("size",size.x,size.y); // value from 0 to 1
		fx_bg_necklace.set("alpha",alpha); // value from 0 to 1
		fx_bg_necklace.set("num",num); // value from 1 to ++
		fx_bg_necklace.set("speed",speed); // value from 0 to 1

		if(on_g) {
			filter(fx_bg_necklace);
		} else {
			pg_necklace_fx_bg.shader(fx_bg_necklace);
		}
	}

	if(on_g) {
		return null;
	} else {
		return pg_necklace_fx_bg; 
	}
}







/**
* Neon fx backgournd by Stan le punk
* this template can be used for texture or direct filtering
* v 0.0.1
* 2019-2019
*/
// setting by class FX
public PGraphics fx_bg_neon(FX fx) {
	return fx_bg_neon(fx.get_canvas(),fx.on_g(),vec2(fx.get_pos()),fx.get_speed().x);
}




// main
PShader fx_bg_neon;
PGraphics pg_neon_fx_bg;
public PGraphics fx_bg_neon(ivec2 canvas, boolean on_g, vec2 pos, float speed) {
	if(!on_g && (pg_neon_fx_bg == null
								|| (canvas.x != pg_neon_fx_bg.width 
								&& canvas.y != pg_neon_fx_bg.height))) {
		pg_neon_fx_bg = createGraphics(canvas.x,canvas.y,get_renderer());
	}
  // setting
	if(fx_bg_neon == null) {
		String path = get_fx_bg_path()+"neon.glsl";
		if(fx_bg_rope_path_exists) {
			fx_bg_neon = loadShader(path);
			println("load shader: neon.glsl");
		}
	} else {
		set_shader_resolution(fx_bg_neon,canvas,on_g);


		fx_bg_neon.set("time",frameCount *speed); 
    // external paramter
    fx_bg_neon.set("position",pos.x,pos.y); // value from 0 to 1

		if(on_g) {
			filter(fx_bg_neon);
		} else {
			pg_neon_fx_bg.shader(fx_bg_neon);
		}
	}

	if(on_g) {
		return null;
	} else {
		return pg_neon_fx_bg; 
	}
}












/**
* PSY fx backgournd by Stan le punk
* this template can be used for texture or direct filtering
* v 0.0.1
* 2019-2019
*/
// setting by class FX
public PGraphics fx_bg_psy(FX fx) {
	return fx_bg_psy(fx.get_canvas(),fx.on_g(),fx.get_num(),fx.get_speed().x);
}



// main
PShader fx_bg_psy;
PGraphics pg_psy_fx_bg;
public PGraphics fx_bg_psy(ivec2 canvas, boolean on_g, int num, float speed) {
	if(!on_g && (pg_psy_fx_bg == null
								|| (canvas.x != pg_psy_fx_bg.width 
								&& canvas.y != pg_psy_fx_bg.height))) {
		pg_psy_fx_bg = createGraphics(canvas.x,canvas.y,get_renderer());
	}
  // setting
	if(fx_bg_psy == null) {
		String path = get_fx_bg_path()+"psy.glsl";
		if(fx_bg_rope_path_exists) {
			fx_bg_psy = loadShader(path);
			println("load shader: psy.glsl");
		}
	} else {
		set_shader_resolution(fx_bg_psy,canvas,on_g);


		fx_bg_psy.set("time",frameCount *speed); // speed value must be low from 0.0001 to 0.05 and it's very fast
    // external paramter
    fx_bg_psy.set("num",num); // 2 or 3

		if(on_g) {
			filter(fx_bg_psy);
		} else {
			pg_psy_fx_bg.shader(fx_bg_psy);
		}
	}

	if(on_g) {
		return null;
	} else {
		return pg_psy_fx_bg; 
	}
}






/**
* SNOW fx backgournd by Stan le punk
* this template can be used for texture or direct filtering
* v 0.0.1
* 2019-2019
*/
// setting by class FX
public PGraphics fx_bg_snow(FX fx) {
	return fx_bg_snow(fx.get_canvas(),fx.on_g(),vec2(fx.get_pos()),vec3(fx.get_colour()),fx.get_speed().x,fx.get_quality());
}



// main
PShader fx_bg_snow;
PGraphics pg_snow_fx_bg;
public PGraphics fx_bg_snow(ivec2 canvas, boolean on_g, vec2 pos, vec3 colour, float speed, float quality) {
	if(!on_g && (pg_snow_fx_bg == null
								|| (canvas.x != pg_snow_fx_bg.width 
								&& canvas.y != pg_snow_fx_bg.height))) {
		pg_snow_fx_bg = createGraphics(canvas.x,canvas.y,get_renderer());
	}
  // setting
	if(fx_bg_snow == null) {
		String path = get_fx_bg_path()+"snow.glsl";
		if(fx_bg_rope_path_exists) {
			fx_bg_snow = loadShader(path);
			println("load shader: snow.glsl");
		}
	} else {
		set_shader_resolution(fx_bg_snow,canvas,on_g);

    fx_bg_snow.set("rgb",colour.x,colour.y,colour.z); // speed value must be low from 0.0001 to 0.05 and it's very fast
		fx_bg_snow.set("time",frameCount *speed); // speed value must be low from 0.0001 to 0.05 and it's very fast

		fx_bg_snow.set("position",pos.x,pos.y); // speed value must be low from 0.0001 to 0.05 and it's very fast

		fx_bg_snow.set("quality",quality); // speed value must be low from 0.0001 to 0.05 and it's very fast
    // external paramter


		if(on_g) {
			filter(fx_bg_snow);
		} else {
			pg_snow_fx_bg.shader(fx_bg_snow);
		}
	}

	if(on_g) {
		return null;
	} else {
		return pg_snow_fx_bg; 
	}
}















/**
* VORONOI HEX
* refactoring
* oroginal shader see glsl file for the link
* v 0.0.1
* 2019-2019
*/
// setting by class FX
public PGraphics fx_bg_voronoi_hex(FX fx) {
	return fx_bg_voronoi_hex(fx.get_canvas(),fx.on_g(),fx.get_size().x,vec3(fx.get_colour()),fx.get_speed().x,fx.get_speed().y,fx.get_strength().x,fx.get_threshold().x,fx.get_mode());
}


// main
PShader fx_bg_voronoi_hex;
PGraphics pg_voronoi_hex_fx_bg;
public PGraphics fx_bg_voronoi_hex(ivec2 canvas, boolean on_g, float size, vec3 colour, float speed_mutation,float speed_colour, float strength, float threshold, int mode) {
	if(!on_g && (pg_voronoi_hex_fx_bg == null
								|| (canvas.x != pg_voronoi_hex_fx_bg.width 
								&& canvas.y != pg_voronoi_hex_fx_bg.height))) {
		pg_voronoi_hex_fx_bg = createGraphics(canvas.x,canvas.y,get_renderer());
	}
  // setting
	if(fx_bg_voronoi_hex == null) {
		String path = get_fx_bg_path()+"voronoi_hex.glsl";
		if(fx_bg_rope_path_exists) {
			fx_bg_voronoi_hex = loadShader(path);
			println("load shader: voronoi_hex.glsl");
		}
	} else {
		set_shader_resolution(fx_bg_voronoi_hex,canvas,on_g);
		// processing parameter
		fx_bg_voronoi_hex.set("time",PApplet.parseFloat(frameCount)); 
    
    // external parameter
    fx_bg_voronoi_hex.set("rgb",colour.x,colour.y,colour.z); // from 0 to 1
    fx_bg_voronoi_hex.set("size",size); // from 1 to 10++
    fx_bg_voronoi_hex.set("speed_mutation",speed_mutation); // from 0 to 1
    fx_bg_voronoi_hex.set("speed_colour",speed_colour); // from 0 to 1
		fx_bg_voronoi_hex.set("strength",strength); // from -0.05 to 0.05
		fx_bg_voronoi_hex.set("threshold",threshold); // from 0.1 to 0.3
		fx_bg_voronoi_hex.set("mode",mode); // from 0 to 1

		if(on_g) {
			filter(fx_bg_voronoi_hex);
		} else {
			pg_voronoi_hex_fx_bg.shader(fx_bg_voronoi_hex);
		}
	}

	if(on_g) {
		return null;
	} else {
		return pg_voronoi_hex_fx_bg; 
	}
}
/**
 * CLASS FX 
 * v 0.4.4
 * @author @stanlepunk
 * @see https://github.com/StanLepunK/Shader
 * 2019-2019
 * Processing 3.5.3
 * Rope library 0.7.1
 * class used to create easy setting for shader fx
*/
public class FX {
	// processing parameter
	private int id;
	private int type;
	private String name;
	private ivec2 canvas;
	private String [] name_slider;
	private String [] name_button;
	private String author;
	private String pack;
	private String version;
	private int revision;
	private boolean on_g = true;
	private boolean pg_filter_is = true;


	// glsl parameter
	private int mode; // 0
	private int num; // 1 
	private float quality; // 2
	private float time; // 3

	private vec2 scale; // 10
	private vec2 resolution; // 11

	private vec3 strength; // 20
	private vec3 angle; // 21
	private vec3 threshold; // 22
	private vec3 pos; // 23
	private vec3 size; // 24
	private vec3 offset; // 25
	private vec3 speed; // 26

	private vec4 level_source; // 30
	private vec4 level_layer; // 31
	private vec4 colour; // 32
	private vec4 cardinal; // 33 > north, east, south, west > top, right, bottom, left

  private float hue; // 200
	private float saturation; // 201
	private float brightness; // 202

	private float red; // 300
	private float green; // 301
	private float blue; // 302

	private float alpha; // 400

	// modular
	private vec3 [] matrix; // 40 > 42
	private vec2 [] pair; // 50 > 42
	private bvec4 [] event; // 10O-102



  // CONSTRUCTOR
  public FX () {}

  // set
  public void set_canvas(int x, int y) {
  	if(this.canvas == null) {
  		this.canvas = ivec2(x,y);
  	} else {
  		this.canvas.set(x,y);
  	}
  }

  public void set_type(int type) {
  	this.type = type;
  }

  public void set_id(int id) {
  	this.id = id;
  }

  public void set_name(String name) {
  	this.name = name;
  }

  public void set_name_slider(String... name) {
  	name_slider = new String[name.length];
  	for(int i = 0 ; i < name_slider.length ; i++) {
  		this.name_slider[i] = name[i];
  	}
  }

  public void set_name_button(String... name) {
  	name_button = new String[name.length];
  	for(int i = 0 ; i < name_button.length ; i++) {
  		this.name_button[i] = name[i];
  	}
  }

  public void set_author(String author) {
  	this.author = author;
  }

  public void set_pack(String pack) {
  	this.pack = pack;
  }

  public void set_version(String version) {
  	this.version = version;
  }

  public void set_revision(int revision) {
  	this.revision = revision;
  }

  public void set(int which, Object... arg) {
  	if(which == 0) {
  		set_mode((int)arg[0]);
  	} else if(which == 1) {
  		set_num((int)arg[0]);
  	} else if(which == 2) {
  		set_quality((float)arg[0]);
  	} else if(which == 3) {
  		set_time((float)arg[0]);
  	} else if(which == 4) {
  		set_on_g((boolean)arg[0]);
  	} else if(which == 5) {
  		set_pg_filter((boolean)arg[0]);
  	}

  		else if(which == 10) {
  		set_scale(to_float_array(arg));
  	} else if(which == 11) {
  		set_resolution(to_float_array(arg));
  	}

  		else if(which == 20) {
  		set_strength(to_float_array(arg));
  	} else if(which == 21) {
  		set_angle(to_float_array(arg));
  	} else if(which == 22) {
  		set_threshold(to_float_array(arg));
  	} else if(which == 23) {
  		set_pos(to_float_array(arg));
  	} else if(which == 24) {
  		set_size(to_float_array(arg));
  	} else if(which == 25) {
  		set_offset(to_float_array(arg));
  	} else if(which == 26) {
  		set_speed(to_float_array(arg));
  	}

  		else if(which == 30) {
  		set_level_source(to_float_array(arg));
  	} else if(which == 31) {
  		set_level_layer(to_float_array(arg));
  	} else if(which == 32) {
  		set_colour(to_float_array(arg));
  	} else if(which == 33) {
  		set_cardinal(to_float_array(arg));
  	}

  		else if(which == 40) {
  		if(matrix == null || matrix.length < 1) matrix = new vec3[1];
  		set_matrix(0,to_float_array(arg));
  	} else if(which == 41) {
  		if(matrix == null || matrix.length < 2) matrix = new vec3[2];
  		set_matrix(1,to_float_array(arg));
  	} else if(which == 42) {
  		if(matrix == null || matrix.length < 3) matrix = new vec3[3];
  		set_matrix(2,to_float_array(arg));
  	}	

  	else if(which == 50) {
  		if(pair == null || pair.length < 1) pair = new vec2[1];
  		set_pair(0,to_float_array(arg));
  	} else if(which == 51) {
  		if(pair == null || pair.length < 2) pair = new vec2[2];
  		set_pair(1,to_float_array(arg));
  	} else if(which == 52) {
  		if(pair == null || pair.length < 3) pair = new vec2[3];
  		set_pair(2,to_float_array(arg));
  	}	

  		else if(which == 100) {
  		if(event == null || event.length < 1) event = new bvec4[1];
  		set_event(0,to_boolean_array(arg));
  	} else if(which == 101) {
  		if(event == null || event.length < 2) event = new bvec4[2];
  		set_event(1,to_boolean_array(arg));
  	} else if(which == 102) {
  		if(event == null || event.length < 3) event = new bvec4[3];
  		set_event(2,to_boolean_array(arg));
  	}
  }

  private float[] to_float_array(Object... arg) {
  	float [] f = new float[arg.length];
  	for(int i = 0 ; i < arg.length ; i++) {
  		if(arg[i] instanceof Float) {
  			f[i] = (float)arg[i];
  		} else {
  			printErr("class FX method to_float_array(): arg",arg,"cannot be cast to float");
  			f[i] = 0;
  		}
  	}
  	return f;
  }


  private boolean[] to_boolean_array(Object... arg) {
  	boolean [] b = new boolean[arg.length];
  	for(int i = 0 ; i < arg.length ; i++) {
  		if(arg[i] instanceof Boolean) {
  			b[i] = (boolean)arg[i];
  		} else {
  			printErr("class FX method to_boolean_array(): arg",arg,"cannot be cast to boolean");
  			b[i] = false;
  		}
  	}
  	return b;
  }



  public void set_on_g(boolean is) {
  	on_g = is;
  }

  public void set_pg_filter(boolean is) {
  	pg_filter_is = is;
  }


  private void set_mode(int mode) {
		this.mode = mode;
	}

	private void set_num(int num) {
		this.num = num;
	}

	private void set_quality(float quality) {
		this.quality = quality;
	}

	private void set_time(float time) {
		this.time = time;
	}

	private void set_scale(float... arg) {
		if(this.scale == null) {
			this.scale = vec2(build_float_2(arg));
		} else {
			this.scale.set(build_float_2(arg));
		}
	}

	private void set_resolution(float... arg) {
		if(this.resolution == null) {
			this.resolution = vec2(build_float_2(arg));
		} else {
			this.resolution.set(build_float_2(arg));
		}
	}

	private void set_strength(float... arg) {
		if(this.strength == null) {
			this.strength = vec3(build_float_3(arg));
		} else {
			this.strength.set(build_float_3(arg));
		}
	}

	private void set_angle(float... arg) {
		if(this.angle == null) {
			this.angle = vec3(build_float_3(arg));
		} else {
			this.angle.set(build_float_3(arg));
		}
	}

	private void set_threshold(float... arg) {
		if(this.threshold == null) {
			this.threshold = vec3(build_float_3(arg));
		} else {
			this.threshold.set(build_float_3(arg));
		}
	}

	private void set_pos(float... arg) {
		if(this.pos == null) {
			this.pos = vec3(build_float_3(arg));
		} else {
			this.pos.set(build_float_3(arg));
		}
	}

	private void set_size(float... arg) {
		if(this.size == null) {
			this.size = vec3(build_float_3(arg));
		} else {
			this.size.set(build_float_3(arg));
		}
	}

	private void set_offset(float... arg) {
		if(this.offset == null) {
			this.offset = vec3(build_float_3(arg));
		} else {
			this.offset.set(build_float_3(arg));
		}
	}

	private void set_speed(float... arg) {
		if(this.speed == null) {
			this.speed = vec3(build_float_3(arg));
		} else {
			this.speed.set(build_float_3(arg));
		}
	}

	private void set_level_source(float... arg) {
		if(this.level_source == null) {
			this.level_source = vec4(build_float_4(arg));
		} else {
			this.level_source.set(build_float_4(arg));
		}
	}

	private void set_level_layer(float... arg) {
		if(this.level_layer == null) {
			this.level_layer = vec4(build_float_4(arg));
		} else {
			this.level_layer.set(build_float_4(arg));
		}
	}

	private void set_colour(float... arg) {
		if(this.colour == null) {
			this.colour = vec4(build_float_4(arg));
		} else {
			this.colour.set(build_float_4(arg));
		}
	}

	private void set_cardinal(float... arg) {
		if(this.cardinal == null) {
			this.cardinal = vec4(build_float_4(arg));
		} else {
			this.cardinal.set(build_float_4(arg));
		}
	}

	private void set_hue(float hue) {
		this.hue = hue;
	}

	private void set_saturation(float saturation) {
		this.saturation = saturation;
	}

	private void set_brightness(float brightness) {
		this.brightness = brightness;
	}

	private void set_red(float red) {
		this.red = red;
	}

	private void set_green(float green) {
		this.green = green;
	}

	private void set_blue(float blue) {
		this.blue = blue;
	}

	private void set_alpha(float alpha) {
		this.alpha = alpha;
	}

	private void set_matrix(int which, float... arg) {
		if(this.matrix[which] == null) {
			this.matrix[which] = vec3(build_float_3(arg));
		} else {
			this.matrix[which].set(build_float_3(arg));
		}
	}

	private void set_pair(int which, float... arg) {
		if(this.pair[which] == null) {
			this.pair[which] = vec2(build_float_2(arg));
		} else {
			this.pair[which].set(build_float_2(arg));
		}
	}

	private void set_event(int which, boolean... arg) {
		if(this.event[which] == null) {
			this.event[which] = bvec4(build_boolean_4(arg));
		} else {
			this.event[which].set(build_boolean_4(arg));
		}
	}



	// get
	public boolean on_g() {
		return on_g;
	}

	public boolean pg_filter_is() {
  	return pg_filter_is;
  }

	public String get_name() {
		return name;
	}

	public ivec2 get_canvas() {
		return this.canvas;
	}

	public String [] get_name_slider() {
  	return name_slider;
  }

  public String [] get_name_button() {
  	return name_button;
  }

	public int get_id() {
		return id;
	}

	public String get_author() {
  	 return author;
  }

  public String get_pack() {
  	return pack;
  }

  public String get_version() {
  	return version;
  }

  public  int get_revision() {
  	return revision;
  }

	public int get_type() {
		return type;
	}

	public int get_mode() {
		return mode;
	}

	public int get_num() {
		return num;
	}

	public float get_quality() {
		return quality;
	}

	public float get_time() {
		return time;
	}

	public vec2 get_scale() {
		if(scale == null) {
			scale = vec2(1);
			printErr("class FX method get_scale(): arg",null,"instead set arg and return",scale);
		} 
		return scale;	
	}

	public vec2 get_resolution() {
		if(resolution == null) {
			resolution = vec2(width,height);
			printErr("class FX method get_resolution(): arg",null,"instead set arg and return",resolution);
		} 
		return resolution;
	}

	public vec3 get_strength() {
		if(strength == null) {
			strength = vec3(0);
			printErr("class FX method get_strength(): arg",null,"instead set arg and return",strength);
		}
		return strength;
	}

	public vec3 get_angle() {
		if(angle == null) {
			angle = vec3(0);
			printErr("class FX method get_angle(): arg",null,"instead set arg and return",angle);
		}
		return angle;
	}

	public vec3 get_threshold() {
		if(threshold == null) {
			threshold = vec3(0);
			printErr("class FX method get_threshold(): arg",null,"instead set arg and return",threshold);
		}
		return threshold;
	}

	public vec3 get_pos() {
		if(pos == null) {
			pos = vec3(width/2,height/2,0);
			printErr("class FX method get_pos(): arg",null,"instead set arg and return",pos);
		}
		return pos;
	}

	public vec3 get_size() {
		if(size == null) {
			size = vec3(5);
			printErr("class FX method get_size(): arg",null,"instead set arg and return",size);
		}
		return size;
	}

	public vec3 get_offset() {
		if(offset == null) {
			offset = vec3(0);
			printErr("class FX method get_offset(): arg",null,"instead set arg and return",offset);
		}
		return offset;
	}

	public vec3 get_speed() {
		if(speed == null) {
			speed = vec3(0);
			printErr("class FX method get_offset(): arg",null,"instead set arg and return",speed);
		}
		return speed;
	}

	public vec4 get_level_source() {
		if(level_source == null) {
			level_source = vec4(1);
			printErr("class FX method get_level_source(): arg",null,"instead set arg and return",level_source);
		}
		return level_source;
	}

	public vec4 get_level_layer() {
		if(level_layer == null) {
			level_layer = vec4(1);
			printErr("class FX method get_level_layer(): arg",null,"instead set arg and return",level_layer);
		}
		return level_layer;
	}

	public vec4 get_colour() {
		if(colour == null) {
			colour = vec4(1);
			printErr("class FX method get_colour(): arg",null,"instead set arg and return",colour);
		}
		return colour;
	}

	public vec4 get_cardinal() {
		if(cardinal == null) {
			cardinal = vec4(1);
			printErr("class FX method get_cardinal(): arg",null,"instead set arg and return",cardinal);
		}
		return cardinal;
	}

	public float get_hue() {
		return hue;
	}

	public float get_saturation() {
		return saturation;
	}

	public float get_brightness() {
		return brightness;
	}

	public float get_red() {
		return red;
	}

	public float get_green() {
		return green;
	}

	public float get_blue() {
		return blue;
	}

	public float get_alpha() {
		return alpha;
	}   

  // matrix
	public vec3 get_matrix(int which) {
		if(matrix != null  && which < matrix.length && which >= 0) {
			if(matrix[which] == null) {
				matrix[which] = vec3(0);
				printErr("class FX method get_matrix(): arg",null,"instead set arg and return",matrix[which]);
			}
			return matrix[which];
		} else if(matrix == null) {
			printErrTempo(180,"class FX method get_matrix(): matrix list is",null);
			return vec3(0);
		} else {
			if(matrix[0] == null) {
				matrix[0] = vec3(0);
				printErr("class FX method get_matrix(",which,") is out of the list available\nthe first component is used and not this is null too\nmatrix '0' is used");
				return matrix[0];
			} else {
				printErr("class FX method get_matrix(",which,") is out of the list available\nthe first component is used");
				return matrix[0];
			}
		}
	}

	public vec3 [] get_matrix() {
		if(matrix != null) {
			return matrix;
		} else return null;
	}
  
  // pair
	public vec2 get_pair(int which) {
		if(pair != null && which < pair.length && which >= 0) {
			if(pair[which] == null) {
				pair[which] = vec2(0);
				printErr("class FX method get_pair(): arg",null,"instead set arg and return",pair[which]);
			}
			return pair[which];
		} else if(pair == null)  {
			printErrTempo(180,"class FX method get_pair(): pair list is",null);
			return vec2(0);
		} else {
			if(pair[0] == null) {
				pair[0] = vec2(0);
				printErr("class FX method get_pair(",which,") is out of the list available\nthe first component is used and not this is null too\npair double '0' is used");
				return pair[0];
			} else {
				printErr("class FX method get_pair(",which,") is out of the list available\nthe first component is used");
				return pair[0];
			}
		}
	}

	public vec2 [] get_pair() {
		if(pair != null) {
			return pair;
		} else return null;
	}
  
  // event
  public bvec4 get_event(int which) {
		if(event != null && which < event.length  && which >= 0) {
			return event[which];
		} else if(event == null) {
			printErrTempo(180,"class FX method get_event(): event list is null\n bvec false is return");
			return bvec4(false);
		} else {
			if(event[0] == null) {
				event[0] = bvec4(false);
				printErr("class FX method get_event(",which,") is out of the list available\nthe first component is used and not this is null too\nevent 'false is used");
				return event[0];
			} else {
				printErr("class FX method get_event(",which,") is out of the list available\nthe first component is used");
				return event[0];
			}
		}
	}

	public bvec4 [] get_event() {
		if(event != null) {
			return event;
		} else {
			return null;
		}
	}

	// util
	private bvec4 build_boolean_4(boolean... arg) {
		if(arg.length == 1 ) {
			return bvec4(arg[0],false,false,false);
		} else if(arg.length == 2) {
			return bvec4(arg[0],arg[1],false,false);
		} else if(arg.length == 3) {
			return bvec4(arg[0],arg[1],arg[2],false);
		} else if(arg.length == 4) {
			return bvec4(arg[0],arg[1],arg[2],arg[3]);
		} else {
			return bvec4(false);
		}
	}


	private vec4 build_float_4(float... arg) {
		if(arg.length == 1 ) {
			return vec4(arg[0],arg[0],arg[0],g.colorModeA);
		} else if(arg.length == 2) {
			return vec4(arg[0],arg[0],arg[0],arg[1]);
		} else if(arg.length == 3) {
			return vec4(arg[0],arg[1],arg[2],g.colorModeA);
		} else if(arg.length == 4) {
			return vec4(arg[0],arg[1],arg[2],arg[3]);
		} else {
			return vec4(g.colorModeX,g.colorModeY,g.colorModeZ,g.colorModeA);
		}
	}

	private vec3 build_float_3(float... arg) {
		if(arg.length == 1 ) {
			return vec3(arg[0],arg[0],arg[0]);
		} else if(arg.length == 2) {
			return vec3(arg[0],arg[1],0);
		} else if(arg.length == 3) {
			return vec3(arg[0],arg[1],arg[2]);
		} else {
			return vec3(0);
		}
	}

	private vec2 build_float_2(float... arg) {
		if(arg.length == 1 ) {
			return vec2(arg[0],arg[0]);
		} else if(arg.length == 2) {
			return vec2(arg[0],arg[1]);
		} else {
			return vec2(0);
		}
	}
}
/**
* POST FX shader collection
*
* 2019-2019
* v 0.2.13
* all filter bellow has been tested.
* @author @stanlepunk
* @see https://github.com/StanLepunK/Shader
*/


/**
* Template by Stan le punk
* this template can be used for texture or direct filtering
v 0.2.2
2018-2019
*/
// setting by class FX
public PGraphics fx_template(PImage source, FX fx) {
	return fx_template(source,fx.on_g(),fx.pg_filter_is(),null);
}

// main
PShader fx_template;
PGraphics pg_template;
public PGraphics fx_template(PImage source, boolean on_g, boolean filter_is, vec4 level_source) {
	if(!on_g && (pg_template == null 
								|| (source.width != pg_template.width 
								|| source.height != pg_template.height))) {
		pg_template = createGraphics(source.width,source.height,get_renderer());
	}

	if(fx_template == null) {
		String path = get_fx_post_path()+"template_fx_post.glsl";
		if(fx_post_rope_path_exists) {
			fx_template = loadShader(path);
			println("load shader: template_fx_post.glsl");
		}
		println("load shader:",path);
	} else {
		fx_shader_flip(fx_template,on_g,filter_is,source,null);

		fx_template.set("texture_source",source);
		fx_template.set("resolution",(float)source.width,(float)source.height);

  
    // fx_template.set("color_mode",3); // mode 0 RGB / mode 3 HSB

    // fx_template.set("hue",cx); // value from 0 to 1
		fx_template.set("level_source",level_source.x,level_source.y,level_source.w,level_source.z); // value from 0 to 1
		// fx_template.set("level_source",1,1,1,1.); // value from 0 to 1

    // rendering
		render_shader(fx_template,pg_template,source,on_g,filter_is);

	}

	// return
	reset_reverse_g(false);
	if(on_g) {
		return null;
	} else {
		return pg_template; 
	}
}



















/**
* Antialiasing FXAA by Stan le punk
* v 0.0.2
* 2019-2019
*/
// setting by class FX
public PGraphics fx_fxaa(PImage source, FX fx) {
	return fx_fxaa(source,fx.on_g(),fx.pg_filter_is(), fx.get_pair(0).x(), fx.get_pair(0).y());
}

// main
PShader fx_fxaa;
PGraphics pg_fxaa;
public PGraphics fx_fxaa(PImage source, boolean on_g, boolean filter_is, float sub_pix_cap, float sub_pix_trim) {
	if(!on_g && (pg_fxaa == null 
								|| (source.width != pg_fxaa.width 
								|| source.height != pg_fxaa.height))) {
		pg_fxaa = createGraphics(source.width,source.height,get_renderer());
	}

	if(fx_fxaa == null) {
		String path = get_fx_post_path()+"AA_FXAA.glsl";
		if(fx_post_rope_path_exists) {
			fx_fxaa = loadShader(path);
			println("load shader: AA_FXAA.glsl");
		}
		println("load shader:",path);
	} else {
		fx_shader_flip(fx_fxaa,on_g,filter_is,source,null);

		fx_fxaa.set("texture_source",source);
		fx_fxaa.set("resolution_source",(float)source.width,(float)source.height);

		
		float edge_threshold = 0.8f; // nothing happen, but after 0.8 the effect is kill
		fx_fxaa.set("edge_threshold",edge_threshold);

		float edge_threshold_min = 0.5f; // nothing happen
		fx_fxaa.set("edge_threshold_min",edge_threshold_min);

		// search
		int search_steps = 8; // nothing happen
		fx_fxaa.set("search_steps",search_steps);

		float search_threshold = 0.5f; // nothing happen from 0 to 1
		fx_fxaa.set("search_threshold",search_threshold);

		// sub
		// float sub_pix_cap = 0.75 ; // something happen from 0 to 1
		fx_fxaa.set("sub_pix_cap",sub_pix_cap);

		//float sub_pix_trim = -0.5; //something happen from -1 to 1 
		fx_fxaa.set("sub_pix_trim",sub_pix_trim);



    // rendering
		render_shader(fx_fxaa,pg_fxaa,source,on_g,filter_is);
	}
	// return
	reset_reverse_g(false);
	if(on_g) {
		return null;
	} else {
		return pg_fxaa; 
	}
}

































/**
* Blur circular
* v 0.2.2
* 2018-2019
*/
// use fx setting
public PGraphics fx_blur_circular(PImage source, FX fx) {
	return fx_blur_circular(source,fx.on_g(),fx.pg_filter_is(),fx.get_strength(),fx.get_num());
}

// main 
PShader fx_blur_circular;
PGraphics pg_blur_circular;
public PGraphics fx_blur_circular(PImage source, boolean on_g, boolean filter_is, vec3 strength, int num) {
	if(!on_g && (pg_blur_circular == null || (source.width != pg_blur_circular.width 
																				|| source.height != pg_blur_circular.height))) {
		pg_blur_circular = createGraphics(source.width,source.height,get_renderer());
	}
	
	if(fx_blur_circular == null) {
		String path = get_fx_post_path()+"blur_circular.glsl";
		if(fx_post_rope_path_exists) {
			fx_blur_circular = loadShader(path);
			println("load shader:",path);
		}
	} else {
		fx_shader_flip(fx_blur_circular,on_g,filter_is,source,null);

		fx_blur_circular.set("texture_source",source);
		fx_blur_circular.set("resolution_source",source.width,source.height);
		fx_blur_circular.set("resolution",source.width,source.height);
    
    // external variable
		if(strength != null) fx_blur_circular.set("strength",strength.x);
		fx_blur_circular.set("num",num); 

		render_shader(fx_blur_circular,pg_blur_circular,source,on_g,filter_is);
	}
	// end
	reset_reverse_g(false);
	if(on_g) {
		return null;	
	} else {
		return pg_blur_circular; 
	}
}








/**
* gaussian blur
* v 0.2.5
* 2018-2019
*/
// setting by class FX
public PGraphics fx_blur_gaussian(PImage source, FX fx) {
	ivec2 res = ivec2();
	boolean second_pass = true;
	return fx_blur_gaussian(source,fx.on_g(),fx.pg_filter_is(),second_pass,res,fx.get_strength().x());
}

// main
PShader fx_blur_gaussian;
PGraphics pg_blur_gaussian;
public PGraphics fx_blur_gaussian(PImage source, boolean on_g, boolean filter_is, boolean second_pass, ivec2 resolution, float strength) {
	if(!on_g && (pg_blur_gaussian == null || (source.width != pg_blur_gaussian.width 
																				|| source.height != pg_blur_gaussian.height))) {
		pg_blur_gaussian = createGraphics(source.width,source.height,get_renderer());
	}
  
  if(pg_blur_gaussian == null) {
  	// security, because that's return problem consol with too much waring message for PImage source
  	if(resolution != null && !all(equal(ivec2(-1),resolution))) {
  		pg_blur_gaussian = fx_image(source,false,filter_is,null,null,null,null,SCREEN);
  	}
  } else {
  	if(resolution != null && !all(equal(ivec2(pg_blur_gaussian),resolution))) {
  		pg_blur_gaussian = fx_image(source,false,filter_is,null,null,null,null,SCREEN);
  	}
  }


	PGraphics pg;
  PGraphics pg_2;

	if(resolution == null) {
  	pg = createGraphics(pg_blur_gaussian.width,pg_blur_gaussian.height,get_renderer());
  	pg_2 = createGraphics(pg_blur_gaussian.width,pg_blur_gaussian.height,get_renderer());
  } else {
  	int min_res = 10;
  	if(any(lessThanEqual(resolution,ivec2(min_res)))) {
  		resolution.set(pg_blur_gaussian.width,pg_blur_gaussian.height);
  	}
  	pg = createGraphics(resolution.x,resolution.y,get_renderer());
  	pg_2 = createGraphics(resolution.x,resolution.y,get_renderer());
  }

	
	if(fx_blur_gaussian == null) {
		String path = get_fx_post_path()+"blur_gaussian.glsl";
		if(fx_post_rope_path_exists) {
			fx_blur_gaussian = loadShader(path);
			println("load shader:",path);
		}
	} else {
		// flip part, for the case it's a texture, PImage or PGraphics
		fx_blur_gaussian.set("texture_source",source);
		if(on_g) {
			fx_blur_gaussian.set("flip_source",true,false);
			if(graphics_is(source).equals("PGraphics") && !reverse_g_source_is()) {
				fx_blur_gaussian.set("flip_source",false,false);
				reverse_g_source(true);
			} 
		}


		if(resolution != null) {
			fx_blur_gaussian.set("resolution",resolution.x,resolution.y);
			fx_blur_gaussian.set("resolution_source",resolution.x,resolution.y);
		}


		// external parameter
		if(strength <= 0.001f) strength = 0.001f;
		fx_blur_gaussian.set("size",strength); // from 1 to nowhere
		fx_blur_gaussian.set("sigma",.5f); // better between 0 and 1
		fx_blur_gaussian.set("horizontal_pass",true);
	  


	  // rendering 
	  if(!on_g) {
	  	pg.beginDraw();            
	    pg.shader(fx_blur_gaussian);
	    pg.image(pg_blur_gaussian,0,0); 
	    pg.endDraw();

	    // Applying the blur shader along the horizontal direction   
	    if(second_pass) {
	    	fx_blur_gaussian.set("texture_source",pg);
	    	fx_blur_gaussian.set("horizontal_pass",false); 	
	    	pg_2.beginDraw();            
	    	pg_2.shader(fx_blur_gaussian);  
	    	pg_2.image(pg,0,0);
	    	pg_2.endDraw(); 	
	    }
	  } else if(on_g) {
	  	g.filter(fx_blur_gaussian);
	  	// Applying the blur shader along the horizontal direction   
	  	if(second_pass) {
	  		fx_blur_gaussian.set("texture_source",g);
	    	fx_blur_gaussian.set("horizontal_pass",false);
	    	g.filter(fx_blur_gaussian);
	  	}
	  }  
	}
	
  // end
  reset_reverse_g(false);
	if(on_g) {
		return null;
	} else {
		if(second_pass) {
			return pg_2; 
		} else {
			return pg;
		}
		
	}
}

















/**
* Blur radial
v 0.3.2
2018-2019
*/
// setting by class FX
public PGraphics fx_blur_radial(PImage source, FX fx) {
	float str = 0;
	if(fx.get_strength() != null) {
		str = fx.get_strength().x();
	}

	float scl = 0;
	if(fx.get_scale() != null) {
		scl = fx.get_scale().x();
	}
	return fx_blur_radial(source,fx.on_g(),fx.pg_filter_is(),vec2(fx.get_pos()),str,scl);
}

// main
PShader fx_blur_radial;
PGraphics pg_blur_radial;
public PGraphics fx_blur_radial(PImage source, boolean on_g, boolean filter_is, vec2 pos, float strength, float scale) {
	if(!on_g && (pg_blur_radial == null 
								|| (source.width != pg_blur_radial.width 
								|| source.height != pg_blur_radial.height))) {
		pg_blur_radial = createGraphics(source.width,source.height,get_renderer());
	}
	
	if(fx_blur_radial == null) {
		String path = get_fx_post_path()+"blur_radial.glsl";
		if(fx_post_rope_path_exists) {
			fx_blur_radial = loadShader(path);
			println("load shader:",path);
		}
	} else {
		fx_shader_flip(fx_blur_radial,on_g,filter_is,source,null);

		fx_blur_radial.set("texture_source",source);

    // external parameter
		fx_blur_radial.set("scale",scale); // from 0 to beyond but good around .9;
		fx_blur_radial.set("strength",strength);
		if(pos != null) fx_blur_radial.set("position",pos.x,pos.y);
		
		render_shader(fx_blur_radial,pg_blur_radial,source,on_g,filter_is);
	}
	
	// return
	reset_reverse_g(false);
	if(on_g) {
		return null;
	} else {
		return pg_blur_radial; 
	}
}












/**
* Colour change A by Stan le punk
v 0.3.2
2018-2019
*/
// setting by class FX
public PGraphics fx_colour_change_a(PImage source, FX fx) {
	return fx_colour_change_a(source,fx.on_g(),fx.pg_filter_is(),fx.get_num(),fx.get_matrix());
}

// main
PShader fx_colour_change_a;
PGraphics pg_colour_change_a;
public PGraphics fx_colour_change_a(PImage source, boolean on_g, boolean filter_is, int num, vec3... mat) {
	if(!on_g && (pg_colour_change_a == null 
								|| (source.width != pg_colour_change_a.width 
								|| source.height != pg_colour_change_a.height))) {
		pg_colour_change_a = createGraphics(source.width,source.height,get_renderer());
	}
	
	if(fx_colour_change_a == null) {
		String path = get_fx_post_path()+"colour_change_A.glsl";
		if(fx_post_rope_path_exists) {
			fx_colour_change_a = loadShader(path);
			println("load shader:",path);
		}
	} else {
		fx_shader_flip(fx_colour_change_a,on_g,filter_is,source,null);

		fx_colour_change_a.set("texture_source",source);

		// external param
		if(mat != null && mat.length == 1) {
			if(mat[0] != null) fx_colour_change_a.set("mat_col_0",mat[0].x,mat[0].y,mat[0].z);
		} else if(mat != null && mat.length == 2) {
			if(mat[0] != null) fx_colour_change_a.set("mat_col_0",mat[0].x,mat[0].y,mat[0].z);
			if(mat[1] != null) fx_colour_change_a.set("mat_col_1",mat[1].x,mat[1].y,mat[1].z);
		} else if(mat != null && mat.length > 2) {
			if(mat[0] != null) fx_colour_change_a.set("mat_col_0",mat[0].x,mat[0].y,mat[0].z);
			if(mat[1] != null) fx_colour_change_a.set("mat_col_1",mat[1].x,mat[1].y,mat[1].z);
			if(mat[2] != null) fx_colour_change_a.set("mat_col_2",mat[2].x,mat[2].y,mat[2].z);
		}

		fx_colour_change_a.set("num",num);

		render_shader(fx_colour_change_a,pg_colour_change_a,source,on_g,filter_is);

	}
	
	// return
	reset_reverse_g(false);
	if(on_g) {
		return null;	
	} else {
		return pg_colour_change_a; 
	}
}













/**
* colour change B
* v 0.1.2
* 2018-2019
*/

// setting by class FX
public PGraphics fx_colour_change_b(PImage source, FX fx) {
	float angle = 0;
	if(fx.get_angle() != null) {
		angle = fx.get_angle().x;
	}

	float strength = 0;
	if(fx.get_strength() != null) {
		strength = fx.get_strength().x;
	}
	return fx_colour_change_b(source,fx.on_g(),fx.pg_filter_is(),angle,strength);
}

PShader fx_colour_change_b;
PGraphics pg_colour_change_b;
public PGraphics fx_colour_change_b(PImage source, boolean on_g, boolean filter_is, float angle, float strength) {
	if(!on_g && (pg_colour_change_b == null 
								|| (source.width != pg_colour_change_b.width 
								|| source.height != pg_colour_change_b.height))) {
		pg_colour_change_b = createGraphics(source.width,source.height,get_renderer());
	}

	if(fx_colour_change_b == null) {
		String path = get_fx_post_path()+"colour_change_B.glsl";
		if(fx_post_rope_path_exists) {
			fx_colour_change_b = loadShader(path);
			println("load shader:",path);
		}
	} else {
		fx_shader_flip(fx_colour_change_b,on_g,filter_is,source,null);

		fx_colour_change_b.set("texture_source",source);
		fx_colour_change_b.set("resolution_source",source.width,source.height);
		
		// external parameter
    fx_colour_change_b.set("angle",angle); 
    fx_colour_change_b.set("strength",1.f);

    // rendering
		render_shader(fx_colour_change_b,pg_colour_change_b,source,on_g,filter_is);
	}

	// return
	reset_reverse_g(false);
	if(on_g) {
		return null;
	} else {
		return pg_colour_change_b; 
	}
}























/**
* Datamosh inpired by an algorithm of Alexandre Rivaux 
* @see https://github.com/alexr4/datamoshing-GLSL
* v 0.0.4
*2019-2019
*/
// setting by class FX
public PGraphics fx_datamosh(PImage source, FX fx) {
	return fx_datamosh(source,fx.on_g(),fx.pg_filter_is(),fx.get_threshold().x(),fx.get_strength().x(),fx.get_pair(0),fx.get_pair(1),fx.get_pair(2));
}

// main
PShader fx_datamosh;
PShader fx_flip_datamosh;
PGraphics pg_datamosh;
public PGraphics fx_datamosh(PImage source, boolean on_g, boolean filter_is, float threshold, float strength, vec2 offset_red, vec2 offset_green, vec2 offset_blue) {
	if(!on_g && (pg_datamosh == null 
								|| (source.width != pg_datamosh.width 
								|| source.height != pg_datamosh.height))) {
		pg_datamosh = createGraphics(source.width,source.height,get_renderer());
	}

	if(fx_datamosh == null) {
		// main glsl
		String path = get_fx_post_path()+"datamosh.glsl";
		if(fx_post_rope_path_exists) {
			fx_datamosh = loadShader(path);
			println("load shader: datamosh.glsl");
		}
		println("load shader:",path);
		// flip glsl
		path = get_fx_post_path()+"flip.glsl";
		if(fx_post_rope_path_exists) {
			fx_flip_datamosh = loadShader(path);
			println("load shader: flip.glsl");
		}
		println("load shader:",path);
	} else {
		fx_shader_flip(fx_datamosh,on_g,filter_is,source,null);

		fx_datamosh.set("resolution",(float)source.width,(float)source.height);
		fx_datamosh.set("resolution_source",(float)source.width,(float)source.height);

    fx_datamosh.set("texture",source);
    
    fx_datamosh.set("strength",strength); // value from -infinite to infinite 
		fx_datamosh.set("threshold",threshold); // value from 0 to 1

		if(offset_red != null) {
			fx_datamosh.set("offset_red",offset_red.x(),offset_red.y());
		}

		if(offset_green != null) {
			fx_datamosh.set("offset_green",offset_green.x(),offset_green.y());
		}
		
		if(offset_blue != null) {
			fx_datamosh.set("offset_blue",offset_blue.x(),offset_blue.y());
		} 

		if(pg_datamosh == null) {
			pg_datamosh = createGraphics(source.width,source.height,get_renderer());
		} else {
			fx_datamosh.set("texture_layer",pg_datamosh);
		}
		if(pg_datamosh.width > 0 && pg_datamosh.height > 0) {
			pg_datamosh.beginDraw();
			pg_datamosh.shader(fx_datamosh);
			pg_datamosh.image(source,0,0);
			pg_datamosh.endDraw();
		}
	}

	// return
	reset_reverse_g(false);
	if(on_g) {
		background(pg_datamosh,CENTER);
		fx_flip_datamosh.set("texture_source",g);
		fx_flip_datamosh.set("resolution_source",width,height);
		fx_flip_datamosh.set("flip_source",1,1);
		filter(fx_flip_datamosh);
		return null;
	} else {
		return pg_datamosh; 
	}
}












/**
* Derivative by Stan le punk
* v 0.0.1
* 2019-2019
*/
// setting by class FX
public PGraphics fx_derivative(PImage source, FX fx) {
	return fx_derivative(source,fx.on_g(),fx.pg_filter_is());
}

// main
PShader fx_derivative;
PGraphics pg_derivative;
public PGraphics fx_derivative(PImage source, boolean on_g, boolean filter_is) {
	if(!on_g && (pg_derivative == null 
								|| (source.width != pg_derivative.width 
								|| source.height != pg_derivative.height))) {
		pg_derivative = createGraphics(source.width,source.height,get_renderer());
	}

	if(fx_derivative == null) {
		String path = get_fx_post_path()+"derivative.glsl";
		if(fx_post_rope_path_exists) {
			fx_derivative = loadShader(path);
			println("load shader: derivative.glsl");
		}
		println("load shader:",path);
	} else {
		fx_shader_flip(fx_derivative,on_g,filter_is,source,null);

		fx_derivative.set("texture_source",source);
		fx_derivative.set("resolution_source",(float)source.width,(float)source.height);

		fx_derivative.set("time",frameCount); // value from 0 to 1

    // rendering
		render_shader(fx_derivative,pg_derivative,source,on_g,filter_is);

	}

	// return
	reset_reverse_g(false);
	if(on_g) {
		return null;
	} else {
		return pg_derivative; 
	}
}





























/**
* Dither bayer 8
* v 0.3.2
* 2018-2019
*/
// setting by class FX
public PGraphics fx_dither_bayer_8(PImage source, FX fx) {
	return fx_dither_bayer_8(source,fx.on_g(),fx.pg_filter_is(),vec3(fx.get_level_source()),fx.get_mode());	
}

// main
PShader fx_dither_bayer_8;
PGraphics pg_dither_bayer_8;
public PGraphics fx_dither_bayer_8(PImage source, boolean on_g, boolean filter_is, vec3 level, int mode) {
	if(!on_g && (pg_dither_bayer_8 == null 
								|| (source.width != pg_dither_bayer_8.width 
								|| source.height != pg_dither_bayer_8.height))) {
		pg_dither_bayer_8 = createGraphics(source.width,source.height,get_renderer());
	}

	
	if(fx_dither_bayer_8 == null) {
		String path = get_fx_post_path()+"dither_bayer_8.glsl";
		if(fx_post_rope_path_exists) {
			fx_dither_bayer_8 = loadShader(path);
			println("load shader:",path);
		}
	} else {
		fx_shader_flip(fx_dither_bayer_8,on_g,filter_is,source,null);

		fx_dither_bayer_8.set("texture_source",source);
		fx_dither_bayer_8.set("resolution_source",source.width,source.height);


		// external parameter
    fx_dither_bayer_8.set("level_source",level.x,level.y,level.z);
    fx_dither_bayer_8.set("mode",mode); // mode 0 : gray / 1 is rgb

    // rendering
		render_shader(fx_dither_bayer_8,pg_dither_bayer_8,source,on_g,filter_is);
	}

	// return
	reset_reverse_g(false);
	if(on_g) {
		return null;
	} else {
		return pg_dither_bayer_8; 
	}
}












/**
* Flip
* v 0.1.1
*2019-2019
*/
// setting by class FX
public PGraphics fx_flip(PImage source, FX fx) {
	return fx_flip(source,fx.on_g(),fx.pg_filter_is(),fx.get_event(0).xy());
}

// main
PShader fx_flip;
PGraphics pg_flip;
public PGraphics fx_flip(PImage source, boolean on_g, boolean filter_is, bvec2 flip) {
	if(!on_g && (pg_flip == null 
								|| (source.width != pg_flip.width 
								|| source.height != pg_flip.height))) {
		pg_flip = createGraphics(source.width,source.height,get_renderer());
	}

	if(fx_flip == null) {
		// main glsl
		String path = get_fx_post_path()+"flip.glsl";
		if(fx_post_rope_path_exists) {
			fx_flip = loadShader(path);
			println("load shader: flip.glsl");
		}
		println("load shader:",path);
	} else {

		ivec2 iflip = ivec2(0);
		if(flip.x) iflip.x(1);
		if(flip.y) iflip.y(1);
		if(on_g) {
			iflip.y = 1-iflip.y;
		}

		fx_flip.set("resolution",(float)source.width,(float)source.height);
		fx_flip.set("resolution_source",(float)source.width,(float)source.height);
    fx_flip.set("texture_source",source);
		fx_flip.set("flip_source",iflip.x(),iflip.y()); // value: 0 or 1

    // rendering
		render_shader(fx_flip,pg_flip,source,on_g,filter_is);
	}

	// return
	reset_reverse_g(false);
	if(on_g) {
		return null;
	} else {
		return pg_flip; 
	}
}











/**
* Glitch FXAA by Stan le punk
* v 0.0.1
* 2019-2019
*/
// setting by class FX
public PGraphics fx_glitch_fxaa(PImage source, FX fx) {
	return fx_glitch_fxaa(source,fx.on_g(),fx.pg_filter_is(),fx.get_cardinal());
}

// main
PShader fx_glitch_fxaa;
PGraphics pg_glitch_fxaa;
public PGraphics fx_glitch_fxaa(PImage source, boolean on_g, boolean filter_is, vec4 cardinal) {
	if(!on_g && (pg_glitch_fxaa == null 
								|| (source.width != pg_glitch_fxaa.width 
								|| source.height != pg_glitch_fxaa.height))) {
		pg_glitch_fxaa = createGraphics(source.width,source.height,get_renderer());
	}

	if(fx_glitch_fxaa == null) {
		String path = get_fx_post_path()+"AA_FXAA_glitch.glsl";
		if(fx_post_rope_path_exists) {
			fx_glitch_fxaa = loadShader(path);
			println("load shader: AA_FXAA.glsl");
		}
		println("load shader:",path);
	} else {
		fx_shader_flip(fx_glitch_fxaa,on_g,filter_is,source,null);

		fx_glitch_fxaa.set("texture_source",source);
		fx_glitch_fxaa.set("resolution_source",(float)source.width,(float)source.height);


		fx_glitch_fxaa.set("nw",cardinal.x(),cardinal.w()); // value from -1 to 1
		fx_glitch_fxaa.set("ne",cardinal.x(),cardinal.y()); // value from -1 to 1
		fx_glitch_fxaa.set("sw",cardinal.z(),cardinal.w()); // value from -1 to 1
		fx_glitch_fxaa.set("se",cardinal.z(),cardinal.y()); // value from -1 to 1

    // rendering
		render_shader(fx_glitch_fxaa,pg_glitch_fxaa,source,on_g,filter_is);
	}
	// return
	reset_reverse_g(false);
	if(on_g) {
		return null;
	} else {
		return pg_glitch_fxaa; 
	}
}










/**
* Grain 
v 0.2.1
2018-2019
*/

// setting by class FX
public PGraphics fx_grain(PImage source, FX fx) {
	float offset = 0;
	if(fx.get_offset() != null) {
		offset = fx.get_offset().x;
	}

	float scl = 0;
	if(fx.get_scale() != null) {
		scl = fx.get_scale().x;
	}
	return fx_grain(source,fx.on_g(),fx.pg_filter_is(),offset,fx.get_mode());
}

// main
PShader fx_grain;
PGraphics pg_grain;
public PGraphics fx_grain(PImage source, boolean on_g, boolean filter_is, float offset, int mode) {
	if(!on_g && (pg_grain == null 
								|| (source.width != pg_grain.width 
								|| source.height != pg_grain.height))) {
		pg_grain = createGraphics(source.width,source.height,get_renderer());
	}

	if(fx_grain == null) {
		String path = get_fx_post_path()+"grain.glsl";
		if(fx_post_rope_path_exists) {
			fx_grain = loadShader(path);
			println("load shader:",path);
		}
	} else {
		fx_shader_flip(fx_grain,on_g,filter_is,source,null);
		// if(on_g) set_shader_flip(fx_grain,source);
		fx_grain.set("texture_source",source);
		fx_grain.set("resolution_source",source.width,source.height);
		fx_grain.set("resolution",source.width,source.height);

		// external param
    fx_grain.set("offset",offset); // that define the offset
		fx_grain.set("mode",mode); // mode 0 is for black and white, and mode 1 for RVB

		render_shader(fx_grain,pg_grain,source,on_g,filter_is);

	}

	// return
	reset_reverse_g(false);
	if(on_g) {
		return null;	
	} else {
		return pg_grain; 
	}
}


















/**
* Grain scatter
v 0.2.2
2018-2019
*/
// setting by class FX
public PGraphics fx_grain_scatter(PImage source, FX fx) {
		float str = 0;
	if(fx.get_strength() != null) {
		str = fx.get_strength().x;
	}
	return fx_grain_scatter(source,fx.on_g(),fx.pg_filter_is(),str);
}

// main
PShader fx_grain_scatter;
PGraphics pg_grain_scatter;
public PGraphics fx_grain_scatter(PImage source, boolean on_g, boolean filter_is, float strength) {
	if(!on_g && (pg_grain_scatter == null 
								|| (source.width != pg_grain_scatter.width 
								|| source.height != pg_grain_scatter.height))) {
		pg_grain_scatter = createGraphics(source.width,source.height,get_renderer());
	}

	if(fx_grain_scatter == null) {
		String path = get_fx_post_path()+"grain_scatter.glsl";
		if(fx_post_rope_path_exists) {
			fx_grain_scatter = loadShader(path);
			println("load shader:",path);
		}
	} else {
		fx_shader_flip(fx_grain_scatter,on_g,filter_is,source,null);

		fx_grain_scatter.set("texture_source",source);
		fx_grain_scatter.set("resolution_source",source.width,source.height);
		fx_grain_scatter.set("resolution",source.width,source.height);

		// external param
		fx_grain_scatter.set("strength",strength);

		// rendering
		render_shader(fx_grain_scatter,pg_grain_scatter,source,on_g,filter_is);

	}
	// return
	reset_reverse_g(false);
	if(on_g) {
		return null;
	} else {
		return pg_grain_scatter; 
	}
}



















/**
* halftone dot
* v 0.1.2
* 2018-2019
*/
// setting by class FX
public PGraphics fx_halftone_dot(PImage source, FX fx) {
	vec2 pos = vec2(source.width/2,source.height/2);
	if(fx.get_pos() != null) {
		pos = vec2(fx.get_pos().x(),fx.get_pos().y());
	}

	float threshold = .95f;
	if(fx.get_threshold() != null) {
		threshold = fx.get_threshold().x();
	}

	float angle = 0;
	if(fx.get_angle() != null) {
		angle = fx.get_angle().x();
	}

	float pixel_size = 5;
	if(fx.get_angle() != null) {
		pixel_size = fx.get_size().x();
	}

	return fx_halftone_dot(source,fx.on_g(),fx.pg_filter_is(),pos,pixel_size,angle,threshold);
}

// main
PShader fx_halftone;
PGraphics pg_halftone_dot;
public PGraphics fx_halftone_dot(PImage source, boolean on_g, boolean filter_is, vec2 pos, float size, float angle, float threshold) {
	if(!on_g && (pg_halftone_dot == null 
								|| (source.width != pg_halftone_dot.width 
								|| source.height != pg_halftone_dot.height))) {
		pg_halftone_dot = createGraphics(source.width,source.height,get_renderer());
	}

	if(fx_halftone == null) {
		String path = get_fx_post_path()+"halftone_dot.glsl";
		if(fx_post_rope_path_exists) {
			fx_halftone = loadShader(path);
			println("load shader:",path);
		}
	} else {
		fx_shader_flip(fx_halftone,on_g,filter_is,source,null);

		fx_halftone.set("texture_source",source);
		fx_halftone.set("resolution_source",source.width,source.height);

		// external parameter
		fx_halftone.set("angle",angle);
    pos.div(source.width,source.height);
    if(graphics_is(source).equals("PGraphics")) {
    	pos.y(1.0f -pos.y);
    }
    fx_halftone.set("position",pos.x,pos.y);

		
		fx_halftone.set("size",size);
		fx_halftone.set("threshold",threshold);

		render_shader(fx_halftone,pg_halftone_dot,source,on_g,filter_is);

	}

	// return
	reset_reverse_g(false);
	if(on_g) {
		return null;
	} else {
		return pg_halftone_dot; 
	}
}










/**
* halftone line
* v 0.1.2
* 2018-2019
*/
// use setting
public PGraphics fx_halftone_line(PImage source, FX fx) {
	vec2 pos = vec2(source.width/2,source.height/2);
	if(fx.get_pos() != null) {
		pos = vec2(fx.get_pos().x,fx.get_pos().y);
	}

	vec3 angle = vec3();
	if(fx.get_angle() != null) {
		angle = fx.get_angle().copy();
	}

	int mode = fx.get_mode();
	int num = fx.get_num();
	float quality = fx.get_quality();

  vec3 threshold = vec3(.9f);
	if(fx.get_threshold() != null) {
		threshold = fx.get_threshold().copy();
	}
	return fx_halftone_line(source,fx.on_g(),fx.pg_filter_is(),pos,angle,mode,num,quality,threshold);	
}

// main
PShader fx_halftone_line;
PGraphics result_halftone_line;
public PGraphics fx_halftone_line(PImage source, boolean on_g, boolean filter_is, vec2 pos, vec3 angle, int mode, int num, float quality, vec3 threshold) {
	if(!on_g && (result_halftone_line == null 
								|| (source.width != result_halftone_line.width 
								|| source.height != result_halftone_line.height))) {
		result_halftone_line = createGraphics(source.width,source.height,get_renderer());
	}

	if(fx_halftone_line == null) {
		String path = get_fx_post_path()+"halftone_line.glsl";
		if(fx_post_rope_path_exists) {
			fx_halftone_line = loadShader(path);
			println("load shader:",path);
		}
	} else {
		fx_shader_flip(fx_halftone_line,on_g,filter_is,source,null);

		fx_halftone_line.set("texture_source",source);
		fx_halftone_line.set("resolution_source",source.width,source.height);
    
    // external parameter
    fx_halftone_line.set("mode",mode);
    fx_halftone_line.set("num",num);
    fx_halftone_line.set("quality",quality);
    fx_halftone_line.set("threshold",threshold.x()); // good between 0.05 and 0.3
		fx_halftone_line.set("angle",angle.x);

		if(graphics_is(source).equals("PGraphics")) {
			pos.y(1.0f -pos.y);
		}
		fx_halftone_line.set("position",pos.x,pos.y); // middle position on window

    // rendering
		render_shader(fx_halftone_line,result_halftone_line,source,on_g,filter_is);
	}

	// return
	reset_reverse_g(false);
	if(on_g) {
		return null;
	} else {
		return result_halftone_line; 
	}
}



/**
* Halftone Multi
* refactoring from 
* v 0.1.1
* 2019-2019
*/
// use setting
public PGraphics fx_halftone_multi(PImage source, FX fx) {
	return fx_halftone_multi(source,fx.on_g(),fx.pg_filter_is(),vec2(fx.get_pos()),fx.get_size().x,fx.get_angle().x,fx.get_quality(),fx.get_threshold().x,fx.get_saturation(),fx.get_mode());
}

// main
PShader fx_halftone_multi;
PGraphics pg_halftone_multi;
public PGraphics fx_halftone_multi(PImage source, boolean on_g, boolean filter_is, vec2 pos, float size, float angle, float quality, float threshold, float saturation, int mode) {
	if(!on_g && (pg_halftone_multi == null 
								|| (source.width != pg_halftone_multi.width 
								|| source.height != pg_halftone_multi.height))) {
		pg_halftone_multi = createGraphics(source.width,source.height,get_renderer());
	}

	if(fx_halftone_multi == null) {
		String path = get_fx_post_path()+"halftone_multi.glsl";
		if(fx_post_rope_path_exists) {
			fx_halftone_multi = loadShader(path);
			println("load shader:",path);
		}
	} else {
		fx_shader_flip(fx_halftone_multi,on_g,filter_is,source,null);
    // if(on_g) set_shader_flip(fx_halftone_multi,source);

		fx_halftone_multi.set("texture_source",source);
		fx_halftone_multi.set("resolution_source",source.width,source.height);
    
		// external param
		if(graphics_is(source).equals("PGraphics")) {
			pos.y(1.0f -pos.y);
		}
		fx_halftone_multi.set("position",pos.x,pos.y); // -1 to 1
		float sat = map(saturation,0,1,-1,1);
		fx_halftone_multi.set("saturation",sat); // -1 to 1
		fx_halftone_multi.set("angle",angle); // in radian
		fx_halftone_multi.set("scale",size); // from 0 to 2 is good
		fx_halftone_multi.set("divs",quality); // from 1 to 16
		fx_halftone_multi.set("sharpness",threshold); // from 0 to 2 is good
		fx_halftone_multi.set("mode",mode); // from 0 to 3 dot, circle and line

		 // rendering
    render_shader(fx_halftone_multi,pg_halftone_multi,source,on_g,filter_is);
	}

	// end
	reset_reverse_g(false);
	if(on_g) {
		return null;
	} else {
		return pg_halftone_multi; 
	}
}











/**
* IMAGE
* v 0.2.2
*/
// setting by class FX
public PGraphics fx_image(PImage source, FX fx) {
	return fx_image(source,fx.on_g(),fx.pg_filter_is(),vec2(fx.get_pos()),vec2(fx.get_size()),vec3(fx.get_colour()),fx.get_cardinal(),fx.get_mode());
}

// main
PShader fx_image;
PGraphics pg_image_rendering;
public PGraphics fx_image(PImage source, boolean on_g, boolean filter_is, vec2 pos, vec2 scale, vec3 colour_background, vec4 pos_curtain, int mode) {
	if(!on_g && (pg_image_rendering == null 
								|| (source.width != pg_image_rendering.width 
								|| source.height != pg_image_rendering.height))) {
		pg_image_rendering = createGraphics(source.width,source.height,get_renderer());
	}

	if(fx_image == null) {
		String path = get_fx_post_path()+"image.glsl";
		if(fx_post_rope_path_exists) {
			fx_image = loadShader(path);
			println("load shader: image.glsl");
		}
		println("load shader:",path);
	} else {
		fx_shader_flip(fx_image,on_g,filter_is,source,null);

		fx_image.set("texture_source",source);
		if(on_g) {
			fx_image.set("resolution",width,height);
		} else {
			fx_image.set("resolution",source.width,source.height);
		}
		fx_image.set("resolution_source",source.width,source.height); 
		
		// fx_image.set("flip_source",true,false);
		// fx_image.set("texture_source",source);
		// fx_image.set("resolution",width,height);
		// fx_image.set("resolution_source",source.width,source.height); 

    // external parameter
		if(colour_background != null) {
	    fx_image.set("colour",colour_background.x,colour_background.y,colour_background.z); // definr RGB color from 0 to 1
	  }

	  if(pos_curtain != null) {
	  	// printTempo(60,"curtain",pos_curtain);
	    fx_image.set("curtain",pos_curtain.x,pos_curtain.y,pos_curtain.z,pos_curtain.w); // definr RGB color from 0 to 1
	  }

	  if(pos != null) {
	  	if(graphics_is(source).equals("PGraphics")) {
	  		pos.y(1.0f -pos.y);
	  	}
	    fx_image.set("position",pos.x,pos.y); // from 0 to 1
	  }
	  
	  if(scale != null) {
	    fx_image.set("scale",scale.x,scale.y); // from 0 to 1
	  }
	  
	  int shader_mode = 0;
	  if(mode == CENTER) {
	    shader_mode = 0;
	  } else if(mode == SCREEN) {
	    shader_mode = 1;
	  } else if(mode == r.SCALE) {
	    shader_mode = 2;
	  }
	  // println("mode",shader_mode);
	  fx_image.set("mode",shader_mode);

    // rendering
		render_shader(fx_image,pg_image_rendering,source,on_g,filter_is);
	}

	// return
	reset_reverse_g(false);
	if(on_g) {
		return null;
	} else {
		return pg_image_rendering; 
	}
}






















/**
* Level
v 0.1.1
2019-2019
*/
// direct filtering
public PGraphics fx_level(PImage source, FX fx) {
	vec3 level = vec3(1);
	if(fx.get_level_source() != null) {
		level.set(fx.get_level_source());
	}
	return fx_level(source,fx.on_g(),fx.pg_filter_is(),fx.get_mode(),level.array());
}

// main method
PShader fx_level;
PGraphics pg_level;
public PGraphics fx_level(PImage source, boolean on_g, boolean filter_is, int mode, float... level) {
	if(!on_g && (pg_level == null 
								|| (source.width != pg_level.width 
								|| source.height != pg_level.height))) {
		pg_level = createGraphics(source.width,source.height,get_renderer());
	}

	if(fx_level == null) {
		String path = get_fx_post_path()+"level.glsl";
		if(fx_post_rope_path_exists) {
			fx_level = loadShader(path);
			println("load shader:",path);
		}
	} else {
		fx_shader_flip(fx_level,on_g,filter_is,source,null);

		fx_level.set("texture_source",source);
		fx_level.set("resolution_source",source.width,source.height);
		if(level.length == 1) {
			fx_level.set("level_source",level[0],level[0],level[0],1);
		} else if(level.length == 2) {
			fx_level.set("level_source",level[0],level[0],level[0],level[1]);
		} else if(level.length == 3) {
			fx_level.set("level_source",level[0],level[1],level[2],1);
		} else if(level.length == 4) {
			fx_level.set("level_source",level[0],level[1],level[2],level[3]);
		} else {
			fx_level.set("level_source",1,1,1,1);
		}
		
		if(mode >= 0 && mode < 2) {
			fx_level.set("mode",mode); // 0 black / 1 white
		} 


    // rendering
		render_shader(fx_level,pg_level,source,on_g,filter_is);

	}

	// return
	reset_reverse_g(false);
	if(on_g) {
		return null;
	} else {
		return pg_level; 
	}
}















/**
* Mask
v 0.2.3
2019-2019
*/
public PGraphics fx_mask(PImage source, PImage mask, FX fx) {
	return fx_mask(source,mask,fx.on_g(),fx.pg_filter_is(),fx.get_mode(),fx.get_num(),fx.get_threshold().xy(),fx.get_level_layer());
}

// main
PShader fx_mask;
PGraphics pg_mask;
public PGraphics fx_mask(PImage source, PImage mask, boolean on_g, boolean filter_is, int mode, int num, vec2 threshold, vec4 level_layer) {
	if(!on_g && (pg_mask == null 
								|| (source.width != pg_mask.width 
								|| source.height != pg_mask.height))) {
		pg_mask = createGraphics(source.width,source.height,get_renderer());
	}

	if(fx_mask == null) {
		String path = get_fx_post_path()+"mask.glsl";
		if(fx_post_rope_path_exists) {
			fx_mask = loadShader(path);
			println("load shader:",path);
		}
	} else {
		fx_shader_flip(fx_mask,on_g,filter_is,source,mask);

		fx_mask.set("texture_source",source);
		fx_mask.set("resolution_source",source.width,source.height);

		fx_mask.set("resolution_layer",mask.width,mask.height);
		fx_mask.set("texture_layer",mask);
		fx_mask.set("mode",mode); // mode 0: gray || mode 1: RGB

		if(num < 2) num = 2;
		fx_mask.set("num",num); // define the num of step separation
    

    if(threshold.min() > threshold.max()) {
    	threshold.x(0);
    }
    threshold.constrain(0,1);
		fx_mask.set("threshold",threshold.min(),threshold.max()); // from 0 to 1, that's born the sensibility from the minium to the maximum


		level_layer.constrain(0,6);
		fx_mask.set("level_layer",level_layer.x(),level_layer.y(),level_layer.z(),level_layer.w()); // strength 1 is the classic strength
		  
    // rendering
    render_shader(fx_mask,pg_mask,source,on_g,filter_is);
 
	}

	// end
	reset_reverse_g(false);
	if(on_g) {
		return null;
	} else {
		return pg_mask; 
	}
}












/**
* mix
* v 0.1.2
* 2019-2019
*
* -2 main
* -1 layer 
* 0 ?
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
*  15 subtract
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

// witj class FX
public PGraphics fx_mix(PImage source, PImage layer, FX fx) {
	vec3 level_source = vec3(.5f);
	if(fx.get_level_source() != null) {
		level_source = vec3(fx.get_level_source());
	}
	vec3 level_layer = vec3(.5f);
	if(fx.get_level_layer() != null) {
		level_layer = vec3(fx.get_level_layer());
	}
  return fx_mix(source,layer,fx.on_g(),fx.pg_filter_is(),fx.get_mode(),level_source,level_layer);
	
}

// main
PShader fx_mix;
PGraphics pg_mix;
public PGraphics fx_mix(PImage source, PImage layer, boolean on_g, boolean filter_is, int mode, vec3 level_source, vec3 level_layer) {
	if(!on_g && (pg_mix == null 
								|| (source.width != pg_mix.width 
								|| source.height != pg_mix.height))) {
		pg_mix = createGraphics(source.width,source.height,get_renderer());
	}

	if(fx_mix == null) {
		String path = get_fx_post_path()+"mix.glsl";
		if(fx_post_rope_path_exists) {
			fx_mix = loadShader(path);
			println("load shader:",path);
		}
	} else {
		fx_shader_flip(fx_mix,on_g,filter_is,source,layer);

		fx_mix.set("texture_source",source);
		fx_mix.set("resolution_source",source.width,source.height);
		fx_mix.set("texture_layer",layer);
		
		if(graphics_is(layer).equals("PGraphics")) {
			fx_mix.set("flip_layer",0,0);
		} else {
			if(on_g) {
				fx_mix.set("flip_layer",1,0);
			}
		}
		

    // external paramer
    fx_mix.set("level_source",level_source.x,level_source.y,level_source.z);
		fx_mix.set("level_layer",level_layer.x,level_layer.y,level_layer.z);

		fx_mix.set("mode",mode); 
    
    // rendering
    render_shader(fx_mix,pg_mix,source,on_g,filter_is);
 
	}

	// end
	reset_reverse_g(false);
	if(on_g) {
		return null;
	} else {
		return pg_mix; 
	}
}



















/**
* Pixel 
* v 0.1.1
* 2018-2019
*/
// setting by class FX
public PGraphics fx_pixel(PImage source, FX fx) {
	ivec2 size = ivec2(5);
	if(fx.get_size() != null) {
		size.set(fx.get_size());
	}
	vec3 level_source = vec3(0,.5f,.5f);
	if(fx.get_level_source() != null) {
		level_source.set(fx.get_level_source());
	}
	return fx_pixel(source,fx.on_g(),fx.pg_filter_is(),size,fx.get_num(),level_source,fx.get_event(0).x());
}

// main
PShader fx_pixel;
PGraphics pg_pixel;
public PGraphics fx_pixel(PImage source, boolean on_g, boolean filter_is, ivec2 size, int num, vec3 level_source, boolean effect_is) {
	if(!on_g && (pg_pixel == null 
								|| (source.width != pg_pixel.width 
								|| source.height != pg_pixel.height))) {
		pg_pixel = createGraphics(source.width,source.height,get_renderer());
	}

	if(fx_pixel == null) {
		String path = get_fx_post_path()+"pixel.glsl";
		if(fx_post_rope_path_exists) {
			fx_pixel = loadShader(path);
			println("load shader:",path);
		}
	} else {
		fx_shader_flip(fx_pixel,on_g,filter_is,source,null);
		// if(on_g) set_shader_flip(fx_pixel,source);

		fx_pixel.set("texture_source",source);
		fx_pixel.set("resolution",source.width,source.height);
		fx_pixel.set("resolution_source",source.width,source.height);
    
		// external param
		fx_pixel.set("use_fx_color",effect_is);
		fx_pixel.set("level_source",level_source.x,level_source.y,level_source.z,1); // from 0 to 1 where
		fx_pixel.set("num",num); // from 2 to 16
    fx_pixel.set("size",size.x,size.y); // define the width and height of pixel

    // rendering
		render_shader(fx_pixel,pg_pixel,source,on_g,filter_is);

	}

	// return
	reset_reverse_g(false);
	if(on_g) {
		return null;
	} else {
		return pg_pixel; 
	}
}









/**
* Posterize by Stan le punk
* v 0.0.1
* 2019-2019
*/
// setting by class FX
public PGraphics fx_posterization(PImage source, FX fx) {
	return fx_posterization(source,fx.on_g(),fx.pg_filter_is(),fx.get_threshold(),fx.get_num());
}

// main
PShader fx_posterization;
PGraphics pg_posterization;
public PGraphics fx_posterization(PImage source, boolean on_g, boolean filter_is, vec3 threshold, int num) {
	if(!on_g && (pg_posterization == null 
								|| (source.width != pg_posterization.width 
								|| source.height != pg_posterization.height))) {
		pg_posterization = createGraphics(source.width,source.height,get_renderer());
	}

	if(fx_posterization == null) {
		String path = get_fx_post_path()+"posterization.glsl";
		if(fx_post_rope_path_exists) {
			fx_posterization = loadShader(path);
			println("load shader: posterization.glsl");
		}
		println("load shader:",path);
	} else {
		fx_shader_flip(fx_posterization,on_g,filter_is,source,null);

		fx_posterization.set("texture_source",source);
		fx_posterization.set("resolution_source",(float)source.width,(float)source.height);


		fx_posterization.set("threshold",threshold.x(),threshold.y(),threshold.z()); // value 0 to 1
		if(num < 2) num = 2;
		fx_posterization.set("num",num);

    // rendering
		render_shader(fx_posterization,pg_posterization,source,on_g,filter_is);

	}

	// return
	reset_reverse_g(false);
	if(on_g) {
		return null;
	} else {
		return pg_posterization; 
	}
}
















/**
* Reaction diffusion
* v 0.0.6
* 2018-2019
*/
/**
WARNING
the g part is not not not not optimized...too slow :((((((
*/
public PGraphics fx_reaction_diffusion(PImage source, FX fx) {
	return fx_reaction_diffusion(source,fx.on_g(),fx.get_pair(0),fx.get_pair(1),vec2(fx.get_scale()),vec3(fx.get_colour()),fx.get_num(),fx.get_event(0).x());
}

// main
PShader fx_reac_diff;
PGraphics pg_reac_diff;
boolean start;
PImage buffer_reac_diff;
public PGraphics fx_reaction_diffusion(PImage source, boolean on_g, vec2 conc_uv, vec2 kf, vec2 scale, vec3 rgb, int num, boolean event) {
	if(pg_reac_diff == null 
								|| (source.width != pg_reac_diff.width 
								|| source.height != pg_reac_diff.height)) {
		pg_reac_diff = createGraphics(source.width,source.height,get_renderer());
	}

	// init
	if(source != null) {
		if(fx_reac_diff == null) {
			String path = get_fx_post_path()+"reaction_diffusion.glsl";
			if(fx_post_rope_path_exists) {
				fx_reac_diff = loadShader(path);
				println("load shader:",path);
			}
		}
		if(pg_reac_diff == null) {
			pg_reac_diff = createGraphics(source.width,source.height,get_renderer());
			println("create feedback");
		}
	}

	// if(!texture_is) set_shader_flip(fx_reac_diff,source);
	// create buffer for g case
  	if(on_g) {
		if(buffer_reac_diff == null) {
			buffer_reac_diff = source.copy();
		} else {
			buffer_reac_diff.loadPixels();
			source.loadPixels();
			buffer_reac_diff.pixels = source.pixels;
			buffer_reac_diff.updatePixels();
		}
	}


	// reset part
	if(!start || event) {
		pg_reac_diff.beginDraw();
		if(!on_g) {
			pg_reac_diff.image(source,0,0,source.width,source.height);
		} else {
			pg_reac_diff.image(buffer_reac_diff,0,0,source.width,source.height);
		}
		pg_reac_diff.endDraw();
		start = true;
	}
  /*
	float ru = 0.25f;
	float rv = 0.04f;
	float f = 0.1f;
	float k = 0.047f;
	float red = 0;
	float green = 0;
	float blue = 0;
	 vec2 scale = vec2(.6);
	 int rd_iteration = 20;
	*/

	// effect part
	textureWrap(REPEAT);
	int rd_iteration = 20;
	if(num > 0 && num < 100) {
		rd_iteration = num;
	}
	for(int i = 0 ; i < num ; i++) {
		if(!on_g) {
			fx_reac_diff.set("texture_source",source);
		} else {
			fx_reac_diff.set("texture_source",buffer_reac_diff);
		}
		fx_reac_diff.set("texture_layer",pg_reac_diff);

		fx_reac_diff.set("resolution",source.width,source.height);
		fx_reac_diff.set("resolution_source",source.width,source.height);
		fx_reac_diff.set("resolution_layer",pg_reac_diff.width,pg_reac_diff.height);

		// eternal param
		if(conc_uv != null) {
			fx_reac_diff.set("ru",conc_uv.u);
			fx_reac_diff.set("rv",conc_uv.v);
		} else {
			fx_reac_diff.set("ru",.25f);
			fx_reac_diff.set("rv",.04f);
		}

		// eternal param
		if(kf != null) {
			fx_reac_diff.set("k",kf.x);
			fx_reac_diff.set("f",kf.y);
		} else {
			fx_reac_diff.set("k",0.1f);
			fx_reac_diff.set("f",0.047f);
		}


		// in progress
		/*
		if(rgb != null) {
			fx_reac_diff.set("red",rgb.r);
			fx_reac_diff.set("green",rgb.g);
			fx_reac_diff.set("blue",rgb.b);
		} else {
			fx_reac_diff.set("red",0);
			fx_reac_diff.set("green",0);
			fx_reac_diff.set("blue",0);
		}
		*/


		fx_reac_diff.set("scale",scale.x,scale.y);

    // rendering
		if(pg_reac_diff != null && !on_g) {
			// texture case
	  	pg_reac_diff.beginDraw();
	  	pg_reac_diff.shader(fx_reac_diff);
	  	pg_reac_diff.image(source,0,0,source.width,source.height);
	  	pg_reac_diff.resetShader();
	  	pg_reac_diff.endDraw();
	  } else {
      // g case
	  	pg_reac_diff.beginDraw();
	  	pg_reac_diff.shader(fx_reac_diff);
	  	pg_reac_diff.image(buffer_reac_diff,0,0,source.width,source.height);
	  	pg_reac_diff.resetShader();
	  	pg_reac_diff.endDraw();
	  	image(pg_reac_diff);
	  }
	}

	// render
	if(on_g) {
		return null;
	} else {
		return pg_reac_diff; 
	}
}






























/**
* split rgb
* v 0.1.1
* 2019-2019
*/
// use setting
public PGraphics fx_split_rgb(PImage source, FX fx) {
	return fx_split_rgb(source,fx.on_g(),fx.pg_filter_is(),fx.get_pair(0),fx.get_pair(1),fx.get_pair(2));
}

// main
PShader fx_split_rgb;
PGraphics pg_split_rgb;
public PGraphics fx_split_rgb(PImage source, boolean on_g, boolean filter_is, vec2 offset_red, vec2 offset_green, vec2 offset_blue) {
	if(!on_g && (pg_split_rgb == null 
								|| (source.width != pg_split_rgb.width 
								|| source.height != pg_split_rgb.height))) {
		pg_split_rgb = createGraphics(source.width,source.height,get_renderer());
	}

	if(fx_split_rgb == null) {
		String path = get_fx_post_path()+"split_rgb_simple.glsl";
		if(fx_post_rope_path_exists) {
			fx_split_rgb = loadShader(path);
			println("load shader:",path);
		}
	} else {
		fx_shader_flip(fx_split_rgb,on_g,filter_is,source,null);

		fx_split_rgb.set("texture_source",source);
		fx_split_rgb.set("resolution_source",source.width,source.height);

		// external param
		if(offset_red != null) {
			fx_split_rgb.set("offset_red",offset_red.x,offset_red.y);
		}

		if(offset_green != null) {
			fx_split_rgb.set("offset_green",offset_green.x,offset_green.y);
		}
		
		if(offset_blue != null) {
			fx_split_rgb.set("offset_blue",offset_blue.x,offset_blue.y);
		} 
		

		 // rendering
    render_shader(fx_split_rgb,pg_split_rgb,source,on_g,filter_is);
	}

	// end
	reset_reverse_g(false);
	if(on_g) {
		return null;
	} else {
		return pg_split_rgb; 
	}
}







/**
* Threshold
* v 0.3.1
* 2018-2019
*/
// setting by class FX
public PGraphics fx_threshold(PImage source, FX fx) {
	return fx_threshold(source,fx.on_g(),fx.pg_filter_is(),vec3(fx.get_level_source()),fx.get_mode());	
}

// main
PShader fx_threshold;
PGraphics pg_threshold;
public PGraphics fx_threshold(PImage source, boolean on_g, boolean filter_is, vec3 level, int mode) {
	if(!on_g && (pg_threshold == null 
								|| (source.width != pg_threshold.width 
								|| source.height != pg_threshold.height))) {
		pg_threshold = createGraphics(source.width,source.height,get_renderer());
	}

	
	if(fx_threshold == null) {
		String path = get_fx_post_path()+"threshold.glsl";
		if(fx_post_rope_path_exists) {
			fx_threshold = loadShader(path);
			println("load shader:",path);
		}
	} else {
		fx_shader_flip(fx_threshold,on_g,filter_is,source,null);

		fx_threshold.set("texture_source",source);
		fx_threshold.set("resolution_source",source.width,source.height);

		// external parameter
		level = map(level,0,1,.05f,1.50f);
    fx_threshold.set("level_source",level.x,level.y,level.z);
    fx_threshold.set("mode",mode); // mode 0 : gray / 1 is rgb

    // rendering
		render_shader(fx_threshold,pg_threshold,source,on_g,filter_is);
	}

	// return
	reset_reverse_g(false);
	if(on_g) {
		return null;
	} else {
		return pg_threshold; 
	}
}



















/**
* warp procedural line by Stan le punk
* @see http://stanlepunk.xyz
* @see https://github.com/StanLepunK/Filter
* v 0.1.1
* 2018-2019
*/
public PGraphics fx_warp_proc(PImage source, FX fx) {
	return fx_warp_proc(source,fx.on_g(),fx.pg_filter_is(),fx.get_strength().x);
}

PShader fx_warp_proc;
PGraphics pg_warp_proc;
public PGraphics fx_warp_proc(PImage source, boolean on_g, boolean filter_is, float strength) {
	if(!on_g && (pg_warp_proc == null 
								|| (source.width != pg_warp_proc.width 
								|| source.height != pg_warp_proc.height))) {
		pg_warp_proc = createGraphics(source.width,source.height,get_renderer());
	}

	if(fx_warp_proc == null) {
		String path = get_fx_post_path()+"warp_proc.glsl";
		if(fx_post_rope_path_exists) {
			fx_warp_proc = loadShader(path);
			println("load shader:",path);
		}
	} else {
		fx_shader_flip(fx_warp_proc,on_g,filter_is,source,null);

		fx_warp_proc.set("texture_source",source);
		fx_warp_proc.set("resolution_source",source.width,source.height);
    
    // external parameter
		fx_warp_proc.set("strength",strength);

		// rendering
		render_shader(fx_warp_proc,pg_warp_proc,source,on_g,filter_is);
	}

	// return
	reset_reverse_g(false);
	if(on_g) {
		return null;
	} else {
		return pg_warp_proc; 
	}
}












/**
* warp texture type A
* v 0.3.2
* 2018-2019
*/
// use setting
public PGraphics fx_warp_tex_a(PImage source, PImage velocity, PImage direction, FX fx) {
	return fx_warp_tex_a(source,velocity,direction,fx.on_g(),fx.pg_filter_is(),fx.get_mode(),fx.get_strength().x);
}

// main
PShader fx_warp_tex_a;
PGraphics pg_warp_tex_a;
public PGraphics fx_warp_tex_a(PImage source, PImage velocity, PImage direction, boolean on_g, boolean filter_is, int mode, float strength) {
	if(!on_g && (pg_warp_proc == null 
								|| (source.width != pg_warp_proc.width 
								|| source.height != pg_warp_proc.height))) {
		pg_warp_proc = createGraphics(source.width,source.height,get_renderer());
	}
  

	if(fx_warp_tex_a == null) {
		String path = get_fx_post_path()+"warp_tex_a.glsl";
		if(fx_post_rope_path_exists) {
			fx_warp_tex_a = loadShader(path);
			println("load shader:",path);
		}
	} else {
		fx_shader_flip(fx_warp_tex_a,on_g,filter_is,source,null);

		fx_warp_tex_a.set("texture_source",source);
		fx_warp_tex_a.set("resolution_source",source.width,source.height);

		
		// external parameter
		// warp sources
		if(direction != null) {
			fx_warp_tex_a.set("texture_direction",direction);
		}
		if(velocity != null) {
			fx_warp_tex_a.set("texture_velocity",velocity);
		}
		//setting external param
		fx_warp_tex_a.set("strength",strength); // good for normal value 0 > 1
		fx_warp_tex_a.set("mode",mode); // mode 0 > show warp / mode 500 show texture velocity / mode 501 show texture direction

		// rendering
		render_shader(fx_warp_tex_a,pg_warp_tex_a,source,on_g,filter_is);
	}

	// return
	reset_reverse_g(false);
	if(on_g) {
		return null;
	} else {
		return pg_warp_tex_a; 
	}
}










/**
* warp texture type B
* v 0.1.2
* 2019-2019
*/
// use setting
public PGraphics fx_warp_tex_b(PImage source, PImage layer, FX fx) {
	return fx_warp_tex_b(source,layer,fx.on_g(),fx.pg_filter_is(),fx.get_strength().x());
}

// main
PShader fx_warp_tex_b;
PGraphics pg_warp_tex_b;
public PGraphics fx_warp_tex_b(PImage source, PImage layer, boolean on_g, boolean filter_is, float strength) {
	if(!on_g && (pg_warp_tex_b == null 
								|| (source.width != pg_warp_tex_b.width 
								|| source.height != pg_warp_tex_b.height))) {
		pg_warp_tex_b = createGraphics(source.width,source.height,get_renderer());
	}

	if(fx_warp_tex_b == null) {
		String path = get_fx_post_path()+"warp_tex_b.glsl";
		if(fx_post_rope_path_exists) {
			fx_warp_tex_b = loadShader(path);
			println("load shader:",path);
		}
	} else {
		fx_shader_flip(fx_warp_tex_b,on_g,filter_is,source,null);

		fx_warp_tex_b.set("texture_source",source);
		fx_warp_tex_b.set("resolution_source",source.width,source.height);
		fx_warp_tex_b.set("texture_layer",layer);
		fx_warp_tex_b.set("resolution_layer",layer.width,layer.height);

		// external param
		fx_warp_tex_b.set("strength",strength);

		 // rendering
    render_shader(fx_warp_tex_b,pg_warp_tex_b,source,on_g,filter_is);
	}

	// end
	reset_reverse_g(false);
	if(on_g) {
		return null;
	} else {
		return pg_warp_tex_b; 
	}
}
/**
* SHADER FX
* @see @stanlepunk
* @see https://github.com/StanLepunK/Shader
* v 0.9.3
* 2019-2019
*/
int NO_FX = -1;
// CONSTANT FX POST
int FX_TEMPLATE = 0;

int FX_BLUR_GAUSSIAN = 200;
int FX_BLUR_RADIAL = 201;
int FX_BLUR_CIRCULAR = 202;

int FX_COLOUR_CHANGE_A = 300;
int FX_COLOUR_CHANGE_B = 301;

int FX_DATAMOSH = 400;
int FX_DITHER_BAYER_8 = 408;

int FX_FLIP = 600;

int FX_GRAIN = 700;
int FX_GRAIN_SCATTER = 701;

int FX_HALFTONE_DOT = 800;
int FX_HALFTONE_LINE = 801;
int FX_HALFTONE_MULTI = 810;

int FX_IMAGE = 900;

int FX_LEVEL = 12_00;

int FX_MASK = 13_00;
int FX_MIX = 13_01;

int FX_PIXEL = 16_00;

int FX_REAC_DIFF = 18_00;

int FX_SPLIT_RGB = 19_50;

int FX_THRESHOLD = 20_00; // don't work
int FX_TOON = 20_20; // don't work


int FX_WARP_PROC = 23_00;

int FX_WARP_TEX_A = 23_10;
int FX_WARP_TEX_B = 23_11;


// CONSTANT FX BACKGROUND
// here we start arbitrarily at 10_000 to no conflict with FX constant arounf 1_000.

int FX_BG_TEMPLATE = 1;

int FX_BG_CELLULAR = 3_0000;

int FX_BG_HEART = 8_0000;

int FX_BG_NECKLACE = 14_0000;

int FX_BG_NEON = 14_0000;

int FX_BG_PSY = 16_0000;

int FX_BG_SNOW = 19_0000;

int FX_BG_VORONOI_HEX = 22_0000;


// get method
public FX get_fx(ArrayList<FX> fx_list, int target) {
	if(fx_list != null && target < fx_list.size()) {
		return fx_list.get(target);
	} else {
		return null;
	}
}

public FX get_fx(ArrayList<FX> fx_list, String name) {
	FX buffer = null;
	if(fx_list != null && fx_list.size() > 0) {	
		for(FX fx : fx_list) {
			if(fx.get_name().equals(name)){
				buffer = fx;
				break;
			}
		}
	} 
	return buffer;
}



// init method
public void init_fx(ArrayList<FX> fx_list, String name, int type) {
	init_fx(fx_list,name,type,-1, null, null, null,-1,null,null);
}


public void init_fx(ArrayList<FX> fx_list, String name, int type, int id, String author, String pack, String version, int revision, String [] name_slider, String [] name_button) {
	boolean exist = false;

	if(fx_list != null && fx_list.size() > 0) {
		for(FX fx : fx_list) {
			if(fx.get_name().equals(name)) {
				exist = true;
				break;
			}
		}
		if(!exist) {
			add_fx_to_manager(fx_list,name,type,id,author,pack,version,revision,name_slider,name_button);
		}
	} else {
		add_fx_to_manager(fx_list,name,type,id,author,pack,version,revision,name_slider,name_button);
	}
}


// add FX
public void add_fx_to_manager(ArrayList<FX> fx_list, String name, int type, int id, String author, String pack, String version, int revision, String [] name_slider, String [] name_button) {
	FX fx = new FX();
	fx.set_name(name);
	fx.set_type(type);
	fx.set_id(id);
	if(author != null) fx.set_author(author);
	fx.set_pack(pack);
	if(version != null) fx.set_version(version);
	fx.set_revision(revision);
	if(name_slider != null) fx.set_name_slider(name_slider);
	if(name_button != null) fx.set_name_button(name_button);
	fx_list.add(fx);
}






/**
* SELECT FX
*/

// POST FX from FX class
public void select_fx_post(PImage main, PImage layer_a, PImage layer_b, FX... fx) {
	for(int i = 0 ; i < fx.length ;i++) {
		if(fx[i] != null) {
			if(fx[i].get_type() == FX_MIX) {
				fx_mix(main,layer_a,fx[i]);
			} else if(fx[i].get_type() == FX_BLUR_GAUSSIAN) {
				fx_blur_gaussian(main,fx[i]); 
			} else if(fx[i].get_type() == FX_BLUR_CIRCULAR) {
				fx_blur_circular(main,fx[i]);
			} else if(fx[i].get_type() == FX_BLUR_RADIAL) {
				fx_blur_radial(main,fx[i]);
			} else if(fx[i].get_type() == FX_COLOUR_CHANGE_A) {
				fx_colour_change_a(main,fx[i]);
			} else if(fx[i].get_type() == FX_COLOUR_CHANGE_B) {
				fx_colour_change_b(main,fx[i]);
			} else if(fx[i].get_type() == FX_DATAMOSH) {
				fx_datamosh(main,fx[i]);
			} else if(fx[i].get_type() == FX_DITHER_BAYER_8) {
				fx_dither_bayer_8(main,fx[i]);
			} else if(fx[i].get_type() == FX_FLIP) {
				fx_flip(main,fx[i]);
			} else if(fx[i].get_type() == FX_GRAIN) {
				 fx_grain(main,fx[i]);
			} else if(fx[i].get_type() == FX_GRAIN_SCATTER) {
				fx_grain_scatter(main,fx[i]);
			} else if(fx[i].get_type() == FX_HALFTONE_DOT) {
				fx_halftone_dot(main,fx[i]);
			} else if(fx[i].get_type() == FX_HALFTONE_LINE) {
				fx_halftone_line(main,fx[i]); 
			} else if(fx[i].get_type() == FX_HALFTONE_LINE) {
				fx_halftone_line(main,fx[i]); 
			} else if(fx[i].get_type() == FX_HALFTONE_MULTI) {
				fx_halftone_multi(main,fx[i]); 
			} else if(fx[i].get_type() == FX_IMAGE) {
				fx_image(main,fx[i]);
			} else if(fx[i].get_type() == FX_MASK) {
				fx_mask(main,layer_a,fx[i]); 
			} else if(fx[i].get_type() == FX_PIXEL) {
				fx_pixel(main,fx[i]);
			} else if(fx[i].get_type() == FX_REAC_DIFF) {
				fx_reaction_diffusion(main,fx[i]);
			} else if(fx[i].get_type() == FX_SPLIT_RGB) {
				fx_split_rgb(main,fx[i]); 
			} else if(fx[i].get_type() == FX_THRESHOLD) {
				fx_threshold(main,fx[i]);
			} else if(fx[i].get_type() == FX_WARP_PROC) {
				fx_warp_proc(main,fx[i]); 
			} else if(fx[i].get_type() == FX_WARP_TEX_A) {
				fx_warp_tex_a(main,layer_a,layer_b,fx[i]); 
			} else if(fx[i].get_type() == FX_WARP_TEX_B) {
				fx_warp_tex_b(main,layer_a,fx[i]); 
			} else {
				printErrTempo(60,"method select_fx_post(): fx",fx[i].get_name(),fx[i].get_type(),"don't match with any fx available");
			}
		} else {
			printErrTempo(60,"method select_fx_post(): fx",i,"is",fx[i],"maybe fx need to be init or instantiate");
		}
		   
	}
}


// BACKGROUND FX from FX class
public void select_fx_background(FX fx) {
	if(fx != null) {
		if(fx.get_type() == FX_BG_TEMPLATE) {
			fx_bg_template(fx);
		} else if(fx.get_type() == FX_BG_CELLULAR) {
			fx_bg_cellular(fx);
		} else if(fx.get_type() == FX_BG_HEART) {
			fx_bg_heart(fx);
		} else if(fx.get_type() == FX_BG_NECKLACE) {
			fx_bg_necklace(fx);
		} else if(fx.get_type() == FX_BG_NEON) {
			fx_bg_neon(fx);
		} else if(fx.get_type() == FX_BG_PSY) {
			fx_bg_psy(fx);
		} else if(fx.get_type() == FX_BG_SNOW) {
			fx_bg_snow(fx);
		} else if(fx.get_type() == FX_BG_VORONOI_HEX) {
			fx_bg_voronoi_hex(fx);
		} else {
			fx_bg_template(fx);
		}
	} else {
		printErrTempo(60,"method select_fx_background(): fx is",fx,"maybe fx need to be init or instantiate");
	}
	    
}
















/**
prepare your setting
v 0.1.1
*/

// single
public void fx_set_mode(ArrayList<FX> fx_list, String name, int mode) {
	fx_set(fx_list,0,name,mode);
}

public void fx_set_num(ArrayList<FX> fx_list, String name, int num) {
	fx_set(fx_list,1,name,num);
}

public void fx_set_quality(ArrayList<FX> fx_list, String name, float quality) {
	fx_set(fx_list,2,name,quality);
}

public void fx_set_time(ArrayList<FX> fx_list, String name, float time) {
	fx_set(fx_list,3,name,time);
}


public void fx_set_on_g(ArrayList<FX> fx_list, String name, boolean on_g) {
	fx_set(fx_list,4,name,on_g);
}

public void fx_set_pg_filter(ArrayList<FX> fx_list, String name, boolean filter_is) {
	fx_set(fx_list,5,name,filter_is);
}

// double
public void fx_set_scale(ArrayList<FX> fx_list, String name, float... arg) {
	set_fx_float_2(fx_list,10,name,arg);
}

public void fx_set_resolution(ArrayList<FX> fx_list, String name, float... arg) {
	set_fx_float_2(fx_list,11,name,arg);
}

// triple
public void fx_set_strength(ArrayList<FX> fx_list, String name, float... arg) {
	set_fx_float_3(fx_list,20,name,arg);
}

public void fx_set_angle(ArrayList<FX> fx_list, String name, float... arg) {
	set_fx_float_3(fx_list,21,name,arg);
}

public void fx_set_threshold(ArrayList<FX> fx_list, String name, float... arg) {
	set_fx_float_3(fx_list,22,name,arg);
}

public void fx_set_pos(ArrayList<FX> fx_list, String name, float... arg) {
	set_fx_float_3(fx_list,23,name,arg);
}

public void fx_set_size(ArrayList<FX> fx_list, String name, float... arg) {
	set_fx_float_3(fx_list,24,name,arg);
}

public void fx_set_offset(ArrayList<FX> fx_list, String name, float... arg) {
	set_fx_float_3(fx_list,25,name,arg);
}

public void fx_set_speed(ArrayList<FX> fx_list, String name, float... arg) {
	set_fx_float_3(fx_list,26,name,arg);
}

// quadruple
public void fx_set_level_source(ArrayList<FX> fx_list, String name, float... arg) {
	set_fx_float_4(fx_list,30,name,arg);
}

public void fx_set_level_layer(ArrayList<FX> fx_list, String name, float... arg) {
	set_fx_float_4(fx_list,31,name,arg);
}

public void fx_set_colour(ArrayList<FX> fx_list, String name, float... arg) {
	set_fx_float_4(fx_list,32,name,arg);
}

public void fx_set_cardinal(ArrayList<FX> fx_list, String name, float... arg) {
	set_fx_float_4(fx_list,33,name,arg);
}

// single
public void fx_set_hue(ArrayList<FX> fx_list, String name, float hue) {
	fx_set(fx_list,200,name,hue);
}

public void fx_set_saturation(ArrayList<FX> fx_list, String name, float saturation) {
	fx_set(fx_list,201,name,saturation);
}

public void fx_set_brightness(ArrayList<FX> fx_list, String name, float brightness) {
	fx_set(fx_list,202,name,brightness);
}

public void fx_set_red(ArrayList<FX> fx_list, String name, float red) {
	fx_set(fx_list,300,name,red);
}

public void fx_set_green(ArrayList<FX> fx_list, String name, float green) {
	fx_set(fx_list,301,name,green);
}

public void fx_set_blue(ArrayList<FX> fx_list, String name, float blue) {
	fx_set(fx_list,302,name,blue);
}

public void fx_set_alpha(ArrayList<FX> fx_list, String name, float alpha) {
	fx_set(fx_list,400,name,alpha);
}


// modulair param
// triple
public void fx_set_matrix(ArrayList<FX> fx_list, String name, int target, float... arg) {
	int which = 40+target;
	set_fx_float_3(fx_list,which,name,arg);
}

// double
public void fx_set_pair(ArrayList<FX> fx_list, String name, int target, float... arg) {
	int which = 50+target;
	set_fx_float_2(fx_list,which,name,arg);
}

// single boolean
public void fx_set_event(ArrayList<FX> fx_list, String name, int target, boolean... arg) {
	int which = 100+target;
	set_fx_boolean_4(fx_list,which,name,arg);
}


/**
* main setting methode
*/
public void set_fx_float_2(ArrayList<FX> fx_list, int which, String name, float... arg) {
	if(arg.length == 1) {
		fx_set(fx_list,which,name,arg[0]);
	} else if(arg.length == 2) {
		fx_set(fx_list,which,name,arg[0],arg[1]);
	} else if(arg.length > 2) {
		fx_set(fx_list,which,name,arg[0],arg[1]);
	}
}

public void set_fx_float_3(ArrayList<FX> fx_list, int which, String name, float... arg) {
	if(arg.length == 1) {
		fx_set(fx_list,which,name,arg[0]);
	} else if(arg.length == 2) {
		fx_set(fx_list,which,name,arg[0],arg[1]);
	} else if(arg.length == 3) {
		fx_set(fx_list,which,name,arg[0],arg[1],arg[2]);
	} else if(arg.length > 3) {
		fx_set(fx_list,which,name,arg[0],arg[1],arg[2]);
	}
}

public void set_fx_float_4(ArrayList<FX> fx_list, int which, String name, float... arg) {
	if(arg.length == 1) {
		fx_set(fx_list,which,name,arg[0]);
	} else if(arg.length == 2) {
		fx_set(fx_list,which,name,arg[0],arg[1]);
	} else if(arg.length == 3) {
		fx_set(fx_list,which,name,arg[0],arg[1],arg[2]);
	} else if(arg.length == 4) {
		fx_set(fx_list,which,name,arg[0],arg[1],arg[2],arg[3]);
	} else if(arg.length > 4) {
		fx_set(fx_list,which,name,arg[0],arg[1],arg[2],arg[3]);
	}
}



public void set_fx_boolean_4(ArrayList<FX> fx_list, int which, String name, boolean... arg) {
	if(arg.length == 1) {
		fx_set(fx_list,which,name,arg[0]);
	} else if(arg.length == 2) {
		fx_set(fx_list,which,name,arg[0],arg[1]);
	} else if(arg.length == 3) {
		fx_set(fx_list,which,name,arg[0],arg[1],arg[2]);
	} else if(arg.length == 4) {
		fx_set(fx_list,which,name,arg[0],arg[1],arg[2],arg[3]);
	} else if(arg.length > 4) {
		fx_set(fx_list,which,name,arg[0],arg[1],arg[2],arg[3]);
	}
}

public void fx_set(ArrayList<FX> fx_list, int which_setting, String name, Object... arg) {
	if(fx_list != null) {
		if(fx_list.size() > 0) {
			for(FX fx : fx_list) {
				if(fx.get_name().equals(name)) {
					fx.set(which_setting,arg);
				}
			}	
		} 
	}
}




































/**
* path shader
* v 0.3.2
*/
// post fx path
String fx_post_rope_path = null;
boolean fx_post_rope_path_exists = false;

public boolean fx_post_path_exist() {
	return fx_post_rope_path_exists;
}

public String get_fx_post_path() {
	if(fx_post_rope_path == null) {
		fx_post_rope_path = "data/shader/fx_post/";
		fx_post_rope_path_exists = true;
	} else {
		File f = new File(sketchPath()+"/"+fx_post_rope_path);
		if(!f.isDirectory()) {
			fx_post_rope_path_exists = false;
			printErrTempo(60,"method get_fx_post_path()",fx_post_rope_path,"no folder found");
		} else {
			fx_post_rope_path_exists = true;
		}

	}
	return fx_post_rope_path;
}

public void set_fx_post_path(String path) {
	fx_post_rope_path = path;
}




// background fx path
String fx_bg_rope_path = null;
boolean fx_bg_rope_path_exists = false;

public boolean fx_bg_path_exist() {
	return fx_bg_rope_path_exists;
}

public String get_fx_bg_path() {
	if(fx_bg_rope_path == null) {
		fx_bg_rope_path = "shader/fx_bg/";
		fx_bg_rope_path_exists = true;
	} else {
		File f = new File(fx_bg_rope_path);
		if(!f.exists()) {
			printErrTempo(60,"get_fx_bg_path()",fx_bg_rope_path,"no folder found");
		} else {
			fx_bg_rope_path_exists = true;
		}

	}
	return fx_bg_rope_path;
}

public void set_fx_bg_path(String path) {
	fx_bg_rope_path = path;
}
















/**
* send information to shader.glsl to flip the source in case this one is a PGraphics or PImage
* v 0.2.1
*/
public void fx_shader_flip(PShader shader, boolean on_g, boolean filter_is, PImage source, PImage layer) {
	if(on_g) {
		set_shader_flip(shader,source);
	}
  // reverse for the case filter is active
	if(!on_g && filter_is) {
		shader.set("flip_source",1,0);
	}

  // case there is layer to manage
	if(layer != null) {
		if(graphics_is(layer).equals("PGraphics")) {
			shader.set("flip_layer",0,0);
		} else {
			if(on_g) {
				shader.set("flip_layer",1,0);
			} 
			if(!on_g && filter_is) {
				shader.set("flip_layer",1,0);
			}
		}
	} 
}




public void set_shader_flip(PShader ps, PImage... img) {
	int num = img.length;
	ps.set("flip_source",1,0);
	
	if(graphics_is(img[0]).equals("PGraphics") && !reverse_g_source_is()) {
		ps.set("flip_source",0,0);
		reverse_g_source(true);
	}

	if(num == 2 && img[1] != null) {
		ps.set("flip_layer",1,0);
		if(graphics_is(img[1]).equals("PGraphics") && !reverse_g_layer_is()) {
			ps.set("flip_layer",0,0);
			reverse_g_layer(true);
		}
	}
}




public void set_shader_resolution(PShader ps, ivec2 canvas, boolean on_g) {
	if(!on_g && canvas != null) {
		ps.set("resolution",canvas.x,canvas.y);
	} else {
		ps.set("resolution",width,height);
	}
}


















/**
reverse graphics for the case is not a texture but a direct shader
*/
boolean filter_reverse_g_source;
boolean filter_reverse_g_layer;
public boolean reverse_g_source_is() {
	return filter_reverse_g_source;
}

public boolean reverse_g_layer_is() {
	return filter_reverse_g_layer;
}

public void reverse_g_source(boolean state){
	filter_reverse_g_source = state;
}

public void reverse_g_layer(boolean state){
	filter_reverse_g_layer = state;
}

public void reset_reverse_g(boolean state){
	filter_reverse_g_source = state;
	filter_reverse_g_layer = state;
}








/**
* RENDER FX
* this method test if the shader must be display on the main Processing render or return a PGraphics
* v 0.1.1
*/
public void render_shader(PShader shader, PGraphics pg, PImage src, boolean on_g, boolean filter_is) {
	if(on_g) {
		render_filter_g(shader);
	} else {
		if(filter_is) {
			render_filter_pgraphics(shader,pg);
		} else {
			render_shader_pgraphics(shader,pg,src);
		}
	}
}

public void render_shader_pgraphics(PShader ps, PGraphics pg, PImage src) {
	if(pg != null && pg.pixels != null) {
  	pg.beginDraw();
  	pg.shader(ps);
  	pg.image(src,0,0,src.width,src.height);
  	pg.resetShader();
  	pg.endDraw();
  }
}

public void render_filter_pgraphics(PShader ps, PGraphics pg) {
	if(pg != null) {
  	pg.filter(ps);
  } 
}

public void render_filter_g(PShader ps) {
	filter(ps);
}
/**
ROPE GLSL METHOD
v 0.0.6
* Copyleft (c) 2019-2019
* @author @stanlepunk
* @see https://github.com/StanLepunK/Rope_framework
*/

/**
transcription of few common glsl method

/**
mix
*/
public float mix(float x, float y, float a) {
  return x*(1-a)+y*a;
}

public vec2 mix(vec2 x, vec2 y, vec2 a) {
  return vec2(mix(x.x,y.x,a.x),mix(x.y,y.y,a.y));
}

public vec3 mix(vec3 x, vec3 y, vec3 a) {
  return vec3(mix(x.x,y.x,a.x),mix(x.y,y.y,a.y),mix(x.z,y.z,a.z));
}

public vec4 mix(vec4 x, vec4 y, vec4 a) {
  return vec4(mix(x.x,y.x,a.x),mix(x.y,y.y,a.y),mix(x.z,y.z,a.z),mix(x.w,y.w,a.w));
}

/**
fract
*/
public float fract(float x) {
  return x - floor(x);
}

public vec2 fract(vec2 v) {
  return vec2(fract(v.x),fract(v.y));
}

public vec3 fract(vec3 v) {
  return vec3(fract(v.x),fract(v.y),fract(v.z));
}

public vec4 fract(vec4 v) {
  return vec4(fract(v.x),fract(v.y),fract(v.z),fract(v.w));
}

/**
sign
*/
public float sign(float x) {
  if(x < 0 ) {
    return -1.f;
  } else if(x > 0) {
    return 1.f ;
  } else return 0.0f;
}

public vec2 sign(vec2 x) {
  return vec2(sign(x.x),sign(x.y));
}

public vec3 sign(vec3 x) {
  return vec3(sign(x.x),sign(x.y),sign(x.z));
}

public vec4 sign(vec4 x) {
  return vec4(sign(x.x),sign(x.y),sign(x.z),sign(x.w));
}


public int sign(int x) {
  return PApplet.parseInt(sign(PApplet.parseFloat(x)));
}

public ivec2 sign(ivec2 x) {
  return ivec2(sign(x.x),sign(x.y));
}

public ivec3 sign(ivec3 x) {
  return ivec3(sign(x.x),sign(x.y),sign(x.z));
}

public ivec4 sign(ivec4 x) {
  return ivec4(sign(x.x),sign(x.y),sign(x.z),sign(x.w));
}


/**
step
*/
public float step(float edge, float x) {
  if(x < edge) return 0; else return 1;
}

public vec2 step(vec2 edge, vec2 x) {
  return vec2(step(edge.x,x.x),step(edge.y,x.y));
}

public vec3 step(vec3 edge, vec3 x) {
  return vec3(step(edge.x,x.x),step(edge.y,x.y),step(edge.z,x.z));
}

public vec4 step(vec4 edge, vec4 x) {
  return vec4(step(edge.x,x.x),step(edge.y,x.y),step(edge.z,x.z),step(edge.w,x.w));
}


/**
smoothstep
*/
public float smoothstep(float edge0, float edge1, float x) {
  if(x <= edge0) {
    return 0; 
  } else if(x >= edge1) {
    return 1;
  } else {
    float t = clamp((x-edge0)/(edge1-edge0),0.f,1.f);
    return t*t*(3.f-2.f*t);
  }
}

public vec2 smoothstep(vec2 edge0, vec2 edge1, vec2 x) {
  return vec2(smoothstep(edge0.x,edge1.x,x.x),smoothstep(edge0.y,edge1.y,x.y));
}

public vec3 smoothstep(vec3 edge0, vec3 edge1, vec3 x) {
  return vec3(smoothstep(edge0.x,edge1.x,x.x),smoothstep(edge0.y,edge1.y,x.y),smoothstep(edge0.z,edge1.z,x.z));
}

public vec4 smoothstep(vec4 edge0, vec4 edge1, vec4 x) {
  return vec4(smoothstep(edge0.x,edge1.x,x.x),smoothstep(edge0.y,edge1.y,x.y),smoothstep(edge0.z,edge1.z,x.z),smoothstep(edge0.w,edge1.w,x.w));
}



/*
mod
*/
public float mod(float x, float y) {
  return x-y*floor(x/y);
}

public vec2 mod(vec2 x, vec2 y) {
  return vec2(mod(x.x,y.x),mod(x.y,y.y));
}

public vec3 mod(vec3 x, vec3 y) {
  return vec3(mod(x.x,y.x),mod(x.y,y.y),mod(x.z,y.z));
}

public vec4 mod(vec4 x, vec4 y) {
  return vec4(mod(x.x,y.x),mod(x.y,y.y),mod(x.z,y.z),mod(x.w,y.w));
}

public ivec2 mod(ivec2 x, ivec2 y) {
  return ivec2(mod(x.x,y.x),mod(x.y,y.y));
}

public ivec3 mod(ivec3 x, ivec3 y) {
  return ivec3(mod(x.x,y.x),mod(x.y,y.y),mod(x.z,y.z));
}

public ivec4 mod(ivec4 x, ivec4 y) {
  return ivec4(mod(x.x,y.x),mod(x.y,y.y),mod(x.z,y.z),mod(x.w,y.w));
}

/**
clamp
*/
public float clamp(float x, float min, float max) {
  return min(max(x,min),max);
}

public vec2 clamp(vec2 x, vec2 min, vec2 max) {
  return min(max(x,min),max);
}

public vec3 clamp(vec3 x, vec3 min, vec3 max) {
  return min(max(x,min),max);
}

public vec4 clamp(vec4 x, vec4 min, vec4 max) {
  return min(max(x,min),max);
}




/**
equal
*/
public boolean equal(float x, float y) {
  return x==y?true:false;
}

public boolean equal(int x, int y) {
  return equal((float)x,(float)y);
}

// with vec
public bvec2 equal(vec2 x, vec2 y) {
  if(x != null && y != null) {
    return bvec2(equal(x.x,y.x),equal(x.y,y.y));
  } else return null;
}

public bvec3 equal(vec3 x, vec3 y) {
  if(x != null && y != null) {
    return bvec3(equal(x.x,y.x),equal(x.y,y.y),equal(x.z,y.z));
  } else return null;
}

public bvec4 equal(vec4 x, vec4 y) {
  if(x != null && y != null) {
    return bvec4(equal(x.x,y.x),equal(x.y,y.y),equal(x.z,y.z),equal(x.w,y.w));
  } else return null;
}

// width ivec
public bvec2 equal(ivec2 x, ivec2 y) {
  if(x != null && y != null) {
    return bvec2(equal(x.x,y.x),equal(x.y,y.y));
  } else return null;
}

public bvec3 equal(ivec3 x, ivec3 y) {
  if(x != null && y != null) {
    return bvec3(equal(x.x,y.x),equal(x.y,y.y),equal(x.z,y.z));
  } else return null;
}

public bvec4 equal(ivec4 x, ivec4 y) {
  if(x != null && y != null) {
    return bvec4(equal(x.x,y.x),equal(x.y,y.y),equal(x.z,y.z),equal(x.w,y.w));
  } else return null;
}




/**
lessThan
*/
public boolean lessThan(float x, float y) {
  return x<y?true:false;
}

public boolean lessThan(int x, int y) {
  return lessThan((float)x,(float)y);
}

// with vec
public bvec2 lessThan(vec2 x, vec2 y) {
  if(x != null && y != null) {
    return bvec2(lessThan(x.x,y.x),lessThan(x.y,y.y));
  } else return null;
}

public bvec3 lessThan(vec3 x, vec3 y) {
  if(x != null && y != null) {
    return bvec3(lessThan(x.x,y.x),lessThan(x.y,y.y),lessThan(x.z,y.z));
  } else return null;
}

public bvec4 lessThan(vec4 x, vec4 y) {
  if(x != null && y != null) {
    return bvec4(lessThan(x.x,y.x),lessThan(x.y,y.y),lessThan(x.z,y.z),lessThan(x.w,y.w));
  } else return null;
}

// width ivec
public bvec2 lessThan(ivec2 x, ivec2 y) {
  if(x != null && y != null) {
    return bvec2(lessThan(x.x,y.x),lessThan(x.y,y.y));
  } else return null;
}

public bvec3 lessThan(ivec3 x, ivec3 y) {
  if(x != null && y != null) {
    return bvec3(lessThan(x.x,y.x),lessThan(x.y,y.y),lessThan(x.z,y.z));
  } else return null;
}

public bvec4 lessThan(ivec4 x, ivec4 y) {
  if(x != null && y != null) {
    return bvec4(lessThan(x.x,y.x),lessThan(x.y,y.y),lessThan(x.z,y.z),lessThan(x.w,y.w));
  } else return null;
}





/**
greaterThan
*/
public boolean greaterThan(float x, float y) {
  return x>y?true:false;
}

public boolean greaterThan(int x, int y) {
  return greaterThan((float)x,(float)y);
}

// with vec
public bvec2 greaterThan(vec2 x, vec2 y) {
  if(x != null && y != null) {
    return bvec2(greaterThan(x.x,y.x),greaterThan(x.y,y.y));
  } else return null; 
}

public bvec3 greaterThan(vec3 x, vec3 y) {
  if(x != null && y != null) {
    return bvec3(greaterThan(x.x,y.x),greaterThan(x.y,y.y),greaterThan(x.z,y.z));
  } else return null; 
}

public bvec4 greaterThan(vec4 x, vec4 y) {
  if(x != null && y != null) {
    return bvec4(greaterThan(x.x,y.x),greaterThan(x.y,y.y),greaterThan(x.z,y.z),greaterThan(x.w,y.w));
  } else return null; 
}

// width ivec
public bvec2 greaterThan(ivec2 x, ivec2 y) {
  if(x != null && y != null) {
    return bvec2(greaterThan(x.x,y.x),greaterThan(x.y,y.y));
  } else return null; 
}

public bvec3 greaterThan(ivec3 x, ivec3 y) {
  if(x != null && y != null) {
    return bvec3(greaterThan(x.x,y.x),greaterThan(x.y,y.y),greaterThan(x.z,y.z));
  } else return null; 
}

public bvec4 greaterThan(ivec4 x, ivec4 y) {
  if(x != null && y != null) {
    return bvec4(greaterThan(x.x,y.x),greaterThan(x.y,y.y),greaterThan(x.z,y.z),greaterThan(x.w,y.w));
  } else return null; 
}






/**
greaterThanEqual
*/
public boolean greaterThanEqual(float x, float y) {
  return x>=y?true:false;
}

public boolean greaterThanEqual(int x, int y) {
  return greaterThanEqual((float)x,(float)y);
}

// with vec
public bvec2 greaterThanEqual(vec2 x, vec2 y) {
  if(x != null && y != null) {
    return bvec2(greaterThanEqual(x.x,y.x),greaterThanEqual(x.y,y.y));
  } else return null; 
}

public bvec3 greaterThanEqual(vec3 x, vec3 y) {
  if(x != null && y != null) {
    return bvec3(greaterThanEqual(x.x,y.x),greaterThanEqual(x.y,y.y),greaterThanEqual(x.z,y.z));
  } else return null; 
}

public bvec4 greaterThanEqual(vec4 x, vec4 y) {
  if(x != null && y != null) {
    return bvec4(greaterThanEqual(x.x,y.x),greaterThanEqual(x.y,y.y),greaterThanEqual(x.z,y.z),greaterThanEqual(x.w,y.w));
  } else return null; 
}

// width ivec
public bvec2 greaterThanEqual(ivec2 x, ivec2 y) {
  if(x != null && y != null) {
    return bvec2(greaterThanEqual(x.x,y.x),greaterThanEqual(x.y,y.y));
  } else return null; 
}

public bvec3 greaterThanEqual(ivec3 x, ivec3 y) {
  if(x != null && y != null) {
    return bvec3(greaterThanEqual(x.x,y.x),greaterThanEqual(x.y,y.y),greaterThanEqual(x.z,y.z));
  } else return null; 
}

public bvec4 greaterThanEqual(ivec4 x, ivec4 y) {
  if(x != null && y != null) {
    return bvec4(greaterThanEqual(x.x,y.x),greaterThanEqual(x.y,y.y),greaterThanEqual(x.z,y.z),greaterThanEqual(x.w,y.w));
  } else return null; 
}






/**
lessThanEqual
*/
public boolean lessThanEqual(float x, float y) {
  return x<=y?true:false;
}

public boolean lessThanEqual(int x, int y) {
  return lessThanEqual((float)x,(float)y);
}

// with vec
public bvec2 lessThanEqual(vec2 x, vec2 y) {
  if(x != null && y != null) {
    return bvec2(lessThanEqual(x.x,y.x),lessThanEqual(x.y,y.y));
  } else return null; 
}

public bvec3 lessThanEqual(vec3 x, vec3 y) {
  if(x != null && y != null) {
    return bvec3(lessThanEqual(x.x,y.x),lessThanEqual(x.y,y.y),lessThanEqual(x.z,y.z));
  } else return null; 
}

public bvec4 lessThanEqual(vec4 x, vec4 y) {
  if(x != null && y != null) {
    return bvec4(lessThanEqual(x.x,y.x),lessThanEqual(x.y,y.y),lessThanEqual(x.z,y.z),lessThanEqual(x.w,y.w));
  } else return null; 
}

// width ivec
public bvec2 lessThanEqual(ivec2 x, ivec2 y) {
  if(x != null && y != null) {
    return bvec2(lessThanEqual(x.x,y.x),lessThanEqual(x.y,y.y));
  } else return null; 
}

public bvec3 lessThanEqual(ivec3 x, ivec3 y) {
  if(x != null && y != null) {
    return bvec3(lessThanEqual(x.x,y.x),lessThanEqual(x.y,y.y),lessThanEqual(x.z,y.z));
  } else return null; 
}

public bvec4 lessThanEqual(ivec4 x, ivec4 y) {
  if(x != null && y != null) {
    return bvec4(lessThanEqual(x.x,y.x),lessThanEqual(x.y,y.y),lessThanEqual(x.z,y.z),lessThanEqual(x.w,y.w));
  } else return null; 
}







/**
all
v 0.0.2
*/
public boolean all(bvec2 b) {
  if(b != null) {
    boolean [] list = b.array();
    boolean result = true;
    for(int i = 0 ; i < list.length ; i++) {
      if(list[i] == false) {
        result = false;
        break;
      }
    }
    return result;
  } else {
    printErr("method all(bvec2 b) return false because argument is",b);
    return false;
  }
}

public boolean all(bvec3 b) {
  if(b != null) {
    boolean [] list = b.array();
    boolean result = true;
    for(int i = 0 ; i < list.length ; i++) {
      if(list[i] == false) {
        result = false;
        break;
      }
    }
    return result;
  } else {
    printErr("method all(bvec3 b) return false because argument is",b);
    return false;
  }
}

public boolean all(bvec4 b) {
  if(b != null) {
    boolean [] list = b.array();
    boolean result = true;
    for(int i = 0 ; i < list.length ; i++) {
      if(list[i] == false) {
        result = false;
        break;
      }
    }
    return result;
  } else {
    printErr("method all(bvec4 b) return false because argument is",b);
    return false;
  }
}

public boolean all(bvec5 b) {
  if(b != null) {
    boolean [] list = b.array();
    boolean result = true;
    for(int i = 0 ; i < list.length ; i++) {
      if(list[i] == false) {
        result = false;
        break;
      }
    }
    return result;
  } else {
    printErr("method all(bvec5 b) return false because argument is",b);
    return false;
  }
}

public boolean all(bvec6 b) {
  if(b != null) {
    boolean [] list = b.array();
    boolean result = true;
    for(int i = 0 ; i < list.length ; i++) {
      if(list[i] == false) {
        result = false;
        break;
      }
    }
    return result;
  } else {
    printErr("method all(bvec6 b) return false because argument is",b);
    return false;
  }
}




/**
any
*/
public boolean any(bvec2 b) {
  if(b != null) {
    boolean [] list = b.array();
    boolean result = false;
    for(int i = 0 ; i < list.length ; i++) {
      if(list[i] == true) {
        result = true;
        break;
      }
    }
    return result;
  } else {
    printErr("method any() return false because argument is",b);
    return false;
  }
}

public boolean any(bvec3 b) {
  if(b != null) {
    boolean [] list = b.array();
    boolean result = false;
    for(int i = 0 ; i < list.length ; i++) {
      if(list[i] == true) {
        result = true;
        break;
      }
    }
    return result;
  } else {
    printErr("method any() return false because argument is",b);
    return false;
  }
}

public boolean any(bvec4 b) {
  if(b != null) {
    boolean [] list = b.array();
    boolean result = false;
    for(int i = 0 ; i < list.length ; i++) {
      if(list[i] == true) {
        result = true;
        break;
      }
    }
    return result;
  } else {
    printErr("method any() return false because argument is",b);
    return false;
  }
}

public boolean any(bvec5 b) {
  if(b != null) {
    boolean [] list = b.array();
    boolean result = false;
    for(int i = 0 ; i < list.length ; i++) {
      if(list[i] == true) {
        result = true;
        break;
      }
    }
    return result;
  } else {
    printErr("method any() return false because argument is",b);
    return false;
  }
}

public boolean any(bvec6 b) {
  if(b != null) {
    boolean [] list = b.array();
    boolean result = false;
    for(int i = 0 ; i < list.length ; i++) {
      if(list[i] == true) {
        result = true;
        break;
      }
    }
    return result;
  } else {
    printErr("method any() return false because argument is",b);
    return false;
  }
}
/**
* Rope framework image
* v 0.5.10
* Copyleft (c) 2014-2019
*
* dependencies
* Processing 3.5.3
*
* @author @stanlepunk
* @see https://github.com/StanLepunK/Rope_framework
*/

/**
* entry return the pixel position from x,y coordinate
* v 0.0.2
*/
// with coordinate
public int entry(ivec2 pos, boolean constrain_is) {
  return entry(g,pos.x(),pos.y(), constrain_is);
}

public int entry(vec2 pos, boolean constrain_is) {
  return entry(g,pos.x(),pos.y(), constrain_is);
}

public int entry(float x, float y, boolean constrain_is) {
  return entry(g,x,y,constrain_is);
}

public int entry(PGraphics pg, ivec2 pos, boolean constrain_is) {
  return entry(pg,pos.x(),pos.y(),constrain_is);
}

public int entry(PGraphics pg, vec2 pos, boolean constrain_is) {
  return entry(pg,pos.x(),pos.y(),constrain_is);
}

public int entry(PGraphics pg, float x, float y, boolean constrain_is) {
  //int max = pg.pixels.length;
  int rank = (int)y * pg.width + (int)x;
  return entry(pg, rank, constrain_is);
}

// with rank
public int entry(int rank, boolean constrain_is) {
  return entry(g,rank,constrain_is);
}

public int entry(PGraphics pg, int rank, boolean constrain_is) {
  int max = pg.pixels.length;
  if(constrain_is) {
    if(rank < 0) rank = 0;
    if(rank >= max) rank = max -1;
  } else {
    if(rank < 0) rank = max-rank;
    if(rank >= max) rank = rank-max;
  }
  return rank;
}








/**
PATTERN GENERATOR
v 0.0.3
2018-2018
*/
public PGraphics pattern_noise(int w, int h, float... inc) {
  PGraphics pg ;
  noiseSeed((int)random(MAX_INT));
  if(w > 0 && h > 0 && inc.length > 0 && inc.length < 5) {
    float [] cm = getColorMode(false);
    colorMode(RGB,255,255,255,255);
    pg = createGraphics(w,h);
    float offset_x [] = new float[inc.length];
    float offset_y [] = new float[inc.length];
    float component [] = new float[inc.length];
    float max [] = new float[inc.length];
    if(inc.length == 1) {
      max[0] = g.colorModeZ;
    } else if (inc.length == 2) {
      max[0] = g.colorModeZ;
      max[1] = g.colorModeA;
    } else if (inc.length == 3) {
      max[0] = g.colorModeX;
      max[1] = g.colorModeY;
      max[2] = g.colorModeZ;
    } else if (inc.length == 4) {
      max[0] = g.colorModeX;
      max[1] = g.colorModeY;
      max[2] = g.colorModeZ;
      max[3] = g.colorModeA;
    }
    colorMode((int)cm[0],cm[1],cm[2],cm[3],cm[4]);

    
    pg.beginDraw();
    for(int i = 0 ; i < inc.length ; i++) {
      offset_y[i] = 0;
    }
    
    for(int x = 0 ; x < w ; x++) {
      for(int i = 0 ; i < inc.length ; i++) {
        offset_x[i] = 0;
      }
      for(int y = 0 ; y < h ; y++) {
        for(int i = 0 ; i < inc.length ; i++) {
          component[i] = map(noise(offset_x[i],offset_y[i]),0,1,0,max[i]);
        }
        int c = 0;
        if(inc.length == 1) c = color(component[0]);
        else if (inc.length == 2) c = color(component[0],component[1]);
        else if (inc.length == 3) c = color(component[0],component[1],component[2]);
        else if (inc.length == 4) c = color(component[0],component[1],component[2],component[3]);

        pg.set(x,y,c);
        for(int i = 0 ; i < inc.length ; i++) {
          offset_x[i] += inc[i];
        }
      }
      for(int i = 0 ; i < inc.length ; i++) {
        offset_y[i] += inc[i];
      }
    }
    pg.endDraw();
    return pg;
  } else {
    printErr("method pattern_noise(): may be problem with size:",w,h,"\nor with component color num >>>",inc.length,"<<< must be between 1 and 4");
    return null;
  }
}











/**
LAYER
v 0.1.0
2018-2018
*/
PGraphics [] rope_layer;
boolean warning_rope_layer;
int which_rope_layer = 0;

// init
public void init_layer() {
  init_layer(width,height,get_renderer(),1);
}

public void init_layer(int num) {
  init_layer(width,height,get_renderer(),num);
}

public void init_layer(int x, int y) {
  init_layer(x,y, get_renderer(),1);
}

public void init_layer(int x, int y, int num) {
  init_layer(x,y, get_renderer(),num);
}

public void init_layer(int x, int y, String type, int num) {
  rope_layer = new PGraphics[num];
  for(int i = 0 ; i < num ; i++) {
    rope_layer[i] = createGraphics(x,y,type);
  }
  
  if(!warning_rope_layer) {
    warning_rope_layer = true;
  }
  String warning = ("WARNING LAYER METHOD\nAll classical method used on the main rendering,\nwill return the PGraphics selected PGraphics layer :\nimage(), set(), get(), fill(), stroke(), rect(), ellipse(), pushMatrix(), popMatrix(), box()...\nto use those methods on the main PGraphics write g.image() for example");
  printErr(warning);
}

// begin and end draw
public void begin_layer() {
  if(get_layer() != null) {
    get_layer().beginDraw();
  }
}

public void end_layer() {
  if(get_layer() != null) {
    get_layer().endDraw();
  }
}



// num
public int get_layer_num() {
  if(rope_layer != null) {
    return  rope_layer.length ;
  } else return -1;  
}


// clear layer
public void clear_layer() {
  if(rope_layer != null && rope_layer.length > 0) {
    for(int i = 0 ; i < rope_layer.length ; i++) {
      String type = get_renderer(rope_layer[i]);
      int w = rope_layer[i].width;
      int h = rope_layer[i].height;
      rope_layer[i] = createGraphics(w,h,type);
    }
  } else {
    String warning = ("void clear_layer(): there is no layer can be clear maybe you forget to create one :)");
    printErr(warning);
  }
  
}

public void clear_layer(int target) {
  if(target > -1 && target < rope_layer.length) {
    String type = get_renderer(rope_layer[target]);
    int w = rope_layer[target].width;
    int h = rope_layer[target].height;
    rope_layer[target] = createGraphics(w,h,type);
  } else {
    String warning = ("void clear_layer(): target "+target+" is out the range of the layers available,\n no layer can be clear");
    printErr(warning);
  }
}




/**
GET LAYER
* May be the method can be improve by using a PGraphics template for buffering instead usin a calling method ????
*/
// get layer
public PGraphics get_layer() {
  return get_layer(which_rope_layer);
}


public PGraphics get_layer(int target) {
  if(rope_layer == null) {
    return g;
  } else if(target > -1 && target < rope_layer.length) {
    return rope_layer[target];
  } else {
    String warning = ("PGraphics get_layer(int target): target "+target+" is out the range of the layers available,\n instead target 0 is used");
    printErr(warning);
    return rope_layer[0];
  }
}

// select layer
public void select_layer(int target) {
  if(rope_layer != null) {
    if(target > -1 && target < rope_layer.length) {
      which_rope_layer = target;
    } else {
      which_rope_layer = 0;
      String warning = ("void select_layer(int target): target "+target+" is out the range of the layers available,\n instead target 0 is used");
      printErr(warning);
    }
  } else {
    printErrTempo(180,"void select_layer(): Your layer system has not been init use method init_layer() in first",frameCount);
  } 
}





















/**
PImage manager library
v 0.7.3
*/
public class R_Image_Manager {
  ArrayList<R_Image> library ;
  int which_img;

  public R_Image_Manager() {}

  private void build() {
    if(library == null) {
      library = new ArrayList<R_Image>();
    }
  }

  public void load(String... path_img) {
    build();
    for(int i = 0 ; i <path_img.length ; i++) {
      String [] temp = path_img[i].split("/");
      PImage img = loadImage(path_img[i]);
      R_Image rop_img = new R_Image(img,temp[temp.length-1],i);
      library.add(rop_img);
    }  
  }

  public void add(PImage img_src) {
    build();
    R_Image rop_img = new R_Image(img_src, "unknow" ,library.size());
    library.add(rop_img);
  }

  public void add(PImage img_src, String name) {
    build();
    R_Image rop_img = new R_Image(img_src, name, library.size());
    library.add(rop_img);
  }

  public void clear() {
    if(library != null) {
      library.clear();
    }
  }



  public void select(int which_one) {
    which_img = which_one ;
  }

  public void select(String target_name) {
    if(library.size() > 0) {
      for(int i = 0 ; i < library.size() ; i++) {
        if(target_name.equals(library.get(i).name)) {
          which_img = i ;
          break ;
        }
      }
    } else {
      printErr("the String target name don't match with any name of image library") ;
    }
  }


  public int size() {
    if(library != null) {
      return library.size() ;
    } else return -1 ;  
  }

  public void set(PImage src_img, int target) {
    build();
    if(target < library.size()) {
      if(src_img.width == get(target).width && src_img.height == get(target).height){
        get(target).pixels = src_img.pixels ;
        get(target).updatePixels();
      } else {
        get(target).resize(src_img.width, src_img.height);
        get(target).pixels = src_img.pixels ;
        get(target).updatePixels();
      }
    } else {
      printErr("Neither target image match with your request");
    }
  }

  public void set(PImage src_img, String target_name) {
    build();
    if(library.size() > 0) {
      if(src_img.width == get(target_name).width && src_img.height == get(target_name).height){
        get(target_name).pixels = src_img.pixels ;
        get(target_name).updatePixels();
      } else {
        get(target_name).resize(src_img.width, src_img.height);
        get(target_name).pixels = src_img.pixels ;
        get(target_name).updatePixels();
      }
    } else {
      printErr("Neither target image match with your request");
    }
  }

  public String get_current_name() {
    return get_name(which_img);
  }

  public String get_name(int target) {
    if(library != null && library.size() > 0) {
      if(target < library.size()) {
        return library.get(target).get_name() ;
      } else return null ;
    } else return null ;
  }



  public int get_rank(String target_name) {
    if(library != null && library.size() > 0) {
      int rank = 0 ;
      for(int i = 0 ; i < library.size() ; i++) {
        String final_name = target_name.split("/")[target_name.split("/").length -1].split("\\.")[0] ;
        if(final_name.equals(library.get(i).name) ) {
          rank = i ;
          break;
        } 
      }
      return rank;
    } else return -1;
  }
  

  public ArrayList<R_Image> list() {
    return library;
  }

  public R_Image [] get() {
    if(library != null && library.size() > 0) {
      return library.toArray(new R_Image[library.size()]);
    } else return null;
  }

 
  public PImage get_current() {
    if(library != null && library.size() > 0 ) {
      if(which_img < library.size()) return library.get(which_img).img; 
      else return library.get(0).get_image(); 
    } else return null ;
  }
  

  public PImage get(int target){
    if(library != null && target >= 0 && target < library.size()) {
      return library.get(target).get_image();
    } else return null;
  }

  public PImage get(String target_name){
    if(library.size() > 0) {
      int target = 0 ;
      for(int i = 0 ; i < library.size() ; i++) {
        String final_name = target_name.split("/")[target_name.split("/").length -1].split("\\.")[0] ;
        if(final_name.equals(library.get(i).name) ) {
          target = i ;
          break;
        } 
      }
      return get(target);
    } else return null;
  }


  public R_Image rand() {
    if(library != null && library.size() > 0) {
      int target = floor(random(library.size()));
      return library.get(target);
    } else return null;
  }
}



/**
* R_Image
* 2019-2019
* v 0.0.2
*/
public class R_Image {
  private PImage img ;
  private String name = "no name" ;
  private int id = -1;

  public R_Image(String path) {
    this.name = path.split("/")[path.split("/").length -1].split("\\.")[0];
    this.img = loadImage(path);
  }

  public R_Image(PImage img) {
    this.img = img;
  }

  public R_Image(PImage img, String name, int id) {
    this.img = img;
    this.name = name;
    this.id = id;
  }
  

  public R_Image get() {
    return this;
  }

  public int get_id() {
    return id;
  }

  public String get_name() {
    return name ;
  }

  public PImage get_image() {
    return img ;
  }
}

/**
resize image
v 0.0.3
*/
/**
* resize your picture proportionaly to the window sketch of the a specificic PGraphics
*/
public void image_resize(PImage src) {
  image_resize(src,g,true);
}

public void image_resize(PImage src, boolean fullfit) {
  image_resize(src,g,fullfit);
}

public void image_resize(PImage src, PGraphics pg, boolean fullfit) {
  image_resize(src, pg.width, pg.height, fullfit);
}

public void image_resize(PImage src, int target_width, int target_height, boolean fullfit) {
  float ratio_w = target_width / (float)src.width;
  float ratio_h = target_height / (float)src.height;
  if(!fullfit) {
    if(ratio_w > ratio_h) {
      src.resize(ceil(src.width *ratio_w), ceil(src.height *ratio_w));
    } else {
      src.resize(ceil(src.width *ratio_h), ceil(src.height *ratio_h));  
    }
  } else {
    if(ratio_w > ratio_h) {
      src.resize(ceil(src.width *ratio_h), ceil(src.height *ratio_h));
    } else {
      src.resize(ceil(src.width *ratio_w), ceil(src.height *ratio_w));  
    }
  }
}

/**
copy window
v 0.0.1
*/
public PImage image_copy_window(PImage src, int where) {
  return image_copy_window(src, g, where);
}

public PImage image_copy_window(PImage src, PGraphics pg, int where) {
  int x = 0 ;
  int y = 0 ;
  if(where == CENTER) {
    x = (src.width -pg.width) /2 ;
    y = (src.height -pg.height) /2 ;   
  } else if(where == LEFT) {
    y = (src.height -pg.height) /2 ; 
  } else if(where == RIGHT) { 
    x = src.width -pg.width ;
    y = (src.height -pg.height) /2 ;   
  } else if(where == TOP) {
    x = (src.width -pg.width) /2 ;   
  } else if(where == BOTTOM) { 
    x = (src.width -pg.width) /2 ;
    y = src.height -pg.height;   
  }  
  return src.get(x, y, pg.width, pg.height); 
}

















/**
IMAGE
v 0.2.2
2016-2018
*/
/**
* additionnal method for image
* @see other method in vec mini library
*/
public void image(PImage img) {
  if(img != null) image(img, 0, 0);
  else printErr("Object PImage pass to method image() is null");
}

public void image(PImage img, int what) {
  if(img != null) {
    float x = 0 ;
    float y = 0 ;
    int w = img.width;
    int h = img.height;
    int where = CENTER;
    if(what == r.FIT || what == LANDSCAPE || what == PORTRAIT || what == SCREEN) {
      float ratio = 1.f;
      int diff_w = width-w;
      int diff_h = height-h;


      if(what == r.FIT) {
        if(diff_w > diff_h) {
          ratio = (float)height / (float)h;
        } else {
          ratio = (float)width / (float)w;
        }
      } else if(what == SCREEN) {
        float ratio_w = (float)width / (float)w;
        float ratio_h = (float)height / (float)h;
        if(ratio_w > ratio_h) {
          ratio = ratio_w;
        } else {
          ratio = ratio_h;
        }
        /*
        if(diff_w > diff_h) {
          ratio = (float)width / (float)w;
        } else {
          ratio = (float)height/ (float)h;
        }
        */
      } else if(what == PORTRAIT) {
        ratio = (float)height / (float)h;
      } else if(what == LANDSCAPE) {
        ratio = (float)width / (float)w;
      }
      w *= ratio;
      h *= ratio;
    } else {
      where = what;
    }

    if(where == CENTER) {
      x = (width /2.f) -(w /2.f);
      y = (height /2.f) -(h /2.f);   
    } else if(where == LEFT) {
      x = 0;
      y = (height /2.f) -(h /2.f);
    } else if(where == RIGHT) {
      x = width -w;
      y = (height /2.f) -(h /2.f);
    } else if(where == TOP) {
      x = (width /2.f) -(w /2.f);
      y = 0;
    } else if(where == BOTTOM) {
      x = (width /2.f) -(w /2.f);
      y = height -h; 
    }
    image(img,x,y,w,h);
  } else {
    printErrTempo(60,"image(); no PImage has pass to the method, img is null");
  } 
}

public void image(PImage img, float coor) {
  if(img != null) image(img, coor, coor);
  else printErr("Object PImage pass to method image() is null");
}

public void image(PImage img, ivec pos) {
  if(pos instanceof ivec2) {
    image(img, vec2(pos.x, pos.y));
  } else if(pos instanceof ivec3) {
    image(img, vec3(pos.x, pos.y, pos.z));
  }
}

public void image(PImage img, ivec pos, ivec2 size) {
  if(pos instanceof ivec2) {
    image(img, vec2(pos.x, pos.y), vec2(size.x, size.y));
  } else if(pos instanceof ivec3) {
    image(img, vec3(pos.x, pos.y, pos.z), vec2(size.x, size.y));
  } 
}

public void image(PImage img, vec pos) {
  if(pos instanceof vec2) {
    vec2 p = (vec2) pos ;
    image(img, p.x, p.y) ;
  } else if(pos instanceof vec3) {
    vec3 p = (vec3) pos ;
    push() ;
    translate(p) ;
    image(img, 0,0) ;
    pop() ;
  }
}

public void image(PImage img, vec pos, vec2 size) {
  if(pos instanceof vec2) {
    vec2 p = (vec2) pos ;
    image(img, p.x, p.y, size.x, size.y) ;
  } else if(pos instanceof vec3) {
    vec3 p = (vec3) pos ;
    push() ;
    translate(p) ;
    image(img, 0,0, size.x, size.y) ;
    pop() ;
  }
}









/**
* For the future need to use shader to do that...but in the future !
*/
public @Deprecated
PImage reverse(PImage img) {
  PImage final_img;
  final_img = createImage(img.width, img.height, RGB) ;
  for(int i = 0 ; i < img.pixels.length ; i++) {
    final_img.pixels[i] = img.pixels[img.pixels.length -i -1];
  }
  return final_img ;
}

/**
* For the future need to use shader to do that...but in the future !
*/
public @Deprecated
PImage mirror(PImage img) {
  PImage final_img ;
  final_img = createImage(img.width, img.height, RGB) ;

  int read_head = 0 ;
  for(int i = 0 ; i < img.pixels.length ; i++) {
    if(read_head >= img.width) {
      read_head = 0 ;
    }
    int reverse_line = img.width -(read_head*2) -1 ;
    int target = i +reverse_line  ;

    final_img.pixels[i] = img.pixels[target] ;

    read_head++ ;
  }
  return final_img ;
}

public @Deprecated
PImage paste(PImage img, int entry, int [] array_pix, boolean vertical_is) {
  if(!vertical_is) {
    return paste_vertical(img, entry, array_pix);
  } else {
    return paste_horizontal(img, entry, array_pix);
  }
}

public @Deprecated
PImage paste_horizontal(PImage img, int entry, int [] array_pix) { 
  PImage final_img ;
  final_img = img.copy() ;
  // reduce the array_pix in this one is bigger than img.pixels.length
  if(array_pix.length > final_img.pixels.length) {
     array_pix = Arrays.copyOf(array_pix,final_img.pixels.length) ;
  }

  int count = 0 ;
  int target = 0 ;
  for(int i = entry ; i < entry+array_pix.length ; i++) {
    if(i < final_img.pixels.length) {
      final_img.pixels[i] = array_pix[count];
    } else {
      target = i -final_img.pixels.length ;
      // security length outbound index
      // change the size can happen ArrayIndexOutBound,
      if(target >= final_img.pixels.length) {
        target = final_img.pixels.length -1;
      }

      final_img.pixels[target] = array_pix[count];
    }
    count++ ;
  }
  return final_img ;
}

public @Deprecated
PImage paste_vertical(PImage img, int entry, int [] array_pix) { 
  PImage final_img;
  final_img = img.copy();
  // reduce the array_pix in this one is bigger than img.pixels.length
  if(array_pix.length > final_img.pixels.length) {
     array_pix = Arrays.copyOf(array_pix,final_img.pixels.length) ;
  }

  int count = 0;
  int target = 0;
  int w = final_img.width;
  int line = 0;

  for(int i = entry ; i < entry+array_pix.length ; i++) {
    int mod = i%w ;
    // the master piece algorithm to change the direction :)
    int where =  entry +(w *(w -(w -mod))) +line;
    if(mod >= w -1) {
      line++;
    }
    if(where < final_img.pixels.length) {
      final_img.pixels[where] = array_pix[count];
    } else {
      target = where -final_img.pixels.length ;
      // security length outbound index
      // change the size can happen ArrayIndexOutBound,
      if(target >= final_img.pixels.length) {
        target = final_img.pixels.length -1;
      }
      final_img.pixels[target] = array_pix[count];
    }
    count++ ;
  }
  return final_img ;
}





















/**
CANVAS
v 0.2.0
*/
PImage [] rope_canvas;
int current_canvas_rope;

// build canvas
public void new_canvas(int num) {
  rope_canvas = new PImage[num];
}

public void create_canvas(int w, int h, int type) {
  rope_canvas = new PImage[1];
  rope_canvas[0] = createImage(w, h, type);
}

public void create_canvas(int w, int h, int type, int which_one) {
  rope_canvas[which_one] = createImage(w, h, type);
}

// clean
public void clean_canvas(int which_canvas) {
  int c = color(0,0) ;
  clean_canvas(which_canvas, c) ;
}

public void clean_canvas(int which_canvas, int c) {
  if(which_canvas < rope_canvas.length) {
    select_canvas(which_canvas) ;
    for(int i = 0 ; i < get_canvas().pixels.length ; i++) {
      get_canvas().pixels[i] = c ;
    }
  } else {
    String message = ("The target: " + which_canvas + " don't match with an existing canvas");
    printErr(message);
  }
}



// misc
public int canvas_size() {
  return rope_canvas.length;
}

// select the canvas must be used for your next work
public void select_canvas(int which_one) {
  if(which_one < rope_canvas.length && which_one >= 0) {
    current_canvas_rope = which_one;
  } else {
    String message = ("void select_canvas(): Your selection " + which_one + " is not available, canvas '0' be use");
    printErr(message);
    current_canvas_rope = 0;
  }
}

// get
public PImage get_canvas(int which) {
  if(which < rope_canvas.length) {
    return rope_canvas[which];
  } else return null; 
}

public PImage get_canvas() {
  return rope_canvas[current_canvas_rope];
}

public int get_canvas_id() {
  return current_canvas_rope;
}

// update
public void update_canvas(PImage img) {
  update_canvas(img,current_canvas_rope);
}

public void update_canvas(PImage img, int which_one) {
  if(which_one < rope_canvas.length && which_one >= 0) {
    rope_canvas[which_one] = img;
  } else {
    printErr("void update_canvas() : Your selection" ,which_one, "is not available, canvas '0' be use");
    rope_canvas[0] = img;
  }  
}


/**
canvas event
v 0.0.1
*/
public void alpha_canvas(int target, float change) { 
  for(int i = 0 ; i < get_canvas(target).pixels.length ; i++) {
    int c = get_canvas(target).pixels[i];
    float rr = red(c);
    float gg = green(c);
    float bb = blue(c);
    float aa = alpha(c);
    aa += change ;
    if(aa < 0 ) {
      aa = 0 ;
    }
    get_canvas(target).pixels[i] = color(rr,gg,bb,aa) ;
  }
  get_canvas(target).updatePixels() ;
}




/**
show canvas
v 0.0.4
*/
boolean fullscreen_canvas_is = false ;
ivec2 show_pos ;
/**
Add to set the center of the canvas in relation with the window
*/
int offset_canvas_x = 0 ;
int offset_canvas_y = 0 ;
public void set_show() {
  if(!fullscreen_canvas_is) {
    surface.setSize(get_canvas().width, get_canvas().height);
  } else {
    offset_canvas_x = width/2 - (get_canvas().width/2);
    offset_canvas_y = height/2 - (get_canvas().height/2);
    show_pos = ivec2(offset_canvas_x,offset_canvas_y) ;
  }
}

public ivec2 get_offset_canvas() {
  return ivec2(offset_canvas_x, offset_canvas_y);
}

public int get_offset_canvas_x() {
  return offset_canvas_x;
}

public int get_offset_canvas_y() {
  return offset_canvas_y;
}

public void show_canvas(int num) {
  if(fullscreen_canvas_is) {
    image(get_canvas(num), show_pos);
  } else {
    image(get_canvas(num));
  }  
}

























/**
* BACKGROUND
* v 0.2.5
* 2015-2019
*/
/**
Background classic processing
*/
// vec
public void background(vec4 c) {
  background(c.x,c.y,c.z,c.w) ;
}

public void background(vec3 c) {
  background(c.x,c.y,c.z) ;
}

public void background(vec2 c) {
  background(c.x,c.y) ;
}
// ivec
public void background(ivec4 c) {
  background(c.x,c.y,c.z,c.w) ;
}

public void background(ivec3 c) {
  background(c.x,c.y,c.z) ;
}

public void background(ivec2 c) {
  background(c.x,c.y) ;
}





/**
background image
*/
public void background(PImage src, int mode) {
  background_calc(src,null,null,null,null,mode);
}

public void background(PImage src, int mode, float red, float green, float blue) {
  vec3 colour_curtain = abs(vec3(red,green,blue).div(vec3(g.colorModeX,g.colorModeY,g.colorModeZ)));
  background_calc(src,null,null,colour_curtain,null,mode);
}

public void background(PImage src, float px, float py, float red, float green, float blue) {
  vec3 colour_curtain = abs(vec3(red,green,blue).div(vec3(g.colorModeX,g.colorModeY,g.colorModeZ)));
  vec2 pos = vec2(px /width, py /height);
  background_calc(src,pos,null,colour_curtain,null,r.SCALE);
}

public void background(PImage src, float px, float py, float scale_x, float red, float green, float blue) {
  vec3 colour_curtain = abs(vec3(red,green,blue).div(vec3(g.colorModeX,g.colorModeY,g.colorModeZ)));
  vec2 pos = vec2(px /width, py /height);
  vec2 scale = vec2(scale_x);
  background_calc(src,pos,scale,colour_curtain,null,r.SCALE);
}

public void background(PImage src, float px, float py, float scale_x, float red, float green, float blue, float curtain_position) {
  vec3 colour_curtain = abs(vec3(red,green,blue).div(vec3(g.colorModeX,g.colorModeY,g.colorModeZ)));
  vec2 pos = vec2(px /width, py /height);
  vec2 scale = vec2(scale_x);
  vec4 curtain_pos = vec4(curtain_position,0,curtain_position,0);
  background_calc(src,pos,scale,colour_curtain,curtain_pos,r.SCALE);
}

public void background(PImage src, vec2 pos, vec2 scale, vec3 colour_background, vec4 pos_curtain, int mode) {
  background_calc(src,pos,scale,colour_background,pos_curtain,mode);
}



PShader img_shader_calc_rope;
public void background_calc(PImage src, vec2 pos, vec2 scale, vec3 colour_background, vec4 pos_curtain, int mode) {
  boolean context_ok = false ;
  if(get_renderer().equals(P2D) || get_renderer().equals(P3D)) {
    context_ok = true;
  } else {
    printErrTempo(180,"method background(PImage img) need context in P3D or P2D to work");
  }
  if(context_ok && src != null && src.width > 0 && src.height > 0) {
    if(img_shader_calc_rope == null) {
      img_shader_calc_rope = loadShader("shader/fx_post/image.glsl");
    }
    if(graphics_is(src).equals("PGraphics")) {
      img_shader_calc_rope.set("flip_source",false,false);
    } else {
      img_shader_calc_rope.set("flip_source",true,false);
    }
    
    img_shader_calc_rope.set("texture_source",src);
    img_shader_calc_rope.set("resolution",width,height);
    img_shader_calc_rope.set("resolution_source",src.width,src.height); 
    
    if(colour_background != null) {
      img_shader_calc_rope.set("colour",colour_background.x,colour_background.y,colour_background.z); // definr RGB color from 0 to 1
    }

    if(pos_curtain != null) {
      img_shader_calc_rope.set("curtain",pos_curtain.x,pos_curtain.y,pos_curtain.z,pos_curtain.w); // definr RGB color from 0 to 1
    }

    if(pos != null) {
      img_shader_calc_rope.set("position",pos.x,pos.y); // from 0 to 1
    }
    
    if(scale != null) {
      img_shader_calc_rope.set("scale",scale.x,scale.y);
    }
    
    int shader_mode = 0;
    if(mode == r.FIT) {
      shader_mode = 0;
    } else if(mode == SCREEN) {
      shader_mode = 1;
    } else if(mode == r.SCALE) {
      shader_mode = 2;
    }
    img_shader_calc_rope.set("mode",shader_mode);

    filter(img_shader_calc_rope);
  }
}












/**
Normalize background
*/
public void background_norm(vec4 bg) {
  background_norm(bg.x,bg.y,bg.z,bg.w) ;
}

public void background_norm(vec3 bg) {
  background_norm(bg.x,bg.y,bg.z,1) ;
}

public void background_norm(vec2 bg) {
  background_norm(bg.x,bg.x,bg.x,bg.y) ;
}

public void background_norm(float c, float a) {
  background_norm(c,c,c,a) ;
}

public void background_norm(float c) {
  background_norm(c,c,c,1) ;
}

public void background_norm(float r,float g, float b) {
  background_norm(r,g,b,1) ;
}

// Main method
float MAX_RATIO_DEPTH = 6.9f ;
public void background_norm(float r_c, float g_c, float b_c, float a_c) {
  rectMode(CORNER) ;
  float x = map(r_c,0,1, 0, g.colorModeX) ;
  float y = map(g_c,0,1, 0, g.colorModeY) ;
  float z = map(b_c,0,1, 0, g.colorModeZ) ;
  float a = map(a_c,0,1, 0, g.colorModeA) ;
  noStroke() ;
  fill(x, y, z, a) ;
  int canvas_x = width ;
  int canvas_y = height ;
  if(renderer_P3D()) {
    canvas_x = width *100 ;
    canvas_y = height *100 ;
    int pos_x = - canvas_x /2 ;
    int pos_y = - canvas_y /2 ;
    // this problem of depth is not clarify, is must refactoring
    int pos_z = PApplet.parseInt( -height *MAX_RATIO_DEPTH) ;
    pushMatrix() ;
    translate(0,0,pos_z) ;
    rect(pos_x,pos_y,canvas_x, canvas_y) ;
    popMatrix() ;
  } else {
    rect(0,0,canvas_x, canvas_y) ;
  }
  // HSB mode
  if(g.colorMode == 3) {
    fill(0, 0, g.colorModeZ) ;
    stroke(0) ;
  // RGB MODE
  } else if (g.colorMode == 1) {
    fill(g.colorModeX, g.colorModeY, g.colorModeZ) ;
    stroke(0) ;

  }
  strokeWeight(1) ; 
}



/**
background rope
*/
public void background_rope(int c) {
  if(g.colorMode == 3) {
    background_rope(hue(c),saturation(c),brightness(c));
  } else {
    background_rope(red(c),green(c),blue(c));
  }
}

public void background_rope(int c, float w) {
  if(g.colorMode == 3) {
    background_rope(hue(c),saturation(c),brightness(c),w);
  } else {
    background_rope(red(c),green(c),blue(c),w );
  }
}

public void background_rope(float c) {
  background_rope(c,c,c);
}

public void background_rope(float c, float w) {
  background_rope(c,c,c,w);
}

public void background_rope(vec4 c) {
  background_rope(c.x,c.y,c.z,c.w);
}

public void background_rope(vec3 c) {
  background_rope(c.x,c.y,c.z);
}

public void background_rope(vec2 c) {
  background_rope(c.x,c.x,c.x,c.y);
}

// master method
public void background_rope(float x, float y, float z, float w) {
  background_norm(x/g.colorModeX, y/g.colorModeY, z/g.colorModeZ, w /g.colorModeA) ;
}

public void background_rope(float x, float y, float z) {
  background_norm(x/g.colorModeX, y/g.colorModeY, z/g.colorModeZ) ;
}























/**
* GRAPHICS METHOD
* v 0.4.3
*/
/**
SCREEN
*/
public void set_window(int px, int py, int sx, int sy) {
  set_window(ivec2(px,py), ivec2(sx,sy), get_screen_location(0));
}

public void set_window(int px, int py, int sx, int sy, int target) {
  set_window(ivec2(px,py), ivec2(sx,sy), get_screen_location(target));
}

public void set_window(ivec2 pos, ivec2 size) {
  set_window(pos, size, get_screen_location(0));
}

public void set_window(ivec2 pos, ivec2 size, int target) {
  set_window(pos, size, get_screen_location(target));
}

public void set_window(ivec2 pos, ivec2 size, ivec2 pos_screen) { 
  int offset_x = pos.x;
  int offset_y = pos.y;
  int dx = pos_screen.x;
  int dy = pos_screen.y;
  surface.setSize(size.x,size.y);
  surface.setLocation(offset_x +dx, offset_y +dy);
}

/**
check screen
*/
/**
screen size
*/
public ivec2 get_screen_size() {
  if(get_screen_num() > 1) {
    return get_display_size(sketchDisplay() -1);
  } else {
    return get_display_size(0);
  }
}

public ivec2 get_screen_size(int target) {
  if(target >= get_display_num()) {
    return null;
  }
  return get_display_size(target);
}

public @Deprecated
ivec2 get_display_size() {
  return get_display_size(sketchDisplay() -1);
}


public ivec2 get_display_size(int target) {
  if(target >= get_display_num()) {
    return null;
  }  
  Rectangle display = get_screen(target);
  return ivec2((int)display.getWidth(), (int)display.getHeight()); 
}

/**
screen location
*/

public ivec2 get_screen_location(int target) {
  Rectangle display = get_screen(target);
  return ivec2((int)display.getX(), (int)display.getY());
}

/**
screen num
*/
public int get_screen_num() {
  return get_display_num();
}

public int get_display_num() {
  GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
  return environment.getScreenDevices().length;
}


/**
screen
*/
public Rectangle get_screen(int target_screen) {
  GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
  GraphicsDevice[] awtDevices = environment.getScreenDevices();
  int target = 0 ;
  if(target_screen < awtDevices.length) {
    target = target_screen;
    GraphicsDevice awtDisplayDevice = awtDevices[target];
    Rectangle display = awtDisplayDevice.getDefaultConfiguration().getBounds();
    return display; 
  } else {
    return null;
  }
}



/**
sketch location 
0.0.2
*/
public ivec2 get_sketch_location() {
  return ivec2(get_sketch_location_x(),get_sketch_location_y());
}

public int get_sketch_location_x() {
  if(get_renderer() != P3D && get_renderer() != P2D) {
    return getJFrame(surface).getX();
  } else {
    return get_rectangle(surface).getX();

  }
  
}

public int get_sketch_location_y() {
  if(get_renderer() != P3D && get_renderer() != P2D) {
    return getJFrame(surface).getY();
  } else {
    return get_rectangle(surface).getY();
  }
}


public com.jogamp.nativewindow.util.Rectangle get_rectangle(PSurface surface) {
  com.jogamp.newt.opengl.GLWindow window = (com.jogamp.newt.opengl.GLWindow) surface.getNative();
  com.jogamp.nativewindow.util.Rectangle rectangle = window.getBounds();
  return rectangle;
}


public static final javax.swing.JFrame getJFrame(final PSurface surface) {
  return (javax.swing.JFrame)
  (
    (processing.awt.PSurfaceAWT.SmoothCanvas) surface.getNative()
  ).getFrame();
}








/**
* Check renderer
*/
boolean renderer_dimension_tested_is ;
boolean three_dim_is = false;
public boolean renderer_P3D() {
  if(!renderer_dimension_tested_is) {
    if(get_renderer(getGraphics()).equals("processing.opengl.PGraphics3D")) {
      three_dim_is = true ; 
    } else {
      three_dim_is = false ;
    }
    renderer_dimension_tested_is =true;
  }
  return three_dim_is;
}


public String get_renderer() {
  return get_renderer(g);
}

public String get_renderer(final PGraphics graph) {
  try {
    if (Class.forName(JAVA2D).isInstance(graph)) return JAVA2D;
    if (Class.forName(FX2D).isInstance(graph)) return FX2D;
    if (Class.forName(P2D).isInstance(graph)) return P2D;
    if (Class.forName(P3D).isInstance(graph)) return P3D;
    if (Class.forName(PDF).isInstance(graph)) return PDF;
    if (Class.forName(DXF).isInstance(graph)) return DXF;
  }

  catch (ClassNotFoundException ex) {
  }
  return "Unknown";
}






public String graphics_is(Object obj) {
  if(obj instanceof PGraphics) {
    return "PGraphics";
  } else if(obj instanceof PGraphics2D) {
    return "PGraphics";
  } else if(obj instanceof PGraphics3D) {
    return "PGraphics";
  } else if(obj instanceof processing.javafx.PGraphicsFX2D) {
    return "PGraphics";
  } else if(obj instanceof PImage) {
    return "PImage";
  } else return null;
}
/**
* R_Mesh
* temp tab before pass to Rope
* v 0.3.2
* 2019-2019
*/

/**
* R_Megabloc
* v 0.1.0
* 2019-2019
*/
public class R_Megabloc {
	private ArrayList<R_Bloc> list;
	private int width;
	private int height;
	private int magnetism = 2;
	private PApplet p;

	public R_Megabloc(PApplet p) {
		list = new ArrayList<R_Bloc>();
		this.p = p;
	}

	public void set(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public void set_magnetism(int magnetism) {
		this.magnetism = magnetism;
	}

	public void set_fill(int fill) {
		for(R_Bloc b : list) {
			b.set_fill(fill);
		}
	}

	public boolean set_fill(int index, int fill) {
		if(index >= 0 && index < list.size()) {
			list.get(index).set_fill(fill);
			return true;
		} else {
			return false;
		}
	}

	public void set_stroke(int stroke) {
		for(R_Bloc b : list) {
			b.set_stroke(stroke);
		}
	}

	public boolean set_stroke(int index, int stroke) {
		if(index >= 0 && index < list.size()) {
			list.get(index).set_stroke(stroke);
			return true;
		} else {
			return false;
		}
	}

		public void set_thickness(float thickness) {
		for(R_Bloc b : list) {
			b.set_thickness(thickness);
		}
	}

	public boolean set_thickness(int index, float thickness) {
		if(index >= 0 && index < list.size()) {
			list.get(index).set_thickness(thickness);
			return true;
		} else {
			return false;
		}
	}

	public void clear() {
		list.clear();
	}

	public int size() {
		return list.size();
	}

	public void add(R_Bloc bloc) {
		list.add(bloc);
	}

	public void add(R_Bloc [] blocs) {
		for(int i = 0 ; i < blocs.length ; i++) {
			list.add(blocs[i]);
		}
	}

	public int get_magnetism() {
		return this.magnetism;
	}

	public int get_width() {
		return width;
	}

	public int get_height() {
		return height;
	}

	public ArrayList<R_Bloc> get()  {
		return list;
	}

	public R_Bloc get(int index) {
		if(index >= 0 && index < list.size()) {
			return list.get(index);
		} else {
			return null;
		}
	}

  public boolean remove(int index) {
		if(index >= 0 && index < list.size()) {
			list.remove(index);
			return true;
		} else {
			return false;
		}
	}

	public void show() {
		show(p.g);
	}

	public void show(PGraphics other) {
		for(R_Bloc b : list) {;
			b.show(other);
		}
	}
}


/**
* R_Bloc
* 2019-2019
* 0.2.3
*/
public class R_Bloc implements rope.core.R_Constants_Colour {
	private ArrayList<vec3> list;
	private int id;
	private String name;
	private boolean end;
	private boolean select_is;
	private boolean select_point_is;
	private boolean action_available_is;
	private int index;
	private int magnetism = 1;
	private int colour_build;
	private int fill;
	private int stroke;
	private float thickness = 2.0f;
	private vec2 ref_coord;
	private vec2 coord;
	private ivec2 canvas;
	private PApplet p;

	public R_Bloc(PApplet p, int width, int height) {
		list = new ArrayList<vec3>();
		id = (int)random(Integer.MAX_VALUE);
		coord = new vec2();
		ref_coord = new vec2();
		colour_build = CYAN;
		fill = BLANC;
		stroke = NOIR;
		this.canvas = new ivec2(width,height);
		this.p = p;
	}

	public void set_id(int id) {
		this.id = id;
	}

	public void set_magnetism(int magnetism) {
		this.magnetism = magnetism;
	}

	public void set_fill(int fill) {
		this.fill = fill;
	}

	public void set_colour_build(int colour_build) {
		this.colour_build = colour_build;
	}

	public void set_stroke(int stroke) {
		this.stroke = stroke;
	}

	public void set_thickness(float thickness) {
		this.thickness = thickness;
	}

	public void set_name(String name) {
		this.name = name;
	}

	// get
	public vec3 [] get() {
		return list.toArray(new vec3[list.size()]);
	}

	public int get_fill() {
		return this.fill;
	}

	public int get_stroke() {
		return this.stroke;
	}

	public float get_thickness() {
		return this.thickness;
	}

	public String get_name() {
		return this.name;
	}

	public int get_id() {
		return this.id;
	}

	public int get_magnetism() {
		return this.magnetism;
	}

	public String get_data() {
		String num = "" + list.size();
		String what = "bloc";
		String field_name = "name:"+name;
		String field_complexity = "complexity:"+num;
		String field_fill = "fill:"+fill;
		String field_stroke = "stroke:"+stroke;
		String field_thickness = "thickness:"+Float.toString(thickness);
		String setting = what + "," + field_name + "," + field_complexity + "," + field_fill + "," + field_stroke + "," + field_thickness;
		for(vec3 v : list) {
			String type = "type:0";
			String ax = "ax:" + Float.toString(v.x());
			String ay = "ay:" + Float.toString(v.y());
			// type 0 is a simple vertex
			// type 1 is for bezier vertex for the future version
			setting += "," + type + "<>" + ax + "<>" + ay;
		}
		setting += ",close:" + end;
		return setting;
	}

	public boolean in_bloc(float x, float y) {
		return in_polygon(get(),vec2(x,y));
	}

	public boolean end_is() {
		return end;
	}

	private boolean intersection(vec2 point) {
		for(int i = 1 ; i < list.size() ; i++) {
			vec2 a = vec2(list.get(i-1));
			vec2 b = vec2(list.get(i));
			if(is_on_line(a,b,point,magnetism)) {
				index = i; 
				return true;
			}
		}
		return false;
	}

	private boolean end(vec2 point) {
		int max = list.size() - 1;
		if(dist(vec2(list.get(0)), point) < magnetism) {
			index = 0;
			end = true;
			return true;
		}
		return false;
	}

	private boolean near_of(vec2 point) {
		for(int i = 1 ; i < list.size() ; i++) {
			if(dist(vec2(list.get(i)), point) < magnetism) {
				index = i;
				return true;
			}

		}
		return false;
	}

	public boolean select_point_is() {
		return select_point_is;
	}

	public boolean select_is() {
		return select_is;
	}


	// update
	public void update(float x, float y) {
		coord.set(x,y);
	}

	/**
	* build
	*/
	public void build(float x, float y, boolean event_is) {
		build(x, y, event_is, true);
	}

	public void build(float x, float y, boolean event_is, boolean security_is) {
		update(x,y);
		if(event_is) {
			vec2 point = vec2(x,y);
			if(list.size() > 1) {
				if(list.size() > 2 && end(point)) {
					add(vec2(list.get(index)));
				} else if(security_is && near_of(point)) {
					list.remove(index);
				} else if(security_is && intersection(point)) {
					add(index, point);
				} else {
					add(point);
				}
			} else {
				if(list.size() == 1 && near_of(point)) {
					//
				} else {
					add(point);
				}
			}
		}
	}

	private void add(vec2 point) {
		vec3 temp = vec3(point);
		mag_canvas(temp);
		list.add(temp);
	}

	private void add(int index, vec2 point) {
		vec3 temp = vec3(point);
		mag_canvas(temp);
		list.add(index,temp);
	}

	/**
	* move
	*/
	public void move(float x, float y, boolean event_is) {
		if(event_is) {
			vec3 offset = vec3(sub(ref_coord,coord));
			for(vec3 p : list) {
				p.sub(offset);
			}
			ref_coord.set(coord);
		} else {
			ref_coord.set(coord);
		}
	}
	
	public void move_point(float x, float y, boolean event_is) {
		if(event_is) {
			vec3 offset = vec3(sub(ref_coord,coord));
			for(vec3 p : list) {
				if(p.z() == 1) {
					p.sub(offset);
					mag_canvas(p);
					p.z(1);
				}
			}
			ref_coord.set(coord);
		} else {
			ref_coord.set(coord);
		}
	}


	private void mag_canvas(vec3 p) {
		if(p.x() < 0 + magnetism) p.x(0);
		if(p.x() > canvas.x() - magnetism) p.x(canvas.x());
		if(p.y() < 0 + magnetism) p.y(0);
		if(p.y() > canvas.y() -magnetism) p.y(canvas.y());
	}


	/**
	* select
	*/
	public void reset_all_points() {
		for(vec3 v : list) {
			select_point_is(false);
			v.z(0);
		}	
	}

	public void select_all_points() {
		for(vec3 v : list) {
			select_point_is(true);
			v.z(1);
		}	
	}

	private void select_point(float x, float y) {
		for(int i = 0 ; i < list.size() ; i++) {
			vec3 v = list.get(i);
			if(dist_is(v, x, y)) {
				select_point_is(true);	
				if(end && (i == 0 || i == list.size() -1)) {
					list.get(0).z(1);
					list.get(list.size() -1).z(1);
				} else {
					v.z(1);
				}
				break;
			}
		}
	}

	private void select(float x, float y) {
		if(in_bloc(x,y)) {
			select_is(true);
		}
	}

	public void select_is(boolean is) {
		this.select_is = is;
	}

	public void select_point_is(boolean is) {
		this.select_point_is = is;
	}

	/**
	* misc
	*/
	private boolean dist_is(vec3 v, float x, float y) {
		return (dist(vec2(v), vec2(x,y)) < magnetism);
	}

	public void clear() {
		list.clear();
	}

	/**
	* show
	*/
	private void next(PGraphics other) {
		if(other != null)
			beginDraw(other);
		if(list.size() > 0) {
			line(list.get(list.size()-1),coord,other);
		}
		if(other != null)
			endDraw(other);
	}

	public boolean show_available_point(float x, float y) {
		return calc_show_available_point(x, y, p.g);
	}

	public boolean show_available_point(float x, float y, PGraphics other) {
		boolean is = false;
		if(other != null)
			beginDraw(other);
		is = calc_show_available_point(x, y, p.g);
		if(other != null)
			endDraw(other);
		return is;
	}

	private boolean calc_show_available_point(float x, float y, PGraphics other) {
		update(x,y);
		fill(BLANC,other);
		float size = 5;
		if(list.size() > 0) {
			for(int index = 0 ; index < list.size() ; index++) {
				if(dist(coord,vec2(list.get(index))) < magnetism) {
					vec2 pos = sub(vec2(list.get(index)),vec2(size).mult(.5f));
					square(pos,size,other);
					line(pos.copy().add(0,size/2),pos.copy().add(size,size/2),other);
					action_available_is = true;
					return true;
				}
				if(list.size() > 1 && index > 0) {
					vec2 a = vec2(list.get(index-1));
					vec2 b = vec2(list.get(index));
					if(is_on_line(a,b,coord,magnetism)) {
						vec2 pos = sub(coord,vec2(size).mult(.5f));
						square(pos,size,other);
						line(pos.copy().add(0,size/2),pos.copy().add(size,size/2),other);
						line(pos.copy().add(size/2,0),pos.copy().add(size/2,size),other);
						action_available_is = true;
						return true;
					}
				}
			}
		}
		return false;
	}

	public void show_anchor_point() {
		calc_show_anchor_point(p.g);
	}

	public void show_anchor_point(PGraphics other) {
		if(other != null)
			beginDraw(other);
		calc_show_anchor_point(other);
		if(other != null)
			endDraw(other);
	}

	private void calc_show_anchor_point(PGraphics other) {
		float size = 5;
		// past selection
		fill(BLANC,other);
		stroke(colour_build,other);
		strokeWeight(1,other);
		int max = list.size() - 1;
		for(int index = 0 ; index < max; index++) {
			if(index == 0 && !end) {
				square(sub(vec2(list.get(index)),vec2(size).mult(.5f)),size * 1.5f,other);
			} else {
				square(sub(vec2(list.get(index)),vec2(size).mult(.5f)),size,other);
			}
		}
		// current selection
		fill(colour_build,other);
		if(max >= 0) {
			square(sub(vec2(list.get(max)),vec2(size).mult(.5f)),size,other);
		}
	}

	public void show_struct() {
		calc_show_struct(false,p.g);
	}

	public void show_struct(PGraphics other) {
		if(other != null) {
			beginDraw(other);
		}
		calc_show_struct(true,other);
		if(other != null) {
			endDraw(other);
		}
	}

	private void calc_show_struct(boolean draw_is, PGraphics other) {
		strokeWeight(1,other);
		noFill(other);
		stroke(colour_build,other);
		beginShape(other);
		for(int index = 0 ; index < list.size() ; index++) {
			vertex(vec2(list.get(index)),other);
		}
		endShape(other);
		if(!end) {
			if(draw_is) {
				beginDraw(other);
				next(other);
				endDraw(other);
			} else {
				next(other);
			}
		}
	}

	public void show() {
		calc_show(p.g);
	}

	public void show(PGraphics other) {
		if(other != null) {
			beginDraw(other);
		}
		calc_show(other);
		if(other != null) {
			endDraw(other);
		}
	}

	private void calc_show(PGraphics other) {
		// fill(fill,other);
		// stroke(stroke,other);
		// strokeWeight(thickness,other);
		aspect(fill,stroke,thickness,other);
		if(list.size() == 2) {
			line(vec2(list.get(0)),vec2(list.get(1)),other);
		} else if(list.size() > 2) {
			beginShape(other);
			for(int index = 0 ; index < list.size() ; index++) {
				vertex(vec2(list.get(index)),other);
			}
			endShape(other);
		}
	}
}





























/**
* R_Plane
* 2019-2019
* 0.1.0
*/
class R_Plane {
	vec3 plane;
	vec3 a;
	float range = 1;
	boolean debug = false;
	ArrayList<R_Node> nodes;

	public R_Plane() {}

	public R_Plane(vec3 a, vec3 b, vec3 c) {
		this.a = a;
		this.plane = get_plane_normal(a,b,c);
	}

	public void set(vec3 a, vec3 b, vec3 c) {
		this.a = a;
		this.plane = get_plane_normal(a,b,c);
	}

	public void set_range(float range) {
		this.range = range;
	}

	public vec3 get_plane_normal(vec3 a, vec3 b, vec3 c) {
		return (sub(a,c).cross(sub(b,c))).normalize();
	}

	public float get_range() {
		return range;
	}

	public ArrayList<R_Node> get_nodes() {
		if(nodes != null) {
			return nodes;
			// return nodes.toArray(new R_Node[nodes.size()]);
		} else {
			return null;
		}
	}

	private boolean in_plane(vec3 any, float range) {
		// Calculate nearest distance between the plane represented by the vectors
		// a,b and c, and the point any
		float d = plane.x()*any.x() + plane.y()*any.y() + plane.z()*any.z() - plane.x()*a.x() - plane.y()*a.y() - plane.z()*a.z();
		// A perfect result would be d == 0 but this will not hapen with realistic
		// float data so the smaller d the closer the point. 
		// Here I have decided the point is on the plane if the distance is less than 
		// range unit.
		return abs(d) < range; 
	}


	public void debug(boolean bebug) {
		this.debug = debug;
	}

	public void clear() {
		if(nodes != null) {
			nodes.clear();
		}
	}

	public int size() {
		if(nodes != null) {
			return nodes.size();
		} else {
			return -1;
		}
	}

	public void add(R_Node any) {
		if(in_plane(any.pos(),range)) {
			if(nodes == null) {
				nodes = new ArrayList<R_Node>();
			} 
			nodes.add(any);
		} else if(debug) {
			println("class R_Plane method add():",any,"not in the plane",plane);
		}
	}
}





/**
* R_Face
* v 0.0.4
*/
public class R_Face {
	vec3 a,b,c;
	int fill;
	int stroke;
	public R_Face() {}

	public R_Face(vec3 a, vec3 b, vec3 c) {
		this.a = a;
		this.b = b;
		this.c = c;
	}

	public void set(vec3 a, vec3 b, vec3 c) {
		this.a = a;
		this.b = b;
		this.c = c;
	}


	public vec3 [] get() {
		vec3 [] summits = new vec3[3];
		summits[0] = a;
		summits[1] = b;
		summits[2] = c;
		return summits;
	}

	public void set_fill(int fill) {
		this.fill = fill;
	}

	public void set_stroke(int stroke) {
		this.stroke = stroke;
	}

	public int get_fill() {
		return this.fill;
	}

	public int get_stroke() {
		return this.stroke;
	}

	public void show() {
		beginShape();
		vertex(a);
		vertex(b);
		vertex(c);
		vertex(a); // close
		endShape();
	}
}


/**
* R_Node
* v 0.2.0
* 2019-2019
*/
public class R_Node {
	private vec3 pos;
	private ArrayList<vec3> dest_list;
	private int branch = 4;
	private int id;

	public R_Node() {}


	public R_Node(vec3 pos) {
		this.id = (int)random(MAX_INT);
		this.pos = pos;
	}

	public R_Node(vec3 pos, vec3 from) {
		this.id = (int)random(MAX_INT);
		this.pos = pos;
		dest_list = new ArrayList<vec3>();
		dest_list.add(from);
	}

	
	public R_Node copy() {
		R_Node node = new R_Node();
		if(dest_list != null) {
			node.dest_list = new ArrayList<vec3>(dest_list);
		}
		node.pos(this.pos);
		node.set_branch(branch);
		node.set_id(id);
		return node;
	}




	public boolean add_destination(vec3 dst) {
		if(dest_list.size() < branch && !all(equal(pos(),vec3(dst)))) {
			boolean equal_is = false;
			vec3 [] list = get_destination();
			for(int i = 0 ; i < list.length ; i++) {
				if(all(equal(list[i],vec3(dst)))) {
					equal_is = true;
				}
			}
			if(!equal_is) {
				dest_list.add(vec3(dst));
			}
			return !equal_is;
		} else {
			return false;
		}
	}
	
	// set
	public void set_destination(vec3 pos) {
		if(dest_list.size() < branch) {
			dest_list.add(pos);
		} 
	}

	public void set_id(int id) {
		this.id = id;
	}
	
	public void set_branch(int branch) {
		this.branch = branch;
	}

	public void pos(vec3 pos) {
		if(pos == null) {
			this.pos = new vec3();
		}	else {
			this.pos = pos;
		}
	}

	public void x(float x) {
		if(this.pos != null) {
			this.pos.x(x);
		} else {
			this.pos = new vec3(x,0,0);
		}
	}

	public void y(float y) {
		if(this.pos != null) {
			this.pos.y(y);
		} else {
			this.pos = new vec3(0,y,0);
		}
	}

	public void z(float z) {
		if(this.pos != null) {
			this.pos.z(z);
		} else {
			this.pos = new vec3(0,0,z);
		}
	}

	

	// get
	public int get_id() {
		return id;
	}

	public int get_branch() {
		return branch;
	}

	public int get_branch_available() {
		return branch - dest_list.size();
	}

	public vec3 [] get_destination() {
		return dest_list.toArray(new vec3[dest_list.size()]);
	}

	public vec3 pos() {
		return pos.xyz();
	}

	public vec3 pointer() {
		return pos;
	}

	public float x() {
		return pos.x();
	}

	public float y() {
		return pos.y();
	}

	public float z() {
		return pos.z();
	}
}










/**
* SEGMENT
* v 0.1.1
* 2019-2019
*/
public class R_Segment {
	private vec3 start;
	private vec3 end;
	private int capacity;
	private boolean direction;
	public R_Segment(vec start, vec end) {
		this.start = vec3(start.x,start.y,start.z);
		this.end = vec3(end.x,end.y,end.z);
	}

	public vec3 get_start() {
		return start;
	}

	public vec3 get_end() {
		return end;
	}

	public float get_angle() {
		return vec2(start).angle(vec2(end));
	}

	public float get_length() {
		return start.dist(end);
	}

	public void set_capacity(int capacity) {
		this.capacity = capacity;
	}

	public void set_direction(boolean direction) {
		this.direction = direction;
	}



	private vec2 line_intersection(R_Segment one, R_Segment two) {
		float x1 = one.get_start().x;
		float y1 = one.get_start().y;
		float x2 = one.get_end().x;
		float y2 = one.get_end().y;
		
		float x3 = two.get_start().x;
		float y3 = two.get_start().y;
		float x4 = two.get_end().x;
		float y4 = two.get_end().y;
		
		float bx = x2 - x1;
		float by = y2 - y1;
		float dx = x4 - x3;
		float dy = y4 - y3;
	 
		float b_dot_d_perp = bx * dy - by * dx;
	 
		if(b_dot_d_perp == 0) {
			return null;
		}
	 
		float cx = x3 - x1;
		float cy = y3 - y1;
	 
		float t = (cx * dy - cy * dx) / b_dot_d_perp;
		if(t < 0 || t > 1) return null;
	 
		float u = (cx * by - cy * bx) / b_dot_d_perp;
		if(u < 0 || u > 1) return null;
	 
		return vec2(x1+t*bx, y1+t*by);
	}
	
	public vec2 meet_at(R_Segment target) {
		return line_intersection(this,target);
	}

	public boolean meet_is(R_Segment target) {
		if(meet_at(target) == null) {
			return false;
		} else {
			return true;
		}
	}
}
/**
* R_Bloc method
* v 0.1.0
* 2019-2019
*/
public R_Bloc create_bloc(vec2 [] points) {
	R_Bloc bloc = new R_Bloc(this,width,height);
	for(vec2 v : points) {
		bloc.build(v.x(),v.y(),true);
	}
	return bloc;
}

/**
* R_Megabloc method
* v 0.1.5
* 2019-2019
*/
boolean add_point_to_bloc_is;
public boolean add_point_to_bloc_is() {
	return add_point_to_bloc_is;
}

public void add_point_to_bloc_is(boolean is) {
	add_point_to_bloc_is = is;
}


/**
* show
*/
// show point
public void bloc_show_struct(R_Megabloc mb, float x, float y) {
	bloc_show_struct(mb,x,y,null);
}

public void bloc_show_struct(R_Megabloc mb, float x, float y, PGraphics other) {
	for(R_Bloc b : mb.get()) {
		if(b.in_bloc(x,y)) {
			if(other == null) {
				b.show_struct();
			} else {
				b.show_struct(other);
			}	
		}
		if(b.select_point_is() || b.select_is()) {
			if(other == null) {
				b.show_anchor_point();
			} else {
				b.show_anchor_point(other);
			}
		}
	}
}

public boolean bloc_show_available_point(R_Megabloc mb, int x , int y) {
	return bloc_show_available_point(mb, x , y, null);
}

public boolean bloc_show_available_point(R_Megabloc mb, int x , int y, PGraphics other) {
	boolean event_is = false;
	for(R_Bloc b : mb.get()) {
		if(b.select_is()) {
			if(b.show_available_point(x,y)) {
				event_is = true;
			}
		}
	}
	return event_is;
}



boolean add_new_bloc_is = true;
public void check_for_new_bloc(R_Megabloc mb) {
	boolean check_for_new_bloc_is = false;
	// check the last current building bloc
	if(mb.size() > 0) {
		int last_index = mb.size() - 1;
		R_Bloc b = mb.get(last_index);
		if(b.end_is()) {
			check_for_new_bloc_is = true;
		}
	} else {
		check_for_new_bloc_is = true;
	}

	// use the result
	if(check_for_new_bloc_is) {
		add_new_bloc_is = true;
	}
}

public void new_bloc(R_Megabloc mb) {
	R_Bloc bloc = new R_Bloc(this,width,height);
	int id = mb.size();
	bloc.set_id(id);
	bloc.set_magnetism(10);
	bloc.set_colour_build(r.CYAN);
	mb.add(bloc);
}
// bolc draw
public void bloc_draw(R_Megabloc mb, int x, int y, boolean event_is, boolean show_struc_is) {
	bloc_draw(mb,x,y,event_is,show_struc_is,null);
}

public void bloc_draw(R_Megabloc mb, int x, int y, boolean event_is, boolean show_struc_is, PGraphics other) {
	if(show_struc_is) {
		for(R_Bloc b : mb.get()) {
			if(b.select_is() || !b.end_is()) {
				if(event_is) {
					b.build(x,y,add_point_to_bloc_is());
					add_point_to_bloc_is(false);
				}
				if(other == null) {
					b.show_struct();
					b.show_anchor_point();				
				} else {
					b.show_struct(other);
					b.show_anchor_point(other);
				}
			}	
		}
	}
}

public void bloc_remove_single_select(R_Megabloc mb) {
	int index = -1;
	if (mb != null) {
		for(int i = 0 ; i < mb.size() ; i++) {
			R_Bloc b = mb.get().get(i);
			if(b.select_is()) {
				index = i;
			}
		}
		if(index > -1) {
			mb.remove(index);
		}
	}
}


/**
* bloc management
*/
boolean bloc_move_event_is = false;
public void bloc_move_event_is(boolean is) {
	bloc_move_event_is = is;
}

public boolean bloc_move_event_is() {
	return bloc_move_event_is;
}



/**
* bloc move point and show available point
*/
public void bloc_select_all_point(R_Megabloc mb, float x, float y, boolean event_is) {
	// reset
	if(event_is && !bloc_move_event_is()) {
		for(R_Bloc b : mb.get()) {
			if(!b.in_bloc(x,y)) {
				b.reset_all_points();
			}
		}
	}
	// select
	for(int index = mb.size() - 1 ; index >= 0 ; index--) {
		R_Bloc b = mb.get(index);
		b.update(x,y);
		if(b.in_bloc(x,y)) {
			if(!b.select_point_is() && !bloc_move_event_is()) {
				b.select_point_is(event_is);
				if(b.select_point_is()) {
					b.select_all_points();
					bloc_move_event_is(true);
				}
			}
			break;
		}
	}
}


/**
* select
*/
public void bloc_select_single_point(R_Megabloc mb, float x, float y, boolean event_is) {
	// select
	for(int index = mb.size() - 1 ; index >= 0 ; index--) {
		R_Bloc b = mb.get(index);
		b.update(x,y);
		if(!bloc_move_event_is()) {
			b.select_point_is(false);
		}
		b.show_anchor_point();
		if(!b.select_point_is()) {
			if(event_is) {
				b.select_point(x,y);
				bloc_move_event_is(true);
			} else {
				b.reset_all_points();
			}
		}
	}
}

int bloc_point_index = -1;
public boolean bloc_move_point(R_Megabloc mb, float x, float y, boolean event_is) {
	boolean move_is = false;
	if(bloc_point_index < 0) {
		for(int index = 0 ; index < mb.size() ; index++) {
			R_Bloc b = mb.get(index);
			boolean is = false;
			bloc_point_index = - 1;
			if(event_is && b.select_point_is()) {
				bloc_point_index = index;
				is = true;
			  move_is = true;
				break;
			}
			b.move_point(x,y,is);
		}
	} else if(bloc_point_index >= 0) {
		R_Bloc b = mb.get(bloc_point_index);
		b.update(x,y);
		if(event_is && b.select_point_is()) {
			move_is = true;
			b.move_point(x,y,true);
		} else {
			bloc_point_index = -1;
		}
	}
	return move_is;
}


/**
* bloc move
*/
int bloc_index = -1;
public void bloc_select(R_Megabloc mb, float x, float y, boolean event_is) {
	for(int index = mb.size() - 1 ; index >= 0 ; index--) {
		R_Bloc b = mb.get(index);
		b.update(x,y);
		if(b.in_bloc(x,y)) {
			if(!b.select_is() && !bloc_move_event_is()) {
				b.select_is(event_is);
			}
			break;
		}
	}
}


public boolean bloc_move(R_Megabloc mb, float x, float y, boolean event_is) {
	boolean move_is = false;
	if(bloc_index < 0) {
		for(int index = 0 ; index < mb.size() ; index++) {
			R_Bloc b = mb.get(index);
			boolean is = false;
			bloc_index = - 1;
			if(event_is && b.select_is()) {
				bloc_index = index;
				is = true;
			  move_is = true;
				break;
			}
			b.move(x,y,is);
		}
	} else if(bloc_index >= 0) {
		R_Bloc b = mb.get(bloc_index);
		if(event_is && b.select_is()) {
			move_is = true;
			b.update(x,y);
			b.move(x,y,true);
		} else {
			bloc_index = -1;
		}
	}
	return move_is;
}




/**
* load save
*/
public void save_megabloc(R_Megabloc mb, String path, String file_name) {
	if(mb != null) {
		String [] save = new String[1];
		// header
		String name = "bloc file name:"+file_name;
		String elem = "elements:"+ mb.size();
		String w = "width:" + width;
		String h = "height:" + height;
		String mag = "magnetism:" + mb.get_magnetism();
		save[0] =  name + "," + elem + "," + w + ","+ h + "," + mag + "\n";
		// details
		for(R_Bloc r : mb.get()) {
			save[0] += (r.get_data() + "\n");
		}
		saveStrings(path+file_name+".blc",save);

	}
}

public String [] load_megabloc(String path_name) {
	String [] data = loadStrings(path_name);
	for(int i = 0 ; i < data.length ; i++) {
		println(data[i]);
	}
	if(data[0].split(",")[0].contains("bloc file name")) {
		return data;
	} else {
		return null;
	}
}

public R_Megabloc read_megabloc(String [] file_type_blc) {
	return read_megabloc(file_type_blc, false, false);
}

public R_Megabloc read_megabloc(String [] file_type_blc, boolean original_canvas_is, boolean fit_is) {
	R_Megabloc mb = new R_Megabloc(this);
	boolean is = true;
	String [] header = file_type_blc[0].split(",");
	// elem
	int elem = 0;
	if(header[1].contains("elements")) {
		elem = atoi(header[1].split(":")[1]);
	} else {
		is = false;
	}
	// dimension
	int original_width = 0;
	if(header[2].contains("width")) {
		original_width = atoi(header[2].split(":")[1]);
	}
	int original_height = 0;
	if(header[3].contains("height")) {
		original_height = atoi(header[3].split(":")[1]);
	}
	if(original_canvas_is) {
		mb.set(original_width,original_height);
	} else {
		mb.set(width,height);
	}
	// magnetism
	int mag = 2;
	if(header[4].contains("magnetism")) {
		mag = atoi(header[4].split(":")[1]);
	}
	// bloc
	for(int i = 1 ; i <= elem ; i++) {
		String bloc_info [] = file_type_blc[i].split(",");
		if(bloc_info[0].contains("bloc") && bloc_info[2].contains("complexity")
				&& bloc_info[3].contains("fill") && bloc_info[4].contains("stroke") && bloc_info[5].contains("thickness")) {
			R_Bloc b = new R_Bloc(this,mb.width,mb.height);;
			b.set_magnetism(mag);
			b.set_fill(atoi(bloc_info[3].split(":")[1]));
			b.set_stroke(atoi(bloc_info[4].split(":")[1]));
			b.set_thickness(atof(bloc_info[5].split(":")[1]));
			int start = 5;
			for(int n = start ; n < bloc_info.length ; n++) {
				if(bloc_info[n].contains("type")) {
					String [] coord = bloc_info[n].split("<>");
					float ax = atof(coord[1].split(":")[1]);
					float ay = atof(coord[2].split(":")[1]);
					if(fit_is && (original_width != width || original_height != height)) {
						ax /= original_width;
						ax *= width;
						ay /= original_height;
						ay *= height;
					}
					b.build(ax,ay,true,false);
				}
			}
			mb.add(b);
		}
	}
	if(is)
		return mb;
	else
		return null;
}















/**
* R_Plane methods
* v 0.0.1
* 2019-2019
*
*/
public boolean in_plane(vec3 a, vec3 b, vec3 c, vec3 any, float range) {
	vec3 n = get_plane_normal(a, b, c);
	// Calculate nearest distance between the plane represented by the vectors
	// a,b and c, and the point any
	float d = n.x()*any.x() + n.y()*any.y() + n.z()*any.z() - n.x()*a.x() - n.y()*a.y() - n.z()*a.z();
	// A perfect result would be d == 0 but this will not hapen with realistic
	// float data so the smaller d the closer the point. 
	// Here I have decided the point is on the plane if the distance is less than 
	// range unit.
	return abs(d) < range; 
}


public vec3 get_plane_normal(vec3 a, vec3 b, vec3 c) {
	return new R_Plane().get_plane_normal(a,b,c);
}
/**
ROPE - Romanesco processing environment – 
* Copyleft (c) 2014-2021
* Stan le Punk > http://stanlepunk.xyz/
* Rope Motion  2015-2021
* v 1.4.0
Rope – Romanesco Processing Environment – 
* @author @stanlepunk
* @see https://github.com/StanLepunK/Rope_framework
*/




/**
* Method motion
* v 0.2.0
*/
/**
* follow
* v 0.2.0
*/

public vec2 follow(vec2 target, float speed, vec3 buf) {
  vec3 f = follow(target.x(), target.y(), 0, speed, speed, speed, buf);
  return vec2(f.x(),f.y());
}

public vec2 follow(vec2 target, vec2 speed, vec3 buf) {
  vec3 f = follow(target.x(), target.y(), 0, speed.x(), speed.y(), 0, buf);
  return vec2(f.x(),f.y());
}

public vec2 follow(float tx, float ty, float speed, vec3 buf) {
  vec3 f = follow(tx, ty ,0 ,speed, speed, speed, buf);
  return vec2(f.x(), f.y());
}

public vec3 follow(vec3 target, float speed, vec3 buf) {
  return follow(target.x(), target.y(), target.z(), speed, speed, speed, buf);
}

public vec3 follow(vec3 target, vec3 speed, vec3 buf) {
  return follow(target.x(), target.y(), target.z(), speed.x(), speed.y(), speed.z(), buf);
}


public vec3 follow(float tx, float ty, float tz, float speed, vec3 buf) {
  return follow(tx, ty, tz, speed, speed, speed, buf);
}

/**
* master method
* Compute position vector Traveller, give the target pos and the speed to go.
*/
public float check_speed_follow(float speed) {
  if(speed < 0 || speed > 1) {
    printErrTempo(120,"vec3 follow(): speed parameter must be a normal value between [0.0, 1.0]\n instead value 1 is attribute to speed");
    speed = 1.0f;
  }
  return speed;
}

public vec3 follow(float tx, float ty, float tz, float sx, float sy, float sz, vec3 buf) {
  sx = check_speed_follow(sx);
  sy = check_speed_follow(sy);
  sz = check_speed_follow(sz);
  // calcul X pos
  float dx = tx - buf.x();
  if(abs(dx) != 0) {
    buf.add_x(dx * sx);
  }
  // calcul Y pos
  float dy = ty - buf.y();
  if(abs(dy) != 0) {
    buf.add_y(dy * sy);
  }
  // calcul Z pos
  float dz = tz - buf.z();
  if(abs(dz) != 0) {
    buf.add_z(dz * sy);
  }
  return buf;
}





/**
Class Motion 
v 1.1.0
2016-2018
* @author Stan le Punk
* @see https://github.com/StanLepunK/Motion
*/

class Motion {
  float vel_ref = 1.f ;
  float vel = 1.f ;
  float max_vel = 1 ;

  float acc = .01f ;
  float dec = .01f ;
  boolean  acc_is = false ;
  boolean  dec_is = true ;

  vec3 dir  ;
  int tempo = 0 ;
  private boolean tempo_is = false ;
  
  // constructor
  Motion() {
  }

  Motion(float max_vel) {
    this.max_vel = max_vel ;
  }



  // get
  public float get_velocity() {
    return vel ;
  }

  public vec3 get_direction() {
    return dir ;
  }

  public float get_acceleration() {
    return acc;
  }

  public float get_deceleration() {
    return dec;
  }

  public boolean acceleration_is() {
    return acc_is ;
  }

  public boolean deceleration_is() {
    return dec_is ;
  }

  public boolean velocity_is() {
    if(vel == 0) return false ; else return true ;
  }




  // set
  public void set_deceleration(float dec) {
    this.dec = abs(dec) ;
  }

  public void set_acceleration(float acc) {
    this.acc = abs(acc) ;
  }

  public void set_velocity(float vel) {
    this.vel = vel ;
  }

  public void set_max_velocity(float max_vel) {
    this.max_vel = max_vel ;
  }

  public void set_tempo(int tempo) {
    tempo_is = true ;
    this.tempo = tempo ;
  }

  public void reset() {
    this.vel = 0 ;
    this.vel_ref = 0 ;
    if(dir == null) {
      this.dir = vec3(0) ;
    } else {
      this.dir.set(0) ;
    }
  }




  // event
  public void stop() {
    this.vel_ref = this.vel ;
    set_velocity(0) ;
  }

  public void start() {
    set_velocity(vel_ref) ;
  }

  public void acceleration_is(boolean state) {
    acc_is = state ;
  }

  public void deceleration_is(boolean state) {
    dec_is = state ;
  }


  // motion
  // deceleration
  public void deceleration() {
    if(vel > 0) {
      vel -= dec ;
      // to stop object
      if(vel < 0) vel = 0 ;
    } else if(vel < 0 ) {
      vel += dec ;
      if(vel > 0) vel = 0 ;
    }
  }
  
  // acceleration
  public void acceleration() {
    if(vel > 0) {
      vel += acc ;
      // limit the velocity to the maximum velocity
      if(vel > max_vel) vel = max_vel ;
    } else if(vel < 0 ) {
      vel -= acc ;
      // limit the velocity to the maximum velocity
      if(abs(vel) > max_vel) vel = -max_vel ;
    }
  }







  /**
  leading 
  v 0.0.3
  */
  public vec2 leading(vec2 leading_pos, vec2 exec_pos) {
    vec3 current_pos_3D = vec3(leading_pos) ;
    vec3 my_pos_3D = vec3(exec_pos) ;
    vec3 lead = leading(current_pos_3D, my_pos_3D) ;
    return vec2(lead.x, lead.y) ;
  }


  vec3 for_vel ;
  vec3 for_dir ;

  vec3 leading_pos ;
  vec3 leading_ref ;
  boolean apply_acc = false ;

  public vec3 leading(vec3 leading_pos, vec3 exec_pos) {
    if(leading_ref == null) {
      leading_ref = vec3(exec_pos) ;
    }
    vec3 new_pos = vec3(exec_pos) ;

    vec3 velocity_xyz = apply_leading(leading_pos) ;
    if(velocity_xyz.equals(vec3(0))) {
      // follow the lead when this one move
      apply_acc = true ;
      new_pos.sub(sub(leading_ref, leading_pos)) ;
    } else {
      new_pos.add(velocity_xyz) ;
    }
    leading_ref.set(leading_pos) ;
    return new_pos ;
  }


  private vec3 apply_leading(vec3 leading_pos) {
    // init var if var is null
    if (dir == null) {
      dir = vec3() ;
    }
    if (for_vel == null) {
      for_vel = vec3() ;
    }
    if (for_dir == null) {
      for_dir = vec3() ;
    }
    if (leading_pos == null) {
      leading_pos = vec3() ;
    }


    vec3 vel_vec3 = vec3() ;
    leading_pos.set(leading_pos) ;

    if(for_vel.equals(leading_pos)) {
      // limit speed
      if (abs(vel) > max_vel) {
        if(vel < 0) {
          vel = -max_vel ;
        } else {
          vel = max_vel ;
        }
      }
      

      if(abs(vel) >= max_vel || !acc_is) {
        apply_acc = false ;
      }

      if(apply_acc && acc_is) {
        acceleration() ;
      }

      if(!apply_acc && dec_is) {
        deceleration() ;
      }

      // update position
      vel_vec3 = mult(dir, vel) ;
    } else {
      vel = dist(leading_pos, for_vel) ;
      dir = sub(leading_pos, for_dir) ;
      dir.normalize() ;
    }
    for_vel.set(leading_pos) ;

    // calcul direction
    if(!tempo_is) tempo = PApplet.parseInt(frameRate *.25f) ;
    if(tempo != 0) {
      if(frameCount%tempo == 0) {
        for_dir.set(leading_pos) ;
      } 
    }
    
    //
    return vel_vec3 ;
  }
  /**
  end leading
  */

  
}







/**
PATH
*/
class Path extends Motion {
  // list of the keypoint, use super_class Path
  ArrayList<vec3> path ;
  // distance between the keypoint and the position of the translation shape
  float dist_from_start = 0 ;
  float dist_a_b = 0 ;

  // a & b are points to calculate the direction and position of the translation to give at the shape
  // vec3 origin, target ;
  // speed ratio to adjust the speed xy according to position target
  vec3 ratio  ;
  //keypoint 
  vec3 pos ;
  

  // find a good keypoint in the ArrayList
  int n = 0 ;
  int m = 1 ;

  Path() {
    super() ;
    path = new ArrayList<vec3>() ;
    pos = vec3(MAX_INT) ;
  }
   // set
   public void set_velocity(float velocity) {
    if(vel < 0) {
      System.err.println("negative value, class Path use the abslolute value of") ;
      System.err.println(vel) ;
    }
    this.vel = abs(vel) ;
   }

  

  // next
  public void previous() {
    vec3 origin, target ;
    if (path.size() > 1 ) {
      vec3 key_a = vec3() ;
      vec3 key_b = vec3() ;
      int origin_rank = path.size() - n -1 ;
      int target_rank = path.size() - m -1 ;
      key_a = (vec3) path.get(origin_rank) ;
      key_b = (vec3) path.get(target_rank) ;

      origin = vec3(key_a) ;
      target = vec3(key_b) ;
      go(origin, target) ;

    } else if (path.size() == 1) {
      vec3 key_a = (vec3) path.get(0) ;
      origin = vec3(key_a) ;
      pos.set(origin) ;
    } else {
      pos.set(-100) ;
    }
  }






  // next
  public void next() {
    vec3 origin, target ;
    if (path.size() > 1 ) {
      vec3 key_a = vec3() ;
      vec3 key_b = vec3() ;
      key_a = (vec3) path.get(n) ;
      key_b = (vec3) path.get(m) ;

      origin = vec3(key_a) ;
      target = vec3(key_b) ;
      go(origin, target) ;

    } else if (path.size() == 1) {
      vec3 key_a = (vec3) path.get(n) ;
      origin = vec3(key_a) ;
      pos.set(origin) ;
    } else {
      pos.set(-100) ;
    }
  }




  // private method of class
  private void go(vec3 origin, vec3 target) {
    if(pos.equals(vec3(MAX_INT))) {
      pos.set(origin) ;
    }
    // distance between the keypoint a & b and the position of the translation shape
    dist_a_b = origin.dist(target) ;
    dist_from_start = pos.dist(origin) ;
    //update the position
    if (dist_from_start < dist_a_b) {
      // calcul speed ratio
      vec3 speed_ratio = sub(origin,target) ;

      // final calcul ratio
      if(ratio == null) {
        ratio = vec3() ;
      }
      ratio.x = speed_ratio.x / speed_ratio.y ;
      ratio.y = speed_ratio.y / speed_ratio.x ;
      if(abs(ratio.x) > abs(ratio.y) ) { 
        ratio.x = 1.0f ; ratio.y = abs(ratio.y) ; 
      } else { 
        ratio.x = abs(ratio.x) ; ratio.y = 1.0f ; 
      }
      
      // Give the good direction to the translation
      if (speed_ratio.x == 0) {
        pos.x += 0 ;
        if (origin.y - target.y < 0 )  {
          pos.y += vel ; 
        } else {
          pos.y -= vel ;
        }
      } 
      if (speed_ratio.y == 0) {
        pos.y += 0 ;
        if (origin.x - target.x < 0 ) {
          pos.x += vel ; 
        } else {
          pos.x -= vel ;
        }     
      }

      if (speed_ratio.x != 0 && speed_ratio.y != 0  )  {
        if (origin.x - target.x < 0 ) {
          pos.x += (vel *ratio.x) ; 
        } else {
          pos.x -= (vel *ratio.x) ;
        }
        if (origin.y - target.y < 0 ) {
          pos.y += (vel *ratio.y) ; 
        } else {
          pos.y -= (vel *ratio.y) ;
        }
      }
    } else {
      n++ ; 
      m++ ;
    }
    //change to the next keypoint 
    if (target.equals(pos)) {  
      m++ ; 
      n++ ; 
    }
    
    if (n != path.size() && m == 1) { 
      m = 1 ; 
      n = 0 ; 
    }
    
    if (m == path.size()) { 
      m = 0 ; 
    }
    
    if (n == path.size()) { 
      n = 0 ; 
    } 
  }









  // get
  public vec3 get_pos() {
    return pos ;
  }

  public int path_size() {
    return path.size() ;
  }

  public vec3 [] path() {
    vec3 [] list = new vec3[path.size()] ;
    for(int i = 0 ; i < path.size() ; i++) {
      list[i] = path.get(i).copy() ;
    }
    return list ;
  }

  public ArrayList<vec3> path_ArrayList() {
    return path ;
  }
  

  // add point to the list to make the path
  public void add(vec coord) {
    path.add(vec3(coord.x,coord.y,coord.z)) ;
  }
  public void add(int x, int y, int z) {
    path.add(vec3(x,y,z)) ;
  }

  public void add(int x, int y) {
    path.add(vec3(x,y,0)) ;
  }
}
/**
* ROPE PROCESSING METHOD
* v 2.8.0
* Copyleft (c) 2014-2019
* Stan le Punk > http://stanlepunk.xyz/
* @author @stanlepunk
* @see https://github.com/StanLepunK/Rope_framework
* Processing 3.5.3.269
* Rope library 0.8.5.30
*/



/**
ADVANCED GHOST METHOD
v 1.1.0
All advanced ghost push Processing method further.
Processing and vec, ivec and bvec method
the idea here is create method directly insprating from Processing to simplify the coder life
*/

/**
* colorMode(vec5 color_component)
* @param component give in order : mode, x, y, z and alpha
*/
/*
void colorMode(vec5 component) {
  int mode = (int)component.a();
  if(mode == HSB) {
    colorMode(HSB,component.b(),component.c(),component.d(),component.e());
  } else if(mode == RGB) {
    colorMode(RGB,component.b(),component.c(),component.d(),component.e());
  } else {
    printErr("The first component of your vec is", mode, "and don't match with any Processing colorMode, instead the current colorMode will be used");
  }
}
*/
/**
* colorMode(int mode, vec4 color_component)
* @param mode give environment HSB or RGB
* @param color_component give in order : x, y, z and alpha
*/
public void colorMode(int mode, vec4 component) {
  if(mode == HSB) {
    colorMode(HSB,component.x(),component.y(),component.z(),component.w());
  } else if(mode == RGB) {
    colorMode(RGB,component.x(),component.y(),component.z(),component.w());
  } else {
    printErr("int mode", mode, "don't match with any Processing colorMode, instead the current colorMode will be used");
  }
}
/**
* colorMode(int mode, vec3 color_component)
* @param mode give environment HSB or RGB
* @param color_component give in order : x, y, z
*/
public void colorMode(int mode, vec3 component) {
  colorMode(mode, vec4(component.x(),component.y(),component.z(),g.colorModeA));
}
/**
* colorMode(int mode, vec2 color_component)
* @param mode give environment HSB or RGB
* @param color_component give in order the x give x,y,z and y give the alpha
*/
public void colorMode(int mode, vec2 component) {
  colorMode(mode, vec4(component.x(),component.x(),component.x(),component.y()));
}







/**
floor
*/
public vec2 floor(vec2 arg) {
  return vec2(floor(arg.x()),floor(arg.y()));
}

public vec3 floor(vec3 arg) {
  return vec3(floor(arg.x()),floor(arg.y()),floor(arg.z()));
}

public vec4 floor(vec4 arg) {
  return vec4(floor(arg.x()),floor(arg.y()),floor(arg.z()),floor(arg.w()));
}






/**
round
*/
public vec2 round(vec2 arg) {
  return vec2(round(arg.x()),round(arg.y()));
}

public vec3 round(vec3 arg) {
  return vec3(round(arg.x()),round(arg.y()),round(arg.z()));
}

public vec4 round(vec4 arg) {
  return vec4(round(arg.x()),round(arg.y()),round(arg.z()),round(arg.w()));
}





/**
ceil
*/
public vec2 ceil(vec2 arg) {
  return vec2(ceil(arg.x()),ceil(arg.y()));
}

public vec3 ceil(vec3 arg) {
  return vec3(ceil(arg.x()),ceil(arg.y()),ceil(arg.z()));
}

public vec4 ceil(vec4 arg) {
  return vec4(ceil(arg.x()),ceil(arg.y()),ceil(arg.z()),ceil(arg.w()));
}


/**
abs
*/
public vec2 abs(vec2 arg) {
  return vec2(abs(arg.x()),abs(arg.y()));
}

public vec3 abs(vec3 arg) {
  return vec3(abs(arg.x()),abs(arg.y()),abs(arg.z()));
}

public vec4 abs(vec4 arg) {
  return vec4(abs(arg.x()),abs(arg.y()),abs(arg.z()),abs(arg.w()));
}

public ivec2 abs(ivec2 arg) {
  return ivec2(abs(arg.x()),abs(arg.y()));
}

public ivec3 abs(ivec3 arg) {
  return ivec3(abs(arg.x()),abs(arg.y()),abs(arg.z()));
}

public ivec4 abs(ivec4 arg) {
  return ivec4(abs(arg.x()),abs(arg.y()),abs(arg.z()),abs(arg.w()));
}



/**
max
*/
public vec2 max(vec2 a, vec2 b) {
  return vec2(max(a.x(),b.x()),max(a.y(),b.y()));
}

public vec3 max(vec3 a, vec3 b) {
  return vec3(max(a.x(),b.x()),max(a.y(),b.y()),max(a.z(),b.z()));
}

public vec4 max(vec4 a, vec4 b) {
  return vec4(max(a.x(),b.x()),max(a.y(),b.y()),max(a.z(),b.z()),max(a.w(),b.w()));
}

public ivec2 max(ivec2 a, ivec2 b) {
  return ivec2(max(a.x(),b.x()),max(a.y(),b.y()));
}

public ivec3 max(ivec3 a, ivec3 b) {
  return ivec3(max(a.x(),b.x()),max(a.y(),b.y()),max(a.z(),b.z()));
}

public ivec4 max(ivec4 a, ivec4 b) {
  return ivec4(max(a.x(),b.x()),max(a.y(),b.y()),max(a.z(),b.z()),max(a.w(),b.w()));
}



/**
min
*/
public vec2 min(vec2 a, vec2 b) {
  return vec2(min(a.x(),b.x()),min(a.y(),b.y()));
}

public vec3 min(vec3 a, vec3 b) {
  return vec3(min(a.x(),b.x()),min(a.y(),b.y()),min(a.z(),b.z()));
}

public vec4 min(vec4 a, vec4 b) {
  return vec4(min(a.x(),b.x()),min(a.y(),b.y()),min(a.z(),b.z()),min(a.w(),b.w()));
}

public ivec2 min(ivec2 a, ivec2 b) {
  return ivec2(min(a.x(),b.x()),min(a.y(),b.y()));
}

public ivec3 min(ivec3 a, ivec3 b) {
  return ivec3(min(a.x(),b.x()),min(a.y(),b.y()),min(a.z(),b.z()));
}

public ivec4 min(ivec4 a, ivec4 b) {
  return ivec4(min(a.x(),b.x()),min(a.y(),b.y()),min(a.z(),b.z()),min(a.w(),b.w()));
}









/**
random
*/
public float random(vec2 v) {
  return random(v.x(),v.y());
}

public float random(ivec2 v) {
  return random(v.x(),v.y());
}




























/**
* PImage method
* v 0.2.2
*/
rope.costume.R_Shape buffer_rope_framework;
public void set_buffer_shape(PGraphics other) {
  if(buffer_rope_framework == null) {
    buffer_rope_framework = new rope.costume.R_Shape(this,other);
  }
}



/**
* set
* v 0.2.1
*/
public void set(vec2 pos, int c, PGraphics other) {
  set((int)pos.x(),(int)pos.y(),c,other);
}

public void set(vec2 pos, int c) {
  set((int)pos.x(),(int)pos.y(),c);
}

// main method
public void set(int x, int y, int c, PGraphics other) {
  if(other != null) {
    set_buffer_shape(other);
    buffer_rope_framework.set(x,y,c,other);
  } else {
    set(x,y,c);
  }
}









/** 
* PGraphics Method
* v 0.1.3
*/

/**
* colorMode
*/
public void colorMode(int mode,PGraphics other) {
  if(other != null) {
    other.colorMode(mode);
  } else {
    colorMode(mode);
  }
}

public void colorMode(int mode, float max, PGraphics other) {
  if(other != null) {
    other.colorMode(mode,max);
  } else {
    colorMode(mode,max);
  }
}

public void colorMode(int mode, float max1, float max2, float max3, PGraphics other) {
  if(other != null) {
    other.colorMode(mode,max1,max2,max3);
  } else {
    colorMode(mode,max1,max2,max3);
  }
}


public void colorMode(int mode, float max1, float max2, float max3, float maxA, PGraphics other) {
  if(other != null) {
    other.colorMode(mode,max1,max2,max3,maxA);
  } else {
    colorMode(mode,max1,max2,max3,maxA);
  }
}
/**
* beginDraw and enDraw() is write here juste to keep a syntew cohesion withe PGraphics other system
*/
public void beginDraw(PGraphics other) {
  if(other != null) {
    other.beginDraw();
  }
}

public void endDraw(PGraphics other) {
  if(other != null) {
    other.endDraw();
  }
}

public void clear(PGraphics other) {
  if(other != null && other.pixels != null) {
    other.clear();
  } else {
    g.clear();
  }
}




/**
* background
*/
public void background(int rgb, PGraphics other) {
  if(other != null) {
    other.background(rgb);
  } else {
    background(rgb);
  }
}

public void background(int rgb, float alpha, PGraphics other) {
  if(other != null) {
    other.background(rgb, alpha);
  } else {
    background(rgb, alpha);
  }
}

public void background(float gray, PGraphics other) {
  if(other != null) {
    other.background(gray);
  } else {
    background(gray);
  }
}

public void background(float gray, float alpha, PGraphics other) {
  if(other != null) {
    other.background(gray,alpha);
  } else {
    background(gray,alpha);
  }
}

public void background(float v1, float v2, float v3, PGraphics other) {
  if(other != null) {
    other.background(v1, v2, v3);
  } else {
    background(v1, v2, v3);
  }
}

public void background(float v1, float v2, float v3, float alpha, PGraphics other) {
  if(other != null) {
    other.background(v1, v2, v3, alpha);
  } else {
    background(v1, v2, v3 ,alpha);
  }
}

public void background(PImage img, PGraphics other) {
  if(other != null) {
    other.background(img);
  } else {
    background(img);
  }
}




/**
* Ellipse
*/
public void ellipse(float px, float py, float sx, float sy, PGraphics other) {
  if(other != null) {
    other.ellipse(px,py,sx,sy);
  } else {
    ellipse(px,py,sx,sy);
  }
}

public void ellipse(vec p, float x, float y) {
  ellipse(p,x,y,null);
}

public void ellipse(vec p, float x, float y, PGraphics other) {
  ellipse(p,vec2(x,y),other);
}

public void ellipse(vec p, float x) {
  ellipse(p,x,null);
}

public void ellipse(vec p, float x, PGraphics other) {
  ellipse(p,vec2(x),other);
}


/**
* main method
*/
public void ellipse(vec p, vec s) {
  ellipse(p,s,null);
}

public void ellipse(vec p, vec s, PGraphics other) {
  if(renderer_P3D() && p instanceof vec3) {
    push(other) ;
    translate(p.x(), p.y(), p.z(),other);
    ellipse(0,0, s.x(), s.y(),other);
    pop(other) ;
  } else {
    ellipse(p.x(),p.y(),s.x(),s.y(),other);
  }
}





/**
* square
*/
public void square(float px, float py, float extent, PGraphics other) {
  if(other != null) {
    other.square(px,py,extent);
  } else {
    square(px,py,extent);
  }
}

public void square(vec p, float extent) {
  square(p,extent,null);
}

public void square(vec p, float extent, PGraphics other) {
  if(renderer_P3D() && p instanceof vec3) {
    push(other);
    translate(p.x(),p.y(),p.z(),other);
    square(0,0,extent,other);
    pop(other);
  } else {
    square(p.x(),p.y(),extent,other);
  }
}





/**
* Rect
*/
public void rect(float px, float py, float sx, float sy, PGraphics other) {
  if(other != null) {
    other.rect(px,py,sx,sy);
  } else {
    rect(px,py,sx,sy);
  }
}

public void rect(vec p, vec s) {
  rect(p,s,null);
}

public void rect(vec p, vec s, PGraphics other) {
  if(renderer_P3D() && p instanceof vec3) {
    push(other);
    translate(p.x(),p.y(),p.z(),other);
    rect(0,0,s.x(),s.y(),other);
    pop(other);
  } else {
    rect(p.x(),p.y(),s.x(),s.y(),other);
  }
}


/**
* triangle
*/
public void triangle(float x1, float y1, float x2, float y2, float x3, float y3, PGraphics other) {
  if(other != null) {
    other.triangle(x1,y1, x2,y2, x3, y3);
  } else {
    triangle(x1,y1, x2,y2, x3, y3);
  }

}


public void triangle(vec a, vec b, vec c) {
  triangle(a,b,c,null);
}

public void triangle(vec a, vec b, vec c, PGraphics other) {
  if(a.z == 0 && b.z == 0 && c.z == 0) {
    triangle(a.x(),a.y(),b.x(),b.y(),c.x(),c.y(),other);
  } else {
    if(renderer_P3D() && a instanceof vec3 && b instanceof vec3 && c instanceof vec3) {
      beginShape(other);
      vertex(a.x(),a.y(),a.z(),other);
      vertex(b.x(),b.y(),b.z(),other);
      vertex(c.x(),c.y(),c.z(),other);
      endShape(CLOSE,other);
    } else {
      triangle(a.x(),a.y(),b.x(),b.y(),c.x(),c.y(),other);
    }
  }
}




/**
* Box
*/
public void box(float size, PGraphics other) {
  box(size,size,size,other);
}

public void box(float x, float y, float z, PGraphics other) {
  if(other != null) {
    other.box(x,y,z);
  } else {
    box(x,y,z);
  }
}

// 
public void box(vec3 p) {
  box(p,null);
}

public void box(vec3 p, PGraphics other) {
  box(p.x(),p.y(),p.z(),other);
}



/**
* Sphere
*/
public void sphere(float radius, PGraphics other) {
  if(other != null) {
    other.sphere(radius);
  } else {
    sphere(radius);
  }
}


public void sphereDetail(int res, PGraphics other) {
  if(other != null) {
    other.sphereDetail(res);
  } else {
    sphereDetail(res);
  }
}

public void sphereDetail(int ures, int vres, PGraphics other) {
  if(other != null) {
    other.sphereDetail(ures, vres);
  } else {
    sphereDetail(ures, vres);
  }

}



/**
* point
*/
public void point(float x, float y, float z, PGraphics other) {
  if(other != null) {
    other.point(x,y,z);
  } else {
    point(x,y,z);
  }
}

public void point(float x, float y, PGraphics other) {
  if(other != null) {
    other.point(x,y);
  } else {
    point(x,y);
  }
}


//
public void point(vec p) {
  point(p,null);
}

public void point(vec p, PGraphics other) {
  if(renderer_P3D() && p instanceof vec3) {
    point(p.x(),p.y(),p.z(),other); 
  } else {
    point(p.x(),p.y(),other);
  }
}




/**
* Line
*/
public void line(float x1, float y1, float x2, float y2, PGraphics other) {
  if(other != null) {
    other.line(x1,y1,x2,y2);
  } else {
    line(x1,y1,x2,y2);
  }
}

public void line(float x1, float y1, float z1, float x2, float y2, float z2, PGraphics other) {
  if(other != null) {
    other.line(x1,y1,z1,x2,y2,z2);
  } else {
    line(x1,y1,z1,x2,y2,z2);
  }
}

//
public void line(vec a, vec b) {
  line(a,b,null);
}

public void line(vec a, vec b, PGraphics other){
  if(renderer_P3D() && a instanceof vec3 && b instanceof vec3) {
    line(a.x(),a.y(),a.z(),b.x(),b.y(),b.z(),other); 
  } else {
    line(a.x(),a.y(),b.x(),b.y(),other);
  }
}















/**
* shape
*/
public void beginShape(PGraphics other) {
  if(other != null) {
    set_buffer_shape(other);
    buffer_rope_framework.beginShape(other);
  } else {
    beginShape();
  }
}

public void beginShape(int kind, PGraphics other) {
  if(other != null) {
    set_buffer_shape(other);
    buffer_rope_framework.beginShape(kind,other);
  } else {
    beginShape(kind);
  }
}


public void endShape(PGraphics other) {
  if(other != null) {
    set_buffer_shape(other);
    buffer_rope_framework.endShape(other);
  } else {
    endShape();
  }
}

public void endShape(int mode, PGraphics other) {
  if(other != null) {
    set_buffer_shape(other);
    buffer_rope_framework.endShape(mode,other);
  } else {
    endShape(mode);
  }
}


/**
* vertex
*/
public void vertex(float x, float y, PGraphics other) {
  if(other != null) {
    set_buffer_shape(other);
    buffer_rope_framework.vertex(x,y,other);
  } else {
    vertex(x,y);
  }
}



public void vertex(float x, float y, float z, PGraphics other) {
  if(other != null) {
    set_buffer_shape(other);
    if(renderer_P3D()) {
      buffer_rope_framework.vertex(x,y,z,other);
    } else {
      buffer_rope_framework.vertex(x,y,other);
    }   
  } else {
    vertex(x,y,z);
  }
}


public void vertex(float [] v, PGraphics other) {
  if(other != null) {
    set_buffer_shape(other);
    buffer_rope_framework.vertex(v,other);
  } else {
    vertex(v);
  }
}


public void vertex(float x, float y, float u, float v, PGraphics other) {
  if(other != null) {
    set_buffer_shape(other);
    buffer_rope_framework.vertex(x,y,u,v,other);
  } else {
    vertex(x,y,u,v,other);
  }
}

public void vertex(float x, float y, float z, float u, float v, PGraphics other) {
  if(other != null) {
    set_buffer_shape(other);
    if(renderer_P3D()) {
      buffer_rope_framework.vertex(x,y,u,v,other);
    } else {
      buffer_rope_framework.vertex(x,y,z,u,v,other);
    }
  } else {
    vertex(x,y,z,u,v,other);
  }
}


public void vertex(vec coord) {
  if(renderer_P3D() && coord instanceof vec3) {
    vertex(coord.x(),coord.y(),coord.z());
  } else {
    vertex(coord.x(),coord.y());
  }
}


public void vertex(vec coord, PGraphics other) {
  if(other != null) {
    set_buffer_shape(other);
    buffer_rope_framework.vertex(coord,other);
  } else {
    vertex(coord);
  }
}



//
public void vertex(vec2 coord, vec2 uv) {
  vertex(coord.x(),coord.y(),uv.x(),uv.y());
}

public void vertex(vec3 coord, vec2 uv) {
  if(renderer_P3D()) {
    vertex(coord.x(),coord.y(),coord.z(),uv.x(),uv.y());
  } else {
    vertex(coord.x(),coord.y(),uv.x(),uv.y());
  }
  
}

public void vertex(vec2 coord, vec2 uv, PGraphics other) {
  if(other != null) {
    set_buffer_shape(other);
    buffer_rope_framework.vertex(coord,uv,other);
  } else {
    vertex(coord,uv);
  }
}


public void vertex(vec3 coord, vec2 uv, PGraphics other) {
  if(other != null) {
    set_buffer_shape(other);
    buffer_rope_framework.vertex(coord,uv,other);
  } else {
    vertex(coord,uv);
  }
}



/**
* Bezier Vertex
*/
public void bezierVertex(float x2, float y2, float x3, float y3,  float x4, float y4, PGraphics other) {
  if(other != null) {
    set_buffer_shape(other);
    buffer_rope_framework.bezierVertex(x2,y2, x3,y3,  x4,y4, other);
  } else {
    bezierVertex(x2,y2, x3,y3,  x4,y4);
  }
}

public void bezierVertex(float x2, float y2, float z2, float x3, float y3, float z3, float x4, float y4, float z4, PGraphics other) {
  if(other != null) {
    set_buffer_shape(other);
    if(renderer_P3D()) {
      buffer_rope_framework.bezierVertex(x2,y2,z2, x3,y3,z3,  x4,y4,z4, other);
    } else {
      buffer_rope_framework.bezierVertex(x2,y2, x3,y3,  x4,y4, other);
    }
  } else {
    if(renderer_P3D()) {
      bezierVertex(x2,y2,z2, x3,y3,z3,  x4,y4,z4);
    } else {
      bezierVertex(x2,y2, x3,y3,  x4,y4);
    }
    
  }
}



//
public void bezierVertex(vec a, vec b, vec c) {
  if(a instanceof vec2 && b instanceof vec2 && b instanceof vec2) {
    bezierVertex(a.x(),a.y(),b.x(),b.y(),c.x(),c.y());
  } else if(a instanceof vec3 && b instanceof vec3 && b instanceof vec3) {
    if(renderer_P3D()) {
      bezierVertex(a.x(),a.y(),a.z(), b.x(),b.y(),b.z(), c.x(),c.y(),c.z());
    } else {
      bezierVertex(a.x(),a.y(), b.x(),b.y(), c.x(),c.y());
    }    
  } else {
    printErr("method bezierVertex() all arg need to be vec2 or vec3");
    exit();
  }
}

public void bezierVertex(vec a, vec b, vec c, PGraphics other) {
  if(other != null) {
    set_buffer_shape(other);
    buffer_rope_framework.bezierVertex(a,b,c,other);
  } else {
    bezierVertex(a,b,c);
  }
}













/**
* Quadratic Vertex
*/
public void quadraticVertex(float cx, float cy, float x3, float y3, PGraphics other) {
  if(other != null) {
    set_buffer_shape(other);
    buffer_rope_framework.quadraticVertex(cx,cy, x3,y3,other);
  } else {
    quadraticVertex(cx,cy, x3,y3);
  }
}

public void quadraticVertex(float cx, float cy, float cz, float x3, float y3, float z3, PGraphics other) {
  if(other != null) {
    set_buffer_shape(other);
    if(renderer_P3D()) {
      buffer_rope_framework.quadraticVertex(cx,cy,cz, x3,y3,z3,other);
    } else {
      buffer_rope_framework.quadraticVertex(cx,cy, x3,y3,other);
    }    
  } else {
    if(renderer_P3D()) {
      quadraticVertex(cx,cy,cz, x3,y3,z3);
    } else {
      quadraticVertex(cx,cy, x3,y3);
    }
  }
}

//
public void quadraticVertex(vec a, vec b) {
  if(a instanceof vec2 && b instanceof vec2) {
    quadraticVertex(a.x(),a.y(), b.x(),b.y());
  } else if(a instanceof vec3 && b instanceof vec3) {
    if(renderer_P3D()) {
      quadraticVertex(a.x(),a.y(),a.z(), b.x(),b.y(),b.z());
    } else {
      quadraticVertex(a.x(),a.y(), b.x(),b.y());
    } 
  } else {
    printErr("method quadraticVertex() all arg need to be vec2 or vec3");
    exit();
  }
}

public void quadraticVertex(vec a, vec b, PGraphics other) {
  if(other != null) {
    set_buffer_shape(other);
    buffer_rope_framework.quadraticVertex(a,b,other);
  } else {
    quadraticVertex(a,b);
  }
}














/**
* Curve Vertex
*/
public void curveVertex(float x, float y, PGraphics other) {
  if(other != null) {
    set_buffer_shape(other);
    buffer_rope_framework.curveVertex(x,y,other);
  } else {
    curveVertex(x,y);
  }
}

public void curveVertex(float x, float y, float z, PGraphics other) {
  if(other != null) {
    set_buffer_shape(other);
    if(renderer_P3D()) {
      buffer_rope_framework.curveVertex(x,y,z,other);
    } else {
      buffer_rope_framework.curveVertex(x,y,other);
    }   
  } else {
    if(renderer_P3D()) {
      curveVertex(x,y,z);
    } else {
      curveVertex(x,y);
    } 
  } 
}



//
public void curveVertex(vec a) {
  if(renderer_P3D() && a instanceof vec3) {
    curveVertex(a.x(),a.y(),a.z());
  } else {
    curveVertex(a.x(),a.y());
  } 
}

public void curveVertex(vec a, PGraphics other) {
   if(other != null) {
    set_buffer_shape(other);
    buffer_rope_framework.curveVertex(a,other);
  } else {
    curveVertex(a);
  }
}




/**
* strokeWeight
*/
public void strokeWeight(float v, PGraphics other) {
  if(other != null) {
    other.strokeWeight(v);
  } else {
    strokeWeight(v);
  }
}

public void noFill(PGraphics other) {
  if(other != null) {
    other.noFill();
  } else {
    noFill();
  }
}

public void noStroke(PGraphics other) {
  if(other != null) {
    other.noStroke();
  } else {
    noStroke();
  }
}



/**
* Fill
*/
public void fill(int rgb, PGraphics other) {
  if(other != null) {
    other.fill(rgb);
  } else {
    fill(rgb);
  }
}

public void fill(int rgb, float alpha, PGraphics other) {
  if(other != null) {
    other.fill(rgb,alpha);
  } else {
    fill(rgb,alpha);
  }
}

public void fill(float gray, PGraphics other) {
  if(other != null) {
    other.fill(gray);
  } else {
    fill(gray);
  }
}

public void fill(float gray, float alpha, PGraphics other) {
  if(other != null) {
    other.fill(gray,alpha);
  } else {
    fill(gray,alpha);
  }
}

public void fill(float v1, float v2, float v3, PGraphics other) {
  if(other != null) {
    other.fill(v1,v2,v3);
  } else {
    fill(v1,v2,v3);
  }
}

public void fill(float v1, float v2, float v3, float alpha, PGraphics other) {
  if(other != null) {
    other.fill(v1,v2,v3,alpha);
  } else {
    fill(v1,v2,v3,alpha);
  }
}


// vec
public void fill(vec2 c) {
  fill(c,null);
}

public void fill(vec2 c, PGraphics other) {
  if(c.y() > 0) fill(c.x(), c.y(),other); 
  else noFill(other);
}

//
public void fill(vec3 c) {
  fill(c,null);
}

public void fill(vec3 c, PGraphics other) {
  fill(c.x(),c.y(),c.z(),other) ;
}

//
public void fill(vec3 c, float a) {
  fill(c,a,null);
}
public void fill(vec3 c, float a, PGraphics other) {
  if(a > 0) fill(c.x(),c.y(),c.z(),a,other); 
  else noFill(other);
}

//
public void fill(vec4 c) {
  fill(c,null);
}

public void fill(vec4 c, PGraphics other) {
  if(c.w() > 0) fill(c.x(),c.y(),c.z(),c.w(),other); 
  else noFill(other);
}



/**
* Stroke
*/
public void stroke(int rgb, PGraphics other) {
  if(other != null) {
    other.stroke(rgb);
  } else {
    stroke(rgb);
  }
}

public void stroke(int rgb, float alpha, PGraphics other) {
  if(other != null) {
    other.stroke(rgb,alpha);
  } else {
    stroke(rgb,alpha);
  }
}

public void stroke(float gray, PGraphics other) {
  if(other != null) {
    other.stroke(gray);
  } else {
    stroke(gray);
  }
}

public void stroke(float gray, float alpha, PGraphics other) {
  if(other != null) {
    other.stroke(gray,alpha);
  } else {
    stroke(gray,alpha);
  }
}

public void stroke(float v1, float v2, float v3, PGraphics other) {
  if(other != null) {
    other.stroke(v1,v2,v3);
  } else {
    stroke(v1,v2,v3);
  }
}

public void stroke(float v1, float v2, float v3, float alpha, PGraphics other) {
  if(other != null) {
    other.stroke(v1,v2,v3,alpha);
  } else {
    stroke(v1,v2,v3,alpha);
  }
}


// vec
public void stroke(vec2 c) {
  stroke(c,null);
}

public void stroke(vec2 c, PGraphics other) {
  if(c.y() > 0) stroke(c.x(), c.y(),other); 
  else noStroke(other);
}

//
public void stroke(vec3 c) {
  stroke(c,null);
}

public void stroke(vec3 c, PGraphics other) {
  stroke(c.x(),c.y(),c.z(),other) ;
}

//
public void stroke(vec3 c, float a) {
  stroke(c,a,null);
}
public void stroke(vec3 c, float a, PGraphics other) {
  if(a > 0) stroke(c.x(),c.y(),c.z(),a,other); 
  else noStroke(other);
}

//
public void stroke(vec4 c) {
  stroke(c,null);
}

public void stroke(vec4 c, PGraphics other) {
  if(c.w() > 0) stroke(c.x(),c.y(),c.z(),c.w(),other); 
  else noStroke(other);
}




/**
* text
*/
// free mode
public void text(String s, float x, float y, PGraphics other) {
  if(other != null) {
    other.text(s, x,y);
  } else {
    text(s, x,y);
  }
}

public void text(char c, float x, float y, PGraphics other) {
  if(other != null) {
    other.text(c, x,y);
  } else {
    text(c, x,y);
  }
}

public void text(int i, float x, float y, PGraphics other) {
  if(other != null) {
    other.text(i, x,y);
  } else {
    text(i, x,y);
  }
}

public void text(float f, float x, float y, PGraphics other) {
  if(other != null) {
    other.text(f, x,y);
  } else {
    text(f, x,y);
  }
}

public void text(String s, float x, float y, float z, PGraphics other) {
  if(other != null) {
    other.text(s, x,y,z);
  } else {
    text(s, x,y,z);
  }
}

public void text(char c, float x, float y, float z, PGraphics other) {
  if(other != null) {
    other.text(c, x,y,z);
  } else {
    text(c, x,y,z);
  }
}

public void text(int i, float x, float y, float z, PGraphics other) {
  if(other != null) {
    other.text(i, x,y,z);
  } else {
    text(i, x,y,z);
  }
}

public void text(float f, float x, float y, float z, PGraphics other) {
  if(other != null) {
    other.text(f, x,y,z);
  } else {
    text(f, x,y,z);
  }
}

public void text(String s, float x, float y, float w, float h, PGraphics other) {
  if(other != null) {
    other.text(s, x,y, w,h);
  } else {
    text(s, x,y, w,h);
  }
}








// vec free mode
public void text(String s, vec pos) {
  text(s,pos,null);
}

public void text(String s, vec pos, PGraphics other) {
  if(pos instanceof vec2 && s != null) {
    vec2 p = (vec2)pos;
    text(s, p.x(),p.y(), other);
  } else if(pos instanceof vec3 && s != null) {
    vec3 p = (vec3)pos;
    text(s, p.x(),p.y(),p.z(), other);
  } else {
    printErrTempo(60,"method text(): String message is null or vec is not an instance of vec3 or vec2");
  }
}

public void text(char c, vec pos) {
  text(c,pos,null);
}

public void text(char c, vec pos, PGraphics other) {
  if(pos instanceof vec2) {
    vec2 p = (vec2)pos;
    text(c, p.x(),p.y(), other);
  } else if(pos instanceof vec3) {
    vec3 p = (vec3)pos;
    text(c, p.x(),p.y(),p.z(), other);
  }
}


public void text(int i, vec pos) {
  text(i,pos,null);
}

public void text(int i, vec pos, PGraphics other) {
  if(pos instanceof vec2) {
    vec2 p = (vec2)pos;
    text(i, p.x(),p.y(), other);
  } else if(pos instanceof vec3) {
    vec3 p = (vec3)pos;
    text(i, p.x(),p.y(),p.z(), other);
  } 
}

public void text(float f, vec pos) {
  text(f,pos,null);
}

public void text(float f, vec pos, PGraphics other) {
  if(pos instanceof vec2) {
    vec2 p = (vec2) pos;
    text(f, p.x(),p.y(), other);
  } else if(pos instanceof vec3) {
    vec3 p = (vec3) pos;
    text(f,p.x(),p.y(),p.z(),other);
  } 
}

public void text(String s, vec2 pos, vec2 size) {
  text(s, pos, size, null);
}

public void text(String s, vec2 pos, vec2 size, PGraphics other) {
  if(other != null) {
    other.text(s, pos.x(),pos.y(), size.x(),size.y());
  } else {
    text(s, pos.x(),pos.y(), size.x(),size.y());
  }
}





public void textAlign(int type, PGraphics other) {
  if(other != null) {
    other.textAlign(type);
  } else {
    textAlign(type);
  }
}


public void textSize(float size, PGraphics other) {
  if(other != null) {
    other.textSize(size);
  } else {
    textSize(size);
  }
}









/**
* Translate
*/
public void translate(float x, float y, float z, PGraphics other) {
  if(other != null) {
    other.translate(x,y,z);
  } else {
    translate(x,y,z);
  }
}

public void translate(float x, float y, PGraphics other) {
  if(other != null) {
    other.translate(x,y);
  } else {
    translate(x,y);
  }
}


// vec
public void translate(vec v) {
  translate(v,null);
}

public void translate(vec v, PGraphics other) {
  if(renderer_P3D() && v instanceof vec3) {
    translate(v.x(),v.y(),v.z(),other); 
  } else {
    translate(v.x(),v.y(),other);
  }
}




// translate X
public void translateX(float f) {
  translateX(f,null);
}

public void translateX(float f, PGraphics other) {
  translate(f,0,other);
}

// translate Y
public void translateY(float f) {
  translateY(f,null);
}

public void translateY(float f, PGraphics other) {
  translate(0,f,other);
}

// translate Z
public void translateZ(float f) {
  translateZ(f,null);
}

public void translateZ(float f, PGraphics other) {
  translate(0,0,f,other);
}


/**
* Rotate
*/
public void rotate(float f, PGraphics other) {
  if(other != null) {
    other.rotate(f);
  } else {
    rotate(f);
  }
}


public void rotateX(float f, PGraphics other) {
  if(other != null) {
    other.rotateX(f);
  } else {
    rotateX(f);
  }
}


public void rotateY(float f, PGraphics other) {
  if(other != null) {
    other.rotateY(f);
  } else {
    rotateY(f);
  }
}

public void rotateZ(float f, PGraphics other) {
  if(other != null) {
    other.rotateZ(f);
  } else {
    rotateZ(f);
  }
}


// vec
public void rotateXY(vec2 rot) {
  rotateXY(rot,null);
}

public void rotateXY(vec2 rot, PGraphics other) {
  rotateX(rot.x);
  rotateY(rot.y);
}


public void rotateXZ(vec2 rot) {
  rotateXZ(rot,null);
}

public void rotateXZ(vec2 rot, PGraphics other) {
  rotateX(rot.x);
  rotateZ(rot.y);
}

public void rotateYZ(vec2 rot) {
  rotateYZ(rot,null);
}

public void rotateYZ(vec2 rot, PGraphics other) {
  rotateY(rot.x);
  rotateZ(rot.y);
}

public void rotateXYZ(vec3 rot) {
  rotateXYZ(rot,null);
}

public void rotateXYZ(vec3 rot, PGraphics other) {
  rotateX(rot.x);
  rotateY(rot.y);
  rotateZ(rot.z);
}





/**
* Matrix
*/
// vec
public void push_3D(vec pos, vec3 dir_cart) {
  vec3 dir = dir_cart.copy() ;
  push();
  if(pos instanceof vec2) {
    vec2 p = (vec2) pos ;
    translate(p) ;
  } else if(pos instanceof vec3) {
    vec3 p = (vec3) pos ;
    translate(p) ;
  } else {
    printErr("Error in void push_3D(), vec pos is not an instance of vec2 or vec3, the matrix don't translate your object") ;
    // exit() ;
  }
  float radius = sqrt(dir.x * dir.x + dir.y * dir.y + dir.z * dir.z);
  float longitude = acos(dir.x / sqrt(dir.x * dir.x + dir.y * dir.y)) * (dir.y < 0 ? -1 : 1);
  float latitude = acos(dir.z / radius) * (dir.z < 0 ? -1 : 1);
  // check NaN result
  if (Float.isNaN(longitude)) longitude = 0;
  if (Float.isNaN(latitude)) latitude = 0;
  if (Float.isNaN(radius)) radius = 0;
  rotateX(latitude);
  rotateY(longitude);
}

public void push_3D(vec pos, vec2 dir_polar) {
  if(pos instanceof vec2) {
    vec2 p = (vec2) pos;
    push();
    translate(p);
    rotateXY(dir_polar);
  } else if(pos instanceof vec3) {
    vec3 p = (vec3) pos;
    push();
    translate(p);
    rotateXY(dir_polar);
  } else {
    printErr("Error in void push_3D(), vec pos is not an instance of vec2 or vec3, the matrix cannot be init") ;
  }
}

public void push_2D(vec pos, float orientation) {
  if(pos instanceof vec2) {
    vec2 p = (vec2)pos;
    push();
    translate(p);
    rotate(orientation);
  } else if(pos instanceof vec3) {
    vec3 p = (vec3)pos;
    push();
    translate(p.x, p.y);
    rotate(orientation);
  } else {
    printErr("Error in void push_3D(), vec pos is not an instance of vec2 or vec3, the matrix cannot be init") ;
  }
}






// push and pop
public void push(PGraphics other) {
  if(other != null) {
    other.push();
  } else {
    push();
  }
}

public void pop(PGraphics other) {
  if(other != null) {
    other.pop();
  } else {
    pop();
  }
}























































/**
* GHOST METHODS for PROCESSING
* 2018-2019
* v 0.3.0
*/
public boolean get_layer_is_correct() {
  if(get_layer() != null && get_layer().width > 0 && get_layer().height > 0) {
    return true;
  } else {
    return false;
  }
}

// colorMode
public void colorMode(int mode) {
  if(get_layer_is_correct()) {
    get_layer().colorMode(mode);
  } else {
    g.colorMode(mode);
  }
}

public void colorMode(int mode, float max) {
  if(get_layer_is_correct()) {
    get_layer().colorMode(mode,max);
  } else {
    g.colorMode(mode,max);
  } 
}


public void colorMode(int mode, float max1, float max2, float max3) {
  if(get_layer_is_correct()) {
    get_layer().colorMode(mode,max1,max2,max3);
  } else {
    g.colorMode(mode,max1,max2,max3);
  }
}
public void colorMode(int mode, float max1, float max2, float max3, float maxA) {
  if(get_layer_is_correct()) {
    get_layer().colorMode(mode,max1,max2,max3,maxA);
  } else {
    g.colorMode(mode,max1,max2,max3,maxA);
  }
}




// Processing ghost method

// position
public void translate(float x, float y) {
  if(get_layer_is_correct()) {
    get_layer().translate(x,y);
  } else {
    g.translate(x,y);
  }
}

public void translate(float x, float y, float z) {
  if(get_layer_is_correct()) {
    get_layer().translate(x,y,z);
  } else {
    g.translate(x,y,z);
  }
}


// rotate
public void rotate(float arg) {
  if(get_layer_is_correct()) {
    get_layer().rotate(arg);
  } else {
    g.rotate(arg);
  }
}


public void rotateX(float arg) {
  if(get_layer_is_correct()) {
    get_layer().rotateX(arg);
  } else {
    g.rotateX(arg);
  }
}

public void rotateY(float arg) {
  if(get_layer_is_correct()) {
    get_layer().rotateY(arg);
  } else {
    g.rotateY(arg);
  }
}


public void rotateZ(float arg) {
  if(get_layer_is_correct()) {
    get_layer().rotateZ(arg);
  } else {
    g.rotateZ(arg);
  }
}

// scale
public void scale(float s) {
  if(get_layer_is_correct()) {
    get_layer().scale(s);
  } else {
    g.scale(s);
  }
}

public void scale(float x, float y) {
  if(get_layer_is_correct()) {
    get_layer().scale(x,y);
  } else {
    g.scale(x,y);
  }
}

public void scale(float x, float y, float z) {
  if(get_layer_is_correct()) {
    get_layer().scale(x,y,z);
  } else {
    g.scale(x,y,z);
  }
}

// shear
public void shearX(float angle) {
  if(get_layer_is_correct()) {
    get_layer().shearX(angle);
  } else {
    g.shearX(angle);
  }
}

public void shearY(float angle) {
  if(get_layer_is_correct()) {
    get_layer().shearY(angle);
  } else {
    g.shearY(angle);
  }
}













/**
aspect
*/
// fill
public void noFill() {
  if(get_layer_is_correct()) {
    get_layer().noFill();
  } else {
    g.noFill();
  }
} 

public void fill(int rgb) {
  if(get_layer_is_correct()) {
    get_layer().fill(rgb);
  } else {
    g.fill(rgb);
  }
}


public void fill(int rgb, float alpha) {
  if(get_layer_is_correct()) {
    get_layer().fill(rgb,alpha);
  } else {
    g.fill(rgb,alpha);
  }
}

public void fill(float gray) {
  if(get_layer_is_correct()) {
    get_layer().fill(gray);
  } else {
    g.fill(gray);
  }
}


public void fill(float gray, float alpha) {
  if(get_layer_is_correct()) {
    get_layer().fill(gray,alpha);
  } else {
    g.fill(gray,alpha);
  }
}

public void fill(float v1, float v2, float v3) {
  if(get_layer_is_correct()) {
    get_layer().fill(v1,v2,v3);
  } else {
    g.fill(v1,v2,v3);
  }
}

public void fill(float v1, float v2, float v3, float alpha) {
  if(get_layer_is_correct()) {
    get_layer().fill(v1,v2,v3,alpha);
  } else {
    g.fill(v1,v2,v3,alpha);
  }
}

// stroke
public void noStroke() {
  if(get_layer_is_correct()) {
    get_layer().noStroke();
  } else {
    g.noStroke();
  }
} 

public void stroke(int rgb) {
  if(get_layer_is_correct()) {
    get_layer().stroke(rgb);
  } else {
    g.stroke(rgb);
  }
}




public void stroke(int rgb, float alpha) {
  if(get_layer_is_correct()) {
    get_layer().stroke(rgb,alpha);
  } else {
    g.stroke(rgb,alpha);
  }
}

public void stroke(float gray) {
  if(get_layer_is_correct()) {
    get_layer().stroke(gray);
  } else {
    g.stroke(gray);
  }
}


public void stroke(float gray, float alpha) {
  if(get_layer_is_correct()) {
    get_layer().stroke(gray,alpha);
  } else {
    g.stroke(gray,alpha);
  }
}

public void stroke(float v1, float v2, float v3) {
  if(get_layer_is_correct()) {
    get_layer().stroke(v1,v2,v3);
  } else {
    g.stroke(v1,v2,v3);
  }
}

public void stroke(float v1, float v2, float v3, float alpha) {
  if(get_layer_is_correct()) {
    get_layer().stroke(v1,v2,v3,alpha);
  } else {
    g.stroke(v1,v2,v3,alpha);
  }
}


// strokeWeight
public void strokeWeight(float thickness) {
  if(get_layer_is_correct()) {
    get_layer().strokeWeight(thickness);
  } else {
    g.strokeWeight(thickness);
  }
}

// strokeJoin
public void strokeJoin(int join) {
  if(get_layer_is_correct()) {
    get_layer().strokeJoin(join);
  } else {
    g.strokeJoin(join);
  }
}

// strokeJoin
public void strokeCapstrokeCap(int cap) {
  if(get_layer_is_correct()) {
    get_layer().strokeCap(cap);
  } else {
    g.strokeCap(cap);
  }
}












/**
shape
*/

public void rectMode(int mode) {
  if(get_layer_is_correct()) {
    get_layer().rectMode(mode);
  } else {
    g.rectMode(mode);
  }
}

public void ellipseMode(int mode) {
  if(get_layer_is_correct()) {
    get_layer().ellipseMode(mode);
  } else {
    g.ellipseMode(mode);
  }
}


// square
public void square(float px, float py, float extent) {
  if(get_layer_is_correct()) {
    get_layer().square(px,py,extent);
  } else {
    g.square(px,py,extent);
  }
}


// rect
public void rect(float px, float py, float sx, float sy) {
  if(get_layer_is_correct()) {
    get_layer().rect(px,py,sx,sy);
  } else {
    g.rect(px,py,sx,sy);
  }
}


public void rect(float px, float py, float sx, float sy, float r) {
  if(get_layer_is_correct()) {
    get_layer().rect(px,py,sx,sy,r);
  } else {
    g.rect(px,py,sx,sy,r);
  }
}

public void rect(float px, float py, float sx, float sy, float tl, float tr, float br, float bl) {
  if(get_layer_is_correct()) {
    get_layer().rect(px,py,sx,sy,tl,tr,br,bl);
  } else {
    g.rect(px,py,sx,sy,tl,tr,br,bl);
  }
}


//arc
public void arc(float a, float b, float c, float d, float start, float stop) {
  if(get_layer_is_correct()) {
    get_layer().arc(a,b,c,d,start,stop);
  } else {
    g.arc(a,b,c,d,start,stop);
  }
}

public void arc(float a, float b, float c, float d, float start, float stop, int mode) {
  if(get_layer_is_correct()) {
    get_layer().arc(a,b,c,d,start,stop,mode);
  } else {
    g.arc(a,b,c,d,start,stop,mode);
  }
}

// ellipse
public void ellipse(int px, int py, int sx, int sy) {
  if(get_layer_is_correct()) {
    get_layer().ellipse(px,py,sx,sy);
  } else {
    g.ellipse(px,py,sx,sy);
  }
}




// box
public void box(float s) {
  if(get_layer_is_correct()) {
    get_layer().box(s,s,s);
  } else {
    g.box(s,s,s);
  }
}

public void box(float w, float h, float d) {
  if(get_layer_is_correct()) {
    get_layer().box(w,h,d);
  } else {
    g.box(w,h,d);
  }
}


// sphere
public void sphere(float r) {
  if(get_layer_is_correct()) {
    get_layer().sphere(r);
  } else {
    g.sphere(r);
    // p.sphere(r);
  }
}


// sphere detail
public void sphereDetail(int res) {
  if(get_layer_is_correct()) {
    get_layer().sphereDetail(res);
  } else {
    g.sphereDetail(res);
  }
}

public void sphereDetail(int ures, int vres) {
  if(get_layer_is_correct()) {
    get_layer().sphereDetail(ures,vres);
  } else {
    g.sphereDetail(ures,vres);
  }
}




//line
public void line(float x1, float y1, float x2, float y2) {
  if(get_layer_is_correct()) {
    get_layer().line(x1,y1,x2,y2);
  } else {
    g.line(x1,y1,x2,y2);
  }
}

public void line(float x1, float y1, float z1, float x2, float y2, float z2) {
  if(get_layer_is_correct()) {
    get_layer().line(x1,y1,z1,x2,y2,z2);
  } else {
    g.line(x1,y1,z1,x2,y2,z2);
  }
}






// point
public void point(float x, float y) {
  if(get_layer_is_correct()) {
    get_layer().point(x,y);
  } else {
    g.point(x,y);
  }
}

public void point(float x, float y, float z) {
  if(get_layer_is_correct()) {
    get_layer().point(x,y,z);
  } else {
    g.point(x,y,z);
  }
}

// quad
public void quad(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4) {
  if(get_layer_is_correct()) {
    get_layer().quad(x1,y1,x2,y2,x3,y3,x4,y4);
  } else {
    g.quad(x1,y1,x2,y2,x3,y3,x4,y4);
  }
}

// triangle
/*
method already use somewhere else
void triangle(float x1, float y1, float x2, float y2, float x3, float y3) {
  if(get_layer() != null) {
    get_layer().triangle(x1,y1,x2,y2,x3,y3);
  } else {
    g.triangle(x1,y1,x2,y2,x3,y3);
  }
}
*/


/**
vertex
*/
// begin
public void beginShape() {
  if(get_layer_is_correct()) {
    get_layer().beginShape();
  } else {
    g.beginShape();
  }
}

public void beginShape(int kind) {
  if(get_layer_is_correct()) {
    get_layer().beginShape(kind);
  } else {
    g.beginShape(kind);
  }
}


public void endShape() {
  if(get_layer_is_correct()) {
    get_layer().endShape();
  } else {
    g.endShape();
  }
}

public void endShape(int mode) {
  if(get_layer_is_correct()) {
    get_layer().endShape(mode);
  } else {
    g.endShape(mode);
  }
}

// shape
public void shape(PShape shape) {
  if(get_layer_is_correct()) {
    get_layer().shape(shape);
  } else {
    g.shape(shape);
  }
}

public void shape(PShape shape, float x, float y) {
  if(get_layer_is_correct()) {
    get_layer().shape(shape,x,y);
  } else {
    g.shape(shape,x,y);
  }
}

public void shape(PShape shape, float a, float b, float c, float d) {
  if(get_layer_is_correct()) {
    get_layer().shape(shape,a,b,c,d);
  } else {
    g.shape(shape,a,b,c,d);
  }
}




//vertex
public void vertex(float x, float y) {
  if(get_layer_is_correct()) {
    get_layer().vertex(x,y);
  } else {
    g.vertex(x,y);
  }
}



public void vertex(float x, float y, float z) {
  if(get_layer_is_correct()) {
    if(renderer_P3D()) { 
      get_layer().vertex(x,y,z);
    } else {
      get_layer().vertex(x,y);
    }
  } else {
    if(renderer_P3D()) {
      g.vertex(x,y,z);
    } else {
      g.vertex(x,y);
    }
  }
}

public void vertex(float [] v) {
  if(get_layer_is_correct()) {
    get_layer().vertex(v);
  } else {
    g.vertex(v);
  }
}

public void vertex(float x, float y, float u, float v) {
  if(get_layer_is_correct()) {
    get_layer().vertex(x,y,u,v);
  } else {
    g.vertex(x,y,u,v);
  }
} 


public void vertex(float x, float y, float z, float u, float v) {
  if(get_layer_is_correct()) {
    if(renderer_P3D()) { 
      get_layer().vertex(x,y,z,u,v);
    } else {
      get_layer().vertex(x,y,u,v);
    }
  } else {
    if(renderer_P3D()) { 
      g.vertex(x,y,z,u,v);
    } else {
      g.vertex(x,y,u,v);
    }
  }
}  


// quadratic vertex
public void quadraticVertex(float cx, float cy, float x3, float y3) {
  if(get_layer_is_correct()) {
    get_layer().quadraticVertex(cx,cy,x3,y3);
  } else {
    g.quadraticVertex(cx,cy,x3,y3);
  }
}

public void quadraticVertex(float cx, float cy, float cz, float x3, float y3, float z3) {
  if(get_layer_is_correct()) {
    if(renderer_P3D()) {
      get_layer().quadraticVertex(cx,cy,cz,x3,y3,z3);
    } else {
      get_layer().quadraticVertex(cx,cy,x3,y3);
    }
  } else {
    if(renderer_P3D()) {
      g.quadraticVertex(cx,cy,cz,x3,y3,z3);
    } else {
      g.quadraticVertex(cx,cy,x3,y3);
    }
  }
}

// curve vertex
public void curveVertex(float x, float y) {
  if(get_layer_is_correct()) {
    get_layer().curveVertex(x,y);
  } else {
    g.curveVertex(x,y);
  }
}

public void curveVertex(float x, float y, float z) {
  if(get_layer_is_correct()) {
    if(renderer_P3D()) {
      get_layer().curveVertex(x,y,z);
    } else {
      get_layer().curveVertex(x,y);
    }
  } else {
    if(renderer_P3D()) {
      g.curveVertex(x,y,z);
    } else {
      g.curveVertex(x,y);
    }
  }
}


//bezier vertex
public void bezierVertex(float x2, float y2, float x3, float y3, float x4, float y4) {
  if(get_layer_is_correct()) {
    get_layer().bezierVertex(x2,y2,x3,y3,x4,y4);
  } else {
    g.bezierVertex(x2,y2,x3,y3,x4,y4);
  }
}


public void bezierVertex(float x2, float y2, float z2, float x3, float y3, float z3, float x4, float y4, float z4) {
  if(get_layer_is_correct()) {
    if(renderer_P3D()) {
      get_layer().bezierVertex(x2,y2,z2, x3,y3,z3, x4,y4,z4);
    } else {
      get_layer().bezierVertex(x2,y2, x3,y3 ,x4,y4 );
    }
  } else {
    if(renderer_P3D()) {
      g.bezierVertex(x2,y2,z2, x3,y3,z3, x4,y4,z4);
    } else {
      g.bezierVertex(x2,y2, x3,y3, x4,y4);
    }
  }
}

// bezier
public void bezier(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4) {
  if(get_layer_is_correct()) {
    get_layer().bezier(x1,y1,x2,y2,x3,y3,x4,y4);
  } else {
    g.bezier(x1,y1,x2,y2,x3,y3,x4,y4);
  }
}

public void bezier(float x1, float y1, float z1, float x2, float y2, float z2, float x3, float y3, float z3, float x4, float y4, float z4) {
  if(get_layer_is_correct()) {
    if(renderer_P3D()) {
      get_layer().bezier(x1,y1,z1, x2,y2,z2, x3,y3,z3, x4,y4,z4);
    } else {
      get_layer().bezier(x1,y1, x2,y2, x3,y3, x4,y4);
    }
  } else {
    if(renderer_P3D()) {
      g.bezier(x1,y1,z1, x2,y2,z2, x3,y3,z3, x4,y4,z4);
    } else {
      g.bezier(x1,y1, x2,y2 ,x3,y3 ,x4,y4);
    }
  }
}

// bezier detail
public void bezierDetail(int detail) {
  if(get_layer_is_correct()) {
    get_layer().bezierDetail(detail);
  } else {
    g.bezierDetail(detail);
  }
}

// curve
public void curve(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4) {
  if(get_layer_is_correct()) {
    get_layer().curve(x1,y1,x2,y2,x3,y3,x4,y4);
  } else {
    g.curve(x1,y1,x2,y2,x3,y3,x4,y4);
  }
}


public void curve(float x1, float y1, float z1, float x2, float y2, float z2, float x3, float y3, float z3, float x4, float y4, float z4) {
  if(get_layer_is_correct()) {
    if(renderer_P3D()) {
      get_layer().curve(x1,y1,z1, x2,y2,z2, x3,y3,z3, x4,y4,z4);
    } else {
      get_layer().curve(x1,y1, x2,y2, x3,y3, x4,y4);
    }
  } else {
    if(renderer_P3D()) {
      g.curve(x1,y1,z1, x2,y2,z2, x3,y3,z3, x4,y4,z4);
    } else {
      g.curve(x1,y1, x2,y2, x3,y3, x4,y4);
    }
  }
}

// curve detail
public void curveDetail(int detail) {
  if(get_layer_is_correct()) {
    get_layer().curveDetail(detail);
  } else {
    g.curveDetail(detail);
  }
}




















// light
public void lights() {
  if(get_layer_is_correct()) {
    get_layer().lights();
  } else {
    g.lights();
  }
}

public void noLights() {
  if(get_layer_is_correct()) {
    get_layer().noLights();
  } else {
    g.noLights();
  }
}

public void ambientLight(float v1, float v2, float v3) {
  if(get_layer_is_correct()) {
    get_layer().ambientLight(v1,v2,v3);
  } else {
    g.ambientLight(v1,v2,v3);
  }
}


public void ambientLight(float v1, float v2, float v3, float x, float y, float z) {
  if(get_layer_is_correct()) {
    get_layer().ambientLight(v1,v2,v3,x,y,z);
  } else {
    g.ambientLight(v1,v2,v3,x,y,z);
  }
}


public void directionalLight(float v1, float v2, float v3, float nx, float ny, float nz) {
  if(get_layer_is_correct()) {
    get_layer().directionalLight(v1,v2,v3,nx,ny,nz);
  } else {
    g.directionalLight(v1,v2,v3,nx,ny,nz);
  }
}


public void lightFalloff(float constant, float linear, float quadratic) {
  if(get_layer_is_correct()) {
    get_layer().lightFalloff(constant,linear,quadratic);
  } else {
    g.lightFalloff(constant,linear,quadratic);
  }
}


public void lightSpecular(float v1, float v2, float v3) {
  if(get_layer_is_correct()) {
    get_layer().lightSpecular(v1,v2,v3);
  } else {
    g.lightSpecular(v1,v2,v3);
  }
}

public void normal(float nx, float ny, float nz) {
  if(get_layer_is_correct()) {
    get_layer().normal(nx,ny,nz);
  } else {
    g.normal(nx,ny,nz);
  }
}

public void pointLight(float v1, float v2, float v3, float x, float y, float z) {
  if(get_layer_is_correct()) {
    get_layer().pointLight(v1,v2,v3,x,y,z);
  } else {
    g.pointLight(v1,v2,v3,x,y,z);
  }
}

public void spotLight(float v1, float v2, float v3, float x, float y, float z, float nx, float ny, float nz, float angle, float concentration) {
  if(get_layer_is_correct()) {
    get_layer().spotLight(v1,v2,v3,x,y,z,nx,ny,nz,angle,concentration);
  } else {
    g.spotLight(v1,v2,v3,x,y,z,nx,ny,nz,angle,concentration);
  }
}


/**
Material properties
*/
public void ambient(int rgb) {
  if(get_layer_is_correct()) {
    get_layer().ambient(rgb);
  } else {
    g.ambient(rgb);
  }
}

public void ambient(float gray) {
  if(get_layer_is_correct()) {
    get_layer().ambient(gray);
  } else {
    g.ambient(gray);
  }
}


public void ambient(float v1, float v2, float v3) {
  if(get_layer_is_correct()) {
    get_layer().ambient(v1,v2,v3);
  } else {
    g.ambient(v1,v2,v3);
  }
}


// emissive
public void emissive(int rgb) {
  if(get_layer_is_correct()) {
    get_layer().emissive(rgb);
  } else {
    g.emissive(rgb);
  }
}

public void emissive(float gray) {
  if(get_layer_is_correct()) {
    get_layer().emissive(gray);
  } else {
    g.emissive(gray);
  }
}


public void emissive(float v1, float v2, float v3) {
  if(get_layer_is_correct()) {
    get_layer().emissive(v1,v2,v3);
  } else {
    g.emissive(v1,v2,v3);
  }
}


// specular
public void specular(int rgb) {
  if(get_layer_is_correct()) {
    get_layer().specular(rgb);
  } else {
    g.specular(rgb);
  }
}

public void specular(float gray) {
  if(get_layer_is_correct()) {
    get_layer().specular(gray);
  } else {
    g.specular(gray);
  }
}


public void specular(float v1, float v2, float v3) {
  if(get_layer_is_correct()) {
    get_layer().specular(v1,v2,v3);
  } else {
    g.specular(v1,v2,v3);
  }
}


// shininess(shine)
public void shininess(float shine) {
  if(get_layer_is_correct()) {
    get_layer().shininess(shine);
  } else {
    g.shininess(shine);
  }
}























/**
* camera ghost
* v 0.2.0
*/
public void camera() {
  if(get_layer_is_correct()) {
    get_layer().camera();
  } else {
    g.camera();
  }
}

public void camera(float orientation_x, float orientation_y, float orientation_z, float pos_x, float pos_y, float pos_z, float up_x, float up_y, float up_z) {
  // orientation is eye
  // pos i center
  // up is up !
  if(get_layer_is_correct()) {
    get_layer().camera(orientation_x,orientation_y,orientation_z, pos_x,pos_y,pos_z, up_x,up_y,up_z);
  } else {
    g.camera(orientation_x,orientation_y,orientation_z, pos_x,pos_y,pos_z, up_x,up_y,up_z);
  }
}


public void beginCamera() {
  if(get_layer_is_correct()) {
    get_layer().beginCamera();
  } else {
    g.beginCamera();
  }
}

public void endCamera() {
  if(get_layer_is_correct()) {
    get_layer().endCamera();
  } else {
    g.endCamera();
  }
}


// frustum(left, right, bottom, top, near, far)
public void frustum(float left, float right, float bottom, float top, float near, float far) {
  if(get_layer_is_correct()) {
    get_layer().frustum(left,right,bottom,top,near,far);
  } else {
    g.frustum(left,right,bottom,top,near,far);
  }
}


// ortho
public void ortho() {
  if(get_layer_is_correct()) {
    get_layer().ortho();
  } else {
    g.ortho();
  }
}

public void ortho(float left, float right, float bottom, float top) {
  if(get_layer_is_correct()) {
    get_layer().ortho(left,right,bottom,top);
  } else {
    g.ortho(left,right,bottom,top);
  }
}


public void ortho(float left, float right, float bottom, float top, float near, float far) {
  if(get_layer_is_correct()) {
    get_layer().ortho(left,right,bottom,top,near,far);
  } else {
    g.ortho(left,right,bottom,top,near,far);
  }
}


  
// perspective
public void perspective() {
  if(get_layer_is_correct()) {
    get_layer().perspective();
  } else {
    g.perspective();
  }
}


public void perspective(float fovy, float aspect, float zNear, float zFar) {
  if(get_layer_is_correct()) {
    get_layer().perspective(fovy,aspect,zNear,zFar);
  } else {
    g.perspective(fovy,aspect,zNear,zFar);
  }
}


















/**
matrix
*/


// push and pop
public void push() {
  if(get_layer_is_correct()) {
    get_layer().push();
  } else {
    g.push();
  }
}


public void pushMatrix() {
  if(get_layer_is_correct()) {
    get_layer().pushMatrix();
  } else {
    g.pushMatrix();
  }
}


public void pop() {
  if(get_layer_is_correct()) {
    get_layer().pop();
  } else {
    g.pop();
  }
}

public void popMatrix() {
  if(get_layer_is_correct()) {
    get_layer().popMatrix();
  } else {
    g.popMatrix();
  }
}


// apply matrix
public void applyMatrix(PMatrix source) {
  if(get_layer_is_correct()) {
    get_layer().applyMatrix(source);
  } else {
    g.applyMatrix(source);
  }
}

public void applyMatrix(float n00, float n01, float n02, float n10, float n11, float n12) {
  if(get_layer_is_correct()) {
    get_layer().applyMatrix(n00,n01,n02,n10,n11,n12);
  } else {
    g.applyMatrix(n00,n01,n02,n10,n11,n12);
  }
}

public void applyMatrix(float n00, float n01, float n02, float n03, float n10, float n11, float n12, float n13, float n20, float n21, float n22, float n23, float n30, float n31, float n32, float n33) {
  if(get_layer_is_correct()) {
    get_layer().applyMatrix(n00,n01,n02,n03,n10,n11,n12,n13,n20,n21,n22,n23,n30,n31,n32,n33);
  } else {
    g.applyMatrix(n00,n01,n02,n03,n10,n11,n12,n13,n20,n21,n22,n23,n30,n31,n32,n33);
  }
}



public void resetMatrix() {
  if(get_layer_is_correct()) {
    get_layer().resetMatrix();
  } else {
    g.resetMatrix();
  }
}


  












/**
image
*/
public void image(PImage img, float x, float y) {
  if(get_layer_is_correct()) {
    get_layer().image(img,x,y);
  } else {
    g.image(img,x,y);
  }
}

public void image(PImage img, float a, float b, float c, float d) {
  if(get_layer_is_correct()) {
    get_layer().image(img,a,b,c,d);
  } else {
    g.image(img,a,b,c,d);
  }
}














/**
get
*/
public int get(int x, int y) {
  if(get_layer_is_correct()) {
    return get_layer().get(x,y);
  } else {
    return g.get(x,y);
  }
} 


public PImage get(int x, int y, int w, int h) {
  if(get_layer_is_correct()) {
    return get_layer().get(x,y,w,h);
  } else {
    return g.get(x,y,w,h);
  }
}


public PImage get() {
  if(get_layer_is_correct()) {
    return get_layer().get();
  } else {
    return g.get();
  }
}









/**
loadPixels()
*/
public void loadPixels() {
  if(get_layer_is_correct()) {
    get_layer().loadPixels();
  } else {
    g.loadPixels();
  }
}


/**
updatePixels()
*/
public void updatePixels() {
  if(get_layer_is_correct()) {
    get_layer().updatePixels();
  } else {
    g.updatePixels();
  }
}








/**
tint
*/
public void tint(int rgb) {
  if(get_layer_is_correct()) {
    get_layer().tint(rgb);
  } else {
    g.tint(rgb);
  }
}

public void tint(int rgb, float alpha) {
  if(get_layer_is_correct()) {
    get_layer().tint(rgb,alpha);
  } else {
    g.tint(rgb,alpha);
  }
}

public void tint(float gray) {
  if(get_layer_is_correct()) {
    get_layer().tint(gray);
  } else {
    g.tint(gray);
  }
}

public void tint(float gray, float alpha) {
  if(get_layer_is_correct()) {
    get_layer().tint(gray,alpha);
  } else {
    g.tint(gray,alpha);
  }
}

public void tint(float v1, float v2, float v3) {
  if(get_layer_is_correct()) {
    get_layer().tint(v1,v2,v3);
  } else {
    g.tint(v1,v2,v3);
  }
}

public void tint(ivec4 v) {
  tint(v.x,v.y,v.z,v.w);
}

public void tint(ivec3 v) {
  tint(v.x,v.y,v.z,g.colorModeA);
}

public void tint(ivec2 v) {
  tint(v.x,v.x,v.x,v.y);
}

public void tint(vec4 v) {
  tint(v.x,v.y,v.z,v.w);
}

public void tint(vec3 v) {
  tint(v.x,v.y,v.z,g.colorModeA);
}

public void tint(vec2 v) {
  tint(v.x,v.x,v.x,v.y);
}

public void tint(float v1, float v2, float v3, float alpha) {
  if(get_layer_is_correct()) {
    get_layer().tint(v1,v2,v3,alpha);
  } else {
    g.tint(v1,v2,v3,alpha);
  }
}















/**
blend
*/
public void blend(int sx, int sy, int sw, int sh, int dx, int dy, int dw, int dh, int mode) {
  if(get_layer_is_correct()) {
    get_layer().blend(sx,sy,sw,sh,dx,dy,dw,dh,mode);
  } else {
    g.blend(sx,sy,sw,sh,dx,dy,dw,dh,mode);
  }
}


public void blend(PImage src, int sx, int sy, int sw, int sh, int dx, int dy, int dw, int dh, int mode) {
  if(get_layer_is_correct()) {
    get_layer().blend(src,sx,sy,sw,sh,dx,dy,dw,dh,mode);
  } else {
    g.blend(src,sx,sy,sw,sh,dx,dy,dw,dh,mode);
  }
}













/**
filter
v 0.0.2
*/
public void filter(PShader shader) {
  if(get_layer_is_correct()) {
    println("void filter(PShader shader)", get_layer());
    println("void filter(PShader shader)", get_layer().width);
    println("void filter(PShader shader)", get_layer().height);
    println("void filter(PShader shader)", shader);
    get_layer().filter(shader);
  } else if (g.pixels != null) {
    g.filter(shader);
  }
}

public void filter(int kind) {
  if(get_layer_is_correct()) {
    get_layer().filter(kind);
  } else if (g.pixels != null) {
    g.filter(kind);
  }
}

public void filter(int kind, float param) {
  if(get_layer_is_correct()) {
    get_layer().filter(kind,param);
  } else if (g.pixels != null) {
    g.filter(kind,param);
  }
}













/**
set
*/
public void set(int x, int y, int c) {
  if(get_layer_is_correct()) {
    get_layer().set(x,y,c);
  } else {
    /*
    x *= displayDensity();
    y *= displayDensity();
    */
    g.set(x,y,c);
  }
}

public void set(int x, int y, PImage img) {
  if(get_layer_is_correct()) {
    get_layer().set(x,y,img);
  } else {
    /*
    x *= displayDensity();
    y *= displayDensity();
    */
    g.set(x,y,img);
  }
}














/**
text
2017-2019
v 0.1.2
*/
public void text(char c, float x, float y) {
  if(get_layer_is_correct()) {
    get_layer().text(c,x,y);
  } else {
    g.text(c,x,y);
  }
}


public void text(char c, float x, float y, float z) {
  if(get_layer_is_correct()) {
    get_layer().text(c,x,y,z);
  } else {
    g.text(c,x,y,z);
  }
}

public void text(char [] chars, int start, int stop, float x, float y) {
  if(get_layer_is_correct()) {
    get_layer().text(chars,start,stop,x,y);
  } else {
    g.text(chars,start,stop,x,y);
  }
}


public void text(char [] chars, int start, int stop, float x, float y, float z) {
  if(get_layer_is_correct()) {
    get_layer().text(chars,start,stop,x,y,z);
  } else {
    g.text(chars,start,stop,x,y,z);
  }
}



public void text(String str, float x, float y) {
  if(get_layer_is_correct()) {
    get_layer().text(str,x,y);
  } else {
    g.text(str,x,y);
  }
}


public void text(String str, float x, float y, float z) {
  if(get_layer_is_correct()) {
    get_layer().text(str,x,y,z);
  } else {
    g.text(str,x,y,z);
  }
}


public void text(String str, float x1, float y1, float x2, float y2) {
  if(get_layer_is_correct()) {
    get_layer().text(str,x1,y1,x2,y2);
  } else {
    g.text(str,x1,y1,x2,y2);
  }
}

public void text(float num, float x, float y) {
  if(get_layer_is_correct()) {
    get_layer().text(num,x,y);
  } else {
    g.text(num,x,y);
  }
}


public void text(float num, float x, float y, float z) {
  if(get_layer_is_correct()) {
    get_layer().text(num,x,y,z);
  } else {
    g.text(num,x,y,z);
  }
}


public void text(int num, float x, float y) {
  if(get_layer_is_correct()) {
    get_layer().text(num,x,y);
  } else {
    g.text(num,x,y);
  }
}


public void text(int num, float x, float y, float z) {
  if(get_layer_is_correct()) {
    get_layer().text(num,x,y,z);
  } else {
    g.text(num,x,y,z);
  }
}


// text Align
public void textAlign(int alignX) {
  if(get_layer_is_correct()) {
    get_layer().textAlign(alignX);
  } else {
    g.textAlign(alignX);
  }
}


public void textAlign(int alignX, int alignY) {
  if(get_layer_is_correct()) {
    get_layer().textAlign(alignX,alignY);
  } else {
    g.textAlign(alignX,alignY);
  }
}

// textLeading(leading)
public void textLeading(float leading) {
if(get_layer_is_correct()) {
    get_layer().textLeading(leading);
  } else {
    g.textLeading(leading);
  }
}


// textMode(mode)
public void textMode(int mode) {
  if(get_layer_is_correct()) {
    get_layer().textMode(mode);
  } else {
    g.textMode(mode);
  }
}

// text Size
public void textSize(float size) {
  if(get_layer_is_correct()) {
    get_layer().textSize(size);
  } else {
    g.textSize(size);
  }
}


// textFont
public void textFont(PFont font) {
  if(font != null) {
    if(get_layer_is_correct()) {
      get_layer().textFont(font);
    } else {
      g.textFont(font);
    }
  }
}

public void textFont(PFont font, float size) {
  if(get_layer_is_correct()) {
    if(get_layer() != null) {
      get_layer().textFont(font,size);
    } else {
      g.textFont(font,size);
    }
  }
}






  
/**
* ROPE SVG
* v 1.5.2
* Copyleft (c) 2014-2019
* @author @stanlepunk
* @see https://github.com/StanLepunK/Rope_framework
*/

class ROPE_svg {
  PApplet p5  ;
  //
  PShape shape_SVG ;
  String path = "" ;
  String folder_brick_name = "brick" ;
  ArrayList<Brick_SVG> list_brick_SVG = new ArrayList<Brick_SVG>() ;
  String name = "" ;
  String header_svg = "" ;
  int ID_brick ;
  private String saved_path_bricks_svg = "" ;

  private boolean position_center = false ;
  
  private boolean bool_scale_translation ; 
  private boolean bool_pos, bool_jitter_svg, bool_scale_svg ;
  private boolean keep_change ;

  private boolean display_fill_original = true ;
  private boolean display_stroke_original = true ;
  private boolean display_thickness_original = true ;

  private boolean display_fill_custom = false ;
  private boolean display_stroke_custom = false ;
  private boolean display_thickness_custom = false ;

  private vec3 pos = vec3() ;
  private vec3 jitter_svg = vec3() ;
  private vec3 scale_svg = vec3() ;

  // Aspect default
  private String [] st ;
  private vec4 fill_custom = vec4(0,0,0,g.colorModeA) ;
  private vec4 stroke_custom = vec4(g.colorModeX,g.colorModeY,g.colorModeZ,g.colorModeA) ;
  private float thickness_custom = 1 ;

  private vec4 fill_factor = vec4(1) ;
  private vec4 stroke_factor = vec4(1) ;






  /**  
  CONSTRUCTOR

  */
  ROPE_svg (PApplet p5, String path, String folder_brick_name) {
    this.p5 = p5 ;
    this.name = file_name(path) ;
    this.folder_brick_name = folder_brick_name ;
    this.path = path ;
    saved_path_bricks_svg = "RPE_SVG/" + folder_brick_name + "/" ;
  }

  ROPE_svg (PApplet p5, String path) {
    this.p5 = p5 ;
    this.name = file_name(path) ;
    this.path = path ;
    saved_path_bricks_svg = "RPE_SVG/" + folder_brick_name + "/" ;
  }



  

  
  








  /**
  PUBLIC METHOD

  */
  public void build(String path_import, String path_brick) {
    list_brick_SVG.clear() ;
    list_ellipse_SVG.clear() ;
    list_rectangle_SVG.clear() ;
    list_vertice_SVG.clear() ;

    shape_SVG = loadShape(path_import) ;

    XML svg_info = loadXML(path_import) ;
    analyze_SVG(svg_info) ;
    save_brick_SVG() ;
    build_SVG(list_brick_SVG, path_brick) ;

  }

  public void build() {
    build(path, saved_path_bricks_svg) ;
  } 






  
  /**
  METHOD to draw all the SVG
  */
  public void draw() {
    reset() ;
    draw_SVG (pos, scale_svg, jitter_svg) ;
    change_boolean_to_false() ;
  }
  
  public void draw(int ID) {
    reset() ;
    draw_SVG (pos, scale_svg, jitter_svg, ID) ;
    change_boolean_to_false() ;
  }


  public void draw(String layer_or_group_name) {
    reset() ;
    vec3 new_pos = pos.copy() ;
    if(bool_scale_translation) {
      push() ;
      vec3 translation = vec3() ;
      translation = scale_translation(scale_svg, layer_or_group_name); 
      translate(translation) ;
    }
    draw_SVG (pos, scale_svg, jitter_svg, layer_or_group_name) ;

    if(bool_scale_translation) pop() ;

    change_boolean_to_false() ;
  }


  private vec3 scale_translation(vec3 scale_svg, String layer_name) {
    vec3 translation = vec3() ;

    int num = 0 ;
    vec3 correction = vec3() ;
    for(int i = 0 ; i < list_brick_SVG.size() ; i++) {
      Brick_SVG b = (Brick_SVG) list_brick_SVG.get(i) ;
      if( b.family_name.contains(layer_name)) {
        // catch position before the scaling
        for(int k = 0 ; k < list_svg_vec(b.ID).length ; k++) {
          num++ ;
          // special translate for the shape kind rect, because this one move from the corner
          if(b.kind == "rect") {
             float width_rect = b.xml_brick.getChild(0).getFloat("width") ;
             float height_rect = b.xml_brick.getChild(0).getFloat("height") ;
             correction.set(width_rect *.5f, height_rect *.5f, 0) ;
           }
        }
      }
    }

    vec3 [] list_raw = new vec3[num] ;
    for(int i = 0 ; i < list_brick_SVG.size() ; i++) {
      Brick_SVG b = (Brick_SVG) list_brick_SVG.get(i) ;
      if( b.family_name.contains(layer_name)) {
        // catch position before the scaling
        for(int k = 0 ; k < list_svg_vec(b.ID).length ; k++) {
          list_raw[k] = list_svg_vec(b.ID)[k].copy()  ;
        }
      }
    }

    // result
    vec3 barycenter = barycenter(list_raw) ;
    if(position_center) {
      vec3 center_pos = vec3(canvas().x,canvas().y, 0) ;
      center_pos.mult(.5f) ;
      barycenter.sub(center_pos) ;
    }
    if(!correction.equals(0)) barycenter.add(correction);
    vec3 barycenter_translated = mult(barycenter, scale_svg) ;
    translation = sub(barycenter, barycenter_translated) ;

    return translation ;
  }


  
  /**
  TEMPORARY CHANGE
  This change don't modify the original coord of point
  */

 /**
 POS
 */
  public void pos(float x) {
    bool_pos = true ;
    pos.set(x) ;
  }

  public void pos(float x, float y) {
    bool_pos = true ;
    pos.set(x,y,0) ;
  }

  public void pos(float x, float y, float z) {
    bool_pos = true ;
    pos.set(x,y,z) ;
  }

  public void pos(vec pos_raw) {
    bool_pos = true ;
    if(pos_raw instanceof vec2) {
      vec2 pos_2D = (vec2) pos_raw ;
      pos.set(pos_2D.x, pos_2D.y, 0) ;
    } else if (pos_raw instanceof vec3) {
      vec3 pos_3D = (vec3) pos_raw ;
      pos.set(pos_3D) ;
    }
  }

  /**
  SCALE
  */
  // scale_translation
  private void scaling(float x) {
    scaling(false, vec3(x,x,0)) ;
  }

  private void scaling(float x, float y) {
    scaling(false, vec3(x,y,0)) ;
  }

  private void scaling(float x, float y, float z) {
    scaling(false, vec3(x,y,z)) ;
  }

  private void scaling(vec scale_raw) {
    scaling(false, scale_raw) ;
  }

  private void scaling(boolean translation, float x) {
    scaling(translation, vec3(x,x,0)) ;
  }

  private void scaling(boolean translation, float x, float y) {
    scaling(translation, vec3(x,y,0)) ;
  }

  private void scaling(boolean translation, float x, float y, float z) {
    scaling(translation, vec3(x,y,z)) ;
  }

  private void scaling(boolean translation, vec scale_raw) {
    bool_scale_translation = translation ;
    bool_scale_svg = true ;
    if(scale_raw instanceof vec2) {
      vec2 scale = (vec2) scale_raw ;
      scale_svg.set(scale.x, scale.y, 1) ;
    } else if (scale_raw instanceof vec3) {
      vec3 scale = (vec3) scale_raw ;
      scale_svg.set(scale) ;
    }
  }



  /**
  JITEER 
  */
  public void jitter(float x) {
    bool_jitter_svg = true ;
    jitter_svg.set(x) ;
  }

  public void jitter(int x, int y) {
    bool_jitter_svg = true ;
    jitter_svg.set(x,y,0) ;
  }

  public void jitter(int x, int y, int z) {
    bool_jitter_svg = true ;
    jitter_svg.set(x,y,z) ;
  }

  public void jitter(vec jitter_raw) {
    bool_jitter_svg = true ;
    if(jitter_raw instanceof vec2) {
      vec2 jitter = (vec2) jitter_raw ;
      jitter_svg.set(jitter.x, jitter.y, 0) ;
    } else if (jitter_raw instanceof vec3) {
      vec3 jitter = (vec3) jitter_raw ;
      jitter_svg.set(jitter) ;
    }
  }

  
  
  
  /* 
  method start() & end() use in correlation with reset for the change like jitter, pos, scale...
  when the svg is using in split mode with name or ID param
  */
  public void start() {
    keep_change = true ;
  }
  public void stop() {
    keep_change = false ;
    reset() ;
  }


  /**
  ASPECT
  v 0.2.0
  */
  /**
  opacity
  */
  public void opacity(float a_fill, float a_stroke) {
    display_stroke_original = true ;
    display_stroke_custom = false ;
    display_fill_original = true ;
    display_fill_custom = false ;
    float normalize_alpha_fill = (a_fill / g.colorModeA) ;
    float normalize_alpha_stroke = (a_stroke / g.colorModeA) ;
    this.fill_factor(1, 1, 1, normalize_alpha_fill) ;
    this.stroke_factor(1, 1, 1, normalize_alpha_stroke) ;

  }

  public void opacityStroke(float a) {
    display_stroke_original = true ;
    display_stroke_custom = false ;
    float normalize_alpha = (a / g.colorModeA) ;
    this.stroke_factor(1, 1, 1, normalize_alpha) ;
  }
    public void opacityFill(float a) {
    display_fill_original = true ;
    display_fill_custom = false ;
    float normalize_alpha = (a / g.colorModeA) ;
    this.fill_factor(1, 1, 1, normalize_alpha) ;
  }
  
  /**
  fill
  */

  public void noFill() {
    display_fill_original = false ;
    display_fill_custom = true ;
    fill_custom.set(0) ;
  }
  
  public void fill(int c) {
    display_fill_original = false ;
    display_fill_custom = true;
    if(g.colorMode == 1 ) fill_custom.set(red(c),green(c),blue(c),alpha(c));
    else if(g.colorMode == 3) fill_custom.set(hue(c),saturation(c),brightness(c),alpha(c));
  }

  public void fill(float value) {
    display_fill_original = false ;
    display_fill_custom = true ;
    fill_custom.set(value,value,value, g.colorModeA) ;
  }

  public void fill(float value, float alpha) {
    display_fill_original = false ;
    display_fill_custom = true ;
    fill_custom.set(value,value,value, alpha) ;
  }

  public void fill(float x, float y, float z) {
    display_fill_original = false ;
    display_fill_custom = true ;
    fill_custom.set(x, y, z, g.colorModeA) ;
  }
  
  public void fill(float x, float y, float z, float a) {
    display_fill_original = false ;
    display_fill_custom = true ;
    fill_custom.set(x,y,z,a) ;
  }

  public void fill(vec v) {
    display_fill_original = false ;
    display_fill_custom = true ;
    if(v instanceof vec2) {
      vec2 v2 = (vec2) v ;
      fill_custom.set(v2.x, v2.x, v2.x, v2.y) ;
    } else if(v instanceof vec3) {
      vec3 v3 = (vec3) v ;
      fill_custom.set(v3.x,v3.y,v3.z, g.colorModeA) ;
    } else if(v instanceof vec3 ) {
      vec4 v4 = (vec4) v ;
      fill_custom.set(v4.x, v4.y, v4.z, v4.w) ;
    }
  }
  /**
  stroke
  */
  public void noStroke() {
    display_stroke_original = false ;
    display_stroke_custom = true ;
    thickness_custom = 0 ;
    stroke_custom.set(0) ;
  }
  
  public void stroke(int c) {
    display_stroke_original = false ;
    display_stroke_custom = true;
    if(g.colorMode == 1 ) stroke_custom.set(red(c),green(c),blue(c),alpha(c));
    else if(g.colorMode == 3) stroke_custom.set(hue(c),saturation(c),brightness(c),alpha(c));
  }

  public void stroke(float value) {
    display_stroke_original = false ;
    display_stroke_custom = true ;
    stroke_custom.set(value, value, value, g.colorModeA) ;
  }

  public void stroke(float value, float a) {
    display_stroke_original = false ;
    display_stroke_custom = true ;
    stroke_custom.set(value, value, value, a) ;
  }

  public void stroke(float x, float y, float z) {
    display_stroke_original = false ;
    display_stroke_custom = true ;
    stroke_custom.set(x, y, z, g.colorModeA) ;
  }

  public void stroke(float x, float y, float z, float a) {
    display_stroke_original = false ;
    display_stroke_custom = true ;
    stroke_custom.set(x,y,z,a) ;
  }


  public void stroke(vec v) {
    display_stroke_original = false ;
    display_stroke_custom = true ;
    if(v instanceof vec2) {
      vec2 v2 = (vec2) v ;
      stroke_custom.set(v2.x, v2.x, v2.x, v2.y) ;
    } else if(v instanceof vec3) {
      vec3 v3 = (vec3) v ;
      stroke_custom.set(v3.x,v3.y,v3.z, g.colorModeA) ;
    } else if(v instanceof vec3 ) {
      vec4 v4 = (vec4) v ;
      stroke_custom.set(v4.x, v4.y, v4.z, v4.w) ;
    }
  }
  /**
  strokeWeight
  */
  public void strokeWeight(float x) {
    display_thickness_original = false ;
    display_thickness_custom = true ;
    thickness_custom = x ;
  }


  /**
  original style
  */
  public void original_style(boolean fill, boolean stroke) {
    display_fill_original = fill ;
    display_stroke_original = stroke ;
    display_thickness_original = stroke ;
  }

  public void original_fill(boolean fill) {
    display_fill_original = fill ;
  }

  public void original_stroke(boolean stroke) {
    display_stroke_original = stroke ;
    display_thickness_original = stroke ;
  }
  

  /**
  fill factor
  use value from '0' to '1' is better !
  */
  public void fill_factor(float x, float y, float z, float a) {
    fill_factor.set(x,y,z,a) ;
  }

  public void stroke_factor(float x, float y, float z, float a) {
    stroke_factor.set(x,y,z,a) ;
  }

  public void fill_factor(vec4 f) {
    fill_factor.set(f.x,f.y,f.z,f.w) ;
  }

  public void stroke_factor(vec4 f) {
    stroke_factor.set(f.x,f.y,f.z,f.w) ;
  }

  /**
  PERMANENTE CHANGE
  This change modify the original points
  */
  public void add_def(int target, vec3... value) {
    if(list_brick_SVG.size() > 0) {
      Brick_SVG b = (Brick_SVG) list_brick_SVG.get(target) ;
      if(b.kind == "polygon" || b.kind == "path" || b.kind == "polyline") {
        for(Vertices v : list_vertice_SVG) {
          if(v.ID == b.get_id()) v.add_value(value) ;
        }
      } else if(b.kind == "line") {
        for(Line l : list_line_SVG) {
          if(l.ID == b.get_id()) l.add_value(value) ;
        }
      } else if(b.kind == "circle" || b.kind == "ellipse") {
        for(Ellipse e : list_ellipse_SVG) {
          if(e.ID == b.get_id()) e.add_value(value) ;
        }
      } else if(b.kind == "rect") {
        for(Rectangle r : list_rectangle_SVG) {
          if(r.ID == b.get_id()) r.add_value(value) ;
        }
      } else if(b.kind == "text") {
        for(ROPEText t : list_text_SVG) {
          if(t.get_id() == b.get_id()) t.add_value(value) ;
        }
      } 
    } 
  }
  

  /**
  SVG info
  */

  
  /**
   return quantity of brick 
  */
  public int num_brick() {
    return list_brick_SVG.size() ;
  }
  

  /**
  list
  */
  public vec3 [] list_svg_vec(int target) {
    vec3 [] p = new vec3[1] ;
    p[0] = vec3(2147483647,2147483647,2147483647) ; // it's maximum value of int in 8 bit :)

    if(list_brick_SVG.size() > 0) {
      Brick_SVG b = (Brick_SVG) list_brick_SVG.get(target) ;
      if(b.kind == "polygon" || b.kind == "path" || b.kind == "polyline") {
        for(Vertices v : list_vertice_SVG) {
          if(v.ID == b.ID) return v.vertices() ;
        }
      } else if(b.kind == "line") {
        for(Line l : list_line_SVG) {
          if(l.ID == b.ID) {
            p[0] = l.pos_a ;
            p[1] = l.pos_b ;
            return p ;
          }
        }
      } else if(b.kind == "circle" || b.kind == "ellipse") {
        for(Ellipse e : list_ellipse_SVG) {
          if(e.ID == b.ID) {
            p[0] = e.pos ;
            return p ;
          }
        }
      } else if(b.kind == "rect") {
        for(Rectangle r : list_rectangle_SVG) {
          if(r.ID == b.ID) { 
            p[0] = r.pos ;
            return p ;
          }
        }
      } 
    } else return p ;
    return p ;
  }


  public PVector [] list_svg_PVector(int target) {
    PVector [] p  ;
    vec3 [] temp_list ;
    temp_list = list_svg_vec(target).clone() ;
    int num = temp_list.length ;
    p = new PVector[num] ;
    for(int i = 0 ; i < num ; i++) {
      p[i] = new PVector(temp_list[i].x, temp_list[i].y, temp_list[i].z) ;
    }
    if (p != null) return p ; else return null ;
  }





  /**
  method to return different definition about the brick
  */

  public String folder_brick_name() {
    return folder_brick_name ;
  }


  public String [] brick_name_list() {
    return name_brick_SVG(list_brick_SVG) ;
  }

  public String brick_name(int target) {
    if(list_brick_SVG.size() > 0 && target < list_brick_SVG.size()) {
      Brick_SVG b = (Brick_SVG) list_brick_SVG.get(target) ;
      return b.brick_name ;

    } else return "No idea for this ID !" ;
  }

  public String [] family_brick() {
    return family_brick_SVG(list_brick_SVG) ;
  }

  public String family_brick(int target) {
    if(list_brick_SVG.size() > 0 && target < list_brick_SVG.size()) {
      Brick_SVG b = (Brick_SVG) list_brick_SVG.get(target) ;
      return b.family_name ;

    } else return "No idea for this ID !" ;
  }

  public String [] kind_brick() {
    return kind_brick_SVG(list_brick_SVG) ;
  }
  public String kind_brick(int target) {
    if(list_brick_SVG.size() > 0 && target < list_brick_SVG.size()) {
      Brick_SVG b = (Brick_SVG) list_brick_SVG.get(target) ;
      return b.kind ;

    } else return "No idea for this ID !" ;
  }





  
  




  /**
  Canvas SVG
  */
  public float width() {
    if(shape_SVG != null) {
      return shape_SVG.width ; 
    } else {
      return 0 ;
    }
  }
  public float height() {
    if(shape_SVG != null) { 
      return shape_SVG.height ; 
    } else {
      return 0 ;
    }
  }
  
  public vec2 canvas() {
    if(shape_SVG != null) {
      return vec2(shape_SVG.width, shape_SVG.height) ; 
    } else { 
      return vec2() ;
    }
  }
  
  
  
  
  
  /**
  Canvas brick SVG
  */
  public float width_brick(int target) {
    if(list_brick_SVG.size() > 0 && target <list_brick_SVG.size()) {
      Brick_SVG b_svg = list_brick_SVG.get(target) ;
      return b_svg.width ; 
    } else return 0 ;
  }

  public float height_brick(int target) {
    if(list_brick_SVG.size() > 0 && target <list_brick_SVG.size()) {
      Brick_SVG b_svg = list_brick_SVG.get(target) ;
      return b_svg.height ; 
    } else return 0 ;
  }
  
  public vec2 canvas_brick(int target) {
    if(list_brick_SVG.size() > 0 && target <list_brick_SVG.size()) {
      Brick_SVG b_svg = list_brick_SVG.get(target) ;
      return vec2(b_svg.width, b_svg.height) ;
    } else return vec2() ;
  }










 /**
  SETTING
  */
  public void mode(int mode) {
    // for info CORNER = 0 / CENTER = 3 > Global variable from Processing
    if(mode == 0 ) position_center = false ;
    else if(mode == 3 ) position_center = true ;
    else position_center = false ;
  }
  /**
  // END PUBLIC METHOD


  */
  







  
  
  
  
  
  






  
  
  
  
  
  
  
  
  /**
  PRIVATE METHOD


  */

  /**
  DRAW

  */
  // reset all change to something flat and borring !
  public void reset() {
    if(!keep_change) {
      if(!bool_pos) {
        pos.set(0) ;
      }
      if(!bool_jitter_svg) {
        jitter_svg.set(0) ;
      }
      if(!bool_scale_svg) {
        scale_svg.set(1) ;
      }
    } else {
      original_style(true, true) ;
      fill_factor(1,1,1,1) ;
      stroke_factor(1,1,1,1) ;
    }
  }
  


  private void change_boolean_to_false() {
    bool_pos = false ;
    bool_scale_svg = false ;
    bool_jitter_svg = false ;
    bool_scale_translation = false ;
  }
  /**
  Draw all shape
  */
  // INTERN METHOD 2D
  private void draw_SVG(vec pos_, vec scale_, vec jitter_) {
    for(int i = 0 ; i < list_brick_SVG.size() ; i++) {
      Brick_SVG b = (Brick_SVG) list_brick_SVG.get(i) ;
      draw_final(pos_, scale_, jitter_, b) ;
    }
  }
 
  
  /**
  Draw shape by ID
  */
  // 2D
  private void draw_SVG (vec pos_, vec scale_, vec jitter_, int ID) {
    if(ID < list_brick_SVG.size()) {
      Brick_SVG b = (Brick_SVG) list_brick_SVG.get(ID) ;
      draw_final(pos_, scale_, jitter_, b) ;
    }
  }
   
  
  /**
  Draw shape by name
  */
  // draw all file from shape or group of layer
  // must be factoring is very ligth method :)
  private void draw_SVG (vec pos_, vec scale_, vec jitter_, String layer_name) {
    for(int i = 0 ; i < list_brick_SVG.size() ; i++) {
      Brick_SVG b = (Brick_SVG) list_brick_SVG.get(i) ;
      if( b.family_name.contains(layer_name)) {
        draw_final(pos_, scale_, jitter_, b) ;
      }
    }
  }

  private void draw_final(vec pos_, vec scale_, vec jitter_, Brick_SVG b) {
    if(b.font != null) textFont(b.font) ;
    if(b.size_font != MAX_INT) textSize(b.size_font) ;

    float average_scale = (scale_.x + scale_.y) *.5f ;
    aspect(b, average_scale) ;
    display_shape(b, pos_, scale_, jitter_) ;

  }
  /**
  END DRAW METHOD

  */






  /**
  ASPECT
  */
  private void aspect(Brick_SVG b, float scale_thickness) {
    aspect_original(b, scale_thickness) ;
    aspect_custom() ;
  }

  // super local
  private  void aspect_original(Brick_SVG b, float scale_thickness) {
    if(display_fill_original) {
      b.aspect_fill(fill_factor) ; 
    } else {
      p5.noFill() ;
    }
    if(display_stroke_original && display_thickness_original) {
      b.aspect_stroke(scale_thickness,stroke_factor) ; 
    } else { 
      p5.noStroke() ;
    }
  }

  private  void aspect_custom() {
    if(fill_custom.alp() > 0 && display_fill_custom && !display_fill_original) {
      p5.fill(fill_custom.red() *fill_factor.x, fill_custom.gre() *fill_factor.y, fill_custom.blu() *fill_factor.y, fill_custom.alp() *fill_factor.w) ; 
    }
    if(display_stroke_custom && !display_stroke_original) {
      if(stroke_custom.alp() > 0 || thickness_custom > 0 ) {
        p5.stroke(stroke_custom.red() *stroke_factor.x, stroke_custom.gre() *stroke_factor.y, stroke_custom.blu() *stroke_factor.z, stroke_custom.alp() *stroke_factor.w) ;
        p5.strokeWeight(thickness_custom) ;
      }
    }
    
    if(!display_fill_original && !display_fill_custom) {
      p5.noFill() ;
    }
    if(!display_stroke_original && !display_stroke_custom) {
      p5.noStroke() ;
    }
  }



  
  


































  /**
  BUILD

  */
  /**
  Main display method

  */
  private void display_shape(Brick_SVG b, vec pos_raw, vec scale_raw, vec jitter_raw) {
    if(pos_raw instanceof vec2 && scale_raw instanceof vec2 && jitter_raw instanceof vec2) {
      vec2 pos_temp = (vec2) pos_raw ;
      vec2 scale_temp = (vec2) scale_raw ;
      vec2 jitter_temp = (vec2) jitter_raw ;

      vec3 pos = vec3(pos_temp) ;
      vec3 scale = vec3(scale_temp) ;
      vec3 jitter = vec3(jitter_temp) ;
      display_shape_3D(b, pos, scale, jitter) ;
    } else if (pos_raw instanceof vec3 && scale_raw instanceof vec3 && jitter_raw instanceof vec3) {
      vec3 pos = (vec3) pos_raw ;
      vec3 scale = (vec3) scale_raw ;
      vec3 jitter = (vec3) jitter_raw ;
      display_shape_3D(b, pos, scale, jitter) ;
    }
  }

  private void display_shape_3D(Brick_SVG b, vec3 pos, vec3 scale, vec3 jitter) {
    if(b.kind == "path" || b.kind == "polygon" || b.kind == "polyline") {
      for(Vertices v : list_vertice_SVG) {
        if(v.ID == b.ID) build_path(pos, scale, jitter, v) ;
      }
    } else if(b.kind == "line") {
      for(Line l : list_line_SVG) {
        if(l.ID == b.ID) {
          build_line(pos, scale, jitter, l) ;
        }
      }
    } else if(b.kind == "ellipse" || b.kind == "circle") {
      for(Ellipse e : list_ellipse_SVG) {
        if(e.ID == b.ID) {
          build_ellipse(pos, scale, jitter, e) ;
        }
      }
    } else if(b.kind == "rect") {
      for(Rectangle r : list_rectangle_SVG) {
        if(r.ID == b.ID) {
          build_rectangle(pos, scale, jitter, r) ;
        }
      }
    } else if(b.kind == "text") {
      for(ROPEText t : list_text_SVG) {
        if(t.ID == b.ID) {
          build_text(pos, scale, jitter, t) ;
        }
      }
    }
  }




  // Build SVG brick
  private void build_SVG(ArrayList<Brick_SVG> list, String path_brick) {
    PShape [] children = new PShape[list.size()] ;
    for(int i = 0 ; i < list.size() ; i++) {
      PShape mother = loadShape(path_brick + folder_brick_name + "_" + i + ".svg") ;
      children = mother.getChildren() ;
      // Problem here with P3D and P2D mode
      // return null pointer exception with type 'text'
      
      Brick_SVG b = (Brick_SVG) list.get(i) ;
      if(b.kind == "polygon" || b.kind == "path" || b.kind == "polyline")  vertex_count(children[0], mother.getName(), b.ID) ;
      if(b.kind == "line")  line_count(b.xml_brick, mother.getName(), b.ID) ;
      else if(b.kind == "circle" || b.kind == "ellipse") ellipse_count(b.xml_brick, b.ID) ;
      else if(b.kind == "rect") rectangle_count(b.xml_brick, b.ID) ;
      else if(b.kind == "text") text_count(b.xml_brick,  b.ID) ;
    }
  }
  

  // TEXT
  // list of group SVG
  ArrayList<ROPEText> list_text_SVG = new ArrayList<ROPEText>() ;
      
  //
  private void text_count(XML xml_shape, int ID) {
    vec6 matrix = matrix_vec(xml_shape) ;
    String sentence = xml_shape.getChild("text").getContent() ;

    ROPEText t = new ROPEText(matrix, sentence, ID) ;
    list_text_SVG.add(t) ;
  }



  

  /**
  Main method to draw text
  */
  public void build_text(vec3 pos, vec3 scale, vec3 jitter, ROPEText t) {
    vec3 temp_pos = vec3(t.pos.x, t.pos.y,0)   ;
    // the order of operation is very weird, because is not a same for the build_vertex()
    if(position_center) {
      vec3 center_pos = vec3(canvas().x,canvas().y, 0) ;
      center_pos.mult(.5f) ; 
      temp_pos.sub(center_pos) ;
    }
    if(!scale.equals(vec3(1))) {
      temp_pos.mult(scale) ; 
    }
    if(!pos.equals(vec3())) {
      temp_pos.add(pos) ;
    }

    
    if(check_matrix(t.matrix)) {
      push() ;
      matrix(t.matrix, temp_pos) ;
      text(t.sentence, 0,0) ;
      pop() ;
    } else {
      // if there is no matrix effect
      text(t.sentence, temp_pos) ;
    }
  }
  /**
  END CIRCLE & ELLIPSE
  */













  /**
  Line
  */
  // list of group SVG
  ArrayList<Line> list_line_SVG = new ArrayList<Line>() ;

  private void line_count(XML xml_shape, String geom_name, int ID) {
    float x_a = xml_shape.getChild(0).getFloat("x1") ;
    float y_a = xml_shape.getChild(0).getFloat("y1") ;
    float x_b = xml_shape.getChild(0).getFloat("x2") ;
    float y_b = xml_shape.getChild(0).getFloat("y2") ;
  
    Line l = new Line(x_a, y_a, x_b, y_b, ID) ;
    list_line_SVG.add(l) ;
  }
  

  /**
  Main method to draw ellipse
  */
  public void build_line(vec3 pos, vec3 scale, vec3 jitter, Line l) {
    vec3 temp_pos_a = vec3(l.pos_a.x, l.pos_a.y,0)  ;
    vec3 temp_pos_b = vec3(l.pos_b.x, l.pos_b.y,0)  ;

  
    // the order of operation is very weird, because is not a same for the build_vertex()
    if(position_center) {
      vec3 center_pos = vec3(canvas().x,canvas().y, 0) ;
      center_pos.mult(.5f) ; 
      temp_pos_a.sub(center_pos) ;
      temp_pos_b.sub(center_pos) ;
    }
    if(!scale.equals(vec3(1))) {
      temp_pos_a.mult(scale) ; 
      temp_pos_b.mult(scale) ; 
    }
    if(!pos.equals(vec3())) {
      temp_pos_a.add(pos) ;
      temp_pos_b.add(pos) ;
    }
  
    line(temp_pos_a.mult(scale), temp_pos_b.mult(scale)) ;
  }
  /**
  END CIRCLE & ELLIPSE
  */







  /**
  ELLIPSE & CIRCLE
  */
  // list of group SVG
  ArrayList<Ellipse> list_ellipse_SVG = new ArrayList<Ellipse>() ;

  private void ellipse_count(XML xml_shape, int ID) {
    vec6 matrix = matrix_vec(xml_shape) ;
    float r = xml_shape.getChild(0).getFloat("r") ;
    float rx = (float)xml_shape.getChild(0).getFloat("rx") ;
    float ry = (float)xml_shape.getChild(0).getFloat("ry") ;
    float cx = xml_shape.getChild(0).getFloat("cx") ;
    float cy = xml_shape.getChild(0).getFloat("cy") ;
    if(r > 0 ) rx = ry = r ;
  
    Ellipse e = new Ellipse(matrix, cx, cy, rx, ry, ID) ;
    list_ellipse_SVG.add(e) ;
  }
  

  /**
  Main method to draw ellipse
  */
  public void build_ellipse(vec3 pos, vec3 scale, vec3 jitter, Ellipse e) {
    vec3 temp_pos = vec3(e.pos.x, e.pos.y,0)  ;

  
    // the order of operation is very weird, because is not a same for the build_vertex()
    if(position_center) {
      vec3 center_pos = vec3(canvas().x,canvas().y, 0) ;
      center_pos.mult(.5f) ; 
      temp_pos.sub(center_pos) ;
    }
    if(!scale.equals(vec3(1))) temp_pos.mult(scale) ; 
    if(!pos.equals(vec3())) temp_pos.add(pos) ;
  
    vec2 temp_size = e.size.copy() ;

    if(check_matrix(e.matrix)) {
      push() ;
      matrix(e.matrix, temp_pos) ;
      ellipse(vec2(0), temp_size.mult(scale.x, scale.y)) ;
      pop() ;
    } else {
      // if there is no matrix effect
      ellipse(temp_pos, temp_size.mult(scale.x, scale.y)) ;
    }

    
  }
  /**
  END CIRCLE & ELLIPSE
  */
  



  /**
  RECTANGLE
  */
  // list of group SVG
  private ArrayList<Rectangle> list_rectangle_SVG = new ArrayList<Rectangle>() ;
  
  private void rectangle_count(XML xml_shape, int ID) {
    vec6 matrix = matrix_vec(xml_shape) ;
    float x = xml_shape.getChild(0).getFloat("x") ;
    float y = xml_shape.getChild(0).getFloat("y") ;
    float width_rect = xml_shape.getChild(0).getFloat("width") ;
    float height_rect = xml_shape.getChild(0).getFloat("height") ;
  
    Rectangle r = new Rectangle(matrix, x, y, width_rect, height_rect, ID) ;
    list_rectangle_SVG.add(r) ;
  }
  
  /**
  Main method to draw ellipse
  */
  
  private void build_rectangle(vec3 pos, vec3 scale, vec3 jitter, Rectangle r) {
    vec3 temp_pos = vec3(r.pos.x, r.pos.y,0)  ;
  
    // the order of operation is very weird, because is not a same for the build_vertex()
    if(position_center) {
      vec3 center_pos = vec3(canvas().x,canvas().y, 0) ;
      center_pos.mult(.5f) ; 
      temp_pos.sub(center_pos) ;
    }
    if(!scale.equals(vec3(1))) temp_pos.mult(scale) ; 
    if(!pos.equals(vec3())) temp_pos.add(pos) ;
  
    vec2 temp_size = r.size.copy() ;

    if(check_matrix(r.matrix)) {
      push() ;
      matrix(r.matrix, temp_pos) ;
      vec2 pos_def = vec2() ;
      // pos_def.x += (temp_size.x *.5) ;
      // pos_def.y += (temp_size.y *.5) ;
      // pos_def.x += mouseX ;
      // pos_def.y += mouseY ;
      rect(pos_def, temp_size) ;
      pop() ;
    } else {
      // if there is no matrix effect
      rect(temp_pos, temp_size.mult(scale.x, scale.y)) ;
    }
  }
  /**
  END RECTANGLE
  */
  

  











  /**
  VERTEX


  */
  /**
  Build
  */
  // list of group SVG
  private ArrayList<Vertices> list_vertice_SVG = new ArrayList<Vertices>() ;
  // here we must build few object for each group, but how ?
  private vec3 [] vert ;
  private int [] vertex_code ;
  private int code_vertex_count ;
  
  private void vertex_count(PShape geom_shape, String geom_name, int ID) {
    int num = geom_shape.getVertexCount() ;
    vertex_code = new int[num] ;
    vert = new vec3[num] ;
    vertex_code = geom_shape.getVertexCodes() ;
    code_vertex_count = geom_shape.getVertexCodeCount() ;
    
    Vertices v = new Vertices(code_vertex_count, num, geom_shape, geom_name, ID) ;
    v.build_vertices_3D(geom_shape) ;
    list_vertice_SVG.add(v) ;
  }
  /**
  END VERTEX
  */
 
  




  /**
  Draw Vertice
  adapted from Processing PShape drawPath for the vertex
  https://github.com/processing/processing/blob/master/core/src/processing/core/PShape.java
  line 1700 and the dust !
  */


  private void build_path(vec3 pos, vec3 scale, vec3 jitter, Vertices v) {
    vec3 center_pos = vec3(canvas().x,canvas().y,0) ;
    center_pos.mult(.5f) ; 
    if(!scale.equals(vec3(1))) center_pos.mult(scale) ; 
  
    if (v.vert == null) return;
  
    boolean insideContour = false;
    beginShape();
    // for the simple vertex
    if (v.code_vertex_count == 0) {  
      for (int i = 0; i <  v.vert.length; i++) {
        vec3 temp_pos_a = v.vert[i].copy() ;
        //
        if(!scale.equals(vec3(1))) temp_pos_a.mult(scale) ;
        //
        if(!jitter.equals(vec3())) {
          vec3 jitter_pos = vec3().jitter((int)jitter.x,(int)jitter.y,(int)jitter.z) ;
          temp_pos_a.add(jitter_pos) ;
        }
        //
        if(position_center) temp_pos_a.sub(center_pos) ;
        //
        if(!pos.equals(vec3())) temp_pos_a.add(pos) ;
        //
        vertex(temp_pos_a);
      }
    // for the complex draw vertex, with bezier, curve...
    } else {  
      int index = 0;
      for (int j = 0; j < v.code_vertex_count; j++) {
        vec3 temp_pos_a , temp_pos_b, temp_pos_c ;
  
        switch (v.vertex_code[j]) {
          //----------
          case VERTEX:
          temp_pos_a = v.vert[index].copy() ;
          //
          if(!scale.equals(vec3(1))) temp_pos_a.mult(scale) ;
          //
          if(!jitter.equals(vec3())) {
            vec3 jitter_pos = vec3().jitter((int)jitter.x,(int)jitter.y,(int)jitter.z) ;
            temp_pos_a.add(jitter_pos) ;
          }
          //
          if(position_center) temp_pos_a.sub(center_pos) ;
          //
          if(!pos.equals(vec3())) temp_pos_a.add(pos) ;
          //
          vertex(temp_pos_a);
          index++;
          break;
        // QUADRATIC_VERTEX
          case QUADRATIC_VERTEX:
          temp_pos_a = v.vert[index].copy() ;
          temp_pos_b = v.vert[index +1].copy() ;
          //
          if(!scale.equals(vec3(1))) {
            temp_pos_a.mult(scale) ;
            temp_pos_b.mult(scale) ;
          }
          //
          if(!jitter.equals(vec3())) {
            vec3 jitter_pos = vec3().jitter((int)jitter.x,(int)jitter.y,(int)jitter.z) ;
            temp_pos_a.add(jitter_pos) ;
            jitter_pos = vec3().jitter((int)jitter.x,(int)jitter.y,(int)jitter.z) ;
            temp_pos_b.add(jitter_pos) ;
          }
          //
          if(position_center) {
            temp_pos_a.sub(center_pos) ;
            temp_pos_b.sub(center_pos) ;
          }
          //
          if(!pos.equals(vec3())) {
            temp_pos_a.add(pos) ;
            temp_pos_b.add(pos) ;
          }
          //
          quadraticVertex(temp_pos_a, temp_pos_b);
          index += 2;
          break;
          // BEZIER_VERTEX
          case BEZIER_VERTEX:
          temp_pos_a = v.vert[index].copy() ;
          temp_pos_b = v.vert[index +1].copy() ;
          temp_pos_c = v.vert[index +2].copy() ;
          //
          if(!scale.equals(vec3(1))) {
            temp_pos_a.mult(scale) ;
            temp_pos_b.mult(scale) ;
            temp_pos_c.mult(scale) ;
          }
          //
          if(!jitter.equals(vec3())) {
            vec3 jitter_pos = vec3().jitter((int)jitter.x,(int)jitter.y,(int)jitter.z) ;
            temp_pos_a.add(jitter_pos) ;
            jitter_pos = vec3().jitter((int)jitter.x,(int)jitter.y,(int)jitter.z) ;
            temp_pos_b.add(jitter_pos) ;
            jitter_pos = vec3().jitter((int)jitter.x,(int)jitter.y,(int)jitter.z) ;
            temp_pos_c.add(jitter_pos) ;
          }
          //
          if(position_center) {
            temp_pos_a.sub(center_pos) ;
            temp_pos_b.sub(center_pos) ;
            temp_pos_c.sub(center_pos) ;
          }
          //
          if(!pos.equals(vec3())) {
            temp_pos_a.add(pos) ;
            temp_pos_b.add(pos) ;
            temp_pos_c.add(pos) ;
          }
          //
          bezierVertex(temp_pos_a, temp_pos_b, temp_pos_c);
          index += 3;
          break;
          // CURVE_VERTEX
          case CURVE_VERTEX:
          temp_pos_a = v.vert[index].copy() ;
          //
          if(!scale.equals(vec3(1))) temp_pos_a.mult(scale) ;
          //
          if(!jitter.equals(vec3())) {
            vec3 jitter_pos = vec3().jitter((int)jitter.x,(int)jitter.y,(int)jitter.z) ;
            temp_pos_a.add(jitter_pos) ;
          }
          //
          if(position_center) temp_pos_a.sub(center_pos) ;
          //
          if(!pos.equals(vec3())) temp_pos_a.add(pos) ;
          //
          curveVertex(temp_pos_a);
          index++;
          break;
          // BREAK
          case BREAK:
          if (insideContour) {
            endContour();
          }
          beginContour();
          insideContour = true;
        }
      }
    }
    if (insideContour) {
      endContour();
    }
    // endShape(CLOSE);
    endShape();
  }
  /**
  END BUILD

  */
























  /** 
  MATRIX 
  transformation

  */

  private vec6 matrix_vec(XML xml_shape) {
    if(xml_shape.getChild(0).getString("transform") != null) {
      String matrix = xml_shape.getChild(0).getString("transform") ;
      if(matrix.startsWith("matrix(")) {
        matrix = matrix.substring(6) ;
      }
      if(matrix.endsWith(")")) {
        matrix = matrix.substring(1, matrix.length() -1) ;
      }
      String [] transform = split(matrix," ") ;
      /**
      more about matrix 3x3 > 6
      https://www.w3.org/TR/SVG/coords.html#TransformMatrixDefined
      */

      float a = Float.parseFloat(transform[0] );
      float b = Float.parseFloat(transform[1] );
      float c = Float.parseFloat(transform[2] );
      float d = Float.parseFloat(transform[3] );

      float e = Float.parseFloat(transform[4] );
      float f = Float.parseFloat(transform[5] );
      vec6 m = vec6(a,b,c,d,e,f) ;
      return m ;
    } else return null ;
  }




  private boolean check_matrix(vec6 matrix) {
    if(matrix != null) {
      float a = matrix.a();
      float b = matrix.b();
      float c = matrix.c();
      float d = matrix.d();
      if(a == 1 && b == 0 && c == 0 && d == 1) {
        return false ; 
      } else return true ;
    } else return false ;
  }



  private void matrix(vec6 matrix, vec3 pos) {
    float a = matrix.a();
    float b = matrix.b();
    float c = matrix.c();
    float d = matrix.d();
    // about matrix 
    // http://stackoverflow.com/questions/4361242/extract-rotation-scale-values-from-2d-transformation-matrix
    boolean matrix_bool = false ;
    boolean rotate_bool = false ;
    boolean scale_bool = false ;
    boolean skew_bool = false ;


    if(a == 1 && b == 0 && c == 0 && d == 1) {
      matrix_bool = false ;
      rotate_bool = false ;
      scale_bool = false ;
      skew_bool = false ;
    } else {
      // run matrix
      matrix_bool = true ;

      boolean rotate_case = false ;
      boolean scale_case = false ;
      boolean skew_case = false ;
      if(abs(a) == abs(d) && abs(c) == abs(b)) rotate_case = true ;
      if(a != 1 && b == 0 && c == 0 &&  abs(a) != abs(d)) scale_case = true ;
      if(a < 1 && b > -1 && c < 1 && d < 1) skew_case = true ;

      // rotate case
      if(rotate_case) {
        rotate_bool = true ;
        scale_bool = false ;
        skew_bool = false ;
      // scale case
      } else if(scale_case) {
        rotate_bool = false ;
        scale_bool = true ;
        skew_bool = false ;
      } else if ((a < -1 || a > 1) && (b < -1 || b > 1)) {
        scale_bool = true ;
        rotate_bool = true ;
        skew_bool = false ;
      }
      // skew case
      if(skew_case && !rotate_case && (a != 1 && b != 0 && c != 0 && d != 1) ) {
        skew_bool = true ;
      }
    }

    /**
    matrix case
    */
    if(matrix_bool) {
      float angle = atan(-matrix.b()/ matrix.a()) ; 

      // rotate
      if(rotate_bool && !scale_bool && !skew_bool) {
        translate(pos);
        if(d <= 0 ) {
          angle = angle +PI;
        }
        rotate(-angle);
      }

      // scale
      if(scale_bool && !rotate_bool && !skew_bool) {
        float sx = sqrt((a * a) + (c * c)) ;
        if(a < 0 || c < 0) {
          sx *= -1;
        }
        float sy = sqrt((b * b) + (d * d)) ;
        if(b < 0 || d < 0) {
          sy *= -1 ;
        }

        translate(pos.x,pos.y);
        scale(sx, sy);
      }

      // scale and rotate
      if(scale_bool && rotate_bool && !skew_bool) {
        // rotation
        if(d <= 0 ) {
          angle += PI;
        }
        // scale
        float sx_1 = a /cos(angle);
        float sx_2 = -c /sin(angle);
        float sy_1 = b /sin(angle);
        float sy_2 = d /cos(angle);
        
        translate(pos);
        rotate(-angle);
        scale(sx_1,sy_2);
      }
      
      // SKEW
      // skew / shear is rotate/scale and skew in same time 
      // skew take the lead on every thing, I believe but not sure
      
      // this alpgorithm is not really good, very approximative :(
      
      if(skew_bool && !rotate_bool && !scale_bool) {
        // calcule the angle for skew-scaling
        // scale
        float sx_1 = a /cos(angle);
        float sx_2 = -c /sin(angle);
        float sy_1 = b /sin(angle);
        float sy_2 = d /cos(angle);

        translate(pos);

        shearX(c);
        shearY(b);
        scale(sx_1,sy_2);
        
      }
    }
  }

  /**

  END MATRIX

  */
























  /**
  EXTRACT POINT
  
  */
  
  private void extract(Vertices v) {
    if (v.vert == null) return;
    if (v.code_vertex_count == 0) {  
      for (int i = 0; i <  v.vert.length; i++) {
        vec3 temp_pos_a = v.vert[i].copy() ;
        vertex(temp_pos_a);
      }
    // for the complex draw vertex, with bezier, curve...
    } else {  
      int index = 0;
      for (int j = 0; j < v.code_vertex_count; j++) {
        vec3 temp_pos_a , temp_pos_b, temp_pos_c ;
        switch (v.vertex_code[j]) {
          case VERTEX:
          temp_pos_a = v.vert[index].copy() ;
          vertex(temp_pos_a);
          index++;
          break;
          // QUADRATIC_VERTEX
          case QUADRATIC_VERTEX:
          temp_pos_a = v.vert[index].copy() ;
          temp_pos_b = v.vert[index +1].copy() ;
          //
          quadraticVertex(temp_pos_a, temp_pos_b);
          index += 2;
          break;
          // BEZIER_VERTEX
          case BEZIER_VERTEX:
          temp_pos_a = v.vert[index].copy() ;
          temp_pos_b = v.vert[index +1].copy() ;
          temp_pos_c = v.vert[index +2].copy() ;
          //
          bezierVertex(temp_pos_a, temp_pos_b, temp_pos_c);
          index += 3;
          break;
          // CURVE_VERTEX
          case CURVE_VERTEX:
          temp_pos_a = v.vert[index].copy() ;
          curveVertex(temp_pos_a);
          index++;
          break;
          // BREAK
          case BREAK:
        }
      }
    }
  }






  /**
  INFO

  */
  public String [] name_brick_SVG (ArrayList<Brick_SVG> list_brick) {
    String [] list ;
    if(list_brick.size() > 0 ) {
      list = new String[list_brick.size()] ;
      for (int i = 0 ; i < list.length ; i++) {
        Brick_SVG b_svg = list_brick.get(i) ;
        list[i] = b_svg.brick_name ;
      }
      return list ;
    } else {
      list = new String[1] ;
      list[0] = "no item in the list" ;
      return list ;
    }
  }
  
  public String [] kind_brick_SVG (ArrayList<Brick_SVG> list_brick) {
    String [] list ;
    if(list_brick.size() > 0 ) {
      list = new String[list_brick.size()] ;
      for (int i = 0 ; i < list.length ; i++) {
        Brick_SVG b_svg = list_brick.get(i) ;
        list[i] = b_svg.kind ;
      }
      return list ;
    } else {
      list = new String[1] ;
      list[0] = "no item in the list" ;
      return list ;
    }
  }
  
  public String [] family_brick_SVG (ArrayList<Brick_SVG> list_brick) {
    String [] list ;
    if(list_brick.size() > 0 ) {
      list = new String[list_brick.size()] ;
      for (int i = 0 ; i < list.length ; i++) {
        Brick_SVG b_svg = list_brick.get(i) ;
        list[i] = b_svg.family_name ;
      }
      return list ;
    } else {
      list = new String[1] ;
      list[0] = "no item in the list" ;
      return list ;
    }
  }
  /**
  END INFO

  */
  

  


















  
  
  /**
  ANALYZE

  */
  private void analyze_SVG(XML target) {
    // catch the header to rebuild a SVG as small as possible to use Processing build PShapeSVG of Processing engine
    header_svg = catch_header_SVG(target) ;


    ID_brick = 0 ;
    String primal_name =("") ;
    String primal_opacity = ("none") ;

    /**
    work in progress for ordering shape




    */
    
    /**



    */
    // style for SVG version 2
    XML [] svg_style = target.getChildren("style") ;

    if(svg_style.length > 0) {
      // new SVG 1.1 version 2
      build_array_style(svg_style[0]) ;
      deep_analyze_SVG(header_svg, true, target, primal_name, primal_opacity) ;

    } else {
      // old SVG
      XML no_style = new XML ("no_style") ;
      deep_analyze_SVG(header_svg, false, target, primal_name, primal_opacity) ;
    }
  }




  private void build_array_style(XML style) {
    String string_style = style.toString() ;
    String [] st_temp = split(string_style,".st") ;
    // remove the first element of the array and the first occurence of each element
    st = new String[st_temp.length -1] ;
    for(int i = 0 ; i < st.length ;i++) {
      st[i] = "st"+ st_temp[i+1] ;
      if(st[i].contains("st"+i)) {
        st[i] = st[i].replaceAll("st"+i, "") ;
      }
    }
    // remove the word style from the last array String
    if(st[st.length -1].contains("</style>")) {
      st[st.length -1] = st[st.length -1].replaceAll("</style>","") ;
    }
  }
  
  private void save_brick_SVG() {
    /* here in the future :
    Save name of SVG, width, height and other global properties
    */
    for(int i = 0 ; i < list_brick_SVG.size() ; i++) {
      Brick_SVG shape = (Brick_SVG) list_brick_SVG.get(i) ;
      saveXML(shape.xml_brick,  saved_path_bricks_svg + folder_brick_name + "_" + i + ".svg") ;
    }
  }

  
  
  
  // Local method
  int rank_analyze = 0 ;
  private void deep_analyze_SVG(String header, boolean style, XML target, String ancestral_name, String opacity_group) {
    rank_analyze ++ ;
    String ID_xml =("") ;
    ID_xml = get_kind_SVG(target) ;

    String [] children_str = target.listChildren() ;
    XML [] children_xml = target.getChildren() ;
    // split XML children



    // build brick XML
    for(int i = 0 ; i < children_xml.length ; i++) {
      if( children_str[i].equals("rect") 
          || children_str[i].equals("line") 
          || children_str[i].equals("polyline") 
          || children_str[i].equals("circle") 
          || children_str[i].equals("ellipse") 
          || children_str[i].equals("polygon") 
          || children_str[i].equals("path")
          || children_str[i].equals("text")
          ) {
        if(check_kind_SVG(children_xml[i])) {
          add_brick_SVG(header_svg, style, children_xml[i], ancestral_name, opacity_group) ;
        }
      } else if(children_str[i].equals("g") ) {
        String new_name = ancestral_name + children_xml[i].getString("id") ;
        if(!style) if(opacity_group == null || opacity_group == "none")  opacity_group = children_xml[i].getString("opacity") ;
        deep_analyze_SVG(header, style, children_xml[i], new_name, opacity_group) ;
      } 
    }
  }
  
  
  
  private void add_brick_SVG(String header, boolean style, XML target_brick, String ancestral_name, String opacity_group) {
    String name = target_brick.getName() ;
    if( name != null && ( name.equals("rect")
                         || name.equals("line")
                         || name.equals("polyline")
                         || name.equals("circle")
                         || name.equals("ellipse")
                         || name.equals("polygon")
                         || name.equals("path"))
                         || name.equals("text")
      ) {
      catch_brick_shape(header, style, target_brick, ancestral_name, opacity_group) ;
    }
  }
    




  /**
  CATCH INFO 
  */
  private String catch_header_SVG(XML target) {
    String s = "" ;
    String string_to_split = target.toString() ;
    String [] part = string_to_split.split("<") ;
    s = "<"+part[1] ;
    return s ;
  }
  
  
  private void catch_brick_shape(String header, boolean style, XML target, String ancestral_name, String opacity_group) {
    Brick_SVG new_brick = new Brick_SVG(header, style, target, ID_brick, ancestral_name, opacity_group) ;
    list_brick_SVG.add(new_brick) ;
    ID_brick++ ;
  }
  /**
  CHECK INFO
  */  
  private boolean check_kind_SVG(XML target_brick) {
    String kind_name = target_brick.getName() ;
    if(kind_name.equals("path")
       || kind_name.equals("rect") 
       || kind_name.equals("line") 
       || kind_name.equals("polyline") 
       || kind_name.equals("polygon")
       || kind_name.equals("circle")
       || kind_name.equals("ellipse")
       || kind_name.equals("text")
       ) {
      return true ;
    } else {
      return false ;
    }
  }

  private boolean check_g_shape(XML target) {
    boolean result = false ;
    if(target.getChild("g")!= null ) result = true ;
    else result = false ;
    return result ;

  }
  /**
  GET
  */
  private String get_kind_SVG(XML target) {
    String kind = "" ;
    if(target.getChild("path") != null ) kind = "path" ;
    else if(target.getChild("line")!= null ) kind = "line" ;
    else if(target.getChild("polyline")!= null ) kind = "polyline" ;
    else if(target.getChild("polygon")!= null ) kind = "polygon" ;
    else if(target.getChild("circle")!= null )kind = "circle" ;
    else if(target.getChild("ellipse")!= null ) kind = "ellipse" ;
    else if(target.getChild("rect")!= null ) kind = "rect" ;
    else if(target.getChild("text")!= null ) kind = "text" ;
    else if(target.getChild("g")!= null ) kind = "g" ;
    else kind = "no kind detected" ;
    return kind ;
  }
  /**
  END ANALYZE

  */
  
  






























  
  /**
  PRIVATE CLASS
  
  */
  /**
  class brick
  */
  private class Brick_SVG {
    private String file_name ;
    private String brick_name = "no name" ;
    private String family_name = "no name" ;
    private String kind = "" ;
    private int ID ;


    // attribut font
    /**
    may be not here but in the class Text with the build method ???
    */
    private PFont font = null  ;
    private float size_font = MAX_INT;
    private int alignment = MAX_INT ;
    // private String sentence = null ;

    private String font_str = null ;
    private String font_size_str = null ;
    private String alignment_str = null ;
    private String font_unit_str = null ;
    /**
    may be not here but in the class Text with the build method ???
    */



    // attribut colour
    private int fill, stroke ;
    private float strokeMitterlimit ;
    private float strokeWeight ;
    private float opacity, opacity_group ;
    private boolean noStroke, noFill ;

    // str
    private String fill_str = null;        
    private String stroke_str = null ;
    private String stroke_mitterlimit_str = null ;
    private String strokeWeight_str = null ;
    private String opacity_str = null ;


    private String clip_rule_str = null ;
    private String fill_rule_str = null ;



    private int width, height ;
    private XML xml_brick ;
    private boolean style ;
    private String built_svg_file = "" ;
   
    Brick_SVG(String header, boolean style, XML brick, int ID, String ancestral_name, String str_opacity_group) {
      this.style = style ;
      this.ID = ID ;
      built_svg_file = header + brick.toString() + "</svg>" ;
      xml_brick = parseXML(built_svg_file) ;
  
      brick_name = get_name(brick) ;
      family_name = ancestral_name + "_" + get_name(xml_brick) ;
      this.kind = get_kind_SVG(xml_brick) ;
      if(str_opacity_group != "none" && str_opacity_group != null) opacity_group = Float.valueOf(str_opacity_group.trim()).floatValue(); else opacity_group = 1.f ;
      set_aspect(brick) ;
    }
  
    public String get_name(XML target) {
      String name = "no name" ;
      if(target.getString("id") != null) name = target.getString("id") ;
      return name ;
    }

    public int get_id() {
      return ID;
    }

    /**
    aspect original
    */
    private void set_aspect(XML target) {
      // catch attribut
      if(style) {
        // style tag from last Illustrator CC
        catch_attribut_by_style(target) ;
      } else {
        // old data from illustrator CS
        catch_attribut(target) ;
      }
      


      // give attribut
      // font size
      if(font_size_str != null) {
        size_font = Float.parseFloat(font_size_str) ;
      }
      // font
      if(font_str != null) {
        String [] fontList = PFont.list() ;
        for(int i = 0 ; i < fontList.length ; i++) {
          if(font_str.equals(fontList[i])) {
            int size = 60 ;
            if(size_font != MAX_INT && size_font > size ) size = (int)size_font ;
            font = createFont(fontList[i], size) ;
          }
        }
      }

      // fill
      if(fill_str == null) {
        fill = 0xff000000 ; 
      } else if(fill_str.contains("none")) {
        noFill = true ;
      } else {
        String fill_temp = "" ;
        fill_temp = fill_str.substring(1) ;
        fill = unhex(fill_temp) ;
      }
      // stroke
      if(stroke_str == null) {
        stroke = MAX_INT ; 
      } else if(stroke_str.contains("none")) {
        noStroke = true ;
      } else {
        String stroke_temp = "" ;
        stroke_temp = stroke_str.substring(1) ;
        stroke = unhex(stroke_temp) ;
      }
      // strokeWeight
      if(strokeWeight_str == null  || strokeWeight_str.contains("none")) strokeWeight = 1.f ; 
      else strokeWeight = Float.valueOf(strokeWeight_str.trim()).floatValue();
      // stroke mitter
      if(stroke_mitterlimit_str == null  || stroke_mitterlimit_str.contains("none")) strokeMitterlimit = 10 ; 
      else strokeMitterlimit = Float.valueOf(stroke_mitterlimit_str.trim()).floatValue();
      // opacity
      if(opacity_str == null || opacity_str.contains("none")) opacity = 1.f ; 
      else opacity = Float.valueOf(opacity_str.trim()).floatValue();
      if(opacity == 1.f && opacity_group != 1.f) opacity = opacity_group ;
    }



    // super local method
    //
    // catch attribut classic SVG version 1
    private void catch_attribut(XML target) {
      fill_str =  target.getString("fill") ;        
      stroke_str =  target.getString("stroke") ;
      stroke_mitterlimit_str =  target.getString("stroke-mitterimit") ;
      strokeWeight_str =  target.getString("stroke-width") ;
      opacity_str =  target.getString("opacity") ;

      font_str = target.getString("font-family") ;
      font_size_str = target.getString("font-size") ;

      clip_rule_str = target.getString("clip-rule") ;
      fill_rule_str = target.getString("fill-rule") ;
    }

    // catch attribut style SVG version 2
    private void catch_attribut_by_style(XML target) {
      String style_id = target.getString("class") ;
      // catch the style in the style list
      String [] id = split(style_id, "st") ;
      // clean white space in the String array, because for the class text there is few style, and there is white space between each one.
      if(id.length > 1) {
        for(int i = 0 ; i < id.length ;i++) {
          if(id[i].contains(" ")) id[i] = id[i].replaceAll(" ", "") ;
          if(i != 0) { 
            int which_style = Integer.parseInt(id[i]) ;
            String my_style = st[which_style] ;
            if(my_style.contains("}") ) {
              my_style = my_style.replaceAll("}","") ;
            }
            if(my_style.contains("{")) {
              my_style = my_style.substring(1) ;
            }

            String [] attribut = split(my_style,";") ;
            // loop to check all component of style
            for(int k = 0 ; k < attribut.length ; k++) {
              if(attribut[k].contains("fill:")) {
                String [] final_data = attribut[k].split(":") ;
                fill_str = final_data[1] ;
              }
              if(attribut[k].contains("stroke:")) {
                String [] final_data = attribut[k].split(":") ;
                stroke_str = final_data[1] ;
              }
              if(attribut[k].contains("stroke-mitterlimit:")) {
                String [] final_data = attribut[k].split(":") ;
                stroke_mitterlimit_str = final_data[1] ;
              }
              if(attribut[k].contains("stroke-width:")) {
                String [] final_data = attribut[k].split(":") ;
                strokeWeight_str = final_data[1] ;
              }
              if(attribut[k].contains("opacity:")) {
                String [] final_data = attribut[k].split(":") ;
                opacity_str = final_data[1] ;
              }
              if(attribut[k].contains("font-family:")) {
                String [] final_data = attribut[k].split(":") ;
                font_str = final_data[1] ;
              }
              if(attribut[k].contains("font-size:")) {
                String [] final_data = attribut[k].split(":") ;
                font_size_str = final_data[1] ;
              }
              if(attribut[k].contains("clip-rule:")) {
                String [] final_data = attribut[k].split(":") ;
                clip_rule_str = final_data[1] ;
              }
              if(attribut[k].contains("fill-rule:")) {
                String [] final_data = attribut[k].split(":") ;
                fill_rule_str = final_data[1] ;
              }
            }
          }
        }
      }
      // clear
      if(font_str != null) {
        if(font_str.contains("'")) {
          font_str = font_str.replaceAll("'","") ;
        }
      } 
      
      // split size and unit type for font
      if(font_size_str != null) {
        if(font_size_str.endsWith("pt")) {
          font_unit_str = "pt" ;
          font_size_str = font_size_str.replaceAll("pt","") ; // * 1.25f;
        } else if (font_size_str.endsWith("pc")) {
          font_unit_str = "pc" ;
          font_size_str = font_size_str.replaceAll("pc","") ; // * 15;
        } else if (font_size_str.endsWith("mm")) {
          font_unit_str = "mm" ;
          font_size_str = font_size_str.replaceAll("mm","") ; // * 3.543307f;
        } else if (font_size_str.endsWith("cm")) {
          font_unit_str = "cm" ;
          font_size_str = font_size_str.replaceAll("cm","") ; // * 35.43307f;
        } else if (font_size_str.endsWith("in")) {
          font_unit_str = "in" ;
          font_size_str = font_size_str.replaceAll("in","") ; // * 90;
        } else if (font_size_str.endsWith("px")) {
          font_unit_str = "px" ;
          font_size_str = font_size_str.replaceAll("px","") ;
        } else if (font_size_str.endsWith("%")) {
          font_unit_str = "%" ;
          font_size_str = font_size_str.replaceAll("%","") ;
        }
      }
    }

    
    
    
    private void aspect_fill(vec4 factor) {
      // HSB mmode
      if(noFill) {
        p5.noFill() ;
      } else {
        if(g.colorMode == 3) {
          p5.fill(hue(fill) *factor.x, saturation(fill) *factor.y, brightness(fill) *factor.z, opacity *g.colorModeA *factor.w) ;
        // RGB mmode
        } else if( g.colorMode == 1 ) {
          float red_col = red(fill) *factor.x ;
          float alpha_col = opacity *g.colorModeA *factor.w ;
          alpha_col = opacity *g.colorModeA *factor.w  ;
          p5.fill(red_col, green(fill) *factor.y, blue(fill) *factor.z, alpha_col) ;
        }
      }
    }

    private void aspect_stroke(float scale, vec4 factor) {
      if(noStroke) {
        p5.noStroke() ;
      } else {
        float thickness = strokeWeight ;
        if(scale != 1 ) thickness *= scale ;
        // HSB mmode
        if(g.colorMode == 3) {
          if(strokeWeight <= 0 || stroke == MAX_INT )  {
            p5.noStroke() ;
          } else {
            p5.strokeWeight(thickness) ;
            p5.stroke(hue(stroke) *factor.x, saturation(stroke) *factor.y, brightness(stroke) *factor.z, opacity *g.colorModeA *factor.w) ; 
          }
        // RGB mmode
        } else if( g.colorMode == 1 ) {
          if(strokeWeight <= 0 || stroke == MAX_INT)  {
            p5.noStroke() ;
          } else {
            p5.strokeWeight(thickness) ;
            p5.stroke(red(stroke) *factor.x, green(stroke) *factor.y, blue(stroke) *factor.z, opacity *g.colorModeA *factor.w) ; 
          }
        }
      }
    }
  }
  

















  /**
  Class Text
  */

  private class ROPEText {
    String shape_name ;
    vec3 pos ;
    vec6 matrix ;
    int ID ;
    String sentence = null ;
  
    ROPEText (vec6 matrix, String sentence, int ID) {
      this.ID = ID ;
      this.pos = vec3(matrix.e, matrix.f,0) ;
      this.matrix = matrix ;

      this.sentence = sentence ;
    }
    
    public void add_value(vec3... value) {
      pos.add(value[0]) ;
    }

    public int get_id() {
      return ID;
    }
  }

  /**
  Class Line
  */
  private class Line {
    String shape_name ;
    vec3 pos_a, pos_b ;
    int ID ;
  
    Line(float x_a, float y_a,  float x_b, float y_b, int ID) {
      this.ID = ID ;
      this.pos_a = vec3(x_a, y_a,0) ;
      this.pos_b = vec3(x_b, y_b,0) ;
    }
    
    public void add_value(vec3... value) {
      pos_a.add(value[0]) ;
      pos_b.add(value[0]) ;
    }
  }



    
  
  /**
  class to build all specific group
  */
  private class Vertices {
    String shape_name = "my name is noboby" ;
    vec2 size ;
    vec3 [] vert ;
    int [] vertex_code ;
    int code_vertex_count ;
    int num ;
    int ID ;
    
    Vertices(int code_vertex_count, int num, PShape p, String mother_name, int ID) {
      this.ID = ID ;
      this.num = num ;
      // not sur we need this shape_name !
      this.shape_name = mother_name + "<>" +p.getName() ;
  
      this.code_vertex_count = code_vertex_count ;
      
      vert = new vec3[num] ;
      vertex_code = new int[num] ;
      vertex_code = p.getVertexCodes() ;
      size = vec2(p.width, p.height);
    }
    
    public void build_vertices_3D(PShape path) {
      for(int i = 0 ; i < num ; i++) {
        vert[i] = vec3(path.getVertex(i)) ;
      }
    }
    
    public vec3 [] vertices() {
      return vert ;
    }

    public void add_value(vec3... value) {
      if(value.length <= vert.length) {
        for(int i = 0 ; i < value.length ; i++) {
          vert[i].add(value[i]) ;
        }
      } else {
        for(int i = 0 ; i < vert.length ; i++) {
          vert[i].add(value[i]) ;
        }
      }
    }
  }


  /**
  Class Ellipse
  */

  private class Ellipse {
    String shape_name ;
    vec3 pos ;
    vec2 size ;
    vec6 matrix ;
    int ID ;
  
    Ellipse(vec6 matrix, float cx, float cy,  float rx, float ry, int ID) {
      this.matrix = matrix ;
      this.ID = ID ;
      this.pos = vec3(cx, cy,0) ;
      this.size = vec2(rx, ry).mult(2) ;
    }
    
    public void add_value(vec3... value) {
      pos.add(value[0]) ;
    }
  }

  /**
  Class Rectangle
  */
  private class Rectangle {
    String shape_name ;
    vec3 pos ;
    vec2 size ;
    vec6 matrix ;
    int ID ;
  
    Rectangle(vec6 matrix, float x, float y,  float width_rect, float height_rect, int ID) {
      this.matrix = matrix ;
      this.ID = ID ;
      this.pos = vec3(x, y,0) ;
      this.size = vec2(width_rect, height_rect) ;
    }
    
    public void add_value(vec3... value) {
      pos.add(value[0]) ;
    }
  }
  /**
  END OF PRIVATE CLASS

  */
}
/**
END OF MAIN CLASS

*/
/**
* ROPE SCIENCE
* v 0.7.9
* Copyleft (c) 2014-2020
* @author @stanlepunk
* @see https://github.com/StanLepunK/Rope_framework
* Processing 4.0.a2
*/




/**
* check if int number is prime number
*/
public boolean is_prime(int n) {
  if(n == 2) {
    return true;
  } else if (n%2==0) {
    return false;
  } else {
    for(int i=3; i*i<=n ; i+=2) {
      if(n%i==0) {
        return false;
      }
    }
    return true;
  } 
}




/**
Gaussian randomize
v 0.0.2
*/
public @Deprecated
float random_gaussian(float value) {
  return random_gaussian(value, .4f) ;
}

public @Deprecated
float random_gaussian(float value, float range) {
  /*
  * It's cannot possible to indicate a value result here, this part need from the coder ?
  */
  printErrTempo(240,"float random_gaussian(); method must be improved or totaly deprecated");
  range = abs(range) ;
  float distrib = random(-1, 1) ;
  float result = 0 ;
  if(value == 0) {
    value = 1 ;
    result = (pow(distrib,5)) *(value *range) +value ;
    result-- ;
  } else {
    result = (pow(distrib,5)) *(value *range) +value ;
  }
  return result;
}



/**
Next Gaussian randomize
v 0.0.2
*/
/**
* return value from -1 to 1
* @return float
*/
Random random = new Random();
public float random_next_gaussian() {
  return random_next_gaussian(1,1);
}

public float random_next_gaussian(int n) {
  return random_next_gaussian(1,n);
}

public float random_next_gaussian(float range) {
  return random_next_gaussian(range,1);
}

public float random_next_gaussian(float range, int n) {
  float roots = (float)random.nextGaussian();
  float arg = map(roots,-2.5f,2.5f,-1,1);  
  if(n > 1) {
    if(n%2 ==0 && arg < 0) {
       arg = -1 *pow(arg,n);
     } else {
       arg = pow(arg,n);
     }
     return arg *range ;
  } else {
    return arg *range ;
  }
}


















/**
Physic Rope
v 0.0.2
*/
public double g_force(double dist, double m_1, double m_2) {
  return R_Constants.G *(dist*dist)/(m_1 *m_2);
}























/**
* Math rope 
* v 1.9.0
* @author Stan le Punk
* @see https://github.com/StanLepunK/Math_rope
*/

//roots dimensions n
public float roots(float valueToRoots, int n) {
  return pow(valueToRoots, 1.0f/n) ;
}

// Decimal
// @return a specific quantity of decimal after comma
public float decimale(float arg, int n) {
  float div = pow(10, abs(n)) ;
  return Math.round(arg *div) / div;
}


/**
* geometry util
* v. 0.0.7
*/
public float perimeter_disc(int r) {
  return 2 *r *PI ;
}

public float radius_from_circle_surface(int surface) {
  return sqrt(surface/PI) ;
}


public boolean inside(ivec pos, ivec size, ivec2 target_pos) {
  return inside(vec2(pos.x,pos.y), vec2(size.x,size.y), vec2(target_pos), ELLIPSE);
}

public boolean inside(ivec pos, ivec size, ivec2 target_pos, int type) {
  return inside(vec2(pos.x,pos.y), vec2(size.x,size.y), vec2(target_pos), type);
}

public boolean inside(vec pos, vec size, vec2 target_pos) {
  return inside(pos, size, target_pos, ELLIPSE);
}

public boolean inside(vec pos, vec size, vec2 target_pos, int type) {
  if(type == ELLIPSE) {
    // this part can be improve to check the 'x' and the 'y'
    if (dist(vec2(pos.x,pos.y), target_pos) < size.x *.5f) return true ; 
    else return false ;
  } else {
    if(target_pos.x > pos.x && target_pos.x < pos.x +size.x && 
       target_pos.y > pos.y && target_pos.y < pos.y +size.y) return true ; 
      else return false ;
  } 
}





/**
* https://forum.processing.org/two/discussion/90/point-and-line-intersection-detection
* refactoring from Quark Algorithm
*/
public boolean is_on_line(vec2 start, vec2 end, vec2 point, float range) {
  vec2 vp = vec2();
  vec2 line = sub(end,start);
  float l2 = line.magSq();
  if (l2 == 0.0f) {
    vp.set(start);
    return false;
  }
  vec2 pv0_line = sub(point, start);
  float t = pv0_line.dot(line)/l2;
  pv0_line.normalize();
  vp.set(line);
  vp.mult(t);
  vp.add(start);
  float d = dist(point, vp);
  if (t >= 0 && t <= 1 && d <= range) {
    return true;
  } else {
    return false;
  }
}

/**
* https://forum.processing.org/one/topic/how-do-i-find-if-a-point-is-inside-a-complex-polygon.html
* http://paulbourke.net/geometry/
* thks to Moggach and Paul Brook
*/
public boolean in_polygon(vec [] points, vec2 pos) {
  int i, j;
  boolean is = false;
  int sides = points.length;
  for(i = 0, j = sides - 1 ; i < sides ; j = i++) {
    if (( ((points[i].y() <= pos.y()) && (pos.y() < points[j].y())) || ((points[j].y() <= pos.y()) && (pos.y() < points[i].y()))) &&
          (pos.x() < (points[j].x() - points[i].x()) * (pos.y() - points[i].y()) / (points[j].y() - points[i].y()) + points[i].x())) {
      is = !is;
    }
  }
  return is;
}









/**
GEOMETRY POLAR and CARTESIAN
*/
/**
Info
http://mathinsight.org/vectors_cartesian_coordinates_2d_3d
http://zone.ni.com/reference/en-XX/help/371361H-01/gmath/3d_cartesian_coordinate_rotation_euler/
http://www.mathsisfun.com/polar-cartesian-coordinates.html
https://en.wikipedia.org/wiki/Spherical_coordinate_system
http://stackoverflow.com/questions/20769011/converting-3d-polar-coordinates-to-cartesian-coordinates
http://www.vias.org/comp_geometry/math_coord_convert_3d.htm
http://mathworld.wolfram.com/Sphere.html
*/
/*
@return float
*/
public float longitude(float x, float range) {
  return map(x, 0,range, 0, TAU) ;
}

public float latitude(float y, float range) {
  return map(y, 0,range, 0, TAU) ;
}

/**
* angle
* v 0.0.2
* @return float
*/
public float angle_radians(float y, float range) {
  return map(y, 0,range, 0, TAU) ;
}

public float angle_degrees(float y, float range) {
  return map(y, 0,range, 0, 360) ;
}

public float angle(vec2 a, vec2 b) {
  return atan2(b.y -a.y, b.x -a.x);
}

public float angle(vec2 v) {
  return atan2(v.y, v.x);
}



  

/* 
return a vector info : radius,longitude, latitude
@return vec3
*/
public vec3 to_polar(vec3 cart) {
  float radius = sqrt(cart.x * cart.x + cart.y * cart.y + cart.z * cart.z);
  float phi = acos(cart.x / sqrt(cart.x * cart.x + cart.y * cart.y)) * (cart.y < 0 ? -1 : 1);
  float theta = acos(cart.z / radius) * (cart.z < 0 ? -1 : 1);
  // check NaN result
  if (Float.isNaN(phi)) phi = 0 ;
  if (Float.isNaN(theta)) theta = 0 ;
  if (Float.isNaN(radius)) radius = 0 ;
  // result
  //return new vec3(radius, longitude, latitude) ;
  return new vec3(phi, theta, radius) ;
}







// Cartesian 3D
/**
// @ return vec3
// return the position of point on Sphere, with longitude and latitude
*/
//If you want just the final pos
public vec3 to_cartesian_3D(vec2 pos, vec2 range, float sizeField)  {
  // vertical plan position
  float verticalY = to_cartesian_2D(pos.y, vec2(0,range.y), vec2(0,TAU), sizeField).x ;
  float verticalZ = to_cartesian_2D(pos.y, vec2(0,range.y), vec2(0,TAU), sizeField).y ; 
  vec3 posVertical = new vec3(0, verticalY, verticalZ) ;
  // horizontal plan position
  float horizontalX = to_cartesian_2D(pos.x, vec2(0,range.x), vec2(0,TAU), sizeField).x ; 
  float horizontalZ = to_cartesian_2D(pos.x, vec2(0,range.x), vec2(0,TAU), sizeField).y  ;
  vec3 posHorizontal = new vec3(horizontalX, 0, horizontalZ) ;
  
  return projection(middle(posVertical, posHorizontal), sizeField) ;
}

public vec3 to_cartesian_3D(float latitude, float longitude) {
  float radius_normal = 1 ;
  return to_cartesian_3D(latitude, longitude, radius_normal);
}

// main method
public vec3 to_cartesian_3D(float latitude, float longitude,  float radius) {
  // https://en.wikipedia.org/wiki/List_of_common_coordinate_transformations
  // https://en.wikipedia.org/wiki/Spherical_coordinate_system
  // https://fr.wikipedia.org/wiki/Coordonn%C3%A9es_sph%C3%A9riques
  

  /*
  //  Must be improve is not really good in the border versus direct polar rotation with the matrix
  */ 
  float theta = longitude%TAU ;
  float phi = latitude%PI ;

  float x = radius *sin(theta) *cos(phi);
  float y = radius *sin(theta) *sin(phi);
  float z = radius *cos(theta);
  return new vec3(x, y, z);
}
/*
vec3 to_cartesian_3D(float longitude, float latitude, float radius) {
  // https://en.wikipedia.org/wiki/List_of_common_coordinate_transformations
  float x = radius *sin(latitude) *cos(longitude);
  float y = radius *sin(latitude) *sin(longitude);
  float z = radius *cos(latitude);
  return new vec3(x, y, z);
}
*/







// To cartesian 2D
public vec2 to_cartesian_2D(float pos, vec2 range, vec2 target_rad, float distance) {
  float rotation_plan = map(pos, range.x, range.y, target_rad.x, target_rad.y)  ;
  return to_cartesian_2D(rotation_plan, distance);
}

public vec2 to_cartesian_2D(float angle, float radius) {
  return to_cartesian_2D(angle).mult(radius);
}

// main method
public vec2 to_cartesian_2D(float angle) {
  float x = cos(angle);
  float y = sin(angle);
  return vec2(x,y);
}







/**
// Projection
*/
// Cartesian projection 2D
public vec2 projection(vec2 direction) {
  return projection(direction, vec2(), 1.f) ;
}

public vec2 projection(vec2 direction, float radius) {
  return projection(direction, vec2(), radius) ;
}
public vec2 projection(vec2 direction, vec2 origin, float radius) {
  vec2 p = direction.copy().dir(origin) ;
  p.mult(radius) ;
  p.add(origin) ;
  return p ;
}
// polar projection 2D
public vec2 projection(float angle) {
  return projection(angle, 1) ;
}
public vec2 projection(float angle, float radius) {
  return vec2(cos(angle) *radius, sin(angle) *radius) ;
}
// cartesian projection 3D
public vec3 projection(vec3 direction) {
  return projection(direction, vec3(), 1.f) ;
}

public vec3 projection(vec3 direction, float radius) {
  return projection(direction, vec3(), radius) ;
}

public vec3 projection(vec3 direction, vec3 origin, float radius) {
  vec3 ref = direction.copy() ;
  vec3 p = ref.dir(origin) ;
  p.mult(radius) ;
  p.add(origin) ;
  return p ;
}


/**
look at 
before target direction
v 0.0.2
*/
// Target direction return the normal direction of the target from the origin
public @Deprecated
vec2 target_direction(vec2 target, vec2 my_position) {
  printErrTempo(240, "vec2 target_direction() deprecated instead use look_at(vec target, vec origin) method, becareful the result is mult by -1");
  return projection(target, my_position, 1).sub(my_position);
}

public @Deprecated
vec3 target_direction(vec3 target, vec3 my_position) {
   printErrTempo(240, "vec2 target_direction() deprecated instead use look_at(vec target, vec origin) method, becareful the result is mult by -1");
  return projection(target, my_position, 1).sub(my_position) ;
}


public vec2 look_at(vec2 target, vec2 origin) {
  return projection(target, origin, 1).sub(origin).mult(-1,1);
}

public vec3 look_at(vec3 target, vec3 origin) {
  return projection(target, origin, 1).sub(origin);
}





/**
SPHERE PROJECTION
*/
/**
FIBONACCI SPHERE PROJECTION CARTESIAN
*/
public vec3 [] list_cartesian_fibonacci_sphere(int num, float step, float root) {
  float root_sphere = root *num ;
  vec3 [] list_points = new vec3[num] ;
  for (int i = 0; i < list_points.length ; i++) list_points[i] = distribution_cartesian_fibonacci_sphere(i, num, step, root_sphere) ;
  return list_points ;
}
/*
float root_cartesian_fibonnaci(int num) {
  return random(1) *num ;
}
*/

public vec3 distribution_cartesian_fibonacci_sphere(int n, int num, float step, float root) {
  if(n<num) {
    float offset = 2.f / num ;
    float y  = (n *offset) -1 + (offset / 2.f) ;
    float r = sqrt(1 - pow(y,2)) ;
    float phi = ((n +root)%num) * step ;
    
    float x = cos(phi) *r ;
    float z = sin(phi) *r ;
    
    return vec3(x,y,z) ;
  } else return vec3() ;
}

/**
POLAR PROJECTION FIBONACCI SPHERE
*/
public vec2 [] list_polar_fibonacci_sphere(int num, float step) {
  vec2 [] list_points = new vec2[num] ;
  for (int i = 0; i < list_points.length ; i++) list_points[i] = distribution_polar_fibonacci_sphere(i, num, step) ;
  return list_points ;
}
public vec2 distribution_polar_fibonacci_sphere(int n, int num, float step) {
  if(n<num) {
    float longitude = r.PHI *TAU *n;
    longitude /= step ;
    // like a normalization of the result ?
    longitude -= floor(longitude); 
    longitude *= TAU;
    if (longitude > PI)  longitude -= TAU;
    // Convert dome height (which is proportional to surface area) to latitude
    float latitude = asin(-1 + 2*n/(float)num);
    return vec2(longitude, latitude) ;
  } else return vec2() ;

}




// normal direction 0-360 to -1, 1 PVector
public PVector normal_direction(int direction) {
  int numPoints = 360;
  float angle = TWO_PI/(float)numPoints;
  direction = 360-direction;
  direction += 180;
  return new PVector(sin(angle*direction), cos(angle*direction));
}

// degre direction from PVector direction
public float deg360(PVector dir) {
  float deg360 ;
  deg360 = 360 -(degrees(atan2(dir.x, dir.y)) +180);
  return deg360 ;
}

public float deg360(vec2 dir) {
  float deg360 ;
  deg360 = 360 -(degrees(atan2(dir.x, dir.y)) +180);
  return deg360 ;
}

/**
ROTATION
*/
//Rotation Objet
public void rotation(float angle, float pos_x, float pos_y) {
  translate(pos_x,pos_y);
  rotate (radians(angle));
}

public void rotation(float angle, vec2 pos) {
  translate(pos.x,pos.y);
  rotate(radians(angle));
}

public vec2 rotation(vec2 ref, vec2 lattice, float angle) {
  float a = angle(lattice, ref) +angle;
  float d = lattice.dist(ref);
  float x = lattice.x +cos(a) *d;
  float y = lattice.y +sin(a) *d;
  return vec2(x,y);
}

/**
May be must push to deprecated
*/
public vec2 rotation_lattice(vec2 ref, vec2 lattice, float angle) {
  float a = angle( lattice, ref) +angle;
  float d = dist( lattice, ref);
  float x = lattice.x +cos(a) *d;
  float y = lattice.y +sin(a) *d;
  return vec2(x,y);
}









/**
PRIMITIVE 2D
*/
/**
DISC
*/
public void disc(PVector pos, int diam, int c ) {
  for ( int i = 1 ; i < diam +1 ; i++) {
    circle(c, pos, i) ;
  }
}

public void chromatic_disc( PVector pos, int diam ) {
  for ( int i = 1 ; i < diam +1 ; i++) {
    chromatic_circle(pos, i) ;
  }
}

/**
CIRCLE
*/
public void chromatic_circle(PVector pos, int d) {
  int surface = d*d ; // surface is equale of square surface where is the cirlcke...make sens ?

  int radius = ceil(radius_from_circle_surface(surface)) ;
  int numPoints = ceil(perimeter_disc( radius)) ;
  for(int i=0; i < numPoints; i++) {
      //circle
      float stepAngle = map(i, 0, numPoints, 0, 2*PI) ; 
      float angle =  2*PI - stepAngle;
      //color
      int c = color (i, 100,100) ;
      //display
      set(PApplet.parseInt(projection(angle, radius).x + pos.x) , PApplet.parseInt(projection(angle, radius).y + pos.y), c);
  }
}


//full cirlce
public void circle(int c, PVector pos, int d) {
  int surface = d*d ; // surface is equale of square surface where is the cirlcke...make sens ?

  int radius = ceil(radius_from_circle_surface(surface)) ;
  int numPoints = ceil(perimeter_disc(radius)) ;
  for(int i=0; i < numPoints; i++) {
      float stepAngle = map(i, 0, numPoints, 0, 2*PI) ; 
      float angle =  2*PI - stepAngle;
      set(PApplet.parseInt(projection(angle, radius).x + pos.x) , PApplet.parseInt(projection(angle, radius).y + pos.y), c);
  }
}

//circle with a specific quantity points
public void circle(int c, PVector pos, int d, int num) {
  int surface = d*d ; // surface is equale of square surface where is the cirlcke...make sens ?

  int radius = ceil(radius_from_circle_surface(surface)) ;
  for(int i=0; i < num; i++) {
      float stepAngle = map(i, 0, num, 0, 2*PI) ; 
      float angle =  2*PI - stepAngle;
      set(PApplet.parseInt(projection(angle, radius).x + pos.x) , PApplet.parseInt(projection(angle, radius).y + pos.y), c);
  }
}
//circle with a specific quantity points and specific shape for each point
public void circle(PVector pos, int d, int num, PVector size, String shape) {
  int surface = d*d ; // surface is equale of square surface where is the cirlcke...make sens ?
  int whichShape = 0 ;
  if (shape.equals("point") )  whichShape = 0;
  else if (shape.equals("ellipse") )  whichShape = 1 ;
  else if (shape.equals("rect") )  whichShape = 2 ;
  else if (shape.equals("box") )  whichShape = 3 ;
  else if (shape.equals("sphere") )  whichShape = 4 ;
  else whichShape = 0 ;

  int radius = ceil(radius_from_circle_surface(surface)) ;
  for(int i=0; i < num; i++) {
    float stepAngle = map(i, 0, num, 0, 2*PI) ; 
    float angle =  2*PI - stepAngle;
    PVector newPos = new PVector(projection(angle, radius).x + pos.x, projection(angle, radius).y + pos.y) ;
    if(whichShape == 0 ) point(newPos.x, newPos.y) ;
    if(whichShape == 1 ) ellipse(newPos.x, newPos.y, size.x, size.y) ;
    if(whichShape == 2 ) rect(newPos.x, newPos.y, size.x, size.y) ;
    if(whichShape == 3 ) {
      pushMatrix() ;
      translate(newPos.x, newPos.y,0) ;
      box(size.x, size.y, size.z) ;
      popMatrix() ;
    }
    if(whichShape == 4 ) {
      pushMatrix() ;
      translate(newPos.x, newPos.y,0) ;
      int detail = (int)size.x /4 ;
      if (detail > 10 ) detail = 10 ;
      sphereDetail(detail) ;
      sphere(size.x) ;
      popMatrix() ;
    }
  }
}
// summits around the circle
public PVector [] circle (PVector pos, int d, int num) {
  PVector [] p = new PVector [num] ;
  int surface = d*d ; 
  int radius = ceil(radius_from_circle_surface(surface)) ;
  
  // choice your starting point
  float startingAnglePos = PI*.5f;
  if(num == 4) startingAnglePos = PI*.25f;
  
  for( int i=0 ; i < num ; i++) {
    float stepAngle = map(i, 0, num, 0, TAU) ; 
    float angle =  TAU - stepAngle -startingAnglePos;
    p[i] = new PVector(projection(angle, radius).x +pos.x,projection(angle, radius).y + pos.y) ;
  }
  return p ;
}

public PVector [] circle (PVector pos, int d, int num, float jitter) {
  PVector [] p = new PVector [num] ;
  int surface = d*d ; 
  int radius = ceil(radius_from_circle_surface(surface)) ;
  
  // choice your starting point
  float startingAnglePos = PI*.5f;
  if(num == 4) startingAnglePos = PI*.25f;
  
  float angleCorrection ; // this correction is cosmetic, when we'he a pair beam this one is stable for your eyes :)
  for( int i=0 ; i < num ; i++) {
    int beam = num /2 ;
    if ( beam%2 == 0 ) angleCorrection = PI / num ; else angleCorrection = 0.0f ;
    if ( num%2 == 0 ) jitter *= -1 ; else jitter *= 1 ; // the beam have two points at the top and each one must go to the opposate...
    
    float stepAngle = map(i, 0, num, 0, TAU) ;
    float jitterAngle = map(jitter, -1, 1, -PI/num, PI/num) ;
    float angle =  TAU -stepAngle +jitterAngle +angleCorrection -startingAnglePos;
    
    p[i] = new PVector(projection(angle, radius).x +pos.x, projection(angle, radius).y +pos.y) ;
  }
  return p ;
}
/**
END DISC and CIRCLE
*/



































/**
PRIMITIVE 3D
*/

/**
POLYDRON
v 0.3.0
*/
  //create Polyhedron
  /*
  "TETRAHEDRON","CUBE", "OCTOHEDRON", "DODECAHEDRON","ICOSAHEDRON","CUBOCTAHEDRON","ICOSI DODECAHEDRON",
  "TRUNCATED CUBE","TRUNCATED OCTAHEDRON","TRUNCATED DODECAHEDRON","TRUNCATED ICOSAHEDRON","TRUNCATED CUBOCTAHEDRON","RHOMBIC CUBOCTAHEDRON",
  "RHOMBIC DODECAHEDRON","RHOMBIC TRIACONTAHEDRON","RHOMBIC COSI DODECAHEDRON SMALL","RHOMBIC COSI DODECAHEDRON GREAT"
  All Polyhedrons can use "POINT" and "LINE" display mode.
  except the "TETRAHEDRON" who can also use "VERTEX" display mode.
  */
  
// MAIN VOID to create polyhedron
public void polyhedron(String type, String style, int size) {
  polyhedron(type,style,size,null);
}
public void polyhedron(String type, String style, int size, PGraphics other) {
  //This is where the actual defining of the polyhedrons takes place

  if(vec_polyhedron_list != null) {
    //clear out whatever verts are currently defined
    vec_polyhedron_list.clear();
  } else {
    vec_polyhedron_list = new ArrayList();
  }
  
  if(type.equals("TETRAHEDRON")) tetrahedron_poly(size) ;
  if(type.equals("CUBE")) cube(size) ;
  if(type.equals("OCTOHEDRON")) octohedron(size) ;
  if(type.equals("DODECAHEDRON"))dodecahedron(size) ;
  if(type.equals("ICOSAHEDRON"))icosahedron(size) ;
  if(type.equals("CUBOCTAHEDRON"))cuboctahedron(size) ;
  if(type.equals("ICOSI DODECAHEDRON"))icosi_dodecahedron(size) ;

  if(type.equals("TRUNCATED CUBE"))truncated_cube(size) ;
  if(type.equals("TRUNCATED OCTAHEDRON"))truncated_octahedron(size) ;
  if(type.equals("TRUNCATED DODECAHEDRON"))truncated_dodecahedron(size) ;
  if(type.equals("TRUNCATED ICOSAHEDRON"))truncated_icosahedron(size) ;
  if(type.equals("TRUNCATED CUBOCTAHEDRON"))truncated_cuboctahedron(size) ;
  
  if(type.equals("RHOMBIC CUBOCTAHEDRON"))rhombic_cuboctahedron(size) ;
  if(type.equals("RHOMBIC DODECAHEDRON"))rhombic_dodecahedron(size) ;
  if(type.equals("RHOMBIC TRIACONTAHEDRON"))rhombic_triacontahedron(size) ;
  if(type.equals("RHOMBIC COSI DODECAHEDRON SMALL"))rhombic_cosi_dodecahedron_small(size) ;
  if(type.equals("RHOMBIC COSI DODECAHEDRON GREAT"))rhombic_cosi_dodecahedron_great(size) ;
  
  // which method to draw
  if(style.equals("LINE")) polyhedron_draw_line(type,other) ;
  if(style.equals("POINT")) polyhedron_draw_point(type,other) ;
  if(style.equals("VERTEX")) polyhedron_draw_vertex(type,other) ;

}




// POLYHEDRON DETAIL 
//set up initial polyhedron
float factor_size_polyhedron ;
//some variables to hold the current polyhedron...
ArrayList<vec3>vec_polyhedron_list;
float edge_polyhedron_length;
String strName, strNotes;

// FEW POLYHEDRON
// BASIC
public void tetrahedron_poly(int size) {
  if(vec_polyhedron_list == null) vec_polyhedron_list = new ArrayList();
  vec_polyhedron_list.add(vec3(1,1,1));
  vec_polyhedron_list.add(vec3(-1,-1,1));
  vec_polyhedron_list.add(vec3(-1,1,-1));
  vec_polyhedron_list.add(vec3(1,-1,-1));
  edge_polyhedron_length = 0 ;
  factor_size_polyhedron = size /2;
}

public void cube(int size) {
  addVerts(1, 1, 1);
  edge_polyhedron_length = 2;
  factor_size_polyhedron = size /2;
}

public void octohedron(int size) {
  addPermutations(1, 0, 0);
  edge_polyhedron_length = r.ROOT2;
  factor_size_polyhedron = size *.8f;
}

public void dodecahedron(int size) {
  addVerts(1, 1, 1);
  addPermutations(0, 1/r.PHI, r.PHI);
  edge_polyhedron_length = 2/r.PHI;
  factor_size_polyhedron = size /2.5f;
}


// SPECIAL
public void icosahedron(int size) {
  addPermutations(0,1,r.PHI);
  edge_polyhedron_length = 2.0f;
  factor_size_polyhedron = size /2.7f;
}

public void icosi_dodecahedron(int size) {
  addPermutations(0,0,2*r.PHI);
  addPermutations(1,r.PHI,sq(r.PHI));
  edge_polyhedron_length = 2;
  factor_size_polyhedron = size/5;
}

public void cuboctahedron(int size) {
  addPermutations(1,0,1);
  edge_polyhedron_length = r.ROOT2;
  factor_size_polyhedron = size /1.9f;
}


// TRUNCATED
public void truncated_cube(int size) {
  addPermutations(r.ROOT2-1,1,1);
  edge_polyhedron_length = 2*(r.ROOT2-1);     
  factor_size_polyhedron = size /2.1f;
}

public void truncated_octahedron(int size) {
  addPermutations(0,1,2);
  addPermutations(2,1,0);
  edge_polyhedron_length = r.ROOT2;
  factor_size_polyhedron = size/3.4f;
}

public void truncated_cuboctahedron(int size) {
  addPermutations(r.ROOT2+1,2*r.ROOT2 + 1, 1);
  addPermutations(r.ROOT2+1,1,2*r.ROOT2 + 1);
  edge_polyhedron_length = 2;
  factor_size_polyhedron = size/6.9f;
}

public void truncated_dodecahedron(int size) {
  addPermutations(0,1/r.PHI,r.PHI+2);
  addPermutations(1/r.PHI,r.PHI,2*r.PHI);
  addPermutations(r.PHI,2,sq(r.PHI));
  edge_polyhedron_length = 2*(r.PHI - 1);
  factor_size_polyhedron = size/6;
}

public void truncated_icosahedron(int size) {
  addPermutations(0,1,3*r.PHI);
  addPermutations(2,2*r.PHI+1,r.PHI);
  addPermutations(1,r.PHI+2,2*r.PHI);
  edge_polyhedron_length = 2;
  factor_size_polyhedron = size/8;
}

// RHOMBIC
public void rhombic_dodecahedron(int size) {
  addVerts(1,1,1);
  addPermutations(0,0,2);
  edge_polyhedron_length = sqrt(3);
  factor_size_polyhedron = size /2.8f;
}

public void rhombic_triacontahedron(int size) {
  addVerts(sq(r.PHI), sq(r.PHI), sq(r.PHI));
  addPermutations(sq(r.PHI), 0, pow(r.PHI, 3));
  addPermutations(0,r.PHI, pow(r.PHI,3));
  edge_polyhedron_length = r.PHI*sqrt(r.PHI+2);
  factor_size_polyhedron = size /7.2f;
}

public void rhombic_cuboctahedron(int size) {
  addPermutations(r.ROOT2 + 1, 1, 1);
  edge_polyhedron_length = 2;
  factor_size_polyhedron = size/4.2f;
}

public void rhombic_cosi_dodecahedron_small(int size) {
  addPermutations(1, 1, pow(r.PHI,3));
  addPermutations(sq(r.PHI),r.PHI,2*r.PHI);
  addPermutations(r.PHI+2,0,sq(r.PHI));
  edge_polyhedron_length = 2;
  factor_size_polyhedron = size/7.4f;
}

public void rhombic_cosi_dodecahedron_great(int size) {
  addPermutations(1/r.PHI,1/r.PHI,r.PHI+3);
  addPermutations(2/r.PHI,r.PHI,2*r.PHI+1);
  addPermutations(1/r.PHI, sq(r.PHI),3*r.PHI-1);
  addPermutations(2*r.PHI-1,2,r.PHI+2);
  addPermutations(r.PHI,3,2*r.PHI);
  edge_polyhedron_length = 2*r.PHI-2;
  factor_size_polyhedron = size/7.8f;
}



//Built Tetrahedron
// EASY METHOD, for direct and single drawing
// classic and easy method
public void polyhedron_draw_point(String name) {
  polyhedron_draw_point(name,null);
}

public void polyhedron_draw_point(String name, PGraphics other) {
  for (int i = 0 ; i < vec_polyhedron_list.size() ; i++) {
    vec3 point = vec_polyhedron_list.get(i);
    if(name.equals("TETRAHEDRON")) {
      push(other);
      rotateX(TAU -1,other);
      rotateY(PI/4,other);
    }
    point(point.x *factor_size_polyhedron, point.y *factor_size_polyhedron, point.z *factor_size_polyhedron);
    if(name.equals("TETRAHEDRON")) {
      pop(other);
    }
  }
}


public void polyhedron_draw_line(String name) {
  polyhedron_draw_line(name,null);
}

public void polyhedron_draw_line(String name, PGraphics other) {
  for (int i=0; i <vec_polyhedron_list.size(); i++) {
    for (int j=i +1; j < vec_polyhedron_list.size(); j++) {
      if (isEdge(i, j, vec_polyhedron_list) || edge_polyhedron_length == 0 ) {
        vec3 v1 = vec_polyhedron_list.get(i).copy();
        vec3 v2 = vec_polyhedron_list.get(j).copy();
        if(name.equals("TETRAHEDRON")) {
          push(other) ;
          rotateX(TAU -1,other);
          rotateY(PI/4,other);
        }
        line( v1.x *factor_size_polyhedron, v1.y *factor_size_polyhedron, v1.z *factor_size_polyhedron, 
              v2.x *factor_size_polyhedron, v2.y *factor_size_polyhedron, v2.z *factor_size_polyhedron,
              other);
        if(name.equals("TETRAHEDRON")) {
          pop(other);
        }
      }
    }
  }
}

public void polyhedron_draw_vertex(String name) {
  polyhedron_draw_vertex(name,null);
}

public void polyhedron_draw_vertex(String name, PGraphics other) {
  // TETRAHEDRON
  if(name.equals("TETRAHEDRON")) {
    push(other);
    rotateX(TAU -1,other);
    rotateY(PI/4,other) ;
    int n = 4 ; // quantity of face of Tetrahedron
    for(int i = 0 ; i < n ; i++) {
      // choice of each point
      int a = i ;
      int b = i+1 ;
      int c = i+2 ;
      if(i == n-2 ) c = 0 ;
      if(i == n-1 ) {
        b = 0 ;
        c = 1 ;
      }
      vec3 v1 = vec_polyhedron_list.get(a).copy();
      vec3 v2 = vec_polyhedron_list.get(b).copy();
      vec3 v3 = vec_polyhedron_list.get(c).copy();
      // scale the position of the points
      v1.mult(factor_size_polyhedron);
      v2.mult(factor_size_polyhedron);
      v3.mult(factor_size_polyhedron);
      
      // drawing
      beginShape(other);
      vertex(v1,other);
      vertex(v2,other);
      vertex(v3,other);
      endShape(CLOSE,other);
    }
    pop(other);
  // OTHER POLYHEDRON
  } else {
    beginShape(other) ;
    for (int i= 0; i <vec_polyhedron_list.size(); i++) {
      for (int j= i +1; j <vec_polyhedron_list.size(); j++) {
        if (isEdge(i, j, vec_polyhedron_list) || edge_polyhedron_length == 0 ) {
          // vLine((PVector)vec_polyhedron_list.get(i), (PVector)vec_polyhedron_list.get(j));
          vec3 v1 = vec_polyhedron_list.get(i).copy();
          vec3 v2 = vec_polyhedron_list.get(j).copy();
          v1.mult(factor_size_polyhedron);
          v2.mult(factor_size_polyhedron);;
          vertex(v1,other);
          vertex(v2,other);
        }
      }
    }
    endShape(CLOSE,other);
  }
}
// END of EASY METHOD and DIRECT METHOD

 



/**
annexe draw polyhedron
*/
public boolean isEdge(int vID1, int vID2, ArrayList<vec3>listPoint) {
  //had some rounding errors that were messing things up, so I had to make it a bit more forgiving...
  int pres = 1000;
  vec3 v1 = listPoint.get(vID1).copy();
  vec3 v2 = listPoint.get(vID2).copy();
  float d = sqrt(sq(v1.x - v2.x) + sq(v1.y - v2.y) + sq(v1.z - v2.z)) + .00001f;
  return (PApplet.parseInt(d *pres)==PApplet.parseInt(edge_polyhedron_length *pres));
}






// ADD POINTS for built POLYHEDRON
/////////////////////////////////
public void addPermutations(float x, float y, float z) {
  //adds vertices for all three permutations of x, y, and z
  addVerts(x, y, z);
  addVerts(z, x, y);
  addVerts(y, z, x);
}


 
public void addVerts(float x, float y, float z) {
  //adds the requested vert and all "mirrored" verts
  vec_polyhedron_list.add (vec3(x,y,z));
  // z
  if (z != 0.0f) vec_polyhedron_list.add (vec3(x,y,-z)); 
  // y
  if (y != 0.0f) {
    vec_polyhedron_list.add (vec3(x, -y, z));
    if (z != 0.0f) vec_polyhedron_list.add(vec3(x,-y,-z));
  } 
  // x
  if (x != 0.0f) {
    vec_polyhedron_list.add (vec3(-x, y, z));
    if (z != 0.0f) vec_polyhedron_list.add(vec3(-x,y,-z));
    if (y != 0.0f) {
      vec_polyhedron_list.add(vec3(-x, -y, z));
      if (z != 0.0f) vec_polyhedron_list.add(vec3(-x,-y,-z));
    }
  }
}
/**
* TABLE METHOD
* v 0.0.4
* 2016-2020
* for Table with the first COLLUMN is used for name and the next 6 for the value.
*The method is used with the Class Info
*
*/
// table method for row sort
public void buildTable(Table table, TableRow [] tableRow, String [] col_name, String [] row_name) {
  // add col
  for(int i = 0 ; i < col_name.length ; i++) {
    table.addColumn(col_name[i]);
  }
  // add row
  tableRow = new TableRow[row_name.length] ;
  buildRow(table, row_name) ;
}

public void buildTable(Table table, String [] col_name) {
  // add col
  for(int i = 0 ; i < col_name.length ; i++) {
    table.addColumn(col_name[i]);
  }
}

public void buildRow(Table table, String [] row_name) {
  int num_row = table.getRowCount() ;
  for(int i = 0 ; i < num_row ; i++) {
    TableRow row = table.getRow(i) ;
    row.setString(table.getColumnTitle(0), row_name[i]) ; 
  }
}

public void setTable(Table table, TableRow [] rows, Info_Object... info) {
  for(int i = 0 ; i < rows.length ; i++) {
    if(rows[i] != null) {
      for(int j = 0 ; j < info.length ; j++) {
        if(info[j] != null && info[j].get_name().equals(rows[i].getString(table.getColumnTitle(0)))) {
          for(int k = 1 ; k < 7 ; k++) {
            if(table.getColumnCount() > k && info[j].catch_obj(k-1) != null)  write_row(rows[i], table.getColumnTitle(k), info[j].catch_obj(k-1)) ;
          }
        }
        
      }
    }
  }
}


public void setRow(Table table, Info_Object info) {
  TableRow result = table.findRow(info.get_name(), table.getColumnTitle(0)) ;
  if(result != null) {
    for(int k = 1 ; k < 7 ; k++) {
      if(table.getColumnCount() > k && info.catch_obj(k-1) != null)  write_row(result, table.getColumnTitle(k), info.catch_obj(k-1)) ;
    }
  }
}

public void write_row(TableRow row, String col_name, Object o) {
  if(o instanceof String) {
    String s = (String) o ;
    row.setString(col_name, s);
  } else if(o instanceof Short) {
    short sh = (Short) o ;
    row.setInt(col_name, sh);
  } else if(o instanceof Integer) {
    int in = (Integer) o ;
    row.setInt(col_name, in);
  } else if(o instanceof Float) {
    float f = (Float) o ;
    row.setFloat(col_name, f);
  } else if(o instanceof Character) {
    char c = (Character) o ;
    String s = Character.toString(c) ;
    row.setString(col_name, s);
  } else if(o instanceof Boolean) {
    boolean b = (Boolean) o ;
    String s = Boolean.toString(b) ;
    row.setString(col_name, s);
  } 
}
/**
Info_dict 
v 0.3.0.1
*/
public class Info_dict {
  ArrayList<Info> list;
  char type_list = 'o';

  Info_dict(char type_list) {
    this.type_list = type_list;
  }

  Info_dict() {
    list = new ArrayList<Info>();
  }

  // add Object
  public void add(String name, Object a) {
    Info_Object info = new Info_Object(name,a);
    list.add(info);
  }
  public void add(String name, Object a, Object b) {
    Info_Object info = new Info_Object(name,a,b);
    list.add(info);
  }
  public void add(String name, Object a, Object b, Object c) {
    Info_Object info = new Info_Object(name,a,b,c);
    list.add(info);
  }
  public void add(String name, Object a, Object b, Object c, Object d) {
    Info_Object info = new Info_Object(name,a,b,c,d);
    list.add(info);
  }
  public void add(String name, Object a, Object b, Object c, Object d, Object e) {
    Info_Object info = new Info_Object(name,a,b,c,d,e);
    list.add(info);
  }
  public void add(String name, Object a, Object b, Object c, Object d, Object e, Object f) {
    Info_Object info = new Info_Object(name,a,b,c,d,e,f);
    list.add(info);
  }
  public void add(String name, Object a, Object b, Object c, Object d, Object e, Object f, Object g) {
    Info_Object info = new Info_Object(name,a,b,c,d,e,f,g);
    list.add(info);
  }

   // size
   public int size() {
    return list.size();
   }

  // read
  public void read() {
    println("Object list");
    for(Info a : list) {
      if(a instanceof Info_Object) {
        Info_Object obj = (Info_Object)a ;
        if(obj.a != null && obj.b == null && obj.c == null && obj.d == null && obj.e == null && obj.f == null && obj.g == null) {
          println(a,get_type(obj.a));   
        }
        if(obj.a != null && obj.b != null && obj.c == null && obj.d == null && obj.e == null && obj.f == null && obj.g == null) {
          println(a,get_type(obj.a),get_type(obj.b));   
        }
        if(obj.a != null && obj.b != null && obj.c != null && obj.d == null && obj.e == null && obj.f == null && obj.g == null) {
          println(a,get_type(obj.a),get_type(obj.b),get_type(obj.c));   
        }
        if(obj.a != null && obj.b != null && obj.c != null && obj.d != null && obj.e == null && obj.f == null && obj.g == null) {
          println(a,get_type(obj.a),get_type(obj.b),get_type(obj.c),get_type(obj.d));   
        }
        if(obj.a != null && obj.b != null && obj.c != null && obj.d != null && obj.e != null && obj.f == null && obj.g == null) {
          println(a,get_type(obj.a),get_type(obj.b),get_type(obj.c),get_type(obj.d),get_type(obj.e));   
        }
        if(obj.a != null && obj.b != null && obj.c != null && obj.d != null && obj.e != null && obj.f != null && obj.g == null) {
          println(a,get_type(obj.a),get_type(obj.b),get_type(obj.c),get_type(obj.d),get_type(obj.e),get_type(obj.f));   
        }
        if(obj.a != null && obj.b != null && obj.c != null && obj.d != null && obj.e != null && obj.f != null && obj.g != null) {
          println(a,get_type(obj.a),get_type(obj.b),get_type(obj.c),get_type(obj.d),get_type(obj.e),get_type(obj.f),get_type(obj.g));   
        }      
      }
    }
  }

  // get
  public Info get(int target) {
    if(target < list.size() && target >= 0) {
      return list.get(target);
    } else return null;
  }
  
  public Info [] get(String which) {
    Info [] info;
    int count = 0;
    for(Info a : list) {
      if(a.get_name().equals(which)) {
        count++;
      }
    }
    if (count > 0 ) {
      info = new Info[count] ;
      count = 0;
      for(Info a : list) {
        if(a.get_name().equals(which)) {
          info[count] = a;
          count++;
        }
      }
    } else {
      info = new Info_String[1];
      info[0] = null;
    }
    if(info.length == 1 && info[0] == null )return null ; else return info;
  }

  // clear
  public void clear() {
    list.clear();
  }

  // remove
  public void remove(String which) {
    for(int i = 0 ; i < list.size() ; i++) {
      Info a = list.get(i);
      if(a.get_name().equals(which)) {
        list.remove(i);
      }
    }
  }
  
  public void remove(int target) {
   if(target < list.size()) {
      list.remove(target);
    }
  }
}

/**
Info_int_dict
v 0.0.2
*/

public class Info_int_dict extends Info_dict {
  ArrayList<Info_int> list_int;
  Info_int_dict() {
    super('i');
    list_int = new ArrayList<Info_int>();
  }


  // add int
  public void add(String name, int a) {
    Info_int info = new Info_int(name,a);
    list_int.add(info);
  } 
  public void add(String name, int a, int b) {
    Info_int info = new Info_int(name,a,b);
    list_int.add(info);
  } 

  public void add(String name, int a, int b, int c) {
    Info_int info = new Info_int(name,a,b,c);
    list_int.add(info);
  } 
  public void add(String name, int a, int b, int c, int d) {
    Info_int info = new Info_int(name, a,b,c,d);
    list_int.add(info);
  } 
  public void add(String name, int a, int b, int c, int d, int e) {
    Info_int info = new Info_int(name,a,b,c,d,e);
    list_int.add(info);
  } 
  public void add(String name, int a, int b, int c, int d, int e, int f) {
    Info_int info = new Info_int(name,a,b,c,d,e,f);
    list_int.add(info);
  }
  public void add(String name, int a, int b, int c, int d, int e, int f, int g) {
    Info_int info = new Info_int(name,a,b,c,d,e,f,g);
    list_int.add(info);
  }


  // size
  public int size() {
    return list_int.size() ;
  }

  // read
  public void read() {
    println("Integer list");
    for(Info a : list_int) {
      println(a,"Integer");
    }
  }
  

  // get
  public Info_int get(int target) {
    if(target < list_int.size() && target >= 0) {
      return list_int.get(target);
    } else return null;
  }
  
  public Info_int [] get(String which) {
    Info_int [] info  ;
    int count = 0;
    for(Info_int a : list_int) {
      if(a.get_name().equals(which)) {
        count++;
      }
    }
    if (count > 0 ) {
      info = new Info_int[count] ;
      count = 0 ;
      for(Info_int a : list_int) {
        if(a.get_name().equals(which)) {
          info[count] = a;
          count++;
        }
      }
    } else {
      info = new Info_int[1] ;
      info[0] = null;
    }
    if(info.length == 1 && info[0] == null )return null ; else return info ;
  }

  // clear
  public void clear() {
    list_int.clear();
  }

  // remove
  public void remove(String which) {
    for(int i = 0 ; i < list_int.size() ; i++) {
      Info_int a = list_int.get(i) ;
      if(a.get_name().equals(which)) {
        list_int.remove(i);
      }
    }
  }
  
  public void remove(int target) {
   if(target < list_int.size()) {
      list_int.remove(target);
    }
  }
}

/**
Info_float_dict
v 0.0.2
*/
public class Info_float_dict extends Info_dict {
  ArrayList<Info_float> list_float ;
  Info_float_dict() {
    super('f');
    list_float = new ArrayList<Info_float>();
  }

  // add float
  public void add(String name, float a) {
    Info_float info = new Info_float(name,a);
    list_float.add(info);
  }
  public void add(String name, float a, float b) {
    Info_float info = new Info_float(name,a,b);
    list_float.add(info);
  }
  public void add(String name, float a, float b, float c) {
    Info_float info = new Info_float(name,a,b,c);
    list_float.add(info);
  }
  public void add(String name, float a, float b, float c, float d) {
    Info_float info = new Info_float(name,a,b,c,d);
    list_float.add(info);
  }
  public void add(String name, float a, float b, float c, float d, float e) {
    Info_float info = new Info_float(name,a,b,c,d,e);
    list_float.add(info);
  }
  public void add(String name, float a, float b, float c, float d, float e, float f) {
    Info_float info = new Info_float(name,a,b,c,d,e,f);
    list_float.add(info);
  }
  public void add(String name, float a, float b, float c, float d, float e, float f, float g) {
    Info_float info = new Info_float(name,a,b,c,d,e,f,g);
    list_float.add(info);
  }

  // size
  public int size() {
    return list_float.size() ;
  }

  //read
  public void read() {
    println("Float list");
    for(Info a : list_float) {
      println(a,"Float");
    }
  }
   

  // get
  public Info_float get(int target) {
    if(target < list_float.size() && target >= 0) {
      return list_float.get(target);
    } else return null;
  }
  
  public Info_float [] get(String which) {
    Info_float [] info;
    int count = 0;
    for(Info_float a : list_float) {
      if(a.get_name().equals(which)) {
        count++;
      }
    }
    if (count > 0 ) {
      info = new Info_float[count] ;
      count = 0 ;
      for(Info_float a : list_float) {
        if(a.get_name().equals(which)) {
          info[count] = a;
          count++;
        }
      }
    } else {
      info = new Info_float[1] ;
      info[0] = null;
    }
    if(info.length == 1 && info[0] == null )return null ; else return info ;
  }

  // clear
  public void clear() {
    list_float.clear();
  }

  // remove
  public void remove(String which) {
    for(int i = 0 ; i < list_float.size() ; i++) {
      Info a = list_float.get(i) ;
      if(a.get_name().equals(which)) {
        list_float.remove(i);
      }
    }
  }
  
  public void remove(int target) {
   if(target < list_float.size()) {
      list_float.remove(target);
    }
  }
}



/**
Info_String_dict

*/
public class Info_String_dict extends Info_dict {
  ArrayList<Info_String> list_String ;
  Info_String_dict() {
    super('s');
    list_String = new ArrayList<Info_String>();
  }

  // add String
  public void add(String name, String a) {
    Info_String info = new Info_String(name,a);
    list_String.add(info);
  }
  public void add(String name, String a, String b) {
    Info_String info = new Info_String(name,a,b); 
    list_String.add(info);
  }
  public void add(String name, String a, String b, String c) {
    Info_String info = new Info_String(name,a,b,c);
    list_String.add(info);
  }
  public void add(String name, String a, String b, String c, String d) {
    Info_String info = new Info_String(name,a,b,c,d);
    list_String.add(info);
  }
  public void add(String name, String a, String b, String c, String d, String e) {
    Info_String info = new Info_String(name,a,b,c,d,e);
    list_String.add(info);
  }
  public void add(String name, String a, String b, String c, String d, String e, String f) {
    Info_String info = new Info_String(name,a,b,c,d,e,f);
    list_String.add(info);
  }
  public void add(String name, String a, String b, String c, String d, String e, String f,String g) {
    Info_String info = new Info_String(name,a,b,c,d,e,f,g);
    list_String.add(info);
  }

  // size
  public int size() {
    return list_String.size() ;
  }

  //read
  public void read() {
    println("String list");
    for(Info a : list_String) {
      println(a,"String");
    }
  }
  

  // get
  public Info_String get(int target) {
    if(target < list_String.size() && target >= 0) {
      return list_String.get(target);
    } else return null;
  }
  
  public Info_String [] get(String which) {
    Info_String [] info  ;
    int count = 0 ;
    for(Info_String a : list_String) {
      if(a.get_name().equals(which)) {
        count++;
      }
    }
    if (count > 0 ) {
      info = new Info_String[count] ;
      count = 0;
      for(Info_String a : list_String) {
        if(a.get_name().equals(which)) {
          info[count] = a;
          count++;
        }
      }
    } else {
      info = new Info_String[1];
      info[0] = null;
    }
    if(info.length == 1 && info[0] == null )return null ; else return info;
  }

  // clear
  public void clear() {
    list_String.clear();
  }

  // remove
  public void remove(String which) {
    for(int i = 0 ; i < list_String.size() ; i++) {
      Info_String a = list_String.get(i);
      if(a.get_name().equals(which)) {
        list_String.remove(i);
      }
    }
  }
  
  public void remove(int target) {
   if(target < list_String.size()) {
      list_String.remove(target);
    }
  }
}


/**
Info_vec_dict
*/
public class Info_vec_dict extends Info_dict {
  ArrayList<Info_vec> list_vec ;
  Info_vec_dict() {
    super('v') ;
    list_vec = new ArrayList<Info_vec>() ;
  }

  // add vec
  public void add(String name, vec a) {
    Info_vec info = new Info_vec(name,a);
    list_vec.add(info);
  }
  public void add(String name, vec a, vec b) {
    Info_vec info = new Info_vec(name,a,b);
    list_vec.add(info);
  }
  public void add(String name, vec a, vec b, vec c) {
    Info_vec info = new Info_vec(name,a,b,c);
    list_vec.add(info);
  }
  public void add(String name, vec a, vec b, vec c, vec d) {
    Info_vec info = new Info_vec(name,a,b,c,d);
    list_vec.add(info);
  }
  public void add(String name, vec a, vec b, vec c, vec d, vec e) {
    Info_vec info = new Info_vec(name,a,b,c,d,e);
    list_vec.add(info);
  }
  public void add(String name, vec a, vec b, vec c, vec d, vec e, vec f) {
    Info_vec info = new Info_vec(name,a,b,c,d,e,f);
    list_vec.add(info);
  }
  public void add(String name, vec a, vec b, vec c, vec d, vec e, vec f, vec g) {
    Info_vec info = new Info_vec(name,a,b,c,d,e,f,g);
    list_vec.add(info);
  }

  // size
  public int size() {
    return list_vec.size();
  }

  //read
  public void read() {
    println("vec list");
    for(Info a : list_vec) {
      println(a,"vec");
    }
  }
  

  // get
  public Info_vec get(int target) {
    if(target < list_vec.size() && target >= 0) {
      return list_vec.get(target);
    } else return null;
  }
  
  public Info_vec [] get(String which) {
    Info_vec [] info;
    int count = 0 ;
    for(Info_vec a : list_vec) {
      if(a.get_name().equals(which)) {
        count++;
      }
    }
    if (count > 0 ) {
      info = new Info_vec[count];
      count = 0 ;
      for(Info_vec a : list_vec) {
        if(a.get_name().equals(which)) {
          info[count] = a;
          count++ ;
        }
      }
    } else {
      info = new Info_vec[1];
      info[0] = null ;
    }
    if(info.length == 1 && info[0] == null )return null ; else return info;
  }

  // clear
  public void clear() {
    list_vec.clear();
  }

  // remove
  public void remove(String which) {
    for(int i = 0 ; i < list_vec.size() ; i++) {
      Info_vec a = list_vec.get(i) ;
      if(a.get_name().equals(which)) {
        list_vec.remove(i);
      }
    }
  }
  
  public void remove(int target) {
   if(target < list_vec.size()) {
      list_vec.remove(target);
    }
  }
}



/**
Info 0.1.0.2

*/
interface Info {
  public String get_name();

  public Object [] catch_all() ;
  public Object catch_obj(int arg);

  public char get_type();
}
 
abstract class Info_method implements Info {
  String name  ;
  // error message
  String error_target = "Your target is beyond of my knowledge !" ;
  String error_value_message = "This value is beyond of my power mate !" ;
  Info_method (String name) {
    this.name = name ;
  }


  public String get_name() { 
    return name ;
  }
}


/**
INFO int

*/
class Info_int extends Info_method {
  char type = 'i' ;
  int a, b, c, d, e, f, g ;
  int num_value ;  


  Info_int(String name) {
    super(name) ;
  }

  Info_int(String name, int... arg) {
    super(name);
    int len = arg.length;
    if(len > 7 ) {
      num_value = 7 ; 
    } else {
      num_value = len;
    }
    if(len > 0) this.a = arg[0] ;
    if(len > 1) this.b = arg[1] ;
    if(len > 2) this.c = arg[2] ;
    if(len > 3) this.d = arg[3] ;
    if(len > 4) this.e = arg[4] ;
    if(len > 5) this.f = arg[5] ;
    if(len > 6) this.g = arg[6] ;
  }


  // get
  public int [] get() {
    int [] list = new int[]{a,b,c,d,e,f,g} ;
    return list ;
  }

  public int get(int which) {
    if(which == 0) {
      return a ; 
    } else if(which == 1) {
      return b ;
    } else if(which == 2) {
      return c ;
    } else if(which == 3) {
      return d ;
    } else if(which == 4) {
      return e ;
    } else if(which == 5) {
      return f ;
    } else if(which == 6) {
      return g ;
    } else {
      System.err.println(error_target) ;
      return 0 ;
    } 
  }
  
  public Object [] catch_all() {
    Object [] list = new Object[]{a,b,c,d,e,f,g} ;
    return list ;
  }

  public Object catch_obj(int which) {
    if(which == 0) {
      return a ; 
    } else if(which == 1) {
      return b ;
    } else if(which == 2) {
      return c ;
    } else if(which == 3) {
      return d ;
    } else if(which == 4) {
      return e ;
    } else if(which == 5) {
      return f ;
    } else if(which == 6) {
      return g ;
    } else {
      System.err.println(error_target) ;
      return null ;
    } 
  }
  
  public char get_type() { return type ; }

  // Print info
  public @Override String toString() {
    if(num_value == 1) {
      return "[ " + name + ": " + a + " ]";
    } else if(num_value == 2) {
      return "[ " + name + ": " + a + ", " + b + " ]";
    } else if(num_value == 3) {
      return "[ " + name + ": " + a + ", " + b + ", " + c + " ]";
    } else if(num_value == 4) {
      return "[ " + name + ": " + a + ", " + b + ", " + c + ", " + d + " ]";
    } else if(num_value == 5) {
      return "[ " + name + ": " + a + ", " + b + ", " + c + ", " + d + ", " + e + " ]";
    } else if(num_value == 6) {
      return "[ " + name + ": " + a + ", " + b + ", " + c + ", " + d + ", " + e + ", " + f + " ]";
    } else if(num_value == 7) {
      return "[ " + name + ": " + a + ", " + b + ", " + c + ", " + d + ", " + e + ", " + f + ", " + g +" ]";
    } else {
      System.err.println(num_value) ;
      System.err.println(error_value_message) ;
      return "hmmm hmmm there is problem with your stuff mate";
    }
  }
}

/**
INFO String
*/
class Info_String extends Info_method {
  char type = 's' ;
  String a, b, c, d, e, f, g ;
  int num_value ;  

  Info_String(String name) {
    super(name) ;
  }

  Info_String(String name, String... arg) {
    super(name);
    int len = arg.length;
    if(len > 7 ) {
      num_value = 7 ; 
    } else {
      num_value = len;
    }
    if(len > 0) this.a = arg[0] ;
    if(len > 1) this.b = arg[1] ;
    if(len > 2) this.c = arg[2] ;
    if(len > 3) this.d = arg[3] ;
    if(len > 4) this.e = arg[4] ;
    if(len > 5) this.f = arg[5] ;
    if(len > 6) this.g = arg[6] ;
  }


  // get
  public String [] get() {
    String [] list = new String[]{a,b,c,d,e,f,g} ;
    return list ;
  }

  public String get(int which) {
    if(which == 0) {
      return a ; 
    } else if(which == 1) {
      return b ;
    } else if(which == 2) {
      return c ;
    } else if(which == 3) {
      return d ;
    } else if(which == 4) {
      return e ;
    } else if(which == 5) {
      return f ;
    } else if(which == 6) {
      return g ;
    }else {
      System.err.println(error_target) ;
      return null ;
    }
  }
  
  public Object [] catch_all() {
    Object [] list = new Object[]{a,b,c,d,e,f,g} ;
    return list ;
  }

  public Object catch_obj(int which) {
    if(which == 0) {
      return a ; 
    } else if(which == 1) {
      return b ;
    } else if(which == 2) {
      return c ;
    } else if(which == 3) {
      return d ;
    } else if(which == 4) {
      return e ;
    } else if(which == 5) {
      return f ;
    } else if(which == 6) {
      return g ;
    }else {
      System.err.println(error_target) ;
      return null ;
    }
  }

  public char get_type() { return type ; }


  // Print info
  public @Override String toString() {
    if(num_value == 1) {
      return "[ " + name + ": " + a + " ]";
    } else if(num_value == 2) {
      return "[ " + name + ": " + a + ", " + b + " ]";
    } else if(num_value == 3) {
      return "[ " + name + ": " + a + ", " + b + ", " + c + " ]";
    } else if(num_value == 4) {
      return "[ " + name + ": " + a + ", " + b + ", " + c + ", " + d + " ]";
    } else if(num_value == 5) {
      return "[ " + name + ": " + a + ", " + b + ", " + c + ", " + d + ", " + e + " ]";
    } else if(num_value == 6) {
      return "[ " + name + ": " + a + ", " + b + ", " + c + ", " + d + ", " + e + ", " + f + " ]";
    } else if(num_value == 7) {
      return "[ " + name + ": " + a + ", " + b + ", " + c + ", " + d + ", " + e + ", " + f + ", " + g + " ]";
    } else {
      System.err.println(num_value) ;
      System.err.println(error_value_message) ;
      return "hmmm hmmm there is problem with your stuff mate";
    }
  }
}

/**
INFO float
*/
class Info_float extends Info_method {
  char type = 'f' ;
  float a, b, c, d, e, f, g ;
  int num_value ; 

  Info_float(String name) {
    super(name) ;
  }

  Info_float(String name, float... arg) {
    super(name);
    int len = arg.length;
    if(len > 7 ) {
      num_value = 7 ; 
    } else {
      num_value = len;
    }
    if(len > 0) this.a = arg[0] ;
    if(len > 1) this.b = arg[1] ;
    if(len > 2) this.c = arg[2] ;
    if(len > 3) this.d = arg[3] ;
    if(len > 4) this.e = arg[4] ;
    if(len > 5) this.f = arg[5] ;
    if(len > 6) this.g = arg[6] ;
  }

  // get
  public float [] get() {
    float [] list = new float[]{a,b,c,d,e,f,g} ;
    return list ;
  }

  public float get(int which) {
    if(which == 0) {
      return a ; 
    } else if(which == 1) {
      return b ;
    } else if(which == 2) {
      return c ;
    } else if(which == 3) {
      return d ;
    } else if(which == 4) {
      return e ;
    } else if(which == 5) {
      return f ;
    } else if(which == 6) {
      return g ;
    } else {
      System.err.println(error_target) ;
      return 0.0f ;
    }
  }
  
  public Object [] catch_all() {
    Object [] list = new Object[]{a,b,c,d,e,f,g} ;
    return list ;
  }

  public Object catch_obj(int which) {
    if(which == 0) {
      return a ; 
    } else if(which == 1) {
      return b ;
    } else if(which == 2) {
      return c ;
    } else if(which == 3) {
      return d ;
    } else if(which == 4) {
      return e ;
    } else if(which == 5) {
      return f ;
    } else if(which == 6) {
      return g ;
    } else {
      System.err.println(error_target) ;
      return null ;
    }
  }

  public char get_type() { return type ; }
  
  // Print info
  public @Override String toString() {
    if(num_value == 1) {
      return "[ " + name + ": " + a + " ]";
    } else if(num_value == 2) {
      return "[ " + name + ": " + a + ", " + b + " ]";
    } else if(num_value == 3) {
      return "[ " + name + ": " + a + ", " + b + ", " + c + " ]";
    } else if(num_value == 4) {
      return "[ " + name + ": " + a + ", " + b + ", " + c + ", " + d + " ]";
    } else if(num_value == 5) {
      return "[ " + name + ": " + a + ", " + b + ", " + c + ", " + d + ", " + e + " ]";
    } else if(num_value == 6) {
      return "[ " + name + ": " + a + ", " + b + ", " + c + ", " + d + ", " + e + ", " + f + " ]";
    } else if(num_value == 7) {
      return "[ " + name + ": " + a + ", " + b + ", " + c + ", " + d + ", " + e + ", " + f + ", " + g + " ]";
    } else {
      System.err.println(num_value) ;
      System.err.println(error_value_message) ;
      return "hmmm hmmm there is problem with your stuff mate";
    }
  }
}

/**
INFO vec
v 0.0.2
*/
class Info_vec extends Info_method {
  char type = 'v' ;
  vec a, b, c, d, e, f, g ;
  int num_value ;  

  Info_vec(String name) {
    super(name) ;
  }

  // vec value
  Info_vec(String name, vec... arg) {
    super(name);
    int len = arg.length;
    if(len > 7 ) {
      num_value = 7 ; 
    } else {
      num_value = len;
    }
    if(len > 0) this.a = arg[0] ;
    if(len > 1) this.b = arg[1] ;
    if(len > 2) this.c = arg[2] ;
    if(len > 3) this.d = arg[3] ;
    if(len > 4) this.e = arg[4] ;
    if(len > 5) this.f = arg[5] ;
    if(len > 6) this.g = arg[6] ;
  }




  // get
  public vec [] get() {
    vec [] list = new vec[]{a,b,c,d,e,f,g} ;
    return list ;
  }

  public vec get(int which) {
    if(which == 0) {
      return a ; 
    } else if(which == 1) {
      return b ;
    } else if(which == 2) {
      return c ;
    } else if(which == 3) {
      return d ;
    } else if(which == 4) {
      return e ;
    } else if(which == 5) {
      return f ;
    } else if(which == 6) {
      return g ;
    }else {
      System.err.println(error_target) ;
      return null;
    }
  }
  
  public Object [] catch_all() {
    Object [] list = new Object[]{a,b,c,d,e,f,g} ;
    return list ;
  }

  public Object catch_obj(int which) {
    if(which == 0) {
      return a ; 
    } else if(which == 1) {
      return b ;
    } else if(which == 2) {
      return c ;
    } else if(which == 3) {
      return d ;
    } else if(which == 4) {
      return e ;
    } else if(which == 5) {
      return f ;
    } else if(which == 6) {
      return g ;
    } else {
      System.err.println(error_target) ;
      return null ;
    }
  }

  public char get_type() { return type; }

  // Print info
  public @Override String toString() {
    if(num_value == 1) {
      return "[ " + name + ": " + a + " ]";
    } else if(num_value == 2) {
      return "[ " + name + ": " + a + ", " + b + " ]";
    } else if(num_value == 3) {
      return "[ " + name + ": " + a + ", " + b + ", " + c + " ]";
    } else if(num_value == 4) {
      return "[ " + name + ": " + a + ", " + b + ", " + c + ", " + d + " ]";
    } else if(num_value == 5) {
      return "[ " + name + ": " + a + ", " + b + ", " + c + ", " + d + ", " + e + " ]";
    } else if(num_value == 6) {
      return "[ " + name + ": " + a + ", " + b + ", " + c + ", " + d + ", " + e + ", " + f + " ]";
    } else if(num_value == 7) {
      return "[ " + name + ": " + a + ", " + b + ", " + c + ", " + d + ", " + e + ", " + f + ", " + g + " ]";
    } else {
      System.err.println(num_value) ;
      System.err.println(error_value_message) ;
      return "hmmm hmmm there is problem with your stuff mate";
    }
  }
}




/**
INFO OBJECT
v 0.0.2
*/
class Info_Object extends Info_method {
  char type = 'o' ;
  Object a, b, c, d, e, f, g ;
  int num_value ;

  Info_Object(String name) {
    super(name) ;
  }


  // Object value
  Info_Object(String name, Object... arg) {
    super(name);
    int len = arg.length;
    if(len > 7 ) {
      num_value = 7 ; 
    } else {
      num_value = len;
    }
    if(len > 0) this.a = arg[0];
    if(len > 1) this.b = arg[1];
    if(len > 2) this.c = arg[2];
    if(len > 3) this.d = arg[3];
    if(len > 4) this.e = arg[4];
    if(len > 5) this.f = arg[5];
    if(len > 6) this.g = arg[6];
  }


  // get
  public Object [] get() {
    Object [] list = new Object []{a,b,c,d,e,f,g} ;
    return list ;
  }

  public Object get(int which) {
    if(which == 0) {
      return a ; 
    } else if(which == 1) {
      return b ;
    } else if(which == 2) {
      return c ;
    } else if(which == 3) {
      return d ;
    } else if(which == 4) {
      return e ;
    } else if(which == 5) {
      return f ;
    } else if(which == 6) {
      return g ;
    } else {
      printErr(error_target) ;
      return null ;
    }
  }
  
  public Object [] catch_all() {
    Object [] list = new Object[]{a,b,c,d,e,f,g} ;
    return list ;
  }

  public Object catch_obj(int which) {
    if(which == 0) {
      return a ; 
    } else if(which == 1) {
      return b ;
    } else if(which == 2) {
      return c ;
    } else if(which == 3) {
      return d ;
    } else if(which == 4) {
      return e ;
    } else if(which == 5) {
      return f ;
    } else if(which == 6) {
      return g ;
    } else {
      printErr(error_target) ;
      return null ;
    }
  }
  
  public char get_type() { return type ; }


  // Print info
  public @Override String toString() {
    if(num_value == 1) {
      return "[ " + name + ": " + a + " ]";
    } else if(num_value == 2) {
      return "[ " + name + ": " + a + ", " + b + " ]";
    } else if(num_value == 3) {
      return "[ " + name + ": " + a + ", " + b + ", " + c + " ]";
    } else if(num_value == 4) {
      return "[ " + name + ": " + a + ", " + b + ", " + c + ", " + d + " ]";
    } else if(num_value == 5) {
      return "[ " + name + ": " + a + ", " + b + ", " + c + ", " + d + ", " + e + " ]";
    } else if(num_value == 6) {
      return "[ " + name + ": " + a + ", " + b + ", " + c + ", " + d + ", " + e + ", " + f + " ]";
    } else if(num_value == 7) {
      return "[ " + name + ": " + a + ", " + b + ", " + c + ", " + d + ", " + e + ", " + f + ", " + g + " ]";
    } else {
      printErr(num_value) ;
      printErr(error_value_message) ;
      return "hmmm hmmm there is problem with your stuff mate";
    }
  }
}
/**
* R_Text
* It's classes collection to manage text and sentences
* v 0.0.5
* 2019-2019
*/


/**
* class R_Typewriter
* v 0.0.4
*/


public class R_Typewriter {
  RShape geom_group;
  String sentence;

  String path;
  String path_vector = null;
  String type;
  String [] type_wanted;

  PFont font;
  int size;
  vec3 pos;
  float angle = 0;
  boolean reset_cloud;
  int align = LEFT;

  public R_Typewriter(PApplet pa, String path, int size) {
    geomerative.RG.init(pa); // Geomerative
    build(path,size,"ttf","otf");
  }

  public R_Typewriter(PApplet pa, String path, int size, String... type_wanted) {
    geomerative.RG.init(pa); // Geomerative
    build(path,size,type_wanted);
  }

  private void build(String path, int size, String... type_wanted) {
    if(extension_is(path,type_wanted)) {
      this.type_wanted = type_wanted;
      this.path = path;
      this.size = size;
      this.font = createFont(this.path,this.size);
      this.type = extension(this.path);
      reset_cloud = true;
    } else {
      printErr("class R_Text: font path don't match with any font type:",path);
    }
  }

  // SET
  public void path(String path, boolean show_warning) {
    if(extension_is(path,type_wanted) && !this.path.equals(path)) {
      this.path = path;
      this.font = createFont(this.path,this.size);
    } else if(show_warning) {
      printErr("class R_Text method set(): font path don't match with any font type:",path);
    }
  }

  public void size(int size){
    if(this.size != size){
      this.size = size;
      this.font = createFont(this.path,this.size);
      reset_cloud = true;
    }
  }

  public void align(int align) {
    this.align = align;
  }

  public void angle(float angle){
    this.angle = angle;
  }

  public void content(String sentence) {
    if(this.sentence == null || !this.sentence.equals(sentence)) {
      this.sentence = sentence;
    }
  }

  public void pos(vec pos) {
    pos(pos.x(),pos.y(),pos.z());
  }

  public void pos(float x, float y) {
    pos(x,y,0);
  }

  public void pos(float x, float y, float z){
    if(pos == null) {
      pos = vec3(x,y,z);
    } else {
      pos.set(x,y,z);
    }
  }

  // GET
  public vec3 pos() {
    if(pos == null) {
      pos = vec3();
    }
    return pos;
  }

  public int size() {
    return size;
  }

  public float get_angle(){
    return this.angle;
  }

  public String get_path() {
    return this.path;
  }

  public void reset(){
    reset_cloud = true;
  }

  // GET POINTS
  ArrayList<vec3>points;
  public vec3 [] get_points(){
    if (this.sentence == null) {
      content("NULL");
      return calc_get_points();
    } else {
      return calc_get_points();
    }
  }

  private vec3 [] calc_get_points(){
    if(points == null) {
      points = new ArrayList<vec3>();
    }
    if(extension_is(path,"ttf")){
      path_vector = path;
    }

    if(path_vector != null && (geom_group == null || reset_cloud)) {
      reset_cloud = false;
      try {
        geom_group = geomerative.RG.getText(sentence,path_vector,size,align);
        points.clear();
        for(int i = 0 ; i < geom_group.children.length ; i++) {
          RPoint[] geom_pts = geom_group.children[i].getPoints();
          for(RPoint p : geom_pts) {
            points.add(geom_to_vec(p).add(pos()));
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
        println("path vector",path_vector);
      } 
    }
    return points.toArray(new vec3[points.size()]); 
  }

  private vec3 geom_to_vec(RPoint p){
    return vec3(p.x,p.y,0);
  }

  // SHOW
  public void show(){
    show(0,0);
  }

    public void show(int w, int h){
    show(w,h,CORNER);
  }

  public void show(int w, int h, int window_position){
    if (this.sentence == null) {
      content("NULL");
      calc_show(w,h,window_position);
    } else {
      calc_show(w,h,window_position);
    }
  }

  private void calc_show(int w, int h, int window_position) {
    if(pos == null) {
      pos = vec3();
    }
    if(this.angle != 0){
      push();
      translate(pos);
      rotate(angle);
    }
    textFont(font);
    textAlign(align);
    if(this.angle != 0){
      if(w > 0 && h > 0){
        if(window_position != CENTER) {
          text(this.sentence,0,0,w,h);
        } else {
          text(this.sentence,-w/2,-h/2,w,h);
        } 
      } else {
        text(this.sentence,0,0);
      }
      pop();
    } else {
      if(w > 0 && h > 0){
        if(window_position != CENTER) {
          text(this.sentence,pos.x(),pos.y(),w,h);
        } else {
          text(this.sentence,-w/2 + pos.x(),-h/2 + pos.y(),w,h);
        }
        // text(this.sentence,pos.x(),pos.y(),w,h);
      } else {
        text(this.sentence,pos);
      }
    }
  }
}















/**
* Poem
* v 0.0.3
*/
public class Poem {
  String name;
  ArrayList<Vers[]> couplet = new ArrayList<Vers[]>();
  ArrayList<Vers> all;
  int vers;

  public Poem(String path) {
    String [] input = loadStrings(path);
    if(input[0] != null){
      build(input);
    } else {
      System.err.print("class Poem: Abort the input String arg[0] passing to constructor is null\n");
    }
  }

  public Poem(String [] input) {
    if(input[0] != null){
      build(input);
    } else {
      System.err.print("class Poem: Abort the input String arg[0] passing to constructor is null\n");
    }
  }

  private void build(String [] input) {
    int index_vers = 0;
    int index_local = 0;
    int index_couplet = 0;
    ArrayList<Vers> temp = new ArrayList<Vers>();
    for(int i = 0 ; i < input.length ; i++) {   
      if(input[i].equals("")) {
        if(temp.size() > 0) {
          Vers [] array = temp.toArray(new Vers[temp.size()]);
          index_couplet++;
          index_local = 0;
          couplet.add(array);
        }
        temp.clear();
      } else {
        Vers vers = new Vers(index_vers,index_local, index_couplet,input[i]);
        temp.add(vers);
        if(i == input.length -1) {
          Vers [] array = temp.toArray(new Vers[temp.size()]);
          couplet.add(array);
        }
        index_local++;
        index_vers++;
      }
    }
  }

  public int size() {
    write_all();
    return all.size();
  }

  public int num_couplets() {
    return couplet.size();
  }

  // get couplet
  public ArrayList<Vers[]> get_couplets() {
    return couplet;
  }

  public Vers [] couplet(int target) {
    if(target < couplet.size()) {
      return couplet.get(target);
    } else {
      Vers [] empty = new Vers[1];
      empty[0] = new Vers(0,0,0,"");
      return empty;
    }
  }

  // get vers
  public ArrayList<Vers> get_vers() {
    write_all();
    return all;
  }

  public Vers get_vers(int target) {
    write_all();
    if(target >= 0 && target < all.size()) {
      return all.get(target);
    } else {
      return null;
    }
  }

  private void write_all() {
    if(all == null) {
      all = new ArrayList<Vers>();
      for(Vers[] couplet : get_couplets()) {
        for(Vers v : couplet) {
          all.add(v);
        }   
      }
    }   
  }
}












public class Vers {
  int id;
  int id_local;
  int couplet;
  String sentence;
  Vers(int id, int id_local, int couplet, String sentence) {
    this.id = id;
    this.id_local = id_local;
    this.couplet = couplet;
    this.sentence = sentence;
  }

  public int id() {
    return id;
  }

  public int id_local() {
    return id_local;
  }

  public int id_couplet() {
    return couplet;
  }

  public String toString() {
    return sentence;
  }

  public Vers read() {
    return this;
  }
}
/**
* Rope UTILS 
* v 1.64.4
* Copyleft (c) 2014-2021
* Rope – Romanesco Processing Environment – 
* @author @stanlepunk
* @see https://github.com/StanLepunK/Rope_framework
*/

// METHOD MANAGER

// FOLDER & FILE MANAGER



// TRANSLATOR 


// EXPORT PDF



/**
* tempo
* v 0.0.1
* 2019-2019
* create tempo partition
*/
float [] tempo = {1};
public void tempo(float... tempo) {
	this.tempo = tempo;
}

public float sum_tempo() {
	float sum = 0;
	for(int i = 0 ; i < tempo().length ; i++) {
		sum += tempo()[i];
	}
	return sum;
}

public float get_tempo(float time) {
	return tempo()[get_tempo_pos(time)];
}

public int get_tempo_pos(float time) {
	float rank = time%sum_tempo();
	float progress = 0;
	int pos = 0;
	for(int i = 0 ; i < tempo().length ; i++) {
		progress += tempo()[i];
		if(rank < progress) {
			pos = i;
			break;
		}  
	} 
	return pos;
}

public float [] tempo() {
	return tempo;
}















/**
* METHOD MANAGER
* to create method from String name, add in a list and recall from this String name later
* v 0.0.4
* 2019-2019
*/
// main method
public void template_method(String name, PApplet pa, Class... classes) {
	if(method_index == null) {
		method_index = new ArrayList<Method_Manager>();
	} 
	init_method(name,pa,classes);
}

boolean method_exist_is = true;
public boolean method_is() {
	return method_exist_is;
}

public void method(String name, PApplet pa, Object... args) {
	method_exist_is = true;
	Method method = method_exist(name, args);
	if(method != null) {
		invoke_method(method, pa, args);
	} else {
		println("method(): no method exist for this name:",name,"or this order of arguments:");
		method_exist_is = false;
		for(int i = 0 ; i < args.length ; i++) {
			println("[",i,"]",args[i].getClass().getName());
		} 
	}
}

// private method
public Method method_exist(String name, Object... args) {
	Method method = null;
	if(method_index != null && method_index.size() > 0) {
		for(Method_Manager mm : method_index) {
			if(mm.get_name().equals(name)) {
				boolean same_is = true;
				if(args.length == mm.get_index().length) {
					for(int i = 0 ; i < args.length; i++) {
						String arg_name = translate_class_to_type_name_if_necessary(args[i]);
						if(!arg_name.equals(mm.get_index()[i])) {
							same_is = false;
							break;
						}
					}
				} else {
					same_is = false;
				}       
				if(same_is) {
					method = mm.get_method();
				}
			}
		}
	}
	return method;
}

public String translate_class_to_type_name_if_necessary(Object arg) {
	String name = arg.getClass().getName();
	if(name.equals("java.lang.Byte")) {
		name = "byte";
	} else if(name.equals("java.lang.Short")) {
		name = "short";
	} else if(name.equals("java.lang.Integer")) {
		name = "int";
	} else if(name.equals("java.lang.Long")) {
		name = "long";
	} else if(name.equals("java.lang.Float")) {
		name = "float";
	} else if(name.equals("java.lang.Double")) {
		name = "double";
	} else if(name.equals("java.lang.Boolean")) {
		name = "boolean";
	} else if(name.equals("java.lang.Character")) {
		name = "char";
	}
	return name;
}


ArrayList<Method_Manager> method_index ;
public void init_method(String name, PApplet pa, Class... classes) { 
	// check if method already exist
	boolean create_class_is = true; 
	for(Method_Manager mm : method_index) {
		if(mm.get_name().equals(name)) {
			if(mm.get_index().length == classes.length) {
				int count_same_classes = 0;
				for(int i = 0 ; i < classes.length ; i++) {
					if(mm.get_index()[i].equals(classes[i].getCanonicalName())) {
						count_same_classes++;
					}
				}
				if(count_same_classes == classes.length) {
					create_class_is = false;
					break;
				}
			}
		}
	}
	// instantiate if necessary
	if(create_class_is) {
		Method method = get_method(name,pa,classes);
		Method_Manager method_manager = new Method_Manager(method,name,classes);
		method_index.add(method_manager);
	} else {
		println("template_method(): this method",name,"with those classes organisation already exist");
	}
}


/**
* Method manger
*/
class Method_Manager {
	Method method;
	String name;
	String [] index;
	Method_Manager(Method method, String name, Class... classes) {
		index = new String[classes.length];
		for(int i = 0 ; i < index.length ; i++) {
			index[i] = classes[i].getName();
		}
		this.method = method;
		this.name = name;
	}

	public String [] get_index() {
		return index;
	}

	public String get_name() {
		return name;
	}

	public Method get_method() {
		return method;
	}
}


/**
 * refactoring of Method Reflective Invocation (v4.0)
 * Stanlepunk (2019/Apr/03)
 * Mod GoToLoop
 * https://Discourse.Processing.org/t/create-callback/9831/16
 */
public static final Method get_method(String name, Object instance, Class... classes) {
	final Class<?> c = instance.getClass();
	try {
		return c.getMethod(name, classes);
	} 
	catch (final NoSuchMethodException e) {
		try {
			final Method m = c.getDeclaredMethod(name, classes);
			m.setAccessible(true);
			return m;
		}   
		catch (final NoSuchMethodException ex) {
			ex.printStackTrace();
			return null;
		}
	}
}

public static final Object invoke_method(Method funct, Object instance, Object... args) {
	try {
		return funct.invoke(instance, args);
	} 
	catch (final ReflectiveOperationException e) {
		throw new RuntimeException(e);
	}
}























/**
CHECK SIZE WINDOW
return true if the window size has changed
*/
ivec2 rope_window_size;
public boolean window_change_is() {
	if(rope_window_size == null || !all(equal(ivec2(width,height),rope_window_size))) {
		check_window_size();
		return true;
	} else {
		return false;
	}
}

public void check_window_size() {
	if(rope_window_size == null) {
		rope_window_size = ivec2(width,height);
	} else {
		rope_window_size.set(width,height);
	}
}







/**
print Constants
v 0.1.1
*/
Constant_list rope_constants_colour_list;
public void print_constants_colour_rope() {
	if(rope_constants_colour_list == null) {
		rope_constants_colour_list = new Constant_list(rope.core.R_Constants_Colour.class);
	}
	println("ROPE CONSTANTS COLOUR");
	for(String s: rope_constants_colour_list.list()){
		println(s);
	}
}

Constant_list rope_constants_list;
public void print_constants_rope() {
	if(rope_constants_list == null) {
		rope_constants_list = new Constant_list(rope.core.R_Constants.class);
	}
	println("ROPE CONSTANTS");
	for(String s: rope_constants_list.list()){
		println(s);
	}
}

Constant_list processing_constants_list;
public void print_constants_processing() {
	if(processing_constants_list == null) {
		processing_constants_list = new Constant_list(PConstants.class);
	}
	println("PROCESSING CONSTANTS");
	for(String s: processing_constants_list.list()) {
		println(s);
	}
} 

public void print_constants() {
	print_constants_rope();
	println(" ");
	print_constants_colour_rope();
	println(" ");
	print_constants_processing();
} 

/*
* class to list the interface stuff
*/
class Constant_list {
	Field[] classFields; 
	Constant_list(Class c){
		classFields = c.getFields();
	}
	public ArrayList<String> list() {
		ArrayList<String> slist = new ArrayList();
		// for each constant
		for (Field f : classFields) {
			String s = "";
			// object type
			s = s + "(" + f.getType() + ")";
			// field name
			s = s + " " + f.getName();
			// value
			try {
				s = s + ": " + f.get(null);
			} 
			catch (IllegalAccessException e) {
			}
			// Optional special handling for field types:
			if (f.getType().equals(int.class)) {
				// ...
			}
			if (f.getType().equals(float.class)) {
				// ...
			}
			slist.add(s);
		}
		return(slist);
	}
}



















/**
* FOLDER & FILE MANAGER
* v 0.8.1
*/
String warning_input_file_folder_message = "Window was closed or the user hit cancel.";
String warning_input_file_not_accepted = ANSI_RED+"This file don't match with any extension accepted:"+ANSI_WHITE;


String [] input_type = {  "default",
													"image","media","movie","shape","sound","text",
													"load",
													"preference","setting"
												};



// filter
String[] ext_default;
String[] ext_image = { "png", "jpeg", "jpg", "tif", "tga", "gif"};
String[] ext_load;
String[] ext_media;
String[] ext_movie = { "mov", "avi", "mp4", "mpg", "mkv"};
String[] ext_preference;
String[] ext_setting = { "csv", "txt", "json"};
String[] ext_shape = { "svg", "obj"};
String[] ext_sound = { "mp3", "wav"};
String[] ext_text = { "txt", "md"};







public void print_extension_filter() {
	print_extension_filter("all");
}


public void print_extension_filter(String type) {
	if(get_inputs() != null && get_inputs().length > 0) {
		if(type.equals("all")) {
			for(int i = 0 ; i < get_inputs().length ; i++) {
				println(get_input(i).get_type());
				printArray(get_input(i).get_filter());
			}
		} else {
			int count = 0;
			for(int i = 0 ; i < input_type.length ; i++) {
				count++;
				if(input_type[i].equals(type)) {
					println(get_input(type).get_type());
					printArray(get_input(type).get_filter());
					break;
				}
				if(count == input_type.length) {
					printErr(ANSI_RED+"method print_extension_filter(): no input available for this type:"+ANSI_WHITE,type);
				}
			}
		}
	} else {
		printErr("method print_extension_filter(): no input available");
	}
}




/*
* INPUT PART
* v 1.0.1
* 2017-2021
*/
R_Input rope_input;

public void select_input() {
	select_input("default");
}

public void select_input(String type) {
	if(rope_input == null) {
		rope_input = new R_Input();
	}
	rope_input.select_input(type);
}

public R_Data_Input get_input(String type) {
	if(rope_input == null)
		rope_input = new R_Input();
	return rope_input.get_input(type);
}

public R_Data_Input [] get_inputs() {
	if(rope_input == null)
		rope_input = new R_Input();
	return rope_input.get_inputs();
}

public R_Data_Input get_input(int target) {
	if(rope_input == null)
		rope_input = new R_Input();
	return rope_input.get_input(target);
}

public boolean input_use_is() {
	return input_use_is("default");
}

public boolean input_use_is(String type) {
	if(rope_input == null)
		rope_input = new R_Input();
	return rope_input.input_use_is(type);
}

public void input_use(boolean is) {
	input_use("default" ,is);
}

public void input_use(String type, boolean is) {
	if(rope_input == null)
		rope_input = new R_Input();
	rope_input.input_use(type, is);
}

public String input_path() {
	return input_path("default");
}

public String input_path(String type) {
	if(rope_input == null)
		rope_input = new R_Input();
	return rope_input.input_path(type);
}

public void reset_input() {
	reset_input("default");
}

public void reset_input(String type) {
	if(rope_input == null)
		rope_input = new R_Input();
	rope_input.reset_input(type);
}

public File input_file() {
	return input_file("default");
}

public File input_file(String type) {
	if(rope_input == null)
		rope_input = new R_Input();
	return rope_input.input_file(type);
}

public void set_filter_input(String type, String... extension) {
	if(rope_input == null)
		rope_input = new R_Input();
	rope_input.set_filter_input(type, extension);
}


/**
* this method is called by method selectInput() in class R_Input
* and the method name must be the same as named
*/
public void select_single_file(File selection) {
	if (selection == null) {
		println("Window was closed or the user hit cancel.");
	} else {
		String default_input = "default";
		for(int i = 0 ; i < get_inputs().length ; i++) {
			if (default_input.toLowerCase().equals(get_input(i).get_type())) { 
				get_input(i).set_file(selection);
				if(get_input(i).get_file() != null) {
					println("method select_single_file(",get_input(i).get_type(),"):",get_input(i).get_file().getPath());
				}
				break;
			}
		}  
	}
}






/**
* class Input
* 2021-2021
* v 0.0.3
*/
class R_Input {
	private R_Data_Input [] input_rope;

	public R_Input() {
		init_input_group();
	}
	

	private void init_input_group() {
		if(input_rope == null) {
			input_rope = new R_Data_Input[input_type.length];
			for(int i = 0 ; i < input_rope.length ; i++) {
				input_rope[i] = new R_Data_Input();
				set_input(input_rope[i],input_type[i]);
			}
		}
	}

	private void set_input(R_Data_Input input, String type) { 
		input.set_type(type);
		input.set_prompt("select "+type);
		if(type.equals("default")) input.set_filter(ext_default);
		else if(type.equals("image")) input.set_filter(ext_image);
		else if(type.equals("load")) input.set_filter(ext_load);
		else if(type.equals("media")) input.set_filter(ext_media);
		else if(type.equals("movie")) input.set_filter(ext_movie);
		else if(type.equals("preference")) input.set_filter(ext_preference);
		else if(type.equals("setting")) input.set_filter(ext_setting);
		else if(type.equals("shape")) input.set_filter(ext_shape);
		else if(type.equals("sound")) input.set_filter(ext_sound);
		else if(type.equals("text")) input.set_filter(ext_text);
	}


	public void set_filter_input(String type, String... ext) {
		if(type.equals("default")) {
			ext_default = ext;
		} else if(type.equals("image")) {
			ext_image = ext;
		} else if(type.equals("load")) {
			ext_load = ext;
		} else if(type.equals("media")) {
			ext_media = ext;
		} else if(type.equals("movie")) {
			ext_movie = ext;
		} else if(type.equals("preference")) {
			ext_preference = ext;
		} else if(type.equals("setting")) {
			ext_setting = ext;
		} else if(type.equals("shape")) {
			ext_shape = ext;
		} else if(type.equals("sound")) {
			ext_sound = ext;
		} else if(type.equals("text")) {
			ext_text = ext;
		} else if(type.equals("default")) {
			ext_default = ext;
		}
		set_input(get_input(type),type);
	}

	// get input
	private String [] get_input_type() {
		return input_type;
	}

	public R_Data_Input get_input(String type) {
		R_Data_Input input = null;
		if(input_rope != null && input_rope.length > 0) {
			for(int i = 0 ; i < input_rope.length ; i++) {
				if(input_rope[i].get_type().equals(type)) {
					input = input_rope[i];
					break;
				}
			}
		}
		return input;
	}

	public R_Data_Input [] get_inputs() {
		return input_rope;
	}

	public R_Data_Input get_input(int target) {
		if(input_rope != null && target < input_rope.length && target >= 0) {
			return input_rope[target];
		} else {
			return null;
		}
	}

	public void select_input(String type) {
		String context = get_renderer();
		boolean apply_filter_is = true;
		if(context.equals(P3D) || context.equals(P2D) || context.equals(FX2D)) {
			apply_filter_is = false;
			println(ANSI_RED+"WARNING:"+ANSI_WHITE+" method select_input(String type) cannot apply filter extension"+ANSI_RED,type,ANSI_WHITE+"\nin this renderer context"+ANSI_RED, context,ANSI_WHITE+"instead classic method selectInput() is used");
		}

		if(!apply_filter_is) {
			type = "default";
			for(int i = 0 ; i < input_rope.length ; i++) {
				if (type.toLowerCase().equals(input_rope[i].get_type())) {  
					selectInput(input_rope[i].get_prompt(),"select_single_file");
					break;
				}
			}
		} else if(apply_filter_is) {
			int check_for_existing_method = 0 ;
			for(int i = 0 ; i < input_rope.length ; i++) {
				check_for_existing_method++;
				if(type.toLowerCase().equals(input_rope[i].get_type())){  
					select_single_file_filtering(input_rope[i]);
					break;
				}
			}

			if(check_for_existing_method == input_rope.length) {
				printErr("void select_input(String type) don't find callback method who's match with type: "+type);
				printErr("type available:");
				printArray(input_type);
			}
		}
	}

	private int max_filter_input;
	private String [] temp_filter_list;
	private void select_single_file_filtering(R_Data_Input input) {
		Frame frame = null;
		FileDialog dialog = new FileDialog(frame, input.get_prompt(), FileDialog.LOAD);
		if(input.get_filter() != null && input.get_filter().length > 0) {
			temp_filter_list = input.get_filter();
			dialog.setFilenameFilter(new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					name = name.toLowerCase();
					for (int i = 0; i < temp_filter_list.length ; i++) {
						if (name.endsWith(temp_filter_list[i]))  {
							return true;
						}
					}
					return false;
				}}
			);
		}  
		dialog.setVisible(true);
		String directory = dialog.getDirectory();
		String filename = dialog.getFile();

		if (filename != null) {
			input.set_file(new File(directory, filename));
		}
		if(input.get_file() != null) {
			println("method select_single_file_filtering(",input.get_type(),"):",input.get_file().getPath());
		}
	}

	// boolean accept_input(String path, String [] ext) {
	// 	boolean accepted = false;
	// 	for (int i = ext.length; i-- != 0;) {
	// 		if (path.endsWith(ext[i]))  {
	// 			accepted = true;
	// 			break;
	// 		}
	// 	}
	// 	return accepted;
	// }

	public void reset_input(String type) {
		for (int i = input_type.length; i-- != 0;) {
			if(input_type[i].equals(type)) {
				input_rope[i].set_is(false);
				break;
			}
		}
	}

	public boolean input_use_is(String type) {
		boolean result = false;
		for (int i = input_type.length; i-- != 0;) {
			if(input_type[i].equals(type)) {
				if(input_rope != null && input_rope[i] != null) {
					result = input_rope[i].get_is();
					break;
				}
			}
		}
		return result;
	}

	public void input_use(String type, boolean is) {
		for (int i = input_type.length; i-- != 0;) {
			if(input_type[i].equals(type)) {
				input_rope[i].set_is(is);
				break;
			}
		}
	}

	public String input_path(String type) {
		String path = null;
		for (int i = input_type.length; i-- != 0;) {
			if(input_type[i].equals(type)) {
				if(input_rope != null) {
					path = input_rope[i].get_path();
					break;
				}
			}
		}
		return path;
	}

	public File input_file(String type) {
		File file = null;
		for (int i = input_type.length; i-- != 0;) {
			if(input_type[i].equals(type)) {
				file = input_rope[i].get_file();
				break;
			}
		}
		return file;
	}

	private void set_input(String type, File file) {
		for(int i = 0 ; i < input_rope.length ; i++) {
			if(type.equals(input_rope[i].get_type())) {
				input_rope[i].set_file(file);
				input_rope[i].set_path(file.getAbsolutePath());
				input_rope[i].set_is(true);
			}
		}
	}
}



/**
* R_Data_Input
* 2017-2021
* v 0.2.0
*/
class R_Data_Input {
	File file = null;
	String type = null;
	String callback = null;
	String path = null;
	String prompt = null;
	String [] filter = null;
	boolean is;
	R_Data_Input() { }
	
	// set
	public void set_file(File file) {
		this.file = file;
		this.path = file.getPath();
	}

	public void set_is(boolean is) {
		this.is = is;
	}

	public void set_path(String path) {
		this.path = path;
	}

	public void set_type(String type) {
		this.type = type;
	}

	public void set_prompt(String prompt) {
		this.prompt = prompt;
	}

	public void set_callback(String callback) {
		this.callback = callback;
	}

	public void set_filter(String [] filter) {
		this.filter = filter;
	}
	
	// get
	public File get_file() {
		return file;
	}

	public String get_type() {
		return type;
	}

	public String get_path() {
		return path;
	}

	public String get_prompt() {
		return prompt;
	}

	public boolean get_is() {
		return is;
	}

	public String get_callback() {
		return callback;
	}

	public String [] get_filter() {
		return filter;
	}
}



















/*
* FOLDER PART
* v 1.0.1
* 2017-2021
*/
R_Folder rope_folder;
public void explore_folder(String path, String... extension) {
	explore_folder(path, false, extension);
}

public void explore_folder(String path, boolean check_sub_folder, String... extension) {
	// printArray(extension);
	if(rope_folder == null)
		rope_folder = new R_Folder();
	rope_folder.explore_folder(path, check_sub_folder, extension);
}

public String folder() {
	if(rope_folder == null)
		rope_folder = new R_Folder();
	return rope_folder.folder();
}

public ArrayList<File> get_files() {
	return rope_folder.get_files();
}

public void select_folder() {
	select_folder("");
}

public void select_folder(String message) {
	if(rope_folder == null)
		rope_folder = new R_Folder();
	rope_folder.select_folder(message);
}

public boolean folder_input_default_is() {
	if(rope_folder == null)
		rope_folder = new R_Folder();
	return rope_folder.folder_input_default_is();
}

/**
* this method is called by method select_folder() in class R_Folder
* and the method name must be the same as named
*/
public void rope_select_folder(File selection) {
	if (selection == null) {
		println(warning_input_file_folder_message);
	} else {
		println("Folder path is:" +selection.getAbsolutePath());
		rope_folder.selected_path_folder = selection.getAbsolutePath();
		rope_folder.folder_selected_is = true;
	}
}





/**
* Class R_Folder
* 2021-2021
* v 0.0.2
*/
public class R_Folder {
	private String selected_path_folder = null;
	private boolean folder_selected_is;
	private boolean explore_subfolder_is = false;
	private ArrayList <File> files;
	private int count_selection;

	public R_Folder() {
		selected_path_folder = null;
		folder_selected_is = false;
		explore_subfolder_is = false;
	}

	public void select_folder(String message) {
		selectFolder(message, "rope_select_folder");
	}

	private void explore_subfolder_is(boolean is) {
		explore_subfolder_is = is;
	}

	private boolean explore_subfolder_is() {
		return explore_subfolder_is;
	}

	private void reset_folder() {
		folder_selected_is = false;
	}

	public String folder() {
		return selected_path_folder;
	}

	private void set_media_list() {
		if(files == null) {
			files = new ArrayList<File>(); 
		} else {
			files.clear();
		}
	}

	public ArrayList<File> get_files() {
		return files ;
	}

	private String [] get_files_sort() {
		if(files != null) {
			String [] list = new String [files.size()];
			for(int i = 0 ; i < get_files().size() ; i++) {
				File f = get_files().get(i);
				list[i] = f.getAbsolutePath();
			}
			Arrays.sort(list);
			return list;

		} else return null ;

	}

	public void explore_folder(String path, boolean check_sub_folder, String... extension) {
		if((folder_input_default_is() || input_use_is()) && path != ("")) {
			count_selection++ ;
			set_media_list();
	
			ArrayList allFiles = list_files(path, check_sub_folder);
		
			String file_name = "";
			int count_pertinent_file = 0 ;
		
			for (int i = 0; i < allFiles.size(); i++) {
				File f = (File) allFiles.get(i);   
				file_name = f.getName(); 
				// Add it to the list if it's not a directory
				if (f.isDirectory() == false) {
					for(int k = 0 ; k < extension.length ; k++) {
						String ext = extension[k].toLowerCase();
						if(extension(file_name) != null && extension(file_name).equals(ext)) {
							count_pertinent_file += 1 ;
							println(count_pertinent_file, "/", i, f.getName());
							files.add(f);
						}
					}
				}
			}
			// to don't loop with this void
			reset_folder_input_default();
			reset_input();
		}
	}

	public boolean folder_input_default_is() {
		return folder_selected_is;
	}

	private void reset_folder_input_default() {
		folder_selected_is = false ;
	}

	// Method to get a list of all files in a directory and all subdirectories
	private ArrayList list_files(String dir, boolean check_sub_folder) {
		ArrayList fileList = new ArrayList(); 
		if(check_sub_folder) { 
			explore_directory(fileList, dir);
		} else {
			if(folder_selected_is) {
				File file = new File(dir);
				File[] subfiles = file.listFiles();
				for(int i = 0 ; i < subfiles.length ; i++) {
					fileList.add(subfiles[i]);
				}
			} else if(input_use_is()) {
				File file = new File(dir);
				fileList.add(file);
			}
		}
		return fileList;
	}

	// Recursive function to traverse subdirectories
	private void explore_directory(ArrayList list_file, String dir) {
		File file = new File(dir);
		if (file.isDirectory()) {
			list_file.add(file);  // include directories in the list

			File[] subfiles = file.listFiles();
			for (int i = 0; i < subfiles.length; i++) {
				// Call this function on all files in this directory
				explore_directory(list_file, subfiles[i].getAbsolutePath());
			}
		} else {
			list_file.add(file);
		}
	}
}





























/**
* SAVE LOAD  FRAME Rope
* v 0.4.1
* 2016-2019
*/
/**
* Save Frame
*/
public void save_frame(String where, String filename, PImage img) {
	float compression = 1.f;
	save_frame(where, filename, compression, img);
}

public void save_frame(String where, String filename) {
	float compression = 1.f ;
	PImage img = g;
	save_frame(where, filename, compression, img);
}

public void save_frame(String where, String filename, float compression) {
	PImage img = g;
	save_frame(where, filename, compression, img);
}

public void save_frame(String where, String filename, float compression, PImage img) {
	// check if the directory or folder exist, if it's not create the path
	File dir = new File(where);
	dir.mkdir() ;
	// final path with file name adding
	String path = where+"/"+filename ;
	try {
		OutputStream os = new FileOutputStream(new File(path));
		loadPixels(); 
		BufferedImage buff_img;
		if(img == null) {
			printErr("method save_frame(): the PImage is null, no save can be done");
		} else {
			buff_img = new BufferedImage(img.width, img.height, BufferedImage.TYPE_INT_RGB);
			buff_img.setRGB(0, 0, img.width, img.height, img.pixels, 0, img.width);

			if(extension_is(path, "bmp")) {
				save_BMP(os, buff_img);
			} else if(extension_is(path, "jpg", "jpeg")) {
				save_JPG(os, compression, buff_img);
			// } else if(extension_is(path, "png")) {
			//   event_PNG();
			//   filename = filename.substring(0,filename.length()-4);
			//   save_PNG(path,filename);
			} else {
				printErr("method save_frame(): no save match with this path "+path);
			}
		} 
	} catch (FileNotFoundException e) {
		//
	}
}






/**
* SAVE PNG
*/
boolean record_PNG;
public void save_PNG() {
	save_PNG("data", "shot_"+ranking_shot);
}

public void save_PNG(String path, String name_file) {
	if(record_PNG) {
		saveFrame(path + "/" + name_file + ".png");
		record_PNG = false;
	}
}

public void event_PNG() {
	record_PNG = true;
}







/**
SAVE JPG
v 0.0.1
*/
// classic one
public boolean save_JPG(OutputStream output, float compression,  BufferedImage buff_img) {
	compression = truncate(compression, 1);
	if(compression < 0) compression = 0.0f;
	else if(compression > 1) compression = 1.0f;

	try {
		ImageWriter writer = null;
		ImageWriteParam param = null;
		IIOMetadata metadata = null;

		if ((writer = image_io_writer("jpeg")) != null) {
			param = writer.getDefaultWriteParam();
			param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
			param.setCompressionQuality(compression);

			writer.setOutput(ImageIO.createImageOutputStream(output));
			writer.write(metadata, new IIOImage(buff_img, null, metadata), param);
			writer.dispose();
			output.flush();
			javax.imageio.ImageIO.write(buff_img, "jpg", output);
		}
		return true;
	}
	catch(IOException e) {
		e.printStackTrace();
	}
	return false;
}

public ImageWriter image_io_writer(String extension) {
	// code from Processing PImage.java
	Iterator<ImageWriter> iter = ImageIO.getImageWritersByFormatName(extension);
	if (iter.hasNext()) {
		return iter.next();
	}
	return null;
}


/**
SAVE BMP
v 0.3.0.1
*/
// SAVE
public boolean save_BMP(OutputStream output, BufferedImage buff_img) {
	try {
		Graphics g = buff_img.getGraphics();
		g.dispose();
		output.flush();
		
		ImageIO.write(buff_img, "bmp", output);
		return true;
	}
	catch(IOException e) {
		e.printStackTrace();
	}
	return false;
}

// LOAD
public PImage load_image_BMP(String fileName) {
	PImage img = null;

	try {
		InputStream is = createInput(fileName);
		BufferedImage buff_img = ImageIO.read(is);
		int[] pix = buff_img.getRGB(0, 0, buff_img.getWidth(), buff_img.getHeight(), null, 0, buff_img.getWidth());
		img = createImage(buff_img.getWidth(),buff_img.getHeight(), RGB);
		// println("Componenent", buff_img.getColorModel().getNumComponents()) ;
		img.pixels = pix;   

		// in case the picture is in grey value...to set the grey because this one is very very bad
		// I don't find any solution to solve it...
		// any idea ?
		if(buff_img.getColorModel().getNumComponents() == 1) {
			float ratio_brightness = .95f;
			for(int i = 0 ; i < img.pixels.length ; i++) {     
				colorMode(HSB);
				float b = brightness(img.pixels[i]) *ratio_brightness ;
				img.pixels[i] = color(0,0,b);
				colorMode(RGB);
			}
		}
	}
	catch(IOException e) {
	}

	if(img != null) {
		return img;
	} else {
		return null ;
	}
}


/**
* SAVE PDF
*/
String ranking_shot = "_######" ;
// PDF
boolean record_PDF;
public void start_PDF() {
	start_PDF(null,null) ;
}

public void start_PDF(String name_file) {
	start_PDF(null, name_file) ;
}

public void start_PDF(String path_folder, String name_file) {
	if(path_folder == null) path_folder = "pdf_folder";
	if(name_file == null) name_file = "pdf_file_"+ranking_shot;

	if (record_PDF && !record_PNG) {
		if(renderer_P3D()) {
			beginRaw(PDF, path_folder+"/"+name_file+".pdf"); 
		} else {
			beginRecord(PDF, path_folder+"/"+name_file+".pdf");
		}
	}
}

public void save_PDF() {
	if (record_PDF && !record_PNG) {
		if(renderer_P3D()) {
			endRaw(); 
		} else {
			endRecord() ;
		}
		println("PDF");
		record_PDF = false;
	}
}

public void event_PDF() {
	record_PDF = true;
}




















































/**
TRANSLATOR 
v 0.4.0
*/
public ivec2 calc_atoi(int index, String str) {
  int result = 0;
    int check_is = 1;
  while (index < str.length()) {
    if (str.charAt(index) >= '0' && str.charAt(index) <= '9') {
      int next = index +1;
      if (next < str.length() && str.charAt(next) >= '0' && str.charAt(next) <= '9') {
          result *= 10;
          result += (str.charAt(index) - '0');
      }
      else {
        result *= 10;
        result += (str.charAt(index) - '0');
        check_is = 0;
      }
    }
    else if (!(str.charAt(index) >= '0' && str.charAt(index) <= '9') && check_is == 0) {
      break ;
    }
    index++;
  }
  return new ivec2(result,index);
}
/**
* atoi
* v 0.0.3
* 2019-2019
*/
public int atoi(String str) {
	int index = 0;
	// sign
	int sign = 1;
	while (index < str.length()) {
		if (str.charAt(index) == '-')
			sign *= -1;
		else if (str.charAt(index) >= '0' && str.charAt(index) <= '9')
			break ;
		index++;
	}
	// clean
	int result = 0;
	ivec2 step = calc_atoi(index, str);
  result = step.x();
	// result
	return (sign * result);
}


/**
* atof
* v 0.0.1
* 2019-2019
*/
public float atof(String str) {
	int index = 0;
	float result = 0;
	// sign
	int sign = 1;
	while (index < str.length()) {
		if (str.charAt(index) == '-')
			sign *= -1;
		else if (str.charAt(index) >= '0' && str.charAt(index) <= '9')
			break ;
		index++;
	}
	// clean for int part
  ivec2 step = calc_atoi(index, str);
  result = step.x();
  index = step.y();
  // clean for floating part
  if(str.charAt(index) == '.') {
    step = calc_atoi(index, str);
    float floating = step.x();
    while(floating > 1) {
      floating *= 0.1f;
    }
    result += floating;
  }
	return (sign * result);
}





/**
primitive to byte, byte to primitive
v 0.1.0
*/
public int int_from_byte(Byte b) {
	int result = b.intValue();
	return result;
}

public Boolean boolean_from_bytes(byte... array_byte) {
	if(array_byte.length == 1) {
		if(array_byte[0] == 0) return false ; return true;
	} else {
		Boolean null_data = null;
		return null_data;
	}
}

public Character char_from_bytes(byte [] array_byte) {
	if(array_byte.length == 2) {
		ByteBuffer buffer = ByteBuffer.wrap(array_byte);
		char result = buffer.getChar();
		return result;
	} else {
		Character null_data = null;
		return null_data;
	}
}

public Short short_from_bytes(byte [] array_byte) {
	if(array_byte.length == 2) {
		ByteBuffer buffer = ByteBuffer.wrap(array_byte);
		short result = buffer.getShort();
		return result;
	} else {
		Short null_data = null;
		return null_data;
	}
}

public Integer int_from_bytes(byte [] array_byte) {
	if(array_byte.length == 4) {
		ByteBuffer buffer = ByteBuffer.wrap(array_byte) ;
		int result = buffer.getInt();
		return result;
	} else {
		Integer null_data = null;
		return null_data;
	}
}

public Long long_from_bytes(byte [] array_byte) {
	if(array_byte.length == 8) {
		ByteBuffer buffer = ByteBuffer.wrap(array_byte) ;
		long result = buffer.getLong();
		return result;
	} else {
		Long null_data = null;
		return null_data;
	}
}

public Float float_from_bytes(byte [] array_byte) {
	if(array_byte.length == 4) {
		ByteBuffer buffer = ByteBuffer.wrap(array_byte) ;
		float result = buffer.getFloat();
		return result;
	} else {
		Float null_data = null;
		return null_data;
	}
}

public Double double_from_bytes(byte [] array_byte) {
	if(array_byte.length == 8) {
		ByteBuffer buffer = ByteBuffer.wrap(array_byte) ;
		double result = buffer.getDouble();
		return result;
	} else {
		Double null_data = null;
		return null_data;
	}
}



// @Deprecated // this method return a short because it's reordering by LITTLE_ENDIAN to used by getShort()
public Integer int_from_4_bytes(byte [] array_byte, boolean little_endian) {
	if(array_byte.length == 4) {
		ByteBuffer buffer = ByteBuffer.wrap(array_byte);
		if(little_endian)buffer.order(ByteOrder.LITTLE_ENDIAN);
		int result = buffer.getShort();
		return result;
	} else {
		Integer null_data = null;
		return null_data;
	}
}



// return byte
public byte[] to_byte(Object obj) {
	if(obj instanceof Boolean) {
		boolean value = (boolean)obj;
		byte [] array = new byte[1];
		array[0] = (byte)(value?1:0);
		return array;
	} else if(obj instanceof Character) {
		char value = (char)obj;
		return ByteBuffer.allocate(2).putChar(value).array();
	} else if(obj instanceof Short) {
		short value = (short)obj;
		return ByteBuffer.allocate(2).putShort(value).array();
	} else if(obj instanceof Integer) {
		int value = (int)obj;
		return ByteBuffer.allocate(4).putInt(value).array();
	} else if(obj instanceof Long) {
		long value = (long)obj;
		return ByteBuffer.allocate(8).putLong(value).array();
	} else if(obj instanceof Float) {
		float value = (float)obj;
		return ByteBuffer.allocate(4).putFloat(value).array();
	} else if(obj instanceof Double) {
		double value = (double)obj;
		return ByteBuffer.allocate(8).putDouble(value).array();
	} else return null;
}



/**
* from ivec, vec to PVector
*/
public PVector to_PVector(Object obj) {
	if(obj instanceof vec || obj instanceof ivec) {
		if(obj instanceof vec) {
			vec v = (vec)obj;
			return new PVector(v.x,v.y,v.z);
		} else {
			ivec iv = (ivec)obj;
			return new PVector(iv.x,iv.y,iv.z);
		}
	} else {
		printErr("method to_Pvectro(): wait for Object of type vec or ivec");
		return null;
	}
}


/**
truncate a float argument
v 0.0.2
*/
public float truncate(float x) {
		return round(x *100.0f) /100.0f;
}

public float truncate(float x, int num) {
	float result = 0.0f ;
	switch(num) {
		case 0:
			result = round(x *1.0f) /1.0f;
			break;
		case 1:
			result = round(x *10.0f) /10.0f;
			break;
		case 2:
			result = round(x *100.0f) /100.0f;
			break;
		case 3:
			result = round(x *1000.0f) /1000.0f;
			break;
		case 4:
			result = round(x *10000.0f) /10000.0f;
			break;
		case 5:
			result = round(x *100000.0f) /100000.0f;
			break;
		default:
			result = x;
			break;
	}
	return result;
}

/**
Int to String
Float to String
v 0.0.3
*/
/*
@ return String
*/
public String join_int_to_String(int []data) {
	String intString ;
	String [] dataString = new String [data.length] ;
	for ( int i = 0 ; i < data.length ; i++) dataString[i] = Integer.toString(data[i]) ;
	intString = join(dataString,"/") ;
	
	return intString ;
}

//float to String with array list
public String join_float_to_String(float []data) {
	String floatString ;
	String [] dataString = new String [data.length] ;
	//for ( int i = 0 ; i < data.length ; i++) dataString[i] = Float.toString(data[i]) ;
	//we must use just one decimal after coma, to dodge the outBoundIndex blablabla
	for ( int i = 0 ; i < data.length ; i++) dataString[i] = String.format("%.1f" ,data[i]) ;
	floatString = join(dataString,"/") ;
	
	return floatString ;
}

//Translater to String
// float to String
public String float_to_String_1(float data) {
	String float_string_value ;
	float_string_value = String.format("%.1f", data ) ;
	return float_String(float_string_value) ;
}
//
public String float_to_String_2(float data) {
	String float_string_value ;
	float_string_value = String.format("%.2f", data ) ;
	return float_String(float_string_value) ;
}
//
public String float_to_String_3(float data) {
	String float_string_value ;
	float_string_value = String.format("%.3f", data ) ;
	return float_String(float_string_value) ;
}

//
public String float_to_String_4(float data) {
	String float_string_value ;
	float_string_value = String.format("%.4f", data ) ;
	return float_String(float_string_value) ;
}
// local
public String float_String(String value) {
	if(value.contains(".")) {
		return value ;
	} else {
		String [] temp = split(value, ",") ;
		value = temp[0] +"." + temp[1] ;
		return value ;
	}
}


// int to String
public String int_to_String(int data) {
	String float_string_value ;
	float_string_value = Integer.toString(data ) ;
	return float_string_value ;
}



/**
array float to vec
*/
public vec4 array_to_vec4_rgba(float... f) {
	vec4 v = vec4(1);
	if(f.length == 1) {
		v.set(f[0],f[0],f[0],1.f);
	} else if(f.length == 2) {
		v.set(f[0],f[0],f[0],f[1]);
	} else if(f.length == 3) {
		v.set(f[0],f[1],f[2],1);
	} else if(f.length > 3) {
		v.set(f[0],f[1],f[2],f[3]);
	}
	return v;
}





























































































/**
print
v 0.3.0
*/
public static final String ANSI_RESET = "\u001B[0m";
public static final String ANSI_BLACK = "\u001B[30m";
public static final String ANSI_RED = "\u001B[31m";
public static final String ANSI_GREEN = "\u001B[32m";
public static final String ANSI_YELLOW = "\u001B[33m";
public static final String ANSI_BLUE = "\u001B[34m";
public static final String ANSI_PURPLE = "\u001B[35m";
public static final String ANSI_CYAN = "\u001B[36m";
public static final String ANSI_WHITE = "\u001B[37m";
/**
* System.out.println(ANSI_RED + "This text is red!" + ANSI_RESET);
*/

public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
/**
* System.out.println(ANSI_GREEN_BACKGROUND + "This text has a green background but default text!" + ANSI_RESET);
* System.out.println(ANSI_RED + "This text has red text but a default background!" + ANSI_RESET);
* System.out.println(ANSI_GREEN_BACKGROUND + ANSI_RED + "This text has a green background and red text!" + ANSI_RESET);
*/
// print Err
public void printErr(Object... obj) {
	System.err.println(write_message(obj));
}

// print tempo
public void printErrTempo(int tempo, Object... obj) {
	if(frameCount%tempo == 0 || frameCount <= 1) {
		String message = write_message(obj);
		System.err.println(message);
	}
}

public void printTempo(int tempo, Object... obj) {
	if(frameCount%tempo == 0 || frameCount <= 1) {
		String message = write_message(obj);
		println(message);
	}
}




public void printArrayTempo(int tempo, Object[] obj) {
	if(frameCount%tempo == 0 || frameCount <= 1) {
		printArray(obj);
	}
}

public void printArrayTempo(int tempo, float[] arg) {
	if(frameCount%tempo == 0 || frameCount <= 10) {
		printArray(arg);
	}
}

public void printArrayTempo(int tempo, int[] arg) {
	if(frameCount%tempo == 0 || frameCount <= 10) {
		printArray(arg);
	}
}

public void printArrayTempo(int tempo, String[] arg) {
	if(frameCount%tempo == 0 || frameCount <= 10) {
		printArray(arg);
	}
}











/**
MAP
map the value between the min and the max
@ return float
*/
public float map_cycle(float value, float min, float max) {
	max += .000001f ;
	float newValue ;
	if(min < 0 && max >= 0 ) {
		float tempMax = max +abs(min) ;
		value += abs(min) ;
		float tempMin = 0 ;
		newValue =  tempMin +abs(value)%(tempMax - tempMin)  ;
		newValue -= abs(min) ;
		return newValue ;
	} else if ( min < 0 && max < 0) {
		newValue = abs(value)%(abs(max)+min) -max ;
		return newValue ;
	} else {
		newValue = min + abs(value)%(max - min) ;
		return newValue ;
	}
}




/*
map the value between the min and the max, but this value is lock between the min and the max
@ return float
*/
public float map_locked(float value, float sourceMin, float sourceMax, float targetMin, float targetMax) {
	if(sourceMax >= targetMax ) sourceMax = targetMax ;
	if (value < sourceMin ) value = sourceMin ;
	if (value > sourceMax ) value = sourceMax ;
	float newMax = sourceMax - sourceMin ;
	float deltaTarget = targetMax - targetMin ;
	float ratio = ((value - sourceMin) / newMax ) ;
	float result = targetMin +deltaTarget *ratio;
	return result; 
}


// to map not linear, start the curve hardly to start slowly
public float map_begin(float value, float sourceMin, float sourceMax, float targetMin, float targetMax, int level) {
	if (value < sourceMin ) value = sourceMin ;
	if (value > sourceMax ) value = sourceMax ;
	float newMax = sourceMax - sourceMin ;
	float deltaTarget = targetMax - targetMin ;
	float ratio = ((value - sourceMin) / newMax ) ;
	ratio = pow(ratio, level) ;
	float result = targetMin +deltaTarget *ratio;
	return result;
}

// to map not linear, start the curve hardly to finish slowly
public float map_end(float value, float sourceMin, float sourceMax, float targetMin, float targetMax, int level) {
	if (value < sourceMin ) value = sourceMin ;
	if (value > sourceMax ) value = sourceMax ;
	float newMax = sourceMax - sourceMin ;
	float deltaTarget = targetMax - targetMin ;
	float ratio = ((value - sourceMin) / newMax ) ;
	ratio = pow(ratio, 1.0f/level) ;
	float result = targetMin +deltaTarget *ratio;
	return result;
}

public float map(float value, float start_1, float stop_1, float start_2, float stop_2, int begin, int end) {
	begin = abs(begin);
	end = abs(end);
	if(begin != 0 && end != 0) {
		if (value < start_1 ) value = start_1;
		if (value > stop_2 ) value = stop_2;

		float new_max = stop_2 - start_1;
		float delta = stop_2 - start_2;
		float ratio = (value - start_1) / new_max;

		ratio = map(ratio,0,1,-1,1);
		if (ratio < 0) {
			if(begin < 2) ratio = pow(ratio,begin) ;
			else ratio = pow(ratio,begin) *(-1);
			if(ratio > 0) ratio *= -1;
		} else {
			ratio = pow(ratio,end);
		}
		
		ratio = map(ratio,-1,1,0,1);
		float result = start_2 +delta *ratio;
		return result;
	} else if(begin == 0 && end != 0) {
		return map_end(value,start_1,stop_1,start_2,stop_2,end);
	} else if(end == 0 && begin != 0) {
		return map_begin(value,start_1,stop_1,start_2,stop_2,begin);
	} else {
		return map(value,start_1,stop_1,start_2,stop_2,1,1);
	}

}











/**
MISC
v 0.0.6
*/
/**
stop trhead draw by using loop and noLoop()
*/
boolean freeze_is ;
public void freeze() {
	freeze_is = (freeze_is)? false:true ;
	if (freeze_is)  {
		noLoop();
	} else {
		loop();
	}
}








/**
PIXEL UTILS
v 0.0.3
*/
public int [][] loadPixels_array_2D() {
	int [][] array_pix;
	loadPixels();
	array_pix = new int[height][width] ;
	int which_pix = 0;
	if(pixels != null) {
		for(int y = 0 ; y < height ; y++) {
			for(int x = 0 ; x < width ; x++) {
				which_pix = y *width +x ;
				array_pix[y][x] = pixels[which_pix] ;
			}
		}
	}
	if(array_pix != null) {
		return array_pix ;
	} else {
		return null ;
	}
}

































/**
CHECK
v 0.2.4
*/
/**
Check Type
v 0.0.4
*/
public String get_type(Object obj) {
	if(obj instanceof Integer) {
		return "Integer";
	} else if(obj instanceof Float) {
		return "Float";
	} else if(obj instanceof String) {
		return "String";
	} else if(obj instanceof Double) {
		return "Double";
	} else if(obj instanceof Long) {
		return "Long";
	} else if(obj instanceof Short) {
		return "Short";
	} else if(obj instanceof Boolean) {
		return "Boolean";
	} else if(obj instanceof Byte) {
		return "Byte";
	} else if(obj instanceof Character) {
		return "Character";
	} else if(obj instanceof PVector) {
		return "PVector";
	} else if(obj instanceof vec) {
		return "vec";
	} else if(obj instanceof ivec) {
		return "ivec";
	} else if(obj instanceof bvec) {
		return "bvec";
	} else if(obj == null) {
		return "null";
	} else return "Unknow" ;
}







/**
* Check OS
* v 0.0.2
*/
public String get_os() {
	return System.getProperty("os.name").toLowerCase();
}

public String get_os_family() {
	String os = System.getProperty("os.name").toLowerCase();
	String family = "";
	if(os.indexOf("win") >= 0) {
		family = "win";
	} else if(os.indexOf("mac") >= 0) {
		family = "mac";
	} else if(os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0 || os.indexOf("aix") >= 0) {
		family = "unix";
	} else if(os.indexOf("solaris") >= 0) {
		family = "solaris";
	}
	return family;
}



























/**
check value in range
*/
public boolean in_range(float min, float max, float value) {
	if(value <= max && value >= min) {
		return true ; 
	} else {
		return false ;
	}
}

public boolean in_range_wheel(float min, float max, float roof_max, float value) {
	if(value <= max && value >= min) {
		return true ;
	} else {
		// wheel value
		if(max > roof_max ) {
			// test hight value
			if(value <= (max - roof_max)) {
				return true ;
			} 
		} 
		if (min < 0) {
			// here it's + min 
			if(value >= (roof_max + min)) {
				return true ;
			} 
		} 
		return false ;
	}
}














































/**
STRING UTILS
v 0.3.3
*/
public String write_message(Object... obj) {
	String mark = " ";
	return write_message_sep(mark,obj);
}
public String write_message_sep(String mark, Object... obj) {
	String m = "";
	for(int i = 0 ; i < obj.length ; i++) {
		m += write_message(obj[i], obj.length,i,mark);
	}
	return m;
}

// local method
public String write_message(Object obj, int length, int i, String mark) {
	String message = "";
	String add = "";
	if(i == length -1) { 
		if(obj == null) {
			add = "null";
		} else {
			add = obj.toString();
		}
		return message += add;
	} else {
		if(obj == null) {
			add = "null";
		} else {
			add = obj.toString();
		}
		return message += add + mark;
	}
}



//STRING SPLIT
public String [] split_text(String str, String separator) {
	String [] text = str.split(separator) ;
	return text  ;
}


//STRING COMPARE LIST SORT
//raw compare
public int longest_String(String[] string_list) {
	int finish = 0;
	if(string_list != null) finish = string_list.length;
	return longest_String(string_list, 0, finish);
}

//with starting and end keypoint in the String must be sort
public int longest_String(String[] string_list, int start, int finish) {
	int length = 0;
	if(string_list != null) {
		for ( int i = start ; i < finish ; i++) {
			if (string_list[i].length() > length ) length = string_list[i].length() ;
		}
	}
	return length;
}

/**
Longuest String with PFont
*/
public int longest_String_pixel(PFont font, String[] string_list) {
	int [] size_font = new int[1];
	size_font[0] = font.getSize();
	int finish = 0;
	if(string_list != null) finish = string_list.length;
	return longest_String_pixel(font.getName(), string_list, size_font, 0, finish);
}

public int longest_String_pixel(PFont font, String[] string_list, int... size_font) {
	int finish = 0;
	if(string_list != null) finish = string_list.length;
	return longest_String_pixel(font.getName(), string_list, size_font, 0, finish);
}

public int longest_String_pixel(PFont font, String[] string_list, int [] size_font, int start, int finish) {
	return longest_String_pixel(font.getName(), string_list, size_font, start, finish);
}

/**
Longuest String with String name Font
*/
public int longest_String_pixel(String font_name, String[] string_list, int... size_font) {
	int finish = 0;
	if(string_list != null) finish = string_list.length;
	return longest_String_pixel(font_name, string_list, size_font, 0, finish);
}

// diferrent size by line
public int longest_String_pixel(String font_name, String[] string_list, int size_font, int start, int finish) {
	int [] s_font = new int[1];
	s_font[0] = size_font;
	return longest_String_pixel(font_name, string_list, s_font, start, finish);
}

public int longest_String_pixel(String font_name, String[] string_list, int [] size_font, int start, int finish) {
	int width_pix = 0 ;
	if(string_list != null) {
		int target_size_font = 0;
		for (int i = start ; i < finish && i < string_list.length; i++) {
			if(i >= size_font.length) target_size_font = 0 ;
			if (width_String(font_name, string_list[i], size_font[target_size_font]) > width_pix) {
				width_pix = width_String(string_list[i],size_font[target_size_font]);
			}
			target_size_font++;
		}
	}
	return width_pix;
}




/**
width String
*/
public int width_String(String target, int size) {
	return width_String("defaultFont", target, size) ;
}

public int width_String(PFont pfont, String target, int size) {
	return width_String(pfont.getName(), target, size);
}

public int width_String(String font_name, String target, int size) {
	Font font = new Font(font_name, Font.BOLD, size) ;
	BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
	FontMetrics fm = img.getGraphics().getFontMetrics(font);
	if(target == null) {
		target = "";
	}
	return fm.stringWidth(target);
}




public int width_char(char target, int size) {
	return width_char("defaultFont", target, size) ;
}

public int width_char(PFont pfont, char target, int size) {
	return width_char(pfont.getName(), target, size);
}
public int width_char(String font_name, char target, int size) {
	Font font = new Font(font_name, Font.BOLD, size) ;
	BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
	FontMetrics fm = img.getGraphics().getFontMetrics(font);
	return fm.charWidth(target);
}




// Research a specific word in String
public boolean research_in_String(String research, String target) {
	boolean result = false ;
	for(int i = 0 ; i < target.length() - research.length() +1; i++) {
		result = target.regionMatches(i,research,0,research.length()) ;
		if (result) break ;
	}
	return result ;
}





/**
String file utils
2014-2018
v 0.2.3
*/
/**
* remove element of the sketch path
*/
public String sketchPath(int minus) {
	minus = abs(minus);
	String [] element = split(sketchPath(),"/");
	String new_path ="" ;
	if(minus < element.length ) {
		for(int i = 0 ; i < element.length -minus ; i++) {
			new_path +="/";
			new_path +=element[i];
		}
		return new_path; 
	} else {
		printErr("The number of path elements is lower that elements must be remove, instead a data folder is used");
		return sketchPath()+"/data";
	}  
}



// remove the path of your file
public String file_name(String s) {
	String file_name = "" ;
	String [] split_path = s.split("/") ;
	file_name = split_path[split_path.length -1] ;
	return file_name ;
}

/**
* work around extension
*/
public String extension(String filename) {
	if(filename != null) {
		filename = filename.toLowerCase();
		if(filename.contains(".")) {
			return filename.substring(filename.lastIndexOf(".") + 1, filename.length());
		} else {
			return null;
		} 
	} else {
		return null;
	}
}

public boolean extension_is(String path, String... data) {
	boolean is = false;
	if(data.length >= 1) {
		String extension_to_compare = extension(path.toLowerCase());
		if(extension_to_compare != null) {
			for(int i = 0 ; i < data.length ; i++) {
				if(extension_to_compare.equals(data[i].toLowerCase())) {
					is = true;
					break;
				} else {
					is = false;
				}
			}
		} else {
			printErr("method extension_is(): [",path.toLowerCase(),"] this path don't have any extension");
		}
	} else {
		printErr("method extension_is() need almost two components, the first is the path and the next is extension");
	}
	return is;
}
/**
Vec, iVec and bVec rope method
v 0.4.3
* Copyleft (c) 2018-2019
* @author @stanlepunk
* @see https://github.com/StanLepunK/Rope_framework
*/




/**
Addition
v 0.0.4
*/
/**
* return the resultat of vector addition
*/
public ivec2 iadd(ivec2 a, ivec2 b) {
  if(a == null || b == null) {
    return null ;
  } else {
    int x = a.x() + b.x();
    int y = a.y() + b.y();
    return new ivec2(x,y) ;
  }
}

public ivec3 iadd(ivec3 a, ivec3 b) {
  if(a == null || b == null) {
    return null ;
  } else {
    int x = a.x() + b.x() ;
    int y = a.y() + b.y() ;
    int z = a.z() + b.z() ;
    return new ivec3(x,y,z)  ;
  }
}

public ivec4 iadd(ivec4 a, ivec4 b) {  
  if(a == null || b == null) {
    return null ;
  } else {
    int x = a.x() + b.x() ;
    int y = a.y() + b.y() ;
    int z = a.z() + b.z() ;
    int w = a.w() + b.w() ;
    return new ivec4(x,y,z,w)  ;
  }
}

public ivec2 iadd(ivec2 a, int arg) {
  return iadd(a,ivec2(arg,arg));
}

public ivec3 iadd(ivec3 a, int arg) {
  return iadd(a,ivec3(arg,arg,arg));
}

public ivec4 iadd(ivec4 a, int arg) {  
  return iadd(a,ivec4(arg,arg,arg,arg));
}




/**
Multiplication
v 0.0.1
*/
/**
* return the resultat of vector multiplication
*/
public ivec2 imult(ivec2 a, ivec2 b) {
  if(a == null || b == null) {
    return null ;
  } else {
    int x = a.x() * b.x();
    int y = a.y() * b.y();
    return new ivec2(x,y);
  }
}

public ivec3 imult(ivec3 a, ivec3 b) {
  if(a == null || b == null) {
    return null ;
  } else {
    int x = a.x() * b.x();
    int y = a.y() * b.y();
    int z = a.z() * b.z();
    return new ivec3(x,y,z);
  }
}

public ivec4 imult(ivec4 a, ivec4 b) {
  if(a == null || b == null) {
    return null ;
  } else {
    int x = a.x() * b.x();
    int y = a.y() * b.y();
    int z = a.z() * b.z();
    int w = a.w() * b.w();
    return new ivec4(x,y,z,w);
  }
}

public ivec2 imult(ivec2 a, int arg) {
  return imult(a,ivec2(arg,arg));
}

public ivec3 imult(ivec3 a, int arg) {
  return imult(a,ivec3(arg,arg,arg));
}

public ivec4 imult(ivec4 a, int arg) {  
  return imult(a,ivec4(arg,arg,arg,arg));
}


/**
Division
v 0.0.3
*/
/**
* return the resultat of vector division
*/
public ivec2 idiv(ivec2 a, ivec2 b) {
  if(a == null || b == null) {
    return null;
  } else {
    int x = a.x() / b.x();
    int y = a.y() / b.y();
    return new ivec2(x,y);
  }
}

public ivec3 idiv(ivec3 a, ivec3 b) {
  if(a == null || b == null) {
    return null;
  } else {
    int x = a.x() / b.x();
    int y = a.y() / b.y();
    int z = a.z() / b.z();
    return new ivec3(x,y,z);
  }
}

public ivec4 idiv(ivec4 a, ivec4 b) {
  if(a == null || b == null) {
    return null ;
  } else {
    int x = a.x() / b.x();
    int y = a.y() / b.y();
    int z = a.z() / b.z();
    int w = a.w() / b.w();
    return new ivec4(x,y,z,w);
  }
}

public ivec2 idiv(ivec2 a, int arg) {
  return idiv(a,ivec2(arg,arg));
}

public ivec3 idiv(ivec3 a, int arg) {
  return idiv(a,ivec3(arg,arg,arg));
}

public ivec4 idiv(ivec4 a, int arg) {  
  return idiv(a,ivec4(arg,arg,arg,arg));
}



/**
Substraction
v 0.0.1
*/
/**
* return the resultat of vector substraction
*/
public ivec2 isub(ivec2 a, ivec2 b) {
  if(a == null || b == null) {
    return null ;
  } else {
    int x = a.x() - b.x();
    int y = a.y() - b.y();
    return new ivec2(x,y);
  }
}

public ivec3 isub(ivec3 a, ivec3 b) {
  if(a == null || b == null) {
    return null ;
  } else {
    int x = a.x() - b.x();
    int y = a.y() - b.y();
    int z = a.z() - b.z();
    return new ivec3(x,y,z);
  }
}

public ivec4 isub(ivec4 a, ivec4 b) {
  if(a == null || b == null) {
    return null ;
  } else {
    int x = a.x() - b.x();
    int y = a.y() - b.y();
    int z = a.z() - b.z();
    int w = a.w() - b.w();
    return new ivec4(x,y,z,w);
  }
}

public ivec2 isub(ivec2 a, int arg) {
  return isub(a,ivec2(arg,arg));
}

public ivec3 isub(ivec3 a, int arg) {
  return isub(a,ivec3(arg,arg,arg));
}

public ivec4 isub(ivec4 a, int arg) {  
  return isub(a,ivec4(arg,arg,arg,arg));
}




































/**
METHOD
Vec
v 1.0.0
*/
/**
Addition
v 0.0.4
*/
/**
* return the resultat of vector addition
*/
public vec2 add(vec2 a, vec2 b) {
  if(a == null || b == null) {
    return null ;
  } else {
    float x = a.x() + b.x();
    float y = a.y() + b.y();
    return new vec2(x,y);
  }
}

public vec3 add(vec3 a, vec3 b) {
  if(a == null || b == null) {
    return null ;
  } else {
    float x = a.x() + b.x();
    float y = a.y() + b.y();
    float z = a.z() + b.z();
    return new vec3(x,y,z);
  }
}

public vec4 add(vec4 a, vec4 b) {  
  if(a == null || b == null) {
    return null ;
  } else {
    float x = a.x() + b.x();
    float y = a.y() + b.y();
    float z = a.z() + b.z();
    float w = a.w() + b.w();
    return new vec4(x,y,z,w);
  }
}
/**
* iVec arg
*/
public vec2 add(ivec2 a, ivec2 b) {
  return add(vec2(a),vec2(b));
}

public vec3 add(ivec3 a, ivec3 b) {
  return add(vec3(a),vec3(b));
}

public vec4 add(ivec4 a, ivec4 b) {  
  return add(vec4(a),vec4(b));
}
/**
* float arg
*/
public vec2 add(vec2 a, float arg) {
  return add(a,vec2(arg,arg));
}

public vec3 add(vec3 a, float arg) {
  return add(a,vec3(arg,arg,arg));
}

public vec4 add(vec4 a, float arg) {  
  return add(a,vec4(arg,arg,arg,arg));
}
/**
* iVec + float
*/

public vec2 add(ivec2 a, float arg) {
  return add(vec2(a),vec2(arg,arg));
}

public vec3 add(ivec3 a, float arg) {
  return add(vec3(a),vec3(arg,arg,arg));
}

public vec4 add(ivec4 a, float arg) {  
  return add(vec4(a),vec4(arg,arg,arg,arg));
}




/**
Multiplication
v 0.0.4
*/
/**
* return the resultat of vector multiplication
*/
public vec2 mult(vec2 a, vec2 b) {
  if(a == null || b == null) {
    return null ;
  } else {
    float x = a.x() * b.x();
    float y = a.y() * b.y();
    return new vec2(x,y);
  }
}

public vec3 mult(vec3 a, vec3 b) {
  if(a == null || b == null) {
    return null ;
  } else {
    float x = a.x() * b.x();
    float y = a.y() * b.y();
    float z = a.z() * b.z();
    return new vec3(x,y,z);
  }
}

public vec4 mult(vec4 a, vec4 b) {
  if(a == null || b == null) {
    return null ;
  } else {
    float x = a.x() * b.x();
    float y = a.y() * b.y();
    float z = a.z() * b.z();
    float w = a.w() * b.w();
    return new vec4(x,y,z,w);
  }
}
/**
* iVec arg
*/
public vec2 mult(ivec2 a, ivec2 b) {
  return mult(vec2(a),vec2(b));
}

public vec3 mult(ivec3 a, ivec3 b) {
  return mult(vec3(a),vec3(b));
}

public vec4 mult(ivec4 a, ivec4 b) {  
  return mult(vec4(a),vec4(b));
}

/**
* float arg
*/
public vec2 mult(vec2 a, float arg) {
  return mult(a,vec2(arg,arg));
}

public vec3 mult(vec3 a, float arg) {
  return mult(a,vec3(arg,arg,arg));
}

public vec4 mult(vec4 a, float arg) {  
  return mult(a,vec4(arg,arg,arg,arg));
}
/**
* iVec + float
*/
public vec2 mult(ivec2 a, float arg) {
  return mult(vec2(a),vec2(arg,arg));
}

public vec3 mult(ivec3 a, float arg) {
  return mult(vec3(a),vec3(arg,arg,arg));
}

public vec4 mult(ivec4 a, float arg) {  
  return mult(vec4(a),vec4(arg,arg,arg,arg));
}




/**
Division
v 0.0.4
*/
/**
* return the resultat of vector division
*/
public vec2 div(vec2 a, vec2 b) {
  if(a == null || b == null) {
    return null;
  } else {
    float x = a.x() /b.x();
    float y = a.y() /b.y();
    return new vec2(x,y);
  }
}

public vec3 div(vec3 a, vec3 b) {
  if(a == null || b == null) {
    return null;
  } else {
    float x = a.x() /b.x();
    float y = a.y() /b.y();
    float z = a.z() /b.z();
    return new vec3(x,y,z);
  }
}

public vec4 div(vec4 a, vec4 b) {
  if(a == null || b == null) {
    return null ;
  } else {
    float x = a.x() /b.x();
    float y = a.y() /b.y();
    float z = a.z() /b.z();
    float w = a.w() /b.w();
    return new vec4(x,y,z,w);
  }
}
/**
* iVec arg
*/
public vec2 div(ivec2 a, ivec2 b) {
  return div(vec2(a),vec2(b));
}

public vec3 div(ivec3 a, ivec3 b) {
  return div(vec3(a),vec3(b));
}

public vec4 div(ivec4 a, ivec4 b) {  
  return div(vec4(a),vec4(b));
}
/**
* float arg
*/
public vec2 div(vec2 a, float arg) {
  return div(a,vec2(arg,arg));
}

public vec3 div(vec3 a, float arg) {
  return div(a,vec3(arg,arg,arg));
}

public vec4 div(vec4 a, float arg) {  
  return div(a,vec4(arg,arg,arg,arg));
}
/**
* iVec + float
*/
public vec2 div(ivec2 a, float arg) {
  return div(vec2(a),vec2(arg,arg));
}

public vec3 div(ivec3 a, float arg) {
  return div(vec3(a),vec3(arg,arg,arg));
}

public vec4 div(ivec4 a, float arg) {  
  return div(vec4(a),vec4(arg,arg,arg,arg));
}


/**
Substraction
v 0.0.5
*/
/**
* return the resultat of vector substraction
*/
public vec2 sub(vec2 a, vec2 b) {
  if(a == null || b == null) {
    return null ;
  } else {
    float x = a.x() - b.x();
    float y = a.y() - b.y();
    return new vec2(x,y);
  }
}

public vec3 sub(vec3 a, vec3 b) {
  if(a == null || b == null) {
    return null ;
  } else {
    float x = a.x() - b.x();
    float y = a.y() - b.y();
    float z = a.z() - b.z();
    return new vec3(x,y,z);
  }
}

public vec4 sub(vec4 a, vec4 b) {
  if(a == null || b == null) {
    return null ;
  } else {
    float x = a.x() - b.x();
    float y = a.y() - b.y();
    float z = a.z() - b.z();
    float w = a.w() - b.w();
    return new vec4(x,y,z,w);
  }
}
/**
* iVec arg
*/
public vec2 sub(ivec2 a, ivec2 b) {
  return sub(vec2(a),vec2(b));
}

public vec3 sub(ivec3 a, ivec3 b) {
  return sub(vec3(a),vec3(b));
}

public vec4 sub(ivec4 a, ivec4 b) {  
  return sub(vec4(a),vec4(b));
}
/**
* float arg
*/
public vec2 sub(vec2 a, float arg) {
  return sub(a,vec2(arg,arg));
}

public vec3 sub(vec3 a, float arg) {
  return sub(a,vec3(arg,arg,arg));
}

public vec4 sub(vec4 a, float arg) {  
  return sub(a,vec4(arg,arg,arg,arg));
}
/**
* iVec + float
*/
public vec2 sub(ivec2 a, float arg) {
  return sub(vec2(a),vec2(arg,arg));
}

public vec3 sub(ivec3 a, float arg) {
  return sub(vec3(a),vec3(arg,arg,arg));
}

public vec4 sub(ivec4 a, float arg) {  
  return sub(vec4(a),vec4(arg,arg,arg,arg));
}



/**
Cross
v 0.0.2
*/
public vec3 cross(vec3 v1, vec3 v2) {
  if(v1 == null ||  v2 == null) {
    return null;
  } else {
    float crossX = v1.y() * v2.z() - v2.y() * v1.z();
    float crossY = v1.z() * v2.x() - v2.z() * v1.x();
    float crossZ = v1.x() * v2.y() - v2.x() * v1.y();
    return vec3(crossX, crossY, crossZ);
  }
}
/**
* @deprecated "cross(vec3 v1, vec3 v2, vec3 target), can be deprecated in the future, need to be test"
*/
public @Deprecated
vec3 cross(vec3 v1, vec3 v2, vec3 target) {
  println("cross(vec3 v1, vec3 v2, vec3 target), can be deprecated in the future, need to be test");
  if(v1 == null ||  v2 == null || target == null) {
    return null ;
  } else {
    float crossX = v1.y() * v2.z() - v2.y() * v1.z();
    float crossY = v1.z() * v2.x() - v2.z() * v1.x();
    float crossZ = v1.x() * v2.y() - v2.x() * v1.y();

    if (target == null) {
      target = vec3(crossX, crossY, crossZ);
    } else {
      target.set(crossX, crossY, crossZ);
    }
    return target ;
  }  
}



/** 
* compare if the first vector is in the area of the second vector, 
* the area of the second vector is define by a Vec area, 
* that give the possibility of different size for each component
* @return boolean
* v 0.4.0
*/

public boolean compare(ivec2 a, ivec2 b) {
  return compare(vec4(a.array()),vec4(b.array()));
}

public boolean compare(ivec3 a, ivec3 b) {
  return compare(vec4(a.array()),vec4(b.array()));
}

public boolean compare(ivec4 a, ivec4 b) {
  return compare(vec4(a.array()),vec4(b.array()));
}


public boolean compare(vec2 a, vec2 b) {
  if(a == null || b == null) {
    println("Is not possible to compare", a, "to", b) ;
    return false ;
  } else {
    return compare(vec4(a.array()),vec4(b.array())) ;
  }
}

// vec3 compare
public boolean compare(vec3 a, vec3 b) {
    if(a == null || b == null) {
    println("Is not possible to compare", a, "to", b) ;
    return false ;
  } else {
    return compare(vec4(a.array()),vec4(b.array())) ;
  }
}
// vec4 compare
public boolean compare(vec4 a, vec4 b) {
  if(a != null && b != null ) {
    if((a.x() == b.x()) && (a.y() == b.y()) && (a.z() == b.z()) && (a.w() == b.w())) {
      return true ; 
    } else {
      return false ;
    }
  } else {
    return false ;
  } 
}


/**
* compare with area
*/
public boolean compare(ivec2 a, ivec2 b, ivec2 area) {
  return compare(vec2(a.array()),vec2(b.array()),vec2(area.array()));
}

public boolean compare(ivec3 a, ivec3 b, ivec3 area) {
  return compare(vec3(a.array()),vec3(b.array()),vec3(area.array()));
}

public boolean compare(ivec4 a, ivec4 b, ivec4 area) {
  return compare(vec4(a.array()),vec4(b.array()),vec4(area.array()));
}

public boolean compare(vec2 a, vec2 b, vec2 area) {
  if(a == null || b == null || area == null) {
    println("Is not possible to compare", a, "with", b, "with", area) ;
    return false ;
  } else {
    return compare(vec4(a.array()),vec4(b.array()),vec4(area.array())) ;
  }
}

public boolean compare(vec3 a, vec3 b, vec3 area) {
    if(a == null || b == null || area == null) {
    println("Is not possible to compare", a, "with", b, "with", area) ;
    return false ;
  } else {
    return compare(vec4(a.array()),vec4(b.array()),vec4(area.array())) ;
  }
}

public boolean compare(vec4 a, vec4 b, vec4 area) {
  if(a != null && b != null && area != null ) {
    if(    (a.x() >= b.x() -area.x() && a.x() <= b.x() +area.x()) 
        && (a.y() >= b.y() -area.y() && a.y() <= b.y() +area.y()) 
        && (a.z() >= b.z() -area.z() && a.z() <= b.z() +area.z()) 
        && (a.w() >= b.w() -area.w() && a.w() <= b.w() +area.w())) {
            return true ; 
    } else {
      return false ;
    }
  } else {
    return false ;
  }
}










/**
Map
*/
/**
* return mapping vector
* @return Vec
*/
public vec2 map(vec2 v,float minIn, float maxIn, float minOut, float maxOut) {
  if(v != null) {
    float x = map(v.x(), minIn, maxIn, minOut, maxOut) ;
    float y = map(v.y(), minIn, maxIn, minOut, maxOut) ;
    return new vec2(x,y) ;
  } else return null ;
}


public vec3 map(vec3 v,float minIn, float maxIn, float minOut, float maxOut) {
  if(v != null) {
    float x = map(v.x(), minIn, maxIn, minOut, maxOut) ;
    float y = map(v.y(), minIn, maxIn, minOut, maxOut) ;
    float z = map(v.z(), minIn, maxIn, minOut, maxOut) ;
    return new vec3(x,y,z) ;
  } else return null ;
}


public vec4 map(vec4 v,float minIn, float maxIn, float minOut, float maxOut) {
  if(v != null) {
    float x = map(v.x(), minIn, maxIn, minOut, maxOut) ;
    float y = map(v.y(), minIn, maxIn, minOut, maxOut) ;
    float z = map(v.z(), minIn, maxIn, minOut, maxOut) ;
    float w = map(v.w(), minIn, maxIn, minOut, maxOut) ;
    return new vec4(x,y,z,w) ;
  } else return null ;
}


/**
Magnitude or length
*/
/**
* @return float
*/
// mag vec2
public float mag(vec2 a) {
  float x = a.x()*a.x();
  float y = a.y()*a.y();
  return sqrt(x+y) ;
}

public float mag(vec2 a, vec2 b) {
  // same result than dist
  float x = (b.x()-a.x())*(b.x()-a.x()) ;
  float y = (b.y()-a.y())*(b.y()-a.y()) ;
  return sqrt(x+y) ;
}
// mag vec3
public float mag(vec3 a) {
  float x = a.x()*a.x();
  float y = a.y()*a.y();
  float z = a.z()*a.z();
  return sqrt(x+y+z) ;
}

public float mag(vec3 a, vec3 b) {
  // same result than dist
  float x = (b.x()-a.x())*(b.x()-a.x()) ;
  float y = (b.y()-a.y())*(b.y()-a.y()) ;
  float z = (b.z()-a.z())*(b.z()-a.z()) ;
  return sqrt(x+y+z) ;
}
// mag vec4
public float mag(vec4 a) {
  float x = a.x()*a.x();
  float y = a.y()*a.y();
  float z = a.z()*a.z();
  float w = a.w()*a.w();
  return sqrt(x+y+z+w) ;
}

public float mag(vec4 a, vec4 b) {
  // same result than dist
  float x = (b.x()-a.x())*(b.x()-a.x()) ;
  float y = (b.y()-a.y())*(b.y()-a.y()) ;
  float z = (b.z()-a.z())*(b.z()-a.z()) ;
  float w = (b.w()-a.w())*(b.w()-a.w()) ;
  return sqrt(x+y+z+w) ;
}



/**
Distance
v 0.0.2
*/
/**
* return the distance beatwen two vectors
* @return float
*/
public float dist(vec2 a, vec2 b) {
  if(a != null && b != null) {
    float dx = a.x() - b.x();
    float dy = a.y() - b.y();
    return (float) Math.sqrt(dx*dx + dy*dy);
  } else return Float.NaN ;
    
}
public float dist(vec3 a, vec3 b) {
  if(a != null && b != null) {
    float dx = a.x() - b.x();
    float dy = a.y() - b.y();
    float dz = a.z() - b.z();
    return (float) Math.sqrt(dx*dx + dy*dy + dz*dz);
  } else return Float.NaN ;
}

public float dist(vec4 a, vec4 b) {
  if(a != null && b != null) {
    float dx = a.x() - b.x();
    float dy = a.y() - b.y();
    float dz = a.z() - b.z();
    float dw = a.w() - b.w();
    return (float) Math.sqrt(dx*dx + dy*dy + dz*dz + dw*dw);
  } else return Float.NaN ;
}


/**
Deprecated Middle
*/
/**
* return the middle between two Vector
* @return Vec
*/
public vec2 middle(vec2 a, vec2 b)  {
  vec2 middle ;
  middle = add(a,b);
  middle.div(2) ;
  println("The method middle is deprecated instead use barycenter(Vec... arg)") ;
  return middle ;
}

public vec2 middle(vec2 [] list)  {
  vec2 middle = vec2() ;
  for (int i = 0 ; i < list.length ; i++) {
    middle.add(list[i]);
  }
  middle.div(list.length) ;
  println("The method middle is deprecated instead use barycenter(Vec... arg)") ;
  return middle ;
}

public vec3 middle(vec3 a, vec3 b) {
  vec3 middle ;
  middle = add(a,b);
  middle.div(2) ;
  return middle ;
}

public vec3 middle(vec3 [] list)  {
  vec3 middle = vec3() ;
  for (int i = 0 ; i < list.length ; i++) {
    middle.add(list[i]);
  }
  middle.div(list.length) ;
  println("The method middle is deprecated instead use barycenter(Vec... arg)") ;
  return middle ;
}

public vec4 middle(vec4 a, vec4 b)  {
  vec4 middle ;
  middle = add(a,b);
  middle.div(2) ;
  println("The method middle is deprecated instead use barycenter(Vec... arg)") ;
  return middle ;
}

public vec4 middle(vec4 [] list)  {
  vec4 middle = vec4() ;
  for (int i = 0 ; i < list.length ; i++) {
    middle.add(list[i]);
  }
  middle.div(list.length) ;
  println("The method middle is deprecated instead use barycenter(Vec... arg)") ;
  return middle ;
}


/**
barycenter
*/
public vec2 barycenter(vec2... v) {
  int div_num = v.length;
  vec2 sum = vec2();
  for(int i = 0 ; i < div_num ; i++) {
    sum.add(v[i]);
  }
  return sum.div(div_num);
}

 
public vec3 barycenter(vec3... v) {
  int div_num = v.length ;
  vec3 sum = vec3();
  for(int i = 0 ; i < div_num ; i++) {
    sum.add(v[i]);
  }
  return sum.div(div_num) ;
}

public vec4 barycenter(vec4... v) {
  int div_num = v.length;
  vec4 sum = vec4();
  for(int i = 0 ; i < div_num ; i++) {
    sum.add(v[i]) ;
  }
  return sum.div(div_num);
}





/**
Jitter
v 0.0.2
*/
// vec2
public vec2 jitter_2D(int range) {
  return jitter_2D(range, range);
}
public vec2 jitter_2D(vec2 range) {
  return jitter_2D((int)range.x(), (int)range.y()) ;
}
public vec2 jitter_2D(int range_x, int range_y) {
  vec2 jitter = vec2() ;
  jitter.x(random_next_gaussian(range_x, 2));
  jitter.y(random_next_gaussian(range_y, 2));
  return jitter ;
}
// vec3
public vec3 jitter_3D(int range) {
  return jitter_3D(range, range, range) ;
}
public vec3 jitter_3D(vec3 range) {
  return jitter_3D((int)range.x, (int)range.y, (int)range.z) ;
}
public vec3 jitter_3D(int range_x, int range_y, int range_z) {
  vec3 jitter = vec3() ;
  jitter.x(random_next_gaussian(range_x, 2));
  jitter.y(random_next_gaussian(range_y, 2));
  jitter.z(random_next_gaussian(range_z, 2));
  return jitter ;
}
// vec4
public vec4 jitter_4D(int range) {
  return jitter_4D(range, range, range, range) ;
}
public vec4 jitter_4D(vec4 range) {
  return jitter_4D((int)range.x, (int)range.y, (int)range.z, (int)range.w) ;
}
public vec4 jitter_4D(int range_x, int range_y, int range_z, int range_w) {
  vec4 jitter = vec4() ;
  jitter.x(random_next_gaussian(range_x, 2));
  jitter.y(random_next_gaussian(range_y, 2));
  jitter.z(random_next_gaussian(range_z, 2));
  jitter.w(random_next_gaussian(range_w, 2));
  return jitter ;
}






/**
Normalize
*/
// VEC 2 from angle
public vec2 norm_rad(float angle) {
  float x = (float)Math.cos(angle) ;
  float y = (float)Math.sin(angle) ;
  return vec2(x,y) ;
}

public vec2 norm_deg(float angle) {
  angle = radians(angle) ;
  float x = (float)Math.cos(angle) ;
  float y = (float)Math.sin(angle) ;
  return vec2(x,y) ;
}


// normalize direction
public vec2 norm_dir(String type, float direction) {
  float x, y = 0 ;
  if(type.equals("DEG")) {
    float angle = TWO_PI/360.f;
    direction = 360-direction;
    direction += 180;
    x = sin(angle *direction) ;
    y = cos(angle *direction);
  } else if (type.equals("RAD")) {
    x = sin(direction) ;
    y = cos(direction);
  } else {
    println("the type must be 'RAD' for radians or 'DEG' for degrees") ;
    x = 0 ;
    y = 0 ;
  }
  return new vec2(x,y) ;
}





























/**
New Vec, iVec and bVec
v 0.0.3
*/

/**
Return a new bVec
*/
/**
* @return bVec
*/
/**
bvec2
*/
public bvec2 bvec2() {
  return new bvec2(false,false) ;
}

public bvec2 bvec2(boolean b) {
  return new bvec2(b,b);
}

public bvec2 bvec2(boolean x, boolean y) { 
  return new bvec2(x,y) ;
}

public bvec2 bvec2(boolean [] array) {
  if(array.length == 1) {
    return new bvec2(array[0],array[0]);
  } else if (array.length > 1) {
    return new bvec2(array[0],array[1]);
  } else {
    return null;
  }
}

public bvec2 bvec2(bvec b) {
  if(b == null) {
    println("bVec null, instead 'false' is used to build bVec") ;
    return new bvec2(false,false) ;
  } else if(b instanceof bvec5 || b instanceof bvec6) {
    return new bvec2(b.a(),b.b()) ;
  } else {
    return new bvec2(b.x(),b.y()) ;
  }
}

/**
ivec3
*/
public bvec3 bvec3() {
  return new bvec3(false,false,false) ;
}

public bvec3 bvec3(boolean b) {
  return new bvec3(b,b,b);
}

public bvec3 bvec3(boolean x, boolean y, boolean z) { 
  return new bvec3(x,y,z) ;
}

public bvec3 bvec3(boolean [] array) {
  if(array.length == 1) {
    return new bvec3(array[0],array[0],array[0]);
  } else if (array.length == 2) {
    return new bvec3(array[0],array[1],false);
  } else if (array.length > 2) {
    return new bvec3(array[0],array[1],array[2]);
  } else {
    return null;
  }
}

public bvec3 bvec3(bvec b) {
  if(b == null) {
    println("bVec null, instead 'false' is used to build bVec") ;
    return new bvec3(false,false,false) ;
  } else {
    return new bvec3(b.x(),b.y(),b.z()) ;
  }
}

/**
ivec4
*/
public bvec4 bvec4() {
  return new bvec4(false,false,false,false) ;
}

public bvec4 bvec4(boolean b) {
  return new bvec4(b,b,b,b);
}

public bvec4 bvec4(boolean x, boolean y, boolean z, boolean w) { 
  return new bvec4(x,y,z,w) ;
}

public bvec4 bvec4(boolean [] array) {
  if(array.length == 1) {
    return new bvec4(array[0],array[0],array[0],array[0]);
  } else if (array.length == 2) {
    return new bvec4(array[0],array[1],false,false);
  } else if (array.length == 3) {
    return new bvec4(array[0],array[1],array[2],false);
  } else if (array.length > 3) {
    return new bvec4(array[0],array[1],array[2],array[3]);
  } else {
    return null;
  }
}

public bvec4 bvec4(bvec b) {
  if(b == null) {
    println("bVec null, instead 'false' is used to build bVec") ;
    return new bvec4(false,false,false,false) ;
  } else {
    return new bvec4(b.x(),b.y(),b.z(),b.w()) ;
  }
}

/**
ivec5
*/
public bvec5 bvec5() {
  return new bvec5(false,false,false,false,false) ;
}

public bvec5 bvec5(boolean b) {
  return new bvec5(b,b,b,b,b);
}

public bvec5 bvec5(boolean a, boolean b, boolean c, boolean d, boolean e) { 
  return new bvec5(a,b,c,d,e) ;
}

public bvec5 bvec5(boolean [] array) {
  if(array.length == 1) {
    return new bvec5(array[0],array[0],array[0],array[0],array[0]);
  } else if (array.length == 2) {
    return new bvec5(array[0],array[1],false,false,false);
  } else if (array.length == 3) {
    return new bvec5(array[0],array[1],array[2],false,false);
  } else if (array.length == 4) {
    return new bvec5(array[0],array[1],array[2],array[3],false);
  } else if (array.length >4) {
    return new bvec5(array[0],array[1],array[2],array[3],array[4]);
  } else {
    return null;
  }
}

public bvec5 bvec5(bvec b) {
  if(b == null) {
    println("bVec null, instead 'false' is used to build bVec") ;
    return new bvec5(false,false,false,false,false) ;
  } else if(b instanceof bvec5 || b instanceof bvec6) {
    return new bvec5(b.a(),b.b(),b.c(),b.d(),b.e()) ;
  } else {
    return new bvec5(b.x(),b.y(),b.z(),b.w(),false) ;
  }
}

/**
bvec6
*/
public bvec6 bvec6() {
  return new bvec6(false,false,false,false,false,false) ;
}

public bvec6 bvec6(boolean b) {
  return new bvec6(b,b,b,b,b,b);
}

public bvec6 bvec6(boolean a, boolean b, boolean c, boolean d, boolean e, boolean f) { 
  return new bvec6(a,b,c,d,e,f) ;
}

public bvec6 bvec6(boolean [] array) {
  if(array.length == 1) {
    return new bvec6(array[0],array[0],array[0],array[0],array[0],array[0]);
  } else if (array.length == 2) {
    return new bvec6(array[0],array[1],false,false,false,false);
  } else if (array.length == 3) {
    return new bvec6(array[0],array[1],array[2],false,false,false);
  } else if (array.length == 4) {
    return new bvec6(array[0],array[1],array[2],array[3],false,false);
  } else if (array.length == 5) {
    return new bvec6(array[0],array[1],array[2],array[3],array[4],false);
  }  else if (array.length > 5) {
    return new bvec6(array[0],array[1],array[2],array[3],array[4],array[5]);
  } else {
    return null;
  }
}

public bvec6 bvec6(bvec b) {
  if(b== null) {
    println("bVec null, instead 'false' is used to build bVec") ;
    return new bvec6(false,false,false,false,false,false) ;
  } else if(b instanceof bvec5 || b instanceof bvec6) {
    return new bvec6(b.a(),b.b(),b.c(),b.d(),b.e(),b.f()) ;
  } else {
    return new bvec6(b.x(),b.y(),b.z(),b.w(),false,false) ;
  }
}

























/**
Return a new iVec
*/
/**
ivec2
*/
public ivec2 ivec2() {
  return ivec2(0) ;
}

public ivec2 ivec2(int v) {
  return new ivec2(v,v);
}

public ivec2 ivec2(int x, int y) { 
  return new ivec2(x,y) ;
}


public ivec2 ivec2(int [] array) {
  if(array.length == 1) {
    return new ivec2(array[0],array[0]);
  } else if (array.length > 1) {
    return new ivec2(array[0],array[1]);
  } else {
    return null;
  }
}

public ivec2 ivec2(ivec p) {
  if(p == null) {
    println("iVec null, instead '0' is used to build iVec") ;
    return new ivec2(0,0) ;
  } else {
    return new ivec2(p.x(),p.y()) ;
  }
}

public ivec2 ivec2(float v) {
  return new ivec2(PApplet.parseInt(v),PApplet.parseInt(v));
}

public ivec2 ivec2(float x, float y) { 
  return new ivec2(PApplet.parseInt(x),PApplet.parseInt(y));
}

public ivec2 ivec2(float [] array) {
  if(array.length == 1) {
    return new ivec2(PApplet.parseInt(array[0]),PApplet.parseInt(array[0]));
  } else if (array.length > 1) {
    return new ivec2(PApplet.parseInt(array[0]),PApplet.parseInt(array[1]));
  } else {
    return null;
  }
}

public ivec2 ivec2(vec p) {
  if(p == null) {
    println("Vec null, instead '0' is used to build iVec") ;
    return new ivec2(0,0) ;
  } else {
    return new ivec2(PApplet.parseInt(p.x()),PApplet.parseInt(p.y()));
  }
}


public ivec2 ivec2(PGraphics media) {
  if(media != null) {
    return new ivec2(media.width,media.height);
  } else {
    return null;
  }
}

public ivec2 ivec2(PImage media) {
  if(media != null) {
    return new ivec2(media.width,media.height);
  } else {
    return null;
  }
}

/**
ivec3
*/
public ivec3 ivec3() {
  return ivec3(0) ;
}

public ivec3 ivec3(int v) {
  return new ivec3(v,v,v);
}

public ivec3 ivec3(int x, int y, int z) { 
  return new ivec3(x,y,z) ;
}

public ivec3 ivec3(int [] array) {
  if(array.length == 1) {
    return new ivec3(array[0],array[0],array[0]);
  } else if (array.length == 2) {
    return new ivec3(array[0],array[1],0);
  } else if (array.length > 2) {
    return new ivec3(array[0],array[1],array[2]);
  } else {
    return null;
  }
}

public ivec3 ivec3(ivec p) {
  if(p == null) {
    println("iVec null, instead '0' is used to build iVec") ;
    return new ivec3(0,0,0) ;
  } else {
    return new ivec3(p.x(),p.y(),p.z()) ;
  }
}

public ivec3 ivec3(float v) {
  return new ivec3(PApplet.parseInt(v),PApplet.parseInt(v),PApplet.parseInt(v));
}

public ivec3 ivec3(float x, float y,float z) { 
  return new ivec3(PApplet.parseInt(x),PApplet.parseInt(y),PApplet.parseInt(z));
}

public ivec3 ivec3(float [] array) {
  if(array.length == 1) {
    return new ivec3(PApplet.parseInt(array[0]),PApplet.parseInt(array[0]),PApplet.parseInt(array[0]));
  } else if (array.length == 2) {
    return new ivec3(PApplet.parseInt(array[0]),PApplet.parseInt(array[1]),0);
  } else if (array.length > 2) {
    return new ivec3(PApplet.parseInt(array[0]),PApplet.parseInt(array[1]),PApplet.parseInt(array[2]));
  } else {
    return null;
  }
}

public ivec3 ivec3(vec p) {
  if(p == null) {
    println("Vec null, instead '0' is used to build iVec") ;
    return new ivec3(0,0,0) ;
  } else {
    return new ivec3(PApplet.parseInt(p.x()),PApplet.parseInt(p.y()),PApplet.parseInt(p.z()));
  }
}

/**
ivec4
*/
public ivec4 ivec4() {
  return ivec4(0) ;
}

public ivec4 ivec4(int v) {
  return new ivec4(v,v,v,v);
}

public ivec4 ivec4(int x, int y, int z, int w) { 
  return new ivec4(x,y,z,w) ;
}

public ivec4 ivec4(int [] array) {
  if(array.length == 1) {
    return new ivec4(array[0],array[0],array[0],array[0]);
  } else if (array.length == 2) {
    return new ivec4(array[0],array[1],0,0);
  } else if (array.length == 3) {
    return new ivec4(array[0],array[1],array[2],0);
  } else if (array.length > 3) {
    return new ivec4(array[0],array[1],array[2],array[3]);
  } else {
    return null;
  }
}

public ivec4 ivec4(ivec p) {
  if(p == null) {
    println("iVec null, instead '0' is used to build iVec") ;
    return new ivec4(0,0,0,0) ;
  } else {
    return new ivec4(p.x(),p.y(),p.z(),p.w()) ;
  }
}

public ivec4 ivec4(float v) {
  return new ivec4(PApplet.parseInt(v),PApplet.parseInt(v),PApplet.parseInt(v),PApplet.parseInt(v));
}

public ivec4 ivec4(float x, float y, float z, float w) { 
  return new ivec4(PApplet.parseInt(x),PApplet.parseInt(y),PApplet.parseInt(z),PApplet.parseInt(w));
}

public ivec4 ivec4(float [] array) {
  if(array.length == 1) {
    return new ivec4(PApplet.parseInt(array[0]),PApplet.parseInt(array[0]),PApplet.parseInt(array[0]),PApplet.parseInt(array[0]));
  } else if (array.length == 2) {
    return new ivec4(PApplet.parseInt(array[0]),PApplet.parseInt(array[1]),0,0);
  } else if (array.length == 3) {
    return new ivec4(PApplet.parseInt(array[0]),PApplet.parseInt(array[1]),PApplet.parseInt(array[2]),0);
  } else if (array.length > 3) {
    return new ivec4(PApplet.parseInt(array[0]),PApplet.parseInt(array[1]),PApplet.parseInt(array[2]),PApplet.parseInt(array[3]));
  } else {
    return null;
  }
}


public ivec4 ivec4(vec p) {
  if(p == null) {
    println("Vec null, instead '0' is used to build iVec") ;
    return new ivec4(0,0,0,0) ;
  } else {
    return new ivec4(PApplet.parseInt(p.x()),PApplet.parseInt(p.y()),PApplet.parseInt(p.z()),PApplet.parseInt(p.w()));
  }
}

/**
ivec5
*/
public ivec5 ivec5() {
  return ivec5(0) ;
}

public ivec5 ivec5(int v) {
  return new ivec5(v,v,v,v,v);
}

public ivec5 ivec5(int a, int b, int c, int d, int e) { 
  return new ivec5(a,b,c,d,e) ;
}

public ivec5 ivec5(int [] array) {
  if(array.length == 1) {
    return new ivec5(array[0],array[0],array[0],array[0],array[0]);
  } else if (array.length == 2) {
    return new ivec5(array[0],array[1],0,0,0);
  } else if (array.length == 3) {
    return new ivec5(array[0],array[1],array[2],0,0);
  } else if (array.length == 4) {
    return new ivec5(array[0],array[1],array[2],array[3],0);
  } else if (array.length > 4) {
    return new ivec5(array[0],array[1],array[2],array[3],array[4]);
  } else {
    return null;
  }
}

public ivec5 ivec5(ivec p) {
  if(p == null) {
    println("iVec null, instead '0' is used to build iVec") ;
    return new ivec5(0,0,0,0,0) ;
  } else if(p instanceof ivec5 || p instanceof ivec6) {
    return new ivec5(p.a(),p.b(),p.c(),p.d(),p.e()) ;
  } else {
    return new ivec5(p.x(),p.y(),p.z(),p.w(),0) ;
  }
}

public ivec5 ivec5(vec p) {
  if(p == null) {
    println("Vec null, instead '0' is used to build iVec") ;
    return new ivec5(0,0,0,0,0) ;
  } else if(p instanceof vec5 || p instanceof vec6) {
    return new ivec5(PApplet.parseInt(p.a()),PApplet.parseInt(p.b()),PApplet.parseInt(p.c()),PApplet.parseInt(p.d()),PApplet.parseInt(p.e()));
  } else {
    return new ivec5(PApplet.parseInt(p.x()),PApplet.parseInt(p.y()),PApplet.parseInt(p.z()),PApplet.parseInt(p.w()),0);
  }
}

/**
ivec6
*/
public ivec6 ivec6() {
  return ivec6(0) ;
}

public ivec6 ivec6(int v) {
  return new ivec6(v,v,v,v,v,v);
}

public ivec6 ivec6(int a, int b, int c, int d, int e, int f) { 
  return new ivec6(a,b,c,d,e,f) ;
}

public ivec6 ivec6(int [] array) {
  if(array.length == 1) {
    return new ivec6(array[0],array[0],array[0],array[0],array[0],array[0]);
  } else if (array.length == 2) {
    return new ivec6(array[0],array[1],0,0,0,0);
  } else if (array.length == 3) {
    return new ivec6(array[0],array[1],array[2],0,0,0);
  } else if (array.length == 4) {
    return new ivec6(array[0],array[1],array[2],array[3],0,0);
  } else if (array.length == 5) {
    return new ivec6(array[0],array[1],array[2],array[3],array[4],0);
  }  else if (array.length > 5) {
    return new ivec6(array[0],array[1],array[2],array[3],array[4],array[5]);
  } else {
    return null;
  }
}

public ivec6 ivec6(ivec p) {
  if(p == null) {
    println("iVec null, instead '0' is used to build iVec") ;
    return new ivec6(0,0,0,0,0,0) ;
  } else if(p instanceof ivec5 || p instanceof ivec6) {
    return new ivec6(p.a(),p.b(),p.c(),p.d(),p.e(),p.f()) ;
  } else {
    return new ivec6(p.x(),p.y(),p.z(),p.w(),0,0) ;
  }
}

public ivec6 ivec6(vec p) {
  if(p == null) {
    println("Vec null, instead '0' is used to build iVec") ;
    return new ivec6(0,0,0,0,0,0) ;
  } else if(p instanceof vec5 || p instanceof vec6) {
    return new ivec6(PApplet.parseInt(p.a()),PApplet.parseInt(p.b()),PApplet.parseInt(p.c()),PApplet.parseInt(p.d()),PApplet.parseInt(p.e()),PApplet.parseInt(p.f()));
  } else {
    return new ivec6(PApplet.parseInt(p.x()),PApplet.parseInt(p.y()),PApplet.parseInt(p.z()),PApplet.parseInt(p.w()),0,0);
  }
}
























/**
Return a new Vec
*/
/**
Vec 2
*/
public vec2 vec2() {
  return new vec2(0,0) ;
}

public vec2 vec2(float x, float y) { 
  return new vec2(x,y) ;
}

public vec2 vec2(float [] array) {
  if(array.length == 1) {
    return new vec2(array[0],array[0]);
  } else if (array.length > 1) {
    return new vec2(array[0],array[1]);
  } else {
    return null;
  }
}

public vec2 vec2(int [] array) {
  if(array.length == 1) {
    return new vec2(array[0],array[0]);
  } else if (array.length > 1) {
    return new vec2(array[0],array[1]);
  } else {
    return null;
  }
}

public vec2 vec2(float v) {
  return new vec2(v,v) ;
}

public vec2 vec2(PVector p) {
  if(p == null) {
    return new vec2(0,0);
  } else {
    return new vec2(p.x,p.y);
  }
}

public vec2 vec2(vec p) {
  if(p == null) {
    return new vec2(0,0);
  } else {
    return new vec2(p.x(),p.y());
  }
}


public vec2 vec2(ivec p) {
  if(p == null) {
    return new vec2(0,0);
  } else {
    return new vec2(p.x(),p.y());
  }
}




public vec2 vec2(PGraphics media) {
  if(media != null) {
    return new vec2(media.width,media.height);
  } else {
    return null;
  }
}

public vec2 vec2(PImage media) {
  if(media != null) {
    return new vec2(media.width,media.height);
  } else {
    return null;
  }
}
/**
Vec 3
*/
public vec3 vec3() {
  return new vec3(0,0,0) ;
}

public vec3 vec3(float x, float y, float z) {
  return new vec3(x,y,z);
}

public vec3 vec3(float [] array) {
  if(array.length == 1) {
    return new vec3(array[0],array[0],array[0]);
  } else if (array.length == 2) {
    return new vec3(array[0],array[1],0);
  } else if (array.length > 2) {
    return new vec3(array[0],array[1],array[2]);
  } else {
    return null;
  }
}

public vec3 vec3(int [] array) {
  if(array.length == 1) {
    return new vec3(array[0],array[0],array[0]);
  } else if (array.length == 2) {
    return new vec3(array[0],array[1],0);
  } else if (array.length > 2) {
    return new vec3(array[0],array[1],array[2]);
  } else {
    return null;
  }
}

public vec3 vec3(float v) {
  return new vec3(v,v,v);
}

public vec3 vec3(PVector p) {
  if(p == null) {
    return new vec3(0,0,0);
  } else {
    return new vec3(p.x, p.y, p.z);
  }
}

public vec3 vec3(vec p) {
  if(p == null) {
    return new vec3(0,0,0);
  } else {
    return new vec3(p.x(),p.y(),p.z());
  }
}

public vec3 vec3(ivec p) {
  if(p == null) {
    return new vec3(0,0,0);
  } else {
    return new vec3(p.x(),p.y(),p.z());
  }
}



/**
Vec 4
*/
public vec4 vec4() {
  return new vec4(0,0,0,0);
}

public vec4 vec4(float x, float y, float z, float w) {
  return new vec4(x,y,z,w);
}

public vec4 vec4(float [] array) {
  if(array.length == 1) {
    return new vec4(array[0],array[0],array[0],array[0]);
  } else if (array.length == 2) {
    return new vec4(array[0],array[1],0,0);
  } else if (array.length == 3) {
    return new vec4(array[0],array[1],array[2],0);
  } else if (array.length > 3) {
    return new vec4(array[0],array[1],array[2],array[3]);
  } else {
    return null;
  }
}

public vec4 vec4(int [] array) {
  if(array.length == 1) {
    return new vec4(array[0],array[0],array[0],array[0]);
  } else if (array.length == 2) {
    return new vec4(array[0],array[1],0,0);
  } else if (array.length == 3) {
    return new vec4(array[0],array[1],array[2],0);
  } else if (array.length > 3) {
    return new vec4(array[0],array[1],array[2],array[3]);
  } else {
    return null;
  }
}

public vec4 vec4(float v) {
  return new vec4(v,v,v,v);
}

public vec4 vec4(PVector p) {
  if(p == null) {
    return new vec4(0,0,0,0);
  } else {
    return new vec4(p.x, p.y, p.z, 0);
  }
}
// build with Vec
public vec4 vec4(vec p) {
  if(p == null) {
    return new vec4(0,0,0,0);
  } else {
    return new vec4(p.x(),p.y(),p.z(),p.w());
  }
}

public vec4 vec4(ivec p) {
  if(p == null) {
    return new vec4(0,0,0,0);
  } else {
    return new vec4(p.x(),p.y(),p.z(),p.w());
  }
}



/**
Vec 5
*/
public vec5 vec5() {
  return new vec5(0,0,0,0,0);
}

public vec5 vec5(float a, float b, float c, float d, float e) {
  return new vec5(a,b,c,d,e);
}

public vec5 vec5(float [] array) {
  if(array.length == 1) {
    return new vec5(array[0],array[0],array[0],array[0],array[0]);
  } else if (array.length == 2){
    return new vec5(array[0],array[1],0,0,0) ;
  } else if (array.length == 3){
    return new vec5(array[0],array[1],array[2],0,0) ;
  } else if (array.length == 4){
    return new vec5(array[0],array[1],array[2],array[3],0) ;
  }  else if (array.length > 4){
    return new vec5(array[0],array[1],array[2],array[3],array[4]) ;
  } else {
    return null;
  }
}

public vec5 vec5(int [] array) {
  if(array.length == 1) {
    return new vec5(array[0],array[0],array[0],array[0],array[0]);
  } else if (array.length == 2){
    return new vec5(array[0],array[1],0,0,0) ;
  } else if (array.length == 3){
    return new vec5(array[0],array[1],array[2],0,0) ;
  } else if (array.length == 4){
    return new vec5(array[0],array[1],array[2],array[3],0) ;
  }  else if (array.length > 4){
    return new vec5(array[0],array[1],array[2],array[3],array[4]) ;
  } else {
    return null;
  }
}

public vec5 vec5(float v) {
  return new vec5(v,v,v,v,v);
}

public vec5 vec5(PVector p) {
  if(p == null) {
    return new vec5(0,0,0,0,0);
  } else {
    return new vec5(p.x, p.y, p.z, 0,0);
  }
}
// build with Vec
public vec5 vec5(vec p) {
  if(p == null) {
    return new vec5(0,0,0,0,0);
  } else if(p instanceof vec5 || p instanceof vec6) {
    return new vec5(p.a(),p.b(),p.c(),p.d(),p.e());
  } else {
    return new vec5(p.x(),p.y(),p.z(),p.w(),p.e());
  }
}

public vec5 vec5(ivec p) {
  if(p == null) {
    return new vec5(0,0,0,0,0);
  }  else if(p instanceof ivec5 || p instanceof ivec6) {
    return new vec5(p.a(),p.b(),p.c(),p.d(),p.e());
  } else {
    return new vec5(p.x(),p.y(),p.z(),p.w(),p.e());
  }
}


/**
Vec 6
*/
public vec6 vec6() {
  return new vec6(0,0,0,0,0,0) ;
}

public vec6 vec6(float a, float b, float c, float d, float e, float f) {
  return new vec6(a,b,c,d,e,f);
}

public vec6 vec6(float [] array) {
  if(array.length == 1) {
    return new vec6(array[0],array[0],array[0],array[0],array[0],array[0]);
  } else if (array.length == 2){
    return new vec6(array[0],array[1],0,0,0,0) ;
  } else if (array.length == 3){
    return new vec6(array[0],array[1],array[2],0,0,0) ;
  } else if (array.length == 4){
    return new vec6(array[0],array[1],array[2],array[3],0,0) ;
  } else if (array.length == 5){
    return new vec6(array[0],array[1],array[2],array[3],array[4],0) ;
  }  else if (array.length > 5){
    return new vec6(array[0],array[1],array[2],array[3],array[4],array[5]) ;
  } else {
    return null;
  }
}

public vec6 vec6(int [] array) {
  if(array.length == 1) {
    return new vec6(array[0],array[0],array[0],array[0],array[0],array[0]);
  } else if (array.length == 2){
    return new vec6(array[0],array[1],0,0,0,0) ;
  } else if (array.length == 3){
    return new vec6(array[0],array[1],array[2],0,0,0) ;
  } else if (array.length == 4){
    return new vec6(array[0],array[1],array[2],array[3],0,0) ;
  } else if (array.length == 5){
    return new vec6(array[0],array[1],array[2],array[3],array[4],0) ;
  }  else if (array.length > 5){
    return new vec6(array[0],array[1],array[2],array[3],array[4],array[5]) ;
  } else {
    return null;
  }
}

public vec6 vec6(float v) {
  return new vec6(v,v,v,v,v,v);
}
public vec6 vec6(PVector p) {
  if(p == null) {
    return new vec6(0,0,0,0,0,0);
  } else {
    return new vec6(p.x, p.y, p.z, 0,0,0);
  }
}

// build with vec
public vec6 vec6(vec p) {
  if(p == null) {
    return new vec6(0,0,0,0,0,0);
  } else if(p instanceof vec5 || p instanceof vec6) {
    return new vec6(p.a(),p.b(),p.c(),p.d(),p.e(),p.f());
  } else {
    return new vec6(p.x(),p.y(),p.z(),p.w(),p.e(),p.f());
  }
}

public vec6 vec6(ivec p) {
  if(p == null) {
    return new vec6(0,0,0,0,0,0) ;
  } else if(p instanceof ivec5 || p instanceof ivec6) {
    return new vec6(p.a(),p.b(),p.c(),p.d(),p.e(),p.f());
  } else {
    return new vec6(p.x(),p.y(),p.z(),p.w(),p.e(),p.f());
  }
}
/**
* ROPE FRAMEWORK - Romanesco processing environment – 
* Copyleft (c) 2014-2020
* @author @stanlepunk
* @see https://github.com/StanLepunK/Rope_framework
* ROPE core
* v 0.2.1.1
*/










 


























/**
Something weird, now it's not necessary to use the method init_rope()
to use the interface Rope_constants...
that's cool but that's very weird !!!!!
*/
Rope r;

String rope_framework_version = "1.3.3.74";
public void rope_version() {
	r = new Rope();
	println("Romanesco Processing Environment - 2015-2021");
	println("Processing: 3.5.3.269");
	println("Rope library: " +r.VERSION);
  println("Rope framework: " + rope_framework_version);
}






/**
event
v 0.0.2
*/
vec2 scroll_event;
public void scroll(MouseEvent e) {
	float scroll_x = e.getCount();
	float scroll_y = e.getCount();
	if(scroll_event == null) {
		scroll_event = vec2(scroll_x,scroll_y);
	} else {
		scroll_event.set(scroll_x,scroll_y);
	}
}


public vec2 get_scroll() {
	if(scroll_event == null) {
		scroll_event = vec2();
		return scroll_event;
	} else {
		return scroll_event;
	}
}

/**
add for the future
import java.awt.event.MouseWheelEvent;
void mouseWheelMoved(MouseWheelEvent e) {
  println(e.getWheelRotation());
  println(e.getScrollType());
  println(MouseWheelEvent.WHEEL_UNIT_SCROLL);
  println(e.getScrollAmount());
  println(e.getUnitsToScroll());
}
*/
/**
* COSTUME classes
* Copyleft (c) 2019-2019
* v 0.11.0
* @author @stanlepunk
* @see https://github.com/StanLepunK/Rope_framework
* Here you finf the class Costume and all the class shape used.
*/


final int POINT = 2; // processing value

final int LINE = 4; // processing value

final int TRIANGLE = 13;
final int SQUARE = 14;
final int PENTAGON = 15;
final int HEXAGON = 16;
final int HEPTAGON = 17;
final int OCTOGON = 18;
final int NONAGON = 19;
final int DECAGON = 20;
final int HENDECAGON = 21;
final int DODECAGON = 22;

final int TEXT_ROPE = 26;

final int RECT = 30; // processing value
final int ELLIPSE = 31; // processing value
final int ARC = 32;  // processing value

final int SPHERE = 40; // processing value
final int BOX = 41; // processing value

final int CROSS_RECT = 52;
final int CROSS_BOX_2 = 53;
final int CROSS_BOX_3 = 54;

final int SPHERE_LOW = 100;
final int SPHERE_MEDIUM = 101;
final int SPHERE_HIGH = 102;
final int TETRAHEDRON = 103;

final int PIXEL = 800;

final int STAR = 805;
final int STAR_3D = 806;

final int FLOWER = 900;

final int TETRAHEDRON_LINE = 1001;
final int CUBE_LINE = 1002;
final int OCTOHEDRON_LINE = 1003;
final int RHOMBIC_COSI_DODECAHEDRON_SMALL_LINE = 1004;
final int ICOSI_DODECAHEDRON_LINE = 1005;

final int HOUSE = 2000;

final int VIRUS = 88_888_888;





















/**
* class Costume 
* 2018-2019
* v 0.7.1
*/

public class Costume {
	PGraphics other;

	boolean fill_is = true;
	boolean stroke_is = true;
	boolean alpha_is = true;

	vec3 pos;
	vec3 size;
	vec3 angle;

	int fill;
	int stroke;
	float thickness = 1.f;

  String name;
  String costume_text;
	int type;
	int node;
	int summits;
	int num;
	int mutation;
  // float angle;
	float [] ratio;
	float [] strength;
	vec2 [] pair;
	boolean is_3D = false;
	boolean is_vertex = true;
	R_Primitive prim;
	PApplet papplet;

	int align = LEFT;

	public Costume(PApplet pa) {
		this.papplet = pa;
	}

	public Costume(PApplet pa, int type) {
		this.papplet = pa;
		this.type = type;
	}

	public Costume(PApplet pa, String costume_text) {
		this.papplet = pa;
		this.type = TEXT_ROPE;
		this.costume_text = costume_text;
	}


	public void pos(float x, float y, float z) {
		if(pos == null) {
			pos = vec3(x,y,z);
		} else {
			pos.set(x,y,z);
		}
	}




	public void size(float x, float y, float z) {
		if(size == null) {
			size = vec3(x,y,z);
		} else {
			size.set(x,y,z);
		}
	}

	public void angle(float x, float y, float z) {
		if(angle == null) {
			angle = vec3(x,y,z);
		} else {
			angle.set(x,y,z);
		}
	}

	public void pass_graphic(PGraphics other) {
  	if(other != null) {
  		this.other = other;
  	}
  }
  
  // set
  public void set_text(String costume_text) {
		this.costume_text = costume_text;
	}

  public void set_name(String name) {
		this.name = name;
	}

  public void set_type(int type) {
		this.type = type;
	}

	public void set_node(int node) {
		this.node = node;
	}

	public void set_mutation(int mutation) {
		this.mutation = mutation;
	}

	public void set_summit(int summits) {
		this.summits = summits;
	}

	public void set_num(int num) {
		this.num = num;
	}

	public void set_align(int align) {
		this.align = align;
	} 

	/**
	public void set_angle(float angle) {
		this.angle = angle;
	}
	*/

	public void set_ratio(float... ratio) {
		this.ratio = ratio;
	}

	public void set_strength(float... strength) {
		this.strength = strength;
	}

	public void set_pair(vec2... pair) {
		this.pair = pair;
	}

	public void is_3D(boolean is_3D) {
		this.is_3D = is_3D;
	}

	public void is_vertex(boolean is_vertex) {
		this.is_vertex = is_vertex;
	}


	// get
	public vec3 pos() {
		return pos;
	}

	public vec3 size() {
		return size;
	}

	public vec3 angle() {
		return angle;
	}


	public int get_fill() {
		return fill;
	}

	public int get_stroke() {
		return stroke;
	}

	public float get_thickness() {
		return thickness;
	}
  
  public String get_name() {
  	return name;
  }
  
	public int get_type() {
		return type;
	}

	public int get_node() {
		return node;
	}

	public int get_mutation() {
		return mutation;
	}

	public int get_summit() {
		return summits;
	}

	public int get_num() {
		return num;
	}
	/**
	public float get_angle() {
		return angle;
	}
	*/

	public float[] get_ratio() {
		return ratio;
	}

	public float[] get_strength() {
		return strength;
	}

	public vec2[] get_pair() {
		return pair;
	}

	public boolean is_3D() {
		return is_3D;
	}

	public boolean is_vertex() {
		return is_vertex;
	}

	public boolean fill_is() {
		return this.fill_is;
	}

	public boolean stroke_is() {
		return this.stroke_is;
	}

	public boolean alpha_is() {
		return this.alpha_is;
	}






	// ASPECT
	public void fill_is(boolean fill_is) {
		this.fill_is = fill_is;
	}

	public void stroke_is(boolean stroke_is) {
		this.stroke_is = stroke_is;
	}

	public void alpha_is(boolean alpha_is) {
		this.alpha_is = alpha_is;
	}

	public void aspect_is(boolean fill_is, boolean stroke_is, boolean alpha_is) {
		this.fill_is = fill_is;
		this.stroke_is = stroke_is;
		this.alpha_is = alpha_is;
	}

	public void init_bool_aspect() {
		this.fill_is = true ;
	  this.stroke_is = true ;
	}

	public void aspect(int fill, int stroke, float thickness) {
	  //checkfill color
	  if(alpha(fill) <= 0 || !this.fill_is)  {
	    noFill(other); 
	  } else {
	  	manage_fill(fill);
	  } 
	  //check stroke color
	  if(alpha(stroke) <=0 || thickness <= 0 || !this.stroke_is) {
	    noStroke(other);
	  } else {
	  	manage_stroke(stroke);
	    manage_thickness(thickness);
	  }
	  init_bool_aspect();
	}

	public void aspect(int fill, int stroke, float thickness, Costume costume) {
		aspect(fill,stroke,thickness,costume.get_type());
	}

	public void aspect(int fill, int stroke, float thickness, int costume) {
		if(costume == r.NULL) {
	    // 
		} else if(costume != r.NULL || costume != POINT) {
	    if(alpha(fill) <= 0 || !fill_rope_is) {
	    	noFill(other); 
	    } else {
	    	manage_fill(fill);
	    }

	    if(alpha(stroke) <= 0  || thickness <= 0 || !stroke_rope_is) {
	    	noStroke(other); 
	    } else {
	    	manage_stroke(stroke);
	      manage_thickness(thickness);
	    }   
	  } else {
	    if(alpha(fill) == 0) {
	    	noStroke(other); 
	    } else {
	    	// case where the fill is use like a stroke, for point, pixel...
	    	manage_stroke(fill);
	    	manage_thickness(thickness);
	    }
	    noFill(other);   
	  }
	  init_bool_aspect();
	}



	public void aspect(vec fill, vec stroke, float thickness) {
	  //checkfill color
	  if(fill.w() <= 0 || !this.fill_is)  {
	    noFill(other) ; 
	  } else {
	    manage_fill(fill);
	  } 
	  //check stroke color
	  if(stroke.w() <=0 || thickness <= 0 || !this.stroke_is) {
	    noStroke(other);
	  } else {
	    manage_stroke(stroke);
	    manage_thickness(thickness);
	  }
	  init_bool_aspect();
	}

	public void aspect(vec fill, vec stroke, float thickness, Costume costume) {
		aspect(fill,stroke,thickness,costume.get_type());
	}


	public void aspect(vec fill, vec stroke, float thickness, int costume) {
	  if(costume == r.NULL) {
	    // 
		} else if(costume != r.NULL || costume != POINT) {
	    if(fill.w() <= 0 || !this.fill_is) {
	    	noFill(other) ; 
	    } else {
	    	manage_fill(fill);
	    } 
	    if(stroke.w() <= 0  || thickness <= 0 || !this.stroke_is) {
	    	noStroke(other); 
	    } else {
	    	manage_stroke(stroke);
	    	manage_thickness(thickness);
	    }   
	  } else {
	    if(fill.w() <= 0 || !this.fill_is) {
	    	noStroke(other); 
	    } else {
	    	// case where the fill is use like a stroke, for point, pixel...
	    	manage_stroke(fill); 
	    	manage_thickness(thickness);
	    }
	    noFill(other);   
	  }
	  init_bool_aspect();
	}


	private void manage_fill(Object arg) {
		if(arg instanceof vec2) {
			vec2 c = (vec2)arg;
			if(alpha_is()) {
				this.fill = color(c.x(),c.x(),c.x(),c.y());
			} else { 
				this.fill = color(c.x(),c.x(),c.x(),g.colorModeA);
			}
			fill(this.fill,other);
		} else if(arg instanceof vec3) {
			vec3 c = (vec3)arg;
			this.fill = color(c.x(),c.y(),c.z(),g.colorModeA);
			fill(this.fill,other);
		} else if(arg instanceof vec4) {
			vec4 c = (vec4)arg;
			if(alpha_is()) {
				this.fill = color(c.x(),c.y(),c.z(),c.w());
			} else {
				this.fill = color(c.x(),c.y(),c.z(),g.colorModeA);
			}
			fill(this.fill,other);
		} else if(arg instanceof Integer) {
			int c = (int)arg;
			if(alpha_is()) {
				fill(c,other);	
			} else {
				if(g.colorMode == 3) {
					this.fill = color(hue(c),saturation(c),brightness(c),g.colorModeA);
				} else {
					this.fill = color(red(c),green(c),blue(c),g.colorModeA);
				}
				fill(this.fill,other);
			}
		}
	}

	private void manage_stroke(Object arg) {
		if(arg instanceof vec2) {
			vec2 c = (vec2)arg;
			if(alpha_is()) {
				this.stroke = color(c.x(),c.x(),c.x(),c.y());
			} else {
				this.stroke = color(c.x(),c.x(),c.x(),g.colorModeA);
			}
			stroke(this.stroke,other);
		} else if(arg instanceof vec3) {
			vec3 c = (vec3)arg;
			this.stroke = color(c.x(),c.y(),c.z(),g.colorModeA);
			stroke(this.stroke,other);
		} else if(arg instanceof vec4) {
			vec4 c = (vec4)arg;
			if(alpha_is()) {
				this.stroke = color(c.x(),c.y(),c.z(),c.w());
			} else {
				this.stroke = color(c.x(),c.y(),c.z(),g.colorModeA);
			}			
			stroke(this.stroke);
		} else if(arg instanceof Integer) {
			int c = (int)arg;
			if(alpha_is()) {
				stroke(c,other);	
			} else {
				if(g.colorMode == 3) {
					this.stroke = color(hue(c),saturation(c),brightness(c),g.colorModeA);
				} else {
					this.stroke = color(red(c),green(c),blue(c),g.colorModeA);
				}
				stroke(this.stroke,other);
			}
		}
	}


	private void manage_thickness(float thickness) {
		this.thickness = thickness;
		strokeWeight(this.thickness,other);
	}


	public void show() {
		show(pos,size,angle);
	}

	public void show(vec3 pos, vec3 size, vec rot) {
		if(rot.x() != 0) costume_rotate_x();
		if(rot.y() != 0) costume_rotate_y();
		if(rot.z() != 0) costume_rotate_z();

		if (this.get_type() == PIXEL) {
			set((int)pos.x(),(int)pos.y(),(int)get_fill_rope(),other);
		} else if (this.get_type() == POINT) {
	    strokeWeight(size.x(),other);
			point(pos,other);
		} else if (this.get_type() == ELLIPSE) {
			push(other);
			translate(pos,other);
			costume_rotate(rot,other);
			ellipse(vec2(),vec2(size),other);
			pop(other);

		} else if (this.get_type() == RECT) {
			push(other);
			translate(pos,other);
			costume_rotate(rot,other);
			rect(vec2(-size.x(),-size.y()).div(2),vec2(size.x(),size.y()),other);
			pop(other);

		} else if (this.get_type() == LINE) {
			if(prim == null) prim = new R_Primitive(papplet,2);
			push(other);
			translate(pos,other);
			costume_rotate(rot,other);
			prim.pass_graphic(other);
			prim.size((int)size.x());
			prim.show();
			pop(other);
		}

		else if (this.get_type() == TRIANGLE) {
			if(prim == null || prim.get_summits() != 3) {
				prim = new R_Primitive(papplet,3);
			}
			push(other);
			translate(pos,other);
			costume_rotate(rot,other);
			prim.pass_graphic(other);
			prim.size((int)size.x());
			prim.show();
			pop(other);
		}  else if (this.get_type() == SQUARE) {
			if(prim == null  || prim.get_summits() != 4) {
				prim = new R_Primitive(papplet,4);
			}
			push(other);
			translate(pos,other);
			costume_rotate(rot,other);
			prim.pass_graphic(other);
			prim.size((int)size.x());
			prim.show();
			pop(other);
		} else if (this.get_type() == PENTAGON) {
			if(prim == null || prim.get_summits() != 5) {
				prim = new R_Primitive(papplet,5);
			}
			push(other);
			translate(pos,other);
			costume_rotate(rot,other);
			prim.pass_graphic(other);
			prim.size((int)size.x());
			prim.show();
			pop(other);
		} else if (this.get_type() == HEXAGON) {
			if(prim == null || prim.get_summits() != 6) {
				prim = new R_Primitive(papplet,6);
			}
			push(other);
			translate(pos,other);
			costume_rotate(rot,other);
			prim.pass_graphic(other);
			prim.size((int)size.x());
			prim.show();
			pop(other);
		} else if (this.get_type() == HEPTAGON) {
			if(prim == null || prim.get_summits() != 7) {
				prim = new R_Primitive(papplet,7);
			}
			push(other);
			translate(pos,other);
			costume_rotate(rot,other);
			prim.pass_graphic(other);
			prim.size((int)size.x());
			prim.show();
			pop(other);
		} else if (this.get_type() == OCTOGON) {
			if(prim == null || prim.get_summits() != 8) {
				prim = new R_Primitive(papplet,8);
			}
			push(other);
			translate(pos,other);
			costume_rotate(rot,other);
			prim.pass_graphic(other);
			prim.size((int)size.x());
			prim.show();
			pop(other);
		} else if (this.get_type() == NONAGON) {
			if(prim == null || prim.get_summits() != 9) {
				prim = new R_Primitive(papplet,9);
			}
			push(other);
			translate(pos,other);
			costume_rotate(rot,other);
			prim.pass_graphic(other);
			prim.size((int)size.x());
			prim.show();
			pop(other);
		} else if (this.get_type() == DECAGON) {
			if(prim == null || prim.get_summits() != 10) {
				prim = new R_Primitive(papplet,10);
			}
			push(other);
			translate(pos,other);
			costume_rotate(rot,other);
			prim.pass_graphic(other);
			prim.size((int)size.x());
			prim.show();
			pop(other);
		} else if (this.get_type() == HENDECAGON) {
			if(prim == null  || prim.get_summits() != 11) {
				prim = new R_Primitive(papplet,11);
			}
			push(other);
			translate(pos,other);
			costume_rotate(rot,other);
			prim.pass_graphic(other);
			prim.size((int)size.x());
			prim.show();
			pop(other);
		} else if (this.get_type() == DODECAGON) {
			if(prim == null  || prim.get_summits() != 12) {
				prim = new R_Primitive(papplet,12);
			}
			push(other);
			translate(pos,other);
			costume_rotate(rot,other);
			prim.pass_graphic(other);
			prim.size((int)size.x());
			prim.show();
			pop(other);
		}

		else if (this.get_type() == CROSS_RECT) {
			push(other);
			translate(pos,other);
			costume_rotate(rot,other);
			cross_rect(ivec2(0),(int)size.y(),(int)size.x(),other);
			pop(other) ;
		} else if (this.get_type() == CROSS_BOX_2) {
			push(other);
			translate(pos,other);
			costume_rotate(rot,other);
			cross_box_2(vec2(size.x(), size.y()),other);
			pop(other) ;
		} else if (this.get_type() == CROSS_BOX_3) {
			push(other);
			translate(pos,other);
			costume_rotate(rot,other);
			cross_box_3(size,other);
			pop(other);
		}



	  else if(this.get_type() == TEXT_ROPE) {
	  	push(other);
	  	translate(pos,other);
	  	costume_rotate(rot,other);
	  	textAlign(align,other);
	  	textSize(size.x(),other);
	  	text(costume_text,0,0,other);
	  	pop(other);
	  }

		else if (this.get_type() == SPHERE_LOW) {
			push(other);
			translate(pos,other);
			costume_rotate(rot,other);
			sphereDetail(5,other);
			sphere(size.x(),other);
			pop(other);
		} else if (this.get_type() == SPHERE_MEDIUM) {
			push(other);
			translate(pos,other);
			costume_rotate(rot,other);
			sphereDetail(12,other);
			sphere(size.x(),other);
			pop(other);
		} else if (this.get_type() == SPHERE_HIGH || this.get_type() == SPHERE) {
			push(other);
			translate(pos,other);
			costume_rotate(rot,other);
			sphere(size.x(),other);
			pop(other);
		} else if (this.get_type() == TETRAHEDRON) {
			push(other);
			translate(pos,other);
			costume_rotate(rot,other);
			polyhedron("TETRAHEDRON","VERTEX",(int)size.x,other);
			pop(other);
		} else if (this.get_type() == BOX) {
			push(other);
			translate(pos,other);
			costume_rotate(rot,other);
			box(size,other);
			pop(other);
		}

		else if (this.get_type() == STAR) {
			float [] ratio = {.38f};
			push(other);
			translate(pos,other);
			costume_rotate(rot,other);

			star_3D_is(false);
			if(get_summit() == 0 ) set_summit(5);
			star_summits(get_summit());
			star(vec3(),size,other);
			pop(other);
		} else if (this.get_type() == STAR_3D) {
			float [] ratio = {.38f};
			push(other);
			translate(pos,other);
			costume_rotate(rot,other);

			star_3D_is(true);
			if(get_summit() == 0 ) set_summit(5);
			star_summits(get_summit());
			star(vec3(),size,other);
			pop(other);
		}


		else if (this.get_type() == FLOWER) {
			push(other);
			translate(pos,other);
			costume_rotate(rot,other);
			if(get_summit() == 0 ) set_summit(5);
			if(get_pair() == null || get_pair().length != get_summit()*2) {
				pair = new vec2[get_summit()*2];
			}
			if(get_strength() == null || get_strength().length != get_summit()*2) {
				strength = new float[get_summit()*2];
			}

			for(int i = 0 ; i < get_summit()*2 ; i++) {
				vec2 value = vec2(.1f,.1f);
				if(pair[i] == null) {
					pair[i] = vec2(value);
				} else {
					pair[i].set(value);
				}
				strength[i] = 1.f;
			}

			for(int i = 0 ; i < get_summit() ; i++) {
				flower_static(pair[i],strength[i],pair[i+get_summit()],strength[i+get_summit()]);
			}
			flower(vec3(),(int)size.x,get_summit(),other);
			pop(other);
		}


		else if (this.get_type() == TETRAHEDRON_LINE) {
			push(other);
			translate(pos,other);
			costume_rotate(rot,other);
			polyhedron("TETRAHEDRON","LINE",(int)size.x(),other);
			pop(other);
		} else if (this.get_type() == CUBE_LINE) {
			push(other);
			translate(pos,other);
			costume_rotate(rot,other);
			polyhedron("CUBE","LINE",(int)size.x(),other);
			pop(other);
		} else if (this.get_type() == OCTOHEDRON_LINE) {
			push(other);
			translate(pos,other);
			costume_rotate(rot,other);
			polyhedron("OCTOHEDRON","LINE",(int)size.x(),other);
			pop(other);
		} else if (this.get_type() == RHOMBIC_COSI_DODECAHEDRON_SMALL_LINE) {
			push(other);
			translate(pos,other);
			costume_rotate(rot,other);
			polyhedron("RHOMBIC COSI DODECAHEDRON SMALL","LINE",(int)size.x(),other);
			pop(other);
		} else if (this.get_type() == ICOSI_DODECAHEDRON_LINE) {
			push(other);
			translate(pos,other);
			costume_rotate(rot,other);
			polyhedron("ICOSI DODECAHEDRON","LINE",(int)size.x(),other);
			pop(other);
		}

		else if(this.get_type() == HOUSE) {
			push(other);
			translate(pos,other);
			costume_rotate(rot,other);
			if(size.z() == 1) {
				house(size.xyy(),other);
			} else {
				house(size.xyz(),other);
			}
			pop(other);
		}


	  else if(this.get_type() == VIRUS) {
			push();
			translate(pos);
			costume_rotate(rot);
			virus(vec3(),size,0,-1);
			pop();
		}



		else if(this.get_type() < 0) {
			push() ;
			translate(pos);
			costume_rotate(rot) ;
			for(int i = 0 ; i < costume_pic_list.size() ; i++) {
				Costume_pic p = costume_pic_list.get(i);
				if(p.get_id() == this.get_type()) {
					if(p.get_type() == 1) {
						PImage img_temp = p.get_img().copy();
						if(size.x == size.y ) {
							img_temp.resize((int)size.x, 0);
						} else if (size.x != size.y) {
							img_temp.resize((int)size.x, (int)size.y);
						}
						image(img_temp,0,0);
						break ;
					} else if(p.get_type() == 2) {
						vec2 scale = vec2(1) ;
						if(size.x == size.y) {
	            scale = vec2(size.x / p.get_svg().width(), size.x / p.get_svg().width());
						} else {
							scale = vec2(size.x / p.get_svg().width(), size.y / p.get_svg().height());
						}
						
						p.get_svg().scaling(scale);
						p.get_svg().draw();
						break;
					}		
				}
			}
			pop();
		}
	  // reset variable can be change the other costume, if the effect is don't use.
		ratio_costume_size = 1;
	}
}












/**
* COSTUME PIC CLASS
* v 0.0.3
* 2014-2019
*/
public class Costume_pic {
	PImage img;
	ROPE_svg svg;
	int type = -1; 
	String name;
	int id;
	public Costume_pic(PApplet p5, String path, int id) {
		// add png
		if(path.endsWith("png") || path.endsWith("PNG")) {
			img = loadImage(path);
			type = 1;
		}

		// add svg
		if(path.endsWith("svg") || path.endsWith("SVG")) {
			svg = new ROPE_svg(p5,path);
			svg.mode(CENTER) ;
			svg.build() ;
			type = 2 ;
		}
		
		String [] split = path.split("/") ;
		name = split[split.length -1] ;
		name = name.substring(0,name.lastIndexOf(".")) ;
		this.id = id;
	}
  
  // get
	public int get_id() {
		return id;
	}

	public int get_type() {
		return type;
	}

	public ROPE_svg get_svg() {
		return svg;
	}

	public PImage get_img() {
		return img;
	}

	public String get_name() {
		return name;
	}
}

























/**
Class House
2019-2019
v 0.2.1
*/
public class House extends R_Shape  {
	private int fill_roof = r.BLOOD;
	private int fill_wall = r.GRAY[6];
	private int fill_ground = r.BLACK;
	private int stroke_roof = r.BLACK;
	private int stroke_wall = r.BLACK;
	private int stroke_ground = r.BLACK;
	private float thickness = 1;
	private boolean aspect_is;

	private int level;
	// private vec3 pos;
	private boolean roof_ar, roof_cr; // to draw or not the small roof side
	private vec3 offset = vec3(-.5f,0,.5f); // to center the house; 

  private vec3 [] pa;
	private vec3 [] pc;

	private int type = CENTER;
	
	public House(PApplet pa) {
		super(pa);
		build();
	}
  
  public House(PApplet pa, float size) {
  	super(pa);
  	size(size);
		build();
	}

	public House(PApplet pa, float sx, float sy, float sz) {
		super(pa);
		size(sx,sy,sz);
		build();
	}
	

	public void mode(int type) {
		this.type = type;
	}


	private void set_peak(float ra, float rc) {
		// check if this peak configuration is permitted
		if(ra + rc > 1.f) {
			if(ra>rc) {
				ra = 1.f-rc; 
			} else {
				rc = 1.f-ra;
			}
		}

		if(ra > 0.f) {
			roof_ar = true;
			if(pa != null && pa[0] != null) {
				pa[0].x = 1.f-ra+offset.x;
			}
		}

		if(rc > 0.f) {
			roof_cr = true;
			if(pc != null && pc[0] != null) {
				pc[0].x = rc+offset.x;
			}
		}
	}
  // aspect
  // fill
  public void set_fill(int c) {
  	aspect_is = true;
  	fill_roof = fill_wall = fill_ground = c;
  }

  public void set_fill_roof(int c) {
		aspect_is = true;
		fill_roof = c;
	}

	public void set_fill_wall(int c) {
		aspect_is = true;
		fill_wall = c;
	}

	public void set_fill_ground(int c) {
		aspect_is = true;
		fill_ground = c;
	}

	public void set_fill(float x, float y, float z, float a) {
		set_fill(color(x,y,z,a));
	}

	public void set_fill_roof(float x, float y, float z, float a) {
		set_fill_roof(color(x,y,z,a));
	}

	public void set_fill_wall(float x, float y, float z, float a) {
		set_fill_wall(color(x,y,z,a));
	}

	public void set_fill_ground(float x, float y, float z, float a) {
		set_fill_ground(color(x,y,z,a));
	}

  // stroke
	public void set_stroke(int c) {
  	aspect_is = true;
  	stroke_roof = stroke_wall = stroke_ground = c;
  }

  public void set_stroke_roof(int c) {
		aspect_is = true;
		stroke_roof = c;
	}

	public void set_stroke_wall(int c) {
		aspect_is = true;
		stroke_wall = c;
	}

	public void set_stroke_ground(int c) {
		aspect_is = true;
		stroke_ground = c;
	}

	public void set_stroke(float x, float y, float z, float a) {
		set_stroke(color(x,y,z,a));
	}

	public void set_stroke_roof(float x, float y, float z, float a) {
		set_stroke_roof(color(x,y,z,a));
	}

	public void set_stroke_wall(float x, float y, float z, float a) {
		set_stroke_wall(color(x,y,z,a));
	}

	public void set_stroke_ground(float x, float y, float z, float a) {
		set_stroke_ground(color(x,y,z,a));
	}

	public void set_thickness(float thickness) {
		aspect_is = true;
		this.thickness = thickness;
	}

	public void aspect_is(boolean is) {
		this.aspect_is = is;
	}

  
  // build
	private void build() {
		pa = new vec3[5];
		pc = new vec3[5];
		
		pa[0] = vec3(1,-1,-0.5f); // roof peak
		pa[1] = vec3(1,0,-1);
		pa[2] = vec3(1,1,-1);
		pa[3] = vec3(1,1,0);
		pa[4] = vec3(1,0,0);

		pc[0] = vec3(0,-1,-0.5f); // roof peak
		pc[1] = vec3(0,0,-1);
		pc[2] = vec3(0,1,-1);
		pc[3] = vec3(0,1,0);
		pc[4] = vec3(0,0,0);

		for(int i = 0 ; i < pa.length ; i++) {
			pa[i].add(offset);
			pc[i].add(offset);
		}
	}
  

	public void show() {
		float smallest_size = 0;
		for(int i = 0 ; i < 3 ; i++) {
			if(smallest_size == 0 || smallest_size > size.array()[i]) {
				smallest_size = size.array()[i];
			}
		}

    // DEFINE FINAL OFFSET
    vec3 def_pos = null;
	  if(this.type == TOP) {
	  	if(pos == null) {
	  		def_pos = vec3();
	  		def_pos.add(vec3(0,size.y*.5f,0));
	  	} else {
	  		def_pos = pos.copy();
	  		def_pos.add(vec3(0,size.y*.5f,0));		
	  	}
	  } else if(this.type == BOTTOM) {
	  	if(pos == null) {
	  		def_pos = vec3();
	  		def_pos.add(vec3(0,-size.y,0));
	  	} else {
	  		def_pos = pos.copy();
	  		def_pos.add(vec3(0,-size.y,0));		
	  	}
	  }



	  // WALL
	  if(aspect_is) {
	  	aspect(fill_wall,stroke_wall,thickness);
	  }
		// draw A : WALL > small and special side
		beginShape(other);
		if(def_pos == null) {
			if(!roof_ar) {
				vertex(pa[0].copy().mult(vec3(size.x,smallest_size,size.z)),other); // special point for the roof peak
			}
			for(int i = 1 ; i < pa.length ; i++) {
				vertex(pa[i].copy().mult(size),other);
			}
		} else {
			if(!roof_ar) {
				vertex(pa[0].copy().mult(vec3(size.x,smallest_size,size.z)).add(def_pos),other); // special point for the roof peak
			}
			for(int i = 1 ; i < pa.length ; i++) {
				vertex(pa[i].copy().mult(size).add(def_pos),other);
			}
		}
		endShape(CLOSE,other);


	  // draw B : WALL > main wall
	  beginShape(other);
		if(def_pos == null) {
			vertex(pa[2].copy().mult(size),other);
			vertex(pa[1].copy().mult(size),other);
			vertex(pc[1].copy().mult(size),other);
			vertex(pc[2].copy().mult(size),other);
		} else {
			vertex(pa[2].copy().mult(size).add(def_pos),other);
			vertex(pa[1].copy().mult(size).add(def_pos),other);
			vertex(pc[1].copy().mult(size).add(def_pos),other);
			vertex(pc[2].copy().mult(size).add(def_pos),other);
		}
		endShape(CLOSE,other);

	  // draw C : WALL > small and special side
		beginShape(other);
		if(def_pos == null) {
			if(!roof_cr) {
				vertex(pc[0].copy().mult(vec3(size.x,smallest_size,size.z)),other); // special point for the roof peak
			}
			for(int i = 1 ; i < pc.length ; i++) {
				vertex(pc[i].copy().mult(size),other);
			}
		} else {
			if(!roof_cr) {
				vertex(pc[0].copy().mult(vec3(size.x,smallest_size,size.z)).add(def_pos),other); // special point for the roof peak
			}
			for(int i = 1 ; i < pc.length ; i++) {
				vertex(pc[i].copy().mult(size).add(def_pos),other);
			}	
		}
		endShape(CLOSE,other);

		// draw D : WALL > main wall
		beginShape(other);
		if(def_pos == null) {
			vertex(pa[3].copy().mult(size),other);
			vertex(pa[4].copy().mult(size),other);
			vertex(pc[4].copy().mult(size),other);
			vertex(pc[3].copy().mult(size),other);
		} else {
			vertex(pa[3].copy().mult(size).add(def_pos),other);
			vertex(pa[4].copy().mult(size).add(def_pos),other);
			vertex(pc[4].copy().mult(size).add(def_pos),other);
			vertex(pc[3].copy().mult(size).add(def_pos),other);
		}
		endShape(CLOSE,other);





    // GROUND
    if(aspect_is) {
	  	aspect(fill_ground,stroke_ground,thickness);
	  }
		// draw G : GROUND
		beginShape(other);
		if(def_pos == null) {
			vertex(pa[2].copy().mult(size),other);
			vertex(pc[2].copy().mult(size),other);
			vertex(pc[3].copy().mult(size),other);
			vertex(pa[3].copy().mult(size),other);
		} else {
			vertex(pa[2].copy().mult(size).add(def_pos),other);
			vertex(pc[2].copy().mult(size).add(def_pos),other);
			vertex(pc[3].copy().mult(size).add(def_pos),other);
			vertex(pa[3].copy().mult(size).add(def_pos),other);
		}
		endShape(CLOSE,other);



    // ROOF
    if(aspect_is) {
	  	aspect(fill_roof,stroke_roof,thickness);
	  }
    // draw E : ROOF > main roof
		beginShape(other);
		if(def_pos == null) {
			vertex(pa[4].copy().mult(size),other);
			vertex(pa[0].copy().mult(vec3(size.x,smallest_size,size.z)),other); // special point for the roof peak
			vertex(pc[0].copy().mult(vec3(size.x,smallest_size,size.z)),other); // special point for the roof peak
			vertex(pc[4].copy().mult(size),other);			
		} else {
			vertex(pa[4].copy().mult(size).add(def_pos),other);
			vertex(pa[0].copy().mult(vec3(size.x,smallest_size,size.z)).add(def_pos),other); // special point for the roof peak
			vertex(pc[0].copy().mult(vec3(size.x,smallest_size,size.z)).add(def_pos),other); // special point for the roof peak
			vertex(pc[4].copy().mult(size).add(def_pos),other);
		}
		endShape(CLOSE,other);

		// draw F : ROOF > main roof
		beginShape(other);
		if(def_pos == null) {
			vertex(pa[0].copy().mult(vec3(size.x,smallest_size,size.z)),other); // special point for the roof peak
			vertex(pa[1].copy().mult(size),other);
			vertex(pc[1].copy().mult(size),other);
			vertex(pc[0].copy().mult(vec3(size.x,smallest_size,size.z)),other); // special point for the roof peak
		} else {
			vertex(pa[0].copy().mult(vec3(size.x,smallest_size,size.z)).add(def_pos),other); // special point for the roof peak
			vertex(pa[1].copy().mult(size).add(def_pos),other);
			vertex(pc[1].copy().mult(size).add(def_pos),other);
			vertex(pc[0].copy().mult(vec3(size.x,smallest_size,size.z)).add(def_pos),other); // special point for the roof peak
		}
		endShape(CLOSE,other);

		// DRAW AR  > small side roof
		if(roof_ar) {
			beginShape(other);
			if(def_pos == null) {
				vertex(pa[0].copy().mult(vec3(size.x,smallest_size,size.z)),other); // special point for the roof peak
				vertex(pa[1].copy().mult(size),other);
				vertex(pa[4].copy().mult(size),other);
			} else {
				vertex(pa[0].copy().mult(vec3(size.x,smallest_size,size.z)).add(def_pos),other); // special point for the roof peak
				vertex(pa[1].copy().mult(size).add(def_pos),other);
				vertex(pa[4].copy().mult(size).add(def_pos),other);
			}
			endShape(CLOSE,other);
		}

		// DRAW CR > small side roof
		if(roof_cr) {
			beginShape(other);
			if(def_pos == null) {
				vertex(pc[0].copy().mult(vec3(size.x,smallest_size,size.z)),other); // special point for the roof peak
				vertex(pc[1].copy().mult(size),other);
				vertex(pc[4].copy().mult(size),other);
			} else {
				vertex(pc[0].copy().mult(vec3(size.x,smallest_size,size.z)).add(def_pos),other); // special point for the roof peak
				vertex(pc[1].copy().mult(size).add(def_pos),other);
				vertex(pc[4].copy().mult(size).add(def_pos),other);
			}
			endShape(CLOSE,other);
		}
	}
}
/**
* Costume method
* Copyleft (c) 2014-2019
* v 1.10.0
* @author @stanlepunk
* @see https://github.com/StanLepunK/Rope_framework
*/







/**
* line2D
* v 0.2.1
* 2019-2019
*/
public void line2D(vec2 p1, vec2 p2, boolean aa_is, boolean update_pix_is, PGraphics pg) {
	line2D(p1.x(), p1.y(), p2.x(), p2.y(), aa_is, update_pix_is, pg);
}

public void line2D(vec2 p1, vec2 p2, boolean aa_is, boolean update_pix_is) {
	line2D(p1.x(), p1.y(), p2.x(), p2.y(), aa_is, update_pix_is, g);
}

public void line2D(float x1, float y1, float x2, float y2, boolean aa_is, boolean update_pix_is) {
	line2D(x1, y1, x2, y2, aa_is, update_pix_is, g);
}

public void line2D(float x1, float y1, float x2, float y2, boolean aa_is, boolean update_pix_is, PGraphics pg) {
	if(!aa_is) {
		draw_line_no_aa(x1, y1, x2, y2, update_pix_is, pg);
	} else { 	
		vec2 src = vec2(x1,y1);
		vec2 dst = vec2(x2,y2);
		float angle = src.angle(dst);
		float range = 0.005f;
		
		boolean x_is = false;
		if(x1 == x2) x_is = true;
		boolean y_is = false;
		if(y1 == y2) y_is = true;
		boolean align_is = false;
		if(x_is || y_is) align_is = true;

		boolean n_is = false;
		if(angle > r.NORTH - range && angle < r.NORTH + range) n_is = true;
		boolean ne_is = false;
		if(angle > r.NORTH_EAST - range && angle < r.NORTH_EAST + range) ne_is = true;
		boolean e_is = false;
		if(angle > r.EAST - range && angle < r.EAST + range) e_is = true;
		boolean se_is = false;
		if(angle > r.SOUTH_EAST - range && angle < r.SOUTH_EAST + range) se_is = true;
		boolean s_is = false;
		if(angle > r.SOUTH - range && angle < r.SOUTH + range) s_is = true;
		boolean sw_is = false;
		if(angle > r.SOUTH_WEST - range && angle < r.SOUTH_WEST + range) sw_is = true;
		boolean w_is = false;
		if(angle > r.WEST - range && angle < r.WEST + range) w_is = true;
		boolean nw_is = false;
		if(angle > r.NORTH_WEST - range && angle < r.NORTH_WEST + range) nw_is = true;

		// resolve
		boolean exception_is = false;
		if(align_is || n_is || ne_is || e_is || se_is || s_is || sw_is || w_is || nw_is) {
			exception_is = true;
		}

		if(exception_is) {
			draw_line_no_aa(x1, y1, x2, y2, update_pix_is, pg);
		} else {
			draw_line_aa_wu(x1, y1, x2, y2, update_pix_is, pg);
		}	
	} 
}







/**
* line2D echo loop
* 2019-2019
* v 0.0.2
* This method return the rest of line after this one meet an other line from a list of walls
*/
public R_Line2D line2D_echo_loop(R_Line2D line, R_Line2D [] walls, ArrayList<R_Line2D> list, float offset, float angle_echo, boolean go_return_is) {
	R_Line2D rest = new R_Line2D(this);
	int count_limit = 0;
	if(go_return_is) offset = -1 *offset;

	for(R_Line2D wall : walls) {
		count_limit ++;
		// add line.a() like exception because this one touch previous border
		vec2 node = wall.intersection(line, line.a());
		if(node != null) {
			R_Line2D line2D = new R_Line2D(this,line.a(),node);
			rest = new R_Line2D(this,node,line.b());

			//offset
			float angle_offset = wall.angle();
			if(offset < 0 ) {
				if(list.size()%2 == 0 && go_return_is) {
					angle_offset += PI;
				} else {

				}
			} else {
				if(list.size()%2 == 0 && go_return_is) {
					angle_offset -= PI;
				} else {
					//
				}
			}

			vec2 displacement = projection(angle_offset,offset);
			rest.offset(displacement);
			
			// classic go and return
			if(go_return_is) {
				rest.angle(rest.angle() +PI);
			// go on a same way
			} else {
				float angle = rest.angle() -PI;

				vec2 temp = projection(angle, width+height).add(rest.a());
				R_Line2D max_line = new R_Line2D(this,rest.b(),temp);
				for(R_Line2D limit_opp : walls) {
					vec2 opp_node = limit_opp.intersection(max_line,vec2(node).add(displacement));
					if(opp_node != null) {
						rest.angle(rest.angle());
						vec2 swap = opp_node.sub(node).sub(displacement);
						rest.offset(swap);
						break;
					}
				}
			}
			// add result
			list.add(line2D);
			break;
		} else {
			// to add the last segment of the main line, 
			// because this one cannot match with any borders or limits
			// before add the last element, it's necessary to check all segments borders
			if(count_limit == walls.length) {
				list.add(line);
			} 
		}
	}
	//angle echo effect
	if(angle_echo != 0) {
		rest.angle(rest.angle()+angle_echo);
	}
	return rest;
}































/**
* line AA Xiaolin Wu based on alogrithm of Bresenham
* v 0.2.3
* 2019-2019
* @see https://github.com/jdarc/wulines/blob/master/src/Painter.java
* @see https://rosettacode.org/wiki/Xiaolin_Wu%27s_line_algorithm#Java
* @see https://en.wikipedia.org/wiki/Bresenham%27s_line_algorithm
* @see https://en.wikipedia.org/wiki/Xiaolin_Wu%27s_line_algorithm
*/

// integer part of x
public int ipart(double x) {
	return (int)x;
}

// fractional part of x
public double fpart(double x) {
	return x - Math.floor(x);
}

// fractional part of x
public double rfpart(double x) {
	return 1.0f - fpart(x);
}
 
public void draw_line_aa_wu(double x_0, double y_0, double x_1, double y_1, boolean update_pixel, PGraphics pg) {
	if(update_pixel) pg.loadPixels();
	// check angle before the steeping
	vec2 src = vec2((float)x_0,(float)y_0);
	vec2 dst = vec2((float)x_1,(float)y_1);
	float angle = src.angle(dst);

	boolean steep = Math.abs(y_1 - y_0) > Math.abs(x_1 - x_0);
	double buffer;
	if (steep) {
		buffer = y_0;
		y_0 = x_0; 
		x_0 = buffer;
		buffer = y_1; 
		y_1 = x_1; 
		x_1 = buffer;
	}
	
	if (x_0 > x_1) {
		buffer = x_0; 
		x_0 = x_1; 
		x_1 = buffer;
		buffer = y_0; 
		y_0 = y_1; 
		y_1 = buffer;
	}

	double dx = x_1 - x_0;
	double dy = y_1 - y_0;
	double gradient = dy / dx;
	
	// MISC
	// here method use to set the design who the Xaolin Wu line, is not the algorithm himself
	// colour part
	float radius = dist(vec2((float)x_0,(float)y_0),vec2((float)x_1,(float)y_1));
	float step_palette = radius;
	int [] col = {pg.strokeColor};
	int colour = col[0];
	float alpha_ratio = 1.0f;
	if(get_palette() != null) {
		col = get_palette();
		step_palette = radius / col.length;  
	}

	// BACK to ALGORITHM
	// handle first endpoint
	int x_end_0 = (int)Math.round(x_0);
	double y_end_0 = y_0 + gradient * (x_end_0 - x_0);
	double x_gap_0 = rfpart(x_0 + 0.5f);
	double stop_intery = y_end_0;  

	// handle second endpoint
	int x_end_1 = (int)Math.round(x_1);
	double start_intery = y_1 + gradient * (x_end_1 - x_1);
	double x_gap_1 = fpart(x_1 + 0.5f);

	colour = colour_wu_line_pixel(stop_intery, start_intery, stop_intery, radius, step_palette, col, angle);
	alpha_ratio = alpha_ratio_wu_line_pixel(stop_intery, start_intery, stop_intery, radius, step_palette, angle);
	pixel_wu(steep, x_end_0, stop_intery, x_gap_0, colour, alpha_ratio, pg);

	colour = colour_wu_line_pixel(start_intery, start_intery, stop_intery, radius, step_palette, col, angle);
	alpha_ratio = alpha_ratio_wu_line_pixel(start_intery, start_intery, stop_intery, radius, step_palette, angle);
	pixel_wu(steep, x_end_1, start_intery, x_gap_1, colour, alpha_ratio, pg);

	// main loop
	// first y-intersection for the main loop
	yes_steep = 0;
	no_steep = 0;
	double intery = y_end_0 + gradient;
	for(int x = x_end_0 ; x <= x_end_1 ; x++) {
		double gap = 1.0f;
		colour = colour_wu_line_pixel(intery, start_intery, stop_intery, radius, step_palette, col, angle);
		alpha_ratio = alpha_ratio_wu_line_pixel(intery, start_intery, stop_intery, radius, step_palette, angle);
		pixel_wu(steep, x, intery, gap, colour, alpha_ratio, pg);
		intery += gradient;
	}
	if(update_pixel) pg.updatePixels();
}

int yes_steep = 0;
int no_steep = 0;
public void pixel_wu(boolean steep, int x, double intery, double gap, int colour, float alpha_ratio, PGraphics pg) {
	double alpha = 0;

	if (steep) {
		alpha = rfpart(intery) * gap;
		plot(PApplet.parseInt(ipart(intery) + 0), x, colour, (float)alpha *alpha_ratio, pg);
		alpha = fpart(intery) * gap;
		plot(PApplet.parseInt(ipart(intery) + 1), x, colour, (float)alpha *alpha_ratio, pg);
	} else {
		alpha = rfpart(intery) * gap;
		plot(x, PApplet.parseInt(ipart(intery) + 0), colour, (float)alpha *alpha_ratio, pg);
		alpha = fpart(intery) * gap;
		plot(x, PApplet.parseInt(ipart(intery) + 1), colour, (float)alpha *alpha_ratio, pg);
	}
}


public float alpha_ratio_wu_line_pixel(double intery, double start, double stop, float radius, float step, float angle) {
	float index = index_wu(intery, start, stop, radius, angle);
	float alpha = 1.0f;
	
	if(alpha_entry_line2D != 1.0f || alpha_exit_line2D != 1.0f) {
		if(alpha_entry_line2D < 0) alpha_entry_line2D = 0;
		if(alpha_entry_line2D > 1) alpha_entry_line2D = 1;
		if(alpha_exit_line2D < 0) alpha_exit_line2D = 0;
		if(alpha_exit_line2D > 1) alpha_exit_line2D = 1;
		alpha = map(index,0,radius,alpha_entry_line2D,alpha_exit_line2D);
	}
	return alpha;
}



public int colour_wu_line_pixel(double intery, double start, double stop, float radius, float step, int [] colour_list, float angle) {
	float index = index_wu(intery, start, stop, radius, angle);
	return colour_line2D((int)index,step,colour_list);
}


public float index_wu(double intery, double start, double stop, float radius, float angle) {
	if(start == stop) {
		start -= 1;
	}
	float index = 1;
	boolean inverse_is = false;
	
	if((angle > r.NORTH_EAST && angle < r.SOUTH_WEST)) {
		inverse_is = true;
	}

	if(inverse_is) {
		index = map((float)intery,(float)stop,(float)start,0,radius);
	} else {
		index = map((float)intery,(float)start,(float)stop,0,radius);
	}
	
	if(index < 0) index = 0;
	if(index > radius) index = radius;
	return index;
}




/**
* NO AA
*/
public void draw_line_no_aa(float x0, float y0, float x1, float y1, boolean update_pixel, PGraphics pg) {
	vec2 src = vec2(x0,y0);
	vec2 dst = vec2(x1,y1);
	float dir = src.angle(dst);
	float radius = dist(src,dst);
	
	// manage colour list
	float step_palette = radius;
	int [] col = {pg.strokeColor};
	if(get_palette() != null) {
		col = get_palette();
		step_palette = radius / col.length;  
	}

	boolean alpha_is = false;
	float [] alpha = {1.f};
	if(alpha_entry_line2D != 1.0f || alpha_exit_line2D != 1.0f) {
		alpha_is = true;
		alpha = new float[ceil(radius)];
		if(alpha_entry_line2D < 0) alpha_entry_line2D = 0;
		if(alpha_entry_line2D > 1) alpha_entry_line2D = 1;
		if(alpha_exit_line2D < 0) alpha_exit_line2D = 0;
		if(alpha_exit_line2D > 1) alpha_exit_line2D = 1;

		for(int i = 0 ; i < alpha.length; i++) {
			alpha[i] = map(i,0,alpha.length,alpha_entry_line2D,alpha_exit_line2D);
		}
	}


	if(update_pixel) pg.loadPixels();
	for(int i = 0 ; i < radius ; i++) {
		float x = cos(dir);
		float y = sin(dir);
		float from_center = i;
		vec2 path = vec2(x,y).mult(from_center).add(src);
		path.constrain(vec2(0),vec2(width,height));
		int px = (int)path.x();
		int py = (int)path.y();

		// update pixel
		int colour = colour_line2D(i,step_palette,col);
		if(alpha_is) {
			plot(px, py, colour, alpha[i], pg);
		} else {
			plot(px, py, colour, 1.0f, pg);
		}
		
	}
	if(update_pixel) pg.updatePixels();
}






// util line2D
public int colour_line2D(int index, float step, int [] colour_list) {
	int target = 0;
	if(tempo() == null) {
		target = floor((float)index/step);
	} else {
		target = get_tempo_pos(index);
	}
	target = target%colour_list.length;
	return colour_list[target];
}


float alpha_entry_line2D = 1.0f;
float alpha_exit_line2D =1.0f;
public void alpha_line2D(float entry, float exit) {
	alpha_entry_line2D = entry;
	alpha_exit_line2D = exit;
}











































/**
Costume selection in shape catalogue
*/
public void costume(float x, float y, float sx, float sy, Object data) {
	costume(vec2(x,y),vec2(sx,sy),data,null);
}

public void costume(float x, float y, float sx, float sy, Object data, PGraphics pg) {
	costume(vec2(x,y),vec2(sx,sy),data,pg);
}

//
public void costume(float x, float y, float z, float sx, float sy, Object data) {
	costume(vec3(x,y,z),vec2(sx,sy),data,null);
}

public void costume(float x, float y, float z, float sx, float sy, Object data, PGraphics pg) {
	costume(vec3(x,y,z),vec2(sx,sy),data,pg);
}

// 
public void costume(float x, float y, float z, float sx, float sy, float sz, Object data) {
	costume(vec3(x,y,z),vec3(sx,sy,sz),data,null);
}

public void costume(float x, float y, float z, float sx, float sy, float sz, Object data, PGraphics pg) {
	costume(vec3(x,y,z),vec3(sx,sy,sz),data,pg);
}

//
public void costume(vec pos, int size_int, Object data) {
	costume(pos,size_int,data,null);
}

public void costume(vec pos, int size_int, Object data, PGraphics pg) {
	int which_costume = 0;
	String sentence = null;
	vec3 rotation = vec3();
	vec3 size = vec3(size_int);
	if(data instanceof Costume) {
		costume_impl(pos,size,rotation,(Costume)data,pg);
	} else if(data instanceof Integer) {
		which_costume = (int)data;
		costume_management(pos,size,rotation,which_costume,null,pg);
	} else if(data instanceof String) {
		sentence = (String)data;
		which_costume = MAX_INT;
		costume_management(pos,size,rotation,which_costume,sentence,pg);
	}
}

//
public void costume(vec pos, vec size, Object data) {
	costume(pos,size,data,null);
}

public void costume(vec pos, vec size, Object data, PGraphics pg) {
	int which_costume = 0;
	String sentence = null;
	vec3 rotation = vec3();
	if(data instanceof Costume) {
		costume_impl(pos,size,rotation,(Costume)data,pg);
	} else if(data instanceof Integer) {
		which_costume = (int)data;
		costume_management(pos,size,rotation,which_costume,null,pg);
	} else if(data instanceof String) {
		sentence = (String)data;
		which_costume = MAX_INT;
		costume_management(pos,size,rotation,which_costume,sentence,pg);
	}
}

//
// for this method we use class Float to be sure of method signature
public void costume(vec pos, vec size, Float rot, Object data) {
	costume(pos,size,rot,data,null);
}

// for this method we use class Float to be sure of method signature
public void costume(vec pos, vec size, Float rot, Object data, PGraphics pg) {
	int which_costume = 0;
	String sentence = null;
	vec3 rotation = vec3(0,0,rot);
	if(data instanceof Costume) {
		costume_impl(pos,size,rotation,(Costume)data,pg);
	} else if(data instanceof Integer) {
		which_costume = (int)data;
		costume_management(pos,size,rotation,which_costume,null,pg);
	} else if(data instanceof String) {
		sentence = (String)data;
		which_costume = MAX_INT;
		costume_management(pos,size,rotation,which_costume,sentence,pg);
	}
}

// 
public void costume(vec pos, vec size, vec rotation, Object data) {
	costume(pos,size,rotation,data,null);
}


public void costume(vec pos, vec size, vec rotation, Object data, PGraphics pg) {
	int which_costume = 0;
	String sentence = null;
	if(data instanceof Costume) {
		costume_impl(pos,size,rotation,(Costume)data,pg);
	} else if(data instanceof Integer) {
		which_costume = (int)data;
		costume_management(pos,size,rotation,which_costume,null,pg);
	} else if(data instanceof String) {
		sentence = (String)data;
		which_costume = MAX_INT;
		costume_management(pos,size,rotation,which_costume,sentence,pg);
	}
}









/**
managing costume rope method
*/
public void costume_management(vec pos, vec size, vec rotation, int which_costume, String sentence, PGraphics pg) {
	vec3 pos_final = vec3(0) ;
	vec3 size_final = vec3(1) ;
	if((pos instanceof vec2 || pos instanceof vec3) 
			&& (size instanceof vec2 || size instanceof vec3)
			&& (rotation instanceof vec2 || rotation instanceof vec3)) {
		// pos
		if(pos instanceof vec2) {
			vec2 temp_pos = (vec2)pos;
			pos_final.set(temp_pos.x, temp_pos.y, 0);
		} else if(pos instanceof vec3) {
			vec3 temp_pos = (vec3)pos;
			pos_final.set(temp_pos);
		}
		//size
		if(size instanceof vec2) {
			vec2 temp_size = (vec2)size;
			size_final.set(temp_size.x, temp_size.y, 1);
		} else if(size instanceof vec3) {
			vec3 temp_size = (vec3)size;
			size_final.set(temp_size);
		}
		//send
		if(sentence == null ) {
			costume_impl(pos_final,size_final,rotation,which_costume,pg);
		} else {
			costume_impl(pos_final,size_final,rotation,sentence,pg);
		}		
	} else {
		printErrTempo(180,"vec pos or vec size if not an instanceof vec2 or vec3, it's not possible to process costume_rope()");
	}
}






/**
MAIN METHOD 
String COSTUME
v 0.4.0
Change the method for method with 
case and which_costume
and 
break
*/
public void costume_impl(vec3 pos, vec3 size, vec rot, String sentence, PGraphics pg) {
	if(rot.x != 0) costume_rotate_x();
	if(rot.y != 0) costume_rotate_y();
	if(rot.z != 0) costume_rotate_z();
	push(pg);
	translate(pos,pg);
	costume_rotate(rot,pg);
	textSize(size.x());
	text(sentence,0,0,pg);
	pop(pg);
}

/**
method to pass costume to class costume
*/
Costume costume_rope_buffer;
public void costume_impl(vec3 pos, vec3 size, vec rot, int which_costume, PGraphics pg) {
	if(costume_rope_buffer == null) {
		costume_rope_buffer = new Costume(this,which_costume);
	} else {
		costume_rope_buffer.set_type(which_costume);
	}
	costume_rope_buffer.pass_graphic(pg);
	costume_rope_buffer.show(pos,size,rot);
}


public void costume_impl(vec pos, vec size, vec rot, Costume costume, PGraphics pg) {
	costume.pass_graphic(pg);
	costume.show(vec3(pos),vec3(size),rot);
}





















































/**
ASPECT ROPE 2016-2019
v 0.2.0
*/
Costume aspect_rope;
public void aspect_is(boolean fill_is, boolean stroke_is, boolean alpha_is) {
	if(aspect_rope == null) aspect_rope = new Costume(this);
	aspect_rope.aspect_is(fill_is,stroke_is,alpha_is);
	fill_rope_is = aspect_rope.fill_is();
	stroke_rope_is = aspect_rope.stroke_is();
	alpha_rope_is = aspect_rope.alpha_is();
}

public void init_bool_aspect() {
	if(aspect_rope == null) {
		aspect_rope = new Costume(this);
	}
	aspect_rope.aspect_is(true,true,true);
}

public void aspect(int fill, int stroke, float thickness) {
	aspect(fill,stroke,thickness,g);
}
public void aspect(int fill, int stroke, float thickness, PGraphics other) {
	if(aspect_rope == null) {
		aspect_rope = new Costume(this);
	}
	aspect_is(aspect_rope.fill_is(),aspect_rope.stroke_is(),aspect_rope.alpha_is());
	aspect_rope.pass_graphic(other);
	aspect_rope.aspect(fill,stroke,thickness);
}

public void aspect(vec fill, vec stroke, float thickness) {
	aspect(fill,stroke,thickness,g);
}

public void aspect(vec fill, vec stroke, float thickness, PGraphics other) {
	if(aspect_rope == null) aspect_rope = new Costume(this);
	aspect_is(aspect_rope.fill_is(),aspect_rope.stroke_is(),aspect_rope.alpha_is());
	aspect_rope.pass_graphic(other);
	aspect_rope.aspect(fill,stroke,thickness);
}

public int get_fill_rope() {
	if(aspect_rope != null) {
		return aspect_rope.get_fill();
	} else {
		return color(g.colorModeX);
	}
}

public int get_stroke_rope() {
	if(aspect_rope != null) {
		return aspect_rope.get_stroke();
	} else {
		return color(0);
	}
}

public float get_thickness_rope() {
	if(aspect_rope != null) {
		return aspect_rope.get_thickness();
	} else {
		return 1.f;
	}
}













































/**
COSTUME
v 0.0.4
*/
/**
simple text 
v 0.0.2
*/

public void costume_text(String arg) {
	costume_rope_buffer.set_text(arg);
}



/**
* rotate behavior
* v 0.3.0
*/
boolean costume_rot_x;
boolean costume_rot_y;
boolean costume_rot_z;

public void costume_rotate_x() {
	costume_rot_x = true;
}

public void costume_rotate_y() {
	costume_rot_y = true;
}

public void costume_rotate_z() {
	costume_rot_z = true;
}

public void costume_rotate(vec rotate) {
	costume_rotate(rotate,null);
}

public void costume_rotate(vec rotate, PGraphics other) {
	if(get_renderer() == P3D) {
		if(costume_rot_x && rotate.x() != 0) {
			rotateX(rotate.x(),other);
			costume_rot_x = false;
		}
		if(costume_rot_y && rotate.y() != 0) {
			rotateY(rotate.y(),other);
			costume_rot_y = false;
		}
		if(costume_rot_z && rotate.z() != 0) {
			rotateZ(rotate.z(),other);
			costume_rot_z = false;
		}
	} else {
		if(rotate.x() == 0 && rotate.y() == 0 && rotate.z() != 0 && costume_rot_x) {
			rotate(rotate.z(),other);
			costume_rot_x = false;
		} 
		if(costume_rot_x && rotate.x() != 0) {
			rotateX(rotate.x(),other);
			costume_rot_x = false;
		}
		if(costume_rot_y && rotate.y() != 0) {
			rotateY(rotate.y(),other);
			costume_rot_y = false;
		}
	}
}


/**
ratio size costume
*/
float ratio_costume_size = 1;
public void set_ratio_costume_size(float ratio) {
	ratio_costume_size = ratio;
}




























/**
add pic 
v 0.0.1
*/
ArrayList <Costume_pic> costume_pic_list = new ArrayList<Costume_pic>() ;

public void load_costume_pic(String path) {
	if(path.endsWith("png") || path.endsWith("PNG") || path.endsWith("svg") || path.endsWith("SVG")) {
		int new_ID = costume_pic_list.size() * (-1) ;
		new_ID -= 1 ;
		Costume_pic c = new Costume_pic(this, path, new_ID) ;
		costume_pic_list.add(c) ; ;
		println("ID pic:", new_ID) ;
	}
}














































/**
house method
*/
House house_costume_rope;
public void house(vec3 size) {
	house(size,null);
}
public void house(vec3 size, PGraphics other) {
	if(house_costume_rope != null) {
		house_costume_rope.size(size);
		house_costume_rope.pass_graphic(other);
		// house_costume_rope.show(g);
		house_costume_rope.show();
	} else {
		//house_costume_rope = new House();
		house_costume_rope = new House(this);
	}
}





/**
* flower method
* 2019-2019
* v 0.0.3
*/
R_Circle flower_costume_rope;
public void flower(vec pos, int diam, int petals_num) {
	flower(pos,diam,petals_num,null);
}
public void flower(vec pos, int diam, int petals_num, PGraphics other) {
	if(flower_costume_rope == null || flower_costume_rope.get_summits() != petals_num) {
		flower_costume_rope = new R_Circle(this,petals_num);
	} else {
		flower_costume_rope.pos(pos);
		flower_costume_rope.size(diam);
		flower_costume_rope.pass_graphic(other);
		flower_costume_rope.show();
		// if(petals_num < 3) petals_num = 3;
	}
}

public void flower_wind(vec2 petal_left, float strength_left, vec2 petal_right, float strength_right) {
	if(flower_costume_rope != null) {
		for(R_Bezier b : flower_costume_rope.get_bezier()) {
			vec2 trouble = vec2().sin_wave(frameCount,petal_left.x(),petal_left.y()).mult(strength_left);
			b.set_a(trouble);
			trouble = vec2().cos_wave(frameCount,petal_right.x(),petal_right.y()).mult(strength_right);
			b.set_b(trouble);
		}
	}
}


public void flower_static(vec2 petal_left, float strength_left, vec2 petal_right, float strength_right) {
	if(flower_costume_rope != null) {
		for(R_Bezier b : flower_costume_rope.get_bezier()) {
			vec2 petal_show = vec2(petal_left.x(),petal_left.y()).mult(strength_left);
			b.set_a(petal_show);
			petal_show = vec2(petal_right.x(),petal_right.y()).mult(strength_right);
			b.set_b(petal_show);
		}
	}
}










































/**
ANNEXE COSTUME
SHAPE CATALOGUE
*/
/**
STAR
*/

R_Star star_costume_rope;
public void star(vec position, vec size) {
	star(position,size,null);
}

public void star(vec position, vec size, PGraphics other) {
	if(star_costume_rope != null) {
		star_costume_rope.pos(position);
		star_costume_rope.size(size);
		star_costume_rope.pass_graphic(other);
		star_costume_rope.show();
	} else {
		star_costume_rope = new R_Star(this);
	}
}


public void star_3D_is(boolean is_3D) {
	if(star_costume_rope != null) {
		star_costume_rope.is_3D(is_3D);
	} else {
		star_costume_rope = new R_Star(this);
	}
}


public void star_summits(int summits) {
	if(star_costume_rope != null) {
		star_costume_rope.set_summits(summits);
	} else {
		star_costume_rope = new R_Star(this);
	}
}

public void star_angle(float angle) {
	if(star_costume_rope != null) {
		star_costume_rope.angle_x(angle);
	} else {
		star_costume_rope = new R_Star(this);
	}
}

public void star_ratio(float... ratio) {
	if(star_costume_rope != null) {
		star_costume_rope.set_ratio(ratio);
	} else {
		star_costume_rope = new R_Star(this);
	}
}








 






















/**
* CROSS
* v 0.2.0
*/
public void cross_rect(ivec2 pos, int thickness, int radius) { 
	cross_rect(pos,thickness,radius,null);
}

public void cross_rect(ivec2 pos, int thickness, int radius, PGraphics other) {
	float h = radius;
	float w = thickness/3;

	// verticale one
	vec2 size = vec2(w,h);
	vec2 pos_temp = vec2(pos.x, pos.y -floor(size.y/2) +(w/2));
	pos_temp.sub(w/2);
	rect(pos_temp,size,other);
	
	// horizontal one
	size.set(h,w);
	pos_temp.set(pos.x-floor(size.x/2) +(w/2),pos.y);
	pos_temp.sub(w/2);
	rect(pos_temp,size,other);
}

public void cross_box_2(vec2 size) {
	cross_box_2(size,null);
}

public void cross_box_2(vec2 size, PGraphics other) {
	float scale_cross = size.sum() *.5f;
	float small_part = scale_cross *ratio_costume_size *.3f;

	box(size.x,small_part,small_part,other);
	box(small_part,size.y,small_part,other);
}

public void cross_box_3(vec3 size) {
	cross_box_3(size,null);
}

public void cross_box_3(vec3 size, PGraphics other) {
	float scale_cross = size.sum() *.3f;
	float small_part = scale_cross *ratio_costume_size *.3f;
	 
	box(size.x,small_part,small_part,other);
	box(small_part,size.y,small_part,other);
	box(small_part,small_part,size.z,other);
}
















/**
VIRUS
2015-2018
v 0.2.2
*/
public void virus(vec pos, vec size) {
	int close = -1 ;
	float angle = 0 ;
	virus(pos,size,angle,close) ;
}

public void virus(vec pos, vec size, float angle) {
	int close = -1;
	virus(pos,size,angle,close);
}


// main method
R_Virus virus_costume_rope;
boolean make_virus = true ;
public void virus(vec pos, vec size, float angle, int close) {
	if(make_virus) {
		virus_costume_rope = new R_Virus(this);
		make_virus = false ;
	}

	if(virus_costume_rope.get_mutation() > 0 && frameCount%virus_costume_rope.get_mutation() == 0) {
		virus_costume_rope.reset() ;
	}
	virus_costume_rope.angle_x(angle) ;
	virus_costume_rope.pos(pos) ;
	virus_costume_rope.size(size) ;
	virus_costume_rope.show() ;	
}

public void virus_mutation(int mutation) {
	if(virus_costume_rope != null && mutation != 0 && mutation != virus_costume_rope.get_mutation()) {
		virus_costume_rope.set_mutation(abs(mutation));
	}
}

public void virus_num(int num) {
	if(virus_costume_rope != null && num != 0 && num != virus_costume_rope.get_summits()) {
		virus_costume_rope.set_summits(abs(num));
	}
}

public void virus_node(int node) {
	if(virus_costume_rope != null && node != 0 && node != virus_costume_rope.get_node()) {
		virus_costume_rope.set_node(abs(node));
	}
}




























/**
* COSTUME INFO
* 2016-2019
* v 0.2.1
*/
// get costume
public int get_costume(int target) {
	costume_list() ;
	if(target >= 0 && target < costume_dict.size()) {
		return costume_dict.get(target).get(0) ;
	} else {
		System.err.println("Your target is out from the list") ;
		return 0 ;
	}
}

// return size of the arrayList costume
public int costumes_size() {
	costume_list() ;
	return costume_dict.size() ;
}




Info_int_dict costume_dict = new Info_int_dict();
boolean list_costume_is_built = false;
int ref_size_pic = -1;
// String costume_text_rope = null;
boolean fill_rope_is = true;
boolean stroke_rope_is = true;
boolean alpha_rope_is = true;
public void costume_list() {
	if(!list_costume_is_built) {
		/* 
		* add(name, code, renderer, type)
		* code: int constante to access directly
		* render: 2 = 2D ; 3 = 3D ;
		* type : 0 = shape ; 1 = bitmap ; 2 = svg  ; 3 = shape with just stroke component ; 4 = text
		*/
		costume_dict.add("NULL",r.NULL,0,0);

		costume_dict.add("PIXEL",PIXEL,2,1);

		costume_dict.add("POINT",POINT,2,0);
		costume_dict.add("ELLIPSE",ELLIPSE,2,0);
		costume_dict.add("RECT",RECT,2,0);
		costume_dict.add("LINE",LINE,2,0);

		costume_dict.add("TRIANGLE",TRIANGLE,2,0);
		costume_dict.add("SQUARE",SQUARE,2,0);
		costume_dict.add("PENTAGON",PENTAGON,2,0);
		costume_dict.add("HEXAGON",HEXAGON,2,0);
		costume_dict.add("HEPTAGON",HEPTAGON,2,0);
		costume_dict.add("OCTOGON",OCTOGON,2,0);
		costume_dict.add("NONAGON",NONAGON,2,0);
		costume_dict.add("DECAGON",DECAGON,2,0);
		costume_dict.add("HENDECAGON",HENDECAGON,2,0);
		costume_dict.add("DODECAGON",DODECAGON,2,0);

		//costume_dict.add("TEXT_ROPE",TEXT_ROPE,2,4);
		
		costume_dict.add("CROSS_RECT",CROSS_RECT,2,0);
		costume_dict.add("CROSS_BOX_2",CROSS_BOX_2,3,0);
		costume_dict.add("CROSS_BOX_3",CROSS_BOX_3,3,0);

		costume_dict.add("SPHERE_LOW",SPHERE_LOW,3,0);
		costume_dict.add("SPHERE_MEDIUM",SPHERE_MEDIUM,3,0);
		costume_dict.add("SPHERE_HIGH",SPHERE_HIGH,3,0);
		costume_dict.add("SPHERE",SPHERE,3,0);
		costume_dict.add("TETRAHEDRON",TETRAHEDRON,3,0);
		costume_dict.add("BOX",BOX,3,0);

		costume_dict.add("STAR",STAR,2,3);
		costume_dict.add("STAR_3D",STAR_3D,2,3);

		costume_dict.add("FLOWER",FLOWER,2,3);

		costume_dict.add("HOUSE",HOUSE,3,0);

		costume_dict.add("VIRUS",VIRUS,3,0);

		list_costume_is_built = true;
	}

	// add costume from your SVG or PNG
	if(ref_size_pic != costume_pic_list.size()) {
		for(Costume_pic c : costume_pic_list) {
			costume_dict.add(c.name, c.get_id(), 3, c.type) ;
		}
		ref_size_pic = costume_pic_list.size() ;
	}
}


// print list costume
public void print_list_costume() {
	if(!list_costume_is_built) {
		costume_list() ;
	}
	println("Costume have " + costume_dict.size() + " costumes.") ;
	if(list_costume_is_built) {
		for(int i = 0 ; i < costume_dict.size() ; i++) {
			String type = "" ;
			if(costume_dict.get(i).get(2) == 0 ) type = "shape" ;
			else if(costume_dict.get(i).get(2) == 1 ) type = "bitmap" ;
			else if(costume_dict.get(i).get(2) == 2 ) type = "scalable vector graphics" ;
			else if(costume_dict.get(i).get(2) == 3 ) type = "shape with no fill component" ;
			println("[ Rank:", i, "][ ID:",costume_dict.get(i).get(0), "][ Name:", costume_dict.get(i).get_name(), "][ Renderer:", costume_dict.get(i).get(1)+"D ][ Picture:", type, "]") ;
		}
	}
}
/**
* SETTING FX BACKGROUND
* v 0.0.1
* 2019-2019
*/

public void setting_fx_bg(ArrayList<FX> fx_list) {
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
public void setting_template_fx_bg(ArrayList<FX> fx_list) {
  init_fx(fx_list,set_template_fx_bg,FX_BG_TEMPLATE);
  vec3 colour = abs(vec3().sin_wave(frameCount,.1f,.02f,.03f));
  fx_set_colour(fx_list,set_template_fx_bg,colour.array());
}



// cellular
String set_cellular_fx_bg = "cellular fx background";
public void setting_cellular_fx_bg(ArrayList<FX> fx_list) {
  init_fx(fx_list,set_cellular_fx_bg,FX_BG_CELLULAR);
  vec4 colour = abs(vec4().sin_wave(frameCount,.1f,.02f,.03f,.04f));
  fx_set_colour(fx_list,set_cellular_fx_bg,colour.array());

  float sx = map(mouseX,0,width,0,1);
  float sy = map(mouseY,0,height,0,1);
  fx_set_speed(fx_list,set_cellular_fx_bg,sx,sy);

  int num = PApplet.parseInt(map(sin(frameCount*.01f),-1,1,1,20));
  fx_set_num(fx_list,set_cellular_fx_bg,num);
  fx_set_quality(fx_list,set_cellular_fx_bg,.5f);
}




// heart
String set_heart_fx_bg = "heart fx background";
public void setting_heart_fx_bg(ArrayList<FX> fx_list) {
  init_fx(fx_list,set_heart_fx_bg,FX_BG_HEART);
  vec4 colour = abs(vec4().sin_wave(frameCount,.1f,.02f,.03f,.04f));
  fx_set_colour(fx_list,set_heart_fx_bg,colour.array());

  float speed = map(mouseX,0,width,0,1);
  fx_set_speed(fx_list,set_heart_fx_bg,speed);

  int num = PApplet.parseInt(map(sin(frameCount*.01f),-1,1,1,20));
  fx_set_num(fx_list,set_heart_fx_bg,num);

  fx_set_quality(fx_list,set_heart_fx_bg,.5f); // 0 to 1

  fx_set_strength(fx_list,set_heart_fx_bg,10);
}


// necklace
String set_necklace_fx_bg = "necklace fx background";
public void setting_necklace_fx_bg(ArrayList<FX> fx_list) {
  init_fx(fx_list,set_necklace_fx_bg,FX_BG_NECKLACE);



  if(mousePressed) {
    float sx = map(mouseX,0,width,0,1);
    float sy = map(mouseY,0,height,0,1);
    fx_set_size(fx_list,set_necklace_fx_bg,sx,sy);


    float px = map(mouseX,0,width,0,1);
    float py = map(mouseY,0,height,0,1);
    // fx_set_pos(fx_list,set_necklace_fx_bg,px,py);
    fx_set_pos(fx_list,set_necklace_fx_bg,.5f,.5f);
  } 

  //float alpha = abs(sin(frameCount*.01));
  float alpha = 1;
  fx_set_colour(fx_list,set_necklace_fx_bg,alpha);

  //float speed = map(mouseX,0,width,0,1);
  float speed = .01f;
  fx_set_speed(fx_list,set_necklace_fx_bg,speed);

  int num = PApplet.parseInt(map(sin(frameCount*.001f),-1,1,10,80));
  // fx_set_num(fx_list,set_necklace_fx_bg,num);
  fx_set_num(fx_list,set_necklace_fx_bg,50);
}



// neon
String set_neon_fx_bg = "neon fx background";
public void setting_neon_fx_bg(ArrayList<FX> fx_list) {
  init_fx(fx_list,set_neon_fx_bg,FX_BG_NEON);

  if(mousePressed) {
    float px = map(mouseX,0,width,0,1);
    float py = map(mouseY,0,height,0,1);
    fx_set_pos(fx_list,set_neon_fx_bg,px,py);
  } 
  
  float speed = abs(sin(frameCount *.01f));
  speed = map(speed,0,1,.001f,.05f);
  fx_set_speed(fx_list,set_neon_fx_bg,speed);
}





// psy
String set_psy_fx_bg = "psy fx background";
public void setting_psy_fx_bg(ArrayList<FX> fx_list) {
  init_fx(fx_list,set_psy_fx_bg,FX_BG_PSY);

  if(mousePressed) {
    int num = PApplet.parseInt(map(mouseX,0,width,2,3));
    fx_set_num(fx_list,set_psy_fx_bg,num);
  } 
  
  float speed = abs(sin(frameCount *.01f));
  speed = map(speed,0,1,.0001f,.005f);
  fx_set_speed(fx_list,set_psy_fx_bg,speed);
}









// snow
String set_snow_fx_bg = "snow fx background";
public void setting_snow_fx_bg(ArrayList<FX> fx_list) {
  init_fx(fx_list,set_snow_fx_bg,FX_BG_SNOW);

  if(mousePressed) {
    vec3 colour = abs(vec3().sin_wave(frameCount,.01f,.02f,.03f));
    fx_set_colour(fx_list,set_snow_fx_bg,colour.array());

    float px = map(mouseX,0,width,0,1);
    float py = map(mouseY,0,height,0,1);
    fx_set_pos(fx_list,set_snow_fx_bg,px,py);
  } 
  
  float speed = map(mouseX,0,width,0,1);
  fx_set_speed(fx_list,set_snow_fx_bg,speed);
  
  float quality = abs(sin(frameCount *.01f));
  fx_set_quality(fx_list,set_snow_fx_bg,quality);
}










// voronoi three p
String set_voronoi_hex_fx_bg = "voronoi hex fx background";
public void setting_voronoi_hex_fx_bg(ArrayList<FX> fx_list) {
  init_fx(fx_list,set_voronoi_hex_fx_bg,FX_BG_VORONOI_HEX);

  if(mousePressed) {


    float size = map(mouseX,0,width,0,10);
    fx_set_size(fx_list,set_voronoi_hex_fx_bg,size);

    float threshold = map(sin(frameCount *.001f),-1,1,.01f,0.3f);
    fx_set_threshold(fx_list,set_voronoi_hex_fx_bg,threshold);
  }

  vec3 colour = abs(vec3().sin_wave(frameCount,.01f,.02f,.03f));
  fx_set_colour(fx_list,set_voronoi_hex_fx_bg,colour.array());

  float speed_mutation = .05f;
  float speed_colour = 1.f;
  // float speed = map(mouseX,0,width,0,1);
  fx_set_speed(fx_list,set_voronoi_hex_fx_bg,speed_mutation,speed_colour);

  float strength = map(sin(frameCount *.001f),-1,1,-0.05f,0.05f);
  fx_set_strength(fx_list,set_voronoi_hex_fx_bg,strength);

  fx_set_mode(fx_list,set_voronoi_hex_fx_bg,0); // two mode available for the moment 0 or 1
}




/**
* fx gui
* 2021-2021
* v 0.0.1
*/
public void instruction(boolean show_is) {
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
public void incrust_is(boolean is) {
	incrust_is = is;
}

public boolean incrust_is() {
	return incrust_is;
}

boolean reset = false;
public void reset(boolean reset){
	this.reset = reset;
}

public boolean reset_is() {
	return reset;
}



public void keyPressed_arrow() {
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

public void keyReleased_arrow_false() {
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

public boolean up_is() {
	return up_is;
}

public void up_is(boolean is) {
	up_is = is;
}

public boolean down_is() {
	return down_is;
}

public void down_is(boolean is) {
	down_is = is;
}

public boolean left_is() {
	return left_is;
}

public void left_is(boolean is) {
	left_is = is;
}

public boolean right_is() {
	return right_is;
}

public void right_is(boolean is) {
	right_is = is;
}

// space
boolean space_is;
public boolean space_is() {
	return space_is;
}

public void space_is(boolean is) {
	space_is = is;
}

public void space_switch() {
  space_is = space_is ? false : true;
}

// media
boolean media_is;
public boolean media_is() {
	return media_is;
}

public void media_is(boolean is) {
	media_is = is;
}


boolean instruction_is;
public boolean instruction_is() {
	return instruction_is;
}

public void instruction_is(boolean is) {
	instruction_is = is;
}

public void instruction_switch() {
  instruction_is = instruction_is ? false : true;
}


// folder_is
boolean folder_is = false;
public boolean folder_is() {
  return folder_is;
}

public void folder_is(boolean is) {
  folder_is = is;
}

// input_is
boolean input_is = false;
public boolean input_is() {
  return input_is;
}

public void input_is(boolean is) {
  input_is = is;
}

// display_movie_is
boolean display_movie_is = false;
public boolean display_movie_is() {
  return display_movie_is;
}

public void display_movie_is(boolean is) {
  display_movie_is = is;
}

// display_img_is
boolean display_img_is = false;
public boolean display_img_is() {
  return display_img_is;
}

public void display_img_is(boolean is) {
  display_img_is = is;
}
/**
UTILS FILTER SHADER
/**
* v 0.1.0
* 2019-2021
*
*/
R_Image_Manager lib_img = new R_Image_Manager();

public void add_media() {
	explore_folder(folder(),false,"jpeg","jpg"); 
	// don't need to add in MAJ extension, the algo check for the UPPERCASE
	if(folder_is() && get_files() != null && get_files().size() > 0) {
  	for(File f : get_files()) {
	  	println("file:",f);
			lib_img.load(f.getAbsolutePath());
	  }
		folder_is(false);
  }
}


PImage img_a,img_b;
public void render_mode_shader(int mode) {
	int index_img_0 = 0;
	int index_img_1 = 1;

	if(mode == 0) {
  	background(r.PINK);
  	setting_fx_post(fx_manager,false);
		draw_fx_post_on_tex(index_img_0);
	} else if(mode == 1) {
		setting_fx_post(fx_manager,true);
		draw_fx_post_on_g(index_img_0, index_img_1, img_noise_1, img_noise_2);
		// draw_fx_post_on_g(img_a,img_b,img_noise_1,img_noise_2);
	} else if(mode == 2) {
		setting_fx_bg(fx_bg_manager);
		draw_fx_bg();
	}
}

/**
* background fx
* 2019-2019
*/
public void draw_fx_bg() {
  // select_fx_background(get_fx(fx_bg_manager,set_template_fx_bg));
	// select_fx_background(get_fx(fx_bg_manager,set_cellular_fx_bg));
	// select_fx_background(get_fx(fx_bg_manager,set_heart_fx_bg));
	// select_fx_background(get_fx(fx_bg_manager,set_necklace_fx_bg));
	// select_fx_background(get_fx(fx_bg_manager,set_neon_fx_bg));
	// select_fx_background(get_fx(fx_bg_manager,set_psy_fx_bg));
	// select_fx_background(get_fx(fx_bg_manager,set_snow_fx_bg));
	select_fx_background(get_fx(fx_bg_manager,set_voronoi_hex_fx_bg));
}


float rot_x; 
float rot_y;
public void other_stuff() {
	translate(width/2,height/2);
	rotateX(rot_x += .01f);
	rotateY(rot_y += .02f);
	box(600,100,100);
}





PImage img_noise_1, img_noise_2;
public void set_pattern(int w, int h, int mode, boolean event) {
	if(event || img_noise_1 == null && img_noise_2 == null) {
		if(mode == RGB) {
			img_noise_1 = generate_pattern(3,w,h); // warp RGB
			img_noise_2 = generate_pattern(3,w,h); // warp RGB
		} else if(mode == GRAY) {
			img_noise_1 = generate_pattern(0,w,h); // warp GRAY
			img_noise_2 = generate_pattern(0,w,h); // warp GRAY
		}	
	} 
}

public PGraphics generate_pattern(int mode, int sx, int sw) {
	vec3 inc = vec3(random(1)*random(1),random(1)*random(1),random(1)*random(1));
	if(mode == 0) {
		// black and white
		return pattern_noise(sx,sw,inc.x);
	} else if(mode == 3) {
		// rgb
		return pattern_noise(sx,sw,inc.array());
	}	else return null;
}

/**
* set img from path
*/
PImage img_input;
public void set_img(PImage img) {
	if(img_input == null && img != null && !media_is()) {
		println("PImage img size", img.width, img.height);
		// img_input = img.copy();
		img_input = img;
		media_is(true);
	} else if(img_input != null && media_is()) {
	  if(window_ref == null || !window_ref.equals(ivec2(img_input.width,img_input.height))) {
	  	surface.setSize(img_input.width,img_input.height);
	  	window_ref = ivec2(img_input.width,img_input.height);
	  }
	}
}

// void set_img(String path) {
// 	if(img_input == null && path != null && !media_is()) {
// 		println(path);
// 		if(extension_is(path,"jpg","jpeg")) {
// 			println("method set_img():",path);
// 			img_input = loadImage(path);
// 			media_is(true);
// 		} else {
// 			printErr("method set_img(): [",path,"] don't match for class PImage");
// 		}

// 	} else if(img_input != null && media_is()) {
// 	  if(window_ref == null || !window_ref.equals(ivec2(img_input.width,img_input.height))) {
// 	  	surface.setSize(img_input.width,img_input.height);
// 	  	window_ref = ivec2(img_input.width,img_input.height);
// 	  }
// 	}
// }

/**
* PGraphics converter for PImage or Movie
* 2018-2018
* v 0.0.4
*/

ArrayList <PGraphics> pg_temp_list;

public PGraphics to_pgraphics(Object obj) {
	return to_pgraphics(obj,0);
}

public PGraphics to_pgraphics(Object obj, int target) {
	// manage list of PGraphics
	if(pg_temp_list == null) {
		pg_temp_list = new ArrayList<PGraphics>();
	}
	if(pg_temp_list.size() < target+1) {
		PGraphics pg = createGraphics(0,0,get_renderer());
		pg_temp_list.add(pg);
	} 

	PGraphics pg_temp = pg_temp_list.get(target);
	if(obj instanceof Movie) {

		int w = ((Movie)obj).width;
		int h = ((Movie)obj).height;
		pg_temp = set_pgraphics_temp(pg_temp,w,h);
		if(obj != null && pg_temp != null && pg_temp.width > 1 && pg_temp.height > 1) {
			pg_temp.beginDraw();
			pg_temp.image((Movie)obj,0,0,w,h);
			pg_temp.endDraw();
		}
	} else if(obj instanceof PImage) {
		int w = ((PImage)obj).width;
		int h = ((PImage)obj).height;
		pg_temp = set_pgraphics_temp(pg_temp,w,h);
		// pg_temp = createGraphics(w,h,get_renderer());
		if(obj != null && pg_temp != null && pg_temp.width > 1 && pg_temp.height > 1) {
			//pass_render_to_pgraphics((PImage)obj);
			pg_temp.beginDraw();
			pg_temp.image((PImage)obj,0,0,w,h);
			pg_temp.endDraw();	
		}
	} else {
		printErr("method to_pgraphics() wait for Object from class Movie, PImage or PGraphics");
	}

	// image(pg_temp);
	return pg_temp;
}

public PGraphics set_pgraphics_temp(PGraphics pg, int w, int h) {
	if(pg == null || pg.width != w || pg.height != h) {
		pg = createGraphics(w,h,get_renderer());
	}
	return pg; 
}

/**
Work around filter g it's hard,
so the option to copy PGraphics g is used, but not sure that's be good with huge rendering sinze
*/
public void draw_fx_post_on_g(int target_img_0, int target_img_1, PImage pattern_1, PImage pattern_2) {
	PImage img_1 = lib_img.get(target_img_0);
	PImage img_2 = lib_img.get(target_img_1);
	if(display_movie_is()) {
		set_movie(input_path());
		if(movie_input != null && !window_change_is()) {
			filter_g(movie_input, img_1, img_2, pattern_1,pattern_2);
		}
	}

	if(display_img_is()) {
		set_img(img_1);
		if(img_input != null && !window_change_is()) {
			filter_g(img_input, img_1, img_2, pattern_1,pattern_2);
		}
	}
	// if(movie_input != null && display_movie_is() && !window_change_is()) {
	// 	filter_g(movie_input, img_1, img_2, pattern_1,pattern_2);
	// }

	// if(img_input != null && display_img_is() && !window_change_is()) {
	// 	filter_g(img_input, img_1, img_2, pattern_1,pattern_2);
	// }

	// if((movie_input != null || img_input != null) && !window_change_is()) {
  //   if(movie_input != null && display_movie_is()) {
  //   	filter_g(movie_input, img_1, img_2, pattern_1,pattern_2);
  //   }
	// 	if(img_input != null && display_img_is()) {
  //   	filter_g(img_input, img_1, img_2, pattern_1,pattern_2);
  //   }
	// }
}

PImage temp;
float angle_g;

public void filter_g(PImage input, PImage img_1, PImage img_2, PImage pattern_1, PImage pattern_2) {
	boolean with_g = true;
  if(with_g) {
  	// image(movie_input,CENTER);
  	background(input,CENTER);
  	if(incrust_is()) {
  		fx_inc_copy(g);
  	}
  	render_post_fx(g,input,img_1,img_2,pattern_1,pattern_2);
  } else {
  	render_post_fx(input,null,img_1,img_2,pattern_1,pattern_2);
	}
	if(incrust_is()) {
		fx_inc(g);
	}
}


public void render_post_fx(PImage src_1, PImage src_2, PImage img_1, PImage img_2, PImage pattern_1, PImage pattern_2) {
	//select_filter(src_1,movie_input,null,FX_HALFTONE_DOT,FX_SCREEN);
	// select_filter(src,null,null,FX_SPLIT_RGB);
	//select_filter(src_1,null,null,FX_SPLIT_RGB);
	// println(get_fx(0).get_name());
	// select_filter(src,null,null,get_fx(0));

	// println(get_fx(3).get_name());

	// select_fx_post(src_1,null,null,get_fx(fx_manager,set_blur_gaussian));
	// select_fx_post(src_1,null,null,get_fx(fx_manager,set_blur_circular));
	// select_fx_post(src_1,null,null,get_fx(fx_manager,set_blur_radial));

	// select_fx_post(src_1,null,null,get_fx(fx_manager,set_colour_change_a));
	// select_fx_post(src_1,null,null,get_fx(fx_manager,set_colour_change_b));
  
  //select_fx_post(src_2,null,null,get_fx(fx_manager,set_datamosh)); // we must pass the movie

	// select_fx_post(src_1,null,null,get_fx(fx_manager,set_dither_bayer_8));

	// select_fx_post(src_1,null,null,get_fx(fx_manager,set_flip));

	// select_fx_post(src_1,null,null,get_fx(fx_manager,set_grain));
	// select_fx_post(src_1,null,null,get_fx(fx_manager,set_grain_scatter));

	// select_fx_post(src_1,null,null,get_fx(fx_manager,set_halftone_dot));
	// select_fx_post(src_1,null,null,get_fx(fx_manager,set_halftone_line));
	select_fx_post(src_1,null,null,get_fx(fx_manager,set_halftone_multi));

	// select_fx_post(src_1,null,null,get_fx(fx_manager,set_image));
	// select_fx_post(img_1,img_2,null,get_fx(fx_manager,set_mask));

	// select_fx_post(src_1,null,null,get_fx(fx_manager,set_level));

	// select_fx_post(src_1,src_1,null,get_fx(fx_manager,set_mix));

	// select_fx_post(src_1,null,null,get_fx(fx_manager,set_pixel));

	// select_fx_post(src_1,null,null,get_fx(fx_manager,set_reac_diff));
	// select_fx_post(src_1,null,null,get_fx(fx_manager,set_split));

	// select_fx_post(src_1,src_1,null,get_fx(fx_manager,set_threshold));

	// select_fx_post(src_1,null,null,get_fx(fx_manager,set_warp_proc));

	// select_fx_post(src_1,img_1,img_1,get_fx(fx_manager,set_warp_tex)); 
	// select_fx_post(src_1,noise_nb,noise_nb,get_fx(fx_manager,set_warp_tex)); 
	//select_fx_post(src_1,pattern_1,pattern_2,get_fx(fx_manager,set_warp_tex_a)); 
	// select_fx_post(src_1,src_1,null,get_fx(fx_manager,set_warp_tex_b)); 


	//select_fx_post(src_1,src_1,FX_HALFTONE_DOT,FX_WARP_PROC,FX_SPLIT_RGB);
}








PImage inc_fx;
public void fx_inc_copy(PImage src) {
	if(inc_fx == null || inc_fx.width != src.width || inc_fx.height != src.height) {
		inc_fx = createImage(src.width,src.height,RGB);
	}
	inc_fx.copy(src,0,0,src.width,src.height, 0,0,src.width,src.height);
}






/**
* FX on texture PGraphics
* 2019-2021
* v 0.1.0
*/
public void draw_fx_post_on_tex(int index_img) {
	if(display_movie_is() && movie_input != null) {
		set_movie(input_path());
		render = to_pgraphics(movie_input);
	}
	if(display_img_is() && img_input != null) {
		set_img(lib_img.get(index_img));
		render = to_pgraphics(img_input);
	}
	//set_img(input_path());
  // if(img_input != null) {
  // 	// render = to_pgraphics(img_input);
  // } else if(movie_input != null) {
  // 	// render = to_pgraphics(movie_input);
  // }
	// set_shape();
	filtering_render();
}




ivec2 window_ref;
PImage layer;
public void filtering_render() {
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


    float threshold = noise(millis() * 0.0001f, frameCount * 0.01f) * 0.15f;
    float strength = abs(sin(frameCount *.01f))*10;
    vec2 offset_red = vec2().sin_wave(frameCount,.01f,.02f);
  	vec2 offset_green = vec2().cos_wave(frameCount,.001f,.01f);
  	vec2 offset_blue = vec2().sin_wave(frameCount,.005f,.002f);
		// pass_render(fx_datamosh(render,on_g,threshold,strength,offset_red,offset_green,offset_blue));
    
    //pass_render(fx_mask(img_a,img_b,on_g));
    int mode = 0 ;
    int num_threshold = 24;
    vec2 range_threshold = vec2(0,1);
    vec4 layer_rgba = vec4(1);
		pass_render(fx_mask(render,render,on_g,filter_is,mode,num_threshold,range_threshold,layer_rgba));
		
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

public void pass_render(PGraphics temp) {
	render.loadPixels();
	temp.loadPixels();
	render.pixels = temp.pixels;
	render.updatePixels();
}



float angle_shape;
public void set_shape() {
	if(render == null) {
		render = createGraphics(g.width,g.height,get_renderer());
	} else {
		render.beginDraw();
		render.background(255,0,0,100);
		render.rectMode(CENTER);
	  angle_shape += .01f;
		render.pushMatrix();
		render.translate(width/2,height/2);
		render.rotate(angle_shape);
	  render.rect(0,0,150,150);
	  render.popMatrix();
	  render.endDraw();
	}
}

/**
* SETTING FX POST method
* v 0.2.6
* 2019-2019
*/


// INCRUSTATION
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
int mode = 1;
public void fx_inc(PImage src) {
  boolean on_g = true;
  boolean filter_is = true;
  if(mousePressed) {
    mode = ceil(random(22));
    println("mode",mode);
  }
  
  // int mode = 2;
  // vec3 level_source = vec3(1);
  vec3 level_source = abs(vec3().sin_wave(frameCount,.01f,.02f,.03f));
  vec3 level_layer = abs(vec3().cos_wave(frameCount,.02f,.04f,.01f));
  if(src.width == inc_fx.width && src.height == inc_fx.height) {
    fx_mix(src,inc_fx,on_g,filter_is,mode,level_source,level_layer);
  }
}












// CLASSIC POST FX
public void setting_fx_post(ArrayList<FX> fx_list, boolean on_g) {
  setting_blur_circular(fx_list,on_g);
  setting_blur_gaussian(fx_list,on_g);
  setting_blur_radial(fx_list,on_g);

  setting_colour_change_a(fx_list,on_g);
  setting_colour_change_b(fx_list,on_g);
  
  setting_datamosh(fx_list,on_g);
  setting_dither_bayer_8(fx_list,on_g);

  setting_flip(fx_list,on_g);

  setting_grain(fx_list,on_g);
  setting_grain_scatter(fx_list,on_g);

  setting_haltone_dot(fx_list,on_g);
  setting_haltone_line(fx_list,on_g);
  setting_haltone_multi(fx_list,on_g);

  setting_image(fx_list,on_g);

  setting_level(fx_list,on_g);

  setting_mask(fx_list,on_g);
  setting_mix(fx_list,on_g);

  setting_pixel(fx_list,on_g);

  setting_reac_diff(fx_list,on_g);

  setting_split(fx_list,on_g);

  setting_threshold(fx_list,on_g);

  setting_warp_proc(fx_list,on_g);
  setting_warp_tex_a(fx_list,on_g);
  setting_warp_tex_b(fx_list,on_g);
}



// blur circular
String set_blur_circular = "blur circular";
public void setting_blur_circular(ArrayList<FX> fx_list, boolean on_g) {
  init_fx(fx_list,set_blur_circular,FX_BLUR_CIRCULAR);
  fx_set_on_g(fx_list,set_blur_circular,on_g);
  fx_set_pg_filter(fx_list,set_blur_circular,true);
  fx_set_num(fx_list,set_blur_circular,36);
  float strength = map(mouseX,0,width,0,100);
  fx_set_strength(fx_list,set_blur_circular,strength);
}


// blur gaussian
String set_blur_gaussian = "blur gaussian";
public void setting_blur_gaussian(ArrayList<FX> fx_list, boolean on_g) {
  init_fx(fx_list,set_blur_gaussian,FX_BLUR_GAUSSIAN);
  fx_set_on_g(fx_list,set_blur_gaussian,on_g);
  fx_set_pg_filter(fx_list,set_blur_gaussian,true);
  float x = mouseX -(width/2);
  int max_blur = 40;
  float size = map(abs(x),0,width/2,0,max_blur);
  fx_set_strength(fx_list,set_blur_gaussian,size);
}


// blur radial
String set_blur_radial = "blur radial";
float current_scale_blur_radial;
public void setting_blur_radial(ArrayList<FX> fx_list, boolean on_g) {
  init_fx(fx_list,set_blur_radial,FX_BLUR_RADIAL);
  fx_set_on_g(fx_list,set_blur_radial,on_g);
  fx_set_pg_filter(fx_list,set_blur_radial,true);
  
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
  current_scale_blur_radial += var *.01f;
  if(current_scale_blur_radial < -range_blur) {
    current_scale_blur_radial = -range_blur;
  } else if(current_scale_blur_radial > range_blur) {
    current_scale_blur_radial = range_blur;
  }
  fx_set_scale(fx_list,set_blur_radial,current_scale_blur_radial);

  float strength = map(sin(frameCount *.01f),-1,1,5,100);
  fx_set_strength(fx_list,set_blur_radial,strength);
}









// colour change
String set_colour_change_a = "colour change A";
public void setting_colour_change_a(ArrayList<FX> fx_list, boolean on_g) {
  init_fx(fx_list,set_colour_change_a,FX_COLOUR_CHANGE_A);
  fx_set_on_g(fx_list,set_colour_change_a,on_g);
  fx_set_pg_filter(fx_list,set_colour_change_a,true);
  
  if(mousePressed) {
    vec3 m0 = vec3().sin_wave(frameCount,.001f,.02f,.005f).mult(10);
    vec3 m1 = vec3().cos_wave(frameCount,.001f,.02f,.005f).mult(10);
    vec3 m2 = vec3().sin_wave(frameCount,.01f,.002f,.002f).mult(10);
    // vec3 m0 = vec3(-1,0,1);
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
public void setting_colour_change_b(ArrayList<FX> fx_list, boolean on_g) {
  init_fx(fx_list,set_colour_change_b,FX_COLOUR_CHANGE_B);
  fx_set_on_g(fx_list,set_colour_change_b,on_g);
  fx_set_pg_filter(fx_list,set_colour_change_b,true);
  if(mousePressed) {
    float angle = map(mouseX,0,width,-PI,PI);
    fx_set_angle(fx_list,set_colour_change_b,angle);
    float strength = map(mouseY,0,height,1,10);
    fx_set_strength(fx_list,set_colour_change_b,strength);
  }
}






// dither bayer 8
String set_datamosh = "datamosh";
public void setting_datamosh(ArrayList<FX> fx_list, boolean on_g) {
  init_fx(fx_list,set_datamosh,FX_DATAMOSH);
  fx_set_on_g(fx_list,set_datamosh,on_g);
  fx_set_pg_filter(fx_list,set_datamosh,true);

  if(mousePressed) {
    float threshold = abs(sin(frameCount *.01f)) *0.1f;
   // println("threshold",threshold);
    fx_set_threshold(fx_list,set_datamosh,threshold);
    vec2 offset_red = vec2().sin_wave(frameCount,.01f,.02f).mult(.1f);
    vec2 offset_green = vec2().cos_wave(frameCount,.001f,.01f).mult(.1f);
    vec2 offset_blue = vec2().sin_wave(frameCount,.005f,.002f).mult(.1f);
    fx_set_pair(fx_list,set_datamosh,0,offset_red.array());
    fx_set_pair(fx_list,set_datamosh,1,offset_green.array());
    fx_set_pair(fx_list,set_datamosh,2,offset_blue.array());

    float strength = map(mouseX,0,width,-100,100);
    fx_set_strength(fx_list,set_datamosh,strength);
  }
}





// dither bayer 8
String set_dither_bayer_8 = "dither bayer 8";
public void setting_dither_bayer_8(ArrayList<FX> fx_list, boolean on_g) {
  init_fx(fx_list,set_dither_bayer_8,FX_DITHER_BAYER_8);
  fx_set_on_g(fx_list,set_dither_bayer_8,on_g);
  fx_set_pg_filter(fx_list,set_dither_bayer_8,true);

  if(keyPressed) {
    fx_set_mode(fx_list,set_dither_bayer_8,0); // gray dither
  } else {
    fx_set_mode(fx_list,set_dither_bayer_8,1); // rgb dither
  }

  if(mousePressed) {
    float level_x = abs(sin(frameCount*.002f)); // for gray and rgb model
    float level_y = abs(sin(frameCount*.001f)); // for the rgb model
    float level_z = abs(sin(frameCount*.005f)); // for the rgb model
    fx_set_level_source(fx_list,set_dither_bayer_8,level_x,level_y,level_z);
  }
}




// grain
String set_flip = "flip";
public void setting_flip(ArrayList<FX> fx_list, boolean on_g) {
  init_fx(fx_list,set_flip,FX_FLIP);
  fx_set_on_g(fx_list,set_flip,on_g);
  fx_set_pg_filter(fx_list,set_flip,true);
  fx_set_event(fx_list,set_flip,0,mousePressed,keyPressed);
}







// grain
String set_grain = "grain";
public void setting_grain(ArrayList<FX> fx_list, boolean on_g) {
  init_fx(fx_list,set_grain,FX_GRAIN);
  fx_set_on_g(fx_list,set_grain,on_g);
  fx_set_pg_filter(fx_list,set_grain,true);
  float grain = random(1);
  fx_set_offset(fx_list,set_grain,grain);
  int mode = 1;
  fx_set_mode(fx_list,set_grain,mode);
 
}






// grain scatter
String set_grain_scatter = "grain scatter";
public void setting_grain_scatter(ArrayList<FX> fx_list, boolean on_g) {
  init_fx(fx_list,set_grain_scatter,FX_GRAIN_SCATTER);
  fx_set_on_g(fx_list,set_grain_scatter,on_g);
  fx_set_pg_filter(fx_list,set_grain_scatter,true);
  if(mousePressed) {
    float strength = map(mouseX,0,width,-100,100);
    fx_set_strength(fx_list,set_grain_scatter,strength);
  }
}






// halftone dot
String set_halftone_dot = "haltone dot";
public void setting_haltone_dot(ArrayList<FX> fx_list, boolean on_g) {
  init_fx(fx_list,set_halftone_dot,FX_HALFTONE_DOT);
  fx_set_on_g(fx_list,set_halftone_dot,on_g);
  fx_set_pg_filter(fx_list,set_halftone_dot,true);

  if(mousePressed) {
    fx_set_threshold(fx_list,set_halftone_dot,sin(frameCount *.01f));
    fx_set_pos(fx_list,set_halftone_dot,mouseX,mouseY);
    fx_set_size(fx_list,set_halftone_dot,(abs(sin(frameCount *.01f))) *30 +1);
    fx_set_angle(fx_list,set_halftone_dot,sin(frameCount *.001f) *TAU);
  }
}






// halftone line
String set_halftone_line = "halftone line";
public void setting_haltone_line(ArrayList<FX> fx_list, boolean on_g) {
  init_fx(fx_list,set_halftone_line,FX_HALFTONE_LINE);
  fx_set_on_g(fx_list,set_halftone_line,on_g);
  fx_set_pg_filter(fx_list,set_halftone_line,true);

  if(mousePressed) {
    fx_set_mode(fx_list,set_halftone_line,0); 
    int num_line = (int)map(mouseY,0,height,20,100); 
    fx_set_num(fx_list,set_halftone_line,num_line);  
    fx_set_quality(fx_list,set_halftone_line,.2f);

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
public void setting_haltone_multi(ArrayList<FX> fx_list, boolean on_g) {
  init_fx(fx_list,set_halftone_multi,FX_HALFTONE_MULTI);
  fx_set_on_g(fx_list,set_halftone_multi,on_g);
  fx_set_pg_filter(fx_list,set_halftone_multi,true);

  if(mousePressed) {
    float px = map(mouseX,0,width,0,1);
    float py = map(mouseY,0,height,0,1);
    fx_set_pos(fx_list,set_halftone_multi,px,py); // 0 to 1
    float quality = 2;
    // float quality = map(mouseX,0,width,0,32);
    fx_set_quality(fx_list,set_halftone_multi,quality); // 1 to 16++

    // float size =.9;
    float size = 1 + abs(sin(frameCount *.01f))*1;
    fx_set_size(fx_list,set_halftone_multi,size); // 0 to 2++

    // float angle = 0;
    float angle = sin(frameCount *.001f) *TAU;
    fx_set_angle(fx_list,set_halftone_multi,angle); // in radians
    
    float threshold = .2f;
    // float threshold = map(mouseY,0,height,0,2);
    fx_set_threshold(fx_list,set_halftone_multi,threshold); // from 0 to 2++
    
    float saturation = abs(sin(frameCount *.01f));
    // float saturation = .5;
    fx_set_saturation(fx_list,set_halftone_multi,saturation); // from 0 to 1
    // fx_set_pos(fx_list,set_halftone_multi,mouseX,mouseY);
    fx_set_mode(fx_list,set_halftone_multi,0); // from 0 to 2
  }
}








// image
String set_image = "image";
public void setting_image(ArrayList<FX> fx_list, boolean on_g) {
  init_fx(fx_list,set_image,FX_IMAGE);
  fx_set_on_g(fx_list,set_image,on_g);
  fx_set_pg_filter(fx_list,set_image,true);

  if(mousePressed) {
    float px = map(mouseX,0,width,0,1);
    float py = map(mouseY,0,height,0,1);
    fx_set_pos(fx_list,set_image,px,py);

    vec3 rgb = abs(vec3().sin_wave(frameCount,.01f,.02f,.03f));
    fx_set_colour(fx_list,set_image,rgb.red(),rgb.blu(),rgb.gre());

    // float size =.9;
    float scale = abs(sin(frameCount *.02f))*3;
    fx_set_size(fx_list,set_image,scale,scale); // 0 to 2++

    float curtain_pos = abs(sin(frameCount *.05f)) *.5f;
    fx_set_cardinal(fx_list,set_image,curtain_pos,curtain_pos,curtain_pos,curtain_pos); // 0 to 2++
  }

  fx_set_mode(fx_list,set_image,r.SCALE);
}







// level
String set_level = "level";
public void setting_level(ArrayList<FX> fx_list, boolean on_g) {
  init_fx(fx_list,set_level,FX_LEVEL);
  fx_set_on_g(fx_list,set_level,on_g);
  fx_set_pg_filter(fx_list,set_level,true);
  fx_set_mode(fx_list,set_level,0);
  if(mousePressed) {
    vec3 level = abs(vec3().sin_wave(frameCount,.01f,.02f,.04f));
    fx_set_level_source(fx_list,set_level,level.array());
  }
}



// mask
String set_mask = "mask";
public void setting_mask(ArrayList<FX> fx_list, boolean on_g) {
  init_fx(fx_list,set_mask,FX_MASK);
  fx_set_on_g(fx_list,set_mask,on_g);
  fx_set_pg_filter(fx_list,set_mask,true);
  fx_set_mode(fx_list,set_mask,0);
  fx_set_num(fx_list,set_mask,12);
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
public void setting_mix(ArrayList<FX> fx_list, boolean on_g) {
  init_fx(fx_list,set_mix,FX_MIX);
  fx_set_on_g(fx_list,set_mix,on_g);
  fx_set_pg_filter(fx_list,set_mix,true);
  // fx_set_mode(set_mix,1); // produit - multiply
  fx_set_mode(fx_list,set_mix,8); 

  vec3 level_source = abs(vec3().sin_wave(frameCount,.001f,.003f,.005f));
  vec3 level_layer = abs(vec3().cos_wave(frameCount,.002f,.001f,.001f));
  fx_set_level_source(fx_list,set_mix,level_source.array());
  fx_set_level_layer(fx_list,set_mix,level_layer.array());

}









// pixel
String set_pixel = "pixel";
boolean effect_pixel_is;
public void setting_pixel(ArrayList<FX> fx_list, boolean on_g) {
  init_fx(fx_list,set_pixel,FX_PIXEL);
  fx_set_on_g(fx_list,set_pixel,on_g);
  fx_set_pg_filter(fx_list,set_pixel,true);

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
    
    float h = abs(sin(frameCount *.01f)); // from 0 to 1 where
    float s = map(mouseY,0,height,0,1); // from 0 to 1 where
    float b = map(mouseX,0,width,0,1); // from 0 to 1 where
    if(s < 0) s = 0; else if (s > 1) s = 1;
    if(b < 0) b = 0; else if (b > 1) s = 1;
    fx_set_level_source(fx_list,set_pixel,h,s,b);
  }
}








String set_reac_diff = "reaction diffusion";
public void setting_reac_diff(ArrayList<FX> fx_list, boolean on_g) {
  init_fx(fx_list,set_reac_diff,FX_REAC_DIFF);
  fx_set_on_g(fx_list,set_reac_diff,on_g);
  fx_set_pg_filter(fx_list,set_reac_diff,true);

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

   vec2 scale = vec2(.6f);
   fx_set_scale(fx_list,set_reac_diff,scale.array());

  int rd_iteration = 20;
  fx_set_num(fx_list,set_reac_diff,rd_iteration);
  
}









String set_split = "split rgb";
public void setting_split(ArrayList<FX> fx_list, boolean on_g) {
  init_fx(fx_list,set_split,FX_SPLIT_RGB);
  fx_set_on_g(fx_list,set_split,on_g);
  fx_set_pg_filter(fx_list,set_split,true);

  if(mousePressed) {
    /*
    float ox_red = map(mouseX,0,width,0,1);
    float oy_red = map(mouseY,0,height,0,1);
    */
    vec2 offset_red = vec2().cos_wave(frameCount,.01f,.02f);
    vec2 offset_green = vec2().sin_wave(frameCount,.01f,.02f);
    vec2 offset_blue = vec2().cos_wave(frameCount,.004f,.01f);
    fx_set_pair(fx_list,set_split,0,offset_red.array());
    fx_set_pair(fx_list,set_split,1,offset_green.array());
    fx_set_pair(fx_list,set_split,2,offset_blue.array());
  }
}



// dither
String set_threshold = "threshold";
public void setting_threshold(ArrayList<FX> fx_list, boolean on_g) {
  init_fx(fx_list,set_threshold,FX_THRESHOLD);
  fx_set_on_g(fx_list,set_threshold,on_g);
  fx_set_pg_filter(fx_list,set_threshold,true);

  if(keyPressed) {
    fx_set_mode(fx_list,set_threshold,0); // gray threshold
  } else {
    fx_set_mode(fx_list,set_threshold,1); // rgb threshold
  }

  if(mousePressed) {
    float level_x = map(mouseX,0,width,0,1); // for gray and rgb model
    float level_y = abs(sin(frameCount*.01f)); // for the rgb model
    float level_z = abs(sin(frameCount*.004f)); // for the rgb model
    fx_set_level_source(fx_list,set_threshold,level_x,level_y,level_z);
  }
}



// WARP setting
String set_warp_proc = "warp procedural";
public void setting_warp_proc(ArrayList<FX> fx_list, boolean on_g) {
  init_fx(fx_list,set_warp_proc,FX_WARP_PROC);
  fx_set_on_g(fx_list,set_warp_proc,on_g);
  fx_set_pg_filter(fx_list,set_warp_proc,true);
  if(mousePressed) {
    fx_set_strength(fx_list,set_warp_proc,map(mouseX,0,width,5,20));
  }
}


String set_warp_tex_a = "warp textural A";
public void setting_warp_tex_a(ArrayList<FX> fx_list, boolean on_g) {
  init_fx(fx_list,set_warp_tex_a,FX_WARP_TEX_A);
  fx_set_on_g(fx_list,set_warp_tex_a,on_g);
  fx_set_pg_filter(fx_list,set_warp_tex_a,true);

  fx_set_mode(fx_list,set_warp_tex_a,0);
  if(mousePressed) {
    fx_set_strength(fx_list,set_warp_tex_a,map(mouseX,0,width,-1,1));
  }
}





String set_warp_tex_b = "warp textural B";
public void setting_warp_tex_b(ArrayList<FX> fx_list, boolean on_g) {
  init_fx(fx_list,set_warp_tex_b,FX_WARP_TEX_B);
  fx_set_on_g(fx_list,set_warp_tex_b,on_g);
  fx_set_pg_filter(fx_list,set_warp_tex_b,true);
  
  if(mousePressed) {
    float strength = map(mouseX,0,width,-.2f,.2f);
    fx_set_strength(fx_list,set_warp_tex_b,strength);
  }
}



/**
* fx utils movie
* 2021-2021
* set movie from path
*/

Movie movie_input;
public void set_movie(String path) {
	if(movie_input == null && path != null && !media_is()) {
		if(extension_is(path,"avi","mov","mp4","mpg")) {
			println("method set_movie():",path);
			movie_input = new Movie(this,path);
			movie_input.loop();
			media_is(true);
		} else {
			printErr("method set_movie(): [",path,"] don't match for class Movie");
		}
	} else if(movie_input != null && media_is()) {
	  if(window_ref == null || !window_ref.equals(ivec2(movie_input.width,movie_input.height))) {
	  	surface.setSize(movie_input.width,movie_input.height);
	  	window_ref = ivec2(movie_input.width,movie_input.height);
	  }
	}
}


boolean reset_movie_speep_is;
boolean go_left_is;
boolean go_right_is;
boolean go_left_is_ref;
boolean go_right_is_ref;
public void remote_command_movie() {
	if(movie_input != null) {
		if(up_is()) set_movie_volume(0.01f);
		if(down_is()) set_movie_volume(-0.01f);

		if(right_is()) {
			go_left_is = false;
			go_right_is = true;
			set_movie_direction(0.5f);
		}
		if(left_is()) {
			go_left_is = true;
			go_right_is = false;
			set_movie_direction(-0.5f);
		}

		if(space_is()) {
			reset_movie_speep_is = true;
			movie_input.pause(); 
		} else {
			movie_input.loop();
		}

		if(reset_movie_speep_is || go_left_is != go_left_is_ref || go_right_is != go_right_is_ref) {
			go_left_is_ref = go_left_is;
			go_right_is_ref = go_right_is;
			mult_speed_movie = 1;
			reset_movie_speep_is = false;
		}
	}
}


float movie_volume = 1;
public void set_movie_volume(float inc) {
	movie_volume += inc;
	if(movie_volume < 0) {
		movie_volume = 0;
	} else if(movie_volume > 1) {
		movie_volume = 1;
	}
	if(movie_input != null) {
		movie_input.volume(movie_volume);
	}
}


int mult_speed_movie = 1;
public void set_movie_speed(int inc) {
	mult_speed_movie += inc;
	if(mult_speed_movie == 0 && inc > 0) mult_speed_movie = 1;
	if(mult_speed_movie == 0 && inc < 0) mult_speed_movie = -1;

}


public void set_movie_direction(float inc) {
	float speed = inc*abs(mult_speed_movie);
	println(speed);
	if(speed > 0) {
		movie_input.speed(speed);
	} else {
		float jump = movie_input.time() + speed;
		if(jump < 0) jump = 0;
		if(jump > movie_input.duration()) jump = movie_input.duration();
		movie_input.jump(jump);
	}
}


/**
* split rgb noise
* @see @stanlepunk
* @see https://github.com/StanLepunK/Shader
* v 0.0.6
* 2019-2019
*/

// use setting
public PGraphics fx_split_rgb_noise(PImage source, FX fx) {
	return fx_split_rgb_noise(source,fx.on_g(),vec2(fx.get_offset()));
}



// main
PShader fx_split_rgb_noise;
PGraphics pg_split_rgb_noise;
public PGraphics fx_split_rgb_noise(PImage source, boolean on_g, vec2 offset) {
	if(!on_g && (pg_split_rgb_noise == null 
								|| (source.width != pg_split_rgb_noise.width 
								&& source.height != pg_split_rgb_noise.height))) {
		pg_split_rgb_noise = createGraphics(source.width,source.height,get_renderer());
	}

	if(fx_split_rgb_noise == null) {
		String path = get_fx_post_path()+"split_rgb_noise.glsl";
		if(fx_post_rope_path_exists) {
			fx_split_rgb_noise = loadShader(path);
			println("load shader:",path);
		}
	} else {
    if(on_g) set_shader_flip(fx_split_rgb_noise,source);
		fx_split_rgb_noise.set("texture_source",source);
		fx_split_rgb_noise.set("resolution_source",source.width,source.height);

		// external param
		fx_split_rgb_noise.set("offset",offset.x,offset.y);

		 // rendering
		boolean pg_filter_is = true;
    render_shader(fx_split_rgb_noise,pg_split_rgb_noise,source,on_g,pg_filter_is);
	}

	// end
	reset_reverse_g(false);
	if(on_g) {
		return null;
	} else {
		return pg_split_rgb_noise; 
	}
}
/**
* TOON by Stan le punk
* @see http://stanlepunk.xyz
* @see https://github.com/StanLepunK/Shader
* v 0.0.6
* 2018-2019
*/

/**
DONT WORK
*/


public PGraphics fx_toon(PImage source) {
	return fx_toon(source,true);
}


PShader fx_toon;
PGraphics result_toon;
public PGraphics fx_toon(PImage source, boolean on_g) {
	if(!on_g && (result_toon == null 
								|| (source.width != result_toon.width 
								&& source.height != result_toon.height))) {
		result_toon = createGraphics(source.width,source.height,get_renderer());
	}
	
	if(fx_toon == null) {
		String path = get_fx_post_path()+"toon.glsl";
		if(fx_post_rope_path_exists) {
			fx_toon = loadShader(path);
			println("load shader: toon.glsl");
		}
	} else {
		if(on_g) set_shader_flip(fx_toon,source);

		fx_toon.set("texture_source",source);
		fx_toon.set("resolution_source",source);
		float x = map(mouseX,0,width,0,1);
		float y = map(mouseY,0,height,0,1);
		fx_toon.set("offset",x,y);

		// rendering
		boolean pg_filter_is = true;
		render_shader(fx_toon,result_toon,source,on_g,pg_filter_is);
	}

	// return
	reset_reverse_g(false);
	if(on_g) {
		return null;
	} else {
		return result_toon; 
	}
}
  public void settings() { 	size(1200,700,P3D); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "fx_app" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
