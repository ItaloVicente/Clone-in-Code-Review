	public void addHelpListener(HelpListener listener) {
		helpListeners.add(listener);
		if (!helpHooked) {
			Control control = getControl();
			if (control != null && !control.isDisposed()) {
				if (this.helpListener == null) {
