package objects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

public class Cursor extends Objects{

	static Image full;
	static Image[] cursor = new Image[2];
	private int count = 0, type = 0;
	
	public Cursor(int x, int y){
		this.x = x;
		this.y = y;
		
		full = new Image("cursor.png");
		cursor[0] = (Image)new WritableImage(full.getPixelReader(), 0, 0, 50, 50);
		cursor[1] = (Image)new WritableImage(full.getPixelReader(), 0, 50, 50, 50);
	}
	
	public void render(GraphicsContext gc){
//		gc.setStroke(Color.RED);
//		gc.strokeRect(x * 50 + 2, y * 50 + 2, 46, 46);
		
		gc.drawImage(cursor[type], x * 50, y * 50);
	}
	
	public int getY(){
		return y;
	}
	
	public void setY(int y){
		this.y = y;
	}
	
	public int getX(){
		return x;
	}
	
	public void setX(int x){
		this.x = x;
	}
	
	public void setType(int typ){
		type = typ;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		if (count != 15)
			count++;
		else if (type == 1){
			count = 0;
			type = 0;
		}else if (type == 0){
			count = 0;
			type = 1;
		}
	}
}
