	private int computeOptimalNumColumnsForWidth(Composite parent, int width) {
		if (minNumColumns >= maxNumColumns || parent.getChildren().length <= minNumColumns) {
			return clampNumColumns(parent, minNumColumns);
		}

		Control[] children = parent.getChildren();
		int minColWidth = 0;

		for (int i = 0; i < children.length; i++) {
			int nextWidth = computeMinimumWidth(i);


			minColWidth = Math.max(minColWidth, nextWidth);
		}

		return clampNumColumns(parent,
				(width - leftMargin - rightMargin + horizontalSpacing) / (minColWidth + horizontalSpacing));
	}

	private int computeColumnWidthForNumColumns(int layoutWidth, int numColumns) {
		return ((layoutWidth - leftMargin - rightMargin) - (numColumns - 1) * horizontalSpacing) / numColumns;
	}

	private Point computeSize(Composite parent, int wHint, int hHint) {
