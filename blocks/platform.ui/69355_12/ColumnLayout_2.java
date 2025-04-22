	private Point computeMinimumSize(int i) {
		SizeCache sc = cache.getCache(i);
		int minWidth = sc.computeMaximumWidth();
		return sc.computeSize(minWidth, SWT.DEFAULT);
	}

	private Point computeControlSize(int controlIndex, int wHint) {
		Control c = cache.getCache(controlIndex).getControl();
