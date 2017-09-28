package objects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class GridEntry extends Objects{

	private int color;
	
	public GridEntry(int x, int y, int color){
		this.x = x;
		this.y = y;
		this.color = color;
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(GraphicsContext gc) {
		if (color == 0){
			gc.setFill(Color.WHITE);
		}else if (color == 1){
			gc.setFill(Color.BLACK);
		}
		gc.fillRect(x * 50, y * 50, 50, 50);
		if(color == 1){
			gc.setFill(Color.WHITE);
			gc.fillOval(x+50*(int)(50*Math.random()),y+50*(int)(50*Math.random()), 2, 2);
		}
	}

}
