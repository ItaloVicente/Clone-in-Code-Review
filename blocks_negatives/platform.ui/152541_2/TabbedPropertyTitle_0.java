		this.addPaintListener(new PaintListener() {

			@Override
			public void paintControl(PaintEvent e) {
				if (image == null && (text == null || text.equals(BLANK))) {
					label.setVisible(false);
				} else {
					label.setVisible(true);
				}
				drawTitleBackground(e);
