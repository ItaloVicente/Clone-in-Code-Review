		TreeFormatter bb = new TreeFormatter();
		bb.append("b"

		TreeFormatter a = new TreeFormatter();
		a.append("b.b"
		a.append("b"
		a.append("c"

		TreeFormatter tree = new TreeFormatter();
		tree.append("a.b"
		tree.append("a.c"
		tree.append("a"
		tree.append("a=c"
		tree.append("a=d"
		ObjectId treeId = insertTree(tree);
