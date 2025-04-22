
				private void removeHyperlinkStyleRanges() {
					StyleRange[] hyperlinkStyleRanges = textWidget.getStyleRanges(true);
					Color blue = Display.getDefault()
							.getSystemColor(SWT.COLOR_BLUE);
					Color white = Display.getDefault()
							.getSystemColor(SWT.COLOR_WHITE);
					for (int i = 0; i < hyperlinkStyleRanges.length; i++) {
						StyleRange styleRange = hyperlinkStyleRanges[i];
						if (styleRange.underline == true
								&& styleRange.foreground == blue
								&& styleRange.background == white) {
							styleRange = (StyleRange) styleRange.clone();
							styleRange.background = null;
							styleRange.foreground = null;
							styleRange.underline = false;
							textWidget.setStyleRange(styleRange);
						}
					}
				}
