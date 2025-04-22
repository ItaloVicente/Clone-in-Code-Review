		if (ncolumns == -1) {
			ncolumns = (wHint - leftMargin - rightMargin - horizontalSpacing) / (cwidth + horizontalSpacing);
			ncolumns = Math.min(ncolumns, children.length);
			ncolumns = Math.max(ncolumns, minNumColumns);
			ncolumns = Math.min(ncolumns, maxNumColumns);
		}
		int perColHeight = ColumnLayoutUtils.computeColumnHeight(ncolumns, sizes, cheight, verticalSpacing);
