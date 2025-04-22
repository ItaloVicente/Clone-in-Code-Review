		if (wHint != SWT.DEFAULT) {
			textWidth = wHint;
		}
		if (hHint != SWT.DEFAULT) {
			textHeight = hHint;
		}
		Rectangle trim = computeTrim(0, 0, textWidth, textHeight);
		return new Point(trim.width, trim.height);
