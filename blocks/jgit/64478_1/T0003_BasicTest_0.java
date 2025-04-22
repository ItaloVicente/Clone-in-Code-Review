	@Test
	public void test012_SubtreeExternalSorting() throws IOException {
		final ObjectId emptyBlob = insertEmptyBlob();
		final Tree t = new Tree(db);
		final FileTreeEntry e0 = t.addFile("a-");
		final FileTreeEntry e1 = t.addFile("a-b");
		final FileTreeEntry e2 = t.addFile("a/b");
		final FileTreeEntry e3 = t.addFile("a=");
		final FileTreeEntry e4 = t.addFile("a=b");

		e0.setId(emptyBlob);
		e1.setId(emptyBlob);
		e2.setId(emptyBlob);
		e3.setId(emptyBlob);
		e4.setId(emptyBlob);

		final Tree a = (Tree) t.findTreeMember("a");
		a.setId(insertTree(a));
		assertEquals(ObjectId
				.fromString("b47a8f0a4190f7572e11212769090523e23eb1ea")
				insertTree(t));
	}

