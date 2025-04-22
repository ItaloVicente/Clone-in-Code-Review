
	private Button createCheckBox(Composite group, String label) {
		Button button = new Button(group, SWT.CHECK | SWT.LEFT);
		button.setText(label);
		GridData data = new GridData(GridData.FILL);
		data.verticalAlignment = GridData.CENTER;
		data.horizontalAlignment = GridData.FILL;
		button.setLayoutData(data);
		return button;
	}

	private void handleWarnCheckboxSelection(boolean selection) {
		if (selection) {
			if (showWarning != null) {
				showWarning.restore();
				showWarning = null;
			}
		} else {
			if (showWarning == null) {
			}
		}
	}

	private void handleBlockCheckboxSelection(boolean selection) {
		if (selection) {
			if (showBlock != null) {
				showBlock.restore();
				showBlock = null;
			}
		} else {
			if (showBlock == null) {
			}
		}
	}

	@Override
	public boolean performOk() {
		doGetPreferenceStore().setValue(UIPreferences.WARN_BEFORE_COMMITTING,
				warnCheckbox.getSelection());
		doGetPreferenceStore().setValue(UIPreferences.BLOCK_COMMIT,
				blockCheckbox.getSelection());
		return super.performOk();
	}

	private Group createGroup(Composite parent, int numColumns) {
		Group group = new Group(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		layout.numColumns = numColumns;
		group.setLayout(layout);
		GridData data = new GridData(SWT.FILL);
		data.horizontalIndent = 0;
		data.verticalAlignment = SWT.FILL;
		data.horizontalAlignment = SWT.END;
		data.grabExcessHorizontalSpace = true;
		group.setLayoutData(data);
		return group;
	}
