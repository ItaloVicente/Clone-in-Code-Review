
			Point tcsize = computeTextClientSize(twidth);
			Point size = computeTextLabelSize(twidth, tcsize);
			tcsize = recomputeTextClientSize(twidth, tcsize, size);

			int height = Math.max(tcsize.y, size.y); // max of label/text client
			height = Math.max(height, tsize.y); // or max of toggle

			boolean leftAlignment = textClient != null && (expansionStyle & LEFT_TEXT_CLIENT_ALIGNMENT) != 0;
