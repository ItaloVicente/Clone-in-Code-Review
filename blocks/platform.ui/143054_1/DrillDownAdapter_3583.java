	protected void updateNavigationButtons() {
		if (homeAction != null) {
			homeAction.setEnabled(canGoHome());
			backAction.setEnabled(canGoBack());
			forwardAction.setEnabled(canGoInto());
		}
	}
