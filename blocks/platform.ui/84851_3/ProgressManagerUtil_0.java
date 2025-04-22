
		int currentLength;
		int upperBoundWidth;
		int upperBoundLength = 0;

		int lowerBoundLength = 0;
		int lowerBoundWidth = 0;

		int estimatedCharactersThatWillFit = maxWidth / averageCharWidth;

		if (estimatedCharactersThatWillFit >= length) {
			int maxExtent = gc.textExtent(textValue).x;
			if (maxExtent <= maxWidth) {
				return textValue;
			}
			currentLength = Math.max(0,
					Math.round(length * ((float) maxWidth / maxExtent)) - ellipsisString.length());
			upperBoundWidth = maxExtent;
			upperBoundLength = length;
		} else {
			currentLength = Math.min(length, Math.max(0, estimatedCharactersThatWillFit - ellipsisString.length()));
			for (;;) {
				String s = clipToLength(textValue, ellipsisString, pivot, currentLength);
				int currentExtent = gc.textExtent(s).x;
				if (currentExtent > maxWidth) {
					upperBoundWidth = currentExtent;
					upperBoundLength = currentLength;
					break;
				}
				if (currentLength == length) {
					return textValue;
				}
				lowerBoundWidth = currentExtent;
				lowerBoundLength = currentLength;
				currentLength = Math.min(length, currentLength * 2 + 1);
			}
		}

		String s;
		for (;;) {
			int oldLength = currentLength;
			s = clipToLength(textValue, ellipsisString, pivot, currentLength);

