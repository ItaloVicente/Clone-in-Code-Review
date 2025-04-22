			borderTopBottom = countPixelsToDrawDistrinctColors(outerKeylineColor, tabOutlineColor);
			x = x - borderTopBottom - sideDropWidth
					- ITEM_LEFT_MARGIN;
			width = width
					+ 2 * (borderTopBottom + sideDropWidth)
					+ ITEM_RIGHT_MARGIN;
			height += 2 * borderTopBottom + marginHeight;
			y -= borderTopBottom;
