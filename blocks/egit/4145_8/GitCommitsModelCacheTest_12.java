		assertFileDeletion(c2, c1,
				rightResult.get(0).getChildren().get("folder/a.txt"), "a.txt",
				RIGHT);
		assertFileAddition(c2, c1,
				rightResult.get(0).getChildren().get("folder/b.txt"), "b.txt",
				RIGHT);
		assertFileChange(c2, c1,
				rightResult.get(0).getChildren().get("folder/c.txt"), "c.txt",
				RIGHT);
