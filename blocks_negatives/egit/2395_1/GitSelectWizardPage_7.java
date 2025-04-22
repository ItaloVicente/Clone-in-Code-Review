		if (importExisting.getSelection())
			return EXISTING_PROJECTS_WIZARD;
		if (newProjectWizard.getSelection())
			return NEW_WIZARD;
		if (generalWizard.getSelection())
			return GENERAL_WIZARD;
		return -1;
