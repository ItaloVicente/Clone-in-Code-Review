		updateConfigComposite(!showAll);
	}

	private void updateConfigComposite(boolean enabled) {
		recursivelySetEnabled(configComposite, enabled);
		if (enabled)
			updateButtonEnablement(getSelectionFromTable());
	}

	private void recursivelySetEnabled(Control control, boolean enabled) {
		if (control instanceof Composite) {
			for (Control child : ((Composite) control).getChildren()) {
				recursivelySetEnabled(child, enabled);
			}
		}
		control.setEnabled(enabled);
