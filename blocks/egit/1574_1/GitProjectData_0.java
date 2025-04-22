	private void deletePropertyFilesAndUncache() {
		deletePropertyFiles(getProject());
		uncache(getProject());
	}

	private static void deletePropertyFiles(IProject project) {
		final File dir = propertyFile(project).getParentFile();
