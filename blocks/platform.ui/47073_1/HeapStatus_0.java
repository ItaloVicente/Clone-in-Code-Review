
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

