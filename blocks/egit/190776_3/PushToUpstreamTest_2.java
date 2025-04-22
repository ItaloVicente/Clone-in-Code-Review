		assertBranchPushed(branchName, branchName, remoteRepo);
	}

	private void assertBranchPushed(String localName, String remoteName,
			Repository remoteRepo) throws Exception {
		ObjectId pushed = remoteRepo.resolve(remoteName);
		assertNotNull("Expected '" + remoteName
