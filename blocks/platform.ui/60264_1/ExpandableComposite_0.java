				size = textLabelCache.computeSize(twidth, SWT.DEFAULT);
				if (tcsize.x > 0) {
					int maxSize = size.x + IGAP + tcsize.x;
					if (twidth < maxSize) {
						int textLabelWidthHint = Math.round(twidth * (size.x / (float) (maxSize)));
						size = computeTextLabelSize(textLabelWidthHint);
