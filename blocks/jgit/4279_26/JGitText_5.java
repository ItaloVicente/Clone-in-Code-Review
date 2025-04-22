package org.eclipse.jgit.submodule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.SubmoduleUpdateCommand;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheEditor;
import org.eclipse.jgit.dircache.DirCacheEditor.PathEdit;
import org.eclipse.jgit.dircache.DirCacheEntry;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryTestCase;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileBasedConfig;
import org.junit.Test;

public class SubmoduleUpdateTest extends RepositoryTestCase {

	@Test
	public void repositoryWithNoSubmodules() {
		SubmoduleUpdateCommand command = new SubmoduleUpdateCommand(db);
		Collection<String> modules = command.call();
		assertNotNull(modules);
		assertTrue(modules.isEmpty());
	}

	@Test
	public void repositoryWithSubmodule() throws Exception {
		writeTrashFile("file.txt"
		Git git = Git.wrap(db);
		git.add().addFilepattern("file.txt").call();
		final RevCommit commit = git.commit().setMessage("create file").call();

		final String path = "sub";
		DirCache cache = db.lockDirCache();
		DirCacheEditor editor = cache.editor();
		editor.add(new PathEdit(path) {

			public void apply(DirCacheEntry ent) {
				ent.setFileMode(FileMode.GITLINK);
				ent.setObjectId(commit);
			}
		});
		editor.commit();

		StoredConfig config = db.getConfig();
		config.setString(ConfigConstants.CONFIG_SUBMODULE_SECTION
				ConfigConstants.CONFIG_KEY_URL
						.toString());
		config.save();

		FileBasedConfig modulesConfig = new FileBasedConfig(new File(
				db.getWorkTree()
		modulesConfig.setString(ConfigConstants.CONFIG_SUBMODULE_SECTION
				ConfigConstants.CONFIG_KEY_PATH
		modulesConfig.save();

		SubmoduleUpdateCommand command = new SubmoduleUpdateCommand(db);
		Collection<String> updated = command.call();
		assertNotNull(updated);
		assertEquals(1
		assertEquals(path

		SubmoduleGenerator generator = SubmoduleGenerator.forIndex(db);
		assertTrue(generator.next());
		Repository subRepo = generator.getRepository();
		assertNotNull(subRepo);
		assertEquals(commit
	}

	@Test
	public void repositoryWithUnconfiguredSubmodule() throws IOException {
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
		String update = "rebase";
		modulesConfig.setString(ConfigConstants.CONFIG_SUBMODULE_SECTION
				ConfigConstants.CONFIG_KEY_UPDATE
		modulesConfig.save();

		SubmoduleUpdateCommand command = new SubmoduleUpdateCommand(db);
		Collection<String> updated = command.call();
		assertNotNull(updated);
		assertTrue(updated.isEmpty());
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

		Repository subRepo = Git.init().setBare(false)
				.setDirectory(new File(db.getWorkTree()
				.getRepository();
		assertNotNull(subRepo);

		SubmoduleUpdateCommand command = new SubmoduleUpdateCommand(db);
		Collection<String> updated = command.call();
		assertNotNull(updated);
		assertTrue(updated.isEmpty());
	}
}
