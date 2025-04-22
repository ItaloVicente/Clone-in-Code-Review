package org.eclipse.jgit.internal.diffmergetool;

import static org.eclipse.jgit.lib.ConfigConstants.CONFIG_KEY_CMD;
import static org.eclipse.jgit.lib.ConfigConstants.CONFIG_KEY_GUITOOL;
import static org.eclipse.jgit.lib.ConfigConstants.CONFIG_KEY_PATH;
import static org.eclipse.jgit.lib.ConfigConstants.CONFIG_KEY_PROMPT;
import static org.eclipse.jgit.lib.ConfigConstants.CONFIG_KEY_TOOL;
import static org.eclipse.jgit.lib.ConfigConstants.CONFIG_KEY_TRUST_EXIT_CODE;
import static org.eclipse.jgit.lib.ConfigConstants.CONFIG_MERGETOOL_SECTION;
import static org.eclipse.jgit.lib.ConfigConstants.CONFIG_MERGE_SECTION;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.Assume.assumeTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.eclipse.jgit.lib.internal.BooleanTriState;
import org.eclipse.jgit.storage.file.FileBasedConfig;
import org.eclipse.jgit.util.FS.ExecutionResult;
import org.junit.Test;

public class ExternalMergeToolTest extends ExternalToolTestCase {

	@Test(expected = ToolException.class)
	public void testUserToolWithError() throws Exception {
		String toolName = "customTool";

		int errorReturnCode = 1;
		String command = "exit " + errorReturnCode;

		FileBasedConfig config = db.getConfig();
		config.setString(CONFIG_MERGETOOL_SECTION
				command);
		config.setString(CONFIG_MERGETOOL_SECTION
				CONFIG_KEY_TRUST_EXIT_CODE

		invokeMerge(toolName);

		fail("Expected exception to be thrown due to external tool exiting with error code: "
				+ errorReturnCode);
	}

	@Test(expected = ToolException.class)
	public void testUserToolWithCommandNotFoundError() throws Exception {
		String toolName = "customTool";

		String command = "exit " + errorReturnCode;

		FileBasedConfig config = db.getConfig();
		config.setString(CONFIG_MERGETOOL_SECTION
				command);

		invokeMerge(toolName);

		fail("Expected exception to be thrown due to external tool exiting with error code: "
				+ errorReturnCode);
	}

	@Test
	public void testKdiff3() throws Exception {
		assumePosixPlatform();

		CommandLineMergeTool autoMergingTool = CommandLineMergeTool.kdiff3;
		assumeMergeToolIsAvailable(autoMergingTool);

		CommandLineMergeTool tool = autoMergingTool;
		PreDefinedMergeTool externalTool = new PreDefinedMergeTool(tool.name()
				tool.getPath()
				tool.getParameters(false)
				tool.isExitCodeTrustable() ? BooleanTriState.TRUE
						: BooleanTriState.FALSE);

		MergeTools manager = new MergeTools(db);
		ExecutionResult result = manager.merge(local
				null
		assertEquals("Expected merge tool to succeed"

		List<String> actualLines = Files.readAllLines(mergedFile.toPath());
		String actualMergeResult = String.join(System.lineSeparator()
				actualLines);
		String expectedMergeResult = DEFAULT_CONTENT;
		assertEquals(
				"Failed to merge equal local and remote versions with pre-defined tool: "
						+ tool.getPath()
				expectedMergeResult
	}

	@Test
	public void testUserDefinedTool() throws Exception {
		String customToolName = "customTool";
		String command = getEchoCommand();

		FileBasedConfig config = db.getConfig();
		config.setString(CONFIG_MERGETOOL_SECTION
				CONFIG_KEY_CMD

		MergeTools manager = new MergeTools(db);
		Map<String
		ExternalMergeTool externalTool = tools.get(customToolName);
		manager.merge(local

		assertEchoCommandHasCorrectOutput();
	}

	@Test
	public void testUserDefinedToolWithPrompt() throws Exception {
		String customToolName = "customTool";
		String command = getEchoCommand();

		FileBasedConfig config = db.getConfig();
		config.setString(CONFIG_MERGETOOL_SECTION
				CONFIG_KEY_CMD

		MergeTools manager = new MergeTools(db);

		PromptHandler promptHandler = PromptHandler.acceptPrompt();
		MissingToolHandler noToolHandler = new MissingToolHandler();

		manager.merge(local
				Optional.of(customToolName)
				promptHandler

		assertEchoCommandHasCorrectOutput();

		List<String> actualToolPrompts = promptHandler.toolPrompts;
		List<String> expectedToolPrompts = Arrays.asList("customTool");
		assertEquals("Expected a user prompt for custom tool call"
				expectedToolPrompts

		assertEquals("Expected to no informing about missing tools"
				Collections.EMPTY_LIST
	}

	@Test
	public void testUserDefinedToolWithCancelledPrompt() throws Exception {
		MergeTools manager = new MergeTools(db);

		PromptHandler promptHandler = PromptHandler.cancelPrompt();
		MissingToolHandler noToolHandler = new MissingToolHandler();

		Optional<ExecutionResult> result = manager.merge(local
				base
				promptHandler
		assertFalse("Expected no result if user cancels the operation"
				result.isPresent());
	}

	@Test
	public void testAllTools() {
		FileBasedConfig config = db.getConfig();
		String customToolName = "customTool";
		config.setString(CONFIG_MERGETOOL_SECTION
				CONFIG_KEY_CMD

		MergeTools manager = new MergeTools(db);
		Set<String> actualToolNames = manager.getAllToolNames();
		Set<String> expectedToolNames = new LinkedHashSet<>();
		expectedToolNames.add(customToolName);
		CommandLineMergeTool[] defaultTools = CommandLineMergeTool.values();
		for (CommandLineMergeTool defaultTool : defaultTools) {
			String toolName = defaultTool.name();
			expectedToolNames.add(toolName);
		}
		assertEquals("Incorrect set of external merge tools"
				actualToolNames);
	}

	@Test
	public void testOverridePredefinedToolPath() {
		String toolName = CommandLineMergeTool.guiffy.name();
		String customToolPath = "/usr/bin/echo";

		FileBasedConfig config = db.getConfig();
		config.setString(CONFIG_MERGETOOL_SECTION
				"echo");
		config.setString(CONFIG_MERGETOOL_SECTION
				customToolPath);

		MergeTools manager = new MergeTools(db);
		Map<String
		ExternalMergeTool mergeTool = tools.get(toolName);
		assertNotNull("Expected tool \"" + toolName + "\" to be user defined"
				mergeTool);

		String toolPath = mergeTool.getPath();
		assertEquals("Expected external merge tool to have an overriden path"
				customToolPath
	}

	@Test
	public void testUserDefinedTools() {
		FileBasedConfig config = db.getConfig();
		String customToolname = "customTool";
		config.setString(CONFIG_MERGETOOL_SECTION
				CONFIG_KEY_CMD
		config.setString(CONFIG_MERGETOOL_SECTION
				CONFIG_KEY_PATH
		config.setString(CONFIG_MERGETOOL_SECTION
				CONFIG_KEY_PROMPT
		config.setString(CONFIG_MERGETOOL_SECTION
				CONFIG_KEY_GUITOOL
		config.setString(CONFIG_MERGETOOL_SECTION
				CONFIG_KEY_TRUST_EXIT_CODE
		MergeTools manager = new MergeTools(db);
		Set<String> actualToolNames = manager.getUserDefinedTools().keySet();
		Set<String> expectedToolNames = new LinkedHashSet<>();
		expectedToolNames.add(customToolname);
		assertEquals("Incorrect set of external merge tools"
				actualToolNames);
	}

	@Test
	public void testCompare() throws ToolException {
		String toolName = "customTool";

		FileBasedConfig config = db.getConfig();
		String subsection = null;
		config.setString(CONFIG_MERGE_SECTION
				toolName);

		String command = getEchoCommand();

		config.setString(CONFIG_MERGETOOL_SECTION
				command);

		Optional<ExecutionResult> result = invokeMerge(toolName);
		assertTrue("Expected external merge tool result to be available"
				result.isPresent());
		int expectedCompareResult = 0;
		assertEquals("Incorrect compare result for external merge tool"
				expectedCompareResult
	}

	@Test
	public void testDefaultTool() throws Exception {
		String toolName = "customTool";
		String guiToolName = "customGuiTool";

		FileBasedConfig config = db.getConfig();
		String subsection = null;
		config.setString(CONFIG_MERGE_SECTION
				toolName);

		MergeTools manager = new MergeTools(db);
		boolean gui = false;
		String defaultToolName = manager.getDefaultToolName(gui);
		assertEquals(
				"Expected configured mergetool to be the default external merge tool"
				toolName

		gui = true;
		String defaultGuiToolName = manager.getDefaultToolName(gui);
		assertNull("Expected default mergetool to not be set"
				defaultGuiToolName);

		config.setString(CONFIG_MERGE_SECTION
				guiToolName);
		manager = new MergeTools(db);
		defaultGuiToolName = manager.getDefaultToolName(gui);
		assertEquals(
				"Expected configured mergetool to be the default external merge guitool"
				guiToolName
	}

	@Test
	public void testOverridePreDefinedToolPath() {
		String newToolPath = "/tmp/path/";

		CommandLineMergeTool[] defaultTools = CommandLineMergeTool.values();
		assertTrue("Expected to find pre-defined external merge tools"
				defaultTools.length > 0);

		CommandLineMergeTool overridenTool = defaultTools[0];
		String overridenToolName = overridenTool.name();
		String overridenToolPath = newToolPath + overridenToolName;
		FileBasedConfig config = db.getConfig();
		config.setString(CONFIG_MERGETOOL_SECTION
				CONFIG_KEY_PATH

		MergeTools manager = new MergeTools(db);
		Map<String
				.getPredefinedTools(true);
		ExternalMergeTool externalMergeTool = availableTools
				.get(overridenToolName);
		String actualMergeToolPath = externalMergeTool.getPath();
		assertEquals(
				"Expected pre-defined external merge tool to have overriden path"
				overridenToolPath
		boolean withBase = true;
		String expectedMergeToolCommand = overridenToolPath + " "
				+ overridenTool.getParameters(withBase);
		String actualMergeToolCommand = externalMergeTool.getCommand();
		assertEquals(
				"Expected pre-defined external merge tool to have overriden command"
				expectedMergeToolCommand
	}

	@Test(expected = ToolException.class)
	public void testUndefinedTool() throws Exception {
		String toolName = "undefined";
		invokeMerge(toolName);
		fail("Expected exception to be thrown due to not defined external merge tool");
	}

	@Test
	public void testDefaultToolExecutionWithPrompt() throws Exception {
		FileBasedConfig config = db.getConfig();
		String subsection = null;
		config.setString("merge"

		String command = getEchoCommand();

		config.setString("mergetool"

		MergeTools manager = new MergeTools(db);

		PromptHandler promptHandler = PromptHandler.acceptPrompt();
		MissingToolHandler noToolHandler = new MissingToolHandler();

		manager.merge(local
				BooleanTriState.TRUE

		assertEchoCommandHasCorrectOutput();
	}

	@Test
	public void testNoDefaultToolName() {
		MergeTools manager = new MergeTools(db);
		boolean gui = false;
		String defaultToolName = manager.getDefaultToolName(gui);
		assertNull("Expected no default tool when none is configured"
				defaultToolName);

		gui = true;
		defaultToolName = manager.getDefaultToolName(gui);
		assertNull("Expected no default tool when none is configured"
				defaultToolName);
	}

	@Test(expected = ToolException.class)
	public void testNullTool() throws Exception {
		MergeTools manager = new MergeTools(db);

		PromptHandler promptHandler = null;
		MissingToolHandler noToolHandler = null;

		Optional<String> tool = null;

		manager.merge(local
				BooleanTriState.TRUE
	}

	@Test(expected = ToolException.class)
	public void testNullToolWithPrompt() throws Exception {
		MergeTools manager = new MergeTools(db);

		PromptHandler promptHandler = PromptHandler.cancelPrompt();
		MissingToolHandler noToolHandler = new MissingToolHandler();

		Optional<String> tool = null;

		manager.merge(local
				BooleanTriState.TRUE
	}

	private Optional<ExecutionResult> invokeMerge(String toolName)
			throws ToolException {
		BooleanTriState prompt = BooleanTriState.UNSET;
		boolean gui = false;

		MergeTools manager = new MergeTools(db);

		PromptHandler promptHandler = PromptHandler.acceptPrompt();
		MissingToolHandler noToolHandler = new MissingToolHandler();

		Optional<ExecutionResult> result = manager.merge(local
				base
				noToolHandler);
		return result;
	}

	private void assumeMergeToolIsAvailable(
			CommandLineMergeTool autoMergingTool) {
		boolean isAvailable = ExternalToolUtils.isToolAvailable(db.getFS()
				db.getDirectory()
		assumeTrue("Assuming external tool is available: "
				+ autoMergingTool.name()
	}

	private String getEchoCommand() {
		return "(echo $LOCAL $REMOTE $MERGED $BASE) > "
				+ commandResult.getAbsolutePath();
	}

	private void assertEchoCommandHasCorrectOutput() throws IOException {
		List<String> actualLines = Files.readAllLines(commandResult.toPath());
		String actualContent = String.join(System.lineSeparator()
		actualLines = Arrays.asList(actualContent.split(" "));
		List<String> expectedLines = Arrays.asList(localFile.getAbsolutePath()
				remoteFile.getAbsolutePath()
				baseFile.getAbsolutePath());
		assertEquals("Dummy test tool called with unexpected arguments"
				expectedLines
	}
}
