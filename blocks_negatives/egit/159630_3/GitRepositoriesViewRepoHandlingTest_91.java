/*******************************************************************************
 * Copyright (c) 2012, 2016 Matthias Sohn <matthias.sohn@sap.com> and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Thomas Wolf <thomas.wolf@paranor.ch> - Bugs 479964, 483664
 *******************************************************************************/
package org.eclipse.egit.ui.view.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.egit.core.internal.indexdiff.IndexDiffCache;
import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.JobFamilies;
import org.eclipse.egit.ui.UIPreferences;
import org.eclipse.egit.ui.internal.RepositoryCacheRule;
import org.eclipse.egit.ui.internal.UIText;
import org.eclipse.egit.ui.test.ContextMenuHelper;
import org.eclipse.egit.ui.test.TestUtil;
import org.eclipse.jgit.api.SubmoduleAddCommand;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotCheckBox;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * SWTBot Tests for the Git Repositories View (repository deletion)
 */
@RunWith(SWTBotJunit4ClassRunner.class)
public class GitRepositoriesViewRepoDeletionTest extends
		GitRepositoriesViewTestBase {

	private static final String DELETE_REPOSITORY_CONTEXT_MENU_LABEL = "RepoViewDeleteRepository.label";

	private static final String REMOVE_REPOSITORY_FROM_VIEW_CONTEXT_MENU_LABEL = "RepoViewRemove.label";

	private File repositoryFile;

	@Before
	public void before() throws Exception {
		repositoryFile = createProjectAndCommitToRepository();
	}

	@Test
	public void testDeleteRepositoryWithContentOk() throws Exception {
		deleteAllProjects();
		assertProjectExistence(PROJ1, false);
		clearView();
		Activator.getDefault().getRepositoryUtil().addConfiguredRepository(
				repositoryFile);
		shareProjects(repositoryFile);
		assertProjectExistence(PROJ1, true);
		refreshAndWait();
		assertHasRepo(repositoryFile);
		SWTBotTree tree = getOrOpenView().bot().tree();
		tree.getAllItems()[0].select();
		ContextMenuHelper.clickContextMenu(tree, myUtil
				.getPluginLocalizedValue(DELETE_REPOSITORY_CONTEXT_MENU_LABEL));
		SWTBotShell shell = bot.shell(UIText.DeleteRepositoryConfirmDialog_DeleteRepositoryWindowTitle);
		shell.activate();
		shell.bot()
				.checkBox(
						UIText.DeleteRepositoryConfirmDialog_DeleteGitDirCheckbox)
				.select();
		shell.bot()
				.checkBox(
						UIText.DeleteRepositoryConfirmDialog_DeleteWorkingDirectoryCheckbox)
				.select();
		shell.bot().button(
				UIText.DeleteRepositoryConfirmDialog_DeleteRepositoryConfirmButton)
				.click();
		TestUtil.joinJobs(JobFamilies.REPOSITORY_DELETE);

		refreshAndWait();
		assertEmpty();
		assertProjectExistence(PROJ1, false);
		assertFalse(repositoryFile.exists());
		assertFalse(new File(repositoryFile.getParentFile(), PROJ1).exists());
		assertFalse(repositoryFile.getParentFile().exists());
	}

	@Test
	public void testDeleteRepositoryKeepProjectsBug479964() throws Exception {
		deleteAllProjects();
		assertProjectExistence(PROJ1, false);
		clearView();
		Activator.getDefault().getRepositoryUtil()
				.addConfiguredRepository(repositoryFile);
		shareProjects(repositoryFile);
		assertProjectExistence(PROJ1, true);
		refreshAndWait();
		assertHasRepo(repositoryFile);
		SWTBotTree tree = getOrOpenView().bot().tree();
		tree.getAllItems()[0].select();
		ContextMenuHelper.clickContextMenu(tree, myUtil
				.getPluginLocalizedValue(DELETE_REPOSITORY_CONTEXT_MENU_LABEL));
		SWTBotShell shell = bot.shell(
				UIText.DeleteRepositoryConfirmDialog_DeleteRepositoryWindowTitle);
		shell.activate();
		shell.bot()
				.checkBox(
						UIText.DeleteRepositoryConfirmDialog_DeleteGitDirCheckbox)
				.select();
		SWTBotCheckBox checkbox = shell.bot().checkBox(
				UIText.DeleteRepositoryConfirmDialog_DeleteWorkingDirectoryCheckbox);
		checkbox.select();
		checkbox.deselect();
		shell.bot().button(
				UIText.DeleteRepositoryConfirmDialog_DeleteRepositoryConfirmButton)
				.click();
		TestUtil.joinJobs(JobFamilies.REPOSITORY_DELETE);

		refreshAndWait();
		assertEmpty();
		assertProjectExistence(PROJ1, false);
		assertFalse(repositoryFile.exists());
		assertTrue(
				new File(repositoryFile.getParentFile(), PROJ1).isDirectory());
	}

	@Ignore("This test is too unstable on the new Kubernetes infrastructure")
	@Test
	public void testRemoveRepositoryRemoveFromCachesBug483664()
			throws Exception {
		Activator.getDefault().getPreferenceStore()
				.setValue(UIPreferences.ALWAYS_USE_STAGING_VIEW, false);

		deleteAllProjects();
		assertProjectExistence(PROJ1, false);
		clearView();
		refreshAndWait();

		Activator.getDefault().getRepositoryUtil()
				.addConfiguredRepository(repositoryFile);
		refreshAndWait();
		assertHasRepo(repositoryFile);
		SWTBotTree tree = getOrOpenView().bot().tree();
		tree.getAllItems()[0].select();
		ContextMenuHelper.clickContextMenuSync(tree,
				myUtil.getPluginLocalizedValue(
				REMOVE_REPOSITORY_FROM_VIEW_CONTEXT_MENU_LABEL));
		TestUtil.joinJobs(JobFamilies.REPOSITORY_DELETE);
		refreshAndWait();
		assertEmpty();

		assertTrue(repositoryFile.exists());
		assertTrue(
				new File(repositoryFile.getParentFile(), PROJ1).isDirectory());
		TestUtil.waitForDecorations();
		closeGitViews();
		TestUtil.waitForJobs(500, 5000);
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IProject project = root.getProject(PROJ1);
		for (int i = 0; i < 101; i++) {
			project.create(null);
			if (i == 0) {
				SWTBotTree explorerTree = TestUtil.getExplorerTree();
				SWTBotTreeItem projectNode = TestUtil.navigateTo(explorerTree,
						PROJ1);
				projectNode.select();
				ContextMenuHelper.isContextMenuItemEnabled(explorerTree, "New");
			}
			project.delete(true, true, null);
		}
		TestUtil.waitForJobs(500, 5000);
		final String[] results = { null, null };
		Job verifier = new Job(testName.getMethodName()) {

			@Override
			protected IStatus run(IProgressMonitor monitor) {
				try {
					TestUtil.joinJobs(
							org.eclipse.egit.core.JobFamilies.INDEX_DIFF_CACHE_UPDATE);
					TestUtil.joinJobs(JobFamilies.REPO_VIEW_REFRESH);
					TestUtil.waitForDecorations();
				} catch (InterruptedException e) {
					results[0] = "Interrupted";
					Thread.currentThread().interrupt();
					return Status.CANCEL_STATUS;
				}
				myRepoViewUtil.dispose();
				waitForFinalization(10000);
				Repository[] repositories = org.eclipse.egit.core.Activator
						.getDefault().getRepositoryCache().getAllRepositories();
				results[0] = Arrays.asList(repositories).toString();
				IndexDiffCache indexDiffCache = org.eclipse.egit.core.Activator
						.getDefault().getIndexDiffCache();
				results[1] = indexDiffCache.currentCacheEntries().toString();
				return Status.OK_STATUS;
			}

		};
		verifier.setRule(new RepositoryCacheRule());
		verifier.setSystem(true);
		verifier.schedule();
		verifier.join();
		List<String> configuredRepos = org.eclipse.egit.core.Activator
				.getDefault().getRepositoryUtil().getConfiguredRepositories();
		assertTrue("Expected no configured repositories",
				configuredRepos.isEmpty());
		assertEquals("Expected no cached repositories", "[]", results[0]);
		assertEquals("Expected no IndexDiffCache entries", "[]", results[1]);

		Activator.getDefault().getPreferenceStore()
				.setValue(UIPreferences.ALWAYS_USE_STAGING_VIEW, true);
	}

	@Test
	public void testDeleteSubmoduleRepository() throws Exception {
		deleteAllProjects();
		assertProjectExistence(PROJ1, false);
		clearView();
		Activator.getDefault().getRepositoryUtil()
				.addConfiguredRepository(repositoryFile);
		shareProjects(repositoryFile);
		assertProjectExistence(PROJ1, true);
		refreshAndWait();
		assertHasRepo(repositoryFile);

		Repository db = lookupRepository(repositoryFile);
		SubmoduleAddCommand command = new SubmoduleAddCommand(db);
		String path = "sub";
		command.setPath(path);
		String uri = db.getDirectory().toURI().toString();
		command.setURI(uri);
		Repository subRepo = command.call();
		assertNotNull(subRepo);
		subRepo.close();

		refreshAndWait();

		SWTBotTree tree = getOrOpenView().bot().tree();
		SWTBotTreeItem item = TestUtil.expandAndWait(tree.getAllItems()[0]);
		item = TestUtil.expandAndWait(item.getNode(
				UIText.RepositoriesViewLabelProvider_SubmodulesNodeText));
		item.getItems()[0].select();
		ContextMenuHelper.clickContextMenu(tree, myUtil
				.getPluginLocalizedValue(DELETE_REPOSITORY_CONTEXT_MENU_LABEL));
		SWTBotShell shell = bot
				.shell(UIText.DeleteRepositoryConfirmDialog_DeleteRepositoryWindowTitle);
		shell.activate();
		shell.bot()
				.checkBox(
						UIText.DeleteRepositoryConfirmDialog_DeleteGitDirCheckbox)
				.select();
		shell.bot()
				.checkBox(
						UIText.DeleteRepositoryConfirmDialog_DeleteWorkingDirectoryCheckbox)
				.select();
		shell.bot().button(
				UIText.DeleteRepositoryConfirmDialog_DeleteRepositoryConfirmButton)
				.click();
		TestUtil.joinJobs(JobFamilies.REPOSITORY_DELETE);

		refreshAndWait();
		assertFalse(subRepo.getDirectory().exists());
		assertFalse(subRepo.getWorkTree().exists());
	}

	/**
	 * Best-effort attempt to get finalization to occur.
	 *
	 * @param maxMillis
	 *            maximum amount of time in milliseconds to try getting the
	 *            garbage collector to finalize objects
	 */
	private void waitForFinalization(int maxMillis) {
		long stop = System.currentTimeMillis() + maxMillis;
		MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
		for (;;) {
			System.gc();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				System.out.println("Garbage collection interrupted");
				break;
			}
			if (memoryBean.getObjectPendingFinalizationCount() == 0) {
				break;
			}
			if (System.currentTimeMillis() > stop) {
				System.out.println(
						"Garbage collection timed out; not all objects collected.");
				break;
			}
		}
	}
}
