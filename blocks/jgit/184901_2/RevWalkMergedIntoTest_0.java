
	@Test
	public void testMergeIntoAnnotatedTag() throws Exception {
		String c = "refs/heads/c";
		String v1 = "refs/tags/v1";
		final RevCommit a = commit();
		final RevCommit b = commit(a);
		createBranch(commit(b)
		createBranch(tag("v1"
		for (Ref f : getRefs()) {
			RevObject object = rw.parseAny(f.getObjectId());
			System.out.println(object);
			System.out.println(rw.peel(object));
		}

		assertTrue(rw.isMergedIntoAll(a
	}
