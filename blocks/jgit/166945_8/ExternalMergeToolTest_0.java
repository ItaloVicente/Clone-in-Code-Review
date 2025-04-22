package org.eclipse.jgit.internal.diffmergetool;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.eclipse.jgit.junit.TestRepository;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileBasedConfig;
import org.eclipse.jgit.util.FS.ExecutionResult;
import org.junit.Test;

public class ExternalDiffToolTest extends ExternalToolTest {

	@Test(expected = ToolException.class)
	public void testUserToolWithError() throws Exception {
		int errorReturnCode = 1;
		String command = "exit " + errorReturnCode;

		FileBasedConfig config = db.getConfig();
		config.setString("difftool"

		DiffTools manager = new DiffTools(db);

		Map<String
		ExternalDiffTool externalTool = tools.get("customTool");
		boolean trustExitCode = true;
		manager.compare(local
		fail("Expected exception to be thrown due to external tool exiting with error code");
	}

	@Test
	public void testAllTools() {
		FileBasedConfig config = db.getConfig();
		config.setString("difftool"

		DiffTools manager = new DiffTools(db);
		Set<String> actualToolNames = manager.getAllToolNames();
		Set<String> expectedToolNames = new LinkedHashSet<>();
		expectedToolNames.add("customTool");
		CommandLineDiffTool[] defaultTools = CommandLineDiffTool.values();
		for (CommandLineDiffTool defaultTool : defaultTools) {
			String toolName = defaultTool.name();
			expectedToolNames.add(toolName);
		}
		assertEquals("Incorrect set of external diff tools"
				actualToolNames);
	}

	@Test
	public void testUserDefinedTool() throws Exception {
		String command = getEchoCommand();

		FileBasedConfig config = db.getConfig();
		config.setString("difftool"

		DiffTools manager = new DiffTools(db);

		Map<String
		ExternalDiffTool externalTool = tools.get("customTool");
		boolean trustExitCode = true;
		manager.compare(local

		assertEchoCommandHasCorrectOutput();
	}

	@Test
	public void testUserDefinedToolWithPrompt() throws Exception {
		String command = getEchoCommand();

		FileBasedConfig config = db.getConfig();
		config.setString("difftool"

		DiffTools manager = new DiffTools(db);

		PromptHandler promptHandler = PromptHandler.acceptPrompt();
		MissingToolHandler noToolHandler = new MissingToolHandler();

		manager.compare(local
				Optional.of(Boolean.TRUE)
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
		DiffTools manager = new DiffTools(db);

		PromptHandler promptHandler = PromptHandler.cancelPrompt();
		MissingToolHandler noToolHandler = new MissingToolHandler();

		Optional<ExecutionResult> result = manager.compare(local
				Optional.empty()
				Optional.of(Boolean.TRUE)
		assertFalse("Expected no result if user cancels the operation"
				result.isPresent());
	}

	@Test
	public void testDefaultTool() throws Exception {
		FileBasedConfig config = db.getConfig();
		String subsection = null;
		config.setString("diff"

		DiffTools manager = new DiffTools(db);
		boolean gui = false;
		String defaultToolName = manager.getDefaultToolName(gui);
		assertEquals(
				"Expected configured difftool to be the default external diff tool"
				"customTool"

		gui = true;
		String defaultGuiToolName = manager.getDefaultToolName(gui);
		assertEquals(
				"Expected configured difftool to be the default external diff tool"
				"customTool"

		config.setString("diff"
		manager = new DiffTools(db);
		defaultGuiToolName = manager.getDefaultToolName(gui);
		assertEquals(
				"Expected configured difftool to be the default external diff guitool"
				"customGuiTool"
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
				Optional.of(Boolean.TRUE)
				promptHandler

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
			testRepository.git().add().addFilepattern(localFile.getName()).call();

			testRepository.git().add().addFilepattern(".gitattributes").call();

			testRepository.branch("master").commit().message("first commit")
					.create();

			DiffTools manager = new DiffTools(db);
			Optional<String> tool = manager.getExternalToolFromAttributes(localFile.getName());
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
				Optional.of(Boolean.TRUE)
	}

	private String getEchoCommand() {
		return "(echo $LOCAL $REMOTE) > " + commandResult.getAbsolutePath();
	}

	private void assertEchoCommandHasCorrectOutput() throws IOException {
		List<String> actualLines = Files.readAllLines(commandResult.toPath());
		String actualContent = String.join(System.lineSeparator()
		actualLines = Arrays.asList(actualContent.split(" "));
		List<String> expectedLines = Arrays.asList(localFile.getAbsolutePath()
				remoteFile.getAbsolutePath());
		assertEquals("Dummy test tool called with unexpected arguments"
				expectedLines
	}
}
