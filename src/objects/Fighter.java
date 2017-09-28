package objects;

import game.StarCommand;

import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

public class Fighter extends Objects{

	private boolean tright, tleft, canfire;
	private int dx, counter;
	private ArrayList<Objects> display;
	private ArrayList<Bullets> bullets;
	static Image full;
	static Image fighter[];
	private int direction, UP = 1, RIGHT = 0, LEFT = 2, DOWN = 3;
	
	public Fighter(int x, int y, ArrayList<Objects> display, ArrayList<Bullets> bullets){
		this.x = x;
		this.y = y;
		canfire = true;
		tright = false;
		tleft = false;
		this.display = display;
		this.bullets = bullets;
		counter = 0;
		fighter = new Image[4];
		full = new Image("Fighter1.png");
		fighter[0] = (Image)new WritableImage(full.getPixelReader(), 0, 0, 50, 50);
		fighter[1] = (Image)new WritableImage(full.getPixelReader(), 50, 0, 50, 50);
		fighter[2] = (Image)new WritableImage(full.getPixelReader(), 0, 50, 50, 50);
		fighter[3] = (Image)new WritableImage(full.getPixelReader(), 50, 50, 50, 50);
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		if (tright && tleft){
			dx = 0;
		}else if (tleft){
			dx = -5;
			direction = LEFT;
		}else if (tright){
			dx = 5;
			direction = RIGHT;
		}else if (!tright && !tleft){
			dx = 0;
		}
		
		x += dx;
		
		if(x > StarCommand.WIDTH - 30)
			x = StarCommand.WIDTH - 30;
		if (x < 0)
			x = 0;
		
		if(!canfire && counter < 15)
			counter++;
		else{
			canfire = true;
			counter = 0;
		}
	}

	@Override
	public void render(GraphicsContext gc) {
		// TODO Auto-generated method stub
		gc.drawImage(fighter[direction], x, y);
	}
	
	public void battlemove(char c){
		if (c == 'd'){
			tright = true;
		}else if (c == 'a'){
			tleft = true;
		}else if (c == 'z'){
			tleft = false;
			
		}else if (c == 'c'){
			tright = false;
		}
	}
	
	public void fire(){
		if (canfire){
			bullets.add(new Bullets(x + 20, y - 10, -10));
			direction = UP;
			display.add(bullets.get(bullets.size() - 1));
			canfire = false;
			direction = UP;
		}
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

}
