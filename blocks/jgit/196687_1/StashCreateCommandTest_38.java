	@Test
	void unmergedPathsShouldCauseException() throws Exception {
		assertThrows(UnmergedPathsException.class
			commitFile("file.txt"
			RevCommit side = commitFile("file.txt"
			commitFile("file.txt"
			git.merge().include(side).call();

			git.stashCreate().call();
		});
