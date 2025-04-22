							IPath projectLocation = project.getLocation();
							if (projectLocation != null) {
								File projectLocationFile = projectLocation.toFile();
								SmartImportRootWizardPage.this.notAlreadyExistingProjects.remove(projectLocationFile);
								SmartImportRootWizardPage.this.alreadyExistingProjects.add(projectLocationFile);
							}
