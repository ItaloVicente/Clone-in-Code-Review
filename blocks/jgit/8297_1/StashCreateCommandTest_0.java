
	@Test(expected = UnmergedPathsException.class)
	public void unmergedPathsShouldCauseException() throws Exception {
		commitFile("file.txt"
		RevCommit side = commitFile("file.txt"
		commitFile("file.txt"
		git.merge().include(side).call();

		git.stashCreate().call();
	}
