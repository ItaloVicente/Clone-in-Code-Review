				for (TreeItem item : tree.getTree().getItems()) {
					File dir = (File) item.getData();
					if (isExistingProject(dir)) {
						tree.setChecked(dir, false);
					} else {
						tree.setChecked(dir, true);
						SmartImportRootWizardPage.this.directoriesToImport.add(dir);
					}
				}
