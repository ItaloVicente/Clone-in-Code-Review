			if (l == maxWidth) {
				break;
			} else if (l > maxWidth) {
				largestTooFewClipped = charsToClip;
				widthAtLargest = l;
				if (charsToClip >= smallestTooManyClipped - 1) {
					charsToClip++;
					break;
				}
				if (l - maxWidth <= characterDelta * 2) {
					charsToClip++;
				} else {
					int deltaWidth = l - widthAtSmallest;
					int desiredWidthChange = l - maxWidth;
					charsToClip = charsToClip
							+ (smallestTooManyClipped - charsToClip) * desiredWidthChange / deltaWidth;
					if (charsToClip == oldCharsToClip) {
						charsToClip++;
					}
				}
			} else {
				smallestTooManyClipped = charsToClip;
				widthAtSmallest = l;
				if (charsToClip <= largestTooFewClipped + 1) {
					break;
				}
				if (maxWidth - l <= characterDelta * 2) {
					charsToClip--;
				} else {
					int deltaWidth = widthAtLargest - l;
					int desiredWidthChange = maxWidth - l;
					charsToClip = charsToClip - (charsToClip - largestTooFewClipped) * desiredWidthChange / deltaWidth;
					if (charsToClip == oldCharsToClip) {
						charsToClip--;
					}
				}
