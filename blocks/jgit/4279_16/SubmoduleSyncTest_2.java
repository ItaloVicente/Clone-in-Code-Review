package org.eclipse.jgit.submodule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.SubmoduleStatusCommand;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheEditor;
import org.eclipse.jgit.dircache.DirCacheEditor.PathEdit;
import org.eclipse.jgit.dircache.DirCacheEntry;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryTestCase;
import org.eclipse.jgit.storage.file.FileBasedConfig;
import org.junit.Test;

public class SubmoduleStatusTest extends RepositoryTestCase {

	@Test
	public void repositoryWithNoSubmodules() {
		SubmoduleStatusCommand command = new SubmoduleStatusCommand(db);
		Map<String
		assertNotNull(statuses);
		assertTrue(statuses.isEmpty());
	}

	@Test
	public void repositoryWithMissingSubmodule() throws IOException {
		final ObjectId id = ObjectId
				.fromString("abcd1234abcd1234abcd1234abcd1234abcd1234");
		final String path = "sub";
		DirCache cache = db.lockDirCache();
		DirCacheEditor editor = cache.editor();
		editor.add(new PathEdit(path) {

			public void apply(DirCacheEntry ent) {
				ent.setFileMode(FileMode.GITLINK);
				ent.setObjectId(id);
			}
		});
		editor.commit();

		SubmoduleStatusCommand command = new SubmoduleStatusCommand(db);
		Map<String
		assertNotNull(statuses);
		assertEquals(1
		Entry<String
				.next();
		assertNotNull(module);
		assertEquals(path
		SubmoduleStatus status = module.getValue();
		assertNotNull(status);
		assertEquals(path
		assertEquals(id
		assertEquals(SubmoduleStatusType.MISSING
	}

	@Test
	public void repositoryWithUninitializedSubmodule() throws IOException {
		final ObjectId id = ObjectId
				.fromString("abcd1234abcd1234abcd1234abcd1234abcd1234");
		final String path = "sub";
		DirCache cache = db.lockDirCache();
		DirCacheEditor editor = cache.editor();
		editor.add(new PathEdit(path) {

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

		SubmoduleStatusCommand command = new SubmoduleStatusCommand(db);
		Map<String
		assertNotNull(statuses);
		assertEquals(1
		Entry<String
				.next();
		assertNotNull(module);
		assertEquals(path
		SubmoduleStatus status = module.getValue();
		assertNotNull(status);
		assertEquals(path
		assertEquals(id
		assertEquals(SubmoduleStatusType.UNINITIALIZED
	}

	@Test
	public void repositoryWithNoHeadInSubmodule() throws IOException {
		final ObjectId id = ObjectId
				.fromString("abcd1234abcd1234abcd1234abcd1234abcd1234");
		final String path = "sub";
		DirCache cache = db.lockDirCache();
		DirCacheEditor editor = cache.editor();
		editor.add(new PathEdit(path) {

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

		Repository subRepo = Git.init().setBare(false)
				.setDirectory(new File(db.getWorkTree()
				.getRepository();
		assertNotNull(subRepo);

		SubmoduleStatusCommand command = new SubmoduleStatusCommand(db);
		Map<String
		assertNotNull(statuses);
		assertEquals(1
		Entry<String
				.next();
		assertNotNull(module);
		assertEquals(path
		SubmoduleStatus status = module.getValue();
		assertNotNull(status);
		assertEquals(path
		assertEquals(id
		assertEquals(SubmoduleStatusType.UNINITIALIZED
	}

	@Test
	public void repositoryWithInitializedSubmodule() throws IOException {
		final ObjectId id = ObjectId
				.fromString("abcd1234abcd1234abcd1234abcd1234abcd1234");
		final String path = "sub";
		DirCache cache = db.lockDirCache();
		DirCacheEditor editor = cache.editor();
		editor.add(new PathEdit(path) {

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

		Repository subRepo = Git.init().setBare(false)
				.setDirectory(new File(db.getWorkTree()
				.getRepository();
		assertNotNull(subRepo);

		RefUpdate update = subRepo.updateRef(Constants.HEAD
		update.setNewObjectId(id);
		update.forceUpdate();

		SubmoduleStatusCommand command = new SubmoduleStatusCommand(db);
		Map<String
		assertNotNull(statuses);
		assertEquals(1
		Entry<String
				.next();
		assertNotNull(module);
		assertEquals(path
		SubmoduleStatus status = module.getValue();
		assertNotNull(status);
		assertEquals(path
		assertEquals(id
		assertEquals(SubmoduleStatusType.INITIALIZED
	}

	@Test
	public void repositoryWithDifferentRevCheckedOutSubmodule()
			throws IOException {
		final ObjectId id = ObjectId
				.fromString("abcd1234abcd1234abcd1234abcd1234abcd1234");
		final String path = "sub";
		DirCache cache = db.lockDirCache();
		DirCacheEditor editor = cache.editor();
		editor.add(new PathEdit(path) {

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

		Repository subRepo = Git.init().setBare(false)
				.setDirectory(new File(db.getWorkTree()
				.getRepository();
		assertNotNull(subRepo);

		RefUpdate update = subRepo.updateRef(Constants.HEAD
		update.setNewObjectId(ObjectId
				.fromString("aaaa0000aaaa0000aaaa0000aaaa0000aaaa0000"));
		update.forceUpdate();

		SubmoduleStatusCommand command = new SubmoduleStatusCommand(db);
		Map<String
		assertNotNull(statuses);
		assertEquals(1
		Entry<String
				.next();
		assertNotNull(module);
		assertEquals(path
		SubmoduleStatus status = module.getValue();
		assertNotNull(status);
		assertEquals(path
		assertEquals(update.getNewObjectId()
		assertEquals(SubmoduleStatusType.REV_CHECKED_OUT
	}
}
