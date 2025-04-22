		if (outerKeylineColor != null && !outerKeylineColor.equals(parent.getBackground())) {
			gc.setForeground(outerKeylineColor);
			if (cornerSize == SQUARE_CORNER) {
				gc.drawRectangle(bounds);
			} else {
				gc.drawPolyline(shape);
			}
		}
