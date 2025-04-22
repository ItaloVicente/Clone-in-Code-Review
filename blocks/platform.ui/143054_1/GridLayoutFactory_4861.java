	public static GridLayout copyLayout(GridLayout l) {
		GridLayout result = new GridLayout(l.numColumns, l.makeColumnsEqualWidth);
		result.horizontalSpacing = l.horizontalSpacing;
		result.marginBottom = l.marginBottom;
		result.marginHeight = l.marginHeight;
		result.marginLeft = l.marginLeft;
		result.marginRight = l.marginRight;
		result.marginTop = l.marginTop;
		result.marginWidth = l.marginWidth;
		result.verticalSpacing = l.verticalSpacing;
