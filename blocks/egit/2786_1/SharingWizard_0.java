						PlatformUI.getWorkbench().getDisplay().syncExec(
								new Runnable() {
									public void run() {
										Set<File> filesToAdd = new HashSet<File>();
										for (Entry<IProject, File> entry : existingPage
												.getProjects(true).entrySet()) {
											filesToAdd.add(entry.getValue());
										}
										for (File file : filesToAdd)
											Activator.getDefault()
													.getRepositoryUtil()
													.addConfiguredRepository(
															file);
									}
								});
