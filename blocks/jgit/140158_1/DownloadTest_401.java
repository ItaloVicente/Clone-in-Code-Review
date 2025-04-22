package org.eclipse.jgit.lfs.server.fs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.nio.file.Files;
import java.nio.file.Path;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.junit.JGitTestUtil;
import org.eclipse.jgit.junit.TestRepository;
import org.eclipse.jgit.lfs.BuiltinLFS;
import org.eclipse.jgit.lfs.lib.LongObjectId;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.util.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CheckoutTest extends LfsServerTest {

	Git git;
	private TestRepository tdb;

	@Override
	@Before
	public void setup() throws Exception {
		super.setup();

		BuiltinLFS.register();

		Path tmp = Files.createTempDirectory("jgit_test_");
		Repository db = FileRepositoryBuilder
				.create(tmp.resolve(".git").toFile());
		db.create();
		StoredConfig cfg = db.getConfig();
		cfg.setBoolean(ConfigConstants.CONFIG_FILTER_SECTION
				ConfigConstants.CONFIG_SECTION_LFS
				ConfigConstants.CONFIG_KEY_USEJGITBUILTIN
		cfg.setBoolean(ConfigConstants.CONFIG_FILTER_SECTION
				ConfigConstants.CONFIG_SECTION_LFS
				ConfigConstants.CONFIG_KEY_REQUIRED
		cfg.setString(ConfigConstants.CONFIG_SECTION_LFS
				server.getURI().toString() + "/lfs");
		cfg.save();

		tdb = new TestRepository<>(db);
		tdb.branch("test").commit()
				.add(".gitattributes"
						"*.bin filter=lfs diff=lfs merge=lfs -text ")
				.add("a.bin"
				.create();
		git = Git.wrap(db);
		tdb.branch("test2").commit().add(".gitattributes"
				"*.bin filter=lfs diff=lfs merge=lfs -text ").create();
	}

	@After
	public void cleanup() throws Exception {
		tdb.getRepository().close();
		FileUtils.delete(tdb.getRepository().getWorkTree()
				FileUtils.RECURSIVE);
	}

	@Test
	public void testUnknownContent() throws Exception {
		git.checkout().setName("test").call();
		assertEquals(
				JGitTestUtil.read(git.getRepository()
		assertEquals("[POST /lfs/objects/batch 200]"
				server.getRequests().toString());
	}

	@Test(expected = JGitInternalException.class)
	public void testUnknownContentRequired() throws Exception {
		StoredConfig cfg = tdb.getRepository().getConfig();
		cfg.setBoolean(ConfigConstants.CONFIG_FILTER_SECTION
				ConfigConstants.CONFIG_SECTION_LFS
				ConfigConstants.CONFIG_KEY_REQUIRED
		cfg.save();

		git.checkout().setName("test").call();
	}

	@Test
	public void testKnownContent() throws Exception {
		putContent(
				LongObjectId.fromString(
						"8bb0cf6eb9b17d0f7d22b456f121257dc1254e1f01665370476383ea776df414")
				"1234567");
		git.checkout().setName("test").call();
		assertEquals(
				"1234567"
				JGitTestUtil.read(git.getRepository()
		assertEquals(
				"[PUT /lfs/objects/8bb0cf6eb9b17d0f7d22b456f121257dc1254e1f01665370476383ea776df414 200"
						+ "
						+ "
				server.getRequests().toString());

		git.checkout().setName("test2").call();
		assertFalse(JGitTestUtil.check(git.getRepository()
		git.checkout().setName("test").call();
		assertEquals("1234567"
				JGitTestUtil.read(git.getRepository()
		assertEquals(3
	}

}
