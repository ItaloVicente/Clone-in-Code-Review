
	@Override
	public IWizardPage getNextPage(IWizardPage page) {
		if (repositorySelection.isSingleRepo()) {
			return super.getNextPage(page);
		} else {
			return null;
		}
	}
