
	@Override
	public void pageChanged(PageChangedEvent event) {
		SelectImportRootWizardPage selectRootPage = (SelectImportRootWizardPage) this.easymportWizard.getPages()[0];
		if (event.getSelectedPage() == selectRootPage) {
			Repository existingRepo = selectRepoPage.getRepository();
			if (existingRepo != null) {
				selectRootPage.setInitialSelectedDirectory(existingRepo.getWorkTree());
			} else if (needToCloneRepository()) {
				selectRootPage.setInitialSelectedDirectory(doClone());
			}
		}
	}

	@Override
	public void setContainer(IWizardContainer container) {
		if (container instanceof IPageChangeProvider) {
			((IPageChangeProvider) container).addPageChangedListener(this);
		}
		super.setContainer(container);
	}

	@Override
	public void dispose() {
		if (getContainer() instanceof IPageChangeProvider) {
			((IPageChangeProvider) getCloneSourceProviders())
					.removePageChangedListener(this);
		}
		super.dispose();
	}
