	protected void createOpenModeGroup(Composite composite) {

		Font font = composite.getFont();

		Group buttonComposite = new Group(composite, SWT.LEFT);
		GridLayout layout = new GridLayout();
		buttonComposite.setLayout(layout);
		GridData data = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL);
		buttonComposite.setLayoutData(data);
		buttonComposite.setText(WorkbenchMessages.WorkbenchPreference_openMode);

		String label = WorkbenchMessages.WorkbenchPreference_doubleClick;
		doubleClickButton = createRadioButton(buttonComposite, label);
		doubleClickButton
				.addSelectionListener(widgetSelectedAdapter(e -> selectClickMode(singleClickButton.getSelection())));
		doubleClickButton.setSelection(!openOnSingleClick);

		label = WorkbenchMessages.WorkbenchPreference_singleClick;
		singleClickButton = createRadioButton(buttonComposite, label);
		singleClickButton
				.addSelectionListener(widgetSelectedAdapter(e -> selectClickMode(singleClickButton.getSelection())));
		singleClickButton.setSelection(openOnSingleClick);

		label = WorkbenchMessages.WorkbenchPreference_singleClick_SelectOnHover;
		selectOnHoverButton = new Button(buttonComposite, SWT.CHECK | SWT.LEFT);
		selectOnHoverButton.setText(label);
		selectOnHoverButton.setEnabled(openOnSingleClick);
		selectOnHoverButton.setSelection(selectOnHover);
		selectOnHoverButton
				.addSelectionListener(widgetSelectedAdapter(e -> selectOnHover = selectOnHoverButton.getSelection()));
		data = new GridData();
