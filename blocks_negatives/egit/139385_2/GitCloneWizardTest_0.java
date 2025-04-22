		Repository repository = FileRepositoryBuilder.create(new File(destRepo,
				Constants.DOT_GIT));
		assertNotNull(repository.resolve("src/" + SampleTestRepository.FIX));
		assertNull(repository.resolve("src/master"));
		assertEquals(repository.resolve("stable"), repository
				.resolve("src/stable"));
		assertNotNull(repository.resolve(Constants.R_TAGS + SampleTestRepository.v2_0_name).name());
		assertTrue(repository.getRefDatabase().getRefsByPrefix(RefDatabase.ALL)
				.size() >= 4);
		bot.button("Cancel").click();
