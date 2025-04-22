		assertFileChange(c2, c1,
				rightResult.get(0).getChildren().get("folder/a.txt"), "a.txt",
				RIGHT);
		assertFileChange(c2, c1,
				rightResult.get(0).getChildren().get("folder/b.txt"), "b.txt",
				RIGHT);
