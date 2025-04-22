		CoolBar coolBar = coolItem.getParent();
		boolean isLastOnRow = false;
		int lastIndex = coolBar.getItemCount() - 1;
		int coolItemIndex = coolBar.indexOf(coolItem);
		int[] wrapIndicies = getAdjustedWrapIndices(coolBar.getWrapIndices());
		for (int row = wrapIndicies.length - 1; row >= 0; row--) {
			if (wrapIndicies[row] <= coolItemIndex) {

				int nextRow = row + 1;
				int nextRowStartIndex;
				if (nextRow > (wrapIndicies.length - 1)) {
					nextRowStartIndex = lastIndex + 1;
				} else {
					nextRowStartIndex = wrapIndicies[nextRow];
				}

				if (coolItemIndex == (nextRowStartIndex - 1)) {
					isLastOnRow = true;
				}
				break;
			}
		}

		int nCurrentWidth;
		if (isLastOnRow) {
			nCurrentWidth = coolItem.getPreferredSize().x;
		} else {
			nCurrentWidth = coolItem.getSize().x;
		}
		setCurrentWidth(nCurrentWidth);
		setCurrentHeight(coolItem.getSize().y);
	}

	@Override
