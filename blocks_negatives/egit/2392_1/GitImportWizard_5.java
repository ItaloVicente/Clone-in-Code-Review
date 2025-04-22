
					if (actionSelection != GitSelectWizardPage.ACTION_DIALOG_SHARE) {
						importProjects();
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
									prj, repoDir);
							try {
								connectProviderOperation.execute(monitor);
							} catch (CoreException e) {
								throw new InvocationTargetException(e);
							}
						}

					}

