			monitor.beginTask(
					UIText.GitMergeEditorInput_CheckingResourcesTaskName,
					IProgressMonitor.UNKNOWN);

			Map<Repository, Collection<String>> pathsByRepository = ResourceUtil
					.splitPathsByRepository(Arrays.asList(locations));
			if (pathsByRepository.size() != 1) {
				throw new InvocationTargetException(
						new IllegalStateException(
								UIText.RepositoryAction_multiRepoSelection));
			}
			Repository repo = pathsByRepository.keySet().iterator().next();
			List<String> filterPaths = new ArrayList<String>(
					pathsByRepository.get(repo));
