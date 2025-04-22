	public void removeHelpListener(HelpListener listener) {
		helpListeners.remove(listener);
		if (helpListeners.isEmpty()) {
			Control control = getControl();
			if (control != null && !control.isDisposed()) {
				control.removeHelpListener(this.helpListener);
				helpHooked = false;
			}
		}
	}
