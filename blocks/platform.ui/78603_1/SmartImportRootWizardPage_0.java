							IPath projectLocation = project.getLocation();
							if (projectLocation != null) {
								File fileLocationFile = projectLocation.toFile();
								SmartImportRootWizardPage.this.notAlreadyExistingProjects.remove(fileLocationFile);
								SmartImportRootWizardPage.this.alreadyExistingProjects.add(fileLocationFile);
							}
