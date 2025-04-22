					for (final Map.Entry<URIish, Map<String, Set<ProjectReference>>> entry : repositories.entrySet()) {
						final URIish gitUrl = entry.getKey();
						final Map<String, Set<ProjectReference>> branches = entry.getValue();

						for (final Map.Entry<String, Set<ProjectReference>> branchEntry : branches.entrySet()) {
							final String branch = branchEntry.getKey();
							final Set<ProjectReference> projects = branchEntry.getValue();

							try {
								final IPath workDir = getWorkingDir(gitUrl, branch, branches.keySet());
								if (workDir.toFile().exists()) {
									final Collection<String> projectNames = new LinkedList<String>();
									for (final ProjectReference projectReference : projects)
										projectNames.add(projectReference.projectDir);
									throw new TeamException(NLS.bind(CoreText.GitProjectSetCapability_CloneToExistingDirectory, new Object[] { workDir, projectNames, gitUrl }));
								}

								int timeout = 60;
								String refName = Constants.R_HEADS + branch;
								final CloneOperation cloneOperation = new CloneOperation(gitUrl, true, null, workDir.toFile(), refName, Constants.DEFAULT_REMOTE_NAME, timeout);
								cloneOperation.run(monitor);

								final File repositoryPath = workDir.append(Constants.DOT_GIT_EXT).toFile();

								Activator.getDefault().getRepositoryUtil().addConfiguredRepository(repositoryPath);

								final IWorkspace workspace = ResourcesPlugin.getWorkspace();
								final IWorkspaceRoot root = workspace.getRoot();
								for (final ProjectReference projectToImport : projects) {
									final IPath projectDir = workDir.append(projectToImport.projectDir);
									final IProjectDescription projectDescription = workspace.loadProjectDescription(projectDir.append(IProjectDescription.DESCRIPTION_FILE_NAME));
									final IProject project = root.getProject(projectDescription.getName());
									project.create(projectDescription, monitor);
									importedProjects.add(project);

									project.open(monitor);
									final ConnectProviderOperation connectProviderOperation = new ConnectProviderOperation(project, repositoryPath);
									connectProviderOperation.execute(monitor);
								}
							} catch (final InvocationTargetException e) {
								throwTeamException(e);
							} catch (final CoreException e) {
								throw TeamException.asTeamException(e);
							} catch (final InterruptedException e) {
							}
						}
