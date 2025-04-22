
	@Test
	public void checkout() throws Exception {
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
		assertTrue(head.isSymbolic());
		assertEquals("refs/heads/master"

		tr.reset("branch");
		head = repo.getRef("HEAD");
		assertEquals(branch
		assertTrue(head.isSymbolic());
		assertEquals("refs/heads/branch"

		tr.reset(detached);
		head = repo.getRef("HEAD");
		assertEquals(detached
		assertFalse(head.isSymbolic());

		tr.reset(detached.name());
		head = repo.getRef("HEAD");
		assertEquals(detached
		assertFalse(head.isSymbolic());
	}
