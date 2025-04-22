	protected void addWindowActivationHelper(Control control) {
		if (windowActivationHelper != null) {
			control.addMouseListener(windowActivationHelper);
		}
	}

