	@Test
	public void testFullNegotiation() throws Exception {
		TestProtocol<User> proto = registerDefault();
		URIish uri = proto.register(new User("user")

		for (int i = 0; i < 32 * 10; i++) {
			local.branch("local-branch-" + i).commit().create();
		}
		RevCommit master = remote.branch("master").commit()
				.add("readme.txt"

		try (Git git = new Git(local.getRepository())) {
			assertNull(local.getRepository().exactRef("refs/heads/master"));
			git.fetch().setRemote(uri.toString()).setRefSpecs(MASTER).call();
			assertEquals(master
					.exactRef("refs/heads/master").getObjectId());
		}
	}

	@Test
	public void testMinimalNegotiation() throws Exception {
		TestProtocol<User> proto = registerDefault();
		URIish uri = proto.register(new User("user")

		for (int i = 0; i < 32 * 10; i++) {
			local.branch("local-branch-" + i).commit().create();
		}
		RevCommit master = remote.branch("master").commit()
				.add("readme.txt"

		TestProtocol.setFetchConfig(new FetchConfig(true
		try (Git git = new Git(local.getRepository())) {
			assertNull(local.getRepository().exactRef("refs/heads/master"));
			git.fetch().setRemote(uri.toString()).setRefSpecs(MASTER).call();
			assertEquals(master
					.exactRef("refs/heads/master").getObjectId());
		}
	}

