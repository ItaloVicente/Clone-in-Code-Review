
	@Override
	public void setForeground(Color color) {
		if (color == null) {
			usedMemCol = getDisplay().getSystemColor(SWT.COLOR_INFO_BACKGROUND);
		} else {
			usedMemCol = color;
		}

		button.redraw();
		button.update();
	}

	@Override
	public Color getForeground() {
		if (usedMemCol != null) {
			return usedMemCol;
		}
		return getDisplay().getSystemColor(SWT.COLOR_INFO_BACKGROUND);
	}

