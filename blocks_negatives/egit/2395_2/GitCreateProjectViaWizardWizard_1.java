					break;
				case GitSelectWizardPage.GENERAL_WIZARD:
					try {
						final String projectName = myCreateGeneralProjectPage
								.getProjectName();
						final boolean defaultLocation = myCreateGeneralProjectPage
								.isDefaultLocation();
						getContainer().run(true, false,
								new WorkspaceModifyOperation() {

									@Override
									protected void execute(
											IProgressMonitor monitor)
											throws CoreException,
											InvocationTargetException,
											InterruptedException {

										final IProjectDescription desc = ResourcesPlugin
												.getWorkspace()
												.newProjectDescription(
														projectName);
										if (!defaultLocation)
											desc
													.setLocation(new Path(
															myGitDir));

										IProject prj = ResourcesPlugin
												.getWorkspace().getRoot()
												.getProject(desc.getName());
										prj.create(desc, monitor);
										prj.open(monitor);

										ResourcesPlugin.getWorkspace()
												.getRoot().refreshLocal(
														IResource.DEPTH_ONE,
														monitor);

										File repoDir = myRepository.getDirectory();
										ConnectProviderOperation cpo = new ConnectProviderOperation(prj, repoDir);
										cpo.execute(new NullProgressMonitor());


									}
								});
					} catch (InvocationTargetException e1) {
						Activator.handleError(e1.getMessage(), e1
								.getTargetException(), true);
					} catch (InterruptedException e1) {
						Activator.handleError(e1.getMessage(), e1, true);
