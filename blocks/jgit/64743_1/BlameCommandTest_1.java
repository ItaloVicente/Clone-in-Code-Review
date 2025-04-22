		try (Git git = new Git(db)) {
			String[] content1 = new String[] { "first"
			writeTrashFile("file.txt"
			git.add().addFilepattern("file.txt").call();
			RevCommit commit1 = git.commit().setMessage("create file").call();

			String[] content2 = new String[] { "first"
			writeTrashFile("file.txt"
			git.add().addFilepattern("file.txt").call();
			RevCommit commit2 = git.commit().setMessage("create file").call();

			BlameCommand command = new BlameCommand(db);
			command.setFilePath("file.txt");
			BlameResult lines = command.call();
			assertEquals(3

			assertEquals(commit1
			assertEquals(0

			assertEquals(commit1
			assertEquals(1

			assertEquals(commit2
			assertEquals(2
		}
