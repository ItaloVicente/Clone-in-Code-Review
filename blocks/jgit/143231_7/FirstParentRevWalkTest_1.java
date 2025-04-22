	@Test(expected = IllegalStateException.class)
	public void testMarkStartBeforeSetFirstParent() throws Exception {
		RevCommit a = commit();

		rw.reset();
		markStart(a);
		rw.setFirstParent(true);
	}

