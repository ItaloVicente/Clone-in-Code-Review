			SubmoduleWalk generator = SubmoduleWalk.forIndex(db);
			assertTrue(generator.next());
			assertEquals(path
			assertEquals(commit
			assertEquals(uri
			assertEquals(path
			assertEquals(uri
			Repository subModRepo = generator.getRepository();
			assertNotNull(subModRepo);
			assertEquals(subCommit
			subModRepo.close();
