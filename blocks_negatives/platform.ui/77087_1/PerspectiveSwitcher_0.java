		toolParent.addPaintListener(new PaintListener() {

			@Override
			public void paintControl(PaintEvent e) {
				if (borderColor == null || borderColor.isDisposed()) {
					borderColor = e.display.getSystemColor(SWT.COLOR_GRAY);
				}
				e.gc.setForeground(borderColor);
				Rectangle bounds = ((Control) e.widget).getBounds();
				e.gc.drawLine(0, bounds.height - 1, bounds.width, bounds.height - 1);
			}
		});
