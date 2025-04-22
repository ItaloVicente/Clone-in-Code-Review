
	@Test
	public void testWithTopoSortAndTreeFilter() throws Exception {
		RevCommit a = commit();
		RevCommit b = commit(tree(file("0"
		RevCommit c = commit(tree(file("0"
		RevCommit d = commit(tree(file("0"

		rw.reset();
		rw.setFirstParent(true);
		rw.sort(RevSort.TOPO
		rw.setTreeFilter(PathFilterGroup.createFromStrings("0"));
		markStart(d);
		assertCommit(d
		assertCommit(c
		assertCommit(b
		assertNull(rw.next());
	}
