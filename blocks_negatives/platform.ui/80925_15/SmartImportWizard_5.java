	@Override
	public IWizardPage getNextPage(IWizardPage page) {
		if (page == this.projectRootPage && !this.projectRootPage.isDetectNestedProject()) {
			return null;
		}
		return super.getNextPage(page);
	}

