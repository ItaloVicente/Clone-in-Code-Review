	@Test
	public void testBranchReflog() throws Exception {
		Collection<ReflogEntry> reflog = git.reflog()
				.setRef(Constants.R_HEADS + "b1").call();
		assertNotNull(reflog);
		assertEquals(2
		ReflogEntry[] reflogs = reflog.toArray(new ReflogEntry[reflog.size()]);
		assertEquals(reflogs[0].getComment()
		assertEquals(reflogs[0].getNewId()
		assertEquals(reflogs[0].getOldId()
		assertEquals(reflogs[1].getNewId()
		assertEquals(reflogs[1].getOldId()
	}
