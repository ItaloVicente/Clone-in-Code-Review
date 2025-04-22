		int width = 10;
		int height = 10;
		if (wHint != SWT.DEFAULT) {
			width = wHint;
		}
		if (hHint != SWT.DEFAULT) {
			height = hHint;
		}
		Rectangle trim = computeTrim(0, 0, width, height);
