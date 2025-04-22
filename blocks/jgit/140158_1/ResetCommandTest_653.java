package org.eclipse.jgit.api;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.eclipse.jgit.api.errors.RefAlreadyExistsException;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lib.BranchConfig.BranchRebaseMode;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class RenameBranchCommandTest extends RepositoryTestCase {

	private static final String PATH = "file.txt";

	private RevCommit head;

	private Git git;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Override
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
	public void renameToExisting() throws Exception {
		assertNotNull(git.branchCreate().setName("foo").call());
		thrown.expect(RefAlreadyExistsException.class);
		git.branchRename().setOldName("master").setNewName("foo").call();
	}

	@Test
	public void renameToTag() throws Exception {
		Ref ref = git.tag().setName("foo").call();
		assertNotNull(ref);
		assertEquals("Unexpected tag name"
				ref.getName());
		ref = git.branchRename().setNewName("foo").call();
		assertNotNull(ref);
		assertEquals("Unexpected ref name"
				ref.getName());
		ref = git.branchRename().setOldName("foo").setNewName(Constants.MASTER)
				.call();
		assertNotNull(ref);
		assertEquals("Unexpected ref name"
				Constants.R_HEADS + Constants.MASTER
	}

	@Test
	public void renameToStupidName() throws Exception {
		Ref ref = git.branchRename().setNewName(Constants.R_HEADS + "foo")
				.call();
		assertEquals("Unexpected ref name"
				Constants.R_HEADS + Constants.R_HEADS + "foo"
				ref.getName());
		ref = git.branchRename().setNewName("foo").call();
		assertNotNull(ref);
		assertEquals("Unexpected ref name"
				ref.getName());
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
		config.setEnum(ConfigConstants.CONFIG_BRANCH_SECTION
				ConfigConstants.CONFIG_KEY_REBASE
		config.save();

		String branch = "b1";

		assertEquals(BranchRebaseMode.REBASE
				config.getEnum(BranchRebaseMode.values()
						ConfigConstants.CONFIG_BRANCH_SECTION
						ConfigConstants.CONFIG_KEY_REBASE
						BranchRebaseMode.NONE));
		assertNull(config.getEnum(BranchRebaseMode.values()
				ConfigConstants.CONFIG_BRANCH_SECTION
				ConfigConstants.CONFIG_KEY_REBASE

		assertNotNull(git.branchRename().setNewName(branch).call());

		config = git.getRepository().getConfig();
		assertNull(config.getEnum(BranchRebaseMode.values()
				ConfigConstants.CONFIG_BRANCH_SECTION
				ConfigConstants.CONFIG_KEY_REBASE
		assertEquals(BranchRebaseMode.REBASE
				config.getEnum(BranchRebaseMode.values()
						ConfigConstants.CONFIG_BRANCH_SECTION
						ConfigConstants.CONFIG_KEY_REBASE
						BranchRebaseMode.NONE));
	}

	@Test
	public void renameBranchExistingSection() throws Exception {
		String branch = "b1";
		StoredConfig config = git.getRepository().getConfig();
		config.setEnum(ConfigConstants.CONFIG_BRANCH_SECTION
				ConfigConstants.CONFIG_KEY_REBASE
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
		config.setEnum(ConfigConstants.CONFIG_BRANCH_SECTION
				ConfigConstants.CONFIG_KEY_REBASE
		config.setBoolean(ConfigConstants.CONFIG_BRANCH_SECTION
				Constants.MASTER
		config.save();

		String branch = "b1";

		assertEquals(BranchRebaseMode.REBASE
				config.getEnum(BranchRebaseMode.values()
						ConfigConstants.CONFIG_BRANCH_SECTION
						ConfigConstants.CONFIG_KEY_REBASE
						BranchRebaseMode.NONE));
		assertNull(config.getEnum(BranchRebaseMode.values()
				ConfigConstants.CONFIG_BRANCH_SECTION
				ConfigConstants.CONFIG_KEY_REBASE
		assertTrue(config.getBoolean(ConfigConstants.CONFIG_BRANCH_SECTION
				Constants.MASTER
		assertFalse(config.getBoolean(ConfigConstants.CONFIG_BRANCH_SECTION
				branch

		assertNotNull(git.branchRename().setNewName(branch).call());

		config = git.getRepository().getConfig();
		assertNull(config.getEnum(BranchRebaseMode.values()
				ConfigConstants.CONFIG_BRANCH_SECTION
				ConfigConstants.CONFIG_KEY_REBASE
		assertEquals(BranchRebaseMode.REBASE
				config.getEnum(BranchRebaseMode.values()
						ConfigConstants.CONFIG_BRANCH_SECTION
						ConfigConstants.CONFIG_KEY_REBASE
						BranchRebaseMode.NONE));
		assertFalse(config.getBoolean(ConfigConstants.CONFIG_BRANCH_SECTION
				Constants.MASTER
		assertTrue(config.getBoolean(ConfigConstants.CONFIG_BRANCH_SECTION
				branch
	}
}
