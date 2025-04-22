		Composite composite = (Composite) super.createDialogArea(parent);

		createMessageArea(composite);
		listViewer = new ListViewer(composite, SWT.SINGLE | SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.BORDER);
		GridData data = new GridData(GridData.FILL_BOTH);
		data.heightHint = convertHeightInCharsToPixels(LIST_HEIGHT);
		data.widthHint = convertWidthInCharsToPixels(LIST_WIDTH);
		listViewer.getList().setLayoutData(data);
		listViewer.getList().setFont(parent.getFont());
		listViewer.setLabelProvider(new LabelProvider() {
			@Override
