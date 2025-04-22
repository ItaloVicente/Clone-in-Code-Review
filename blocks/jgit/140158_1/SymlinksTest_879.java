package org.eclipse.jgit.submodule;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.eclipse.jgit.lib.ConfigConstants.CONFIG_KEY_PATH;
import static org.eclipse.jgit.lib.ConfigConstants.CONFIG_KEY_URL;
import static org.eclipse.jgit.lib.ConfigConstants.CONFIG_SUBMODULE_SECTION;
import static org.eclipse.jgit.lib.Constants.DOT_GIT_MODULES;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheEditor;
import org.eclipse.jgit.dircache.DirCacheEditor.PathEdit;
import org.eclipse.jgit.dircache.DirCacheEntry;
import org.eclipse.jgit.errors.ConfigInvalidException;
import org.eclipse.jgit.errors.NoWorkTreeException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.junit.TestRepository;
import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevBlob;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.treewalk.filter.PathFilter;
import org.junit.Before;
import org.junit.Test;

public class SubmoduleWalkTest extends RepositoryTestCase {
	private TestRepository<Repository> testDb;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		testDb = new TestRepository<>(db);
	}

	@Test
	public void repositoryWithNoSubmodules() throws IOException {
		SubmoduleWalk gen = SubmoduleWalk.forIndex(db);
		assertFalse(gen.next());
		assertNull(gen.getPath());
		assertEquals(ObjectId.zeroId()
	}

	@Test
	public void bareRepositoryWithNoSubmodules() throws IOException {
		FileRepository bareRepo = createBareRepository();
		boolean result = SubmoduleWalk.containsGitModulesFile(bareRepo);
		assertFalse(result);
	}

	@Test
	public void repositoryWithRootLevelSubmodule() throws IOException
			ConfigInvalidException
		final ObjectId id = ObjectId
				.fromString("abcd1234abcd1234abcd1234abcd1234abcd1234");
		final String path = "sub";
		DirCache cache = db.lockDirCache();
		DirCacheEditor editor = cache.editor();
		editor.add(new PathEdit(path) {

			@Override
			public void apply(DirCacheEntry ent) {
				ent.setFileMode(FileMode.GITLINK);
				ent.setObjectId(id);
			}
		});
		editor.commit();

		SubmoduleWalk gen = SubmoduleWalk.forIndex(db);
		assertTrue(gen.next());
		assertEquals(path
		assertEquals(id
		assertEquals(new File(db.getWorkTree()
		assertNull(gen.getConfigUpdate());
		assertNull(gen.getConfigUrl());
		assertNull(gen.getModulesPath());
		assertNull(gen.getModulesUpdate());
		assertNull(gen.getModulesUrl());
		assertNull(gen.getRepository());
		Status status = Git.wrap(db).status().call();
		assertTrue(!status.isClean());
		assertFalse(gen.next());
	}

	@Test
	public void repositoryWithRootLevelSubmoduleAbsoluteRef()
			throws IOException
		final ObjectId id = ObjectId
				.fromString("abcd1234abcd1234abcd1234abcd1234abcd1234");
		final String path = "sub";
		File dotGit = new File(db.getWorkTree()
				+ Constants.DOT_GIT);
		if (!dotGit.getParentFile().exists())
			dotGit.getParentFile().mkdirs();

		File modulesGitDir = new File(db.getDirectory()
				"modules" + File.separatorChar + path);
		try (BufferedWriter fw = Files.newBufferedWriter(dotGit.toPath()
				UTF_8)) {
			fw.append("gitdir: " + modulesGitDir.getAbsolutePath());
		}
		FileRepositoryBuilder builder = new FileRepositoryBuilder();
		builder.setWorkTree(new File(db.getWorkTree()
		builder.build().create();

		DirCache cache = db.lockDirCache();
		DirCacheEditor editor = cache.editor();
		editor.add(new PathEdit(path) {

			@Override
			public void apply(DirCacheEntry ent) {
				ent.setFileMode(FileMode.GITLINK);
				ent.setObjectId(id);
			}
		});
		editor.commit();

		SubmoduleWalk gen = SubmoduleWalk.forIndex(db);
		assertTrue(gen.next());
		assertEquals(path
		assertEquals(id
		assertEquals(new File(db.getWorkTree()
		assertNull(gen.getConfigUpdate());
		assertNull(gen.getConfigUrl());
		assertNull(gen.getModulesPath());
		assertNull(gen.getModulesUpdate());
		assertNull(gen.getModulesUrl());
		try (Repository subRepo = gen.getRepository()) {
			assertNotNull(subRepo);
			assertEquals(modulesGitDir.getAbsolutePath()
					subRepo.getDirectory().getAbsolutePath());
			assertEquals(new File(db.getWorkTree()
					subRepo.getWorkTree().getAbsolutePath());
		}
		assertFalse(gen.next());
	}

	@Test
	public void repositoryWithRootLevelSubmoduleRelativeRef()
			throws IOException
		final ObjectId id = ObjectId
				.fromString("abcd1234abcd1234abcd1234abcd1234abcd1234");
		final String path = "sub";
		File dotGit = new File(db.getWorkTree()
				+ Constants.DOT_GIT);
		if (!dotGit.getParentFile().exists())
			dotGit.getParentFile().mkdirs();

		File modulesGitDir = new File(db.getDirectory()
				+ File.separatorChar + path);
		try (BufferedWriter fw = Files.newBufferedWriter(dotGit.toPath()
				UTF_8)) {
			fw.append("gitdir: " + "../" + Constants.DOT_GIT + "/modules/"
					+ path);
		}
		FileRepositoryBuilder builder = new FileRepositoryBuilder();
		builder.setWorkTree(new File(db.getWorkTree()
		builder.build().create();

		DirCache cache = db.lockDirCache();
		DirCacheEditor editor = cache.editor();
		editor.add(new PathEdit(path) {

			@Override
			public void apply(DirCacheEntry ent) {
				ent.setFileMode(FileMode.GITLINK);
				ent.setObjectId(id);
			}
		});
		editor.commit();

		SubmoduleWalk gen = SubmoduleWalk.forIndex(db);
		assertTrue(gen.next());
		assertEquals(path
		assertEquals(id
		assertEquals(new File(db.getWorkTree()
		assertNull(gen.getConfigUpdate());
		assertNull(gen.getConfigUrl());
		assertNull(gen.getModulesPath());
		assertNull(gen.getModulesUpdate());
		assertNull(gen.getModulesUrl());
		try (Repository subRepo = gen.getRepository()) {
			assertNotNull(subRepo);
			assertEqualsFile(modulesGitDir
			assertEqualsFile(new File(db.getWorkTree()
					subRepo.getWorkTree());
			subRepo.close();
			assertFalse(gen.next());
		}
	}

	@Test
	public void repositoryWithNestedSubmodule() throws IOException
			ConfigInvalidException {
		final ObjectId id = ObjectId
				.fromString("abcd1234abcd1234abcd1234abcd1234abcd1234");
		final String path = "sub/dir/final";
		DirCache cache = db.lockDirCache();
		DirCacheEditor editor = cache.editor();
		editor.add(new PathEdit(path) {

			@Override
			public void apply(DirCacheEntry ent) {
				ent.setFileMode(FileMode.GITLINK);
				ent.setObjectId(id);
			}
		});
		editor.commit();

		SubmoduleWalk gen = SubmoduleWalk.forIndex(db);
		assertTrue(gen.next());
		assertEquals(path
		assertEquals(id
		assertEquals(new File(db.getWorkTree()
		assertNull(gen.getConfigUpdate());
		assertNull(gen.getConfigUrl());
		assertNull(gen.getModulesPath());
		assertNull(gen.getModulesUpdate());
		assertNull(gen.getModulesUrl());
		assertNull(gen.getRepository());
		assertFalse(gen.next());
	}

	@Test
	public void generatorFilteredToOneOfTwoSubmodules() throws IOException {
		final ObjectId id1 = ObjectId
				.fromString("abcd1234abcd1234abcd1234abcd1234abcd1234");
		final String path1 = "sub1";
		final ObjectId id2 = ObjectId
				.fromString("abcd1234abcd1234abcd1234abcd1234abcd1235");
		final String path2 = "sub2";
		DirCache cache = db.lockDirCache();
		DirCacheEditor editor = cache.editor();
		editor.add(new PathEdit(path1) {

			@Override
			public void apply(DirCacheEntry ent) {
				ent.setFileMode(FileMode.GITLINK);
				ent.setObjectId(id1);
			}
		});
		editor.add(new PathEdit(path2) {

			@Override
			public void apply(DirCacheEntry ent) {
				ent.setFileMode(FileMode.GITLINK);
				ent.setObjectId(id2);
			}
		});
		editor.commit();

		SubmoduleWalk gen = SubmoduleWalk.forIndex(db);
		gen.setFilter(PathFilter.create(path1));
		assertTrue(gen.next());
		assertEquals(path1
		assertEquals(id1
		assertFalse(gen.next());
	}

	@Test
	public void indexWithGitmodules() throws Exception {
		final ObjectId subId = ObjectId
				.fromString("abcd1234abcd1234abcd1234abcd1234abcd1234");
		final String path = "sub";

		final Config gitmodules = new Config();
		gitmodules.setString(CONFIG_SUBMODULE_SECTION
				"sub");
		gitmodules.setString(CONFIG_SUBMODULE_SECTION
		final RevBlob gitmodulesBlob = testDb.blob(gitmodules.toText());

		gitmodules.setString(CONFIG_SUBMODULE_SECTION
		writeTrashFile(DOT_GIT_MODULES

		DirCache cache = db.lockDirCache();
		DirCacheEditor editor = cache.editor();
		editor.add(new PathEdit(path) {

			@Override
			public void apply(DirCacheEntry ent) {
				ent.setFileMode(FileMode.GITLINK);
				ent.setObjectId(subId);
			}
		});
		editor.add(new PathEdit(DOT_GIT_MODULES) {

			@Override
			public void apply(DirCacheEntry ent) {
				ent.setFileMode(FileMode.REGULAR_FILE);
				ent.setObjectId(gitmodulesBlob);
			}
		});
		editor.commit();

		SubmoduleWalk gen = SubmoduleWalk.forIndex(db);
		assertTrue(gen.next());
		assertEquals(path
		assertEquals(subId
		assertEquals(new File(db.getWorkTree()
		assertNull(gen.getConfigUpdate());
		assertNull(gen.getConfigUrl());
		assertEquals("sub"
		assertNull(gen.getModulesUpdate());
		assertNull(gen.getRepository());
		assertFalse(gen.next());
	}

	@Test
	public void treeIdWithGitmodules() throws Exception {
		final ObjectId subId = ObjectId
				.fromString("abcd1234abcd1234abcd1234abcd1234abcd1234");
		final String path = "sub";

		final Config gitmodules = new Config();
		gitmodules.setString(CONFIG_SUBMODULE_SECTION
				"sub");
		gitmodules.setString(CONFIG_SUBMODULE_SECTION

		RevCommit commit = testDb.getRevWalk().parseCommit(testDb.commit()
				.noParents()
				.add(DOT_GIT_MODULES
				.edit(new PathEdit(path) {

							@Override
							public void apply(DirCacheEntry ent) {
								ent.setFileMode(FileMode.GITLINK);
								ent.setObjectId(subId);
							}
						})
				.create());

		SubmoduleWalk gen = SubmoduleWalk.forPath(db
		assertEquals(path
		assertEquals(subId
		assertEquals(new File(db.getWorkTree()
		assertNull(gen.getConfigUpdate());
		assertNull(gen.getConfigUrl());
		assertEquals("sub"
		assertNull(gen.getModulesUpdate());
		assertNull(gen.getRepository());
		assertFalse(gen.next());
	}

	@Test
	public void testTreeIteratorWithGitmodules() throws Exception {
		final ObjectId subId = ObjectId
				.fromString("abcd1234abcd1234abcd1234abcd1234abcd1234");
		final String path = "sub";

		final Config gitmodules = new Config();
		gitmodules.setString(CONFIG_SUBMODULE_SECTION
				"sub");
		gitmodules.setString(CONFIG_SUBMODULE_SECTION

		RevCommit commit = testDb.getRevWalk().parseCommit(testDb.commit()
				.noParents()
				.add(DOT_GIT_MODULES
				.edit(new PathEdit(path) {

							@Override
							public void apply(DirCacheEntry ent) {
								ent.setFileMode(FileMode.GITLINK);
								ent.setObjectId(subId);
							}
						})
				.create());

		final CanonicalTreeParser p = new CanonicalTreeParser();
		p.reset(testDb.getRevWalk().getObjectReader()
		SubmoduleWalk gen = SubmoduleWalk.forPath(db
		assertEquals(path
		assertEquals(subId
		assertEquals(new File(db.getWorkTree()
		assertNull(gen.getConfigUpdate());
		assertNull(gen.getConfigUrl());
		assertEquals("sub"
		assertNull(gen.getModulesUpdate());
		assertNull(gen.getRepository());
		assertFalse(gen.next());
	}

	@Test
	public void testTreeIteratorWithGitmodulesNameNotPath() throws Exception {
		final ObjectId subId = ObjectId
				.fromString("abcd1234abcd1234abcd1234abcd1234abcd1234");
		final String path = "sub";
		final String arbitraryName = "x";

		final Config gitmodules = new Config();
		gitmodules.setString(CONFIG_SUBMODULE_SECTION
				CONFIG_KEY_PATH
		gitmodules.setString(CONFIG_SUBMODULE_SECTION
				CONFIG_KEY_URL

		RevCommit commit = testDb.getRevWalk()
				.parseCommit(testDb.commit().noParents()
						.add(DOT_GIT_MODULES
						.edit(new PathEdit(path) {

							@Override
							public void apply(DirCacheEntry ent) {
								ent.setFileMode(FileMode.GITLINK);
								ent.setObjectId(subId);
							}
						}).create());

		final CanonicalTreeParser p = new CanonicalTreeParser();
		p.reset(testDb.getRevWalk().getObjectReader()
		SubmoduleWalk gen = SubmoduleWalk.forPath(db
		assertEquals(path
		assertEquals(subId
		assertEquals(new File(db.getWorkTree()
		assertNull(gen.getConfigUpdate());
		assertNull(gen.getConfigUrl());
		assertEquals("sub"
		assertNull(gen.getModulesUpdate());
		assertNull(gen.getRepository());
		assertFalse(gen.next());
	}
}
