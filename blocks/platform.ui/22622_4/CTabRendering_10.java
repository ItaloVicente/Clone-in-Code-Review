		setSelectedTabFill(new Color[] { color }, new int[] { 100 });
	}

	public void setSelectedTabFill(Color[] colors, int[] percents) {
		selectedTabFillColors = colors;
		selectedTabFillPercents = percents;
		parent.redraw();
	}

	public void setUnselectedTabsColor(Color color) {
		setUnselectedTabsColor(new Color[] { color }, new int[] { 100 });
	}

	public void setUnselectedTabsColor(Color[] colors, int[] percents) {
		unselectedTabsColors = colors;
		unselectedTabsPercents = percents;
