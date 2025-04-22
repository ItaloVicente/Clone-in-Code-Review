	/**
	 * Get style range at x/y coordinates
	 *
	 * @param x
	 * @param y
	 * @return style range, will be null when no style range exists at given
	 *         coordinates
	 */
	private StyleRange getStyleRange(final int x, final int y) {
		final StyledText t = getTextWidget();
		final int offset;
		try {
			offset = t.getOffsetAtLocation(new Point(x, y));
		} catch (IllegalArgumentException e) {
			return null;
