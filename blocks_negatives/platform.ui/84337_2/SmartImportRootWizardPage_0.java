		Group workingSetsGroup = new Group(res, SWT.NONE);
		GridData layoutData = new GridData(SWT.FILL, SWT.FILL, true, false, 4, 1);
		layoutData.verticalIndent = 20;
		workingSetsGroup.setLayoutData(layoutData);
		workingSetsGroup.setLayout(new GridLayout(1, false));
		workingSetsGroup.setText(DataTransferMessages.SmartImportWizardPage_workingSets);
		workingSetsBlock = new WorkingSetConfigurationBlock(getDialogSettings(),
		if (this.workingSets != null) {
			workingSetsBlock.setWorkingSets(this.workingSets.toArray(new IWorkingSet[this.workingSets.size()]));
		}
		workingSetsBlock.createContent(workingSetsGroup);
