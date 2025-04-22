	@Test
	public void makeBundle_containsObjectInGcRestPack() throws Exception {
		RevCommit commit0 = git.commit().message("0").create();
		RevCommit commit1 = git.commit().message("1").parent(commit0).create();
		git.update("master"

		RevCommit commit2 = git.commit().message("0").create();

		GarbageCollectCommand gc = Git.wrap(repo).gc();
		gc.call();

		byte[] bundle = makeBundle();
		try (Repository newRepo = new InMemoryRepository(
				new DfsRepositoryDescription("copy"))) {
			fetchFromBundle(newRepo
			Ref ref = newRepo.exactRef("refs/heads/master");
			assertNotNull(ref);
			assertEquals(commit1.toObjectId()

			assertTrue(newRepo.getObjectDatabase().has(commit2));
		}
	}

