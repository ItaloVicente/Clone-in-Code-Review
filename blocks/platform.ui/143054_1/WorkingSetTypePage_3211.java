		Font font = parent.getFont();
		Composite composite = new Composite(parent, SWT.NULL);
		composite.setLayout(new GridLayout());
		composite.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));
		setControl(composite);

		PlatformUI.getWorkbench().getHelpSystem().setHelp(composite, IWorkbenchHelpContextIds.WORKING_SET_TYPE_PAGE);
		Label typesLabel = new Label(composite, SWT.NONE);
		typesLabel.setText(WorkbenchMessages.WorkingSetTypePage_typesLabel);
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		typesLabel.setLayoutData(data);
		typesLabel.setFont(font);

		typesListViewer = new TableViewer(composite, SWT.BORDER | SWT.SINGLE);
		data = new GridData(GridData.FILL_BOTH);
		data.heightHint = SIZING_SELECTION_WIDGET_HEIGHT;
		data.widthHint = SIZING_SELECTION_WIDGET_WIDTH;
		typesListViewer.getTable().setLayoutData(data);
		typesListViewer.getTable().setFont(font);
		typesListViewer.addSelectionChangedListener(event -> handleSelectionChanged());
		typesListViewer.addDoubleClickListener(event -> handleDoubleClick());
		typesListViewer.setContentProvider(new ArrayContentProvider());
		typesListViewer.setLabelProvider(new LabelProvider() {
			private ResourceManager images = new LocalResourceManager(JFaceResources.getResources());

