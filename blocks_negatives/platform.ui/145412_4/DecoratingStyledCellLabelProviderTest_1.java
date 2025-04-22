			event.gc.setBackground(fViewer.getControl().getDisplay()
					.getSystemColor(SWT.COLOR_RED));
			event.gc.fillRectangle(new Rectangle(bounds.width / 2 + bounds.x
					- 5, bounds.y, 10, bounds.height));
			event.gc.fillRectangle(new Rectangle(bounds.x, bounds.height / 2
					+ bounds.y - 5, bounds.width, 10));
