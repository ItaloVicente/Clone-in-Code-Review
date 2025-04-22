package org.eclipse.egit.ui.internal.external.tools;

import static org.eclipse.jgit.lib.ConfigConstants.CONFIG_KEY_CMD;
import static org.eclipse.jgit.lib.ConfigConstants.CONFIG_KEY_PROMPT;
import static org.eclipse.jgit.lib.ConfigConstants.CONFIG_KEY_TOOL;
import static org.eclipse.jgit.lib.ConfigConstants.CONFIG_KEY_TRUST_EXIT_CODE;
import static org.eclipse.jgit.lib.ConfigConstants.CONFIG_MERGETOOL_SECTION;
import static org.eclipse.jgit.lib.ConfigConstants.CONFIG_MERGE_SECTION;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.regex.Pattern;

import org.eclipse.egit.ui.UIPreferences;
import org.eclipse.egit.ui.internal.diffmerge.MergeToolMode;
import org.eclipse.egit.ui.test.ContextMenuHelper;
import org.eclipse.egit.ui.test.TestUtil;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ExternalMergeToolUiTest extends ExternalToolUiTestCase {

	private String previousCustomToolPreference;

	private int previousMergeToolModePreference;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		previousCustomToolPreference = preferenceNode
				.get(UIPreferences.MERGE_TOOL_CUSTOM, "");
		previousMergeToolModePreference = preferenceNode.getInt(
				UIPreferences.MERGE_TOOL_MODE,
				MergeToolMode.INTERNAL.ordinal());
	}

	@Override
	@After
	public void tearDown() throws Exception {
		try {
			preferenceNode.put(UIPreferences.MERGE_TOOL_CUSTOM,
					previousCustomToolPreference);
			preferenceNode.putInt(UIPreferences.MERGE_TOOL_MODE,
					previousMergeToolModePreference);
		} finally {
			super.tearDown();
		}
	}

	@Test
	public void testExternalMergeTool() throws Exception {
		assumePosixPlatform();

		String toolName = "custom_tool";
		configureEchoTools(toolName);
		preferenceNode.putInt(UIPreferences.MERGE_TOOL_MODE,
				MergeToolMode.EXTERNAL.ordinal());
		preferenceNode.put(UIPreferences.MERGE_TOOL_CUSTOM, toolName);

		createMergeConflict();
		triggerMergeToolAction();

		List<String> commandOutputLines = waitForToolOutput();

		String actualCommandOutput = String.join(System.lineSeparator(),
				commandOutputLines);
		String expectedOutputPattern = toolName + " merged=.*.txt";
		boolean matchingOutput = Pattern.matches(expectedOutputPattern,
				actualCommandOutput);
		assertTrue("Command output doesn't match expected pattern: "
				+ expectedOutputPattern + ", command output: "
				+ actualCommandOutput, matchingOutput);
	}

	@Test
	public void testExternalMergeToolGitConfig() throws Exception {
		assumePosixPlatform();

		createMergeConflict();

		int runs = 2;
		for (int i = 0; i < runs; ++i) {
			clearResultFile();

			String toolName = "custom_tool" + i;
			configureEchoTools(toolName);
			preferenceNode.putInt(UIPreferences.MERGE_TOOL_MODE,
					MergeToolMode.GIT_CONFIG.ordinal());
			preferenceNode.put(UIPreferences.MERGE_TOOL_CUSTOM, toolName);

			triggerMergeToolAction();

			List<String> commandOutputLines = waitForToolOutput();

			String actualCommandOutput = String.join(System.lineSeparator(),
					commandOutputLines);
			String expectedOutputPattern = toolName + " merged=.*.txt";
			boolean matchingOutput = Pattern.matches(expectedOutputPattern,
					actualCommandOutput);
			assertTrue("Command output doesn't match expected pattern: "
					+ expectedOutputPattern + ", command output: "
					+ actualCommandOutput, matchingOutput);
		}
	}

	@Test
	public void testExternalDiffToolGitAttributesOverride() throws Exception {
		assumePosixPlatform();

		String toolNameGitConfig = "custom_tool_git_config";
		String toolNameGitAttributes = "custom_tool_git_attributes";
		configureEchoTools(toolNameGitConfig, toolNameGitAttributes);
		preferenceNode.putInt(UIPreferences.MERGE_TOOL_MODE,
				MergeToolMode.GIT_CONFIG.ordinal());
		preferenceNode.put(UIPreferences.MERGE_TOOL_CUSTOM, toolNameGitConfig);

		String gitAttributesContents = "atttributes:" + "\n" + "*.txt" + "\t"
				+ "mergetool=" + toolNameGitAttributes;
		writeFolderGitAttributes(gitAttributesContents);

		createMergeConflict();
		triggerMergeToolAction();

		List<String> commandOutputLines = waitForToolOutput();

		String actualCommandOutput = String.join(System.lineSeparator(),
				commandOutputLines);
		String expectedOutputPattern = toolNameGitAttributes + " merged=.*.txt";
		boolean matchingOutput = Pattern.matches(expectedOutputPattern,
				actualCommandOutput);
		assertTrue("Command output doesn't match expected pattern: "
				+ expectedOutputPattern + ", command output: "
				+ actualCommandOutput, matchingOutput);
	}

	private void triggerMergeToolAction() {
		SWTBotTree packageExplorer = TestUtil.getExplorerTree();
		SWTBotTreeItem project1 = getProjectItem(packageExplorer, PROJ1)
				.select();

		SWTBotTreeItem folderNode = TestUtil.expandAndWait(project1)
				.getNode(FOLDER);
		SWTBotTreeItem fileNode = TestUtil.expandAndWait(folderNode)
				.getNode(FILE1);
		fileNode.select();
		ContextMenuHelper.clickContextMenu(packageExplorer,
				util.getPluginLocalizedValue("TeamMenu.label"),
				util.getPluginLocalizedValue("MergeToolAction.label"));
	}

	private void configureEchoTools(String defaultToolName,
			String... extraToolNames) throws Exception {
		configureTools(this::configureDefaultTool,
				this::configureEchoToolSubsection, defaultToolName,
				extraToolNames);
	}

	private void configureDefaultTool(String toolName, StoredConfig config) {
		String subsection = null;
		config.setString(CONFIG_MERGE_SECTION, subsection, CONFIG_KEY_TOOL,
				toolName);
	}

	private void configureEchoToolSubsection(String toolName,
			StoredConfig config) {
		String command = getEchoCommand(toolName);

		config.setString(CONFIG_MERGETOOL_SECTION, toolName, CONFIG_KEY_CMD,
				command);
		config.setString(CONFIG_MERGETOOL_SECTION, toolName,
				CONFIG_KEY_TRUST_EXIT_CODE, String.valueOf(Boolean.TRUE));
		config.setString(CONFIG_MERGETOOL_SECTION, toolName, CONFIG_KEY_PROMPT,
				String.valueOf(false));
	}

	private String getEchoCommand(String toolName) {
		return "(echo " + toolName + " merged=\"$MERGED\" > "
				+ resultFile.toAbsolutePath().toString() + "; exit 1)";
	}
}
