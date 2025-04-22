				final Set<String> allBranches = branches.keySet();

				File repositoryPath = null;
				if (allBranches.size() == 1)
					repositoryPath = findConfiguredRepository(gitUrl);

				if (repositoryPath == null) {
					try {
						IPath workDir = getWorkingDir(gitUrl, branch, branches.keySet());
						repositoryPath = cloneIfNecessary(gitUrl, branch, workDir, projects, monitor);
					} catch (final InterruptedException e) {
						return Collections.emptyList();
