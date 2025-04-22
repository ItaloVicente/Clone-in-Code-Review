		assertFileDeletion(c,
				leftResult.get(0).getChildren().get("folder/a.txt"), "a.txt",
				LEFT);
		assertFileDeletion(c,
				leftResult.get(0).getChildren().get("folder/b.txt"), "b.txt",
				LEFT);
