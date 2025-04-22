	/**
	 *
	 */
	public void importProjects() {

		Display.getDefault().syncExec(new Runnable() {

			public void run() {

				switch (mySelectionPage.getWizardSelection()) {
				case GitSelectWizardPage.EXISTING_PROJECTS_WIZARD:
					try {
						ProjectUtils.createProjects(myProjectsImportPage
								.getCheckedProjects(), myRepository,
								myProjectsImportPage.getSelectedWorkingSets(),
								new NullProgressMonitor());
					} catch (OperationCanceledException e) {
						return;
					} catch (CoreException e) {
						Activator.handleError(e.getMessage(), e, true);
					}
					break;
				case GitSelectWizardPage.NEW_WIZARD:
