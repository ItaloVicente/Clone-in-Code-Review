	private List<IHyperlink> detectHyperlinks(ITextViewer textViewer,
			String content, int index, int contentOffset) {
		Shell shell = textViewer.getTextWidget().getShell();
		List<IHyperlink> links = null;
		Matcher matcher = PATTERN_COMMIT_ID.matcher(content);
		while (matcher.find()) {
			if (index != -1
					&& (index < matcher.start() || index > matcher.end())) {
				continue;
			}
			if (links == null) {
				links = new ArrayList<IHyperlink>();
			}
			int start = matcher.start(1);
			Region region = new Region(contentOffset + start, matcher.end(1)
					- start);

			CommitHyperlink hyperlink = new CommitHyperlink(region,
					matcher.group(1), shell);
			links.add(hyperlink);
		}
		return links;
	}

