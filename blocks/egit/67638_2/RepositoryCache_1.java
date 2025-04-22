		for (final Iterator<Map.Entry<File, Reference<Repository>>> i = repositoryCache
				.entrySet().iterator(); i.hasNext();) {
			Map.Entry<File, Reference<Repository>> entry = i.next();
			File gitDir = entry.getKey();
			if (configuredRepositories.contains(gitDir.getPath())) {
				continue;
			}
			Set<File> outerRepositories = nesting.get(gitDir);
			boolean found = false;
			if (outerRepositories != null) {
				for (File outer : outerRepositories) {
					if (configuredRepositories.contains(outer.getPath())) {
						found = true;
						break;
					}
				}
			}
			if (!found) {
				removeFromCache(i, entry.getValue().get(), gitDir);
			}
		}
	}

	private void removeFromCache(
			Iterator<Map.Entry<File, Reference<Repository>>> iterator,
			Repository repository, File gitDir) {
		iterator.remove();
		nesting.remove(gitDir);
		for (Set<File> others : nesting.values()) {
			others.remove(gitDir);
		}
		if (repository != null) {
			Activator.getDefault().getIndexDiffCache().remove(repository);
		}
	}

	private void computeNesting(Repository repository, File gitDir) {
		Set<File> outer = new HashSet<>();
		java.nio.file.Path gitPath = gitDir.toPath();
		for (Map.Entry<File, Reference<Repository>> entry : repositoryCache
				.entrySet()) {
			File outerGitDir = entry.getKey();
			if (gitPath.startsWith(outerGitDir.toPath())) {
				outer.add(outerGitDir);
			} else {
				Repository outerRepo = entry.getValue().get();
				if (outerRepo != null && !outerRepo.isBare()) {
					File outerWorkDir = new Path(
							outerRepo.getWorkTree().getAbsolutePath()).toFile();
					if (gitPath.startsWith(outerWorkDir.toPath())) {
						outer.add(outerGitDir);
					}
				}
			}
		}
		if (!outer.isEmpty()) {
			nesting.put(gitDir, outer);
		}
		if (repository.isBare()) {
			return;
		}
		java.nio.file.Path myWorkDir = new Path(
				repository.getWorkTree().getAbsolutePath()).toFile().toPath();
		for (Map.Entry<File, Reference<Repository>> entry : repositoryCache
				.entrySet()) {
			if (entry.getValue().get() == null) {
				continue; // Will be pruned next time
			}
			File innerGitDir = entry.getKey();
			java.nio.file.Path innerGitPath = innerGitDir.toPath();
			if (innerGitPath.startsWith(gitPath)
					|| innerGitPath.startsWith(myWorkDir)) {
				Set<File> other = nesting.get(innerGitDir);
				if (other == null) {
					other = new HashSet<File>();
					other.add(gitDir);
					nesting.put(innerGitDir, other);
				} else {
					other.add(gitDir);
				}
