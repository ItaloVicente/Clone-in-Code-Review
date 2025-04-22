					}

					if (actionSelection != GitSelectWizardPage.ACTION_NO_SHARE) {

						IProject[] projects;
						if (projectsToShare == null)
							projects = getAddedProjects();
						else
							projects = projectsToShare;
						for (IProject prj : projects) {
							if (monitor.isCanceled())
								throw new InterruptedException();
							ConnectProviderOperation connectProviderOperation = new ConnectProviderOperation(
									prj, myRepository.getDirectory());
							try {
								connectProviderOperation.execute(monitor);
							} catch (CoreException e) {
								throw new InvocationTargetException(e);
							}
						}

					}

