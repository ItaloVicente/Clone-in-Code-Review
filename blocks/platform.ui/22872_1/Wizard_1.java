	public void addPage(IWizardPage page) {
		pages.add(page);
		page.setWizard(this);
	}

	@Override
