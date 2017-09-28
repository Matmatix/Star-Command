package objects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Bullets extends Objects {

	private int dy;
	
	public Bullets(int x, int y, int dy){
		this.x = x;
		this.y = y;
		this.dy = dy;
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		y += dy;
	}

	@Override
	public void render(GraphicsContext gc) {
		// TODO Auto-generated method stub
		gc.setFill(Color.WHITE);
		gc.fillRect(x - 2, y, 4, 8);
	}

}
