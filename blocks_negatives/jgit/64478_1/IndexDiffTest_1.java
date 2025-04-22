		TreeFormatter dir = new TreeFormatter();
		dir.append("file3", FileMode.REGULAR_FILE, ObjectId.fromString("0123456789012345678901234567890123456789"));

		TreeFormatter tree = new TreeFormatter();
		tree.append("dir", FileMode.TREE, insertTree(dir));
		tree.append("file2", FileMode.REGULAR_FILE, ObjectId.fromString("0123456789012345678901234567890123456789"));
		ObjectId treeId = insertTree(tree);
