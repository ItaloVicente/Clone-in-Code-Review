		CURRENT_METHOD = method;
	}

	public static boolean activateOnOpen() {
		return getOpenMethod() == DOUBLE_CLICK;
	}

	private void addListener(Control c) {
		c.addListener(SWT.MouseEnter, eventHandler);
		c.addListener(SWT.MouseExit, eventHandler);
		c.addListener(SWT.MouseMove, eventHandler);
		c.addListener(SWT.MouseDown, eventHandler);
		c.addListener(SWT.MouseUp, eventHandler);
		c.addListener(SWT.KeyDown, eventHandler);
		c.addListener(SWT.Selection, eventHandler);
		c.addListener(SWT.DefaultSelection, eventHandler);
		c.addListener(SWT.Collapse, eventHandler);
		c.addListener(SWT.Expand, eventHandler);
	}

	private void fireSelectionEvent(SelectionEvent e) {
		if (e.item != null && e.item.isDisposed()) {
