		cloneSource = new RepositorySelectionPage(true, presetURI);
		cloneSource.setHelpContext(HELP_CONTEXT);
		validSource = new SourceBranchPage() {

			@Override
			public void setVisible(boolean visible) {
				if (visible) {
					setSelection(cloneSource.getSelection());
					setCredentials(cloneSource.getCredentials());
				}
				super.setVisible(visible);
			}
		};
