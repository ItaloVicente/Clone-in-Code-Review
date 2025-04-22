				size = textLabelCache.computeSize(innerwHint, SWT.DEFAULT);
				if (innerwHint != SWT.DEFAULT) {
					if (tcsize.x > 0) {
						int maxSize = size.x + IGAP + tcsize.x;
						if (innerwHint < maxSize) {
							int textLabelWidthHint = Math.round(innerwHint * (size.x / (float) (maxSize)));
							size = computeTextLabelSize(textLabelWidthHint);
						}
						tcsize = textClientCache.computeSize(innerwHint - (size.x + IGAP), SWT.DEFAULT);
