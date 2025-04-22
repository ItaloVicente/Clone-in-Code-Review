		final Group group = new Group(parent, SWT.NONE);
		group.setText(NewKeysPreferenceMessages.ShowCommandKeysGroup_Title);
		GridDataFactory.fillDefaults().applyTo(group);
		GridLayoutFactory.fillDefaults().numColumns(1).applyTo(group);
		fShowCommandKey = new Button(group, SWT.CHECK);
		fShowCommandKey.setText(NewKeysPreferenceMessages.ShowCommandKeysForKeyboard_Text);
		fShowCommandKey.setSelection(getPreferenceStore().getBoolean(IPreferenceConstants.SHOW_KEYS_ENABLED_FOR_KEYBOARD));
