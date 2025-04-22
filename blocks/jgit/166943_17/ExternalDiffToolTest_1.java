	@Test
	public void testDefaultToolExecutionWithPrompt() throws Exception {
		FileBasedConfig config = db.getConfig();
		String subsection = null;
		config.setString("diff"

		String command = getEchoCommand();

		config.setString("difftool"

		DiffTools manager = new DiffTools(db);

		PromptHandler promptHandler = PromptHandler.acceptPrompt();
		MissingToolHandler noToolHandler = new MissingToolHandler();

		manager.compare(local
				false

		assertEchoCommandHasCorrectOutput();
	}

	@Test
	public void testNoDefaultToolName() {
		DiffTools manager = new DiffTools(db);
		boolean gui = false;
		String defaultToolName = manager.getDefaultToolName(gui);
		assertNull("Expected no default tool when none is configured"
				defaultToolName);

		gui = true;
		defaultToolName = manager.getDefaultToolName(gui);
		assertNull("Expected no default tool when none is configured"
				defaultToolName);
	}

	@Test
	public void testExternalToolInGitAttributes() throws Exception {
		String content = "attributes:\n*.txt 		difftool=customTool";
		File gitattributes = writeTrashFile(".gitattributes"
		gitattributes.deleteOnExit();
		try (TestRepository<Repository> testRepository = new TestRepository<>(
				db)) {
			FileBasedConfig config = db.getConfig();
			config.setString("difftool"
			testRepository.git().add().addFilepattern(localFile.getName())
					.call();

			testRepository.git().add().addFilepattern(".gitattributes").call();

			testRepository.branch("master").commit().message("first commit")
					.create();

			DiffTools manager = new DiffTools(db);
			Optional<String> tool = manager
					.getExternalToolFromAttributes(localFile.getName());
			assertTrue("Failed to find user defined tool"
			assertEquals("Failed to find user defined tool"
					tool.get());
		} finally {
			Files.delete(gitattributes.toPath());
		}
	}

	@Test
	public void testNotExternalToolInGitAttributes() throws Exception {
		String content = "";
		File gitattributes = writeTrashFile(".gitattributes"
		gitattributes.deleteOnExit();
		try (TestRepository<Repository> testRepository = new TestRepository<>(
				db)) {
			FileBasedConfig config = db.getConfig();
			config.setString("difftool"
			testRepository.git().add().addFilepattern(localFile.getName())
					.call();

			testRepository.git().add().addFilepattern(".gitattributes").call();

			testRepository.branch("master").commit().message("first commit")
					.create();

			DiffTools manager = new DiffTools(db);
			Optional<String> tool = manager
					.getExternalToolFromAttributes(localFile.getName());
			assertFalse(
					"Expected no external tool if no default tool is specified in .gitattributes"
					tool.isPresent());
		} finally {
			Files.delete(gitattributes.toPath());
		}
	}

	@Test(expected = ToolException.class)
	public void testNullTool() throws Exception {
		DiffTools manager = new DiffTools(db);

		boolean trustExitCode = true;
		ExternalDiffTool tool = null;
		manager.compare(local
	}

	@Test(expected = ToolException.class)
	public void testNullToolWithPrompt() throws Exception {
		DiffTools manager = new DiffTools(db);

		PromptHandler promptHandler = PromptHandler.cancelPrompt();
		MissingToolHandler noToolHandler = new MissingToolHandler();

		Optional<String> tool = null;
		manager.compare(local
				BooleanTriState.TRUE
	}

