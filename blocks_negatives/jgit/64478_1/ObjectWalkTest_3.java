		try (ObjectInserter inserter = db.newObjectInserter()) {
			ObjectId empty = inserter.insert(new TreeFormatter());

			TreeFormatter A = new TreeFormatter();
			A.append("A", FileMode.TREE, empty);
			A.append("B", FileMode.TREE, empty);
			ObjectId idA = inserter.insert(A);

			TreeFormatter root = new TreeFormatter();
			root.append("A", FileMode.TREE, idA);
			root.append("B", FileMode.REGULAR_FILE, bId);
			ObjectId idRoot = inserter.insert(root);
			inserter.flush();

			tree_root = objw.parseTree(idRoot);
			tree_A = objw.parseTree(idA);
			tree_AB = objw.parseTree(empty);
			b = commit(tree_root);
