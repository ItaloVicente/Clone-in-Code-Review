	public void test003_WriteShouldBeEmptyTree() throws IOException {
		final Tree t = new Tree(db);
		final ObjectId emptyId = insertEmptyBlob();
		t.addFile("should-be-empty").setId(emptyId);
		t.accept(new WriteTree(trash, db), TreeEntry.MODIFIED_ONLY);
		assertEquals("7bb943559a305bdd6bdee2cef6e5df2413c3d30a", t.getId()
				.name());

		File o;
		o = new File(new File(new File(db.getDirectory(), "objects"), "7b"),
				"b943559a305bdd6bdee2cef6e5df2413c3d30a");
		assertTrue("Exists " + o, o.isFile());
		assertTrue("Read-only " + o, !o.canWrite());

		o = new File(new File(new File(db.getDirectory(), "objects"), "e6"),
				"9de29bb2d1d6434b8b29ae775ad8c2e48c5391");
		assertTrue("Exists " + o, o.isFile());
		assertTrue("Read-only " + o, !o.canWrite());
	}

