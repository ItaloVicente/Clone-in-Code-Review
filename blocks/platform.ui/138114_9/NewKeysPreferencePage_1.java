	private void createShowKeysControls(Composite parent) {
		ShowKeysUI showKeysUI = new ShowKeysUI(fWorkbench, getPreferenceStore());

		final Composite controls = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(1).applyTo(controls);
		fShowCommandKey = new Button(controls, SWT.CHECK);
		fShowCommandKey.setText(NewKeysPreferenceMessages.ShowCommandKeys_Text);
		fShowCommandKey.setSelection(getPreferenceStore().getBoolean(IPreferenceConstants.SHOW_KEYS_ENABLED));
		fShowCommandKey.addSelectionListener(SelectionListener.widgetSelectedAdapter((e) -> {
			if (fShowCommandKey.getSelection()) {
				showKeysUI.openForPreview(ShowKeysToggleHandler.COMMAND_ID, null);
			}
		}));
	}

