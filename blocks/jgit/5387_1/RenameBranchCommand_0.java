package org.eclipse.jgit.api;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.RepositoryTestCase;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.Before;
import org.junit.Test;

public class RenameBranchCommandTest extends RepositoryTestCase {

	private static final String PATH = "file.txt";

	private RevCommit head;

	private Git git;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		git = Git.wrap(db);
		writeTrashFile(PATH
		git.add().addFilepattern(PATH).call();
		head = git.commit().setMessage("add file").call();
		assertNotNull(head);
	}

	@Test
	public void renameBranchNoConfigValues() throws Exception {
		StoredConfig config = git.getRepository().getConfig();
		config.unsetSection(ConfigConstants.CONFIG_BRANCH_SECTION
				Constants.MASTER);
		config.save();

		String branch = "b1";
		assertTrue(config.getNames(ConfigConstants.CONFIG_BRANCH_SECTION
				Constants.MASTER).isEmpty());
		assertNotNull(git.branchRename().setNewName(branch).call());

		config = git.getRepository().getConfig();
		assertTrue(config.getNames(ConfigConstants.CONFIG_BRANCH_SECTION
				Constants.MASTER).isEmpty());
		assertTrue(config.getNames(ConfigConstants.CONFIG_BRANCH_SECTION
				branch).isEmpty());
	}

	@Test
	public void renameBranchSingleConfigValue() throws Exception {
		StoredConfig config = git.getRepository().getConfig();
		config.setBoolean(ConfigConstants.CONFIG_BRANCH_SECTION
				Constants.MASTER
		config.save();

		String branch = "b1";

		assertTrue(config.getBoolean(ConfigConstants.CONFIG_BRANCH_SECTION
				Constants.MASTER
		assertFalse(config.getBoolean(ConfigConstants.CONFIG_BRANCH_SECTION
				branch

		assertNotNull(git.branchRename().setNewName(branch).call());

		config = git.getRepository().getConfig();
		assertFalse(config.getBoolean(ConfigConstants.CONFIG_BRANCH_SECTION
				Constants.MASTER
		assertTrue(config.getBoolean(ConfigConstants.CONFIG_BRANCH_SECTION
				branch
	}

	@Test
	public void renameBranchExistingSection() throws Exception {
		String branch = "b1";
		StoredConfig config = git.getRepository().getConfig();
		config.setBoolean(ConfigConstants.CONFIG_BRANCH_SECTION
				Constants.MASTER
		config.setString(ConfigConstants.CONFIG_BRANCH_SECTION
				Constants.MASTER
		config.setString(ConfigConstants.CONFIG_BRANCH_SECTION
				"b");
		config.save();

		assertNotNull(git.branchRename().setNewName(branch).call());

		config = git.getRepository().getConfig();
		assertArrayEquals(new String[] { "b"
				ConfigConstants.CONFIG_BRANCH_SECTION
	}

	@Test
	public void renameBranchMultipleConfigValues() throws Exception {
		StoredConfig config = git.getRepository().getConfig();
		config.setBoolean(ConfigConstants.CONFIG_BRANCH_SECTION
				Constants.MASTER
		config.setBoolean(ConfigConstants.CONFIG_BRANCH_SECTION
				Constants.MASTER
		config.save();

		String branch = "b1";

		assertTrue(config.getBoolean(ConfigConstants.CONFIG_BRANCH_SECTION
				Constants.MASTER
		assertFalse(config.getBoolean(ConfigConstants.CONFIG_BRANCH_SECTION
				branch
		assertTrue(config.getBoolean(ConfigConstants.CONFIG_BRANCH_SECTION
				Constants.MASTER
		assertFalse(config.getBoolean(ConfigConstants.CONFIG_BRANCH_SECTION
				branch

		assertNotNull(git.branchRename().setNewName(branch).call());

		config = git.getRepository().getConfig();
		assertFalse(config.getBoolean(ConfigConstants.CONFIG_BRANCH_SECTION
				Constants.MASTER
		assertTrue(config.getBoolean(ConfigConstants.CONFIG_BRANCH_SECTION
				branch
		assertFalse(config.getBoolean(ConfigConstants.CONFIG_BRANCH_SECTION
				Constants.MASTER
		assertTrue(config.getBoolean(ConfigConstants.CONFIG_BRANCH_SECTION
				branch
	}
}
