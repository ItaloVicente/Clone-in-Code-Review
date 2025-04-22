	@Test
	void testMarkStartBeforeSetFirstParent() throws Exception {
		assertThrows(IllegalStateException.class
			RevCommit a = commit();

			rw.reset();
			markStart(a);
			rw.setFirstParent(true);
		});
