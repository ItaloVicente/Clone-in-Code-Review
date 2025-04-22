	@Test
	public void testCoreEol1() throws Exception {
		testCoreEol(EOL.NATIVE
	}

	@Test
	public void testCoreEol2() throws Exception {
		testCoreEol(EOL.NATIVE
	}

	@Test
	public void testCoreEol3() throws Exception {
		testCoreEol(EOL.NATIVE
	}

	@Test
	public void testCoreEol4() throws Exception {
		testCoreEol(EOL.LF
	}

	@Test
	public void testCoreEol5() throws Exception {
		testCoreEol(EOL.LF
	}

	@Test
	public void testCoreEol6() throws Exception {
		testCoreEol(EOL.LF
	}

	@Test
	public void testCoreEol7() throws Exception {
		testCoreEol(EOL.CRLF
	}

	@Test
	public void testCoreEol8() throws Exception {
		testCoreEol(EOL.CRLF
	}

	@Test
	public void testCoreEol9() throws Exception {
		testCoreEol(EOL.CRLF
	}

	private void testCoreEol(EOL modeForCommitting
		Git git = new Git(db);
		FileBasedConfig config = db.getConfig();
		config.setEnum(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_KEY_EOL
		config.save();

		File trashFile = writeTrashFile(Constants.DOT_GIT_ATTRIBUTES
				"*.txt text");
		git.add().addFilepattern(trashFile.getName()).call();
		RevCommit commit = git.commit().setMessage("create .gitattributes")
				.call();

		String joinedCrlf = "a\r\nb\r\nc\r\n";
		trashFile = writeTrashFile("file.txt"
		git.add().addFilepattern(trashFile.getName()).call();
		commit = git.commit().setMessage("create file").call();

		trashFile.delete();
		config.setEnum(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_KEY_EOL
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

