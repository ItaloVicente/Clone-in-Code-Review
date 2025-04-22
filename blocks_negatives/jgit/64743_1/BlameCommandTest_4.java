		command.setFilePath("file.txt");
		BlameResult lines = command.call();
		assertEquals(content1.length, lines.getResultContents().size());
		assertEquals(commit3, lines.getSourceCommit(0));
		assertEquals(commit3, lines.getSourceCommit(1));
		assertEquals(commit3, lines.getSourceCommit(2));
