	private void createLink(Composite res) {
		Link showOtherImportWizards = new Link(res, SWT.NONE);
		showOtherImportWizards
				.setText("<A>" + DataTransferMessages.SmartImportWizardPage_showOtherSpecializedImportWizard + "</A>"); //$NON-NLS-1$ //$NON-NLS-2$
		showOtherImportWizards.setLayoutData(new GridData(SWT.END, SWT.END, true, true, 4, 1));
		showOtherImportWizards.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ImportExportWizard importWizard = new ImportExportWizard(ImportExportWizard.IMPORT);
				importWizard.init(PlatformUI.getWorkbench(), new StructuredSelection(selection));
				IDialogSettings workbenchSettings = WorkbenchPlugin.getDefault().getDialogSettings();
				IDialogSettings wizardSettings = workbenchSettings.getSection("ImportExportAction"); //$NON-NLS-1$
				if (wizardSettings == null) {
					wizardSettings = workbenchSettings.addNewSection("ImportExportAction"); //$NON-NLS-1$
				}
				importWizard.setDialogSettings(wizardSettings);
				importWizard.addPages();
				getWizard().getContainer().showPage(importWizard.getPages()[0]);
			}
		});
	}

