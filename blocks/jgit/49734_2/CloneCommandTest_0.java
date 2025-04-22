		try (SubmoduleWalk walk = SubmoduleWalk.forIndex(git.getRepository())) {
			assertTrue(walk.next());
			Repository subRepo = walk.getRepository();
			addRepoToClose(subRepo);
			assertNotNull(subRepo);
			assertEquals(
					new File(git.getRepository().getWorkTree()
					subRepo.getWorkTree());
			assertEquals(new File(new File(git.getRepository().getDirectory()
					"modules")
		}
