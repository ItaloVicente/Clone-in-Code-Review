		monitor.beginTask(UIText.CommitActionHandler_calculatingChanges,
				repositories.size() * 1000);
		for (Map.Entry<Repository, HashSet<IProject>> entry : repositories
				.entrySet()) {
			Repository repository = entry.getKey();
			EclipseGitProgressTransformer jgitMonitor = new EclipseGitProgressTransformer(monitor);
			HashSet<IProject> projects = entry.getValue();
			CountingVisitor counter = new CountingVisitor();
			for (IProject p : projects) {
				try {
					p.accept(counter);
				} catch (CoreException e) {
				}
			}
			IndexDiff indexDiff = new IndexDiff(repository, Constants.HEAD,
					IteratorService.createInitialIterator(repository));
			indexDiff.diff(jgitMonitor, counter.count, 0, NLS.bind(
					UIText.CommitActionHandler_repository, repository
							.getDirectory().getPath()));
			indexDiffs.put(repository, indexDiff);

			for (IProject project : projects) {
				includeList(project, indexDiff.getAdded(), indexChanges);
				includeList(project, indexDiff.getChanged(), indexChanges);
				includeList(project, indexDiff.getRemoved(), indexChanges);
				includeList(project, indexDiff.getMissing(), notIndexed);
				includeList(project, indexDiff.getModified(), notIndexed);
				includeList(project, indexDiff.getUntracked(), notTracked);
