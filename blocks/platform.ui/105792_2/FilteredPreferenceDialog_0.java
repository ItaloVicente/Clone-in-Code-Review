	@Override
	protected Control createHelpControl(Composite parent) {
		Control control = super.createHelpControl(parent);
		if (control instanceof ToolBar) {
			ToolBar toolBar = (ToolBar) control;

			ToolItem separator = new ToolItem(toolBar, SWT.SEPARATOR);
			separator.setWidth(0);

			ToolItem importButton = new ToolItem(toolBar, SWT.PUSH);
			Image importImage = JFaceResources.getImage(DLG_IMG_IMPORT);
			importButton.setImage(importImage);
			importButton.setToolTipText(JFaceResources.getString("PreferencesDialog.importButtonLabel")); //$NON-NLS-1$
			importButton.addListener(SWT.Selection, e -> {
				Wizard importWizard = new PreferencesImportWizard();
				WizardDialog wizardDialog = new WizardDialog(parent.getShell(), importWizard);
				wizardDialog.open();
				if (wizardDialog.getReturnCode() == 0) {
					parent.getShell().close();
				}
			});

			separator = new ToolItem(toolBar, SWT.SEPARATOR);
			separator.setWidth(0);

			ToolItem exportButton = new ToolItem(toolBar, SWT.PUSH);
			Image exportImage = JFaceResources.getImage(DLG_IMG_EXPORT);
			exportButton.setImage(exportImage);
			exportButton.setToolTipText(JFaceResources.getString("PreferencesDialog.exportButtonLabel")); //$NON-NLS-1$
			exportButton.addListener(SWT.Selection, e -> {
				Wizard exportWizard = new PreferencesExportWizard();
				WizardDialog wizardDialog = new WizardDialog(parent.getShell(), exportWizard);
				wizardDialog.open();
			});
		} else if (control instanceof Link) {
		}
		return control;
	}

