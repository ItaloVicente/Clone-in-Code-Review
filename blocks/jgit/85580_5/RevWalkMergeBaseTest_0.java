
	@Test
	public void testInconsistentCommitTimes() throws Exception {

		final RevCommit a = commit(2);
		final RevCommit b = commit(-1
		final RevCommit c = commit(2
		final RevCommit d = commit(1

		rw.setRevFilter(RevFilter.MERGE_BASE);
		markStart(d);
		markStart(c);
		assertCommit(b
		assertNull(rw.next());
	}

}
