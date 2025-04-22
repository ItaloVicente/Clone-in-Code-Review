	private Control addMenuListener(Control control) {
		if (menuListener != null) {
			control.addListener(SWT.MenuDetect, menuListener);
		}
		return control;
	}

