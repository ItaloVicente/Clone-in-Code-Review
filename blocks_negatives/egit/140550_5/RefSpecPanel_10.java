		addRefSpecTableListener(new SelectionChangeListener() {
			@Override
			public void selectionChanged() {
				updateAddPredefinedButton(addConfiguredButton,
						predefinedConfigured);
				updateAddPredefinedButton(addBranchesButton, predefinedBranches);
				updateAddPredefinedButton(addTagsButton, Transport.REFSPEC_TAGS);
			}
