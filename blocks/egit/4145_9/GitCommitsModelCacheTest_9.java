		assertFileAddition(c1, c2,
				leftResult.get(0).getChildren().get("a.txt"), "a.txt", LEFT);
		assertFileDeletion(c1, c2,
				leftResult.get(0).getChildren().get("b.txt"), "b.txt", LEFT);
		assertFileChange(c1, c2, leftResult.get(0).getChildren().get("c.txt"),
				"c.txt", LEFT);
