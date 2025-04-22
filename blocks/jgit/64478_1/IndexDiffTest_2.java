		Tree tree = new Tree(db);
		tree.addFile("file2");
		tree.addFile("dir/file3");
		assertEquals(2
		tree.findBlobMember("file2").setId(ObjectId.fromString("30d67d4672d5c05833b7192cc77a79eaafb5c7ad"));
		Tree tree2 = (Tree) tree.findTreeMember("dir");
		tree2.findBlobMember("file3").setId(ObjectId.fromString("873fb8d667d05436d728c52b1d7a09528e6eb59b"));
		tree2.setId(insertTree(tree2));
		tree.setId(insertTree(tree));
