		Composite detailsComp = new Composite(composite, SWT.NONE);
		detailsComp.setLayout(new GridLayout());
		detailsComp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		descriptionText = new Text(detailsComp, SWT.READ_ONLY | SWT.WRAP | SWT.BORDER | SWT.V_SCROLL);
		data = new GridData(SWT.FILL, SWT.FILL, true, false);
		data.heightHint = Dialog.convertHeightInCharsToPixels(fontMetrics, 5);
		descriptionText.setLayoutData(data);
		setInitialStates();

		new Label(detailsComp, SWT.NONE).setText(ActivityMessages.ActivitiesPreferencePage_requirements);
		dependantViewer = new TableViewer(detailsComp, SWT.BORDER);
		dependantViewer.getControl().setLayoutData(new GridData(GridData.FILL_BOTH));
		dependantViewer.setContentProvider(new ActivityCategoryContentProvider());
		dependantViewer.setLabelProvider(new ActivityCategoryLabelProvider());
		dependantViewer.setInput(Collections.EMPTY_SET);
