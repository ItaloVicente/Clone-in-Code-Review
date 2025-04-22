	@Test
	void testMergeBaseWithFirstParentNotAllowed() throws Exception {
		assertThrows(IllegalStateException.class
			RevCommit a = commit();

			rw.reset();
			rw.setFirstParent(true);
			rw.setRevFilter(RevFilter.MERGE_BASE);
			markStart(a);
			assertNull(rw.next());
		});
