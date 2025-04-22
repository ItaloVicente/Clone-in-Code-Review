package org.eclipse.jgit.pgm;

import static org.eclipse.jgit.lib.ConfigConstants.CONFIG_KEY_CMD;
import static org.eclipse.jgit.lib.ConfigConstants.CONFIG_KEY_PROMPT;
import static org.eclipse.jgit.lib.ConfigConstants.CONFIG_KEY_TOOL;
import static org.eclipse.jgit.lib.ConfigConstants.CONFIG_MERGETOOL_SECTION;
import static org.eclipse.jgit.lib.ConfigConstants.CONFIG_MERGE_SECTION;
import static org.junit.Assert.fail;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.internal.diffmergetool.ExternalMergeTool;
import org.eclipse.jgit.internal.diffmergetool.MergeTools;
import org.eclipse.jgit.lib.StoredConfig;
import org.junit.Before;
import org.junit.Test;

public class MergeToolTest extends ToolTestCase {

	private static final String MERGE_TOOL = CONFIG_MERGETOOL_SECTION;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		configureEchoTool(TOOL_NAME);
	}

	@Test
	public void testUndefinedTool() throws Exception {
		String toolName = "undefined";
		String[] conflictingFilenames = createMergeConflict();

		List<String> expectedErrors = new ArrayList<>();
		for (String conflictingFilename : conflictingFilenames) {
			expectedErrors.add("External merge tool is not defined: " + toolName);
			expectedErrors.add("merge of " + conflictingFilename + " failed");
		}

		runAndCaptureUsingInitRaw(expectedErrors
				"--no-prompt"
	}

	@Test(expected = Die.class)
	public void testUserToolWithCommandNotFoundError() throws Exception {
		String toolName = "customTool";

		String command = "exit " + errorReturnCode;

		StoredConfig config = db.getConfig();
		config.setString(CONFIG_MERGETOOL_SECTION
				command);

		createMergeConflict();
		runAndCaptureUsingInitRaw(MERGE_TOOL
				toolName);

		fail("Expected exception to be thrown due to external tool exiting with error code: "
				+ errorReturnCode);
	}

	@Test
	public void testEmptyToolName() throws Exception {
		String emptyToolName = "";

		StoredConfig config = db.getConfig();
		String subsection = null;
		config.setString(CONFIG_MERGE_SECTION
				emptyToolName);

		createMergeConflict();

		String araxisErrorLine = "compare: unrecognized option `-wait' @ error/compare.c/CompareImageCommand/1123.";
		String[] expectedErrorOutput = { araxisErrorLine
		runAndCaptureUsingInitRaw(Arrays.asList(expectedErrorOutput)
				MERGE_TOOL
	}

	@Test
	public void testAbortMerge() throws Exception {
		String[] inputLines = {
				"y"
				"n"
				"n"
		};
		String[] conflictingFilenames = createMergeConflict();
		int abortIndex = 1;
		String[] expectedOutput = getExpectedAbortMergeOutput(
				conflictingFilenames
				abortIndex);

		String option = "--tool";

		InputStream inputStream = createInputStream(inputLines);
		assertArrayOfLinesEquals("Incorrect output for option: " + option
				expectedOutput
						MERGE_TOOL
	}

	@Test
	public void testAbortLaunch() throws Exception {
		String[] inputLines = {
				"n"
		};
		String[] conflictingFilenames = createMergeConflict();
		String[] expectedOutput = getExpectedAbortLaunchOutput(
				conflictingFilenames);

		String option = "--tool";

		InputStream inputStream = createInputStream(inputLines);
		assertArrayOfLinesEquals("Incorrect output for option: " + option
				expectedOutput
						MERGE_TOOL
	}

	@Test
	public void testMergeConflict() throws Exception {
		String[] inputLines = {
				"y"
				"y"
				"y"
				"y"
		};
		String[] conflictingFilenames = createMergeConflict();
		String[] expectedOutput = getExpectedMergeConflictOutput(
				conflictingFilenames);

		String option = "--tool";

		InputStream inputStream = createInputStream(inputLines);
		assertArrayOfLinesEquals("Incorrect output for option: " + option
				expectedOutput
						MERGE_TOOL
	}

	@Test
	public void testDeletedConflict() throws Exception {
		String[] inputLines = {
				"d"
				"m"
		};
		String[] conflictingFilenames = createDeletedConflict();
		String[] expectedOutput = getExpectedDeletedConflictOutput(
				conflictingFilenames);

		String option = "--tool";

		InputStream inputStream = createInputStream(inputLines);
		assertArrayOfLinesEquals("Incorrect output for option: " + option
				expectedOutput
						MERGE_TOOL
	}

	@Test
	public void testNoConflict() throws Exception {
		createStagedChanges();
		String[] expectedOutput = { "No files need merging" };

		String[] options = { "--tool"

		for (String option : options) {
			assertArrayOfLinesEquals("Incorrect output for option: " + option
					expectedOutput
					runAndCaptureUsingInitRaw(MERGE_TOOL
		}
	}

	@Test
	public void testMergeConflictNoPrompt() throws Exception {
		String[] conflictingFilenames = createMergeConflict();
		String[] expectedOutput = getExpectedMergeConflictOutputNoPrompt(
				conflictingFilenames);

		String option = "--tool";

		assertArrayOfLinesEquals("Incorrect output for option: " + option
				expectedOutput
				runAndCaptureUsingInitRaw(MERGE_TOOL
	}

	@Test
	public void testMergeConflictNoGuiNoPrompt() throws Exception {
		String[] conflictingFilenames = createMergeConflict();
		String[] expectedOutput = getExpectedMergeConflictOutputNoPrompt(
				conflictingFilenames);

		String option = "--tool";

		assertArrayOfLinesEquals("Incorrect output for option: " + option
				expectedOutput
						"--no-gui"
	}

	@Test
	public void testToolHelp() throws Exception {
		List<String> expectedOutput = new ArrayList<>();

		MergeTools diffTools = new MergeTools(db);
		Map<String
				.getPredefinedTools(true);
		List<ExternalMergeTool> availableTools = new ArrayList<>();
		List<ExternalMergeTool> notAvailableTools = new ArrayList<>();
		for (ExternalMergeTool tool : predefinedTools.values()) {
			if (tool.isAvailable()) {
				availableTools.add(tool);
			} else {
				notAvailableTools.add(tool);
			}
		}

		expectedOutput.add(
				"'git mergetool --tool=<tool>' may be set to one of the following:");
		for (ExternalMergeTool tool : availableTools) {
			String toolName = tool.getName();
			expectedOutput.add(toolName);
		}
		String customToolHelpLine = TOOL_NAME + "." + CONFIG_KEY_CMD + " "
				+ getEchoCommand();
		expectedOutput.add("user-defined:");
		expectedOutput.add(customToolHelpLine);
		expectedOutput.add(
				"The following tools are valid
		for (ExternalMergeTool tool : notAvailableTools) {
			String toolName = tool.getName();
			expectedOutput.add(toolName);
		}
		String[] userDefinedToolsHelp = {
				"Some of the tools listed above only work in a windowed"
				"environment. If run in a terminal-only session
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

	private String[] getExpectedMergeConflictOutputNoPrompt(
			String[] conflictFilenames) {
		List<String> expected = new ArrayList<>();
		expected.add("Merging:");
		for (String conflictFilename : conflictFilenames) {
			expected.add(conflictFilename);
		}
		for (String conflictFilename : conflictFilenames) {
			expected.add("Normal merge conflict for '" + conflictFilename
					+ "':");
			expected.add("{local}: modified file");
			expected.add("{remote}: modified file");
			Path filePath = getFullPath(conflictFilename);
			expected.add(filePath.toString());
			expected.add(conflictFilename + " seems unchanged.");
		}
		return expected.toArray(new String[0]);
	}

	private static String[] getExpectedAbortLaunchOutput(
			String[] conflictFilenames) {
		List<String> expected = new ArrayList<>();
		expected.add("Merging:");
		for (String conflictFilename : conflictFilenames) {
			expected.add(conflictFilename);
		}
		if (conflictFilenames.length > 1) {
			String conflictFilename = conflictFilenames[0];
			expected.add(
					"Normal merge conflict for '" + conflictFilename + "':");
			expected.add("{local}: modified file");
			expected.add("{remote}: modified file");
			expected.add("Hit return to start merge resolution tool ("
					+ TOOL_NAME + "):");
		}
		return expected.toArray(new String[0]);
	}

	private String[] getExpectedAbortMergeOutput(
			String[] conflictFilenames
		List<String> expected = new ArrayList<>();
		expected.add("Merging:");
		for (String conflictFilename : conflictFilenames) {
			expected.add(conflictFilename);
		}
		for (int i = 0; i < conflictFilenames.length; ++i) {
			if (i == abortIndex) {
				break;
			}

			String conflictFilename = conflictFilenames[i];
			expected.add(
					"Normal merge conflict for '" + conflictFilename + "':");
			expected.add("{local}: modified file");
			expected.add("{remote}: modified file");
			Path fullPath = getFullPath(conflictFilename);
			expected.add("Hit return to start merge resolution tool ("
					+ TOOL_NAME + "): " + fullPath);
			expected.add(conflictFilename + " seems unchanged.");
			expected.add("Was the merge successful [y/n]?");
			if (i < conflictFilenames.length - 1) {
				expected.add(
						"\tContinue merging other unresolved paths [y/n]?");
			}
		}
		return expected.toArray(new String[0]);
	}

	private String[] getExpectedMergeConflictOutput(
			String[] conflictFilenames) {
		List<String> expected = new ArrayList<>();
		expected.add("Merging:");
		for (String conflictFilename : conflictFilenames) {
			expected.add(conflictFilename);
		}
		for (int i = 0; i < conflictFilenames.length; ++i) {
			String conflictFilename = conflictFilenames[i];
			expected.add("Normal merge conflict for '" + conflictFilename
					+ "':");
			expected.add("{local}: modified file");
			expected.add("{remote}: modified file");
			Path filePath = getFullPath(conflictFilename);
			expected.add("Hit return to start merge resolution tool ("
					+ TOOL_NAME + "): " + filePath);
			expected.add(conflictFilename + " seems unchanged.");
			expected.add("Was the merge successful [y/n]?");
			if (i < conflictFilenames.length - 1) {
			}
		}
		return expected.toArray(new String[0]);
	}

	private static String[] getExpectedDeletedConflictOutput(
			String[] conflictFilenames) {
		List<String> expected = new ArrayList<>();
		expected.add("Merging:");
		for (String mergeConflictFilename : conflictFilenames) {
			expected.add(mergeConflictFilename);
		}
		for (int i = 0; i < conflictFilenames.length; ++i) {
			String conflictFilename = conflictFilenames[i];
			expected.add(conflictFilename + " seems unchanged.");
			expected.add("{local}: deleted");
			expected.add("{remote}: modified file");
			expected.add("Use (m)odified or (d)eleted file
		}
		return expected.toArray(new String[0]);
	}

	private static String getEchoCommand() {
		return "(echo \"$MERGED\")";
	}
}
