	private StyleRange getStyleRange(final int x, final int y) {
		final StyledText t = sourceViewer.getTextWidget();
		final int offset;
		try {
			offset = t.getOffsetAtLocation(new Point(x, y));
		} catch (IllegalArgumentException e) {
			return null;
		}
		if (offset < t.getCharCount())
			return t.getStyleRangeAtOffset(offset);
		else
			return null;
	}

