	@Test
	public void cherryPick() throws Exception {
		repo.updateRef("HEAD").link("refs/heads/master");
		RevCommit head = tr.branch("master").commit()
				.add("foo"
				.create();
		rw.parseBody(head);
		RevCommit toPick = tr.commit()
				.author(new PersonIdent("Cherrypick Author"
						tr.getClock()
				.author(new PersonIdent("Cherrypick Committer"
						tr.getClock()
				.message("message to cherry-pick")
				.add("bar"
				.create();
		RevCommit result = tr.cherryPick(toPick);
		rw.parseBody(result);

		Ref headRef = tr.getRepository().getRef("HEAD");
		assertEquals(result
		assertTrue(headRef.isSymbolic());
		assertEquals("refs/heads/master"

		assertEquals(1
		assertEquals(head
		assertEquals(toPick.getAuthorIdent()

		assertEquals(new PersonIdent(head.getCommitterIdent()
				new PersonIdent(result.getCommitterIdent()
		assertTrue(toPick.getCommitTime() < result.getCommitTime());

		assertEquals("message to cherry-pick"
		assertEquals("foo contents\n"
		assertEquals("bar contents\n"
	}

	@Test
	public void cherryPickWithContentMerge() throws Exception {
		RevCommit base = tr.branch("HEAD").commit()
				.add("foo"
				.create();
		tr.branch("HEAD").commit()
				.add("foo"
				.create();
		RevCommit toPick = tr.commit()
				.message("message to cherry-pick")
				.parent(base)
				.add("foo"
				.create();
		RevCommit result = tr.cherryPick(toPick);
		rw.parseBody(result);

		assertEquals("message to cherry-pick"
		assertEquals("changed foo contents\n\nlast line\n"
				blobAsString(result
	}

	@Test
	public void cherryPickWithIdenticalContents() throws Exception {
		RevCommit base = tr.branch("HEAD").commit().add("foo"
				.create();
		RevCommit head = tr.branch("HEAD").commit()
				.parent(base)
				.add("bar"
				.create();
		RevCommit toPick = tr.commit()
				.parent(base)
				.message("message to cherry-pick")
				.add("bar"
				.create();
		assertNotEquals(head
		assertNull(tr.cherryPick(toPick));
		assertEquals(head
	}

