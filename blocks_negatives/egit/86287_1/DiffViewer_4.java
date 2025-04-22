	/** Refresh style ranges */
	public void refreshStyleRanges() {
		DiffStyleRange[] ranges = formatter != null ? formatter.getRanges()
				: new DiffStyleRange[0];
		for (DiffStyleRange range : ranges)
			switch (range.diffType) {
			case ADD:
				range.foreground = addForegroundColor;
				range.lineBackground = addBackgroundColor;
				break;
			case REMOVE:
				range.foreground = removeForegroundColor;
				range.lineBackground = removeBackgroundColor;
				break;
			case HUNK:
				range.foreground = hunkForegroundColor;
				range.lineBackground = hunkBackgroundColor;
				break;
			case HEADLINE:
				range.font = headlineFont;
				range.foreground = headlineForegroundColor;
				range.lineBackground = headlineBackgroundColor;
