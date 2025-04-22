package org.eclipse.egit.ui.test.history;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.egit.ui.UIText;
import org.eclipse.egit.ui.common.LocalRepositoryTestCase;
import org.eclipse.egit.ui.test.ContextMenuHelper;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotPerspective;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTable;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotToolbarToggleButton;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(SWTBotJunit4ClassRunner.class)
public class HistoryViewTest extends LocalRepositoryTestCase {
	private static final String SECONDFOLDER = "secondFolder";

	private static final String ADDEDFILE = "another.txt";

	private static final String ADDEDMESSAGE = "A new file in a new folder";

	private static SWTBotPerspective perspective;

	private static int commitCount;

	@BeforeClass
	public static void setup() throws Exception {
		createProjectAndCommitToRepository();
		perspective = bot.activePerspective();
		bot.perspectiveById("org.eclipse.pde.ui.PDEPerspective").activate();
		IProject prj = ResourcesPlugin.getWorkspace().getRoot().getProject(
				PROJ1);
		IFolder folder2 = prj.getFolder(SECONDFOLDER);
		folder2.create(false, true, null);
		IFile addedFile = folder2.getFile(ADDEDFILE);
		addedFile.create(new ByteArrayInputStream("More content".getBytes(prj
				.getDefaultCharset())), false, null);
		addAndCommit(addedFile, ADDEDMESSAGE);
		commitCount = 3;
		waitInUI();
	}

	@AfterClass
	public static void shutdown() {
		perspective.activate();
	}

	@Test
	public void testOpenHistoryOnFileNoFilter() throws Exception {
		initFilter(0);

		assertEquals("Wrong number of commits", commitCount,
				getHistoryViewTable(PROJ1).rowCount());
		assertEquals("Wrong number of commits", commitCount - 1,
				getHistoryViewTable(PROJ1, FOLDER).rowCount());
		assertEquals("Wrong number of commits", commitCount - 1,
				getHistoryViewTable(PROJ1, FOLDER, FILE1).rowCount());
		assertEquals("Wrong number of commits", 1, getHistoryViewTable(PROJ1,
				FOLDER, FILE2).rowCount());
		assertEquals("Wrong number of commits", 1, getHistoryViewTable(PROJ1,
				SECONDFOLDER).rowCount());
		assertEquals("Wrong number of commits", 1, getHistoryViewTable(PROJ1,
				SECONDFOLDER, ADDEDFILE).rowCount());
		assertEquals("Wrong number of commits", 1, getHistoryViewTable(PROJ2)
				.rowCount());

		assertEquals("Wrong commit message", ADDEDMESSAGE, getHistoryViewTable(
				PROJ1, SECONDFOLDER, ADDEDFILE).getTableItem(0).getText(0));
		assertEquals("Wrong commit message", "Initial commit",
				getHistoryViewTable(PROJ1, FOLDER, FILE2).getTableItem(0)
						.getText(0));
	}

	@Test
	public void testOpenHistoryOnFileRepoFilter() throws Exception {
		initFilter(1);
		assertEquals("Wrong number of commits", commitCount,
				getHistoryViewTable(PROJ1).rowCount());
		assertEquals("Wrong number of commits", commitCount,
				getHistoryViewTable(PROJ1, FOLDER).rowCount());
		assertEquals("Wrong number of commits", commitCount,
				getHistoryViewTable(PROJ1, FOLDER, FILE1).rowCount());
		assertEquals("Wrong number of commits", commitCount,
				getHistoryViewTable(PROJ1, FOLDER, FILE2).rowCount());
		assertEquals("Wrong number of commits", commitCount,
				getHistoryViewTable(PROJ1, SECONDFOLDER).rowCount());
		assertEquals("Wrong number of commits", commitCount,
				getHistoryViewTable(PROJ1, SECONDFOLDER, ADDEDFILE).rowCount());
		assertEquals("Wrong number of commits", commitCount,
				getHistoryViewTable(PROJ2).rowCount());
	}

	@Test
	public void testOpenHistoryOnFileProjectFilter() throws Exception {
		initFilter(2);
		assertEquals("Wrong number of commits", commitCount,
				getHistoryViewTable(PROJ1).rowCount());
		assertEquals("Wrong number of commits", commitCount,
				getHistoryViewTable(PROJ1, FOLDER).rowCount());
		assertEquals("Wrong number of commits", commitCount,
				getHistoryViewTable(PROJ1, FOLDER, FILE1).rowCount());
		assertEquals("Wrong number of commits", commitCount,
				getHistoryViewTable(PROJ1, FOLDER, FILE2).rowCount());
		assertEquals("Wrong number of commits", commitCount,
				getHistoryViewTable(PROJ1, SECONDFOLDER).rowCount());
		assertEquals("Wrong number of commits", commitCount,
				getHistoryViewTable(PROJ1, SECONDFOLDER, ADDEDFILE).rowCount());
		assertEquals("Wrong number of commits", 1, getHistoryViewTable(PROJ2)
				.rowCount());
	}

	@Test
	public void testOpenHistoryOnFileFolderFilter() throws Exception {
		initFilter(3);
		assertEquals("Wrong number of commits", commitCount,
				getHistoryViewTable(PROJ1).rowCount());
		assertEquals("Wrong number of commits", commitCount,
				getHistoryViewTable(PROJ1, FOLDER).rowCount());
		assertEquals("Wrong number of commits", commitCount - 1,
				getHistoryViewTable(PROJ1, FOLDER, FILE1).rowCount());
		assertEquals("Wrong number of commits", commitCount - 1,
				getHistoryViewTable(PROJ1, FOLDER, FILE2).rowCount());
		assertEquals("Wrong number of commits", commitCount,
				getHistoryViewTable(PROJ1, SECONDFOLDER).rowCount());
		assertEquals("Wrong number of commits", 1, getHistoryViewTable(PROJ1,
				SECONDFOLDER, ADDEDFILE).rowCount());
		assertEquals("Wrong number of commits", commitCount,
				getHistoryViewTable(PROJ2).rowCount());
	}

	private void initFilter(int filter) {
		getHistoryViewTable(PROJ1);
		SWTBotView view = bot
				.viewById("org.eclipse.team.ui.GenericHistoryView");
		SWTBotToolbarToggleButton folder = (SWTBotToolbarToggleButton) view
				.toolbarButton(UIText.HistoryPage_ShowAllVersionsForFolder);
		SWTBotToolbarToggleButton project = (SWTBotToolbarToggleButton) view
				.toolbarButton(UIText.HistoryPage_ShowAllVersionsForProject);
		SWTBotToolbarToggleButton repo = (SWTBotToolbarToggleButton) view
				.toolbarButton(UIText.HistoryPage_ShowAllVersionsForRepo);
		switch (filter) {
		case 0:
			if (folder.isChecked())
				folder.click();
			if (project.isChecked())
				project.click();
			if (repo.isChecked())
				repo.click();
			break;
		case 1:
			if (!repo.isChecked())
				repo.click();
			break;
		case 2:
			if (!project.isChecked())
				project.click();
			break;
		case 3:
			if (!folder.isChecked())
				folder.click();
			break;
		default:
			break;
		}
	}

	@Test
	public void testOpenHistoryOnProject() throws Exception {
		SWTBotTable table = getHistoryViewTable(PROJ1);
		int rowCount = table.rowCount();
		assertTrue(table.rowCount() > 0);
		assertEquals(table.getTableItem(rowCount - 1).getText(0),
				"Initial commit");
	}

	@Test
	public void testAddCommit() throws Exception {
		String commitMessage = "The special commit";
		int countBefore = getHistoryViewTable(PROJ1).rowCount();
		touchAndSubmit(commitMessage);
		waitInUI();
		int countAfter = getHistoryViewTable(PROJ1).rowCount();
		assertEquals("Wrong number of entries", countBefore + 1, countAfter);
		assertEquals("Wrong comit message", commitMessage, getHistoryViewTable(
				PROJ1).getTableItem(0).getText(0));
	}

	private SWTBotTable getHistoryViewTable(String... path) {
		SWTBotTree projectExplorerTree = bot.viewById(
				"org.eclipse.jdt.ui.PackageExplorer").bot().tree();
		if (path.length == 1)
			getProjectItem(projectExplorerTree, path[0]).select();
		else if (path.length == 2)
			getProjectItem(projectExplorerTree, path[0]).expand().getNode(
					path[1]).select();
		else
			getProjectItem(projectExplorerTree, path[0]).expand().getNode(
					path[1]).expand().getNode(path[2]).select();
		ContextMenuHelper.clickContextMenu(projectExplorerTree, "Show In",
				"History");
		return bot.viewById("org.eclipse.team.ui.GenericHistoryView").bot()
				.table();
	}
}
