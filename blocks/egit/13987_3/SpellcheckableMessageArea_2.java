					textWidget.setStyleRanges(
							new StyleRange[0]);
					for (StyleRange styleRange : UIUtils
							.getHyperlinkDetectorStyleRanges(
									sourceViewer,
									configuration
											.getHyperlinkDetectors(sourceViewer)))
						textWidget.setStyleRange(styleRange);
