						if (hyperlinkDetector instanceof HyperlinkSourceViewer.NoMaskHyperlinkDetector) {
							continue;
						}
						IHyperlink[] newLinks = null;
						try {
							newLinks = hyperlinkDetector.detectHyperlinks(
									viewer, currentLine, false);
						} catch (RuntimeException e) {
							detectors.remove();
						}
						if (newLinks != null && newLinks.length > 0) {
							Collections.addAll(hyperlinksOnLine, newLinks);
