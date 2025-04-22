/*******************************************************************************
 * Copyright (C) 2010, Dariusz Luksza <dariusz@luksza.org>
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *******************************************************************************/
package org.eclipse.egit.ui.view.synchronize;

import static org.eclipse.egit.ui.UIText.GitModelWorkingTree_workingTree;
import static org.eclipse.egit.ui.UIText.SynchronizeWithAction_localRepoName;
import static org.eclipse.egit.ui.UIText.SynchronizeWithAction_tagsName;
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
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.egit.ui.UIText;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotEditor;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotStyledText;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.junit.Ignore;
import org.junit.Test;

public class SynchronizeViewGitChangeSetModelTest extends
		AbstractSynchronizeViewTest {

	@Test
	public void shouldReturnNoChanges() throws Exception {
		resetRepositoryToCreateInitialTag();
		changeFilesInProject();

		launchSynchronization(SynchronizeWithAction_localRepoName, HEAD,
				SynchronizeWithAction_localRepoName, MASTER, false);
		setGitChangeSetPresentationModel();

		SWTBotTree syncViewTree = bot.viewByTitle("Synchronize").bot().tree();
		assertEquals(0, syncViewTree.getAllItems().length);
	}

	@Test
	public void shouldReturnListOfChanges() throws Exception {
		resetRepositoryToCreateInitialTag();
		changeFilesInProject();

		launchSynchronization(null, null, SynchronizeWithAction_localRepoName,
				HEAD, true);
		setGitChangeSetPresentationModel();

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
		setGitChangeSetPresentationModel();

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
		setGitChangeSetPresentationModel();

		SWTBotTree syncViewTree = bot.viewByTitle("Synchronize").bot().tree();
		assertEquals(1, syncViewTree.getAllItems().length);
	}

	@Test
	public void shouldOpenCompareEditor() throws Exception {
		resetRepositoryToCreateInitialTag();
		changeFilesInProject();

		launchSynchronization(null, null, SynchronizeWithAction_tagsName,
				INITIAL_TAG, true);
		setGitChangeSetPresentationModel();

		SWTBot compare = getCompareEditorForFileInGitChangeSet(FILE1, true)
				.bot();
		assertNotNull(compare);
	}

	@Test public void shouldListFileDeletedChange() throws Exception {
		resetRepositoryToCreateInitialTag();
		deleteFileAndCommit(PROJ1);

		launchSynchronization(null, null, SynchronizeWithAction_tagsName,
				INITIAL_TAG, true);
		setGitChangeSetPresentationModel();

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
		setGitChangeSetPresentationModel();

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

	@Test public void shouldExchangeCompareEditorSidesBetweenIncomingAndOutgoingChanges()
			throws Exception {
		resetRepositoryToCreateInitialTag();
		makeChangesAndCommit(PROJ1);

		launchSynchronization(SynchronizeWithAction_localRepoName, HEAD,
				SynchronizeWithAction_tagsName, INITIAL_TAG, false);
		setGitChangeSetPresentationModel();
		SWTBotEditor outgoingCompare = getCompareEditorForFileInGitChangeSet(
				FILE1, false);
		SWTBot outgoingCompareBot = outgoingCompare.bot();
		String outgoingLeft = outgoingCompareBot.styledText(0).getText();
		String outgoingRight = outgoingCompareBot.styledText(1).getText();
		outgoingCompare.close();

		launchSynchronization(SynchronizeWithAction_tagsName, INITIAL_TAG,
				SynchronizeWithAction_localRepoName, HEAD, false);
		setGitChangeSetPresentationModel();

		SWTBot incomingComp = getCompareEditorForFileInGitChangeSet(
				FILE1, false).bot();
		assertThat(outgoingLeft, equalTo(incomingComp.styledText(1).getText()));
		assertThat(outgoingRight, equalTo(incomingComp.styledText(0).getText()));
	}

	@Test public void shouldNotShowIgnoredFiles()
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
		setGitChangeSetPresentationModel();

		SWTBotTree syncViewTree = bot.viewByTitle("Synchronize").bot().tree();
		syncViewTree.expandNode(UIText.GitModelWorkingTree_workingTree);
		assertEquals(1, syncViewTree.getAllItems().length);
		SWTBotTreeItem proj1Node = syncViewTree.getAllItems()[0];
		proj1Node.getItems()[0].expand();
		assertEquals(1, proj1Node.getItems()[0].getItems().length);
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
		setGitChangeSetPresentationModel();

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
		setGitChangeSetPresentationModel();

		SWTBotTree syncViewTree = bot.viewByTitle("Synchronize").bot().tree();
		SWTBotTreeItem workingTree = syncViewTree
				.expandNode(UIText.GitModelWorkingTree_workingTree);
		assertEquals(1, syncViewTree.getAllItems().length);
		workingTree.expand().getNode(name).doubleClick();

		SWTBotEditor editor = bot.editorByTitle(name);
		editor.setFocus();

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

}
