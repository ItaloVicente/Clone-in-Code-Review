					if (repositoryAlreadyExistsForUrl(repositoryPath, gitUrl)) {
						shouldClone = false;
					} else {
						final Collection<String> projectNames = new LinkedList<String>();
						for (final ProjectReference projectReference : projects)
							projectNames.add(projectReference.getProjectDir());
						throw new TeamException(
								NLS.bind(
										CoreText.GitProjectSetCapability_CloneToExistingDirectory,
										new Object[] { workDir, projectNames,
												gitUrl }));
					}
