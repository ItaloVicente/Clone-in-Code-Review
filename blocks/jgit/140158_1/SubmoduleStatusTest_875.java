package org.eclipse.jgit.submodule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import org.eclipse.jgit.api.SubmoduleInitCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheEditor;
import org.eclipse.jgit.dircache.DirCacheEditor.PathEdit;
import org.eclipse.jgit.dircache.DirCacheEntry;
import org.eclipse.jgit.errors.ConfigInvalidException;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.storage.file.FileBasedConfig;
import org.junit.Test;

public class SubmoduleInitTest extends RepositoryTestCase {

	@Test
	public void repositoryWithNoSubmodules() throws GitAPIException {
		SubmoduleInitCommand command = new SubmoduleInitCommand(db);
		Collection<String> modules = command.call();
		assertNotNull(modules);
		assertTrue(modules.isEmpty());
	}

	@Test
	public void repositoryWithUninitializedModule() throws IOException
			ConfigInvalidException
		final String path = addSubmoduleToIndex();

		SubmoduleWalk generator = SubmoduleWalk.forIndex(db);
		assertTrue(generator.next());
		assertNull(generator.getConfigUrl());
		assertNull(generator.getConfigUpdate());

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

		SubmoduleInitCommand command = new SubmoduleInitCommand(db);
		Collection<String> modules = command.call();
		assertNotNull(modules);
		assertEquals(1
		assertEquals(path

		generator = SubmoduleWalk.forIndex(db);
		assertTrue(generator.next());
		assertEquals(url
		assertEquals(update
	}

	@Test
	public void resolveSameLevelRelativeUrl() throws Exception {
		final String path = addSubmoduleToIndex();

		FileBasedConfig config = db.getConfig();
		config.setString(ConfigConstants.CONFIG_REMOTE_SECTION
				Constants.DEFAULT_REMOTE_NAME
				base);
		config.save();

		SubmoduleWalk generator = SubmoduleWalk.forIndex(db);
		assertTrue(generator.next());
		assertNull(generator.getConfigUrl());
		assertNull(generator.getConfigUpdate());

		FileBasedConfig modulesConfig = new FileBasedConfig(new File(
				db.getWorkTree()
		modulesConfig.setString(ConfigConstants.CONFIG_SUBMODULE_SECTION
				ConfigConstants.CONFIG_KEY_PATH
		String url = "./sub.git";
		modulesConfig.setString(ConfigConstants.CONFIG_SUBMODULE_SECTION
				ConfigConstants.CONFIG_KEY_URL
		String update = "rebase";
		modulesConfig.setString(ConfigConstants.CONFIG_SUBMODULE_SECTION
				ConfigConstants.CONFIG_KEY_UPDATE
		modulesConfig.save();

		SubmoduleInitCommand command = new SubmoduleInitCommand(db);
		Collection<String> modules = command.call();
		assertNotNull(modules);
		assertEquals(1
		assertEquals(path

		generator = SubmoduleWalk.forIndex(db);
		assertTrue(generator.next());
		assertEquals(update
	}

	@Test
	public void resolveOneLevelHigherRelativeUrl() throws IOException
			ConfigInvalidException
		final String path = addSubmoduleToIndex();

		FileBasedConfig config = db.getConfig();
		config.setString(ConfigConstants.CONFIG_REMOTE_SECTION
				Constants.DEFAULT_REMOTE_NAME
				base);
		config.save();

		SubmoduleWalk generator = SubmoduleWalk.forIndex(db);
		assertTrue(generator.next());
		assertNull(generator.getConfigUrl());
		assertNull(generator.getConfigUpdate());

		FileBasedConfig modulesConfig = new FileBasedConfig(new File(
				db.getWorkTree()
		modulesConfig.setString(ConfigConstants.CONFIG_SUBMODULE_SECTION
				ConfigConstants.CONFIG_KEY_PATH
		String url = "../sub.git";
		modulesConfig.setString(ConfigConstants.CONFIG_SUBMODULE_SECTION
				ConfigConstants.CONFIG_KEY_URL
		String update = "rebase";
		modulesConfig.setString(ConfigConstants.CONFIG_SUBMODULE_SECTION
				ConfigConstants.CONFIG_KEY_UPDATE
		modulesConfig.save();

		SubmoduleInitCommand command = new SubmoduleInitCommand(db);
		Collection<String> modules = command.call();
		assertNotNull(modules);
		assertEquals(1
		assertEquals(path

		generator = SubmoduleWalk.forIndex(db);
		assertTrue(generator.next());
		assertEquals(update
	}

	@Test
	public void resolveTwoLevelHigherRelativeUrl() throws IOException
			ConfigInvalidException
		final String path = addSubmoduleToIndex();

		FileBasedConfig config = db.getConfig();
		config.setString(ConfigConstants.CONFIG_REMOTE_SECTION
				Constants.DEFAULT_REMOTE_NAME
				base);
		config.save();

		SubmoduleWalk generator = SubmoduleWalk.forIndex(db);
		assertTrue(generator.next());
		assertNull(generator.getConfigUrl());
		assertNull(generator.getConfigUpdate());

		FileBasedConfig modulesConfig = new FileBasedConfig(new File(
				db.getWorkTree()
		modulesConfig.setString(ConfigConstants.CONFIG_SUBMODULE_SECTION
				ConfigConstants.CONFIG_KEY_PATH
		String url = "../../server2/sub.git";
		modulesConfig.setString(ConfigConstants.CONFIG_SUBMODULE_SECTION
				ConfigConstants.CONFIG_KEY_URL
		String update = "rebase";
		modulesConfig.setString(ConfigConstants.CONFIG_SUBMODULE_SECTION
				ConfigConstants.CONFIG_KEY_UPDATE
		modulesConfig.save();

		SubmoduleInitCommand command = new SubmoduleInitCommand(db);
		Collection<String> modules = command.call();
		assertNotNull(modules);
		assertEquals(1
		assertEquals(path

		generator = SubmoduleWalk.forIndex(db);
		assertTrue(generator.next());
		assertEquals(update
	}

	@Test
	public void resolveWorkingDirectoryRelativeUrl() throws IOException
			GitAPIException
		final String path = addSubmoduleToIndex();

		String base = db.getWorkTree().getAbsolutePath();
		if (File.separatorChar == '\\')
			base = base.replace('\\'
		FileBasedConfig config = db.getConfig();
		config.unset(ConfigConstants.CONFIG_REMOTE_SECTION
				Constants.DEFAULT_REMOTE_NAME
		config.save();

		SubmoduleWalk generator = SubmoduleWalk.forIndex(db);
		assertTrue(generator.next());
		assertNull(generator.getConfigUrl());
		assertNull(generator.getConfigUpdate());

		FileBasedConfig modulesConfig = new FileBasedConfig(new File(
				db.getWorkTree()
		modulesConfig.setString(ConfigConstants.CONFIG_SUBMODULE_SECTION
				ConfigConstants.CONFIG_KEY_PATH
		String url = "./sub.git";
		modulesConfig.setString(ConfigConstants.CONFIG_SUBMODULE_SECTION
				ConfigConstants.CONFIG_KEY_URL
		String update = "rebase";
		modulesConfig.setString(ConfigConstants.CONFIG_SUBMODULE_SECTION
				ConfigConstants.CONFIG_KEY_UPDATE
		modulesConfig.save();

		SubmoduleInitCommand command = new SubmoduleInitCommand(db);
		Collection<String> modules = command.call();
		assertNotNull(modules);
		assertEquals(1
		assertEquals(path

		generator = SubmoduleWalk.forIndex(db);
		assertTrue(generator.next());
		assertEquals(base + "/sub.git"
		assertEquals(update
	}

	@Test
	public void resolveInvalidParentUrl() throws IOException
			ConfigInvalidException
		final String path = addSubmoduleToIndex();

		String base = "no_slash";
		FileBasedConfig config = db.getConfig();
		config.setString(ConfigConstants.CONFIG_REMOTE_SECTION
				Constants.DEFAULT_REMOTE_NAME
				base);
		config.save();

		SubmoduleWalk generator = SubmoduleWalk.forIndex(db);
		assertTrue(generator.next());
		assertNull(generator.getConfigUrl());
		assertNull(generator.getConfigUpdate());

		FileBasedConfig modulesConfig = new FileBasedConfig(new File(
				db.getWorkTree()
		modulesConfig.setString(ConfigConstants.CONFIG_SUBMODULE_SECTION
				ConfigConstants.CONFIG_KEY_PATH
		String url = "../sub.git";
		modulesConfig.setString(ConfigConstants.CONFIG_SUBMODULE_SECTION
				ConfigConstants.CONFIG_KEY_URL
		modulesConfig.save();

		try {
			new SubmoduleInitCommand(db).call();
			fail("Exception not thrown");
		} catch (JGitInternalException e) {
			assertTrue(e.getCause() instanceof IOException);
		}
	}

	private String addSubmoduleToIndex() throws IOException {
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
		return path;
	}
}
