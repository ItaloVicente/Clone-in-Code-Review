	/**
	 * Recursively walk through the tree of components and set enabled state of
	 * each control.
	 *
	 * @param control
	 *            The root control
	 * @param enabled
	 *            Whether or not we're enabled.
	 */
	private void recursivelySetEnabled(Control control, boolean enabled) {
		if (control instanceof Composite) {
			for (Control child : ((Composite) control).getChildren()) {
				recursivelySetEnabled(child, enabled);
			}
		}
		control.setEnabled(enabled);
	}

	/** Update the enablement of components in the limit composite */
	private void updateLimitsCompositeEnablement() {
		boolean enableAll = !allButton.getSelection();
		recursivelySetEnabled(compositeLimits, enableAll);
		updateLimitTextEnablement();
	}

