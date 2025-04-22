	@Override
	public void reset() {
		super.reset();
		Control control = getControl();
		control.setBackground(null);
		control.setForeground(null);
		control.setFont(control.getDisplay().getSystemFont());
		control.setCursor(control.getDisplay()
				.getSystemCursor(SWT.CURSOR_ARROW));
		control.setBackgroundImage(null);
		GradientBackgroundListener.remove(control);
	}

