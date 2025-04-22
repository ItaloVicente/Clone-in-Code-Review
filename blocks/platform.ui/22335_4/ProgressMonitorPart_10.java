		blockedStatus = reason;
		updateLabel();

	}

	private void setCancelEnabled(boolean enabled) {
		if (fStopButton != null && !fStopButton.isDisposed()) {
			fStopButton.setEnabled(enabled);
			if (enabled)
				fToolBar.setFocus();
		}
	}
