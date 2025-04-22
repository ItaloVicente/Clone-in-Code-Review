		if (cornerShape == CornerShape.SQUARE) {
			Rectangle rect = new Rectangle(bounds.x, bounds.y, width, height);
			gc.fillRectangle(rect);
			rectShape = rect;
		} else {
			int[] points = new int[1024];
			int index = 0;
			int radius = cornerSize / 2;

			int circX = bounds.x + delta / 2 + radius;
			int circY = bounds.y + radius;

			index = 0;
			int[] ltt = drawCircle(circX, circY, radius, CirclePart.LEFT_TOP);
			System.arraycopy(ltt, 0, points, index, ltt.length);
			index += ltt.length;
