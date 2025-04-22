		try (Git git = new Git(db)) {
			String[] content = new String[] { "first"

			writeTrashFile("file.txt"
			git.add().addFilepattern("file.txt").call();
			RevCommit commit = git.commit().setMessage("create file").call();

			BlameCommand command = new BlameCommand(db);
			command.setFilePath("file.txt");
			BlameResult lines = command.call();
			assertNotNull(lines);
			assertEquals(3

			for (int i = 0; i < 3; i++) {
				assertEquals(commit
				assertEquals(i
			}
