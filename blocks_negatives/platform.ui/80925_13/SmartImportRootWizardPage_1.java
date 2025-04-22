		try {
			if (sourceIsValid()) {
				Point initialSelection = rootDirectoryText.getSelection();
				getContainer().run(true, true, new IRunnableWithProgress() {
					@Override
					public void run(IProgressMonitor monitor) {
						SmartImportRootWizardPage.this.potentialProjects = getWizard().getImportJob()
								.getImportProposals(monitor);
						if (!potentialProjects.containsKey(getWizard().getImportJob().getRoot())) {
							potentialProjects.put(getWizard().getImportJob().getRoot(), Collections.emptyList());
						}
