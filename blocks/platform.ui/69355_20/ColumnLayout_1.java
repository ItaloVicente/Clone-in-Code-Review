		updateCache(composite, flushCache);
		return computeSize(composite, wHint, hHint);
	}

	private int clampNumColumns(Composite parent, int desiredNumColumns) {
		int ncolumns = desiredNumColumns;
		ncolumns = Math.min(ncolumns, parent.getChildren().length);
		ncolumns = Math.min(ncolumns, maxNumColumns);
		ncolumns = Math.max(ncolumns, minNumColumns);
		ncolumns = Math.max(ncolumns, 1);
		return ncolumns;
