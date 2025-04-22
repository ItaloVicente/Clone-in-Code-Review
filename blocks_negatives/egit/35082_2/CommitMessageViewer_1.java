		StyleRange[] hyperlinkStyleRanges = UIUtils
				.getHyperlinkDetectorStyleRanges(CommitMessageViewer.this,
						fHyperlinkDetectors);

		for (StyleRange styleRange : hyperlinkStyleRanges)
			text.setStyleRange(styleRange);
