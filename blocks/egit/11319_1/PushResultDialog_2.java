
	private void createToggleButton(Composite parent) {
		boolean toggleState = !Activator.getDefault().getPreferenceStore()
				.getBoolean(UIPreferences.SHOW_REBASE_CONFIRM);
		toggleButton = new Button(parent, SWT.CHECK | SWT.LEFT);
		toggleButton.setText(UIText.PushResultDialog_ToggleShowButton);
		toggleButton.setSelection(toggleState);
	}
