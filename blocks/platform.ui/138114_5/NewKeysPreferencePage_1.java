	private void createShowKeysControls(Composite parent) {
		final Composite controls = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(1).applyTo(controls);

		fShowCommandKey = new Button(controls, SWT.CHECK);
		fShowCommandKey.setText(NewKeysPreferenceMessages.ShowCommandKeys_Text);
		fShowCommandKey.setSelection(getPreferenceStore().getBoolean(IPreferenceConstants.SHOW_KEYS_ENABLED));
	}

