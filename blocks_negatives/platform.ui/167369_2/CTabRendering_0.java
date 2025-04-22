
		if (outerKeyline == null)
			outerKeyline = gc.getDevice().getSystemColor(SWT.COLOR_BLACK);
		gc.setForeground(outerKeyline);

		if (cornerSize == SQUARE_CORNER) {
			gc.drawRectangle(rectShape);
		} else {
			gc.drawPolyline(shape);
		}
