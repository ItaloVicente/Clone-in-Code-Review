		return unselectedTabsColors;
	}

	private int[] getUnselectedTabsPercents(int state) {
		if (unselectedTabsPercents == null) {
			return (state & SWT.SELECTED) != 0 ? parentWrapper
					.getSelectionGradientPercents() : parentWrapper
					.getGradientPercents();
