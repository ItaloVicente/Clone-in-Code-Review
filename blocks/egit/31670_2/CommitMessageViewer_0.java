	private void applyFormatJobResultInUI(FormatResult formatResult) {
		StyledText text = getTextWidget();
		if (!UIUtils.isUsable(text))
			return;

		setDocument(new Document(formatResult.getCommitInfo()));

		text.setStyleRanges(formatResult.getStyleRange());

		StyleRange[] hyperlinkStyleRanges = UIUtils
				.getHyperlinkDetectorStyleRanges(CommitMessageViewer.this,
						fHyperlinkDetectors);

		for (StyleRange styleRange : hyperlinkStyleRanges)
			text.setStyleRange(styleRange);
	}

