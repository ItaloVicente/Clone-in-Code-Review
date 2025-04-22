	protected Object prepareInput(IProgressMonitor monitor)
			throws InvocationTargetException, InterruptedException {
		try (RevWalk rw = new RevWalk(repository)) {
			monitor.beginTask(
					UIText.GitCompareEditorInput_CompareResourcesTaskName,
					IProgressMonitor.UNKNOWN);

			for (IResource resource : resources) {
				if (resource == null) {
					continue;
				}
				RepositoryMapping map = RepositoryMapping.getMapping(resource);
				if (map == null) {
					throw new InvocationTargetException(
							new IllegalStateException(
									UIText.GitCompareEditorInput_ResourcesInDifferentReposMessagge));
				}
				if (repository != null && repository != map.getRepository())
					throw new InvocationTargetException(
							new IllegalStateException(
									UIText.GitCompareEditorInput_ResourcesInDifferentReposMessagge));
				String repoRelativePath = map.getRepoRelativePath(resource);
				filterPathStrings.add(repoRelativePath);
				DiffNode node = new DiffNode(Differencer.NO_CHANGE) {
					@Override
					public Image getImage() {
						return FOLDER_IMAGE;
					}
				};
				diffRoots.put(new Path(map.getRepoRelativePath(resource)),
						node);
				repository = map.getRepository();
			}

			if (repository == null)
				throw new InvocationTargetException(new IllegalStateException(
						UIText.GitCompareEditorInput_ResourcesInDifferentReposMessagge));

			if (monitor.isCanceled())
				throw new InterruptedException();

			final RevCommit baseCommit;
			try {
				try {
					baseCommit = rw
							.parseCommit(repository.resolve(baseVersion));
				} catch (IOException e) {
					throw new InvocationTargetException(e);
				}
