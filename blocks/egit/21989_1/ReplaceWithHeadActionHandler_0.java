	@Override
	public boolean isEnabled() {
		return super.isEnabled() && isAtLeastOneTracked();
	}

	private boolean isAtLeastOneTracked() {
		Repository[] repositories = getRepositories();
		IResource[] selectedResources = getSelectedResources();
		List<IResource> notUntracked = new ArrayList<IResource>(
				Arrays.asList(selectedResources));
		for (Repository repository : repositories) {
			IndexDiffData indexDiff = org.eclipse.egit.core.Activator
					.getDefault().getIndexDiffCache()
					.getIndexDiffCacheEntry(repository).getIndexDiff();
			if (indexDiff != null) {
				for (IResource selectedResource : selectedResources) {
					String repoRelativePath = makeRepoRelative(repository,
							selectedResource);
					if (containsPrefix(indexDiff.getUntracked(),
							repoRelativePath)) {
						notUntracked.remove(selectedResource);
					}
				}
			}
		}
		return !notUntracked.isEmpty();
	}

	private static String makeRepoRelative(Repository repo, IResource res) {
		return Repository.stripWorkDir(repo.getWorkTree(), res.getLocation()
				.toFile());
	}

	private boolean containsPrefix(Set<String> collection, String prefix) {
		for (String path : collection)
			if (path.startsWith(prefix))
				return true;
		return false;
	}

