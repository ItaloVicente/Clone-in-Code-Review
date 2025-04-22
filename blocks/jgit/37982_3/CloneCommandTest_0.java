
		SubmoduleWalk walk = SubmoduleWalk.forIndex(git2.getRepository());
		assertTrue(walk.next());
		Repository clonedSub1 = walk.getRepository();
		addRepoToClose(clonedSub1);
		assertNotNull(clonedSub1);
		assertEquals(
				new File(git2.getRepository().getWorkTree()
				clonedSub1.getWorkTree());
		assertEquals(new File(new File(git2.getRepository().getDirectory()
				"modules")
		walk.release();
