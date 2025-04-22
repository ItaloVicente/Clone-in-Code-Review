	@Test(expected = UnmergedPathsException.class)
	public void unmergedPathsShouldCauseException() throws Exception {
		commitFile("file.txt", "master", "base");
		RevCommit side = commitFile("file.txt", "side", "side");
		commitFile("file.txt", "master", "master");
		git.merge().include(side).call();

		git.stashCreate().call();
