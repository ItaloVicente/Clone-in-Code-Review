	@Test(expected = IllegalStateException.class)
	public void testMergeBaseWithFirstParentNotAllowed() throws Exception {
		RevCommit a = commit();

		rw.reset();
		rw.setFirstParent(true);
		rw.setRevFilter(RevFilter.MERGE_BASE);
		markStart(a);
		assertNull(rw.next());
