/*******************************************************************************
 * Copyright (C) 2010, Dariusz Luksza <dariusz@luksza.org>
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *******************************************************************************/
package org.eclipse.egit.ui.view.synchronize;

import static org.eclipse.egit.ui.UIText.SynchronizeWithAction_localRepoName;
import static org.eclipse.egit.ui.UIText.SynchronizeWithAction_tagsName;
import static org.eclipse.jgit.lib.Constants.HEAD;
import static org.eclipse.jgit.lib.Constants.MASTER;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotEditor;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotStyledText;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.junit.Ignore;
import org.junit.Test;

public class SynchronizeViewWorkspaceModelTest extends AbstractSynchronizeViewTest {

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
		assertTrue(syncItems[0].getText().contains(PROJ1));
	}

	@Test
	public void shouldCompareBranchAgainstTag() throws Exception {
		resetRepositoryToCreateInitialTag();
		makeChangesAndCommit(PROJ1);

		launchSynchronization(SynchronizeWithAction_tagsName, INITIAL_TAG,
				SynchronizeWithAction_localRepoName, HEAD, false);

		SWTBotTree syncViewTree = bot.viewByTitle("Synchronize").bot().tree();
		assertEquals(2, syncViewTree.getAllItems().length);
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

	@Test public void shouldOpenCompareEditor()
			throws Exception {
		resetRepositoryToCreateInitialTag();
		changeFilesInProject();

		launchSynchronization(null, null, SynchronizeWithAction_tagsName,
				INITIAL_TAG, true);

		SWTBot compare = getCompareEditorForFileInWorspaceModel(FILE1)
				.bot();
		assertNotNull(compare);
	}

	@Test public void shouldListFileDeletedChange() throws Exception {
		resetRepositoryToCreateInitialTag();
		deleteFileAndCommit(PROJ1);

		launchSynchronization(null, null, SynchronizeWithAction_tagsName,
				INITIAL_TAG, true);

		SWTBotTree syncViewTree = bot.viewByTitle("Synchronize").bot().tree();
		assertEquals(1, syncViewTree.getAllItems().length);

		SWTBotTreeItem projectTree = waitForNodeWithText(syncViewTree, PROJ1);
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
		SWTBotTreeItem projectTree = waitForNodeWithText(syncViewTree,
				EMPTY_PROJECT);
		assertEquals(1, syncViewTree.getAllItems().length);

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
		SWTBotEditor compEditor = getCompareEditorForFileInWorspaceModel(
				FILE1);
		SWTBot outgoingCompare = compEditor.bot();
		String outgoingLeft = outgoingCompare.styledText(0).getText();
		String outgoingRight = outgoingCompare.styledText(1).getText();
		compEditor.close();

		launchSynchronization(SynchronizeWithAction_tagsName, INITIAL_TAG,
				SynchronizeWithAction_localRepoName, HEAD, false);

		SWTBot incomingComp = getCompareEditorForFileInWorspaceModel(
				FILE1).bot();
		String incomingLeft = incomingComp.styledText(0).getText();
		String incomingRight = incomingComp.styledText(1).getText();
		assertThat(outgoingLeft, equalTo(incomingRight));
		assertThat(outgoingRight, equalTo(incomingLeft));
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

		SWTBotTree syncViewTree = bot.viewByTitle("Synchronize").bot().tree();
		SWTBotTreeItem projectTree = waitForNodeWithText(syncViewTree, PROJ1);
		projectTree.expand();
		assertEquals(1, projectTree.getItems().length);
	}

	public void shouldShowNonWorkspaceFileInSynchronization()
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
		SWTBotTreeItem workingTree = syncViewTree.expandNode(PROJ1);
		assertEquals(1, syncViewTree.getAllItems().length);
		assertEquals(1, workingTree.getNodes(name).size());
	}

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
		SWTBotTreeItem workingTree = syncViewTree.expandNode(PROJ1);
		assertEquals(1, syncViewTree.getAllItems().length);
		workingTree.expand().getNode(name).doubleClick();

		SWTBotEditor editor = bot.editorByTitle(name);
		editor.setFocus();

		SWTBotStyledText left = editor.bot().styledText(content);
		SWTBotStyledText right = editor.bot().styledText("");
		assertNotSame(left, right);
	}

}
