		final Tree t = new Tree(db);
		final FileTreeEntry f = t.addFile("i-am-a-file");
		writeTrashFile(f.getName(), "and this is the data in me\n");
		t.accept(new WriteTree(trash, db), TreeEntry.MODIFIED_ONLY);
		assertEquals(ObjectId.fromString("00b1f73724f493096d1ffa0b0f1f1482dbb8c936"),
				t.getTreeId());
