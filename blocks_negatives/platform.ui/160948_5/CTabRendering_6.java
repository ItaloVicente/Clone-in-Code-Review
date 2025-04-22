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
			int[] tmpPoints = new int[index];
			System.arraycopy(points, 0, tmpPoints, 0, index);
			gc.fillPolygon(tmpPoints);
			Color tempBorder = new Color(gc.getDevice(), 182, 188, 204);
			gc.setForeground(tempBorder);
			tempBorder.dispose();
			if (active) {
				gc.drawPolyline(tmpPoints);
			} else {
				gc.drawLine(inactive[0], inactive[1], inactive[2], inactive[3]);
				gc.drawLine(inactive[4], inactive[5], inactive[6], inactive[7]);
			}
