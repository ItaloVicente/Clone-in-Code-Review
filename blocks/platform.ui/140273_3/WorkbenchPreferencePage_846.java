		openAfterDelayButton.setLayoutData(data);

		createNoteComposite(font, buttonComposite, WorkbenchMessages.Preference_note,
				WorkbenchMessages.WorkbenchPreference_noEffectOnAllViews);
	}

	private void selectClickMode(boolean singleClick) {
		openOnSingleClick = singleClick;
		selectOnHoverButton.setEnabled(openOnSingleClick);
		openAfterDelayButton.setEnabled(openOnSingleClick);
	}

	protected static Button createRadioButton(Composite parent, String label) {
		Button button = new Button(parent, SWT.RADIO | SWT.LEFT);
		button.setText(label);
		return button;
	}

	protected static Combo createCombo(Composite parent) {
		Combo combo = new Combo(parent, SWT.READ_ONLY);
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.widthHint = IDialogConstants.ENTRY_FIELD_WIDTH;
		combo.setLayoutData(data);
		return combo;
	}

	protected static Label createLabel(Composite parent, String text) {
		Label label = new Label(parent, SWT.LEFT);
		label.setText(text);
		GridData data = new GridData();
		data.horizontalSpan = 1;
		data.horizontalAlignment = GridData.FILL;
		label.setLayoutData(data);
		return label;
	}

	protected static void createSpace(Composite parent) {
		Label vfiller = new Label(parent, SWT.LEFT);
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.BEGINNING;
		gridData.grabExcessHorizontalSpace = false;
		gridData.verticalAlignment = GridData.CENTER;
		gridData.grabExcessVerticalSpace = false;
		vfiller.setLayoutData(gridData);
	}

	@Override
