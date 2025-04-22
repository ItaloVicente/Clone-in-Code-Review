		}
		return null;
	}

	protected StyleRange getCurrentLink(StyledText text) {
		int currentSelectionEnd = text.getSelection().y;
		int currentSelectionStart = text.getSelection().x;

		for (StyleRange range : text.getStyleRanges()) {
			if ((currentSelectionStart >= range.start)
					&& (currentSelectionEnd <= (range.start + range.length))) {
				return range;
			}
		}
		return null;
	}

	private void addListeners(StyledText styledText) {
