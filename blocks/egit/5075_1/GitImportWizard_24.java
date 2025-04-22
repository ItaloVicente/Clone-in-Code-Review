	protected List<CloneSourceProvider> getCloneSourceProviders() {
		List<CloneSourceProvider> cloneSourceProvider = super.getCloneSourceProviders();
		cloneSourceProvider.add(0, CloneSourceProvider.LOCAL);
		return cloneSourceProvider;
	}

	public void init(IWorkbench workbench, IStructuredSelection selection) {
