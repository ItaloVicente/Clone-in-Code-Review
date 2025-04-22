		TreeFormatter bb = new TreeFormatter();
		bb.append("b", FileMode.REGULAR_FILE, ObjectId.fromString("8d840bd4e2f3a48ff417c8e927d94996849933fd"));

		TreeFormatter a = new TreeFormatter();
		a.append("b", FileMode.REGULAR_FILE, ObjectId
				.fromString("db89c972fc57862eae378f45b74aca228037d415"));
		a.append("b.b", FileMode.TREE, insertTree(bb));
		a.append("c", FileMode.REGULAR_FILE, ObjectId.fromString("52ad142a008aeb39694bafff8e8f1be75ed7f007"));

		TreeFormatter tree = new TreeFormatter();
		tree.append("a.b", FileMode.REGULAR_FILE, ObjectId.fromString("f6f28df96c2b40c951164286e08be7c38ec74851"));
		tree.append("a.c", FileMode.REGULAR_FILE, ObjectId.fromString("6bc0e647512d2a0bef4f26111e484dc87df7f5ca"));
		tree.append("a", FileMode.TREE, insertTree(a));
		tree.append("a=c", FileMode.REGULAR_FILE, ObjectId.fromString("06022365ddbd7fb126761319633bf73517770714"));
		tree.append("a=d", FileMode.REGULAR_FILE, ObjectId.fromString("fa6414df3da87840700e9eeb7fc261dd77ccd5c2"));
		ObjectId treeId = insertTree(tree);
