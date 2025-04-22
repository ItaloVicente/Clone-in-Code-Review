					Map<IProject, File> projectsToConnect = new HashMap<>();
					for (ProjectRecord projectRecord : projectsToCreate) {
						if (actMonitor.isCanceled())
							throw new OperationCanceledException();
						actMonitor.subTask(projectRecord.getProjectLabel());
						IProject project = createExistingProject(projectRecord,
								open, new SubProgressMonitor(actMonitor, 1));
						if (project == null)
							continue;
