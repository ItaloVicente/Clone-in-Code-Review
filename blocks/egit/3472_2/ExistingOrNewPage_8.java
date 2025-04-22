	protected void setRepository(Repository repository) {
		if (repository == this.selectedRepository)
			return;
		this.selectedRepository = repository;
		relPath.setText(""); //$NON-NLS-1$
	}

	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		if (visible)
			updateControls();
	}

	private void fillTreeItemWithGitDirectory(RepositoryMapping m,
			TreeItem treeItem, boolean isAlternative) {
