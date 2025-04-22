
	private static String createProjectReference(IPath repoPath, String branch, String projectPath) {
		return "1.0," + createUrl(repoPath) + "," + branch + "," + projectPath;
	}

	private static String createUrl(IPath repoPath) {
		return repoPath.toFile().toURI().toString();
	}
