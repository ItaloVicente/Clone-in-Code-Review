					break;
				case GitSelectWizardPage.GENERAL_WIZARD:
					try {

						final String projectName = createGeneralProjectPage
								.getProjectName();
						final String path = importWithDirectoriesPage.getPath();
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
										desc.setLocation(new Path(path));

										IProject prj = ResourcesPlugin
												.getWorkspace().getRoot()
												.getProject(desc.getName());
										prj.create(desc, monitor);
										prj.open(monitor);
										File repoDir = selectRepoPage.getRepository().getDirectory();
										ConnectProviderOperation cpo = new ConnectProviderOperation(prj, repoDir);
										cpo.execute(new NullProgressMonitor());

										ResourcesPlugin.getWorkspace()
												.getRoot().refreshLocal(
														IResource.DEPTH_ONE,
														monitor);

									}
								});
					} catch (InvocationTargetException e1) {
						Activator.handleError(e1.getMessage(), e1
								.getTargetException(), true);
					} catch (InterruptedException e1) {
						Activator.handleError(e1.getMessage(), e1, true);
