package org.eclipse.ui.examples.undo;


import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.GC;

public class Boxes  {

	private List<Box> boxes = new ArrayList<Box>();

	public Boxes() {
		super();
	}

	public void add(Box box) {
		boxes.add(box);
	}

	public void remove(Box box) {
		boxes.remove(box);
	}

	public void clear() {
		boxes = new ArrayList<Box>();
	}

	public boolean contains(Box box) {
		return boxes.contains(box);
	}

	public void draw(GC gc) {
		for (int i = 0; i < boxes.size(); i++) {
			boxes.get(i).draw(gc);
		}
	}

	public Box getBox(int x, int y) {
		for (int i=0; i< boxes.size(); i++) {
			Box box = boxes.get(i);
			if (box.contains(x, y)) {
				return box;
			}
		}
		return null;
	}

	public List<Box> getBoxes() {
		return boxes;
	}

	public void setBoxes(List<Box> boxes) {
		this.boxes = boxes;
	}

}
