package objects;

import javafx.scene.paint.Color;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

public class Path extends Objects{

	static Image full;
	static Image[] paths;
	private int type; //This is what determines what path image it is
	
	/*
	 * 0 = is straight up & down
	 * 1 = straight side to side
	 * 2 = arrow up
	 * 3 = arrow 
	 */
	public Path(int x, int y, int type){
		this.x = x;
		this.y = y;
		
		full = new Image("pathway - all directions.png");
		paths = new Image[10];
		paths[0] = (Image)new WritableImage(full.getPixelReader(), 0, 0, 50, 50);
		paths[1] = (Image)new WritableImage(full.getPixelReader(), 50, 0, 50, 50);
		paths[2] = (Image)new WritableImage(full.getPixelReader(), 100, 0, 50, 50);
		paths[3] = (Image)new WritableImage(full.getPixelReader(), 0, 50, 50, 50);
		paths[4] = (Image)new WritableImage(full.getPixelReader(), 50, 50, 50, 50);
		paths[5] = (Image)new WritableImage(full.getPixelReader(), 100, 50, 50, 50);
		paths[6] = (Image)new WritableImage(full.getPixelReader(), 0, 100, 50, 50);
		paths[7] = (Image)new WritableImage(full.getPixelReader(), 50, 100, 50, 50);
		paths[8] = (Image)new WritableImage(full.getPixelReader(), 100, 100, 50, 50);
		paths[9] = (Image)new WritableImage(full.getPixelReader(), 0, 150, 50, 50);
		this.type = type;
	}
	
	public void setType(int typ){
		type = typ;
	}
	
	public void print(){
		System.out.println(type);
	}
	
	//proabably never called
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(GraphicsContext gc) {
//		gc.setFill(Color.WHITE);
//		gc.fillRect(x * 50 + 20, y * 50 + 20, 10, 10);
		
		gc.drawImage(paths[type], x * 50, y * 50);
	}

}
