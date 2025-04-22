	private Point fixupDisplayBounds(Point tipSize, Point location) {
		if (this.respectDisplayBounds) {
			Rectangle bounds;
			Point rightBounds = new Point(tipSize.x + location.x, tipSize.y + location.y);

			if (this.respectMonitorBounds) {
				bounds = this.shell.getDisplay().getPrimaryMonitor().getBounds();
			} else {
				bounds = getPrimaryClientArea();
			}

			if (!(bounds.contains(location) && bounds.contains(rightBounds))) {
				if (rightBounds.x > bounds.x + bounds.width) {
					location.x -= rightBounds.x - (bounds.x + bounds.width);
				}

				if (rightBounds.y > bounds.y + bounds.height) {
					location.y -= rightBounds.y - (bounds.y + bounds.height);
				}

				if (location.x < bounds.x) {
					location.x = bounds.x;
				}

				if (location.y < bounds.y) {
					location.y = bounds.y;
				}
			}
		}
		return location;
	}

