	private GitSelectWizardPage importWithDirectoriesPage = new GitSelectWizardPage(){
		public void setVisible(boolean visible) {
			if (visible && (cloneDestination.cloneSettingsChanged())) {
				setCallerRunsCloneOperation(true);
				try {
					performClone(currentSearchResult.getGitRepositoryInfo());
					importWithDirectoriesPage.getControl().getDisplay().asyncExec(new Runnable() {

						public void run() {
							runCloneOperation(getContainer());
							cloneDestination.saveSettingsForClonedRepo();
						}});
				} catch (URISyntaxException e) {
					Activator.error(UIText.GitImportWizard_errorParsingURI, e);
				} catch (NoRepositoryInfoException e) {
					Activator.error(UIText.GitImportWizard_noRepositoryInfo, e);
				} catch (Exception e) {
					Activator.error(e.getMessage(), e);
				}
			}
			super.setVisible(visible);
		}
	};
