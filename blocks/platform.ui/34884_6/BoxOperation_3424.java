package org.eclipse.ui.examples.undo;


import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;

public class Box  {

	public int x1, y1, x2, y2;

	public Box(int x1, int y1, int x2, int y2) {
		super();
		set(x1, y1, x2, y2);
	}

	public void move(Point origin) {
		set(origin.x, origin.y, origin.x + getWidth(), origin.y + getHeight());
	}

	public void draw(GC gc) {
		gc.drawRectangle(x1, y1, x2-x1, y2-y1);
	}

	private void set(int x1, int y1, int x2, int y2) {
		this.x1 = Math.min(x1, x2);
		this.y1 = Math.min(y1, y2);
		this.x2 = Math.max(x1, x2);
		this.y2 = Math.max(y1, y2);
	}

	public boolean contains(int x, int y) {
		return x >= x1 &&
			x <= x2 &&
			y >= y1 &&
			y <= y2;
	}

	public int getWidth() {
		return x2 - x1;
	}

	public int getHeight() {
		return y2 - y1;
	}
}
