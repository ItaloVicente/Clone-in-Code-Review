
	@Test
	public void testGetMergedInto() throws Exception {
		final RevCommit i = commit();
		final RevCommit a = commit(i);
		final RevCommit e = commit(commit(i));

		final RevCommit o1 = commit(a);
		final RevCommit o2 = commit(a);

		final RevCommit b = commit(o1);
		final RevCommit c = commit(o1
		final RevCommit d = commit(o2);

		List<RevCommit> haystacks = new ArrayList<>();
		haystacks.add(b);
		haystacks.add(c);
		haystacks.add(d);
		haystacks.add(e);

		List<RevCommit> result = rw.getMergedInto(a

		assertTrue(result.size() == 3);
		assertTrue(result.contains(b));
		assertTrue(result.contains(c));
		assertTrue(result.contains(d));
	}
