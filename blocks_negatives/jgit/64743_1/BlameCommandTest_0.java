		Git git = new Git(db);

		String[] content = new String[] { "first", "second", "third" };

		writeTrashFile("file.txt", join(content));
		git.add().addFilepattern("file.txt").call();
		RevCommit commit = git.commit().setMessage("create file").call();

		BlameCommand command = new BlameCommand(db);
		command.setFilePath("file.txt");
		BlameResult lines = command.call();
		assertNotNull(lines);
		assertEquals(3, lines.getResultContents().size());

		for (int i = 0; i < 3; i++) {
			assertEquals(commit, lines.getSourceCommit(i));
			assertEquals(i, lines.getSourceLine(i));
