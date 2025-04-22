		assertFileChange(c1, c2,
				leftResult.get(0).getChildren().get("folder/a.txt"), "a.txt",
				LEFT);
		assertFileChange(c1, c2,
				leftResult.get(0).getChildren().get("folder2/b.txt"), "b.txt",
				LEFT);
