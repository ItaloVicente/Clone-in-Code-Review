package org.eclipse.jgit.internal.diffmergetool;

import static org.eclipse.jgit.lib.ConfigConstants.CONFIG_MERGETOOL_SECTION;
import static org.eclipse.jgit.lib.ConfigConstants.CONFIG_MERGE_SECTION;
import static org.eclipse.jgit.lib.ConfigConstants.CONFIG_KEY_CMD;
import static org.eclipse.jgit.lib.ConfigConstants.CONFIG_KEY_GUITOOL;
import static org.eclipse.jgit.lib.ConfigConstants.CONFIG_KEY_PATH;
import static org.eclipse.jgit.lib.ConfigConstants.CONFIG_KEY_PROMPT;
import static org.eclipse.jgit.lib.ConfigConstants.CONFIG_KEY_TOOL;
import static org.eclipse.jgit.lib.ConfigConstants.CONFIG_KEY_TRUST_EXIT_CODE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Map;
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

		MergeTools manager = new MergeTools(db);

		BooleanTriState prompt = BooleanTriState.UNSET;
		BooleanTriState gui = BooleanTriState.UNSET;

		manager.merge(local

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

		MergeTools manager = new MergeTools(db);

		BooleanTriState prompt = BooleanTriState.UNSET;
		BooleanTriState gui = BooleanTriState.UNSET;

		manager.merge(local

		fail("Expected exception to be thrown due to external tool exiting with error code: "
				+ errorReturnCode);
	}

	@Test
	public void testToolNames() {
		MergeTools manager = new MergeTools(db);
		Set<String> actualToolNames = manager.getToolNames();
		Set<String> expectedToolNames = Collections.emptySet();
		assertEquals("Incorrect set of external merge tool names"
				expectedToolNames
	}

	@Test
	public void testAllTools() {
		MergeTools manager = new MergeTools(db);
		Set<String> actualToolNames = manager.getAvailableTools().keySet();
		Set<String> expectedToolNames = new LinkedHashSet<>();
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
	public void testNotAvailableTools() {
		MergeTools manager = new MergeTools(db);
		Set<String> actualToolNames = manager.getNotAvailableTools().keySet();
		Set<String> expectedToolNames = Collections.emptySet();
		assertEquals("Incorrect set of not available external merge tools"
				expectedToolNames
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

		BooleanTriState prompt = BooleanTriState.UNSET;
		BooleanTriState gui = BooleanTriState.UNSET;

		MergeTools manager = new MergeTools(db);

		int expectedCompareResult = 0;
		ExecutionResult compareResult = manager.merge(local
				base
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
		BooleanTriState gui = BooleanTriState.UNSET;
		String defaultToolName = manager.getDefaultToolName(gui);
		assertEquals(
				"Expected configured mergetool to be the default external merge tool"
				toolName

		gui = BooleanTriState.TRUE;
		String defaultGuiToolName = manager.getDefaultToolName(gui);
		assertEquals(
				"Expected configured mergetool to be the default external merge tool"
				"my_gui_tool"

		config.setString(CONFIG_MERGE_SECTION
				guiToolName);
		manager = new MergeTools(db);
		defaultGuiToolName = manager.getDefaultToolName(gui);
		assertEquals(
				"Expected configured mergetool to be the default external merge guitool"
				"my_gui_tool"
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
				.getAvailableTools();
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
		MergeTools manager = new MergeTools(db);

		String toolName = "undefined";
		BooleanTriState prompt = BooleanTriState.UNSET;
		BooleanTriState gui = BooleanTriState.UNSET;

		manager.merge(local
		fail("Expected exception to be thrown due to not defined external merge tool");
	}

	private String getEchoCommand() {
		return "(echo \"$LOCAL\" \"$REMOTE\") > "
				+ commandResult.getAbsolutePath();
	}
}
