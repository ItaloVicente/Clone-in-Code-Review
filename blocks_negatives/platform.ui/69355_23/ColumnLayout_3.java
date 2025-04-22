		int ncolumns = (carea.width - leftMargin - rightMargin + horizontalSpacing) / (cwidth + horizontalSpacing);
		ncolumns = Math.min(ncolumns, children.length);
		ncolumns = Math.max(ncolumns, minNumColumns);
		ncolumns = Math.min(ncolumns, maxNumColumns);
		int realWidth = (carea.width - leftMargin - rightMargin + horizontalSpacing) / ncolumns - horizontalSpacing;

		int fillWidth = Math.max(cwidth, realWidth);
		int perColHeight = ColumnLayoutUtils.computeColumnHeight(ncolumns, sizes, cheight, verticalSpacing);
