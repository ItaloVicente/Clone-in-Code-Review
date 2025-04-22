	/**
	 * Use hyperlink detectors to find a text viewer's hyperlinks and apply them
	 * to the text widget. Existing overlapping styles are overwritten by new
	 * styles from this.
	 *
	 * @param textViewer
	 * @param hyperlinkDetectors
	 * @deprecated Instead of applying SWT styling directly use JFace
	 *             infrastructure (
	 *             {@link org.eclipse.jface.text.rules.DefaultDamagerRepairer
	 *             DefaultDamagerRepairer},
	 *             {@link org.eclipse.jface.text.rules.ITokenScanner
	 *             ITokenScanner}) to do syntax coloring. See also
	 *             {@link org.eclipse.egit.ui.internal.dialogs.HyperlinkTokenScanner}
	 *             .
	 */
	@Deprecated
	public static void applyHyperlinkDetectorStyleRanges(
			ITextViewer textViewer, IHyperlinkDetector[] hyperlinkDetectors) {
		StyleRange[] styleRanges = getHyperlinkDetectorStyleRanges(textViewer,
				hyperlinkDetectors);
		StyledText styledText = textViewer.getTextWidget();
		for (StyleRange styleRange : styleRanges)
			styledText.setStyleRange(styleRange);
	}

	/**
	 * Use hyperlink detectors to find a text viewer's hyperlinks and return the
	 * style ranges to render them.
	 *
	 * @param textViewer
	 * @param hyperlinkDetectors
	 * @return the style ranges to render the detected hyperlinks
	 * @deprecated Instead of applying SWT styling directly use JFace
	 *             infrastructure (
	 *             {@link org.eclipse.jface.text.rules.DefaultDamagerRepairer
	 *             DefaultDamagerRepairer},
	 *             {@link org.eclipse.jface.text.rules.ITokenScanner
	 *             ITokenScanner}) to do syntax coloring. See also
	 *             {@link org.eclipse.egit.ui.internal.dialogs.HyperlinkTokenScanner}
	 *             .
	 */
	@Deprecated
	public static StyleRange[] getHyperlinkDetectorStyleRanges(
			ITextViewer textViewer, IHyperlinkDetector[] hyperlinkDetectors) {
		HashSet<StyleRange> styleRangeList = new LinkedHashSet<>();
		if (hyperlinkDetectors != null && hyperlinkDetectors.length > 0) {
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
						}
					}
				} catch (BadLocationException e) {
					Activator.logError(e.getMessage(), e);
					break;
				}
			}
		}
		StyleRange[] styleRangeArray = new StyleRange[styleRangeList.size()];
		styleRangeList.toArray(styleRangeArray);
		return styleRangeArray;
	}

