	@Test
	public void testListAllBranchesShouldNotDie() throws Exception {
		Git git = setUpRepoWithRemote();
		git.branchList().setListMode(ListMode.ALL).call();
	}

