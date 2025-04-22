		try (ObjectInserter inserter = db.newObjectInserter()) {
			ObjectId empty = inserter.insert(new TreeFormatter());

			TreeFormatter A = new TreeFormatter();
			A.append("A"
			A.append("B"
			ObjectId idA = inserter.insert(A);

			TreeFormatter root = new TreeFormatter();
			root.append("A"
			root.append("B"
			ObjectId idRoot = inserter.insert(root);
			inserter.flush();

			tree_root = objw.parseTree(idRoot);
			tree_A = objw.parseTree(idA);
			tree_AB = objw.parseTree(empty);
			b = commit(tree_root);
