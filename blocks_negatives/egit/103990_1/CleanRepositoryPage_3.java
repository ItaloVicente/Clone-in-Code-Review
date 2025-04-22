					try {
						command.call();
					} catch (GitAPIException ex) {
						Activator.logError("cannot call clean command!", ex); //$NON-NLS-1$
					}

					try {
						IProject[] projects = ProjectUtil.getProjectsContaining(repository, itemsToClean);
						ProjectUtil.refreshResources(projects,
								subMonitor.newChild(1));
					} catch (CoreException e) {
					}
