		final File repositoryPath = workDir.append(Constants.DOT_GIT_EXT).toFile();

		if (workDir.toFile().exists()) {
			if (repositoryAlreadyExistsForUrl(repositoryPath, gitUrl))
				return repositoryPath;
			else {
				final Collection<String> projectNames = new LinkedList<String>();
				for (final ProjectReference projectReference : projects)
					projectNames.add(projectReference.getProjectDir());
				throw new TeamException(
						NLS.bind(CoreText.GitProjectSetCapability_CloneToExistingDirectory,
								new Object[] { workDir, projectNames, gitUrl }));
			}
		} else {
			try {
				int timeout = 60;
				String refName = Constants.R_HEADS + branch;
				final CloneOperation cloneOperation = new CloneOperation(
						gitUrl, true, null, workDir.toFile(), refName,
						Constants.DEFAULT_REMOTE_NAME, timeout);
				cloneOperation.run(monitor);

				return repositoryPath;
			} catch (final InvocationTargetException e) {
				throw getTeamException(e);
