		composite.addMouseMoveListener(e -> {
			Point p = e.display.getCursorLocation();
			if (trackState == NOT_SIZING) {
				setCursor(composite, new Point(e.x, e.y));
			} else if (trackState == HORIZONTAL_SIZING) {
				dragHorizontal(composite, p);
			} else if (trackState == VERTICAL_SIZING) {
				dragVertical(composite, p);
			} else if (trackState == CORNER_SIZING) {
				dragCorner(composite, p);
