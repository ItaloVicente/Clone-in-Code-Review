		try (Git git = new Git(db)) {
			FileBasedConfig config = db.getConfig();
			config.setEnum(ConfigConstants.CONFIG_CORE_SECTION
					ConfigConstants.CONFIG_KEY_AUTOCRLF
			config.save();

			String joinedCrlf = "a\r\nb\r\nc\r\n";
			File trashFile = writeTrashFile("file.txt"
			git.add().addFilepattern("file.txt").call();
			RevCommit commit = git.commit().setMessage("create file").call();

			trashFile.delete();
			config.setEnum(ConfigConstants.CONFIG_CORE_SECTION
					ConfigConstants.CONFIG_KEY_AUTOCRLF
			config.save();
			git.reset().setMode(ResetType.HARD).call();

			BlameCommand command = new BlameCommand(db);
			command.setFilePath("file.txt");
			BlameResult lines = command.call();

			assertEquals(3
			assertEquals(commit
			assertEquals(commit
			assertEquals(commit
		}
