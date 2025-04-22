
	private void detailsEnabled(Control ctrl, boolean value) {
		if (ctrl instanceof Composite) {
			Composite comp = (Composite) ctrl;
			for (Control c : comp.getChildren())
				detailsEnabled(c, value);
		} else {
			ctrl.setEnabled(value);
		}
	}

