	private void addLink(final StringBuilder d, String linkLabel,
			final List<StyleRange> styles, final RevCommit to) {
		final ObjectLink sr = new ObjectLink();
		sr.targetCommit = to;
		sr.foreground = linkColor;
		sr.underline = true;
		sr.start = d.length();
