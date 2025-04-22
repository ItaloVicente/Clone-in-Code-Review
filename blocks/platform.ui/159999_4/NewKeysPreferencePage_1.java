		fShowCommandKeyForMouseEvents = new Button(group, SWT.CHECK);
		fShowCommandKeyForMouseEvents.setText(NewKeysPreferenceMessages.ShowCommandKeysForMouse_Text);
		fShowCommandKeyForMouseEvents
				.setSelection(getPreferenceStore().getBoolean(IPreferenceConstants.SHOW_KEYS_ENABLED_FOR_MOUSE_EVENTS));
		fShowCommandKeyForMouseEvents.addSelectionListener(SelectionListener.widgetSelectedAdapter(e -> {
			if (fShowCommandKeyForMouseEvents.getSelection()) {
				showKeysUI.openForPreview(ShowKeysToggleHandler.COMMAND_ID, null);
			}
		}));
