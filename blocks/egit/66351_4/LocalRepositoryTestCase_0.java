		return createProjectAndCommitToRepository(repoName, PROJ1, PROJ2);
	}

	protected File createProjectAndCommitToRepository(String repoName,
			String projectName) throws Exception {
		return createProjectAndCommitToRepository(repoName, projectName, null);
	}

	protected File createProjectAndCommitToRepository(String repoName,
			String project1Name, String project2Name) throws Exception {
