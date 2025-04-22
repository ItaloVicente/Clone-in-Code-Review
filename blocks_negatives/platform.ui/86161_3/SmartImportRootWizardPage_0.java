
					SmartImportRootWizardPage.this.notAlreadyExistingProjects = new HashSet<>(
							potentialProjects.keySet());
					SmartImportRootWizardPage.this.alreadyExistingProjects = new HashSet<>();
					for (IProject project : ResourcesPlugin.getWorkspace().getRoot().getProjects()) {
						IPath location = project.getLocation();
						if (location == null) {
							continue;
						}
						SmartImportRootWizardPage.this.notAlreadyExistingProjects.remove(location.toFile());
						SmartImportRootWizardPage.this.alreadyExistingProjects.add(location.toFile());
					}
