		Control control = getLabelControl();
		((GridData) control.getLayoutData()).horizontalSpan = numColumns;
		((GridData) list.getLayoutData()).horizontalSpan = numColumns - 1;
	}

	private void createButtons(Composite box) {
		addButton = createPushButton(box, "ListEditor.add");//$NON-NLS-1$
		removeButton = createPushButton(box, "ListEditor.remove");//$NON-NLS-1$
		upButton = createPushButton(box, "ListEditor.up");//$NON-NLS-1$
		downButton = createPushButton(box, "ListEditor.down");//$NON-NLS-1$
	}

	protected abstract String createList(String[] items);

	private Button createPushButton(Composite parent, String key) {
		Button button = new Button(parent, SWT.PUSH);
		button.setText(JFaceResources.getString(key));
		button.setFont(parent.getFont());
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		int widthHint = convertHorizontalDLUsToPixels(button,
				IDialogConstants.BUTTON_WIDTH);
		data.widthHint = Math.max(widthHint, button.computeSize(SWT.DEFAULT,
				SWT.DEFAULT, true).x);
		button.setLayoutData(data);
		button.addSelectionListener(getSelectionListener());
		return button;
	}

	public void createSelectionListener() {
		selectionListener = widgetSelectedAdapter(event -> {
			Widget widget = event.widget;
			if (widget == addButton) {
				addPressed();
			} else if (widget == removeButton) {
				removePressed();
			} else if (widget == upButton) {
				upPressed();
			} else if (widget == downButton) {
				downPressed();
			} else if (widget == list) {
				selectionChanged();
			}
