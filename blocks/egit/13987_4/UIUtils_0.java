	public static StyleRange[] getHyperlinkDetectorStyleRanges(
			ITextViewer textViewer, IHyperlinkDetector[] hyperlinkDetectors) {
		List<StyleRange> styleRangeList = new ArrayList<StyleRange>();
		if (hyperlinkDetectors != null && hyperlinkDetectors.length > 0) {
			for (int i = 0; i < textViewer.getTextWidget().getText().length(); i++) {
				IRegion region = new Region(i, 0);
				for (IHyperlinkDetector hyperLinkDetector : hyperlinkDetectors) {
					IHyperlink[] hyperlinks = hyperLinkDetector
							.detectHyperlinks(textViewer, region, true);
					if (hyperlinks != null) {
						for (IHyperlink hyperlink : hyperlinks) {
							StyleRange hyperlinkStyleRange = new StyleRange(
									hyperlink.getHyperlinkRegion().getOffset(),
									hyperlink.getHyperlinkRegion().getLength(),
									Display.getDefault().getSystemColor(
											SWT.COLOR_BLUE), Display
											.getDefault().getSystemColor(
													SWT.COLOR_WHITE));
							hyperlinkStyleRange.underline = true;
							styleRangeList.add(hyperlinkStyleRange);
						}
					}
				}
			}
		}
		StyleRange[] styleRangeArray = new StyleRange[styleRangeList.size()];
		styleRangeList.toArray(styleRangeArray);
		return styleRangeArray;
	}

