		int width = bounds.width;
		int[] points = new int[1024];
		int index = 0;
		int radius = cornerSize / 2;
		int circX = bounds.x + radius;
		int circY = onBottom ? bounds.y + bounds.height + 1 - header - radius : bounds.y - 1 + radius;
		int selectionX1, selectionY1, selectionX2, selectionY2;
