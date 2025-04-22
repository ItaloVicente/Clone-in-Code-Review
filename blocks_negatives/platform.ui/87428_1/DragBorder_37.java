			border.addPaintListener(new PaintListener() {
				@Override
				public void paintControl(PaintEvent e) {
					if (isHighlight) {
						e.gc.setForeground(hilightColor);
					}
					else {
						e.gc.setForeground(baseColor);
					}

					Rectangle bb = border.getBounds();
					e.gc.drawRectangle(0,0,bb.width-1, bb.height-1);
