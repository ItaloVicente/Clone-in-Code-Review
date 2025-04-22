	private static boolean matchesPage(SmartImportJob job, SmartImportRootWizardPage page) {
		File jobRoot = job.getRoot().getAbsoluteFile();
		File pageRoot = page.getSelectedRoot().getAbsoluteFile();
		boolean sameSource = jobRoot.equals(pageRoot)
				|| (isValidArchive(pageRoot) && getExpandDirectory(pageRoot).getAbsoluteFile().equals(jobRoot));
		return sameSource && job.isDetectNestedProjects() == page.isDetectNestedProject()
				&& job.isConfigureProjects() == page.isConfigureProjects();
