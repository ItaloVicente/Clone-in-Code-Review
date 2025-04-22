	private StyleRange[] getHyperlinkDetectorStyleRanges() {
		List<StyleRange> styleRangeList = new ArrayList<StyleRange>();
		IHyperlinkDetector[] hyperlinkDetectors = configuration
				.getHyperlinkDetectors(sourceViewer);
		if (hyperlinkDetectors != null && hyperlinkDetectors.length > 0) {
			for (int i = 0; i < getTextWidget().getText().length(); i++) {
				IRegion region = new Region(i, 0);
				for (IHyperlinkDetector hyperLinkDetector : hyperlinkDetectors) {
					IHyperlink[] hyperlinks = hyperLinkDetector
							.detectHyperlinks(sourceViewer, region, true);
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

