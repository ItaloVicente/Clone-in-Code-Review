	private boolean haveSubmoduleChanges(@NonNull Repository repository)
			throws IOException {
		boolean hasChanges = false;
		try (SubmoduleWalk walk = SubmoduleWalk.forIndex(repository)) {
			while (!hasChanges && walk.next()) {
				Repository submodule = walk.getRepository();
				if (submodule != null) {
					Repository cached = org.eclipse.egit.core.Activator
							.getDefault().getRepositoryCache().lookupRepository(
									submodule.getDirectory().getAbsoluteFile());
					hasChanges = cached != null
							&& RepositoryUtil.hasChanges(cached);
					submodule.close();
				}
			}
