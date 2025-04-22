		RevCommit update = updateSubmoduleRevision();
		FetchResult result = fetch(FetchRecurseSubmodulesMode.ON_DEMAND);

		assertTrue(result.submoduleResults().containsKey("sub"));
		FetchResult subResult = result.submoduleResults().get("sub");

		assertTrue(subResult.submoduleResults().isEmpty());
		assertSubmoduleFetchHeads(commit1

		assertEquals(update
				git2.getRepository().resolve(Constants.FETCH_HEAD));
	}

	@Test
	public void shouldNotFetchSubmodulesWhenOnDemandAndRevisionNotChanged()
			throws Exception {
		FetchResult result = fetch(FetchRecurseSubmodulesMode.ON_DEMAND);
		assertTrue(result.submoduleResults().isEmpty());
		assertSubmoduleFetchHeads(submodule1Head
	}

	@Test
	public void shouldNotFetchSubmodulesWhenSubmoduleConfigurationSetToNo()
			throws Exception {
		StoredConfig config = git2.getRepository().getConfig();
		config.setEnum(ConfigConstants.CONFIG_SUBMODULE_SECTION
				ConfigConstants.CONFIG_KEY_FETCH_RECURSE_SUBMODULES
				FetchRecurseSubmodulesMode.NO);
		config.save();
		updateSubmoduleRevision();
		FetchResult result = fetch(null);
		assertTrue(result.submoduleResults().isEmpty());
		assertSubmoduleFetchHeads(submodule1Head
	}

	@Test
	public void shouldFetchSubmodulesWhenSubmoduleConfigurationSetToYes()
			throws Exception {
		StoredConfig config = git2.getRepository().getConfig();
		config.setEnum(ConfigConstants.CONFIG_SUBMODULE_SECTION
				ConfigConstants.CONFIG_KEY_FETCH_RECURSE_SUBMODULES
				FetchRecurseSubmodulesMode.YES);
		config.save();
		FetchResult result = fetch(null);
		assertTrue(result.submoduleResults().containsKey("sub"));
		FetchResult subResult = result.submoduleResults().get("sub");
		assertTrue(subResult.submoduleResults().containsKey("sub"));
		assertSubmoduleFetchHeads(commit1
	}

	@Test
	public void shouldNotFetchSubmodulesWhenFetchConfigurationSetToNo()
			throws Exception {
		StoredConfig config = git2.getRepository().getConfig();
		config.setEnum(ConfigConstants.CONFIG_FETCH_SECTION
				ConfigConstants.CONFIG_KEY_RECURSE_SUBMODULES
				FetchRecurseSubmodulesMode.NO);
		config.save();
		updateSubmoduleRevision();
		FetchResult result = fetch(null);
		assertTrue(result.submoduleResults().isEmpty());
		assertSubmoduleFetchHeads(submodule1Head
	}

	@Test
	public void shouldFetchSubmodulesWhenFetchConfigurationSetToYes()
			throws Exception {
		StoredConfig config = git2.getRepository().getConfig();
		config.setEnum(ConfigConstants.CONFIG_FETCH_SECTION
				ConfigConstants.CONFIG_KEY_RECURSE_SUBMODULES
				FetchRecurseSubmodulesMode.YES);
		config.save();
		FetchResult result = fetch(null);
		assertTrue(result.submoduleResults().containsKey("sub"));
		FetchResult subResult = result.submoduleResults().get("sub");
		assertTrue(subResult.submoduleResults().containsKey("sub"));
		assertSubmoduleFetchHeads(commit1
	}

	private RevCommit updateSubmoduleRevision() throws Exception {
