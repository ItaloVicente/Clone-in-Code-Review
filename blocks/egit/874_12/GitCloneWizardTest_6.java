package org.eclipse.egit.ui.view.synchronize;

import static org.eclipse.swtbot.swt.finder.waits.Conditions.shellCloses;
import static org.eclipse.swtbot.swt.finder.waits.Conditions.shellIsActive;
import static org.junit.Assert.assertEquals;

import java.io.File;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.egit.ui.common.EGitTestCase;
import org.eclipse.egit.ui.common.GitImportRepoWizard;
import org.eclipse.egit.ui.common.RepoPropertiesPage;
import org.eclipse.egit.ui.common.RepoRemoteBranchesPage;
import org.eclipse.egit.ui.test.ContextMenuHelper;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotEditor;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class SynchronizeViewTest extends EGitTestCase {

	private static boolean changePerspective = false;

	@Test
	public void shouldReturnNoChanges() throws Exception {
		changeFilesInProject();
		showSynchronizationDialog("org.eclipse.egit.ui");

		bot.shell("Synchronize repository: egit" + File.separator + ".git")
				.activate();

		bot.comboBox(0).setSelection("local .git");
		bot.comboBox(1).setSelection("HEAD");

		bot.comboBox(2).setSelection("local .git");
		bot.comboBox(3).setSelection("master");


		bot.button("OK").click();

		handleConfirmOpenPerspective();

		bot.waitUntil(shellIsActive("Git Resource Synchronization"), 15000);
		SWTBotShell gitResSyncShell = bot.shell("Git Resource Synchronization");
		bot.waitUntil(shellCloses(gitResSyncShell), 300000);

		SWTBotTree syncViewTree = bot.viewByTitle("Synchronize").bot().tree();
		assertEquals(0, syncViewTree.getAllItems().length);
	}

	@Test
	public void shouldReturnListOfChanges() throws Exception {
		changeFilesInProject();
		showSynchronizationDialog("org.eclipse.egit.ui");

		bot.shell("Synchronize repository: egit" + File.separator + ".git")
				.activate();

		bot.comboBox(0).setSelection("local .git");
		bot.comboBox(1).setSelection("HEAD");

		bot.comboBox(2).setSelection("local .git");
		bot.comboBox(3).setSelection("master");

		bot.checkBox("Include local uncommited changes in comparison").click();

		bot.button("OK").click();

		handleConfirmOpenPerspective();

		bot.waitUntil(shellIsActive("Git Resource Synchronization"), 15000);
		SWTBotShell shell = bot.shell("Git Resource Synchronization");
		bot.waitUntil(shellCloses(shell), 300000);

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

	@Test
	public void shouldCompareBranchAgainstTag() throws Exception {
		showSynchronizationDialog("org.eclipse.egit.ui");

		bot.shell("Synchronize repository: egit" + File.separator + ".git")
				.activate();

		bot.comboBox(0).setSelection("origin");
		bot.comboBox(1).setSelection("stable-0.7");

		bot.comboBox(2).setSelection("local .git");
		bot.comboBox(3).setSelection("v0.8.1");

		bot.button("OK").click();

		handleConfirmOpenPerspective();

		bot.waitUntil(shellIsActive("Git Resource Synchronization"), 15000);
		SWTBotShell shell = bot.shell("Git Resource Synchronization");
		bot.waitUntil(shellCloses(shell), 300000);

		SWTBotTree syncViewTree = bot.viewByTitle("Synchronize").bot().tree();
		assertEquals(8, syncViewTree.getAllItems().length);
	}

	@Test
	public void shouldCompareTagAgainstTag() throws Exception {
		showSynchronizationDialog("org.eclipse.egit.ui");

		bot.shell("Synchronize repository: egit" + File.separator + ".git")
				.activate();

		bot.comboBox(0).setSelection("local .git");
		bot.comboBox(1).setSelection("v0.7.0");

		bot.comboBox(2).setSelection("local .git");
		bot.comboBox(3).setSelection("v0.8.1");

		bot.button("OK").click();

		handleConfirmOpenPerspective();

		bot.waitUntil(shellIsActive("Git Resource Synchronization"), 15000);
		SWTBotShell shell = bot.shell("Git Resource Synchronization");
		bot.waitUntil(shellCloses(shell), 300000);

		SWTBotTree syncViewTree = bot.viewByTitle("Synchronize").bot().tree();
		assertEquals(8, syncViewTree.getAllItems().length);
	}

	@Test
	@Ignore
	public void shouldLaunchSynchronizationFromGitRepositories()
			throws Exception {
		bot.menu("Window").menu("Show View").menu("Other...").click();
		bot.shell("Show View").bot().tree().expandNode("Git").getNode(
				"Git Repositories").doubleClick();

		SWTBotTree repositoriesTree = bot.viewByTitle("Git Repositories").bot()
				.tree();
		SWTBotTreeItem egitRoot = repositoriesTree.getAllItems()[0];
		egitRoot.expand();
		egitRoot.collapse();
		egitRoot.expand();
		SWTBotTreeItem remoteBranch = egitRoot.expandNode("Branches")
				.expandNode("Remote Branches");
		SWTBotTreeItem branchNode = remoteBranch.getNode("origin/stable-0.7");
		branchNode.select();
		branchNode.contextMenu("Synchronize").click();

		handleConfirmOpenPerspective();

		bot.waitUntil(shellIsActive("Git Resource Synchronization"), 15000);
		SWTBotShell shell = bot.shell("Git Resource Synchronization");
		bot.waitUntil(shellCloses(shell), 300000);


		SWTBotTree syncViewTree = bot.viewByTitle("Synchronize").bot().tree();
		assertEquals(8, syncViewTree.getAllItems().length);
	}

	@Before
	public void setupViews() {
		bot.perspectiveById("org.eclipse.jdt.ui.JavaPerspective").activate();
		bot.viewByTitle("Package Explorer").show();
	}

	@BeforeClass
	public static void setupEnvironment() throws Exception {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceDescription description = workspace.getDescription();
		description.setAutoBuilding(false);
		workspace.setDescription(description);

		GitImportRepoWizard importWizard = new GitImportRepoWizard();

		importWizard.openWizard();
		String repoName = "egit";
		String repoUrl = "git://egit.eclipse.org/egit.git";
		if (!importWizard.containsRepo(repoName))
			addRepository(importWizard, repoUrl);

		importWizard.selectAndCloneRepository(repoName);
		importWizard.waitForCreate();
		waitForWorkspaceRefresh();
	}

	@AfterClass
	public static void restoreEnvironmentSetup() throws Exception {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceDescription description = workspace.getDescription();
		description.setAutoBuilding(true);
		workspace.setDescription(description);
	}

	private static void addRepository(GitImportRepoWizard importWizard,
			String repoUrl) {
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

	private void handleConfirmOpenPerspective() {
		if (!changePerspective) {
			changePerspective = true;
			bot.waitUntil(shellIsActive("Confirm Open Perspective"), 15000);
			bot.checkBox("Remember my decision").click();
			bot.button("No").click();
		}
	}

}
