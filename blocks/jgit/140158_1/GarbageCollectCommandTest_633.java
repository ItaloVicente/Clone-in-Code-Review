package org.eclipse.jgit.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.jgit.junit.JGitTestUtil;
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
import org.eclipse.jgit.transport.TagOpt;
import org.eclipse.jgit.transport.TrackingRefUpdate;
import org.eclipse.jgit.transport.URIish;
import org.junit.Before;
import org.junit.Test;

public class FetchCommandTest extends RepositoryTestCase {

	private Git git;
	private Git remoteGit;

	@Before
	public void setupRemoteRepository() throws Exception {
		git = new Git(db);

		Repository remoteRepository = createWorkRepository();
		remoteGit = new Git(remoteRepository);

		final StoredConfig config = db.getConfig();
		RemoteConfig remoteConfig = new RemoteConfig(config
		URIish uri = new URIish(remoteRepository.getDirectory().toURI().toURL());
		remoteConfig.addURI(uri);
		remoteConfig.update(config);
		config.save();
	}

	@Test
	public void testFetch() throws Exception {

		RevCommit commit = remoteGit.commit().setMessage("initial commit").call();
		Ref tagRef = remoteGit.tag().setName("tag").call();

		git.fetch().setRemote("test")
				.setRefSpecs("refs/heads/master:refs/heads/x").call();

		assertEquals(commit.getId()
				db.resolve(commit.getId().getName() + "^{commit}"));
		assertEquals(tagRef.getObjectId()
				db.resolve(tagRef.getObjectId().getName()));
	}

	@Test
	public void testForcedFetch() throws Exception {
		remoteGit.commit().setMessage("commit").call();
		remoteGit.commit().setMessage("commit2").call();
		git.fetch().setRemote("test")
				.setRefSpecs("refs/heads/master:refs/heads/master").call();

		remoteGit.commit().setAmend(true).setMessage("amended").call();
		FetchResult res = git.fetch().setRemote("test")
				.setRefSpecs("refs/heads/master:refs/heads/master").call();
		assertEquals(RefUpdate.Result.REJECTED
				res.getTrackingRefUpdate("refs/heads/master").getResult());
		res = git.fetch().setRemote("test")
				.setRefSpecs("refs/heads/master:refs/heads/master")
				.setForceUpdate(true).call();
		assertEquals(RefUpdate.Result.FORCED
				res.getTrackingRefUpdate("refs/heads/master").getResult());
	}

	@Test
	public void fetchAddsBranches() throws Exception {
		final String branch1 = "b1";
		final String branch2 = "b2";
		final String remoteBranch1 = "test/" + branch1;
		final String remoteBranch2 = "test/" + branch2;
		remoteGit.commit().setMessage("commit").call();
		Ref branchRef1 = remoteGit.branchCreate().setName(branch1).call();
		remoteGit.commit().setMessage("commit").call();
		Ref branchRef2 = remoteGit.branchCreate().setName(branch2).call();

		git.fetch().setRemote("test").setRefSpecs(spec).call();
		assertEquals(branchRef1.getObjectId()
		assertEquals(branchRef2.getObjectId()
	}

	@Test
	public void fetchDoesntDeleteBranches() throws Exception {
		final String branch1 = "b1";
		final String branch2 = "b2";
		final String remoteBranch1 = "test/" + branch1;
		final String remoteBranch2 = "test/" + branch2;
		remoteGit.commit().setMessage("commit").call();
		Ref branchRef1 = remoteGit.branchCreate().setName(branch1).call();
		remoteGit.commit().setMessage("commit").call();
		Ref branchRef2 = remoteGit.branchCreate().setName(branch2).call();

		git.fetch().setRemote("test").setRefSpecs(spec).call();
		assertEquals(branchRef1.getObjectId()
		assertEquals(branchRef2.getObjectId()

		remoteGit.branchDelete().setBranchNames(branch1).call();
		git.fetch().setRemote("test").setRefSpecs(spec).call();
		assertEquals(branchRef1.getObjectId()
		assertEquals(branchRef2.getObjectId()
	}

	@Test
	public void fetchUpdatesBranches() throws Exception {
		final String branch1 = "b1";
		final String branch2 = "b2";
		final String remoteBranch1 = "test/" + branch1;
		final String remoteBranch2 = "test/" + branch2;
		remoteGit.commit().setMessage("commit").call();
		Ref branchRef1 = remoteGit.branchCreate().setName(branch1).call();
		remoteGit.commit().setMessage("commit").call();
		Ref branchRef2 = remoteGit.branchCreate().setName(branch2).call();

		git.fetch().setRemote("test").setRefSpecs(spec).call();
		assertEquals(branchRef1.getObjectId()
		assertEquals(branchRef2.getObjectId()

		remoteGit.commit().setMessage("commit").call();
		branchRef2 = remoteGit.branchCreate().setName(branch2).setForce(true).call();
		git.fetch().setRemote("test").setRefSpecs(spec).call();
		assertEquals(branchRef1.getObjectId()
		assertEquals(branchRef2.getObjectId()
	}

	@Test
	public void fetchPrunesBranches() throws Exception {
		final String branch1 = "b1";
		final String branch2 = "b2";
		final String remoteBranch1 = "test/" + branch1;
		final String remoteBranch2 = "test/" + branch2;
		remoteGit.commit().setMessage("commit").call();
		Ref branchRef1 = remoteGit.branchCreate().setName(branch1).call();
		remoteGit.commit().setMessage("commit").call();
		Ref branchRef2 = remoteGit.branchCreate().setName(branch2).call();

		git.fetch().setRemote("test").setRefSpecs(spec).call();
		assertEquals(branchRef1.getObjectId()
		assertEquals(branchRef2.getObjectId()

		remoteGit.branchDelete().setBranchNames(branch1).call();
		git.fetch().setRemote("test").setRefSpecs(spec)
				.setRemoveDeletedRefs(true).call();
		assertNull(db.resolve(remoteBranch1));
		assertEquals(branchRef2.getObjectId()
	}

	@Test
	public void fetchShouldAutoFollowTag() throws Exception {
		remoteGit.commit().setMessage("commit").call();
		Ref tagRef = remoteGit.tag().setName("foo").call();

		git.fetch().setRemote("test")
				.setTagOpt(TagOpt.AUTO_FOLLOW).call();

		assertEquals(tagRef.getObjectId()
	}

	@Test
	public void fetchShouldAutoFollowTagForFetchedObjects() throws Exception {
		remoteGit.commit().setMessage("commit").call();
		Ref tagRef = remoteGit.tag().setName("foo").call();
		remoteGit.commit().setMessage("commit2").call();
		git.fetch().setRemote("test")
				.setTagOpt(TagOpt.AUTO_FOLLOW).call();
		assertEquals(tagRef.getObjectId()
	}

	@Test
	public void fetchShouldNotFetchTagsFromOtherBranches() throws Exception {
		remoteGit.commit().setMessage("commit").call();
		remoteGit.checkout().setName("other").setCreateBranch(true).call();
		remoteGit.commit().setMessage("commit2").call();
		remoteGit.tag().setName("foo").call();
		git.fetch().setRemote("test")
				.setRefSpecs("refs/heads/master:refs/remotes/origin/master")
				.setTagOpt(TagOpt.AUTO_FOLLOW).call();
		assertNull(db.resolve("foo"));
	}

	@Test
	public void fetchWithUpdatedTagShouldNotTryToUpdateLocal() throws Exception {
		final String tagName = "foo";
		remoteGit.commit().setMessage("commit").call();
		Ref tagRef = remoteGit.tag().setName(tagName).call();
		ObjectId originalId = tagRef.getObjectId();

		git.fetch().setRemote("test").setRefSpecs(spec)
				.setTagOpt(TagOpt.AUTO_FOLLOW).call();
		assertEquals(originalId

		remoteGit.commit().setMessage("commit 2").call();
		remoteGit.tag().setName(tagName).setForceUpdate(true).call();

		FetchResult result = git.fetch().setRemote("test").setRefSpecs(spec)
				.setTagOpt(TagOpt.AUTO_FOLLOW).call();

		Collection<TrackingRefUpdate> refUpdates = result
				.getTrackingRefUpdates();
		assertEquals(1
		TrackingRefUpdate update = refUpdates.iterator().next();
		assertEquals("refs/heads/master"

		assertEquals(originalId
	}

	@Test
	public void fetchWithExplicitTagsShouldUpdateLocal() throws Exception {
		final String tagName = "foo";
		remoteGit.commit().setMessage("commit").call();
		Ref tagRef1 = remoteGit.tag().setName(tagName).call();

		git.fetch().setRemote("test").setRefSpecs(spec)
				.setTagOpt(TagOpt.AUTO_FOLLOW).call();
		assertEquals(tagRef1.getObjectId()

		remoteGit.commit().setMessage("commit 2").call();
		Ref tagRef2 = remoteGit.tag().setName(tagName).setForceUpdate(true)
				.call();

		FetchResult result = git.fetch().setRemote("test").setRefSpecs(spec)
				.setTagOpt(TagOpt.FETCH_TAGS).call();
		TrackingRefUpdate update = result.getTrackingRefUpdate(Constants.R_TAGS
				+ tagName);
		assertEquals(RefUpdate.Result.FORCED
		assertEquals(tagRef2.getObjectId()
	}

	@Test
	public void testFetchWithPruneShouldKeepOriginHead() throws Exception {
		commitFile("foo"
		Git cloned = Git.cloneRepository()
				.setDirectory(createTempDirectory("testCloneRepository"))
						+ git.getRepository().getWorkTree().getAbsolutePath())
				.call();
		assertNotNull(cloned);
		Repository clonedRepo = cloned.getRepository();
		addRepoToClose(clonedRepo);
		ObjectId originMasterId = clonedRepo
				.resolve("refs/remotes/origin/master");
		assertNotNull("Should have origin/master"
		assertNotEquals("origin/master should not be zero ID"
				ObjectId.zeroId()
		ObjectId originHeadId = clonedRepo.resolve("refs/remotes/origin/HEAD");
		if (originHeadId == null) {
			JGitTestUtil.write(
					new File(clonedRepo.getDirectory()
							"refs/remotes/origin/HEAD")
					"ref: refs/remotes/origin/master\n");
			originHeadId = clonedRepo.resolve("refs/remotes/origin/HEAD");
		}
		assertEquals("Should have origin/HEAD"
		FetchResult result = cloned.fetch().setRemote("origin")
				.setRemoveDeletedRefs(true).call();
		assertTrue("Fetch after clone should be up-to-date"
				result.getTrackingRefUpdates().isEmpty());
		assertEquals("origin/master should still exist"
				clonedRepo.resolve("refs/remotes/origin/master"));
		assertEquals("origin/HEAD should be unchanged"
				clonedRepo.resolve("refs/remotes/origin/HEAD"));
	}

	@Test
	public void fetchAddRefsWithDuplicateRefspec() throws Exception {
		final String branchName = "branch";
		final String remoteBranchName = "test/" + branchName;
		remoteGit.commit().setMessage("commit").call();
		Ref branchRef = remoteGit.branchCreate().setName(branchName).call();

		final StoredConfig config = db.getConfig();
		RemoteConfig remoteConfig = new RemoteConfig(config
		remoteConfig.addFetchRefSpec(new RefSpec(spec1));
		remoteConfig.addFetchRefSpec(new RefSpec(spec2));
		remoteConfig.update(config);

		git.fetch().setRemote("test").setRefSpecs(spec1).call();
		assertEquals(branchRef.getObjectId()
	}

	@Test
	public void fetchPruneRefsWithDuplicateRefspec()
			throws Exception {
		final String branchName = "branch";
		final String remoteBranchName = "test/" + branchName;
		remoteGit.commit().setMessage("commit").call();
		Ref branchRef = remoteGit.branchCreate().setName(branchName).call();

		final StoredConfig config = db.getConfig();
		RemoteConfig remoteConfig = new RemoteConfig(config
		remoteConfig.addFetchRefSpec(new RefSpec(spec1));
		remoteConfig.addFetchRefSpec(new RefSpec(spec2));
		remoteConfig.update(config);

		git.fetch().setRemote("test").setRefSpecs(spec1).call();
		assertEquals(branchRef.getObjectId()

		remoteGit.branchDelete().setBranchNames(branchName).call();
		git.fetch().setRemote("test").setRefSpecs(spec1)
				.setRemoveDeletedRefs(true).call();
		assertNull(db.resolve(remoteBranchName));
	}

	@Test
	public void fetchUpdateRefsWithDuplicateRefspec() throws Exception {
		final String tagName = "foo";
		remoteGit.commit().setMessage("commit").call();
		Ref tagRef1 = remoteGit.tag().setName(tagName).call();
		List<RefSpec> refSpecs = new ArrayList<>();
		git.fetch().setRemote("test").setRefSpecs(refSpecs)
				.setTagOpt(TagOpt.AUTO_FOLLOW).call();
		assertEquals(tagRef1.getObjectId()

		remoteGit.commit().setMessage("commit 2").call();
		Ref tagRef2 = remoteGit.tag().setName(tagName).setForceUpdate(true)
				.call();
		FetchResult result = git.fetch().setRemote("test").setRefSpecs(refSpecs)
				.setTagOpt(TagOpt.FETCH_TAGS).call();
		assertEquals(2
		TrackingRefUpdate update = result
				.getTrackingRefUpdate(Constants.R_TAGS + tagName);
		assertEquals(RefUpdate.Result.FORCED
		assertEquals(tagRef2.getObjectId()
	}
}
