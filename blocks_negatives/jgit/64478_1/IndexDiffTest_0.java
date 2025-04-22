		TreeFormatter dir = new TreeFormatter();
		dir.append("file3", FileMode.REGULAR_FILE, ObjectId.fromString("873fb8d667d05436d728c52b1d7a09528e6eb59b"));

		TreeFormatter tree = new TreeFormatter();
		tree.append("file2", FileMode.REGULAR_FILE, ObjectId.fromString("30d67d4672d5c05833b7192cc77a79eaafb5c7ad"));
		tree.append("dir", FileMode.TREE, insertTree(dir));
		ObjectId treeId = insertTree(tree);
