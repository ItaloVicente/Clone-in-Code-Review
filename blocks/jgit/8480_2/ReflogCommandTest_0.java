
	@Test
	public void testAmendReflog() throws Exception {
		RevCommit commit2a = git.commit().setAmend(true)
				.setMessage("Deleted file").call();
		Collection<ReflogEntry> reflog = git.reflog().call();
		assertNotNull(reflog);
		assertEquals(4
		ReflogEntry[] reflogs = reflog.toArray(new ReflogEntry[reflog.size()]);
		assertEquals(reflogs[3].getComment()
		assertEquals(reflogs[3].getNewId()
		assertEquals(reflogs[3].getOldId()
		assertEquals(reflogs[2].getComment()
				"checkout: moving from master to b1");
		assertEquals(reflogs[2].getNewId()
		assertEquals(reflogs[2].getOldId()
		assertEquals(reflogs[1].getComment()
		assertEquals(reflogs[1].getNewId()
		assertEquals(reflogs[1].getOldId()
		assertEquals(reflogs[0].getComment()
		assertEquals(reflogs[0].getNewId()
		assertEquals(reflogs[0].getOldId()
	}
