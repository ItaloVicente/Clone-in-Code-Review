		untrackedFile = new File(db.getWorkTree(),
				"notAddedToIndex.txt");
		FileUtils.createNewFile(untrackedFile);
		try (PrintWriter writer2 = new PrintWriter(untrackedFile)) {
			writer2.print("content");
		}
