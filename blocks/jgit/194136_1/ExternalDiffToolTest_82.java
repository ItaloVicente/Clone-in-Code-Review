public class ExternalDiffToolTest extends ExternalToolTestCase {

	@Test(expected = ToolException.class)
	public void testUserToolWithError() throws Exception {
		String toolName = "customTool";

		int errorReturnCode = 1;
		String command = "exit " + errorReturnCode;

		FileBasedConfig config = db.getConfig();
		config.setString(CONFIG_DIFFTOOL_SECTION
				command);

		invokeCompare(toolName);

		fail("Expected exception to be thrown due to external tool exiting with error code: "
				+ errorReturnCode);
	}

	@Test(expected = ToolException.class)
	public void testUserToolWithCommandNotFoundError() throws Exception {
		String toolName = "customTool";

		String command = "exit " + errorReturnCode;

		FileBasedConfig config = db.getConfig();
		config.setString(CONFIG_DIFFTOOL_SECTION
				command);

		invokeCompare(toolName);
		fail("Expected exception to be thrown due to external tool exiting with error code: "
				+ errorReturnCode);
	}

	@Test
	public void testUserDefinedTool() throws Exception {
		String command = getEchoCommand();

		FileBasedConfig config = db.getConfig();
		String customToolName = "customTool";
		config.setString(CONFIG_DIFFTOOL_SECTION
				CONFIG_KEY_CMD

		DiffTools manager = new DiffTools(db);

		Map<String
		ExternalDiffTool externalTool = tools.get(customToolName);
		boolean trustExitCode = true;
		manager.compare(local

		assertEchoCommandHasCorrectOutput();
	}

	@Test
	public void testUserDefinedToolWithPrompt() throws Exception {
		String command = getEchoCommand();

		FileBasedConfig config = db.getConfig();
		String customToolName = "customTool";
		config.setString(CONFIG_DIFFTOOL_SECTION
				CONFIG_KEY_CMD

		DiffTools manager = new DiffTools(db);

		PromptHandler promptHandler = PromptHandler.acceptPrompt();
		MissingToolHandler noToolHandler = new MissingToolHandler();

		manager.compare(local
				BooleanTriState.TRUE
				promptHandler

		assertEchoCommandHasCorrectOutput();

		List<String> actualToolPrompts = promptHandler.toolPrompts;
		List<String> expectedToolPrompts = Arrays.asList("customTool");
		assertEquals("Expected a user prompt for custom tool call"
				expectedToolPrompts

		assertEquals("Expected to no informing about missing tools"
				Collections.EMPTY_LIST
	}
