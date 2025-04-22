		assertFileDeletion(c2, c1,
				rightResult.get(0).getChildren().get("folder/a.txt"), "a.txt",
				RIGHT);
		assertFileAddition(c2, c1,
				rightResult.get(0).getChildren().get("folder1/b.txt"), "b.txt",
				RIGHT);
		assertFileChange(c2, c1,
				rightResult.get(0).getChildren().get("folder2/c.txt"), "c.txt",
				RIGHT);
