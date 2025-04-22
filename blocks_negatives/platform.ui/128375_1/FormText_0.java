	private void repaint(GC gc, int x, int y, int width, int height) {
		Image textBuffer = new Image(getDisplay(), width, height);
		Color bg = getBackground();
		Color fg = getForeground();
		if (!getEnabled()) {
			bg = getDisplay().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND);
			fg = getDisplay().getSystemColor(SWT.COLOR_WIDGET_NORMAL_SHADOW);
