					}
					actMonitor.subTask(projectRecord.getProjectLabel());
					IProject project = createExistingProject(projectRecord,
							open, progress.newChild(1));
					if (project == null) {
						continue;
					}
