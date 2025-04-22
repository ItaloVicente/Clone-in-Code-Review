		setContents(targetFile, newContents);
		return addAndCommit(targetFile, repoRelativePath, commitMessage);
	}

	private RevCommit addAndCommit(IFile targetFile, String repoRelativePath,
			String commitMessage) throws Exception {
