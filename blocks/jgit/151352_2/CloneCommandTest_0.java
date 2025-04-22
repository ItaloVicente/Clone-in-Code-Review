	@Test
	public void testBareCloneRepositoryMirror() throws Exception {
		File directory = createTempDirectory(
				"testCloneRepositoryWithBranch_mirror");
		CloneCommand command = Git.cloneRepository();
		command.setBranch("refs/heads/master");
		command.setDirectory(directory);
		command.setURI(fileUri());
		Git git2 = command.call();
		addRepoToClose(git2.getRepository());
		assertNotNull(git2);
		assertNotNull(git2.getRepository().resolve("tag-for-blob"));
		assertNotNull(git2.getRepository().resolve("tag-initial"));
		assertEquals(git2.getRepository().getFullBranch()
		assertEquals("refs/heads/master
				git2.branchList().setListMode(ListMode.ALL).call()));
		assertNotNull(git2.getRepository().exactRef("refs/meta/foo/bar"));
		RemoteConfig cfg = new RemoteConfig(git2.getRepository().getConfig()
				Constants.DEFAULT_REMOTE_NAME);
		List<RefSpec> specs = cfg.getFetchRefSpecs();
		assertEquals(1
				specs.get(0));
	}

