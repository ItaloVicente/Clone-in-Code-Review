		if (selectedTabFillColors == null) {
			setSelectedTabFill(gc.getDevice().getSystemColor(SWT.COLOR_WHITE));
		}
		if (selectedTabFillColors.length == 1) {
			gc.setBackground(selectedTabFillColors[0]);
			gc.setForeground(selectedTabFillColors[0]);
		} else if (!onBottom && selectedTabFillColors.length == 2) {
