	private void addDropFeedback(Rectangle itemBounds) {
		int x = itemBounds.x - 16 / 2;
		int y = itemBounds.y - 16 / 2;
		dndManager.clearOverlay();
		addRect(x, y, 0, 0, 16, 2);
		addRect(x, y, 1, 2, 14, 2);
		addRect(x, y, 2, 4, 12, 2);
		addRect(x, y, 3, 6, 10, 2);
		addRect(x, y, 4, 8, 8, 2);
		addRect(x, y, 5, 10, 6, 2);
		addRect(x, y, 6, 12, 4, 2);
		addRect(x, y, 7, 14, 2, 2);
	}

	private void addRect(int xBase, int yBase, int x, int y, int w, int h) {
		dndManager.addImage(new Rectangle(xBase + x, yBase + y, w, h), new Image(Display.getCurrent(), w, h));
	}

