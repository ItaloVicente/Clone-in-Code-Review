	private static String createProjectReference(IPath repoPath,
			String protocol, String user, String branch, String projectPath)
			throws Exception {
		return "1.0," + createUrl(repoPath, protocol, user) + "," + branch
				+ "," + projectPath;
	}

