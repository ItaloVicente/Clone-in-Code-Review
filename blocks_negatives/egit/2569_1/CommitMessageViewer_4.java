	private void makeGrayText(StringBuilder d, List<StyleRange> styles) {
		int p0 = 0;
		for (int i = 0; i < styles.size(); ++i) {
			StyleRange r = styles.get(i);
			if (p0 < r.start) {
				StyleRange nr = new StyleRange(p0, r.start - p0, SYS_DARKGRAY,
						null);
				styles.add(i, nr);
				p0 = r.start;
			} else {
				if (r.foreground == null)
					r.foreground = SYS_DARKGRAY;
				p0 = r.start + r.length;
			}
		}
		if (d.length() - 1 > p0) {
			StyleRange nr = new StyleRange(p0, d.length() - p0, SYS_DARKGRAY,
					null);
			styles.add(nr);
		}
	}

	private void addLink(final StringBuilder d, String linkLabel,
			final List<StyleRange> styles, final RevCommit to) {
		final ObjectLink sr = new ObjectLink();
		sr.targetCommit = to;
		sr.foreground = SYS_LINKCOLOR;
		sr.underline = true;
		sr.start = d.length();
		d.append(linkLabel);
		sr.length = d.length() - sr.start;
		styles.add(sr);
	}

	private void addLink(final StringBuilder d, final List<StyleRange> styles,
			final RevCommit to) {
		addLink(d, to.getId().name(), styles, to);
	}

	private void buildDiffs(final StringBuilder d,
			final List<StyleRange> styles, IProgressMonitor monitor,
			boolean trace) throws InterruptedException,
			InvocationTargetException {

		final String[] currentEncoding = new String[1];

		if (trace)
			GitTraceLocation.getTrace().traceEntry(
					GitTraceLocation.HISTORYVIEW.getLocation());
		if (commit.getParentCount() != 1) {
			d.append(UIText.CommitMessageViewer_CanNotRenderDiffMessage);
