	@Test
	public void checkout() throws Exception {
		Ref head = repo.getRef("HEAD");
		RevCommit master = tr.branch("master").commit().create();
		RevCommit branch = tr.branch("branch").commit().create();
		RevCommit detached = tr.commit().create();

		assertNull(head);
		assertEquals(master
		assertEquals(branch

		tr.checkout("master");
		head = repo.getRef("HEAD");
		assertEquals(master
		assertTrue(head.isSymbolic());
		assertEquals("refs/heads/master"

		tr.checkout("branch");
		head = repo.getRef("HEAD");
		assertEquals(branch
		assertTrue(head.isSymbolic());
		assertEquals("refs/heads/branch"

		tr.checkout(detached);
		head = repo.getRef("HEAD");
		assertEquals(detached
		assertFalse(head.isSymbolic());

		tr.checkout(detached.name());
		head = repo.getRef("HEAD");
		assertEquals(detached
		assertFalse(head.isSymbolic());
	}

