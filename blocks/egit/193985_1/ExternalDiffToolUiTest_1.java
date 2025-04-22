package org.eclipse.egit.ui.internal.external.tools;

import static org.eclipse.jgit.lib.ConfigConstants.CONFIG_DIFFTOOL_SECTION;
import static org.eclipse.jgit.lib.ConfigConstants.CONFIG_DIFF_SECTION;
import static org.eclipse.jgit.lib.ConfigConstants.CONFIG_KEY_CMD;
import static org.eclipse.jgit.lib.ConfigConstants.CONFIG_KEY_PROMPT;
import static org.eclipse.jgit.lib.ConfigConstants.CONFIG_KEY_TOOL;
import static org.eclipse.jgit.lib.ConfigConstants.CONFIG_KEY_TRUST_EXIT_CODE;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.regex.Pattern;

import org.eclipse.egit.ui.UIPreferences;
import org.eclipse.egit.ui.internal.diffmerge.DiffToolMode;
import org.eclipse.egit.ui.test.ContextMenuHelper;
import org.eclipse.egit.ui.test.TestUtil;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ExternalDiffToolUiTest extends ExternalToolUiTestCase {

	private String previousPerExtensionPreference;

	private String previousDiffToolCustomPreference;

	private int previousDiffToolModePreference;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		previousPerExtensionPreference = preferenceNode
				.get(UIPreferences.EXTERNAL_DIFF_TOOL_FOR_EXTENSION, "");
		previousDiffToolCustomPreference = preferenceNode
				.get(UIPreferences.DIFF_TOOL_CUSTOM, "");
		previousDiffToolModePreference = preferenceNode.getInt(
				UIPreferences.DIFF_TOOL_MODE, DiffToolMode.INTERNAL.ordinal());
	}

	@Override
	@After
	public void tearDown() throws Exception {
		try {
			preferenceNode.put(UIPreferences.EXTERNAL_DIFF_TOOL_FOR_EXTENSION,
					previousPerExtensionPreference);
			preferenceNode.put(UIPreferences.DIFF_TOOL_CUSTOM,
					previousDiffToolCustomPreference);
			preferenceNode.putInt(UIPreferences.DIFF_TOOL_MODE,
					previousDiffToolModePreference);
		} finally {
			super.tearDown();
		}
	}

	@Test
	public void testExternalDiffToolPreference() throws Exception {
		assumePosixPlatform();

		preferenceNode.putInt(UIPreferences.DIFF_TOOL_MODE,
				DiffToolMode.EXTERNAL_FOR_TYPE.ordinal());
		String command = "(echo test_command local=\"$LOCAL\" remote=\"$REMOTE\" > "
				+ resultFile.toAbsolutePath().toString() + ")";
		preferenceNode.put(UIPreferences.EXTERNAL_DIFF_TOOL_FOR_EXTENSION,
				"txt," + command);

		createMergeConflict();
		triggerCompareWithPreviousAction();

		List<String> commandOutputLines = waitForToolOutput();

		String actualCommandOutput = String.join(System.lineSeparator(),
				commandOutputLines);
		String expectedOutputPattern = "test_command local=.*.txt remote=.*.txt";
		boolean matchingOutput = Pattern.matches(expectedOutputPattern,
				actualCommandOutput);
		assertTrue("Command output doesn't match expected pattern: "
				+ expectedOutputPattern + ", command output: "
				+ actualCommandOutput, matchingOutput);
	}

	@Test
	public void testExternalDiffToolGitConfig() throws Exception {
		assumePosixPlatform();

		createMergeConflict();

		int runs = 2;
		for (int i = 0; i < runs; ++i) {
			clearResultFile();

			String toolName = "custom_tool" + i;
			configureEchoTools(toolName);
			preferenceNode.putInt(UIPreferences.DIFF_TOOL_MODE,
					DiffToolMode.GIT_CONFIG.ordinal());
			preferenceNode.put(UIPreferences.DIFF_TOOL_CUSTOM, toolName);

			triggerCompareWithPreviousAction();

			List<String> commandOutputLines = waitForToolOutput();

			String actualCommandOutput = String.join(System.lineSeparator(),
					commandOutputLines);
			String expectedOutputPattern = toolName
					+ " local=.*.txt remote=.*.txt";
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
		preferenceNode.putInt(UIPreferences.DIFF_TOOL_MODE,
				DiffToolMode.GIT_CONFIG.ordinal());
		preferenceNode.put(UIPreferences.DIFF_TOOL_CUSTOM, toolNameGitConfig);

		String gitAttributesContents = "atttributes:" + "\n" + "*.txt" + "\t"
				+ "difftool=" + toolNameGitAttributes;
		writeFolderGitAttributes(gitAttributesContents);

		createMergeConflict();
		triggerCompareWithPreviousAction();

		List<String> commandOutputLines = waitForToolOutput();

		String actualCommandOutput = String.join(System.lineSeparator(),
				commandOutputLines);
		String expectedOutputPattern = toolNameGitAttributes
				+ " local=.*.txt remote=.*.txt";
		boolean matchingOutput = Pattern.matches(expectedOutputPattern,
				actualCommandOutput);
		assertTrue("Command output doesn't match expected pattern: "
				+ expectedOutputPattern + ", command output: "
				+ actualCommandOutput, matchingOutput);
	}

	private void triggerCompareWithPreviousAction() {
		SWTBotTree packageExplorer = TestUtil.getExplorerTree();
		SWTBotTreeItem project1 = getProjectItem(packageExplorer, PROJ1)
				.select();

		SWTBotTreeItem folderNode = TestUtil.expandAndWait(project1)
				.getNode(FOLDER);
		SWTBotTreeItem fileNode = TestUtil.expandAndWait(folderNode)
				.getNode(FILE1);
		fileNode.select();
		ContextMenuHelper.clickContextMenu(packageExplorer,
				util.getPluginLocalizedValue("CompareWithMenu.label"),
				util.getPluginLocalizedValue(
						"CompareWithPreviousAction.label"));
	}

	private void configureEchoTools(String defaultToolName,
			String... extraToolNames) throws Exception {
		configureTools(this::configureDefaultTool,
				this::configureEchoToolSubsection, defaultToolName,
				extraToolNames);
	}

	private void configureDefaultTool(String defaultToolName,
			StoredConfig config) {
		String subsection = null;
		config.setString(CONFIG_DIFF_SECTION, subsection, CONFIG_KEY_TOOL,
				defaultToolName);
	}

	private void configureEchoToolSubsection(String toolName,
			StoredConfig config) {
		String command = getEchoCommand(toolName);

		config.setString(CONFIG_DIFFTOOL_SECTION, toolName, CONFIG_KEY_CMD,
				command);
		config.setString(CONFIG_DIFFTOOL_SECTION, toolName,
				CONFIG_KEY_TRUST_EXIT_CODE, String.valueOf(Boolean.TRUE));
		config.setString(CONFIG_DIFFTOOL_SECTION, toolName, CONFIG_KEY_PROMPT,
				String.valueOf(false));
	}

	private String getEchoCommand(String toolName) {
		return "(echo " + toolName + "  local=\"$LOCAL\" remote=\"$REMOTE\" > "
				+ resultFile.toAbsolutePath().toString() + ")";
	}
}
