	/**
	 * @param easymportJob2
	 * @param projectRootPage2
	 * @return
	 */
	private static boolean matchesPage(SmartImportJob easymportJob2, SmartImportRootWizardPage projectRootPage2) {
		return easymportJob2.getRoot().getAbsoluteFile().equals(projectRootPage2.getSelectedRoot().getAbsoluteFile())
				&& easymportJob2.isDetectNestedProjects() == projectRootPage2.isDetectNestedProject()
				&& easymportJob2.isConfigureProjects() == projectRootPage2.isConfigureProjects();
