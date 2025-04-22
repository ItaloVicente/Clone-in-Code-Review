	@Test
	public void testCloneRepositoryOnlyOneTag() throws Exception {
		File directory = createTempDirectory("testCloneRepositoryWithBranch");
		CloneCommand command = Git.cloneRepository();
		command.setBranch("tag-initial");
		command.setBranchesToClone(
				Collections.singletonList("refs/tags/tag-initial"));
		command.setDirectory(directory);
		command.setURI(fileUri());
		Git git2 = command.call();
		addRepoToClose(git2.getRepository());
		assertNotNull(git2);
		assertNull(git2.getRepository().resolve("tag-for-blob"));
		assertNull(git2.getRepository().resolve("refs/heads/master"));
		assertNotNull(git2.getRepository().resolve("tag-initial"));
		ObjectId taggedCommit = db.resolve("tag-initial^{commit}");
		assertEquals(taggedCommit.name()
		RemoteConfig cfg = new RemoteConfig(git2.getRepository().getConfig()
				Constants.DEFAULT_REMOTE_NAME);
		List<RefSpec> specs = cfg.getFetchRefSpecs();
		assertEquals(1
		assertEquals(
				new RefSpec("+refs/tags/tag-initial:refs/tags/tag-initial")
				specs.get(0));
	}

