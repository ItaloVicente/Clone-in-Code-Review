		{
			Tree root = new Tree(db);
			Tree A = root.addTree("A");
			FileTreeEntry B = root.addFile("B");
			B.setId(bId);

			Tree A_A = A.addTree("A");
			Tree A_B = A.addTree("B");

			try (final ObjectInserter inserter = db.newObjectInserter()) {
				A_A.setId(inserter.insert(Constants.OBJ_TREE, A_A.format()));
				A_B.setId(inserter.insert(Constants.OBJ_TREE, A_B.format()));
				A.setId(inserter.insert(Constants.OBJ_TREE, A.format()));
				root.setId(inserter.insert(Constants.OBJ_TREE, root.format()));
				inserter.flush();
			}

			tree_root = rw.parseTree(root.getId());
			tree_A = rw.parseTree(A.getId());
			tree_AB = rw.parseTree(A_A.getId());
			assertSame(tree_AB, rw.parseTree(A_B.getId()));
			b = commit(rw.parseTree(root.getId()));
