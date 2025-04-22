	@Test
	public void testFullNegotiation() throws Exception {
		TestProtocol<User> proto = registerDefault();
		URIish uri = proto.register(new User("user")

		for (int i = 0; i < 10 * HAVES_PER_ROUND; i++) {
			local.branch("local-branch-" + i).commit().create();
		}
		remote.tick(11 * HAVES_PER_ROUND);
		RevCommit master = remote.branch("master").commit()
				.add("readme.txt"

		try (Git git = new Git(local.getRepository())) {
			assertNull(local.getRepository().exactRef("refs/heads/master"));
			git.fetch().setRemote(uri.toString()).setRefSpecs(MASTER).call();
			assertEquals(master
					.exactRef("refs/heads/master").getObjectId());
			assertEquals(10 * HAVES_PER_ROUND
		}
	}

	@Test
	public void testMinimalNegotiation() throws Exception {
		TestProtocol<User> proto = registerDefault();
		URIish uri = proto.register(new User("user")

		for (int i = 0; i < 10 * HAVES_PER_ROUND; i++) {
			local.branch("local-branch-" + i).commit().create();
		}
		remote.tick(11 * HAVES_PER_ROUND);
		RevCommit master = remote.branch("master").commit()
				.add("readme.txt"

		TestProtocol.setFetchConfig(new FetchConfig(true
		try (Git git = new Git(local.getRepository())) {
			assertNull(local.getRepository().exactRef("refs/heads/master"));
			git.fetch().setRemote(uri.toString()).setRefSpecs(MASTER).call();
			assertEquals(master
					.exactRef("refs/heads/master").getObjectId());
			assertTrue(havesCount <= 2 * HAVES_PER_ROUND);

			master = remote.branch("master").commit().parent(master).create();
			local.tick(2 * HAVES_PER_ROUND);
			for (int i = 0; i < 5 * HAVES_PER_ROUND; i++) {
				local.branch("local-" + i).commit().create();
			}
			git.fetch().setRemote(uri.toString()).setRefSpecs(MASTER).call();
			assertEquals(master
					.exactRef("refs/heads/master").getObjectId());
			assertEquals(6 * HAVES_PER_ROUND
		}
	}

