			if (cornerSize == SQUARE_CORNER) {
				Color color = hotUnselectedTabsColorBackground;
				if (color == null) {
					color = gc.getDevice().getSystemColor(SWT.COLOR_WHITE);
				}
				gc.setBackground(color);

				Rectangle rect = new Rectangle(bounds.x, bounds.y, bounds.width, bounds.height);
				gc.fillRectangle(rect);
