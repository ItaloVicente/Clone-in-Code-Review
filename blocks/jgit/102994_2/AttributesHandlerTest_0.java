	@Test
	public void testRelativePaths() throws Exception {
		setupRepo("sub/ global"
		walk = beginWalk();
		assertIteration(F
		assertIteration(D
		assertIteration(F
		assertIteration(F
		endWalk();
		writeTrashFile("sub/sub/b.txt"
		walk = beginWalk();
		assertIteration(F
		assertIteration(D
		assertIteration(F
		assertIteration(F
		assertIteration(D
		assertIteration(F
				attrs("init foo subsub2 subsub top top_sub global"));
		endWalk();
	}

