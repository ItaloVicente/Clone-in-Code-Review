	private void checkMinimumWidth(Control inner) {
		control = inner;
		update();
		resetCache();
		int minimumWidth = sizeCache.computeMinimumWidth();
		int maximumWidth = controlComputeSize(SWT.DEFAULT, SWT.DEFAULT).x;
		assertTrue("Minimum width was not less than maximum width for control: expected less than " + maximumWidth
				+ ", but was " + minimumWidth, minimumWidth < maximumWidth);
	}

