
	@Test
	public void testMergeIntoAnnotatedTag() throws Exception {
		String c = "refs/heads/c";
		String v1 = "refs/tags/v1";
		final RevCommit a = commit();
		final RevCommit b = commit(a);
		createBranch(commit(b)
		createBranch(tag("v1"

		assertTrue(rw.isMergedIntoAll(a
	}
