		Font font = parent.getFont();

		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout());
		composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		composite.setFont(font);

		PlatformUI.getWorkbench().getHelpSystem().setHelp(composite,
				IIDEHelpContextIds.NEW_PROJECT_REFERENCE_WIZARD_PAGE);

		Label referenceLabel = new Label(composite, SWT.NONE);
		referenceLabel.setText(REFERENCED_PROJECTS_TITLE);
		referenceLabel.setFont(font);

		referenceProjectsViewer = CheckboxTableViewer.newCheckList(composite, SWT.BORDER);
		referenceProjectsViewer.getTable().setFont(composite.getFont());
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);

		data.heightHint = getDefaultFontHeight(referenceProjectsViewer.getTable(), PROJECT_LIST_MULTIPLIER);
		referenceProjectsViewer.getTable().setLayoutData(data);
		referenceProjectsViewer.setLabelProvider(WorkbenchLabelProvider.getDecoratingWorkbenchLabelProvider());
		referenceProjectsViewer.setContentProvider(getContentProvider());
		referenceProjectsViewer.setComparator(new ViewerComparator());
		referenceProjectsViewer.setInput(ResourcesPlugin.getWorkspace());

		setControl(composite);
	}

	protected IStructuredContentProvider getContentProvider() {
		return new WorkbenchContentProvider() {
			@Override
