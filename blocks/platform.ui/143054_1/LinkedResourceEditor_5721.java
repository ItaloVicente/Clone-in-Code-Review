	private void initializeDialogUnits(Control control) {
		GC gc = new GC(control);
		gc.setFont(control.getFont());
		fontMetrics = gc.getFontMetrics();
		gc.dispose();
	}

