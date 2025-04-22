package org.eclipse.jgit.pgm;

import static org.eclipse.jgit.lib.ConfigConstants.CONFIG_KEY_CMD;
import static org.eclipse.jgit.lib.ConfigConstants.CONFIG_KEY_PROMPT;
import static org.eclipse.jgit.lib.ConfigConstants.CONFIG_KEY_TOOL;
import static org.eclipse.jgit.lib.ConfigConstants.CONFIG_MERGETOOL_SECTION;
import static org.eclipse.jgit.lib.ConfigConstants.CONFIG_MERGE_SECTION;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.jgit.internal.diffmergetool.CommandLineMergeTool;
import org.eclipse.jgit.lib.StoredConfig;
import org.junit.Before;
import org.junit.Test;

public class MergeToolTest extends ExternalToolTestCase {

	private static final String MERGE_TOOL = CONFIG_MERGETOOL_SECTION;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		configureEchoTool(TOOL_NAME);
	}

	@Test
	public void testTool() throws Exception {
		createMergeConflict();
		String[] expectedOutput = getExpectedToolOutput();

		String[] options = {
				"--tool"
				"-t"
		};

		for (String option : options) {
			assertArrayOfLinesEquals("Incorrect output for option: " + option
					expectedOutput
					runAndCaptureUsingInitRaw(MERGE_TOOL
							TOOL_NAME));
		}
	}

	@Test
	public void testToolNoGuiNoPrompt() throws Exception {
		createMergeConflict();
		String[] expectedOutput = getExpectedToolOutput();

		String[] options = { "--tool"

		for (String option : options) {
			assertArrayOfLinesEquals("Incorrect output for option: " + option
					expectedOutput
							"--no-gui"
		}
	}

	@Test
	public void testToolHelp() throws Exception {
		CommandLineMergeTool[] defaultTools = CommandLineMergeTool.values();
		List<String> expectedOutput = new ArrayList<>();
		expectedOutput.add(
				"'git mergetool --tool=<tool>' may be set to one of the following:");
		for (CommandLineMergeTool defaultTool : defaultTools) {
			String toolName = defaultTool.name();
			expectedOutput.add(toolName);
		}
		String customToolHelpLine = TOOL_NAME + "." + CONFIG_KEY_CMD + " "
				+ getEchoCommand();
		expectedOutput.add("user-defined:");
		expectedOutput.add(customToolHelpLine);
		String[] userDefinedToolsHelp = {
				"The following tools are valid
				"Some of the tools listed above only work in a windowed"
				"environment. If run in a terminal-only session
		};
		expectedOutput.addAll(Arrays.asList(userDefinedToolsHelp));

		String option = "--tool-help";
		assertArrayOfLinesEquals("Incorrect output for option: " + option
				expectedOutput.toArray(new String[0])
				runAndCaptureUsingInitRaw(MERGE_TOOL
	}

	private void configureEchoTool(String toolName) {
		StoredConfig config = db.getConfig();
		String subsection = null;
		config.setString(CONFIG_MERGE_SECTION
				toolName);

		String command = getEchoCommand();

		config.setString(CONFIG_MERGETOOL_SECTION
				command);
		config.setString(CONFIG_MERGETOOL_SECTION
				String.valueOf(false));
	}

	private String[] getExpectedToolOutput() {
		String[] mergeConflictFilenames = { "a"
		List<String> expectedOutput = new ArrayList<>();
		expectedOutput.add("Merging:");
		for (String mergeConflictFilename : mergeConflictFilenames) {
			expectedOutput.add(mergeConflictFilename);
		}
		for (String mergeConflictFilename : mergeConflictFilenames) {
			expectedOutput.add("Normal merge conflict for '"
					+ mergeConflictFilename + "':");
			expectedOutput.add("{local}: modified file");
			expectedOutput.add("{remote}: modified file");
			expectedOutput.add("TODO: Launch mergetool '" + TOOL_NAME
					+ "' for path '" + mergeConflictFilename + "'...");
		}
		return expectedOutput.toArray(new String[0]);
	}
}
