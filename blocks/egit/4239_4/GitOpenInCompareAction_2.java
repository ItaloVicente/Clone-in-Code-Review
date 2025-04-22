	private ITypedElement getHeadFileElement(GitModelBlob blob) {
		Repository repo = blob.getRepository();
		String gitPath = stripWorkDir(repo.getWorkTree(), blob.getLocation().toFile());

		return CompareUtils.getFileRevisionTypedElement(gitPath, blob.getBaseCommit(), repo);
	}

