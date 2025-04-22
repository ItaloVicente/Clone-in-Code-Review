package org.eclipse.egit.ui.views.synchronize;

import static org.eclipse.swtbot.swt.finder.waits.Conditions.shellCloses;
import static org.eclipse.swtbot.swt.finder.waits.Conditions.shellIsActive;
import static org.junit.Assert.assertEquals;

import org.eclipse.egit.ui.common.EGitTesCase;
import org.eclipse.egit.ui.common.GitImportRepoWizard;
import org.eclipse.egit.ui.common.RepoPropertiesPage;
import org.eclipse.egit.ui.common.RepoRemoteBranchesPage;
import org.eclipse.egit.ui.test.ContextMenuHelper;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotEditor;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.junit.Before;
import org.junit.Test;

public class SynchronizeViewTest extends EGitTesCase {

	@Test
	public void shouldReturnNoChanges() throws Exception {
		changeFilesInProject();
		showSynchronizationDialog("org.eclipse.egit.ui");

		bot.shell("Synchronize repository: egit/.git").activate();

		bot.comboBox(0).setSelection(0); // select local
		bot.comboBox(1).setSelection(0); // select HEAD
		bot.comboBox(2).setSelection(0); // select local
		bot.comboBox(3).setSelection(1); // select master


		bot.button("OK").click();

		bot.waitUntil(shellIsActive("Confirm Open Perspective"), 15000);
		bot.button("No").click();

		SWTBotShell gitResSyncShell = bot.shell("Git Resource Synchronization");
		bot.waitUntil(shellCloses(gitResSyncShell), 6000000);

		SWTBotTree syncViewTree = bot.viewByTitle("Synchronize").bot().tree();
		assertEquals(0, syncViewTree.getAllItems().length);
	}

	@Test
	public void shouldReturnListOfChnages() throws Exception {
		changeFilesInProject();
		showSynchronizationDialog("org.eclipse.egit.ui");

		bot.shell("Synchronize repository: egit/.git").activate();

		bot.comboBox(0).setSelection(0); // select local
		bot.comboBox(1).setSelection(0); // select HEAD
		bot.comboBox(2).setSelection(0); // select local
		bot.comboBox(3).setSelection(1); // select master

		bot.checkBox("Include local uncommited changes in comparison").click();

		bot.button("OK").click();

		bot.waitUntil(shellIsActive("Confirm Open Perspective"), 45000);
		bot.button("No").click();

		SWTBotShell shell = bot.shell("Git Resource Synchronization");
		bot.waitUntil(shellCloses(shell), 6000000);

		SWTBotTree syncViewTree = bot.viewByTitle("Synchronize").bot().tree();
		assertEquals(2, syncViewTree.getAllItems().length);

		SWTBotTreeItem[] syncItems = syncViewTree.getAllItems();
		assertEquals("org.eclipse.egit.core", syncItems[0].getText());
		assertEquals("org.eclipse.egit.ui", syncItems[1].getText());

		syncItems[0].expand();
		syncItems[1].expand();

		assertEquals(1, syncItems[0].getNodes().size());
		assertEquals("pom.xml", syncItems[0].getNodes().get(0));
		assertEquals(1, syncItems[1].getNodes().size());
		assertEquals("pom.xml", syncItems[1].getNodes().get(0));
	}

	@Before
	public void setupViews() {
		bot.perspectiveById("org.eclipse.jdt.ui.JavaPerspective").activate();
		bot.viewByTitle("Package Explorer").show();

		if (bot.viewByTitle("Package Explorer").bot().tree().rowCount() < 8) {
			GitImportRepoWizard importWizard = new GitImportRepoWizard();

			importWizard.openWizard();
			addRepository(importWizard, "git://egit.eclipse.org/egit.git");

			importWizard.selectAndCloneRepository(0);
			importWizard.waitForCreate();
			waitForWorkspaceRefresh();
		}
	}

	private void addRepository(GitImportRepoWizard importWizard, String repoUrl) {
		RepoPropertiesPage propertiesPage = importWizard.openCloneWizard();

		RepoRemoteBranchesPage remoteBranches = propertiesPage
				.nextToRemoteBranches(repoUrl);
		remoteBranches.selectBranches("master");

		remoteBranches.nextToWorkingCopy().waitForCreate();
	}

	private void changeFilesInProject() throws Exception {
		SWTBot packageExplorerBot = bot.viewByTitle("Package Explorer").bot();
		packageExplorerBot.activeShell();
		SWTBotTree tree = packageExplorerBot.tree();

		SWTBotTreeItem coreTreeItem = tree.getAllItems()[3];
		coreTreeItem.expand();
		for (String node : coreTreeItem.getNodes()) {
			if (node.contains("pom.xml")) {
				coreTreeItem.getNode(node).doubleClick();
				break;
			}
		}

		SWTBotEditor corePomEditor = bot.editorByTitle("pom.xml");
		corePomEditor.toTextEditor()
				.insertText("<!-- EGit jUnit test case -->");
		corePomEditor.saveAndClose();
		coreTreeItem.collapse();

		SWTBotTreeItem uiTreeItem = tree.getAllItems()[6];
		uiTreeItem.expand();
		for (String node : uiTreeItem.getNodes()) {
			if (node.contains("pom.xml")) {
				uiTreeItem.getNode(node).doubleClick();
				break;
			}
		}

		SWTBotEditor uiPomEditor = bot.editorByTitle("pom.xml");
		uiPomEditor.toTextEditor().insertText("<!-- EGit jUnit test case -->");
		uiPomEditor.saveAndClose();
		uiTreeItem.collapse();
	}

	private void showSynchronizationDialog(String projectName) {
		SWTBot packageExplorerBot = bot.viewByTitle("Package Explorer").bot();
		packageExplorerBot.activeShell();
		SWTBotTree tree = packageExplorerBot.tree();

		for (SWTBotTreeItem item : tree.getAllItems()) {
			if (item.getText().contains(projectName)) {
				item.select();
				break;
			}
		}

		ContextMenuHelper.clickContextMenu(tree, "Team", "Synchronize...");
	}

}
