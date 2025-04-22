				Wizard wiz = new ExternalProjectImportWizard() {

					@Override
					public void addPages() {
						super.addPages();
					}

					@Override
					public boolean performFinish() {

						final Set<IPath> previousLocations = new HashSet<IPath>();
						for (IProject project : ResourcesPlugin.getWorkspace()
								.getRoot().getProjects()) {
							previousLocations.add(project.getLocation());
						}

						boolean success = super.performFinish();
						if (success) {

							IWorkspaceRunnable wsr = new IWorkspaceRunnable() {

								public void run(IProgressMonitor monitor)
										throws CoreException {
									File gitDir = repo.getDirectory();
									File gitWorkDir = repo.getWorkDir();
									Path workPath = new Path(gitWorkDir
											.getAbsolutePath());

									for (IProject prj : ResourcesPlugin
											.getWorkspace().getRoot()
											.getProjects()) {

										if (workPath.isPrefixOf(prj
												.getLocation())) {
											if (previousLocations.contains(prj
													.getLocation())) {
												continue;
											}
											ConnectProviderOperation connectProviderOperation = new ConnectProviderOperation(
													prj, gitDir);
											connectProviderOperation
													.run(new SubProgressMonitor(
															monitor, 20));

										}
									}

								}
							};

							try {
								ResourcesPlugin.getWorkspace().run(
										wsr,
										ResourcesPlugin.getWorkspace()
												.getRoot(),
										IWorkspace.AVOID_UPDATE,
										new NullProgressMonitor());
								scheduleRefresh();
							} catch (CoreException ce) {
								MessageDialog
										.openError(
												getShell(),
												UIText.RepositoriesView_Error_WindowTitle,
												ce.getMessage());
							}

						}
						return success;
					}

				};
