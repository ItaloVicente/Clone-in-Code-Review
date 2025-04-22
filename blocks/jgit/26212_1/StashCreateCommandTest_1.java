		untrackedFile = writeTrashFile("untracked.txt"
	}

	private void validateStashedCommit(final RevCommit commit)
			throws IOException {
		validateCoreStashedCommit(commit);
		assertEquals(2
	}

	private void validateStashedCommitWithAdditionalParent(final RevCommit commit)
			throws IOException {
		validateCoreStashedCommit(commit);
		assertEquals(3
