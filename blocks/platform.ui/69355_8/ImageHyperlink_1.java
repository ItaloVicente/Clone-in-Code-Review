
		if (wHint != SWT.DEFAULT)
			width = wHint;
		if (hHint != SWT.DEFAULT)
			height = hHint;

		return new Point(width + trim.width, height + trim.height);
