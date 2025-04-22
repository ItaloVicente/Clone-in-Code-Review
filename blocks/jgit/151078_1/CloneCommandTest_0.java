		try (SubmoduleWalk walk = SubmoduleWalk
				.forIndex(git2.getRepository())) {
			assertTrue(walk.next());
			try (Repository clonedSub1 = walk.getRepository()) {
				assertNotNull(clonedSub1);
				assertEquals(new File(git2.getRepository().getWorkTree()
						walk.getPath())
				assertEquals(
						new File(new File(git2.getRepository().getDirectory()
								"modules")
						clonedSub1.getDirectory());
				status = new SubmoduleStatusCommand(clonedSub1);
				statuses = status.call();
			}
			assertFalse(walk.next());
