			} else {
				Rectangle bounds = ((TableItem) event.item).getBounds(event.index);
				for (int i = 0; i < bounds.width; i += 4) {
					int width = new Random().nextInt(4) + 1;
					event.gc.setForeground(event.display.getSystemColor(SWT.COLOR_BLACK));
					event.gc.setLineWidth(width);
					event.gc.drawLine(bounds.x + i, bounds.y, bounds.x + i, bounds.y + bounds.height);
				}
