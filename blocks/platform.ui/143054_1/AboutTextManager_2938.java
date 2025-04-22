		}
		return null;
	}

	private StyleRange findNextRange() {
		StyleRange[] ranges = styledText.getStyleRanges();
		int currentSelectionEnd = styledText.getSelection().y;

		for (StyleRange range : ranges) {
			if (range.start >= currentSelectionEnd) {
				return range;
			}
		}
		return null;
	}

	private StyleRange findPreviousRange() {
		StyleRange[] ranges = styledText.getStyleRanges();
		int currentSelectionStart = styledText.getSelection().x;

		for (int i = ranges.length - 1; i > -1; i--) {
			if ((ranges[i].start + ranges[i].length - 1) < currentSelectionStart) {
