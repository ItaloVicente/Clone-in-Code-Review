package org.eclipse.jgit.api;

import java.io.File;
import java.io.FileOutputStream;

import org.eclipse.jgit.api.CreateBranchCommand.SetupUpstreamMode;
import org.eclipse.jgit.api.ListBranchCommand.ListMode;
import org.eclipse.jgit.api.errors.CannotDeleteCurrentBranchException;
import org.eclipse.jgit.api.errors.InvalidRefNameException;
import org.eclipse.jgit.api.errors.NotMergedException;
import org.eclipse.jgit.api.errors.RefAlreadyExistsException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryTestCase;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.transport.FetchResult;
import org.eclipse.jgit.transport.RefSpec;
import org.eclipse.jgit.transport.RemoteConfig;
import org.eclipse.jgit.transport.URIish;

public class BranchCommandTest extends RepositoryTestCase {
	private Git git;

	RevCommit initialCommit;

	RevCommit secondCommit;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		git = new Git(db);
		writeTrashFile("SomeFile.txt"
		git.commit().setMessage("initial commit").call();
		File sourceFile = new File(db.getWorkTree()
		FileOutputStream fos = new FileOutputStream(sourceFile);
		fos.write("Hello world".getBytes("UTF-8"));
		fos.close();
		git.add().addFilepattern("Test.txt").call();
		initialCommit = git.commit().setMessage("Initial commit").call();
		fos = new FileOutputStream(sourceFile);
		fos.write("Some change".getBytes("UTF-8"));
		fos.close();
		git.add().addFilepattern("Test.txt").call();
		secondCommit = git.commit().setMessage("Second commit").call();
		RefUpdate rup = db.updateRef("refs/heads/master");
		rup.setNewObjectId(initialCommit.getId());
		rup.setForceUpdate(true);
		rup.update();
	}


	private Git setUpRepoWithRemote() throws Exception {
		Repository remoteRepository = createWorkRepository();
		Git remoteGit = new Git(remoteRepository);
		File sourceFile = new File(remoteRepository.getWorkTree()
		FileOutputStream fos = new FileOutputStream(sourceFile);
		fos.write("Hello world".getBytes("UTF-8"));
		fos.close();
		remoteGit.add().addFilepattern("Test.txt").call();
		initialCommit = remoteGit.commit().setMessage("Initial commit").call();
		fos = new FileOutputStream(sourceFile);
		fos.write("Some change".getBytes("UTF-8"));
		fos.close();
		remoteGit.add().addFilepattern("Test.txt").call();
		secondCommit = remoteGit.commit().setMessage("Second commit").call();
		RefUpdate rup = remoteRepository.updateRef("refs/heads/master");
		rup.setNewObjectId(initialCommit.getId());
		rup.forceUpdate();

		Repository localRepository = createWorkRepository();
		Git localGit = new Git(localRepository);
		StoredConfig config = localRepository.getConfig();
		RemoteConfig rc = new RemoteConfig(config
		rc.addURI(new URIish(remoteRepository.getDirectory().getPath()));
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

	public void testCreateAndList() throws Exception {
		int localBefore;
		int remoteBefore;
		int allBefore;

		localBefore = git.branchList().call().size();
		remoteBefore = git.branchList().setListMode(ListMode.REMOTE).call()
				.size();
		allBefore = git.branchList().setListMode(ListMode.ALL).call().size();

		assertEquals(localBefore + remoteBefore
		Ref newBranch = git.branchCreate().setParameters("NewForTestList"
				false
		assertEquals("refs/heads/NewForTestList"

		assertEquals(1
		assertEquals(0
				.size()
				- remoteBefore);
		assertEquals(1
				.size()
				- allBefore);
		newBranch = git.branchCreate().setParameters(
				"refs/remotes/origin/NewRemoteForTestList"
				null).call();
		assertEquals("refs/heads/refs/remotes/origin/NewRemoteForTestList"
				newBranch.getName());
		assertEquals(2
		assertEquals(0
				.size()
				- remoteBefore);
		assertEquals(2
				.size()
				- allBefore);
		try {
			git.branchCreate().setName("In va lid").call();
			fail("Create branch with invalid ref name should fail");
		} catch (InvalidRefNameException e) {
		}
	}

	public void testCreateForce() throws Exception {
		Ref newBranch = git.branchCreate().setParameters("NewForce"
				secondCommit.getId().name()
		assertEquals(newBranch.getTarget().getObjectId()
		try {
			newBranch = git.branchCreate().setParameters("NewForce"
					initialCommit.getId().name()
			fail("Should have failed");
		} catch (RefAlreadyExistsException e) {
		}
		newBranch = git.branchCreate().setParameters("NewForce"
				initialCommit.getId().name()
		assertEquals(newBranch.getTarget().getObjectId()
	}

	public void testDelete() throws Exception {
		git.branchCreate().setParameters("ForDelete"
				.call();
		git.branchDelete().setBranchNames("ForDelete").call();
		git.branchCreate().setParameters("ForDelete"
				secondCommit.getId().name()
		try {
			git.branchDelete().setBranchNames("ForDelete").call();
			fail("Deletion of a non-merged branch without force should have failed");
		} catch (NotMergedException e) {
		}
		git.branchDelete().setBranchNames("ForDelete").setForce(true).call();
		git.branchCreate().setParameters("ForDelete"
				.call();
		try {
			git.branchCreate()
					.setParameters("ForDelete"
			fail("Repeated creation of same branch without force should fail");
		} catch (RefAlreadyExistsException e) {
		}
		Ref newBranch = git.branchCreate().setParameters("ForDelete"
				initialCommit.name()
		assertEquals(newBranch.getTarget().getObjectId()
		newBranch = git.branchCreate().setParameters("ForDelete"
				secondCommit.name()
		assertEquals(newBranch.getTarget().getObjectId()
		git.branchDelete().setBranchNames("ForDelete").setForce(true).call();
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

	public void testPullConfigRemoteBranch() throws Exception {
		Git localGit = setUpRepoWithRemote();
		Ref remote = localGit.branchList().setListMode(ListMode.REMOTE).call()
				.get(0);
		assertEquals("refs/remotes/origin/master"
		localGit.branchCreate().setParameters("newFromRemote"
				remote.getName()
		assertEquals("origin"
				"branch"
		localGit.branchDelete().setBranchNames("newFromRemote").call();
		assertNull(localGit.getRepository().getConfig().getString("branch"
				"newFromRemote"
		localGit.branchCreate().setParameters("newFromRemote"
				remote.getName()
		assertNull(localGit.getRepository().getConfig().getString("branch"
				"newFromRemote"
		localGit.branchDelete().setBranchNames("newFromRemote").call();
	}

	public void testPullConfigLocalBranch() throws Exception {
		Git localGit = setUpRepoWithRemote();
		localGit.branchCreate().setParameters("newFromMaster"
				null).call();
		assertNull(localGit.getRepository().getConfig().getString("branch"
				"newFromMaster"
		localGit.branchDelete().setBranchNames("newFromMaster").call();
		localGit.branchCreate().setParameters("newFromMaster"
				SetupUpstreamMode.TRACK).call();
		assertEquals("."
				"branch"
		localGit.branchDelete().setBranchNames("newFromMaster").call();
		assertNull(localGit.getRepository().getConfig().getString("branch"
				"newFromRemote"
	}

	public void testPullConfigRenameLocalBranch() throws Exception {
		Git localGit = setUpRepoWithRemote();
		localGit.branchCreate().setParameters("newFromMaster"
				null).call();
		assertNull(localGit.getRepository().getConfig().getString("branch"
				"newFromMaster"
		localGit.branchDelete().setBranchNames("newFromMaster").call();
		localGit.branchCreate().setParameters("newFromMaster"
				SetupUpstreamMode.TRACK).call();
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

	public void testRenameLocalBranch() throws Exception {
		git.branchCreate().setParameters("existing"
				.call();
		Ref branch = git.branchCreate().setParameters("fromMasterForRename"
				false
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
	}

	public void testRenameRemoteTrackingBranch() throws Exception {
		Git localGit = setUpRepoWithRemote();
		Ref remoteBranch = localGit.branchList().setListMode(ListMode.REMOTE)
				.call().get(0);
		Ref renamed = localGit.branchRename()
				.setOldName(remoteBranch.getName()).setNewName("newRemote")
				.call();
		assertEquals(Constants.R_REMOTES + "newRemote"
	}
}
