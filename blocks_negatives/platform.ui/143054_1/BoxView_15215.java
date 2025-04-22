		paintCanvas.addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent event) {
				event.gc.setForeground(paintCanvas.getForeground());
				boxes.draw(event.gc);
			}
