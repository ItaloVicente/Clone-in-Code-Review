		Git git = new Git(db);
		FileBasedConfig config = db.getConfig();
		config.setEnum(ConfigConstants.CONFIG_CORE_SECTION, null,
				ConfigConstants.CONFIG_KEY_AUTOCRLF, modeForCommitting);
		config.save();

		String joinedCrlf = "a\r\nb\r\nc\r\n";
		File trashFile = writeTrashFile("file.txt", joinedCrlf);
		git.add().addFilepattern("file.txt").call();
		RevCommit commit = git.commit().setMessage("create file").call();

		trashFile.delete();
		config.setEnum(ConfigConstants.CONFIG_CORE_SECTION, null,
				ConfigConstants.CONFIG_KEY_AUTOCRLF, modeForReset);
		config.save();
		git.reset().setMode(ResetType.HARD).call();

		BlameCommand command = new BlameCommand(db);
		command.setFilePath("file.txt");
		BlameResult lines = command.call();

		assertEquals(3, lines.getResultContents().size());
		assertEquals(commit, lines.getSourceCommit(0));
		assertEquals(commit, lines.getSourceCommit(1));
		assertEquals(commit, lines.getSourceCommit(2));
