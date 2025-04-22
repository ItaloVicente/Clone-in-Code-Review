		super("newWizardSelectionPage", workbench, selection, null, WorkbenchTriggerPoints.NEW_WIZARDS);//$NON-NLS-1$

		setTitle(WorkbenchMessages.NewWizardSelectionPage_description);
		wizardCategories = root;
		primaryWizards = primary;
		this.projectsOnly = projectsOnly;
	}
