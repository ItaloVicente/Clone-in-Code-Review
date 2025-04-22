		if (cornerSize == SQUARE_CORNER) {
			Rectangle rect = new Rectangle(bounds.x, bounds.y, width, height);
			gc.fillRectangle(rect);
			rectShape = rect;
		} else {
			int[] points = new int[1024];
			int index = 0;
			int radius = cornerSize / 2;
