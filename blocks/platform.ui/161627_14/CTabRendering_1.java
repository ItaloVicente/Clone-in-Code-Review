		if (selectedTabHighlightColor != null) {
			gc.setBackground(selectedTabHighlightColor);
			int verticalOffset = drawTabHighlightOnTop ? 0 : bounds.height - 2;
			gc.fillRectangle(bounds.x, bounds.y + verticalOffset, bounds.width, 3);
		}

