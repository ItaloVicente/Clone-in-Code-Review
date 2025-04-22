	private GitSelectWizardPage importWithDirectoriesPage = new GitSelectWizardPage(){
		public void setVisible(boolean visible) {
			if (visible && (cloneDestination.cloneSettingsChanged())) {
				setCallerRunsCloneOperation(true);
				try {
					performClone(new URIish(currentSearchResult.getGitRepositoryInfo().getCloneUri()), getCredentials());
					importWithDirectoriesPage.getControl().getDisplay().asyncExec(new Runnable() {
