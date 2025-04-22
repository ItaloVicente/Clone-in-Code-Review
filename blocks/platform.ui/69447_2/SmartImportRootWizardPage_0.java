
		createInputSelectionOptions(res);

		createConfigurationOptions(res);

		Composite proposalParent = new Composite(res, SWT.NONE);
		proposalParent.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 4, 1));
		proposalParent.setLayout(new FillLayout());
		createProposalsGroup(proposalParent);

		Group workingSetsGroup = new Group(res, SWT.NONE);
		GridData layoutData = new GridData(SWT.FILL, SWT.FILL, true, false, 4, 1);
		layoutData.verticalIndent = 20;
		workingSetsGroup.setLayoutData(layoutData);
		workingSetsGroup.setLayout(new GridLayout(1, false));
		workingSetsGroup.setText(DataTransferMessages.SmartImportWizardPage_workingSets);
		workingSetsBlock = new WorkingSetConfigurationBlock(getDialogSettings(),
				"org.eclipse.ui.resourceWorkingSetPage"); //$NON-NLS-1$
		if (this.workingSets != null) {
			workingSetsBlock.setWorkingSets(this.workingSets.toArray(new IWorkingSet[this.workingSets.size()]));
		}
		workingSetsBlock.createContent(workingSetsGroup);

		if (this.selection != null) {
			rootDirectoryText.setText(this.selection.getAbsolutePath());
			validatePage();
		}

		setControl(res);
	}

	private void createInputSelectionOptions(Composite res) {
