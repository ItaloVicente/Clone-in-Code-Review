		if (visible) {
			if (branchPage.isSourceRepoEmpty()) {
				initialBranch.setEnabled(false);
				showImportWizard.setSelection(false);
				showImportWizard.setEnabled(false);
			}
