	@Test
	public void testShouldNotSearchPacksAgainTheSecondTime() throws Exception {
		FileRepository bareRepository = newTestRepositoryWithOnePackfile();

		ObjectDirectory dir = bareRepository.getObjectDatabase();

		assertTrue(dir.searchPacksAgain(dir.packList.get()));

		Thread.sleep(3000L);

		assertFalse(dir.searchPacksAgain(dir.packList.get()));
	}

	private FileRepository newTestRepositoryWithOnePackfile() throws Exception {
		FileRepository repository = createBareRepository();
		TestRepository<FileRepository> testRepository = new TestRepository(repository);
		testRepository.commit();
		testRepository.packAndPrune();

		FileBasedConfig repoConfig = repository.getConfig();
		repoConfig.setBoolean(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_KEY_TRUSTFOLDERSTAT
		repoConfig.save();

		return repository;
	}

