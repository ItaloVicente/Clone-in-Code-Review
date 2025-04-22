
		IWizard wizard = mainPage.getWizard();
		if (wizard instanceof NewWizard) {
			if (WizardsRegistryReader.FULL_EXAMPLES_WIZARD_CATEGORY.equals(((NewWizard) wizard)
					.getCategoryId())) {
				filter.setFilterPrimaryWizards(true);
			}
		}
