
				if (brokenBidiPlatformTextWidth != -1) {
					Layout restrictedWidthLayout = new Layout() {
						@Override
						protected Point computeSize(Composite composite,
								int wHint, int hHint, boolean flushCache) {
							Point size = SpellcheckableMessageArea.this
									.getSize();
							Rectangle trim = SpellcheckableMessageArea.this
									.computeTrim(0, 0, 0, 0);
							size.x -= trim.width;
							size.y -= trim.height;
							if (size.x > brokenBidiPlatformTextWidth)
								size.x = brokenBidiPlatformTextWidth;
							return size;
						}

						@Override
						protected void layout(Composite composite,
								boolean flushCache) {
							Point size = computeSize(composite, SWT.DEFAULT,
									SWT.DEFAULT, flushCache);
							textWidget.setBounds(0, 0, size.x, size.y);
						}
					};
					setLayout(restrictedWidthLayout);
				}
