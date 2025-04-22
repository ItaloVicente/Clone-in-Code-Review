		overlayShell.addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent e) {
				e.gc.setForeground(blue);
				e.gc.setBackground(blue);
				for (Adornment adornment : adornments) {
					adornment.drawAdornment(e.gc);
				}
