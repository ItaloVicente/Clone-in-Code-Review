package org.eclipse.jgit.submodule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.SubmoduleSyncCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheEditor;
import org.eclipse.jgit.dircache.DirCacheEditor.PathEdit;
import org.eclipse.jgit.dircache.DirCacheEntry;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.storage.file.FileBasedConfig;
import org.junit.Test;

public class SubmoduleSyncTest extends RepositoryTestCase {

	@Test
	public void repositoryWithNoSubmodules() throws GitAPIException {
		SubmoduleSyncCommand command = new SubmoduleSyncCommand(db);
		Map<String
		assertNotNull(modules);
		assertTrue(modules.isEmpty());
	}

	@Test
	public void repositoryWithSubmodule() throws Exception {
		writeTrashFile("file.txt"
		Git git = Git.wrap(db);
		git.add().addFilepattern("file.txt").call();
		git.commit().setMessage("create file").call();

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

		FileBasedConfig modulesConfig = new FileBasedConfig(new File(
				db.getWorkTree()
		modulesConfig.setString(ConfigConstants.CONFIG_SUBMODULE_SECTION
				ConfigConstants.CONFIG_KEY_PATH
		modulesConfig.setString(ConfigConstants.CONFIG_SUBMODULE_SECTION
				ConfigConstants.CONFIG_KEY_URL
		modulesConfig.save();

		Repository subRepo = Git.cloneRepository()
				.setURI(db.getDirectory().toURI().toString())
				.setDirectory(new File(db.getWorkTree()
				.getRepository();
		addRepoToClose(subRepo);
		assertNotNull(subRepo);

		SubmoduleWalk generator = SubmoduleWalk.forIndex(db);
		assertTrue(generator.next());
		assertNull(generator.getConfigUrl());
		assertEquals(url

		SubmoduleSyncCommand command = new SubmoduleSyncCommand(db);
		Map<String
		assertNotNull(synced);
		assertEquals(1
		Entry<String
		assertEquals(path
		assertEquals(url

		generator = SubmoduleWalk.forIndex(db);
		assertTrue(generator.next());
		assertEquals(url
		try (Repository subModRepository = generator.getRepository()) {
			StoredConfig submoduleConfig = subModRepository.getConfig();
			assertEquals(url
					submoduleConfig.getString(
							ConfigConstants.CONFIG_REMOTE_SECTION
							Constants.DEFAULT_REMOTE_NAME
							ConfigConstants.CONFIG_KEY_URL));
		}
	}

	@Test
	public void repositoryWithRelativeUriSubmodule() throws Exception {
		writeTrashFile("file.txt"
		Git git = Git.wrap(db);
		git.add().addFilepattern("file.txt").call();
		git.commit().setMessage("create file").call();

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

		FileBasedConfig config = db.getConfig();
		config.setString(ConfigConstants.CONFIG_REMOTE_SECTION
				Constants.DEFAULT_REMOTE_NAME
				base);
		config.save();

		FileBasedConfig modulesConfig = new FileBasedConfig(new File(
				db.getWorkTree()
		modulesConfig.setString(ConfigConstants.CONFIG_SUBMODULE_SECTION
				ConfigConstants.CONFIG_KEY_PATH
		modulesConfig.setString(ConfigConstants.CONFIG_SUBMODULE_SECTION
				ConfigConstants.CONFIG_KEY_URL
		modulesConfig.save();

		Repository subRepo = Git.cloneRepository()
				.setURI(db.getDirectory().toURI().toString())
				.setDirectory(new File(db.getWorkTree()
				.getRepository();
		assertNotNull(subRepo);
		addRepoToClose(subRepo);

		SubmoduleWalk generator = SubmoduleWalk.forIndex(db);
		assertTrue(generator.next());
		assertNull(generator.getConfigUrl());
		assertEquals(current

		modulesConfig.setString(ConfigConstants.CONFIG_SUBMODULE_SECTION
				ConfigConstants.CONFIG_KEY_URL
		modulesConfig.save();

		SubmoduleSyncCommand command = new SubmoduleSyncCommand(db);
		Map<String
		assertNotNull(synced);
		assertEquals(1
		Entry<String
		assertEquals(path

		generator = SubmoduleWalk.forIndex(db);
		assertTrue(generator.next());
		try (Repository subModRepository1 = generator.getRepository()) {
			StoredConfig submoduleConfig = subModRepository1.getConfig();
					submoduleConfig.getString(
							ConfigConstants.CONFIG_REMOTE_SECTION
							Constants.DEFAULT_REMOTE_NAME
							ConfigConstants.CONFIG_KEY_URL));
		}
	}
}
