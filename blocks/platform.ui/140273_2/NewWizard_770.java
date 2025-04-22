		}

		mainPage = new NewWizardSelectionPage(workbench, selection, root, primary, projectsOnly);
		addPage(mainPage);
	}

	public String getCategoryId() {
		return categoryId;
	}

	private IWizardCategory getChildWithID(IWizardCategory parent, String id) {
