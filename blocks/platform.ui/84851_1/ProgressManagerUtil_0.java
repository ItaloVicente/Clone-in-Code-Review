		int widthAtLargest = maxExtent;
		int largestTooFewClipped = 0;
		int smallestTooManyClipped = length;
		int widthAtSmallest = 0;
		int charsToClip = Math.min(length - 1,
				Math.round(length * (1 - ((float) maxWidth / maxExtent))) + ellipsis.length());
		String s;
		for (;;) {
			int oldCharsToClip = charsToClip;
			s = getClippedString(textValue, pivot, charsToClip);

