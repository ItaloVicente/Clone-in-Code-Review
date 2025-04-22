			assertTrue(havesCount <= 2 * HAVES_PER_ROUND);

			master = remote.branch("master").commit().parent(master).create();
			local.tick(2 * HAVES_PER_ROUND);
			for (int i = 0; i < 5 * HAVES_PER_ROUND; i++) {
				local.branch("local-" + i).commit().create();
			}
			git.fetch().setRemote(uri.toString()).setRefSpecs(MASTER).call();
			assertEquals(master, local.getRepository()
					.exactRef("refs/heads/master").getObjectId());
			assertEquals(6 * HAVES_PER_ROUND, havesCount);
