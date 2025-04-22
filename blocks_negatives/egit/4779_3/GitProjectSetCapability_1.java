					final Map<URIish, Map<String, Set<ProjectReference>>> repositories =
						new LinkedHashMap<URIish, Map<String, Set<ProjectReference>>>();
					for (final String reference : referenceStrings) {
						try {
							final ProjectReference projectReference = new ProjectReference(
									reference);
							Map<String, Set<ProjectReference>> repositoryBranches = repositories
									.get(projectReference.getRepository());
							if (repositoryBranches == null) {
								repositoryBranches = new HashMap<String, Set<ProjectReference>>();
								repositories.put(projectReference.getRepository(),
										repositoryBranches);
							}
							Set<ProjectReference> projectReferences = repositoryBranches.get(projectReference.getBranch());
							if (projectReferences == null) {
								projectReferences = new LinkedHashSet<ProjectReference>();
								repositoryBranches.put(projectReference.getBranch(), projectReferences);
							}

							projectReferences.add(projectReference);
						} catch (final IllegalArgumentException e) {
							throw new TeamException(reference, e);
						} catch (final URISyntaxException e) {
							throw new TeamException(reference, e);
						}
					}
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
										projectNames.add(projectReference.getProjectDir());
									throw new TeamException(NLS.bind(
											CoreText.GitProjectSetCapability_CloneToExistingDirectory,
											new Object[] { workDir, projectNames, gitUrl }));
								}

								int timeout = 60;
								String refName = Constants.R_HEADS + branch;
								final CloneOperation cloneOperation = new CloneOperation(
										gitUrl, true, null, workDir.toFile(), refName,
										Constants.DEFAULT_REMOTE_NAME, timeout);
								cloneOperation.run(wsOpMonitor);

								final File repositoryPath = workDir.append(Constants.DOT_GIT_EXT).toFile();

								Activator.getDefault().getRepositoryUtil().addConfiguredRepository(repositoryPath);

								final IWorkspace workspace = ResourcesPlugin.getWorkspace();
								final IWorkspaceRoot root = workspace.getRoot();
								for (final ProjectReference projectToImport : projects) {
									final IPath projectDir = workDir
											.append(projectToImport.getProjectDir());
									final IProjectDescription projectDescription = workspace
											.loadProjectDescription(projectDir
													.append(IProjectDescription.DESCRIPTION_FILE_NAME));
									final IProject project = root
											.getProject(projectDescription.getName());
									project.create(projectDescription, wsOpMonitor);
									importedProjects.add(project);

									project.open(wsOpMonitor);
									final ConnectProviderOperation connectProviderOperation = new ConnectProviderOperation(
											project, repositoryPath);
									connectProviderOperation.execute(wsOpMonitor);
								}
							} catch (final InvocationTargetException e) {
								throwTeamException(e);
							} catch (final CoreException e) {
								throw TeamException.asTeamException(e);
							} catch (final InterruptedException e) {
								importedProjects.clear();
							}
						}
					}

