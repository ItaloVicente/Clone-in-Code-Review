		Composite dialogArea = (Composite) super.createDialogArea(parent);
		Label l = new Label(dialogArea, SWT.NONE);
		l.setText(IDEWorkbenchMessages.ResourceSelectionDialog_label);
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		l.setLayoutData(data);

		pattern = new Text(dialogArea, SWT.SINGLE | SWT.BORDER);
		pattern.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		l = new Label(dialogArea, SWT.NONE);
		l.setText(IDEWorkbenchMessages.ResourceSelectionDialog_matching);
		data = new GridData(GridData.FILL_HORIZONTAL);
		l.setLayoutData(data);
		resourceNames = new Table(dialogArea, SWT.SINGLE | SWT.BORDER | SWT.V_SCROLL);
		data = new GridData(GridData.FILL_BOTH);
		data.heightHint = 12 * resourceNames.getItemHeight();
		resourceNames.setLayoutData(data);

		l = new Label(dialogArea, SWT.NONE);
		l.setText(IDEWorkbenchMessages.ResourceSelectionDialog_folders);
		data = new GridData(GridData.FILL_HORIZONTAL);
		l.setLayoutData(data);

		folderNames = new Table(dialogArea, SWT.SINGLE | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		data = new GridData(GridData.FILL_BOTH);
		data.widthHint = 300;
		data.heightHint = 4 * folderNames.getItemHeight();
		folderNames.setLayoutData(data);

		if (gatherResourcesDynamically) {
			updateGatherThread = new UpdateGatherThread();
		} else {
			updateFilterThread = new UpdateFilterThread();
		}

		pattern.addKeyListener(new KeyAdapter() {
			@Override
