/*******************************************************************************
 * Copyright (C) 2009, Robin Rosenberg <robin.rosenberg@dewire.com>
 * Copyright (C) 2010, Ketan Padegaonkar <KetanPadegaonkar@gmail.com>
 * Copyright (C) 2010, Matthias Sohn <matthias.sohn@sap.com>
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.egit.ui.wizards.clone;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Platform;
import org.eclipse.egit.ui.common.RepoPropertiesPage;
import org.eclipse.egit.ui.common.RepoRemoteBranchesPage;
import org.eclipse.egit.ui.common.WorkingCopyPage;
import org.eclipse.egit.ui.internal.UIText;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.RefDatabase;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.osgi.util.NLS;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class GitCloneWizardTest extends GitCloneWizardTestBase {

	@BeforeClass
	public static void setup() throws Exception {
		r = new SampleTestRepository(NUMBER_RANDOM_COMMITS, false);
	}

	@Test
	public void updatesParameterFieldsInImportDialogWhenURIIsUpdated()
			throws Exception {

		importWizard.openWizard();
		RepoPropertiesPage propertiesPage = importWizard.openRepoPropertiesPage();

		propertiesPage.assertSourceParams(null, "www.jgit.org", "/EGIT", "git",
				"", true, "", "", false, false);

		propertiesPage.appendToURI("X");

		propertiesPage.assertSourceParams(null, "www.jgit.org", "/EGITX",
				"git", "", true, "", "", false, false);

		propertiesPage.assertSourceParams(null, "www.jgit.org", "/EGIT", "git",
				"", true, "", "", false, false);

		propertiesPage.assertSourceParams(
				" User not supported on git protocol.", "www.jgit.org",
				"/EGIT", "git", "", true, "", "", false, false);


		propertiesPage.assertSourceParams(null, "www.jgit.org", "/EGIT",
				"ssh", "", true, "user", "", true, true);

		propertiesPage.assertSourceParams(null, "www.jgit.org", "/EGIT",
				"ssh", "", true, "user", "", true, true);

		propertiesPage.assertSourceParams(null, "www.jgit.org", "/EGIT",
				"ssh", "33", true, "user", "hi", true, true);

		propertiesPage.assertSourceParams(" Host required for ssh protocol.",
				"", "/EGIT", "ssh", "", true, "", "", true, true);

		if (Platform.getOS().equals(Platform.OS_WIN32))
			propertiesPage.assertSourceParams(" "
					+ System.getProperty("user.dir")
					+ "\\.\\some\\place does not exist.", "", "/some/place",
					"file", "", false, "", "", false, false);
		else
			propertiesPage.assertSourceParams(" /some/place does not exist.",
					"", "/some/place", "file", "", false, "", "", false, false);


		bot.textWithLabel("Host:").setText("example.com");

		propertiesPage.assertSourceParams(null, "example.com", "/EGIT",
				"ssh", "", true, "user", "", true, true);

		bot.textWithLabel("User:").setText("gitney");
		propertiesPage.assertSourceParams(null, "example.com", "/EGIT",
				"ssh", "", true, "gitney", "", true, true);

		bot.textWithLabel("Password:").setText("fsck");
		propertiesPage.assertSourceParams(null, "example.com", "/EGIT",
				"ssh", "", true, "gitney", "fsck", true, true);

		bot.textWithLabel("Port:").setText("99");
		propertiesPage.assertSourceParams(null, "example.com", "/EGIT",
				"ssh", "99", true, "gitney", "fsck", true, true);

		bot.comboBoxWithLabel("Protocol:").setSelection("ftp");
		propertiesPage.assertSourceParams(null, "example.com", "/EGIT", "ftp",
				"99", true, "gitney", "fsck", true, true);

		bot.comboBoxWithLabel("Protocol:").setSelection("git");
		propertiesPage.assertSourceParams(null, "example.com", "/EGIT",
				"git", "99", true, "", "", false, false);

		bot.comboBoxWithLabel("Protocol:").setSelection("file");
		propertiesPage.assertSourceParams(" /EGIT does not exist.", "",
				"/EGIT",
				"file", "", false,
				"", "", false, false);

		if (Platform.getOS().equals(Platform.OS_WIN32))
		else
		propertiesPage.assertSourceParams(null, "", System.getProperty(
				"user.home"), "file", "", false, "", "",
				false, false);

		propertiesPage.setURI(System.getProperty("user.home"));
		propertiesPage.assertSourceParams(null, "", System.getProperty(
				"user.home"), "file", "", false, "", "",
				false, false);

		if (Platform.getOS().equals(Platform.OS_WIN32)) {
			propertiesPage.setURI(System.getProperty("user.home"));
			propertiesPage.assertSourceParams(null, "", System.getProperty(
					"user.home"), "file", "", false, "", "",
					false, false);
		}
		bot.button("Cancel").click();
	}

	@Test
	public void testFileURIWithSpace() throws Exception {
		importWizard.openWizard();
		RepoPropertiesPage propertiesPage = importWizard
				.openRepoPropertiesPage();
		if (Platform.getOS().equals(Platform.OS_WIN32)) {
			propertiesPage.assertSourceParams(" "
					+ System.getProperty("user.dir")
					+ "\\.\\Some\\Directory With Spaces\\bare.git does not exist.",
					"", "/Some/Directory With Spaces/bare.git", "file", "",
					false, "", "", false, false);
		} else {
			propertiesPage.assertSourceParams(
					" /Some/Directory With Spaces/bare.git does not exist.", "",
					"/Some/Directory With Spaces/bare.git", "file", "", false,
					"", "", false, false);
		}
		bot.button("Cancel").click();
	}

	@Test
	public void canCloneARemoteRepo() throws Exception {
		destRepo = new File(ResourcesPlugin.getWorkspace()
				.getRoot().getLocation().toFile(), "test1");

		importWizard.openWizard();
		RepoPropertiesPage propertiesPage = importWizard.openRepoPropertiesPage();

		RepoRemoteBranchesPage remoteBranches = propertiesPage
				.nextToRemoteBranches(r.getUri());

		cloneRepo(destRepo, remoteBranches);
		bot.button("Cancel").click();
	}

	@Test
	public void clonedRepositoryShouldExistOnFileSystem() throws Exception {
		importWizard.openWizard();
		RepoPropertiesPage repoProperties = importWizard.openRepoPropertiesPage();
		RepoRemoteBranchesPage remoteBranches = repoProperties
				.nextToRemoteBranches(r.getUri());
		remoteBranches.assertRemoteBranches(SampleTestRepository.FIX, Constants.MASTER);
		WorkingCopyPage workingCopy = remoteBranches.nextToWorkingCopy();
		workingCopy.assertWorkingCopyExists();
		bot.button("Cancel").click();
	}

	@Test
	public void alteringSomeParametersDuringClone() throws Exception {
		destRepo = new File(ResourcesPlugin.getWorkspace()
				.getRoot().getLocation().toFile(), "test2");

		importWizard.openWizard();
		RepoPropertiesPage repoProperties = importWizard.openRepoPropertiesPage();
		RepoRemoteBranchesPage remoteBranches = repoProperties
				.nextToRemoteBranches(r.getUri());
		remoteBranches.deselectAllBranches();
		remoteBranches
				.assertErrorMessage("At least one branch must be selected.");
		remoteBranches.assertNextIsDisabled();

		remoteBranches.selectBranches(SampleTestRepository.FIX);
		remoteBranches.assertNextIsEnabled();

		WorkingCopyPage workingCopy = remoteBranches.nextToWorkingCopy();
		workingCopy.setDirectory(destRepo.toString());
		workingCopy.assertBranch(SampleTestRepository.FIX);
		workingCopy.setRemoteName("src");
		workingCopy.waitForCreate();

		try (Repository repository = FileRepositoryBuilder
				.create(new File(destRepo, Constants.DOT_GIT))) {
			assertNotNull(
					repository.resolve("src/" + SampleTestRepository.FIX));
			assertNull(repository.resolve("src/master"));
			assertEquals(repository.resolve("stable"),
					repository.resolve("src/stable"));
			assertNotNull(repository
					.resolve(Constants.R_TAGS + SampleTestRepository.v2_0_name)
					.name());
			assertTrue(repository.getRefDatabase()
					.getRefsByPrefix(RefDatabase.ALL).size() >= 4);
			bot.button("Cancel").click();
		}
	}

	@Ignore
	@Test
	public void invalidHostnameFreezesDialog() throws Exception {
		importWizard.openWizard();
		RepoPropertiesPage repoProperties = importWizard.openRepoPropertiesPage();
		RepoRemoteBranchesPage remoteBranches = repoProperties
		remoteBranches.assertErrorMessage(NLS.bind(
				UIText.SourceBranchPage_CompositeTransportErrorMessage,
				"Exception caught during execution of ls-remote command",
		remoteBranches.assertCannotProceed();
		remoteBranches.cancel();
	}

	@Ignore
	@Test
	public void invalidPortFreezesDialog() throws Exception {
		importWizard.openWizard();
		RepoPropertiesPage repoProperties = importWizard.openRepoPropertiesPage();
		RepoRemoteBranchesPage remoteBranches = repoProperties
		remoteBranches.assertErrorMessage(NLS.bind(
				UIText.SourceBranchPage_CompositeTransportErrorMessage,
				"Exception caught during execution of ls-remote command",
		remoteBranches.assertCannotProceed();
		remoteBranches.cancel();
	}

	@Ignore
	public void timeoutToASocketFreezesDialog() throws Exception {
		importWizard.openWizard();
		RepoPropertiesPage repoProperties = importWizard.openRepoPropertiesPage();
		RepoRemoteBranchesPage remoteBranches = repoProperties
		remoteBranches
		remoteBranches.assertCannotProceed();
		remoteBranches.cancel();
	}

	@Test
	public void acceptCloneCommandAsURI() throws Exception {
		importWizard.openWizard();
		RepoPropertiesPage propertiesPage = importWizard
				.openRepoPropertiesPage();

		propertiesPage.assertSourceParams(null, "www.jgit.org", "/EGIT", "git",
				"", true, "", "", false, false);

		propertiesPage.assertSourceParams(null, "www.jgit.org", "/EGIT", "git",
				"", true, "", "", false, false);

		propertiesPage.assertSourceParams(null, "www.jgit.org", "/EGIT", "git",
				"", true, "", "", false, false);

		bot.button("Cancel").click();
	}

	@Test
	public void gitStyleUriSelectsSshProtocol() throws Exception {
		importWizard.openWizard();

		RepoPropertiesPage propertiesPage = importWizard
				.openRepoPropertiesPage();

		propertiesPage.setURI("git@github.com:someone/somerepo.git");
		propertiesPage.assertSourceParams(null, "github.com",
				"someone/somerepo.git", "ssh", "", true, "git", "", true,
				true);
	}
}
