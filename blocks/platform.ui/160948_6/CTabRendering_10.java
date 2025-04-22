				if (!active) {
					System.arraycopy(points, index - 2, inactive, inactive_index, 2);
					inactive[inactive_index] -= 1;
					inactive_index += 2;
				}
				gc.setClipping(points[0], onBottom ? bounds.y - header : bounds.y,
						parent.getSize().x - (shadowEnabled ? SIDE_DROP_WIDTH : 0 + INNER_KEYLINE + OUTER_KEYLINE),
						bounds.y + bounds.height);

				Color color = hotUnselectedTabsColorBackground;
				if (color == null) {
					color = gc.getDevice().getSystemColor(SWT.COLOR_WHITE);
				}
				gc.setBackground(color);
