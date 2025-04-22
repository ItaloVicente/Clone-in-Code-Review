	@Override
	public void reset() {
		super.reset();
		Control control = getControl();
		control.setFont(control.getDisplay().getSystemFont());
		control.setCursor(control.getDisplay()
				.getSystemCursor(SWT.CURSOR_ARROW));
		control.setBackgroundImage(null);
		GradientBackgroundListener.remove(control);

		if (WEBSITE_CLASS.equals(control.getClass().getName())) {
			control.setBackground(control.getDisplay().getSystemColor(
					SWT.COLOR_LIST_BACKGROUND));
			control.setForeground(control.getDisplay().getSystemColor(
					SWT.COLOR_LINK_FOREGROUND));
		} else {
			control.setBackground(null);
			control.setForeground(null);
		}
	}

