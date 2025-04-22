		assertFileDeletion(c,
				leftResult.get(0).getChildren().get("folder/a.txt"), "a.txt",
				LEFT);
		assertFileDeletion(c,
				leftResult.get(0).getChildren().get("folder2/b.txt"), "b.txt",
				LEFT);
