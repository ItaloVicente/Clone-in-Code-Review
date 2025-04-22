		DialogCheck.assertDialog(dialog);
	}

	public void testNewProjectPage2() {
		BasicNewProjectResourceWizard wizard = new BasicNewProjectResourceWizard();
		wizard.init(PlatformUI.getWorkbench(), null);
		wizard.setNeedsProgressMonitor(true);

		WizardNewProjectReferencePage page = new WizardNewProjectReferencePage(
				"basicReferenceProjectPage");//$NON-NLS-1$
		page.setTitle(ResourceMessages.NewProject_referenceTitle);
		page.setDescription(ResourceMessages.NewProject_referenceDescription);
		page.setWizard(wizard);

		WizardDialog dialog = new WizardDialog(getShell(), wizard);
		dialog.create();
		dialog.getShell().setSize(
				Math.max(SIZING_WIZARD_WIDTH_2, dialog.getShell().getSize().x),
				SIZING_WIZARD_HEIGHT_2);
		dialog.getShell().setText("CreateFileAction_title");
		dialog.showPage(page);
