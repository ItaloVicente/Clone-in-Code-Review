	private void addDropFeedback(Rectangle itemBounds) {
		dndManager.clearOverlay();
		int y = itemBounds.y - imagesHeight / 2;
		for (int i = 0; i < images.size(); i++) {
			Image img = images.get(i);
			Rectangle bounds = img.getBounds();
			int x = itemBounds.x - bounds.width / 2;
			Rectangle imageRect = new Rectangle(x, y, bounds.width, bounds.height);
			dndManager.addImage(imageRect, img);
			y += bounds.height;
		}
	}

