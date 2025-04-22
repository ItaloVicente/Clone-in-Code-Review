		int deltaWidth = countPixelsToDrawDistrinctColors(innerKeylineColor, tabOutlineColor)
				+ 2 * (shadowEnabled ? SIDE_DROP_WIDTH : 0)
				+ 2 * marginWidth;
		int deltaHeight = countPixelsToDrawDistrinctColors(innerKeylineColor, tabOutlineColor)
				+ 2 * (shadowEnabled ? BOTTOM_DROP_WIDTH : 0) + 2 * marginHeight;
		int width = bounds.width - deltaWidth;
