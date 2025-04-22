
	public void testEmptyTreeCorruption() throws Exception {
		ObjectId bId = ObjectId
				.fromString("abbbfafe3129f85747aba7bfac992af77134c607");
		final RevTree tree_root
		final RevCommit b;
		{
			Tree root = new Tree(db);
			Tree A = root.addTree("A");
			FileTreeEntry B = root.addFile("B");
			B.setId(bId);

			Tree A_A = A.addTree("A");
			Tree A_B = A.addTree("B");

			final ObjectWriter ow = new ObjectWriter(db);
			A_A.setId(ow.writeTree(A_A));
			A_B.setId(ow.writeTree(A_B));
			A.setId(ow.writeTree(A));
			root.setId(ow.writeTree(root));

			tree_root = rw.parseTree(root.getId());
			tree_A = rw.parseTree(A.getId());
			tree_AB = rw.parseTree(A_A.getId());
			assertSame(tree_AB
			b = commit(rw.parseTree(root.getId()));
		}

		markStart(b);

		assertCommit(b
		assertNull(objw.next());

		assertSame(tree_root
		assertSame(tree_A
		assertSame(tree_AB
		assertSame(rw.lookupBlob(bId)
		assertNull(objw.nextObject());
	}
