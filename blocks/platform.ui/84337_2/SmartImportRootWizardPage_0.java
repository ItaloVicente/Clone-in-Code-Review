	private void createWorkingSetsGroup(Composite parent) {
		Composite workingSetComposite = new Composite(parent, SWT.NONE);
		GridData layoutData = new GridData(SWT.FILL, SWT.FILL, true, false, 4, 1);
		layoutData.verticalIndent = 20;
		workingSetComposite.setLayoutData(layoutData);
		workingSetComposite.setLayout(new GridLayout(1, false));
		WorkingSetRegistry registry = WorkbenchPlugin.getDefault().getWorkingSetRegistry();
		String[] workingSetIds = Arrays.stream(registry.getNewPageWorkingSetDescriptors())
				.map(WorkingSetDescriptor::getId).toArray(String[]::new);
		IStructuredSelection wsSel = null;
		if (this.workingSets != null) {
			wsSel = new StructuredSelection(this.workingSets.toArray());
		}
		this.workingSetsGroup = new WorkingSetGroup(workingSetComposite, wsSel, workingSetIds);
	}

