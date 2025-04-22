	}

	private void setMark() {
		updateStats(); // get up-to-date stats before taking the mark
		mark = usedMem;
		hasChanged = true;
		redraw();
	}

	private void clearMark() {
		mark = -1;
		hasChanged = true;
		redraw();
	}

	private void gc() {
