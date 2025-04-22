	@Test
	public void amendHead() throws Exception {
		RevCommit root = tr.commit()
				.add("foo"
				.create();
		RevCommit orig = tr.commit().parent(root)
				.add("bar"
				.create();
		tr.branch("master").update(orig);
		tr.checkout("master");

		RevCommit amended = tr.amend("HEAD")
				.add("foo"
				.create();

		Ref head = repo.getRef(Constants.HEAD);
		assertEquals(amended
		assertTrue(head.isSymbolic());
		assertEquals("refs/heads/master"

		assertEquals("fixed foo contents"
		assertEquals("bar contents"
	}

