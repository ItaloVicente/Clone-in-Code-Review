		if (getOuterKeylineWeight() != 0) {
			gc.setForeground(outerKeylineColor);
			if (cornerSize == SQUARE_CORNER) {
				gc.drawRectangle(rectShape);
			} else {
				gc.drawPolyline(shape);
			}
		}
