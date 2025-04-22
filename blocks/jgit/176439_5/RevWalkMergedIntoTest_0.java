
	@Test
	public void testGetMergedInto() throws Exception {
		String b = "refs/heads/b";
		String c = "refs/heads/c";
		String d = "refs/heads/d";
		String e = "refs/heads/e";
		final RevCommit i = commit();
		final RevCommit a = commit(i);
		final RevCommit o1 = commit(a);
		final RevCommit o2 = commit(a);
		createBranch(commit(o1)
		createBranch(commit(o1
		createBranch(commit(o2)
		createBranch(commit(commit(i))

		List<String>  modifiedResult = rw.getMergedInto(a
				.stream().map(Ref::getName).collect(Collectors.toList());

		assertTrue(modifiedResult.size() == 3);
		assertTrue(modifiedResult.contains(b));
		assertTrue(modifiedResult.contains(c));
		assertTrue(modifiedResult.contains(d));
	}

	@Test
	public void testIsMergedIntoAny() throws Exception {
		String b = "refs/heads/b";
		String c = "refs/heads/c";
		final RevCommit i = commit();
		final RevCommit a = commit(i);
		createBranch(commit(commit(a))
		createBranch(commit(commit(i))

		assertTrue( rw.isMergedIntoAny(a
	}

	@Test
	public void testIsMergedIntoAll() throws Exception {

		String b = "refs/heads/b";
		String c = "refs/heads/c";
		String d = "refs/heads/c";
		final RevCommit a = commit();
		final RevCommit o1 = commit(a);
		final RevCommit o2 = commit(a);
		createBranch(commit(o1)
		createBranch(commit(o1
		createBranch(commit(o2)

		assertTrue(rw.isMergedIntoAll(a
	}
