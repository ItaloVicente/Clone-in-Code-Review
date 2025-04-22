

			Point tcsize = textClientCache.computeSize(tcWidthAfterSplit, SWT.DEFAULT);
			Point size = textLabelCache.computeSize(labelWidthAfterSplit, SWT.DEFAULT);

			int height = Math.max(tcsize.y, size.y); // max of label/text client
			height = Math.max(height, tsize.y); // or max of toggle

			boolean leftAlignment = textClient != null && (expansionStyle & LEFT_TEXT_CLIENT_ALIGNMENT) != 0;
