		if (selectedTabFillColor == null)
			selectedTabFillColor = gc.getDevice().getSystemColor(
					SWT.COLOR_WHITE);
		gc.setBackground(selectedTabFillColor);
		gc.setForeground(selectedTabFillColor);
		Color gradientTop = null;
