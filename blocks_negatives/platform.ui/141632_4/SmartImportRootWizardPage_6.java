		Runnable workingSetsRetriever = new Runnable() {
			@Override
			public void run() {
				for (IWorkingSet workingSet : SmartImportRootWizardPage.this.workingSetsGroup
						.getSelectedWorkingSets()) {
					SmartImportRootWizardPage.this.workingSets.add(workingSet);
				}
			}
