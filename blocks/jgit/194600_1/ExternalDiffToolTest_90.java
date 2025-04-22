				guiToolName
	}

	@Test
	public void testOverridePreDefinedToolPath() {
		String newToolPath = "/tmp/path/";

		CommandLineDiffTool[] defaultTools = CommandLineDiffTool.values();
		assertTrue("Expected to find pre-defined external diff tools"
				defaultTools.length > 0);

		CommandLineDiffTool overridenTool = defaultTools[0];
		String overridenToolName = overridenTool.name();
		String overridenToolPath = newToolPath + overridenToolName;
		FileBasedConfig config = db.getConfig();
		config.setString(CONFIG_DIFFTOOL_SECTION
				CONFIG_KEY_PATH

		DiffTools manager = new DiffTools(db);
		Map<String
				.getPredefinedTools(true);
		ExternalDiffTool externalDiffTool = availableTools
				.get(overridenToolName);
		String actualDiffToolPath = externalDiffTool.getPath();
		assertEquals(
				"Expected pre-defined external diff tool to have overriden path"
				overridenToolPath
		String expectedDiffToolCommand = overridenToolPath + " "
				+ overridenTool.getParameters();
		String actualDiffToolCommand = externalDiffTool.getCommand();
		assertEquals(
				"Expected pre-defined external diff tool to have overriden command"
				expectedDiffToolCommand
	}

	@Test(expected = ToolException.class)
	public void testUndefinedTool() throws Exception {
		String toolName = "undefined";
		invokeCompare(toolName);
		fail("Expected exception to be thrown due to not defined external diff tool");
	}

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

	private Optional<ExecutionResult> invokeCompare(String toolName)
			throws ToolException {
		DiffTools manager = new DiffTools(db);

		BooleanTriState prompt = BooleanTriState.UNSET;
		boolean gui = false;
		BooleanTriState trustExitCode = BooleanTriState.TRUE;
		PromptHandler promptHandler = PromptHandler.acceptPrompt();
		MissingToolHandler noToolHandler = new MissingToolHandler();

		Optional<ExecutionResult> result = manager.compare(local
				Optional.of(toolName)
				promptHandler
		return result;
	}

	private String getEchoCommand() {
		return "(echo \"$LOCAL\" \"$REMOTE\") > "
				+ commandResult.getAbsolutePath();
	}

	private void assertEchoCommandHasCorrectOutput() throws IOException {
		List<String> actualLines = Files.readAllLines(commandResult.toPath());
		String actualContent = String.join(System.lineSeparator()
		actualLines = Arrays.asList(actualContent.split(" "));
		List<String> expectedLines = Arrays.asList(localFile.getAbsolutePath()
				remoteFile.getAbsolutePath());
		assertEquals("Dummy test tool called with unexpected arguments"
				expectedLines
