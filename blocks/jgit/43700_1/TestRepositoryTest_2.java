
	@Test
	public void amend() throws Exception {
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

		RevCommit amended = tr.amend("master")
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

	private String blobAsString(AnyObjectId treeish
			throws Exception {
		RevObject obj = tr.get(rw.parseTree(treeish)
		assertSame(RevBlob.class
		ObjectLoader loader = rw.getObjectReader().open(obj);
		return new String(loader.getCachedBytes()
	}
