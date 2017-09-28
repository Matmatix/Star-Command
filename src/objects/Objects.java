package objects;

import javafx.scene.canvas.GraphicsContext;
public abstract class Objects {

	protected int x, y;
	
	public abstract void update();
	public abstract void render(GraphicsContext gc);
	
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
