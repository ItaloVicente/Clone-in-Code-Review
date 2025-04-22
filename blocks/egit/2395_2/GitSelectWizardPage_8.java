				if (importExisting.getSelection())
					wizardSelection = EXISTING_PROJECTS_WIZARD;
				else if (newProjectWizard.getSelection())
					wizardSelection = NEW_WIZARD;
				else if (generalWizard.getSelection())
					wizardSelection = GENERAL_WIZARD;
				else
					wizardSelection = EXISTING_PROJECTS_WIZARD;
