	private int computeMinimumWidth(int i) {
		SizeCache sc = cache.getCache(i);
		return sc.computeMinimumWidth();
	}

	private Point computeControlSize(int controlIndex, int wHint) {
		Control c = cache.getCache(controlIndex).getControl();
