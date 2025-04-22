	@Override
	protected Control createHelpControl(Composite parent) {
		Control control = super.createHelpControl(parent);
		if (control instanceof ToolBar) {
			ToolBar toolBar = (ToolBar) control;

			new ToolItem(toolBar, SWT.SEPARATOR).setWidth(0);

			ToolItem importButton = new ToolItem(toolBar, SWT.PUSH);
			importButton.setImage(importImage);
			importButton.setToolTipText(WorkbenchMessages.Preference_importTooltip);
			importButton.addListener(SWT.Selection, e -> openImportWizard(parent));

			new ToolItem(toolBar, SWT.SEPARATOR).setWidth(0);

			ToolItem exportButton = new ToolItem(toolBar, SWT.PUSH);
			exportButton.setImage(exportImage);
			exportButton.setToolTipText(WorkbenchMessages.Preference_exportTooltip);
			exportButton.addListener(SWT.Selection, e -> openExportWizard(parent));
		} else if (control instanceof Link) {
			Composite linkParent = ((Link) control).getParent();
			Link importLink = new Link(linkParent, SWT.WRAP | SWT.NO_FOCUS);
			((GridLayout) parent.getLayout()).numColumns++;
			importLink.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_CENTER));
			importLink.setText(" <a>" + WorkbenchMessages.Preference_import + "</a>"); //$NON-NLS-1$ //$NON-NLS-2$
			importLink.addListener(SWT.Selection, e -> openImportWizard(parent));

			Link exportLink = new Link(linkParent, SWT.WRAP | SWT.NO_FOCUS);
			((GridLayout) parent.getLayout()).numColumns++;
			exportLink.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_CENTER));
			exportLink.setText(" <a>" + WorkbenchMessages.Preference_export + "</a>"); //$NON-NLS-1$ //$NON-NLS-2$
			exportLink.addListener(SWT.Selection, e -> openExportWizard(parent));
		}
		return control;
	}

	private void openImportWizard(Composite parent) {
		Wizard importWizard = new PreferencesImportWizard();
		WizardDialog wizardDialog = new WizardDialog(parent.getShell(), importWizard);
		wizardDialog.open();
		if (wizardDialog.getReturnCode() == 0) {
			parent.getShell().close();
		}
	}

	private void openExportWizard(Composite parent) {
		Wizard exportWizard = new PreferencesExportWizard();
		WizardDialog wizardDialog = new WizardDialog(parent.getShell(), exportWizard);
		wizardDialog.open();
	}

