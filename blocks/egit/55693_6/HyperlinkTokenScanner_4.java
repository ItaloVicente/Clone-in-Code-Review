						if (!(hyperlinkDetector instanceof HyperlinkSourceViewer.NoMaskHyperlinkDetector)) {
							IHyperlink[] newLinks = hyperlinkDetector
									.detectHyperlinks(viewer, currentLine,
											false);
							if (newLinks != null && newLinks.length > 0) {
								Collections.addAll(hyperlinksOnLine, newLinks);
							}
