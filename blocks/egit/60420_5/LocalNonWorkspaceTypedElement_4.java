	private void fireChanges() {
		fireContentChanged();
		Repository myRepository = repository;
		boolean updated = false;
		if (myRepository != null && !myRepository.isBare()) {
			updated = refreshRepositoryState(myRepository);
		}
		if (!updated) {
			RepositoryMapping mapping = RepositoryMapping.getMapping(path);
			if (mapping != null) {
				mapping.getRepository().fireEvent(new IndexChangedEvent());
			}
		}
	}

	private boolean refreshRepositoryState(@NonNull Repository repo) {
		IPath repositoryRoot = new Path(repo.getWorkTree().getPath());
		IPath relativePath = path.makeRelativeTo(repositoryRoot);
		IndexDiffCacheEntry indexDiffCacheForRepository = org.eclipse.egit.core.Activator
				.getDefault().getIndexDiffCache().getIndexDiffCacheEntry(repo);
		if (indexDiffCacheForRepository != null) {
			indexDiffCacheForRepository.refreshFiles(
					Collections.singleton(relativePath.toString()));
			return true;
		}
		return false;
	}

