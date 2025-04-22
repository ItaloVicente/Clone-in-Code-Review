		assertFileDeletion(c2, c1, rightResult.get(0).getChildren()
				.get("a.txt"), "a.txt", RIGHT);
		assertFileAddition(c2, c1, rightResult.get(0).getChildren()
				.get("b.txt"), "b.txt", RIGHT);
		assertFileChange(c2, c1, rightResult.get(0).getChildren().get("c.txt"),
				"c.txt", RIGHT);
