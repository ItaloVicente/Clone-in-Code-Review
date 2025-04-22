		tree.addCheckStateListener(event -> {
			if (isExistingProject((File) event.getElement()) || isExistingProjectName((File) event.getElement())) {
				tree.setChecked(event.getElement(), false);
				return;
			}
			if (event.getChecked()) {
				SmartImportRootWizardPage.this.directoriesToImport.add((File) event.getElement());
			} else {
				SmartImportRootWizardPage.this.directoriesToImport.remove(event.getElement());
