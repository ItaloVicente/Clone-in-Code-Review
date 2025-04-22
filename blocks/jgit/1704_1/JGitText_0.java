package org.eclipse.jgit.api;

import java.io.File;
import java.io.FileOutputStream;

import org.eclipse.jgit.api.BranchCommand.ListMode;
import org.eclipse.jgit.api.BranchCommand.SetupUpstreamMode;
import org.eclipse.jgit.api.errors.NotMergedException;
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

		localBefore = git.branch().call().size();
		remoteBefore = git.branch().setList(ListMode.REMOTE).call().size();
		allBefore = git.branch().setList(ListMode.ALL).call().size();

		assertEquals(localBefore + remoteBefore
		Ref newBranch = git.branch().setCreate("NewForTestList"
				"master"
		assertEquals("refs/heads/NewForTestList"

		assertEquals(1
		assertEquals(0
				- remoteBefore);
		assertEquals(1
				- allBefore);
		newBranch = git.branch().setCreate(
				"refs/remotes/origin/NewRemoteForTestList"
				null).call().get(0);
		assertEquals("refs/heads/refs/remotes/origin/NewRemoteForTestList"
				newBranch.getName());
		assertEquals(2
		assertEquals(0
				- remoteBefore);
		assertEquals(2
				- allBefore);
	}

	public void testCreateForce() throws Exception {
		Ref newBranch = git.branch().setCreate("NewForce"
				secondCommit.getId().name()
		assertEquals(newBranch.getTarget().getObjectId()
		try {
			newBranch = git.branch().setCreate("NewForce"
					initialCommit.getId().name()
			fail("Should have failed");
		}
		newBranch = git.branch().setCreate("NewForce"
				initialCommit.getId().name()
		assertEquals(newBranch.getTarget().getObjectId()
	}

	public void testDelete() throws Exception {
		git.branch().setCreate("ForDelete"
				.get(0);
		git.branch().setDelete("ForDelete"
		git.branch().setCreate("ForDelete"
				null).call().get(0);
		try {
			git.branch().setDelete("ForDelete"
			fail("Deletion of a non-merged branch without force should have failed");
		} catch (NotMergedException e) {
		}
		git.branch().setDelete("ForDelete"
		git.branch().setCreate("ForDelete"
				.get(0);
		try {
			git.branch().setCreate("ForDelete"
					.get(0);
			fail("Repeated creation of same branch without force should fail");
		}
		Ref newBranch = git.branch().setCreate("ForDelete"
				initialCommit.name()
		assertEquals(newBranch.getTarget().getObjectId()
		newBranch = git.branch().setCreate("ForDelete"
				secondCommit.name()
		assertEquals(newBranch.getTarget().getObjectId()
		git.branch().setDelete("ForDelete"
	}

	public void testPullConfigRemoteBranch() throws Exception {
		Git localGit = setUpRepoWithRemote();
		Ref remote = localGit.branch().setList(ListMode.REMOTE).call().get(0);
		assertEquals("refs/remotes/origin/master"
		localGit.branch().setCreate("newFromRemote"
				null).call();
		assertEquals("origin"
				"branch"
		localGit.branch().setDelete("newFromRemote"
		assertNull(localGit.getRepository().getConfig().getString("branch"
				"newFromRemote"
		localGit.branch().setCreate("newFromRemote"
				SetupUpstreamMode.NOTRACK).call();
		assertNull(localGit.getRepository().getConfig().getString("branch"
				"newFromRemote"
		localGit.branch().setDelete("newFromRemote"
	}

	public void testPullConfigLocalBranch() throws Exception {
		Git localGit = setUpRepoWithRemote();
		localGit.branch().setCreate("newFromMaster"
				.call();
		assertNull(localGit.getRepository().getConfig().getString("branch"
				"newFromMaster"
		localGit.branch().setDelete("newFromMaster"
		localGit.branch().setCreate("newFromMaster"
				SetupUpstreamMode.TRACK).call();
		assertEquals("."
				"branch"
		localGit.branch().setDelete("newFromMaster"
		assertNull(localGit.getRepository().getConfig().getString("branch"
				"newFromRemote"
	}
}
