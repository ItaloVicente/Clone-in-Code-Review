	void drawCorners(GC gc, Rectangle bounds) {
		Color bg = gc.getBackground();
		Color fg = gc.getForeground();
		Color toFill = parent.getParent().getBackground();
		gc.setAlpha(255);
		gc.setBackground(toFill);
		gc.setForeground(toFill);
		int radius = cornerSize / 2 + 1;
		int leftX = bounds.x - 1;
		int topY = bounds.y - 1;
		int rightX = bounds.x + bounds.width;
		int bottomY = bounds.y + bounds.height;
		drawCutout(gc, leftX, topY, radius, CirclePart.LEFT_TOP);
		drawCutout(gc, rightX, topY, radius, CirclePart.RIGHT_TOP);
		drawCutout(gc, leftX, bottomY, radius, CirclePart.LEFT_BOTTOM);
		drawCutout(gc, rightX, bottomY, radius, CirclePart.RIGHT_BOTTOM);
		gc.setBackground(bg);
		gc.setForeground(fg);
	}

	private void drawCutout(GC gc, int x, int y, int radius, CirclePart side) {
		int centerX = x + (side.isLeft() ? radius : -radius);
		int centerY = y + (side.isTop() ? radius : -radius);

		int[] circle = drawCircle(centerX, centerY, radius, side);
		int[] result = new int[circle.length + 2];
		result[0] = x;
		result[1] = y;
		int count = circle.length / 2;
		for (int idx = 0; idx < count; idx++) {
			int destIdx = idx * 2 + 2;
			int srcIdx = (count - 1 - idx) * 2;
			result[destIdx] = circle[srcIdx];
			result[destIdx + 1] = circle[srcIdx + 1];
		}

		gc.fillPolygon(result);
	}

