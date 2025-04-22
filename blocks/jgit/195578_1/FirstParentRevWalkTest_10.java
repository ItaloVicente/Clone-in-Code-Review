
		assertEquals(d
		assertEquals(c
		assertEquals(b
		assertNull(rw.next());
	}

	@Test
	public void testWithTopoSortAndTreeFilter_shouldUseFilteredRevCommits()
			throws Exception {
		RevCommit a = commit();
		RevCommit b = commit(tree(file("0"
		RevCommit c = commit(tree(file("0"
		RevCommit d = commit(tree(file("0"

		rw.reset();
		rw.setFirstParent(true);
		rw.sort(RevSort.TOPO
		rw.setTreeFilter(PathFilterGroup.createFromStrings("0"));
		markStart(d);

		RevCommit x = rw.next();
		assertTrue(x instanceof FilteredRevCommit);
		assertEquals(1
		assertEquals(c

		RevCommit y = rw.next();
		assertTrue(y instanceof FilteredRevCommit);
		assertEquals(1
		assertEquals(b

		RevCommit z = rw.next();
		assertTrue(z instanceof FilteredRevCommit);
		assertEquals(0

