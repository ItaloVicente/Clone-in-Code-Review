			unselectedTabsPercents = selected ? parentWrapper
					.getSelectionGradientPercents() :
				parentWrapper.getGradientPercents();
		}
		if (unselectedTabsColors == null) {
			unselectedTabsColors = new Color[] { gc.getDevice().getSystemColor(
					SWT.COLOR_WHITE) };
			unselectedTabsPercents = new int[] { 100 };
