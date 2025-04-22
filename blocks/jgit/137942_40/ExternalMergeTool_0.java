package org.eclipse.jgit.internal.diffmergetool;

import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.Set;

import org.eclipse.jgit.lib.internal.BooleanTriState;
import org.eclipse.jgit.storage.file.FileBasedConfig;
import org.junit.Test;

public class ExternalMergeToolTest extends ExternalToolTestCase {

	@Test
	public void testToolNames() {
		MergeTools manager = new MergeTools(db);
		Set<String> actualToolNames = manager.getToolNames();
		Set<String> expectedToolNames = Collections.emptySet();
		assertEquals("Incorrect set of external diff tool names"
				expectedToolNames
	}

	@Test
	public void testAllTools() {
		MergeTools manager = new MergeTools(db);
		Set<String> actualToolNames = manager.getAvailableTools().keySet();
		Set<String> expectedToolNames = Collections.emptySet();
		assertEquals("Incorrect set of available external diff tools"
				expectedToolNames
	}

	@Test
	public void testUserDefinedTools() {
		MergeTools manager = new MergeTools(db);
		Set<String> actualToolNames = manager.getUserDefinedTools().keySet();
		Set<String> expectedToolNames = Collections.emptySet();
		assertEquals("Incorrect set of user defined external diff tools"
				expectedToolNames
	}

	@Test
	public void testNotAvailableTools() {
		MergeTools manager = new MergeTools(db);
		Set<String> actualToolNames = manager.getNotAvailableTools().keySet();
		Set<String> expectedToolNames = Collections.emptySet();
		assertEquals("Incorrect set of not available external diff tools"
				expectedToolNames
	}

	@Test
	public void testCompare() throws ToolException {
		MergeTools manager = new MergeTools(db);

		String newPath = "";
		String oldPath = "";
		String newId = "";
		String oldId = "";
		String toolName = "";
		BooleanTriState prompt = BooleanTriState.UNSET;
		BooleanTriState gui = BooleanTriState.UNSET;
		BooleanTriState trustExitCode = BooleanTriState.UNSET;

		int expectedCompareResult = 0;
		int compareResult = manager.merge(newPath
				toolName
		assertEquals("Incorrect compare result for external diff tool"
				expectedCompareResult
	}

	@Test
	public void testDefaultTool() throws Exception {
		FileBasedConfig config = db.getConfig();
		String subsection = null;
		config.setString("diff"

		MergeTools manager = new MergeTools(db);
		BooleanTriState gui = BooleanTriState.UNSET;
		String defaultToolName = manager.getDefaultToolName(gui);
		assertEquals(
				"Expected configured difftool to be the default external diff tool"
				"my_default_toolname"

		gui = BooleanTriState.TRUE;
		String defaultGuiToolName = manager.getDefaultToolName(gui);
		assertEquals(
				"Expected configured difftool to be the default external diff tool"
				"my_gui_tool"

		config.setString("diff"
		manager = new MergeTools(db);
		defaultGuiToolName = manager.getDefaultToolName(gui);
		assertEquals(
				"Expected configured difftool to be the default external diff guitool"
				"my_gui_tool"
	}
}
