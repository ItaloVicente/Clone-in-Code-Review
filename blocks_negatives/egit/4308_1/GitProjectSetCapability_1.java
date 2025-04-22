		for (final Map.Entry<URIish, Map<String, Set<ProjectReference>>> entry : repositories.entrySet()) {
			final URIish gitUrl = entry.getKey();
			final Map<String, Set<ProjectReference>> branches = entry.getValue();

			for (final Map.Entry<String, Set<ProjectReference>> branchEntry : branches.entrySet()) {
				final String branch = branchEntry.getKey();
				final Set<ProjectReference> projects = branchEntry.getValue();

				try {
					final IPath workDir = getWorkingDir(gitUrl, branch,
							branches.keySet());
					if (workDir.toFile().exists()) {
						final Collection<String> projectNames = new LinkedList<String>();
						for (final ProjectReference projectReference : projects)
							projectNames.add(projectReference.projectDir);
						throw new TeamException(NLS.bind(
								CoreText.GitProjectSetCapability_CloneToExistingDirectory,
								new Object[] { workDir, projectNames, gitUrl }));
