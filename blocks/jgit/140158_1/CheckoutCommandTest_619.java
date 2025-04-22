package org.eclipse.jgit.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.List;

import org.eclipse.jgit.api.CreateBranchCommand.SetupUpstreamMode;
import org.eclipse.jgit.api.ListBranchCommand.ListMode;
import org.eclipse.jgit.api.errors.CannotDeleteCurrentBranchException;
import org.eclipse.jgit.api.errors.DetachedHeadException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRefNameException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.api.errors.NotMergedException;
import org.eclipse.jgit.api.errors.RefAlreadyExistsException;
import org.eclipse.jgit.api.errors.RefNotFoundException;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.transport.FetchResult;
import org.eclipse.jgit.transport.RefSpec;
import org.eclipse.jgit.transport.RemoteConfig;
import org.eclipse.jgit.transport.URIish;
import org.junit.Before;
import org.junit.Test;

public class BranchCommandTest extends RepositoryTestCase {
	private Git git;

	RevCommit initialCommit;

	RevCommit secondCommit;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		git = new Git(db);
		git.commit().setMessage("initial commit").call();
		writeTrashFile("Test.txt"
		git.add().addFilepattern("Test.txt").call();
		initialCommit = git.commit().setMessage("Initial commit").call();
		writeTrashFile("Test.txt"
		git.add().addFilepattern("Test.txt").call();
		secondCommit = git.commit().setMessage("Second commit").call();
		RefUpdate rup = db.updateRef("refs/heads/master");
		rup.setNewObjectId(initialCommit.getId());
		rup.setForceUpdate(true);
		rup.update();
	}

	private Git setUpRepoWithRemote() throws Exception {
		Repository remoteRepository = createWorkRepository();
		try (Git remoteGit = new Git(remoteRepository)) {
			writeTrashFile("Test.txt"
			remoteGit.add().addFilepattern("Test.txt").call();
			initialCommit = remoteGit.commit().setMessage("Initial commit").call();
			writeTrashFile("Test.txt"
			remoteGit.add().addFilepattern("Test.txt").call();
			secondCommit = remoteGit.commit().setMessage("Second commit").call();
			RefUpdate rup = remoteRepository.updateRef("refs/heads/master");
			rup.setNewObjectId(initialCommit.getId());
			rup.forceUpdate();

			Repository localRepository = createWorkRepository();
			Git localGit = new Git(localRepository);
			StoredConfig config = localRepository.getConfig();
			RemoteConfig rc = new RemoteConfig(config
			rc.addURI(new URIish(remoteRepository.getDirectory().getAbsolutePath()));
			rc.update(config);
			config.save();
			FetchResult res = localGit.fetch().setRemote("origin").call();
			assertFalse(res.getTrackingRefUpdates().isEmpty());
			rup = localRepository.updateRef("refs/heads/master");
			rup.setNewObjectId(initialCommit.getId());
			rup.forceUpdate();
			rup = localRepository.updateRef(Constants.HEAD);
			rup.link("refs/heads/master");
			rup.setNewObjectId(initialCommit.getId());
			rup.update();
			return localGit;
		}
	}

	@Test
	public void testCreateAndList() throws Exception {
		int localBefore;
		int remoteBefore;
		int allBefore;

		try {
			git.branchCreate().setName("In va lid").call();
			fail("Create branch with invalid ref name should fail");
		} catch (InvalidRefNameException e) {
		}
		try {
			git.branchCreate().setName("master").call();
			fail("Create branch with existing ref name should fail");
		} catch (RefAlreadyExistsException e) {
		}

		localBefore = git.branchList().call().size();
		remoteBefore = git.branchList().setListMode(ListMode.REMOTE).call()
				.size();
		allBefore = git.branchList().setListMode(ListMode.ALL).call().size();

		assertEquals(localBefore + remoteBefore
		Ref newBranch = createBranch(git
				null);
		assertEquals("refs/heads/NewForTestList"

		assertEquals(1
		assertEquals(0
				.size()
				- remoteBefore);
		assertEquals(1
				.size()
				- allBefore);
		newBranch = createBranch(git
				"refs/remotes/origin/NewRemoteForTestList"
				null);
		assertEquals("refs/heads/refs/remotes/origin/NewRemoteForTestList"
				newBranch.getName());
		assertEquals(2
		assertEquals(0
				.size()
				- remoteBefore);
		assertEquals(2
				.size()
				- allBefore);
	}

	@Test(expected = InvalidRefNameException.class)
	public void testInvalidBranchHEAD() throws Exception {
		git.branchCreate().setName("HEAD").call();
		fail("Create branch with invalid ref name should fail");
	}

	@Test(expected = InvalidRefNameException.class)
	public void testInvalidBranchDash() throws Exception {
		git.branchCreate().setName("-x").call();
		fail("Create branch with invalid ref name should fail");
	}

	@Test
	public void testListAllBranchesShouldNotDie() throws Exception {
		setUpRepoWithRemote().branchList().setListMode(ListMode.ALL).call();
	}

	@Test
	public void testListBranchesWithContains() throws Exception {
		git.branchCreate().setName("foo").setStartPoint(secondCommit).call();

		List<Ref> refs = git.branchList().call();
		assertEquals(2

		List<Ref> refsContainingSecond = git.branchList()
				.setContains(secondCommit.name()).call();
		assertEquals(1
		assertEquals("refs/heads/foo"
	}

	@Test
	public void testCreateFromCommit() throws Exception {
		Ref branch = git.branchCreate().setName("FromInitial").setStartPoint(
				initialCommit).call();
		assertEquals(initialCommit.getId()
		branch = git.branchCreate().setName("FromInitial2").setStartPoint(
				initialCommit.getId().name()).call();
		assertEquals(initialCommit.getId()
		try {
			git.branchCreate().setName("FromInitial").setStartPoint(
					secondCommit).call();
		} catch (RefAlreadyExistsException e) {
		}
		branch = git.branchCreate().setName("FromInitial").setStartPoint(
				secondCommit).setForce(true).call();
		assertEquals(secondCommit.getId()
	}

	@Test
	public void testCreateForce() throws Exception {
		Ref newBranch = createBranch(git
				.getId().name()
		assertEquals(newBranch.getTarget().getObjectId()
		try {
			newBranch = createBranch(git
					.getId().name()
			fail("Should have failed");
		} catch (RefAlreadyExistsException e) {
		}
		newBranch = createBranch(git
				.name()
		assertEquals(newBranch.getTarget().getObjectId()
		git.branchDelete().setBranchNames("NewForce").call();

		git.branchCreate().setName("NewForce").setStartPoint("master").call();
		assertEquals(newBranch.getTarget().getObjectId()
		try {
			git.branchCreate().setName("NewForce").setStartPoint("master")
					.call();
			fail("Should have failed");
		} catch (RefAlreadyExistsException e) {
		}
		git.branchCreate().setName("NewForce").setStartPoint("master")
				.setForce(true).call();
		assertEquals(newBranch.getTarget().getObjectId()
	}

	@Test
	public void testCreateFromLightweightTag() throws Exception {
		RefUpdate rup = db.updateRef("refs/tags/V10");
		rup.setNewObjectId(initialCommit);
		rup.setExpectedOldObjectId(ObjectId.zeroId());
		rup.update();

		Ref branch = git.branchCreate().setName("FromLightweightTag")
				.setStartPoint("refs/tags/V10").call();
		assertEquals(initialCommit.getId()

	}

	@Test
	public void testCreateFromAnnotatetdTag() throws Exception {
		Ref tagRef = git.tag().setName("V10").setObjectId(secondCommit).call();
		Ref branch = git.branchCreate().setName("FromAnnotatedTag")
				.setStartPoint("refs/tags/V10").call();
		assertFalse(tagRef.getObjectId().equals(branch.getObjectId()));
		assertEquals(secondCommit.getId()
	}

	@Test
	public void testDelete() throws Exception {
		createBranch(git
		git.branchDelete().setBranchNames("ForDelete").call();
		createBranch(git
		try {
			git.branchDelete().setBranchNames("ForDelete").call();
			fail("Deletion of a non-merged branch without force should have failed");
		} catch (NotMergedException e) {
		}
		List<String> deleted = git.branchDelete().setBranchNames("ForDelete")
				.setForce(true).call();
		assertEquals(1
		assertEquals(Constants.R_HEADS + "ForDelete"
		createBranch(git
		try {
			createBranch(git
			fail("Repeated creation of same branch without force should fail");
		} catch (RefAlreadyExistsException e) {
		}
		Ref newBranch = createBranch(git
				.name()
		assertEquals(newBranch.getTarget().getObjectId()
		newBranch = createBranch(git
				null);
		assertEquals(newBranch.getTarget().getObjectId()
		git.branchDelete().setBranchNames("ForDelete").setForce(true);
		try {
			git.branchDelete().setBranchNames("master").call();
			fail("Deletion of checked out branch without force should have failed");
		} catch (CannotDeleteCurrentBranchException e) {
		}
		try {
			git.branchDelete().setBranchNames("master").setForce(true).call();
			fail("Deletion of checked out branch with force should have failed");
		} catch (CannotDeleteCurrentBranchException e) {
		}
	}

	@Test
	public void testPullConfigRemoteBranch() throws Exception {
		Git localGit = setUpRepoWithRemote();
		Ref remote = localGit.branchList().setListMode(ListMode.REMOTE).call()
				.get(0);
		assertEquals("refs/remotes/origin/master"
		createBranch(localGit
		assertEquals("origin"
				"branch"
		localGit.branchDelete().setBranchNames("newFromRemote").call();
		assertNull(localGit.getRepository().getConfig().getString("branch"
				"newFromRemote"

		createBranch(localGit
		assertEquals("origin"
				"branch"
		localGit.branchDelete().setBranchNames("refs/heads/newFromRemote")
				.call();
		assertNull(localGit.getRepository().getConfig().getString("branch"
				"newFromRemote"

		createBranch(localGit
				SetupUpstreamMode.NOTRACK);
		assertNull(localGit.getRepository().getConfig().getString("branch"
				"newFromRemote"
		localGit.branchDelete().setBranchNames("newFromRemote").call();
	}

	@Test
	public void testPullConfigLocalBranch() throws Exception {
		Git localGit = setUpRepoWithRemote();
		createBranch(localGit
		assertNull(localGit.getRepository().getConfig().getString("branch"
				"newFromMaster"
		localGit.branchDelete().setBranchNames("newFromMaster").call();
		createBranch(localGit
				SetupUpstreamMode.TRACK);
		assertEquals("."
				"branch"
		localGit.branchDelete().setBranchNames("refs/heads/newFromMaster")
				.call();
		assertNull(localGit.getRepository().getConfig().getString("branch"
				"newFromRemote"
	}

	@Test
	public void testPullConfigRenameLocalBranch() throws Exception {
		Git localGit = setUpRepoWithRemote();
		createBranch(localGit
		assertNull(localGit.getRepository().getConfig().getString("branch"
				"newFromMaster"
		localGit.branchDelete().setBranchNames("newFromMaster").call();
		createBranch(localGit
				SetupUpstreamMode.TRACK);
		assertEquals("."
				"branch"
		localGit.branchRename().setOldName("newFromMaster").setNewName(
				"renamed").call();
		assertNull("."
				"branch"
		assertEquals("."
				"branch"
		localGit.branchDelete().setBranchNames("renamed").call();
		assertNull(localGit.getRepository().getConfig().getString("branch"
				"newFromRemote"
	}

	@Test
	public void testRenameLocalBranch() throws Exception {
		try {
			git.branchRename().call();
		} catch (InvalidRefNameException e) {
		}
		try {
			git.branchRename().setNewName("In va lid").call();
		} catch (InvalidRefNameException e) {
		}
		try {
			git.branchRename().setOldName("notexistingbranch").setNewName(
					"newname").call();
		} catch (RefNotFoundException e) {
		}
		createBranch(git
		Ref branch = createBranch(git
				null);
		assertEquals(Constants.R_HEADS + "fromMasterForRename"
				.getName());
		Ref renamed = git.branchRename().setOldName("fromMasterForRename")
				.setNewName("newName").call();
		assertEquals(Constants.R_HEADS + "newName"
		try {
			git.branchRename().setOldName(renamed.getName()).setNewName(
					"existing").call();
			fail("Should have failed");
		} catch (RefAlreadyExistsException e) {
		}
		try {
			git.branchRename().setNewName("In va lid").call();
			fail("Rename with invalid ref name should fail");
		} catch (InvalidRefNameException e) {
		}
		RefUpdate rup = git.getRepository().updateRef(Constants.HEAD
		rup.setNewObjectId(initialCommit);
		rup.forceUpdate();
		try {
			git.branchRename().setNewName("detached").call();
		} catch (DetachedHeadException e) {
		}
	}

	@Test
	public void testRenameRemoteTrackingBranch() throws Exception {
		Git localGit = setUpRepoWithRemote();
		Ref remoteBranch = localGit.branchList().setListMode(ListMode.REMOTE)
				.call().get(0);
		Ref renamed = localGit.branchRename()
				.setOldName(remoteBranch.getName()).setNewName("newRemote")
				.call();
		assertEquals(Constants.R_REMOTES + "newRemote"
	}

	@Test
	public void testCreationImplicitStart() throws Exception {
		git.branchCreate().setName("topic").call();
		assertEquals(db.resolve("HEAD")
	}

	@Test
	public void testCreationNullStartPoint() throws Exception {
		String startPoint = null;
		git.branchCreate().setName("topic").setStartPoint(startPoint).call();
		assertEquals(db.resolve("HEAD")
	}

	public Ref createBranch(Git actGit
			String startPoint
			throws JGitInternalException
		CreateBranchCommand cmd = actGit.branchCreate();
		cmd.setName(name);
		cmd.setForce(force);
		cmd.setStartPoint(startPoint);
		cmd.setUpstreamMode(mode);
		return cmd.call();
	}
}
