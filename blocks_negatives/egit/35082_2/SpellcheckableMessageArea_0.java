					StyleRange[] styleRanges = UIUtils
							.getHyperlinkDetectorStyleRanges(
									sourceViewer,
									configuration
											.getHyperlinkDetectors(sourceViewer));
					textWidget.setStyleRanges(styleRanges);
