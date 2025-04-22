	protected void setOkButtonEnabled(boolean enabled) {
		if (getButton(Window.OK) != null)
			getButton(Window.OK).setEnabled(enabled);
	}

	protected boolean isOkButtonEnabled() {
		return getButton(Window.OK) != null && getButton(Window.OK).isEnabled();
	}

