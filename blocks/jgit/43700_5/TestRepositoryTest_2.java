
	@Test
	public void amendRef() throws Exception {
		RevCommit root = tr.commit()
				.add("todelete"
				.create();
		RevCommit orig = tr.commit().parent(root)
				.rm("todelete")
				.add("foo"
				.add("bar"
				.add("dir/baz"
				.create();
		rw.parseBody(orig);
		tr.branch("master").update(orig);
		assertEquals("foo contents"
		assertEquals("bar contents"
		assertEquals("baz contents"

		RevCommit amended = tr.amendRef("master")
				.tick(3)
				.add("bar"
				.create();
		assertEquals(amended
		rw.parseBody(amended);

		assertEquals(1
		assertEquals(root
		assertEquals(orig.getFullMessage()
		assertEquals(orig.getAuthorIdent()

		assertEquals(new PersonIdent(orig.getCommitterIdent()
				new PersonIdent(amended.getCommitterIdent()
		assertTrue(orig.getCommitTime() < amended.getCommitTime());

		assertEquals("foo contents"
		assertEquals("fixed bar contents"
		assertEquals("baz contents"
		assertNull(TreeWalk.forPath(repo
	}

	@Test
	public void amendHead() throws Exception {
		RevCommit root = tr.commit()
				.add("foo"
				.create();
		RevCommit orig = tr.commit().parent(root)
				.message("original message")
				.add("bar"
				.create();
		tr.branch("master").update(orig);
		tr.reset("master");

		RevCommit amended = tr.amendRef("HEAD")
				.add("foo"
				.create();

		Ref head = repo.getRef(Constants.HEAD);
		assertEquals(amended
		assertTrue(head.isSymbolic());
		assertEquals("refs/heads/master"

		rw.parseBody(amended);
		assertEquals("original message"
		assertEquals("fixed foo contents"
		assertEquals("bar contents"
	}

	@Test
	public void amendCommit() throws Exception {
		RevCommit root = tr.commit()
				.add("foo"
				.create();
		RevCommit orig = tr.commit().parent(root)
				.message("original message")
				.add("bar"
				.create();
		RevCommit amended = tr.amend(orig.copy())
				.add("foo"
				.create();

		rw.parseBody(amended);
		assertEquals("original message"
		assertEquals("fixed foo contents"
		assertEquals("bar contents"
	}

	private String blobAsString(AnyObjectId treeish
			throws Exception {
		RevObject obj = tr.get(rw.parseTree(treeish)
		assertSame(RevBlob.class
		ObjectLoader loader = rw.getObjectReader().open(obj);
		return new String(loader.getCachedBytes()
	}
