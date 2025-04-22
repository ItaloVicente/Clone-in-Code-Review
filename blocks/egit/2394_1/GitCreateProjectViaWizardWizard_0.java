					try {
						ProjectUtils.createProjects(myProjectsImportPage
								.getCheckedProjects(), myRepository,
								myProjectsImportPage.getSelectedWorkingSets(),
								new NullProgressMonitor());
					} catch (OperationCanceledException e) {
						return;
					} catch (CoreException e) {
						Activator.handleError(e.getMessage(), e, true);
					}
