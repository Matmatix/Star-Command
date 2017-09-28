package objects;

import game.StarCommand;

import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import java.util.Random;

public class EnemyFighter extends Objects{

	private boolean tright, tleft, canfire;
	private int dx, counter, movecounter;
	private ArrayList<Objects> display;
	private ArrayList<Bullets> bullets;
	private Fighter fighter;
	private Random r;
	public Image colonistShip;
	public Image csAngles[];
	private int direction, UP = 0, LEFT = 1, DOWN = 2, RIGHT = 3;
	public EnemyFighter(int x, int y, ArrayList<Objects> display, ArrayList<Bullets> bullets, Fighter fighter){
		this.x = x;
		this.y = y;
		this.bullets = bullets;
		this.display = display;
		tright = false;
		tleft = false;
		canfire = true;
		counter = 0;
		this.fighter = fighter;
		r = new Random();
		movecounter = 0;
		colonistShip = new Image("ColonistShip.png");
		csAngles = new Image[10];
		csAngles[0] = (Image)new WritableImage(colonistShip.getPixelReader(), 0, 0, 50, 50);
		csAngles[1] = (Image)new WritableImage(colonistShip.getPixelReader(), 50, 0, 50, 50);
		csAngles[2] = (Image)new WritableImage(colonistShip.getPixelReader(), 0, 50, 50, 50);
		csAngles[3] = (Image)new WritableImage(colonistShip.getPixelReader(), 50, 50, 50, 50);
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		if (tright && tleft){
			dx = 0;
		}else if (tleft){
			dx = -4;
		}else if (tright){
			dx = 4;
		}else if (!tright && !tleft){
			dx = 0;
		}
		
		x += dx;
		
		if (x > StarCommand.WIDTH - 30)
			x = StarCommand.WIDTH - 30;
		if (x < 0)
			x = 0;
		
		if (movecounter < 30){
			movecounter++;
		}else {
			movecounter = 0;
			tright = false;
			tleft = false;
			battlemove('a');
		}
		
		if(!canfire && counter < 30)
			counter++;
		else{
			canfire = true;
			counter = 0;
		}
		
		if (fighter.getX() > x - 80 && fighter.getX() < x + 80)
			fire();
	}

	@Override
	public void render(GraphicsContext gc) {
		// TODO Auto-generated method stub
//		gc.setFill(Color.RED);
//		gc.fillRect(x, y, 30, 40);
//		gc.fillRect(x + 10, y + 40, 10, 10);
		gc.drawImage(csAngles[direction], x, y);
	}
	
	public void battlemove(char c){
		int s = r.nextInt(2);
		if (s == 0){
			tleft = true;
			direction = LEFT;
		}
		else{
			tright = true;
			direction = RIGHT;
		}
	}
	
	public void fire(){
		if (canfire){
			direction = DOWN;
			bullets.add(new Bullets(x + 15, y + 100, 10));
			display.add(bullets.get(bullets.size() - 1));
			canfire = false;
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
