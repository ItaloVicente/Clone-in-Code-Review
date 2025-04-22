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
			Entry<Repository, Collection<String>> entry = pathsByRepository
					.entrySet().iterator().next();
			Repository repo = entry.getKey();
			List<String> filterPaths = new ArrayList<>(entry.getValue());

			if (monitor.isCanceled())
				throw new InterruptedException();
