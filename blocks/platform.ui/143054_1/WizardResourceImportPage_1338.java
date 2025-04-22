		initializeDialogUnits(parent);

		Composite composite = new Composite(parent, SWT.NULL);
		composite.setLayout(new GridLayout());
		composite.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_FILL | GridData.HORIZONTAL_ALIGN_FILL));
		composite.setSize(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		composite.setFont(parent.getFont());

		createSourceGroup(composite);

		createDestinationGroup(composite);

		createOptionsGroup(composite);

		restoreWidgetValues();
		updateWidgetEnablements();
		setPageComplete(determinePageCompletion());
		setErrorMessage(null); // should not initially have error message

		setControl(composite);
	}

	protected void createDestinationGroup(Composite parent) {
		Composite containerGroup = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		containerGroup.setLayout(layout);
		containerGroup.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL));
		containerGroup.setFont(parent.getFont());

		Label resourcesLabel = new Label(containerGroup, SWT.NONE);
		resourcesLabel.setText(IDEWorkbenchMessages.WizardImportPage_folder);
		resourcesLabel.setFont(parent.getFont());

		containerNameField = new Text(containerGroup, SWT.SINGLE | SWT.BORDER);
		BidiUtils.applyBidiProcessing(containerNameField, StructuredTextTypeHandlerFactory.FILE);

		containerNameField.addListener(SWT.Modify, this);
		GridData data = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL);
		data.widthHint = SIZING_TEXT_FIELD_WIDTH;
		containerNameField.setLayoutData(data);
		containerNameField.setFont(parent.getFont());

		containerBrowseButton = new Button(containerGroup, SWT.PUSH);
		containerBrowseButton.setText(IDEWorkbenchMessages.WizardImportPage_browse2);
		containerBrowseButton.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));
		containerBrowseButton.addListener(SWT.Selection, this);
		containerBrowseButton.setFont(parent.getFont());
		setButtonLayoutData(containerBrowseButton);

		initialPopulateContainerField();
	}

	protected void createFileSelectionGroup(Composite parent) {

		this.selectionGroup = new ResourceTreeAndListGroup(parent, new FileSystemElement("Dummy", null, true), //$NON-NLS-1$
				getFolderProvider(), new WorkbenchLabelProvider(), getFileProvider(), new WorkbenchLabelProvider(),
				SWT.NONE, DialogUtil.inRegularFontMode(parent));

		ICheckStateListener listener = event -> updateWidgetEnablements();

		WorkbenchViewerComparator comparator = new WorkbenchViewerComparator();
		this.selectionGroup.setTreeComparator(comparator);
		this.selectionGroup.setListComparator(comparator);
		this.selectionGroup.addCheckStateListener(listener);

	}

	protected abstract void createSourceGroup(Composite parent);

	@Override
