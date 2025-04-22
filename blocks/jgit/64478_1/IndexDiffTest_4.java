		tree.addFile("a.b").setId(ObjectId.fromString("f6f28df96c2b40c951164286e08be7c38ec74851"));
		tree.addFile("a.c").setId(ObjectId.fromString("6bc0e647512d2a0bef4f26111e484dc87df7f5ca"));
		tree.addFile("a/b.b/b").setId(ObjectId.fromString("8d840bd4e2f3a48ff417c8e927d94996849933fd"));
		tree.addFile("a/b").setId(ObjectId.fromString("db89c972fc57862eae378f45b74aca228037d415"));
		tree.addFile("a/c").setId(ObjectId.fromString("52ad142a008aeb39694bafff8e8f1be75ed7f007"));
		tree.addFile("a=c").setId(ObjectId.fromString("06022365ddbd7fb126761319633bf73517770714"));
		tree.addFile("a=d").setId(ObjectId.fromString("fa6414df3da87840700e9eeb7fc261dd77ccd5c2"));

		Tree tree3 = (Tree) tree.findTreeMember("a/b.b");
		tree3.setId(insertTree(tree3));
		Tree tree2 = (Tree) tree.findTreeMember("a");
		tree2.setId(insertTree(tree2));
		tree.setId(insertTree(tree));
