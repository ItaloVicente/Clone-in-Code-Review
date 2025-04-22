					try {
						ProjectUtils.createProjects(projectsImportPage
								.getCheckedProjects(), selectRepoPage
								.getRepository(), projectsImportPage
								.getSelectedWorkingSets(),
								new NullProgressMonitor());
					} catch (OperationCanceledException e) {
						return;
					} catch (CoreException e) {
						Activator.handleError(e.getMessage(), e, true);
					}
