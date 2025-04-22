/*******************************************************************************
 * Copyright (C) 2010, Dariusz Luksza <dariusz@luksza.org>
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *******************************************************************************/
package org.eclipse.egit.ui.view.synchronize;

import static org.eclipse.egit.ui.UIText.CommitAction_commit;
import static org.eclipse.egit.ui.UIText.CommitDialog_Commit;
import static org.eclipse.egit.ui.UIText.CommitDialog_CommitChanges;
import static org.eclipse.egit.ui.UIText.CommitDialog_SelectAll;
import static org.eclipse.egit.ui.UIText.GitModelWorkingTree_workingTree;
import static org.eclipse.egit.ui.UIText.SynchronizeWithAction_localRepoName;
import static org.eclipse.egit.ui.UIText.SynchronizeWithAction_tagsName;
import static org.eclipse.egit.ui.test.ContextMenuHelper.clickContextMenu;
import static org.eclipse.egit.ui.test.TestUtil.waitUntilTreeHasNodeContainsText;
import static org.eclipse.jgit.lib.Constants.HEAD;
import static org.eclipse.jgit.lib.Constants.MASTER;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertThat;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.egit.core.op.ConnectProviderOperation;
import org.eclipse.egit.core.op.ResetOperation;
import org.eclipse.egit.core.op.ResetOperation.ResetType;
import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.JobFamilies;
import org.eclipse.egit.ui.UIText;
import org.eclipse.egit.ui.common.LocalRepositoryTestCase;
import org.eclipse.egit.ui.test.Eclipse;
import org.eclipse.egit.ui.test.TestUtil;
import org.eclipse.jface.bindings.keys.KeyStroke;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepository;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotEditor;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.waits.Conditions;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotRadio;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotStyledText;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotToolbarDropDownButton;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.eclipse.team.ui.synchronize.ISynchronizeManager;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class SynchronizeViewTest extends LocalRepositoryTestCase {

	private static final String INITIAL_TAG = "initial-tag";

	private static final String TEST_COMMIT_MSG = "test commit";

	private static final String EMPTY_PROJECT = "EmptyProject";

	private static final String EMPTY_REPOSITORY = "EmptyRepository";

	private static File repositoryFile;

	@Test
	public void shouldReturnNoChanges() throws Exception {
		resetRepositoryToCreateInitialTag();
		changeFilesInProject();

		launchSynchronization(SynchronizeWithAction_localRepoName, HEAD,
				SynchronizeWithAction_localRepoName, MASTER, false);

		SWTBotTree syncViewTree = bot.viewByTitle("Synchronize").bot().tree();
		assertEquals(0, syncViewTree.getAllItems().length);
	}

	@Test
	public void shouldReturnListOfChanges() throws Exception {
		resetRepositoryToCreateInitialTag();
		changeFilesInProject();

		launchSynchronization(null, null, SynchronizeWithAction_localRepoName,
				HEAD, true);

		SWTBotTree syncViewTree = bot.viewByTitle("Synchronize").bot().tree();
		SWTBotTreeItem[] syncItems = syncViewTree.getAllItems();
		assertEquals(GitModelWorkingTree_workingTree, syncItems[0].getText());
	}

	@Test
	public void shouldCompareBranchAgainstTag() throws Exception {
		resetRepositoryToCreateInitialTag();
		makeChangesAndCommit(PROJ1);

		launchSynchronization(SynchronizeWithAction_tagsName, INITIAL_TAG,
				SynchronizeWithAction_localRepoName, HEAD, false);

		SWTBotTree syncViewTree = bot.viewByTitle("Synchronize").bot().tree();
		assertEquals(1, syncViewTree.getAllItems().length);
	}

	@Test
	public void shouldCompareTagAgainstTag() throws Exception {
		resetRepositoryToCreateInitialTag();
		makeChangesAndCommit(PROJ1);
		createTag(PROJ1, "v0.1");

		launchSynchronization(SynchronizeWithAction_tagsName, INITIAL_TAG,
				SynchronizeWithAction_tagsName, "v0.1", false);

		SWTBotTree syncViewTree = bot.viewByTitle("Synchronize").bot().tree();
		assertEquals(1, syncViewTree.getAllItems().length);
	}

	@Test
	public void shouldOpenCompareEditorInGitChangeSet() throws Exception {
		resetRepositoryToCreateInitialTag();
		changeFilesInProject();

		launchSynchronization(null, null, SynchronizeWithAction_tagsName,
				INITIAL_TAG, true);

		SWTBot compare = getCompareEditorForFileInGitChangeSet(FILE1, true)
				.bot();
		assertNotNull(compare);
	}

	@Test public void shouldOpenCompareEditorInWorkspaceModel()
			throws Exception {
		resetRepositoryToCreateInitialTag();
		changeFilesInProject();

		launchSynchronization(null, null, SynchronizeWithAction_tagsName,
				INITIAL_TAG, true);

		SWTBot compare = getCompareEditorForFileInWorkspaceModel().bot();
		assertNotNull(compare);
	}

	@Test public void shouldListFileDeletedChange() throws Exception {
		resetRepositoryToCreateInitialTag();
		deleteFileAndCommit(PROJ1);

		launchSynchronization(null, null, SynchronizeWithAction_tagsName,
				INITIAL_TAG, true);

		SWTBotTree syncViewTree = bot.viewByTitle("Synchronize").bot().tree();
		assertEquals(1, syncViewTree.getAllItems().length);

		SWTBotTreeItem commitTree = waitForNodeWithText(syncViewTree,
				TEST_COMMIT_MSG);
		SWTBotTreeItem projectTree = waitForNodeWithText(commitTree, PROJ1);
		assertEquals(1, projectTree.getItems().length);

		SWTBotTreeItem folderTree = waitForNodeWithText(projectTree, FOLDER);
		assertEquals(1, folderTree.getItems().length);

		SWTBotTreeItem fileTree = folderTree.getItems()[0];
		assertEquals("test.txt", fileTree.getText());
	}

	@Test public void shouldSynchronizeInEmptyRepository() throws Exception {
		createEmptyRepository();

		launchSynchronization(EMPTY_REPOSITORY, EMPTY_PROJECT, null, null,
				null, null, true);

		SWTBotTree syncViewTree = bot.viewByTitle("Synchronize").bot().tree();
		SWTBotTreeItem commitTree = waitForNodeWithText(syncViewTree,
				GitModelWorkingTree_workingTree);
		assertEquals(1, syncViewTree.getAllItems().length);
		SWTBotTreeItem projectTree = waitForNodeWithText(commitTree,
				EMPTY_PROJECT);
		assertEquals(2, projectTree.getItems().length);

		SWTBotTreeItem folderTree = waitForNodeWithText(projectTree, FOLDER);
		assertEquals(2, folderTree.getItems().length);

		SWTBotTreeItem fileTree = folderTree.getItems()[0];
		assertEquals(FILE1, fileTree.getText());
		fileTree = folderTree.getItems()[1];
		assertEquals(FILE2, fileTree.getText());
	}

	@Test public void shouldExchangeCompareEditorSidesBetweenIncomingAndOutgoingChangesInGitChangeSet()
			throws Exception {
		resetRepositoryToCreateInitialTag();
		makeChangesAndCommit(PROJ1);

		launchSynchronization(SynchronizeWithAction_localRepoName, HEAD,
				SynchronizeWithAction_tagsName, INITIAL_TAG, false);
		SWTBotEditor outgoingCompare = getCompareEditorForFileInGitChangeSet(
				FILE1, false);
		SWTBot outgoingCompareBot = outgoingCompare.bot();
		String outgoingLeft = outgoingCompareBot.styledText(0).getText();
		String outgoingRight = outgoingCompareBot.styledText(1).getText();
		outgoingCompare.close();

		launchSynchronization(SynchronizeWithAction_tagsName, INITIAL_TAG,
				SynchronizeWithAction_localRepoName, HEAD, false);

		SWTBot incomingComp = getCompareEditorForFileInGitChangeSet(
				FILE1, false).bot();
		assertThat(outgoingLeft, equalTo(incomingComp.styledText(1).getText()));
		assertThat(outgoingRight, equalTo(incomingComp.styledText(0).getText()));
	}

	@Test public void shouldExchangeCompareEditorSidesBetweenIncomingAndOutgoingChangesInWorkspaceModel()
			throws Exception {
		resetRepositoryToCreateInitialTag();
		makeChangesAndCommit(PROJ1);

		launchSynchronization(SynchronizeWithAction_localRepoName, HEAD,
				SynchronizeWithAction_tagsName, INITIAL_TAG, false);
		SWTBotEditor compEditor = getCompareEditorForFileInWorkspaceModel();
		SWTBot outgoingCompare = compEditor.bot();
		String outgoingLeft = outgoingCompare.styledText(0).getText();
		String outgoingRight = outgoingCompare.styledText(1).getText();
		compEditor.close();

		launchSynchronization(SynchronizeWithAction_tagsName, INITIAL_TAG,
				SynchronizeWithAction_localRepoName, HEAD, false);

		SWTBot incomingComp = getCompareEditorForFileInWorkspaceModel()
				.bot();
		String incomingLeft = incomingComp.styledText(0).getText();
		String incomingRight = incomingComp.styledText(1).getText();
		assertThat(outgoingLeft, equalTo(incomingRight));
		assertThat(outgoingRight, equalTo(incomingLeft));
	}

	@Test public void shouldNotShowIgnoredFilesInGitChangeSetModel()
			throws Exception {
		resetRepositoryToCreateInitialTag();
		String ignoredName = "to-be-ignored.txt";

		IProject proj = ResourcesPlugin.getWorkspace().getRoot()
				.getProject(PROJ1);

		IFile ignoredFile = proj.getFile(ignoredName);
		ignoredFile.create(new ByteArrayInputStream("content of ignored file"
				.getBytes(proj.getDefaultCharset())), false, null);

		IFile gitignore = proj.getFile(".gitignore");
		gitignore.create(
				new ByteArrayInputStream(ignoredName.getBytes(proj
						.getDefaultCharset())), false, null);
		proj.refreshLocal(IResource.DEPTH_INFINITE, null);

		launchSynchronization(SynchronizeWithAction_tagsName, INITIAL_TAG,
				SynchronizeWithAction_localRepoName, HEAD, true);

		SWTBotTree syncViewTree = bot.viewByTitle("Synchronize").bot().tree();
		bot.waitUntil(Conditions.treeHasRows(syncViewTree, syncViewTree.rowCount()), 20000);

		syncViewTree.expandNode(UIText.GitModelWorkingTree_workingTree);
		bot.waitUntil(Conditions.treeHasRows(syncViewTree, syncViewTree.rowCount()), 20000);
		assertEquals(1, syncViewTree.getAllItems().length);

		SWTBotTreeItem proj1Node = syncViewTree.getAllItems()[0];
		bot.waitUntil(Conditions.treeHasRows(syncViewTree, syncViewTree.rowCount()), 20000);
		proj1Node.getItems()[0].expand();
		assertEquals(1, proj1Node.getItems()[0].getItems().length);

		syncViewTree = setPresentationModel("Workspace").tree();
		SWTBotTreeItem projectTree = waitForNodeWithText(syncViewTree, PROJ1);
		projectTree.expand();
		assertEquals(1, projectTree.getItems().length);
	}

	@Test public void shouldShowNonWorkspaceFileInSynchronization()
			throws Exception {
		String name = "non-workspace.txt";
		File root = new File(getTestDirectory(), REPO1);
		File nonWorkspace = new File(root, name);
		BufferedWriter writer = new BufferedWriter(new FileWriter(nonWorkspace));
		writer.append("file content");
		writer.close();

		launchSynchronization(SynchronizeWithAction_tagsName, INITIAL_TAG,
				SynchronizeWithAction_localRepoName, HEAD, true);

		SWTBotTree syncViewTree = bot.viewByTitle("Synchronize").bot().tree();
		SWTBotTreeItem workingTree = syncViewTree
				.expandNode(UIText.GitModelWorkingTree_workingTree);
		assertEquals(1, syncViewTree.getAllItems().length);
		assertEquals(1, workingTree.getNodes(name).size());
	}

	@Test
	public void shouldShowCompareEditorForNonWorkspaceFileFromSynchronization()
			throws Exception {
		String content = "file content";
		String name = "non-workspace.txt";
		File root = new File(getTestDirectory(), REPO1);
		File nonWorkspace = new File(root, name);
		BufferedWriter writer = new BufferedWriter(new FileWriter(nonWorkspace));
		writer.append(content);
		writer.close();

		launchSynchronization(SynchronizeWithAction_tagsName, INITIAL_TAG,
				SynchronizeWithAction_localRepoName, HEAD, true);

		SWTBotTree syncViewTree = bot.viewByTitle("Synchronize").bot().tree();
		SWTBotTreeItem workingTree = syncViewTree
				.expandNode(UIText.GitModelWorkingTree_workingTree);
		assertEquals(1, syncViewTree.getAllItems().length);
		workingTree.expand().getNode(name).doubleClick();

		SWTBotEditor editor = bot.editorByTitle(name);

		SWTBotStyledText left = editor.bot().styledText(content);
		SWTBotStyledText right = editor.bot().styledText("");
		assertNotSame(left, right);
	}

	@Ignore
	@Test
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
		new Eclipse().openPreferencePage(null);
		bot.tree().getTreeItem("Team").expand().select();
		SWTBotRadio syncPerspectiveCheck = bot.radio("Never");
		if (!syncPerspectiveCheck.isSelected())
			syncPerspectiveCheck.click();
		bot.comboBox(0).setSelection("None");

		bot.comboBox().setSelection("None");

		bot.button(IDialogConstants.OK_LABEL).click();

		repositoryFile = createProjectAndCommitToRepository();
		createChildRepository(repositoryFile);
		Activator.getDefault().getRepositoryUtil()
				.addConfiguredRepository(repositoryFile);

		bot.perspectiveById("org.eclipse.jdt.ui.JavaPerspective").activate();
		bot.viewByTitle("Package Explorer").show();

		createTag(PROJ1, INITIAL_TAG);
	}

	@AfterClass
	public static void restoreEnvironmentSetup() throws Exception {
		new Eclipse().reset();
	}

	private void changeFilesInProject() throws Exception {
		SWTBot packageExlBot = bot.viewByTitle("Package Explorer").bot();
		SWTBotTreeItem coreTreeItem = selectProject(PROJ1, packageExlBot.tree());
		SWTBotTreeItem rootNode = coreTreeItem.expand().getNode(0)
				.expand().select();
		rootNode.getNode(0).select().doubleClick();

		SWTBotEditor corePomEditor = bot.editorByTitle(FILE1);
		corePomEditor.toTextEditor()
				.insertText("<!-- EGit jUnit test case -->");
		corePomEditor.saveAndClose();

		rootNode.getNode(1).select().doubleClick();
		SWTBotEditor uiPomEditor = bot.editorByTitle(FILE2);
		uiPomEditor.toTextEditor().insertText("<!-- EGit jUnit test case -->");
		uiPomEditor.saveAndClose();
		coreTreeItem.collapse();
	}

	private void resetRepositoryToCreateInitialTag() throws Exception {
		ResetOperation rop = new ResetOperation(
				lookupRepository(repositoryFile), Constants.R_TAGS +
						INITIAL_TAG, ResetType.HARD);
		rop.execute(new NullProgressMonitor());
	}

	private static void createTag(String projectName, String tagName)
			throws Exception {
		showDialog(projectName, "Team", "Tag...");

		bot.shell("Create new tag").bot().activeShell();
		bot.text(0).setFocus();
		bot.text(0).setText(tagName);
		bot.styledText(0).setFocus();
		bot.styledText(0).setText(tagName);
		bot.button(IDialogConstants.OK_LABEL).click();
		TestUtil.joinJobs(JobFamilies.TAG);
	}

	private void makeChangesAndCommit(String projectName) throws Exception {
		changeFilesInProject();
		commit(projectName);
	}

	private void deleteFileAndCommit(String projectName) throws Exception {
		ResourcesPlugin.getWorkspace().getRoot().getProject(PROJ1)
				.getFile(new Path("folder/test.txt")).delete(true, null);

		commit(projectName);
	}

	private void commit(String projectName) throws InterruptedException {
		showDialog(projectName, "Team", CommitAction_commit);

		bot.shell(CommitDialog_CommitChanges).bot().activeShell();
		bot.styledText(0).setText(TEST_COMMIT_MSG);
		bot.button(CommitDialog_SelectAll).click();
		bot.button(CommitDialog_Commit).click();
		TestUtil.joinJobs(JobFamilies.COMMIT);
	}

	private static void showDialog(String projectName, String... cmd) {
		SWTBot packageExplorerBot = bot.viewByTitle("Package Explorer").bot();
		packageExplorerBot.activeShell();
		SWTBotTree tree = packageExplorerBot.tree();

		selectProject(projectName, tree);

		clickContextMenu(tree, cmd);
	}

	private static SWTBotTreeItem selectProject(String projectName,
			SWTBotTree tree) {
		for (SWTBotTreeItem item : tree.getAllItems()) {
			if (item.getText().contains(projectName)) {
				item.select();
				return item;
			}
		}

		throw new RuntimeException("Poject with name " + projectName +
				" was not found in given tree");
	}

	private void launchSynchronization(String srcRepo, String srcRef,
			String dstRepo, String dstRef, boolean includeLocal) throws InterruptedException{
		launchSynchronization(REPO1, PROJ1, srcRepo, srcRef, dstRepo, dstRef,
				includeLocal);
	}

	private void launchSynchronization(String repo, String projectName,
			String srcRepo, String srcRef, String dstRepo, String dstRef,
			boolean includeLocal) throws InterruptedException {
		showDialog(projectName, "Team", "Synchronize...");

		bot.shell("Synchronize repository: " + repo + File.separator + ".git");

		if (!includeLocal)
			bot.checkBox(
					UIText.SelectSynchronizeResourceDialog_includeUncommitedChanges)
					.click();

		if (!includeLocal && srcRepo != null)
			bot.comboBox(0)
					.setSelection(srcRepo);
		if (!includeLocal && srcRef != null)
			bot.comboBox(1).setSelection(srcRef);

		if (dstRepo != null)
			bot.comboBox(2)
					.setSelection(dstRepo);
		if (dstRef != null)
			bot.comboBox(3).setSelection(dstRef);

		bot.button(IDialogConstants.OK_LABEL).click();

		Job.getJobManager().join(ISynchronizeManager.FAMILY_SYNCHRONIZE_OPERATION, null);
	}

	private SWTBot setPresentationModel(String model) throws Exception {
		SWTBotView syncView = bot.viewByTitle("Synchronize");
		SWTBotToolbarDropDownButton dropDown = syncView
				.toolbarDropDownButton("Show File System Resources");
		dropDown.menuItem(model).click();
		dropDown.pressShortcut(KeyStroke.getInstance("ESC"));

		return syncView.bot();
	}

	private void createEmptyRepository() throws Exception {
		File gitDir = new File(new File(getTestDirectory(), EMPTY_REPOSITORY),
				Constants.DOT_GIT);
		Repository myRepository = new FileRepository(gitDir);
		myRepository.create();

		IProject firstProject = ResourcesPlugin.getWorkspace().getRoot()
				.getProject(EMPTY_PROJECT);

		if (firstProject.exists())
			firstProject.delete(true, null);
		IProjectDescription desc = ResourcesPlugin.getWorkspace()
				.newProjectDescription(EMPTY_PROJECT);
		desc.setLocation(new Path(new File(myRepository.getWorkTree(),
				EMPTY_PROJECT).getPath()));
		firstProject.create(desc, null);
		firstProject.open(null);

		IFolder folder = firstProject.getFolder(FOLDER);
		folder.create(false, true, null);
		IFile textFile = folder.getFile(FILE1);
		textFile.create(new ByteArrayInputStream("Hello, world"
				.getBytes(firstProject.getDefaultCharset())), false, null);
		IFile textFile2 = folder.getFile(FILE2);
		textFile2.create(new ByteArrayInputStream("Some more content"
				.getBytes(firstProject.getDefaultCharset())), false, null);

		new ConnectProviderOperation(firstProject, gitDir).execute(null);
	}

	private SWTBotEditor getCompareEditorForFileInGitChangeSet(String fileName,
			boolean includeLocalChanges) {
		SWTBotTree syncViewTree = bot.viewByTitle("Synchronize").bot().tree();

		SWTBotTreeItem rootTree;
		if (includeLocalChanges)
			rootTree = waitForNodeWithText(syncViewTree,
					GitModelWorkingTree_workingTree);
		else
			rootTree = waitForNodeWithText(syncViewTree, TEST_COMMIT_MSG);

		SWTBotTreeItem projNode = waitForNodeWithText(rootTree, PROJ1);
		SWTBotTreeItem folderNode = waitForNodeWithText(projNode, FOLDER);
		waitForNodeWithText(folderNode, fileName).doubleClick();

		return bot.editorByTitle(fileName);
	}

	private SWTBotTreeItem waitForNodeWithText(SWTBotTree tree, String name) {
		waitUntilTreeHasNodeContainsText(bot, tree, name, 10000);
		return getTreeItemContainingText(tree.getAllItems(), name).expand();
	}

	private SWTBotTreeItem waitForNodeWithText(SWTBotTreeItem tree, String name) {
		waitUntilTreeHasNodeContainsText(bot, tree, name, 15000);
		return getTreeItemContainingText(tree.getItems(), name).expand();
	}

	private SWTBotTreeItem getTreeItemContainingText(SWTBotTreeItem[] items,
			String text) {
		for (SWTBotTreeItem item : items)
			if (item.getText().contains(text))
				return item;

		throw new WidgetNotFoundException(
					"Tree item elment containing text: test commit was not found");
	}

	private SWTBotEditor getCompareEditorForFileInWorkspaceModel()
			throws Exception {
		SWTBotTree syncViewTree = setPresentationModel("Workspace").tree();
		SWTBotTreeItem projectTree = waitForNodeWithText(syncViewTree, PROJ1);
		SWTBotTreeItem folderTree = waitForNodeWithText(projectTree, FOLDER);
		waitForNodeWithText(folderTree, FILE1).doubleClick();

		return bot.editorByTitle(FILE1);
	}

}
