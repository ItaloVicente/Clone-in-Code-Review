
	@Test
	public void resetFromSymref() throws Exception {
		repo.updateRef("HEAD").link("refs/heads/master");
		Ref head = repo.getRef("HEAD");
		RevCommit master = tr.branch("master").commit().create();
		RevCommit branch = tr.branch("branch").commit().create();
		RevCommit detached = tr.commit().create();

		assertTrue(head.isSymbolic());
		assertEquals("refs/heads/master"
		assertEquals(master
		assertEquals(branch

		tr.reset("master");
		head = repo.getRef("HEAD");
		assertEquals(master
		assertTrue(head.isSymbolic());
		assertEquals("refs/heads/master"

		tr.reset("branch");
		head = repo.getRef("HEAD");
		assertEquals(branch
		assertTrue(head.isSymbolic());
		assertEquals("refs/heads/master"
		ObjectId lastHeadBeforeDetach = head.getObjectId().copy();

		tr.reset(detached);
		head = repo.getRef("HEAD");
		assertEquals(detached
		assertFalse(head.isSymbolic());

		tr.reset(detached.name());
		head = repo.getRef("HEAD");
		assertEquals(detached
		assertFalse(head.isSymbolic());

		tr.reset("master");
		head = repo.getRef("HEAD");
		assertEquals(lastHeadBeforeDetach
		assertFalse(head.isSymbolic());
	}

	@Test
	public void resetFromDetachedHead() throws Exception {
		Ref head = repo.getRef("HEAD");
		RevCommit master = tr.branch("master").commit().create();
		RevCommit branch = tr.branch("branch").commit().create();
		RevCommit detached = tr.commit().create();

		assertNull(head);
		assertEquals(master
		assertEquals(branch

		tr.reset("master");
		head = repo.getRef("HEAD");
		assertEquals(master
		assertFalse(head.isSymbolic());

		tr.reset("branch");
		head = repo.getRef("HEAD");
		assertEquals(branch
		assertFalse(head.isSymbolic());

		tr.reset(detached);
		head = repo.getRef("HEAD");
		assertEquals(detached
		assertFalse(head.isSymbolic());

		tr.reset(detached.name());
		head = repo.getRef("HEAD");
		assertEquals(detached
		assertFalse(head.isSymbolic());
	}
