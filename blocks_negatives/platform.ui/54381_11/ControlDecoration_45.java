		paintListener = new PaintListener() {
			@Override
			public void paintControl(PaintEvent event) {
				Control control = (Control) event.widget;
				Rectangle rect = getDecorationRectangle(control);
				if (shouldShowDecoration()) {
					event.gc.drawImage(getImage(), rect.x, rect.y);
				}
