			gc.fillRectangle(bounds);

			int index = 0;
			int outlineY = onBottom ? bottomY + bounds.height : bottomY - bounds.height - 1;
			int[] points = new int[12];

			startX = bounds.x - 1;
			endX = bounds.x + bounds.width;
			selectionX1 = startX + 1;
