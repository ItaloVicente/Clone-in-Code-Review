		monitor.beginTask(UIText.GitMergeEditorInput_CheckingResourcesTaskName,
				IProgressMonitor.UNKNOWN);

		final Map<Repository, Collection<String>> pathsByRepository = ResourceUtil
				.splitPathsByRepository(Arrays.asList(locations));
		if (pathsByRepository.size() != 1)
			throw new InvocationTargetException(new IllegalStateException(
					UIText.RepositoryAction_multiRepoSelection));
		final Repository repository = pathsByRepository.keySet().iterator()
				.next();
		final List<String> filterPaths = new ArrayList<String>(
				pathsByRepository.get(repository));

		checkCanceled(monitor);

