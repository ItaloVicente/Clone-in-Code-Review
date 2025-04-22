					if (information != null) {
						Rectangle cellBounds = cell.getBounds();
						Rectangle bounds = viewer.getTree().getClientArea();
						bounds.y = cellBounds.y;
						bounds.height = cellBounds.height;
						int extra = entry.getExtraWidth();
						bounds.x = bounds.x + bounds.width - extra;
						bounds.width = extra;
						if (bounds.contains(getHoverEventLocation())) {
							setInformation(information, bounds);
							return;
						}
					}
