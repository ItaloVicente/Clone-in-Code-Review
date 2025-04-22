			gc.setClipping(points[0], onBottom ? bounds.y - header : bounds.y,
					parent.getSize().x
							- (shadowEnabled ? SIDE_DROP_WIDTH : 0
									+ INNER_KEYLINE + OUTER_KEYLINE), bounds.y
							+ bounds.height);

