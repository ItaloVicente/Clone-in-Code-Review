	private void fireChanges() {
		fireContentChanged();
		RepositoryMapping mapping = RepositoryMapping.getMapping(path);
		if (mapping != null) {
			mapping.getRepository().fireEvent(new IndexChangedEvent());
		} else {
			Repository myRepository = repository;
			if (myRepository != null && !myRepository.isBare()) {
				refreshRepositoryState(myRepository);
			}
		}
	}

	private void refreshRepositoryState(@NonNull Repository repo) {
		IPath repositoryRoot = new Path(repo.getWorkTree().getPath());
		IPath relativePath = path.makeRelativeTo(repositoryRoot);
		IndexDiffCacheEntry indexDiffCacheForRepository = org.eclipse.egit.core.Activator
				.getDefault().getIndexDiffCache().getIndexDiffCacheEntry(repo);
		if (indexDiffCacheForRepository != null) {
			indexDiffCacheForRepository.refreshFiles(
					Collections.singleton(relativePath.toString()));
		}
	}

