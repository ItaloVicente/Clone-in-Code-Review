package org.eclipse.jgit.diffmergetool;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
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

import org.eclipse.jgit.storage.file.FileBasedConfig;
import org.eclipse.jgit.util.FS.ExecutionResult;
import org.junit.Test;

public class ExternalMergeToolTest extends ExternalToolTest {


	@Test(expected = ToolException.class)
	public void testUserToolWithError() throws Exception {
		int errorReturnCode = 1;
		String command = "exit " + errorReturnCode;

		FileBasedConfig config = db.getConfig();
		config.setString("mergetool"

		MergeTools manager = new MergeTools(db);
		Map<String
		ExternalMergeTool externalTool = tools.get("customTool");
		manager.merge(local
		fail("Expected exception to be thrown due to external tool exiting with error code");
	}

	@Test
	public void testAllTools() {
		FileBasedConfig config = db.getConfig();
		config.setString("mergetool"

		MergeTools manager = new MergeTools(db);
		Set<String> actualToolNames = manager.getAllToolNames();
		Set<String> expectedToolNames = new LinkedHashSet<>();
		expectedToolNames.add("customTool");
		CommandLineMergeTool[] defaultTools = CommandLineMergeTool.values();
		for (CommandLineMergeTool defaultTool : defaultTools) {
			String toolName = defaultTool.name();
			expectedToolNames.add(toolName);
		}
		assertEquals("Incorrect set of external merge tools"
				actualToolNames);
	}

	@Test
	public void testKdiff3() throws Exception {
		assumePosixPlatform();

		CommandLineMergeTool autoMergingTool = CommandLineMergeTool.kdiff3;
		assumeMergeToolIsAvailable(autoMergingTool);

		CommandLineMergeTool tool = autoMergingTool;
		PreDefinedMergeTool externalTool = new PreDefinedMergeTool(
				tool.name()
				tool.getParameters(false)
				BooleanOption.toConfigured(tool.isExitCodeTrustable()));

				MergeTools manager = new MergeTools(db);
		ExecutionResult result = manager.merge(local
				null
		assertEquals("Expected merge tool to succeed"
				result.getRc());

		List<String> actualLines = Files
				.readAllLines(mergedFile.toPath());
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
		String command = getEchoCommand();

		FileBasedConfig config = db.getConfig();
		config.setString("mergetool"

		MergeTools manager = new MergeTools(db);
		Map<String
		ExternalMergeTool externalTool = tools.get("customTool");
		manager.merge(local

		assertEchoCommandHasCorrectOutput();
	}

	@Test
	public void testUserDefinedToolWithPrompt() throws Exception {
		String command = getEchoCommand();

		FileBasedConfig config = db.getConfig();
		config.setString("mergetool"

		MergeTools manager = new MergeTools(db);

		PromptHandler promptHandler = PromptHandler.acceptPrompt();
		MissingToolHandler noToolHandler = new MissingToolHandler();

		manager.merge(local
				Optional.of("customTool")
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
				Optional.of(Boolean.TRUE)
		assertFalse("Expected no result if user cancels the operation"
				result.isPresent());
	}

	@Test
	public void testDefaultTool() throws Exception {
		FileBasedConfig config = db.getConfig();
		String subsection = null;
		config.setString("merge"
		config.setString("merge"

		MergeTools manager = new MergeTools(db);
		boolean gui = false;
		String defaultToolName = manager.getDefaultToolName(gui);
		assertEquals(
				"Expected configured difftool to be the default external merge tool"
				"customTool"

		gui = true;
		String defaultGuiToolName = manager.getDefaultToolName(gui);
		assertEquals(
				"Expected configured difftool to be the default external merge tool"
				"customTool"

		config.setString("merge"
		manager = new MergeTools(db);
		defaultGuiToolName = manager.getDefaultToolName(gui);
		assertEquals(
				"Expected configured difftool to be the default external merge guitool"
				"customGuiTool"
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
				Optional.empty()
				promptHandler

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

		ExternalMergeTool tool = null;
		manager.merge(local
	}

	@Test(expected = ToolException.class)
	public void testNullToolWithPrompt() throws Exception {
		MergeTools manager = new MergeTools(db);

		PromptHandler promptHandler = PromptHandler.cancelPrompt();
		MissingToolHandler noToolHandler = new MissingToolHandler();

		Optional<String> tool = null;

		manager.merge(local
				Optional.of(Boolean.TRUE)
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

	private void assumeMergeToolIsAvailable(
			CommandLineMergeTool autoMergingTool) {
		boolean isAvailable = ExternalToolUtils.isToolAvailable(db.getFS()
				db.getDirectory()
		assumeTrue("Assuming external tool is available: "
				+ autoMergingTool.name()
	}
}
