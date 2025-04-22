			viewer.getTree().addListener(SWT.PaintItem, event -> {
				if (!event.gc.isClipped()) {
					return;
				}
				Object obj = ((TreeItem) event.item).getData();
				if (obj instanceof StagingEntry) {
					StagingEntry entry = (StagingEntry) obj;
					String text = getConflictText(entry);
					if (text.isEmpty()) {
						entry.setExtraWidth(0);
						return;
					}

					Rectangle bounds = viewer.getTree().getClientArea();
					Rectangle clip = event.gc.getClipping();
					if (clip.width < bounds.width) {
						clip.width = bounds.width;
						event.gc.setClipping(clip);
					}
					Rectangle cellBounds = ((TreeItem) event.item).getBounds();
					Point extent = event.gc.textExtent(text);
					int width = extent.x
							+ 2 * IDialogConstants.HORIZONTAL_SPACING;
					entry.setExtraWidth(width);
					int rightEdge = bounds.x + bounds.width;
					event.gc.fillRectangle(rightEdge - width, cellBounds.y,
							width, cellBounds.height);
					event.gc.drawString(text,
							rightEdge - extent.x
									- IDialogConstants.HORIZONTAL_SPACING,
							cellBounds.y + (cellBounds.height - extent.y) / 2);
				}
