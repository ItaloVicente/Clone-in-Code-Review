package org.eclipse.jgit.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Properties;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.hooks.PrePushHook;
import org.eclipse.jgit.junit.JGitTestUtil;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.transport.PushResult;
import org.eclipse.jgit.transport.RefLeaseSpec;
import org.eclipse.jgit.transport.RefSpec;
import org.eclipse.jgit.transport.RemoteConfig;
import org.eclipse.jgit.transport.RemoteRefUpdate;
import org.eclipse.jgit.transport.TrackingRefUpdate;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.jgit.util.FS;
import org.junit.Test;

public class PushCommandTest extends RepositoryTestCase {

	@Test
	public void testPush() throws JGitInternalException
			GitAPIException

		Repository db2 = createWorkRepository();
		final StoredConfig config2 = db2.getConfig();

		config2.setString("fsck"
		config2.save();

		final StoredConfig config = db.getConfig();
		RemoteConfig remoteConfig = new RemoteConfig(config
		URIish uri = new URIish(db2.getDirectory().toURI().toURL());
		remoteConfig.addURI(uri);
		remoteConfig.update(config);
		config.save();

		try (Git git1 = new Git(db)) {
			RevCommit commit = git1.commit().setMessage("initial commit").call();
			Ref tagRef = git1.tag().setName("tag").call();

			try {
				db2.resolve(commit.getId().getName() + "^{commit}");
				fail("id shouldn't exist yet");
			} catch (MissingObjectException e) {
			}

			RefSpec spec = new RefSpec("refs/heads/master:refs/heads/x");
			git1.push().setRemote("test").setRefSpecs(spec)
					.call();

			assertEquals(commit.getId()
					db2.resolve(commit.getId().getName() + "^{commit}"));
			assertEquals(tagRef.getObjectId()
					db2.resolve(tagRef.getObjectId().getName()));
		}
	}

	@Test
	public void testPrePushHook() throws JGitInternalException
			GitAPIException

		Repository db2 = createWorkRepository();

		final StoredConfig config = db.getConfig();
		RemoteConfig remoteConfig = new RemoteConfig(config
		URIish uri = new URIish(db2.getDirectory().toURI().toURL());
		remoteConfig.addURI(uri);
		remoteConfig.update(config);
		config.save();

		File hookOutput = new File(getTemporaryDirectory()
		writeHookFile(PrePushHook.NAME
				+ hookOutput.toPath() + "\"\ncat - >>\"" + hookOutput.toPath()
				+ "\"\nexit 0");

		try (Git git1 = new Git(db)) {
			RevCommit commit = git1.commit().setMessage("initial commit").call();

			RefSpec spec = new RefSpec("refs/heads/master:refs/heads/x");
			git1.push().setRemote("test").setRefSpecs(spec).call();
			assertEquals("1:test
					+ commit.getName() + " refs/heads/x "
					+ ObjectId.zeroId().name() + "\n"
		}
	}

	private File writeHookFile(String name
			throws IOException {
		File path = new File(db.getWorkTree() + "/.git/hooks/"
		JGitTestUtil.write(path
		FS.DETECTED.setExecute(path
		return path;
	}


	@Test
	public void testTrackingUpdate() throws Exception {
		Repository db2 = createBareRepository();

		String remote = "origin";
		String branch = "refs/heads/master";
		String trackingBranch = "refs/remotes/" + remote + "/master";

		try (Git git = new Git(db)) {
			RevCommit commit1 = git.commit().setMessage("Initial commit")
					.call();

			RefUpdate branchRefUpdate = db.updateRef(branch);
			branchRefUpdate.setNewObjectId(commit1.getId());
			branchRefUpdate.update();

			RefUpdate trackingBranchRefUpdate = db.updateRef(trackingBranch);
			trackingBranchRefUpdate.setNewObjectId(commit1.getId());
			trackingBranchRefUpdate.update();

			final StoredConfig config = db.getConfig();
			RemoteConfig remoteConfig = new RemoteConfig(config
			URIish uri = new URIish(db2.getDirectory().toURI().toURL());
			remoteConfig.addURI(uri);
			remoteConfig.update(config);
			config.save();


			RevCommit commit2 = git.commit().setMessage("Commit to push").call();

			RefSpec spec = new RefSpec(branch + ":" + branch);
			Iterable<PushResult> resultIterable = git.push().setRemote(remote)
					.setRefSpecs(spec).call();

			PushResult result = resultIterable.iterator().next();
			TrackingRefUpdate trackingRefUpdate = result
					.getTrackingRefUpdate(trackingBranch);

			assertNotNull(trackingRefUpdate);
			assertEquals(trackingBranch
			assertEquals(branch
			assertEquals(commit2.getId()
			assertEquals(commit2.getId()
			assertEquals(commit2.getId()
		}
	}

	@Test
	public void testPushRefUpdate() throws Exception {
		try (Git git = new Git(db);
				Git git2 = new Git(createBareRepository())) {
			final StoredConfig config = git.getRepository().getConfig();
			RemoteConfig remoteConfig = new RemoteConfig(config
			URIish uri = new URIish(git2.getRepository().getDirectory().toURI()
					.toURL());
			remoteConfig.addURI(uri);
			remoteConfig.update(config);
			config.save();

			writeTrashFile("f"
			git.add().addFilepattern("f").call();
			RevCommit commit = git.commit().setMessage("adding f").call();

			assertEquals(null
			git.push().setRemote("test").call();
			assertEquals(commit.getId()
					git2.getRepository().resolve("refs/heads/master"));

			git.branchCreate().setName("refs/heads/test").call();
			git.checkout().setName("refs/heads/test").call();

			for (int i = 0; i < 6; i++) {
				writeTrashFile("f" + i
				git.add().addFilepattern("f" + i).call();
				commit = git.commit().setMessage("adding f" + i).call();
				git.push().setRemote("test").call();
				git2.getRepository().getRefDatabase().getRefs();
				assertEquals("failed to update on attempt " + i
						git2.getRepository().resolve("refs/heads/test"));
			}
		}
	}

	@Test
	public void testPushWithRefSpecFromConfig() throws Exception {
		try (Git git = new Git(db);
				Git git2 = new Git(createBareRepository())) {
			final StoredConfig config = git.getRepository().getConfig();
			RemoteConfig remoteConfig = new RemoteConfig(config
			URIish uri = new URIish(git2.getRepository().getDirectory().toURI()
					.toURL());
			remoteConfig.addURI(uri);
			remoteConfig.addPushRefSpec(new RefSpec("HEAD:refs/heads/newbranch"));
			remoteConfig.update(config);
			config.save();

			writeTrashFile("f"
			git.add().addFilepattern("f").call();
			RevCommit commit = git.commit().setMessage("adding f").call();

			assertEquals(null
			git.push().setRemote("test").call();
			assertEquals(commit.getId()
					git2.getRepository().resolve("refs/heads/newbranch"));
		}
	}

	@Test
	public void testPushWithoutPushRefSpec() throws Exception {
		try (Git git = new Git(db);
				Git git2 = new Git(createBareRepository())) {
			final StoredConfig config = git.getRepository().getConfig();
			RemoteConfig remoteConfig = new RemoteConfig(config
			URIish uri = new URIish(git2.getRepository().getDirectory().toURI()
					.toURL());
			remoteConfig.addURI(uri);
			remoteConfig.addFetchRefSpec(new RefSpec(
			remoteConfig.update(config);
			config.save();

			writeTrashFile("f"
			git.add().addFilepattern("f").call();
			RevCommit commit = git.commit().setMessage("adding f").call();

			git.checkout().setName("not-pushed").setCreateBranch(true).call();
			git.checkout().setName("branchtopush").setCreateBranch(true).call();

			assertEquals(null
					git2.getRepository().resolve("refs/heads/branchtopush"));
			assertEquals(null
					.resolve("refs/heads/not-pushed"));
			assertEquals(null
			git.push().setRemote("test").call();
			assertEquals(commit.getId()
					git2.getRepository().resolve("refs/heads/branchtopush"));
			assertEquals(null
					.resolve("refs/heads/not-pushed"));
			assertEquals(null
		}
	}

	@Test
	public void testPushAfterGC() throws Exception {
		Repository db2 = createWorkRepository();

		final StoredConfig config = db.getConfig();
		RemoteConfig remoteConfig = new RemoteConfig(config
		URIish uri = new URIish(db2.getDirectory().toURI().toURL());
		remoteConfig.addURI(uri);
		remoteConfig.update(config);
		config.save();

		try (Git git1 = new Git(db);
				Git git2 = new Git(db2)) {
			git1.commit().setMessage("initial commit").call();

			git1.push().setRemote("test").setRefSpecs(spec).call();

			git2.branchCreate().setName("refs/heads/other").call();
			git2.checkout().setName("refs/heads/other").call();

			writeTrashFile("a"
			git2.add().addFilepattern("a").call();
			RevCommit commit2 = git2.commit().setMessage("adding a").call();

			Properties res = git1.gc().setExpire(null).call();
			assertEquals(7

			writeTrashFile("b"
			git1.add().addFilepattern("b").call();
			RevCommit commit3 = git1.commit().setMessage("adding b").call();

			try {
				git1.push().setRemote("test").setRefSpecs(spec).call();
			} catch (TransportException e) {
				assertTrue("should be caused by a MissingObjectException"
						.getCause().getCause() instanceof MissingObjectException);
				fail("caught MissingObjectException for a change we don't have");
			}

			try {
				db.resolve(commit2.getId().getName() + "^{commit}");
				fail("id shouldn't exist locally");
			} catch (MissingObjectException e) {
			}
			assertEquals(commit2.getId()
					db2.resolve(commit2.getId().getName() + "^{commit}"));
			assertEquals(commit3.getId()
					db2.resolve(commit3.getId().getName() + "^{commit}"));
		}
	}

	@Test
	public void testPushWithLease() throws JGitInternalException
			GitAPIException

		Repository db2 = createWorkRepository();

		final StoredConfig config = db.getConfig();
		RemoteConfig remoteConfig = new RemoteConfig(config
		URIish uri = new URIish(db2.getDirectory().toURI().toURL());
		remoteConfig.addURI(uri);
		remoteConfig.update(config);
		config.save();

		try (Git git1 = new Git(db)) {
			RevCommit commit = git1.commit().setMessage("initial commit").call();
			git1.branchCreate().setName("initial").call();

			RefSpec spec = new RefSpec("refs/heads/master:refs/heads/x");
			git1.push().setRemote("test").setRefSpecs(spec)
					.call();

			assertEquals(commit.getId()
					db2.resolve(commit.getId().getName() + "^{commit}"));

			git1.commit().setMessage("second commit").call();
			Iterable<PushResult> results =
					git1.push().setRemote("test").setRefSpecs(spec)
							.setRefLeaseSpecs(new RefLeaseSpec("refs/heads/x"
							.call();
			for (PushResult result : results) {
				RemoteRefUpdate update = result.getRemoteUpdate("refs/heads/x");
				assertEquals(update.getStatus()
			}

			git1.commit().setMessage("third commit").call();

			results =
					git1.push().setRemote("test").setRefSpecs(spec)
							.setRefLeaseSpecs(new RefLeaseSpec("refs/heads/x"
							.call();
			for (PushResult result : results) {
				RemoteRefUpdate update = result.getRemoteUpdate("refs/heads/x");
				assertEquals(update.getStatus()
			}
		}
	}
}
