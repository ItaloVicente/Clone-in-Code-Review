		IDialogSettings settings = getDialogSettings();
		newResourcePage = new NewWizardNewPage(this, wizardCategories, primaryWizards, projectsOnly);
		newResourcePage.setDialogSettings(settings);

		Control control = newResourcePage.createControl(parent);
		getWorkbench().getHelpSystem().setHelp(control, IWorkbenchHelpContextIds.NEW_WIZARD_SELECTION_WIZARD_PAGE);
		setControl(control);
	}

	protected void saveWidgetValues() {
		newResourcePage.saveWidgetValues();
	}

	@Override
