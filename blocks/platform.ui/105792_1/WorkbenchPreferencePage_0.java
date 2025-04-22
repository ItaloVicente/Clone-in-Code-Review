	protected void createImportExportGroup(Composite composite) {
		Group buttonGroup = new Group(composite, SWT.LEFT);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		buttonGroup.setLayout(layout);
		GridData gd = new GridData();
		gd.horizontalAlignment = GridData.FILL;
		gd.grabExcessHorizontalSpace = true;
		buttonGroup.setLayoutData(gd);
		buttonGroup.setText(WorkbenchMessages.ImportExportPreferences_title);

		int widthHint = convertHorizontalDLUsToPixels(IDialogConstants.BUTTON_WIDTH);

		importPreferencesButton = new Button(buttonGroup, SWT.PUSH);
		importPreferencesButton.setText(WorkbenchMessages.ImportExportPreferences_import);

		Point minImportButtonSize = importPreferencesButton.computeSize(SWT.DEFAULT, SWT.DEFAULT, true);
		GridData importGridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		importGridData.widthHint = Math.max(widthHint, minImportButtonSize.x);
		importPreferencesButton.setLayoutData(importGridData);

		importPreferencesButton.addListener(SWT.Selection, e -> {
			PreferencesImportWizard importWizard = new PreferencesImportWizard();
			WizardDialog wizardDialog = new WizardDialog(composite.getShell(), importWizard);
			wizardDialog.open();
			if (wizardDialog.getReturnCode() == 0) {
				composite.getShell().close();
			}
		});

		exportPreferencesButton = new Button(buttonGroup, SWT.PUSH);
		exportPreferencesButton.setText(WorkbenchMessages.ImportExportPreferences_export);

		Point minExportButtonSize = exportPreferencesButton.computeSize(SWT.DEFAULT, SWT.DEFAULT, true);
		GridData exportGridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		exportGridData.widthHint = Math.max(widthHint, minExportButtonSize.x);
		exportPreferencesButton.setLayoutData(exportGridData);

		exportPreferencesButton.addListener(SWT.Selection, e -> {
			PreferencesExportWizard exportWizard = new PreferencesExportWizard();
			WizardDialog wizardDialog = new WizardDialog(composite.getShell(), exportWizard);
			wizardDialog.open();
		});
	}

