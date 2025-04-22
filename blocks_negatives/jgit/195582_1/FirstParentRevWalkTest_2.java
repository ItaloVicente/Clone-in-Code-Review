
		assertEquals(d, rw.next());
		assertEquals(c, rw.next());
		assertEquals(b, rw.next());
		assertNull(rw.next());
	}

	@Test
	public void testWithTopoSortAndTreeFilter_shouldUseFilteredRevCommits()
			throws Exception {
		RevCommit a = commit();
		RevCommit b = commit(tree(file("0", blob("b"))), a);
		RevCommit c = commit(tree(file("0", blob("c"))), b, a);
		RevCommit d = commit(tree(file("0", blob("d"))), c);

		rw.reset();
		rw.setFirstParent(true);
		rw.sort(RevSort.TOPO, true);
		rw.setTreeFilter(PathFilterGroup.createFromStrings("0"));
		markStart(d);

		RevCommit x = rw.next();
		assertTrue(x instanceof FilteredRevCommit);
		assertEquals(1, x.getParentCount());
		assertEquals(c, x.getParent(0));

		RevCommit y = rw.next();
		assertTrue(y instanceof FilteredRevCommit);
		assertEquals(1, y.getParentCount());
		assertEquals(b, y.getParent(0));

		RevCommit z = rw.next();
		assertTrue(z instanceof FilteredRevCommit);
		assertEquals(0, z.getParentCount());

