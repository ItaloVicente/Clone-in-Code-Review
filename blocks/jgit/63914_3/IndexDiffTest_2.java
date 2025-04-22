		TreeFormatter bb = new TreeFormatter();
		bb.append("b"

		TreeFormatter a = new TreeFormatter();
		a.append("b"
				.fromString("db89c972fc57862eae378f45b74aca228037d415"));
		a.append("b.b"
		a.append("c"

		TreeFormatter tree = new TreeFormatter();
		tree.append("a.b"
		tree.append("a.c"
		tree.append("a"
		tree.append("a=c"
		tree.append("a=d"
		ObjectId treeId = insertTree(tree);
