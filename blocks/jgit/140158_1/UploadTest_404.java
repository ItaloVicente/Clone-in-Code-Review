package org.eclipse.jgit.lfs.server.fs;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.Assert.assertEquals;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.RemoteAddCommand;
import org.eclipse.jgit.junit.JGitTestUtil;
import org.eclipse.jgit.junit.TestRepository;
import org.eclipse.jgit.lfs.BuiltinLFS;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.PathFilter;
import org.eclipse.jgit.util.FileUtils;
import org.eclipse.jgit.util.IO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PushTest extends LfsServerTest {

	Git git;

	private TestRepository localDb;

	private Repository remoteDb;

	@Override
	@Before
	public void setup() throws Exception {
		super.setup();

		BuiltinLFS.register();

		Path rtmp = Files.createTempDirectory("jgit_test_");
		remoteDb = FileRepositoryBuilder.create(rtmp.toFile());
		remoteDb.create(true);

		Path tmp = Files.createTempDirectory("jgit_test_");
		Repository db = FileRepositoryBuilder
				.create(tmp.resolve(".git").toFile());
		db.create(false);
		StoredConfig cfg = db.getConfig();
		cfg.setString("filter"
		cfg.setString("lfs"
		cfg.save();

		localDb = new TestRepository<>(db);
		localDb.branch("master").commit().add(".gitattributes"
				"*.bin filter=lfs diff=lfs merge=lfs -text ").create();
		git = Git.wrap(db);

		URIish uri = new URIish(
		RemoteAddCommand radd = git.remoteAdd();
		radd.setUri(uri);
		radd.setName(Constants.DEFAULT_REMOTE_NAME);
		radd.call();

		git.checkout().setName("master").call();
		git.push().call();
	}

	@After
	public void cleanup() throws Exception {
		remoteDb.close();
		localDb.getRepository().close();
		FileUtils.delete(localDb.getRepository().getWorkTree()
				FileUtils.RECURSIVE);
		FileUtils.delete(remoteDb.getDirectory()
	}

	@Test
	public void testPushSimple() throws Exception {
		JGitTestUtil.writeTrashFile(localDb.getRepository()
				"1234567");
		git.add().addFilepattern("a.bin").call();
		RevCommit commit = git.commit().setMessage("add lfs blob").call();
		git.push().call();

		ObjectId id = commit.getId();
		try (RevWalk walk = new RevWalk(remoteDb)) {
			RevCommit rc = walk.parseCommit(id);
			try (TreeWalk tw = new TreeWalk(walk.getObjectReader())) {
				tw.addTree(rc.getTree());
				tw.setFilter(PathFilter.create("a.bin"));
				tw.next();

				assertEquals(tw.getPathString()
				ObjectLoader ldr = walk.getObjectReader()
						.open(tw.getObjectId(0)
				try(InputStream is = ldr.openStream()) {
					assertEquals(
							new String(IO
									.readWholeStream(is
											(int) ldr.getSize())
									.array()
				}
			}

		}

		assertEquals(
				"[POST /lfs/objects/batch 200
				server.getRequests().toString());
	}

}
