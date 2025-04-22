	private void createWorkbenchGroup(Composite parent) {
		final Group g = createGroup(parent, UIText.CloneDestinationPage_workspaceImport);
		showImportWizard = new Button(g, SWT.CHECK);
		showImportWizard.setSelection(true);
		showImportWizard.setText(UIText.CloneDestinationPage_importProjectsAfterClone);
		showImportWizard.setLayoutData(createFieldGridData());
		showImportWizard.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				checkPage();
			}
		});
	}

