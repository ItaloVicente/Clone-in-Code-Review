		Git git = new Git(db);

		String[] content1 = new String[] { "a", "b", "c" };
		writeTrashFile(sourcePath, join(content1));
		git.add().addFilepattern(sourcePath).call();
		RevCommit commit1 = git.commit().setMessage("create file").call();

		writeTrashFile(destPath, join(content1));
		git.add().addFilepattern(destPath).call();
		git.rm().addFilepattern(sourcePath).call();
		git.commit().setMessage("moving file").call();

		String[] content2 = new String[] { "a", "b", "c2" };
		writeTrashFile(destPath, join(content2));
		git.add().addFilepattern(destPath).call();
		RevCommit commit3 = git.commit().setMessage("editing file").call();

		BlameCommand command = new BlameCommand(db);
		command.setFollowFileRenames(true);
		command.setFilePath(destPath);
		BlameResult lines = command.call();

		assertEquals(commit1, lines.getSourceCommit(0));
		assertEquals(0, lines.getSourceLine(0));
		assertEquals(sourcePath, lines.getSourcePath(0));

		assertEquals(commit1, lines.getSourceCommit(1));
		assertEquals(1, lines.getSourceLine(1));
		assertEquals(sourcePath, lines.getSourcePath(1));

		assertEquals(commit3, lines.getSourceCommit(2));
		assertEquals(2, lines.getSourceLine(2));
		assertEquals(destPath, lines.getSourcePath(2));
