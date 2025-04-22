		assertFileAddition(c1, c2,
				leftResult.get(0).getChildren().get("folder/a.txt"), "a.txt",
				LEFT);
		assertFileDeletion(c1, c2,
				leftResult.get(0).getChildren().get("folder/b.txt"), "b.txt",
				LEFT);
		assertFileChange(c1, c2,
				leftResult.get(0).getChildren().get("folder/c.txt"), "c.txt",
				LEFT);
