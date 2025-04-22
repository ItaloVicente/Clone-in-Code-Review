	public Color getForeground(Object element, int columnIndex) {
		if (columnIndex == 0) {
			final FileDiff c = (FileDiff) element;
			if (!c.isMarked()) {
				return resourceManager.getDevice().getSystemColor(SWT.COLOR_DARK_GRAY);
			}
		}
		return null;
	}

	public Color getBackground(Object element, int columnIndex) {
		return null;
	}
