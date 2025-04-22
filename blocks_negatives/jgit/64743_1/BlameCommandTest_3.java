		Git git = new Git(db);

		String[] content1 = new String[] { "a" };
		writeTrashFile("file.txt", join(content1));
		git.add().addFilepattern("file.txt").call();
		RevCommit commit1 = git.commit().setMessage("create file").call();

		writeTrashFile("file1.txt", join(content1));
		git.add().addFilepattern("file1.txt").call();
		git.rm().addFilepattern("file.txt").call();
		git.commit().setMessage("moving file").call();

		String[] content2 = new String[] { "a", "b" };
		writeTrashFile("file1.txt", join(content2));
		git.add().addFilepattern("file1.txt").call();
		RevCommit commit3 = git.commit().setMessage("editing file").call();

		writeTrashFile("file2.txt", join(content2));
		git.add().addFilepattern("file2.txt").call();
		git.rm().addFilepattern("file1.txt").call();
		git.commit().setMessage("moving file again").call();

		BlameCommand command = new BlameCommand(db);
		command.setFollowFileRenames(true);
		command.setFilePath("file2.txt");
		BlameResult lines = command.call();

		assertEquals(commit1, lines.getSourceCommit(0));
		assertEquals(0, lines.getSourceLine(0));
		assertEquals("file.txt", lines.getSourcePath(0));

		assertEquals(commit3, lines.getSourceCommit(1));
		assertEquals(1, lines.getSourceLine(1));
		assertEquals("file1.txt", lines.getSourcePath(1));
