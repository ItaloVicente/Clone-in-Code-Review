			int tooBigBy = l - maxWidth;
			if (tooBigBy == 0) {
				break;
			} else if (tooBigBy > 0) {
				upperBoundLength = currentLength;
				upperBoundWidth = l;
				if (currentLength <= lowerBoundLength + 1) {
					currentLength = lowerBoundLength;
					break;
				}
				if (tooBigBy <= averageCharWidth * 2) {
					currentLength--;
				} else {
					int spaceToRightOfLowerBound = maxWidth - lowerBoundWidth;
					currentLength = lowerBoundLength
							+ (currentLength - lowerBoundLength) * spaceToRightOfLowerBound / (l - lowerBoundWidth);
					if (currentLength >= oldLength) {
						currentLength = oldLength - 1;
					} else if (currentLength <= lowerBoundLength) {
						currentLength = lowerBoundLength + 1;
					}
				}
			} else {
				lowerBoundLength = currentLength;
				lowerBoundWidth = l;
				if (currentLength >= upperBoundLength - 1) {
					currentLength = upperBoundLength - 1;
					break;
				}
				if (-tooBigBy <= averageCharWidth * 2) {
					currentLength++;
				} else {
					currentLength = currentLength
							+ (upperBoundLength - currentLength) * (-tooBigBy) / (upperBoundWidth - l);
					if (currentLength <= oldLength) {
						currentLength = oldLength + 1;
					} else if (currentLength >= upperBoundLength) {
						currentLength = upperBoundLength - 1;
					}
				}
