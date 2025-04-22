			IDocument doc = textViewer.getDocument();
			for (int line = 0; line < doc.getNumberOfLines(); line++) {
				try {
					IRegion region = doc.getLineInformation(line);
					for (IHyperlinkDetector hyperLinkDetector : hyperlinkDetectors) {
						IHyperlink[] hyperlinks = hyperLinkDetector
								.detectHyperlinks(textViewer, region, true);
						if (hyperlinks != null) {
							for (IHyperlink hyperlink : hyperlinks) {
								StyleRange hyperlinkStyleRange = new StyleRange(
										hyperlink.getHyperlinkRegion()
												.getOffset(), hyperlink
												.getHyperlinkRegion()
												.getLength(), Display
												.getDefault().getSystemColor(
														SWT.COLOR_BLUE),
										Display.getDefault().getSystemColor(
												SWT.COLOR_WHITE));
								hyperlinkStyleRange.underline = true;
								styleRangeList.add(hyperlinkStyleRange);
							}
