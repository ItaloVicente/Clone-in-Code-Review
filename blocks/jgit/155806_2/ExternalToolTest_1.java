package org.eclipse.jgit.diffmergetool;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.Assume.assumeTrue;

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
		assumePosixPlatform();

		int errorReturnCode = 1;
		String command = "exit " + errorReturnCode;

		FileBasedConfig config = db.getConfig();
		config.setString("mergetool"

		MergeToolManager manager = new MergeToolManager(db);
		Map<String
		ExternalMergeTool externalTool = tools.get("customTool");
		manager.merge(local
		fail("Expected exception to be thrown due to external tool exiting with error code");
	}

	@Test
	public void testAllTools() {
		FileBasedConfig config = db.getConfig();
		config.setString("mergetool"

		MergeToolManager manager = new MergeToolManager(db);
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

		MergeToolManager manager = new MergeToolManager(db);
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
		assumePosixPlatform();

		String command = "echo \"$LOCAL\n$REMOTE\n$MERGED\n$BASE\" &> "
				+ commandResult.getAbsolutePath();

		FileBasedConfig config = db.getConfig();
		config.setString("mergetool"

		MergeToolManager manager = new MergeToolManager(db);
		Map<String
		ExternalMergeTool externalTool = tools.get("customTool");
		manager.merge(local

		List<String> actualLines = Files.readAllLines(commandResult.toPath());
		List<String> expectedLines = Arrays.asList(localFile.getAbsolutePath()
				remoteFile.getAbsolutePath()
				baseFile.getAbsolutePath());
		assertEquals("Dummy test tool called with unexpected arguments"
				expectedLines
	}

	@Test
	public void testUserDefinedToolWithPrompt() throws Exception {
		assumePosixPlatform();

		String command = "echo \"$LOCAL\n$REMOTE\n$MERGED\n$BASE\" &> "
				+ commandResult.getAbsolutePath();

		FileBasedConfig config = db.getConfig();
		config.setString("mergetool"

		MergeToolManager manager = new MergeToolManager(db);

		PromptHandler promptHandler = new PromptHandler();
		MissingToolHandler noToolHandler = new MissingToolHandler();

		manager.merge(local
				Optional.of("customTool")
				promptHandler

		List<String> actualLines = Files.readAllLines(commandResult.toPath());
		List<String> expectedLines = Arrays.asList(localFile.getAbsolutePath()
				remoteFile.getAbsolutePath()
				baseFile.getAbsolutePath());
		assertEquals("Dummy test tool called with unexpected arguments"
				expectedLines

		List<String> actualToolPrompts = promptHandler.toolPrompts;
		List<String> expectedToolPrompts = Arrays.asList("customTool");
		assertEquals("Expected a user prompt for custom tool call"
				expectedToolPrompts

		assertEquals("Expected to no informing about missing tools"
				Collections.EMPTY_LIST
	}

	private void assumeMergeToolIsAvailable(
			CommandLineMergeTool autoMergingTool) {
		boolean isAvailable = ExternalToolUtils.isToolAvailable(db.getFS()
				db.getDirectory()
		assumeTrue("Assuming external tool is available: "
				+ autoMergingTool.name()
	}
}
