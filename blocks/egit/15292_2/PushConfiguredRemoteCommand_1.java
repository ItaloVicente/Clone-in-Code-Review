	public boolean isEnabled() {
		RepositoryTreeNode<?> node = getSelectedNodes().get(0);
		try {
			return getRemoteConfig(node) != null;
		} catch (ExecutionException e) {
			return false;
