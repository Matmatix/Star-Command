package objects;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
public class EnemyShip extends Objects{
	
	private int moves;
	public Image colonistShip;
	public Image csAngles[];
	
	public EnemyShip(int x, int y, int moves){
		this.x = x;
		this.y = y;
		this.moves  = moves;
		colonistShip = new Image("ColonistShip.png");
		csAngles = new Image[10];
		csAngles[0] = (Image)new WritableImage(colonistShip.getPixelReader(), 0, 0, 50, 50);
		csAngles[1] = (Image)new WritableImage(colonistShip.getPixelReader(), 50, 0, 50, 50);
		csAngles[2] = (Image)new WritableImage(colonistShip.getPixelReader(), 0, 50, 50, 50);
		csAngles[3] = (Image)new WritableImage(colonistShip.getPixelReader(), 50, 50, 50, 50);
	}
	
	public void update(){
	
	}
	
	public void render(GraphicsContext gc){
		//gc.setFill(Color.GREEN);
		//gc.fillRect(x * 50 + 5, y * 50 + 5, 40, 40);
		gc.drawImage(csAngles[0], x*50, y*50);
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
