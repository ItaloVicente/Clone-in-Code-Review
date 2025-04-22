		PlatformUI.getWorkbench().getHelpSystem().setHelp(getControl(),
				IIDEHelpContextIds.PROJECT_REFERENCE_PROPERTY_PAGE);

		Composite composite = new Composite(parent, SWT.NONE);

		initialize();

		createDescriptionLabel(composite);

		listViewer = CheckboxTableViewer.newCheckList(composite, SWT.TOP
				| SWT.BORDER);

		if(!project.isOpen())
			listViewer.getControl().setEnabled(false);

		listViewer.setLabelProvider(WorkbenchLabelProvider
				.getDecoratingWorkbenchLabelProvider());
		listViewer.setContentProvider(getContentProvider(project));
		listViewer.setComparator(new ViewerComparator());
		listViewer.setInput(project.getWorkspace());
		try {
			listViewer.setCheckedElements(project.getDescription()
					.getReferencedProjects());
		} catch (CoreException e) {
		}

		listViewer.addCheckStateListener(event -> modified = true);

		applyDialogFont(composite);

		GridLayoutFactory.fillDefaults().generateLayout(composite);

		return composite;
	}

	protected IStructuredContentProvider getContentProvider(
			final IProject project) {
		return new WorkbenchContentProvider() {
			@Override
