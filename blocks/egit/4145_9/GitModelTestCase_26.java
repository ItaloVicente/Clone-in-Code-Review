	protected Commit getCommit(File repoFile, String rev) throws Exception {
		Repository repo = lookupRepository(repoFile);
		ObjectId revId = repo.resolve(rev);

		Commit commit = mock(Commit.class);
		Mockito.when(commit.getId()).thenReturn(
				AbbreviatedObjectId.fromObjectId(revId));

		return commit;
