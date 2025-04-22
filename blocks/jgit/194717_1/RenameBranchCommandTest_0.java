
	@Test
	public void renameToConflictingTargetBranch() throws Exception {
		assertNotNull(git.branchCreate().setName("a/b").call());
		assertThrows("should throw InvalidRefNameException"
				InvalidRefNameException.class
				() -> git.branchRename().setNewName("a/b/c").call());
	}
