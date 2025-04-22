		FetchResult result = fetch(FetchRecurseSubmodulesMode.ON_DEMAND);

		assertTrue(result.submoduleResults().containsKey("sub"));
		FetchResult subResult = result.submoduleResults().get("sub");

		assertTrue(subResult.submoduleResults().isEmpty());
		assertSubmoduleFetchHeads(commit1, submodule2Head);

		assertEquals(update,
				git2.getRepository().resolve(Constants.FETCH_HEAD));
	}

	@Test
	public void shouldNotFetchSubmodulesWhenOnDemandAndRevisionNotChanged()
			throws Exception {
		FetchResult result = fetch(FetchRecurseSubmodulesMode.ON_DEMAND);
		assertTrue(result.submoduleResults().isEmpty());
		assertSubmoduleFetchHeads(submodule1Head, submodule2Head);
