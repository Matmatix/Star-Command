package objects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

public class Ship extends Objects{
	
	private int moves, dx;
	private boolean tleft = false, tright = false;
	static Image full, ship, shipup, shipleft, shipright, shipdown;
	
	public Ship(int x, int y, int moves){
		this.x = x;
		this.y = y;
		this.moves  = moves;
		dx = 0;
		
		full = new Image("Fighter1.png");
		shipup = (Image)new WritableImage(full.getPixelReader(), 50, 0, 50, 50);
		shipleft = (Image)new WritableImage(full.getPixelReader(), 0, 50, 50, 50);
		shipright = (Image)new WritableImage(full.getPixelReader(), 0, 0, 50, 50);
		shipdown = (Image)new WritableImage(full.getPixelReader(), 50, 50, 50, 50);
		ship = (Image)new WritableImage(full.getPixelReader(), 50, 0, 50, 50);
	}
	
	public void update(){
		if (tright && tleft){
			dx = 0;
		}else if (tleft){
			dx = -5;
		}else if (tright){
			dx = 5;
		}
	}
	
	public void render(GraphicsContext gc){
		gc.drawImage(ship, x * 50, y*50);
	}
	
	public void setDirection(int i){
		if (i==0){
			ship = shipup;
		}else if (i==1){
			ship = shipleft;
		}else if (i==2){
			ship = shipright;
		}else if (i==3){
			ship = shipdown;
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

	public int getMoves() {
		return moves;
	}

	public void setMoves(int moves) {
		this.moves = moves;
	}

}
