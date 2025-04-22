		if (selectedTabHighlightColor != null) {
			gc.setBackground(selectedTabHighlightColor);
			boolean highlightOnTop = drawTabHighlightOnTop;
			if (onBottom) {
				highlightOnTop = !highlightOnTop;
			}
			int verticalOffset = highlightOnTop ? 0 : bounds.height - 2;
			gc.fillRectangle(bounds.x, bounds.y + verticalOffset, bounds.width, 3);
		}

