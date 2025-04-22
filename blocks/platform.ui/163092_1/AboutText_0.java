	private Optional<String> safeLinkAt(int offset) {
		return Optional.ofNullable(item).flatMap(a -> a.linkAt(offset));
	}

	private void launch(StyledText text, String link) {
		text.setCursor(busyCursor);
		Program.launch(link);
		StyleRange selectionRange = getCurrentRange();
		text.setSelectionRange(selectionRange.start, selectionRange.length);
		text.setCursor(null);
	}

