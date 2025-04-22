
	@Test
	public void testEmptyTreeCorruption() throws Exception {
		ObjectId bId = ObjectId
				.fromString("abbbfafe3129f85747aba7bfac992af77134c607");
		final RevTree tree_root, tree_A, tree_AB;
		final RevCommit b;
		{
			Tree root = new Tree(db);
			Tree A = root.addTree("A");
			FileTreeEntry B = root.addFile("B");
			B.setId(bId);

			Tree A_A = A.addTree("A");
			Tree A_B = A.addTree("B");

			final ObjectInserter inserter = db.newObjectInserter();
			try {
				A_A.setId(inserter.insert(Constants.OBJ_TREE, A_A.format()));
				A_B.setId(inserter.insert(Constants.OBJ_TREE, A_B.format()));
				A.setId(inserter.insert(Constants.OBJ_TREE, A.format()));
				root.setId(inserter.insert(Constants.OBJ_TREE, root.format()));
				inserter.flush();
			} finally {
				inserter.release();
			}

			tree_root = rw.parseTree(root.getId());
			tree_A = rw.parseTree(A.getId());
			tree_AB = rw.parseTree(A_A.getId());
			assertSame(tree_AB, rw.parseTree(A_B.getId()));
			b = commit(rw.parseTree(root.getId()));
		}

		markStart(b);

		assertCommit(b, objw.next());
		assertNull(objw.next());

		assertSame(tree_root, objw.nextObject());
		assertSame(tree_A, objw.nextObject());
		assertSame(tree_AB, objw.nextObject());
		assertSame(rw.lookupBlob(bId), objw.nextObject());
		assertNull(objw.nextObject());
	}
