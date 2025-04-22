							IPath location = project.getLocation();
							if (location == null) {
								continue;
							}
							SmartImportRootWizardPage.this.notAlreadyExistingProjects.remove(location.toFile());
							SmartImportRootWizardPage.this.alreadyExistingProjects.add(location.toFile());
