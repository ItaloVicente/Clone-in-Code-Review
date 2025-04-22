	@Override
	public void setVisible(boolean visible) {
		if (visible) {
			GitPreconfiguredSynchronizeWizardPage prevPage = (GitPreconfiguredSynchronizeWizardPage) getWizard()
					.getPages()[0];
			Repository[] repositories = prevPage.getRepositoriesForCustomeConfiguration();
			treeViewer.setInput(repositories);
		}
		super.setVisible(visible);
	}

