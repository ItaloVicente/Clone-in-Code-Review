	private void addDropFeedback(Rectangle itemBounds) {
		int x = itemBounds.x - 16 / 2;
		int y = itemBounds.y - 16 / 2;
		dndManager.clearOverlay();
		addRect(x, y, 2, 0, 12, 2);
		addRect(x, y, 3, 2, 10, 2);
		addRect(x, y, 4, 4, 8, 2);
		addRect(x, y, 5, 6, 6, 2);
		addRect(x, y, 6, 8, 4, 2);
		addRect(x, y, 7, 10, 2, 2);
	}

	private void addRect(int xBase, int yBase, int x, int y, int w, int h) {
		dndManager.addImage(new Rectangle(xBase + x, yBase + y, w, h), new Image(Display.getCurrent(), w, h));
	}

