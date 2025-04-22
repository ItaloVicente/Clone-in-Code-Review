		private Point computeTextClientSize(int width) {
			if (textClient == null || width == 0)
				return NULL_SIZE;
			Point tcsize = textClientCache.computeSize(width, SWT.DEFAULT);
			if (tcsize.x <= width) {
				tcsize = minWidth(tcsize, textClientCache.computeSize(SWT.DEFAULT, SWT.DEFAULT));
			} else if (width > 0) {
				tcsize = textLabel.computeSize(width, SWT.DEFAULT, true);

			}
			return tcsize;
		}

		private Point recomputeTextClientSize(int width, Point tcsize, Point size) {
			if (tcsize.x > 0 && width > 0) {
				int widthHint = width;
				if (size.x > 0)
					widthHint -= IGAP + size.x;
				if (widthHint > 0 && (widthHint < tcsize.x || (expansionStyle & LEFT_TEXT_CLIENT_ALIGNMENT) != 0)) {
					tcsize = textClientCache.computeSize(widthHint, SWT.DEFAULT);

					if (tcsize.x > width) {
						tcsize = textLabel.computeSize(widthHint, SWT.DEFAULT, true);
					}
				}
			}
			return tcsize;
		}

		private Point computeTextLabelSize(int width, Point tcsize) {
			if (textLabel == null || width == 0)
				return NULL_SIZE;
			Point size = textLabelCache.computeSize(width, SWT.DEFAULT);
			if (tcsize.x > 0 && width > 0) {
				if (size.x <= width) {
					size = minWidth(size, textLabelCache.computeSize(SWT.DEFAULT, SWT.DEFAULT));

				}

				int maxSize = size.x + IGAP + tcsize.x;
				int fillHint = width - IGAP - tcsize.x;
				if (width < maxSize) {
					int textLabelWidthHint = Math.round(width * (size.x / (float) (maxSize)));
					if (fillHint > textLabelWidthHint / 2)
						textLabelWidthHint = fillHint;

					if (textLabel instanceof Label) {
						GC gc = new GC(textLabel);
						size = FormUtil.computeWrapSize(gc, ((Label) textLabel).getText(), textLabelWidthHint);
						gc.dispose();
					} else
						size = textLabelCache.computeSize(textLabelWidthHint, SWT.DEFAULT);

				}
			}
			return size;
		}

