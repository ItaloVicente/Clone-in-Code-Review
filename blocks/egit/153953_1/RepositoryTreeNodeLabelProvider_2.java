	@Override
	public void update(ViewerCell cell) {
		if (showPaths) {
			update(cell, super::update);
		} else {
			super.update(cell);
		}
	}

