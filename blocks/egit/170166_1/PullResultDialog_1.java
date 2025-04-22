
	private void createToggleButton(Composite parent) {
		boolean toggleState = !Activator.getDefault().getPreferenceStore()
				.getBoolean(UIPreferences.SHOW_PULL_CONFIRM);
		toggleButton = new Button(parent, SWT.CHECK | SWT.LEFT);
		toggleButton.setText(UIText.PullResultDialog_ToggleShowButton);
		toggleButton.setSelection(toggleState);
	}

	@Override
	protected void buttonPressed(int buttonId) {
		if (toggleButton != null)
			Activator.getDefault().getPreferenceStore().setValue(
					UIPreferences.SHOW_PULL_CONFIRM,
					!toggleButton.getSelection());
		super.buttonPressed(buttonId);
	}
