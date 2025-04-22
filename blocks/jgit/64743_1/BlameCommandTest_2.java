		try (Git git = new Git(db)) {
			String[] content1 = new String[] { "a"
			writeTrashFile(sourcePath
			git.add().addFilepattern(sourcePath).call();
			RevCommit commit1 = git.commit().setMessage("create file").call();

			writeTrashFile(destPath
			git.add().addFilepattern(destPath).call();
			git.rm().addFilepattern(sourcePath).call();
			git.commit().setMessage("moving file").call();

			String[] content2 = new String[] { "a"
			writeTrashFile(destPath
			git.add().addFilepattern(destPath).call();
			RevCommit commit3 = git.commit().setMessage("editing file").call();

			BlameCommand command = new BlameCommand(db);
			command.setFollowFileRenames(true);
			command.setFilePath(destPath);
			BlameResult lines = command.call();

			assertEquals(commit1
			assertEquals(0
			assertEquals(sourcePath

			assertEquals(commit1
			assertEquals(1
			assertEquals(sourcePath

			assertEquals(commit3
			assertEquals(2
			assertEquals(destPath
		}
