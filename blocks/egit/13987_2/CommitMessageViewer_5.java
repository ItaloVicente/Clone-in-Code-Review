	private StyleRange[] getHyperlinkDetectorStyleRanges() {
		List<StyleRange> styleRangeList = new ArrayList<StyleRange>();
		if (fHyperlinkDetectors != null && fHyperlinkDetectors.length > 0) {
			for (int i = 0; i < getTextWidget().getText().length(); i++) {
				IRegion region = new Region(i, 0);
				for (IHyperlinkDetector hyperLinkDetector : fHyperlinkDetectors) {
					IHyperlink[] hyperlinks = hyperLinkDetector
							.detectHyperlinks(this, region, true);
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

