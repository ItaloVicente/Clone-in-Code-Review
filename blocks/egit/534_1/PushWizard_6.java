	@Override
	public IWizardPage getNextPage(IWizardPage page) {
		if (page == getPages()[0]){
			refSpecPage.setSelection(repoPage.getSelection());
		}
		return super.getNextPage(page);
	}

