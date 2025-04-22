		Composite composite = (Composite) super.createDialogArea(parent);
		createMessageArea(composite);
		listViewer = CheckboxTableViewer.newCheckList(composite, SWT.BORDER);
		GridData data = new GridData(GridData.FILL_BOTH);
		data.heightHint = SIZING_SELECTION_WIDGET_HEIGHT;
		data.widthHint = SIZING_SELECTION_WIDGET_WIDTH;
		listViewer.getTable().setLayoutData(data);
		listViewer.getTable().setFont(parent.getFont());
		listViewer.setLabelProvider(FileEditorMappingLabelProvider.INSTANCE);
