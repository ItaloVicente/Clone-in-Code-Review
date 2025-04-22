	public static void applyHyperlinkDetectorStyleRanges(
			ITextViewer textViewer, IHyperlinkDetector[] hyperlinkDetectors) {
		StyleRange[] styleRanges = getHyperlinkDetectorStyleRanges(textViewer,
				hyperlinkDetectors);
		StyledText styledText = textViewer.getTextWidget();
		for (StyleRange styleRange : styleRanges)
			styledText.setStyleRange(styleRange);
	}

