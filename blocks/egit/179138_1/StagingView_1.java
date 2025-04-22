
	private int getMainSashFormOrientation() {
		IDialogSettings settings = getDialogSettings();
		return settings.getBoolean(MAIN_SASH_FORM_ORIENTATION_VERTICAL)
				? SWT.VERTICAL
				: SWT.HORIZONTAL;
	}

