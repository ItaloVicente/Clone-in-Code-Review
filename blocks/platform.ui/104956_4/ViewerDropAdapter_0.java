
		Rectangle bounds = getBounds(item);
		if (bounds == null) {
			return LOCATION_NONE;
		}
		if ((coordinates.y - bounds.y) < 5) {
			return LOCATION_BEFORE;
		}
		if ((bounds.y + bounds.height - coordinates.y) < 5) {
			return LOCATION_AFTER;
