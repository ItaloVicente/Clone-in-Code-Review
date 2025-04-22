		Git git = new Git(db);

		String[] content1 = new String[] { "first", "second" };
		writeTrashFile("file.txt", join(content1));
		git.add().addFilepattern("file.txt").call();
		RevCommit commit1 = git.commit().setMessage("create file").call();

		String[] content2 = new String[] { "first", "second", "third" };
		writeTrashFile("file.txt", join(content2));
		git.add().addFilepattern("file.txt").call();
		RevCommit commit2 = git.commit().setMessage("create file").call();

		BlameCommand command = new BlameCommand(db);
		command.setFilePath("file.txt");
		BlameResult lines = command.call();
		assertEquals(3, lines.getResultContents().size());

		assertEquals(commit1, lines.getSourceCommit(0));
		assertEquals(0, lines.getSourceLine(0));

		assertEquals(commit1, lines.getSourceCommit(1));
		assertEquals(1, lines.getSourceLine(1));

		assertEquals(commit2, lines.getSourceCommit(2));
		assertEquals(2, lines.getSourceLine(2));
