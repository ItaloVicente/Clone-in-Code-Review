		}
		return null;
	}

	private StyleRange findPreviousRange() {
		StyleRange[] ranges = styledText.getStyleRanges();
		int currentSelectionStart = styledText.getSelection().x;

		for (int i = ranges.length - 1; i > -1; i--) {
			if ((ranges[i].start + ranges[i].length - 1) < currentSelectionStart) {
