		int selectedItemWidth = parent.getSelectionIndex() > -1 ? parent
				.getSelection().getControl().getBounds().width : parent
				.getBounds().width;
		int leftRightBorder = (parent.getBounds().width - selectedItemWidth) / 2;
		int topBorder = INNER_KEYLINE + OUTER_KEYLINE;

		rendererWrapper.drawBackground(gc,
				partHeaderBounds.x + leftRightBorder, partHeaderBounds.height
						+ topBorder, partHeaderBounds.width - leftRightBorder
				defaultBackground, getUnselectedTabsColors(state),
				getUnselectedTabsPercents(state), vertical);
