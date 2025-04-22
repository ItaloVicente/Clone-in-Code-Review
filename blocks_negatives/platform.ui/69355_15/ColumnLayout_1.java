		int cwHint = SWT.DEFAULT;
		if (ncolumns != -1) {
			cwHint = wHint - leftMargin - rightMargin - (ncolumns - 1) * horizontalSpacing;
			if (cwHint <= 0)
				cwHint = 0;
			else
				cwHint /= ncolumns;
