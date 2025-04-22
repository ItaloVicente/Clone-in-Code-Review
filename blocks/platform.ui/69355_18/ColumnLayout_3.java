		int columnWidth = 0;
		int nColumns;
		if (wHint == SWT.DEFAULT) {
			nColumns = clampNumColumns(parent, maxNumColumns);

			for (int i = 0; i < children.length; i++) {
				columnWidth = Math.max(columnWidth, computeControlSize(i, SWT.DEFAULT).x);
			}
		} else if (wHint == MIN_SIZE) {
			nColumns = clampNumColumns(parent, 0);

			for (int i = 0; i < children.length; i++) {
				columnWidth = Math.max(columnWidth, computeMinimumWidth(i));
			}
		} else {
			nColumns = computeOptimalNumColumnsForWidth(parent, wHint);
			columnWidth = computeColumnWidthForNumColumns(wHint, nColumns);
