		if (selectedTabHighlightColor != null) {
			gc.setBackground(selectedTabHighlightColor);
			boolean highlightOnTop = drawTabHighlightOnTop;
			if (onBottom) {
				highlightOnTop = !highlightOnTop;
			}
			int verticalOffset = highlightOnTop ? 0 : bounds.height - 2;
			int horizontalOffset = itemIndex == 0 || cornerSize == SQUARE_CORNER ? 0 : 1;
			int widthAdjustment = cornerSize == SQUARE_CORNER ? 0 : 1;
			gc.fillRectangle(bounds.x + horizontalOffset, bounds.y + verticalOffset, bounds.width - widthAdjustment, 3);
		}

