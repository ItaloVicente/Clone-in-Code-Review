package org.eclipse.jgit.submodule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.text.MessageFormat;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.api.SubmoduleAddCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheEditor;
import org.eclipse.jgit.dircache.DirCacheEditor.PathEdit;
import org.eclipse.jgit.dircache.DirCacheEntry;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileBasedConfig;
import org.junit.Test;

public class SubmoduleAddTest extends RepositoryTestCase {

	@Test
	public void commandWithNullPath() throws GitAPIException {
		try {
			new SubmoduleAddCommand(db).setURI("uri").call().close();
			fail("Exception not thrown");
		} catch (IllegalArgumentException e) {
			assertEquals(JGitText.get().pathNotConfigured
		}
	}

	@Test
	public void commandWithEmptyPath() throws GitAPIException {
		try {
			new SubmoduleAddCommand(db).setPath("").setURI("uri").call()
					.close();
			fail("Exception not thrown");
		} catch (IllegalArgumentException e) {
			assertEquals(JGitText.get().pathNotConfigured
		}
	}

	@Test
	public void commandWithNullUri() throws GitAPIException {
		try {
			new SubmoduleAddCommand(db).setPath("sub").call().close();
			fail("Exception not thrown");
		} catch (IllegalArgumentException e) {
			assertEquals(JGitText.get().uriNotConfigured
		}
	}

	@Test
	public void commandWithEmptyUri() throws GitAPIException {
		try {
			new SubmoduleAddCommand(db).setPath("sub").setURI("").call()
					.close();
			fail("Exception not thrown");
		} catch (IllegalArgumentException e) {
			assertEquals(JGitText.get().uriNotConfigured
		}
	}

	@Test
	public void addSubmodule() throws Exception {
		try (Git git = new Git(db)) {
			writeTrashFile("file.txt"
			git.add().addFilepattern("file.txt").call();
			RevCommit commit = git.commit().setMessage("create file").call();

			SubmoduleAddCommand command = new SubmoduleAddCommand(db);
			String path = "sub";
			command.setPath(path);
			String uri = db.getDirectory().toURI().toString();
			command.setURI(uri);
			ObjectId subCommit;
			try (Repository repo = command.call()) {
				assertNotNull(repo);
				subCommit = repo.resolve(Constants.HEAD);
			}

			SubmoduleWalk generator = SubmoduleWalk.forIndex(db);
			generator.loadModulesConfig();
			assertTrue(generator.next());
			assertEquals(path
			assertEquals(path
			assertEquals(commit
			assertEquals(uri
			assertEquals(path
			assertEquals(uri
			try (Repository subModRepo = generator.getRepository()) {
				assertNotNull(subModRepo);
				assertEquals(subCommit
			}

			Status status = Git.wrap(db).status().call();
			assertTrue(status.getAdded().contains(Constants.DOT_GIT_MODULES));
			assertTrue(status.getAdded().contains(path));
		}
	}

	@Test
	public void addSubmoduleWithName() throws Exception {
		try (Git git = new Git(db)) {
			writeTrashFile("file.txt"
			git.add().addFilepattern("file.txt").call();
			RevCommit commit = git.commit().setMessage("create file").call();

			SubmoduleAddCommand command = new SubmoduleAddCommand(db);
			String name = "testsub";
			command.setName(name);
			String path = "sub";
			command.setPath(path);
			String uri = db.getDirectory().toURI().toString();
			command.setURI(uri);
			ObjectId subCommit;
			try (Repository repo = command.call()) {
				assertNotNull(repo);
				subCommit = repo.resolve(Constants.HEAD);
			}

			SubmoduleWalk generator = SubmoduleWalk.forIndex(db);
			generator.loadModulesConfig();
			assertTrue(generator.next());
			assertEquals(name
			assertEquals(path
			assertEquals(commit
			assertEquals(uri
			assertEquals(path
			assertEquals(uri
			try (Repository subModRepo = generator.getRepository()) {
				assertNotNull(subModRepo);
				assertEquals(subCommit
			}

			Status status = Git.wrap(db).status().call();
			assertTrue(status.getAdded().contains(Constants.DOT_GIT_MODULES));
			assertTrue(status.getAdded().contains(path));
		}
	}

	@Test
	public void addExistentSubmodule() throws Exception {
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

		SubmoduleAddCommand command = new SubmoduleAddCommand(db);
		command.setPath(path);
		try {
			command.call().close();
			fail("Exception not thrown");
		} catch (JGitInternalException e) {
			assertEquals(
					MessageFormat.format(JGitText.get().submoduleExists
					e.getMessage());
		}
	}

	@Test
	public void addSubmoduleWithInvalidPath() throws Exception {
		SubmoduleAddCommand command = new SubmoduleAddCommand(db);
		command.setPath("-invalid-path");
		command.setName("sub");
		try {
			command.call().close();
			fail("Exception not thrown");
		} catch (IllegalArgumentException e) {
			assertEquals("Invalid submodule path '-invalid-path'"
					e.getMessage());
		}
	}

	@Test
	public void addSubmoduleWithInvalidUri() throws Exception {
		SubmoduleAddCommand command = new SubmoduleAddCommand(db);
		command.setPath("valid-path");
		command.setURI("-upstream");
		try {
			command.call().close();
			fail("Exception not thrown");
		} catch (IllegalArgumentException e) {
			assertEquals("Invalid submodule URL '-upstream'"
		}
	}

	@Test
	public void addSubmoduleWithRelativeUri() throws Exception {
		try (Git git = new Git(db)) {
			writeTrashFile("file.txt"
			git.add().addFilepattern("file.txt").call();
			RevCommit commit = git.commit().setMessage("create file").call();

			SubmoduleAddCommand command = new SubmoduleAddCommand(db);
			String path = "sub";
			String uri = "./.git";
			command.setPath(path);
			command.setURI(uri);
			Repository repo = command.call();
			assertNotNull(repo);
			addRepoToClose(repo);

			SubmoduleWalk generator = SubmoduleWalk.forIndex(db);
			assertTrue(generator.next());
			assertEquals(path
			assertEquals(commit
			assertEquals(uri
			assertEquals(path
			String fullUri = db.getDirectory().getAbsolutePath();
			if (File.separatorChar == '\\') {
				fullUri = fullUri.replace('\\'
			}
			assertEquals(fullUri
			try (Repository subModRepo = generator.getRepository()) {
				assertNotNull(subModRepo);
				assertEquals(fullUri
						subModRepo.getConfig().getString(
								ConfigConstants.CONFIG_REMOTE_SECTION
								Constants.DEFAULT_REMOTE_NAME
								ConfigConstants.CONFIG_KEY_URL));
			}
			assertEquals(commit

			Status status = Git.wrap(db).status().call();
			assertTrue(status.getAdded().contains(Constants.DOT_GIT_MODULES));
			assertTrue(status.getAdded().contains(path));
		}
	}

	@Test
	public void addSubmoduleWithExistingSubmoduleDefined() throws Exception {
		String path1 = "sub1";
		String path2 = "sub2";

		FileBasedConfig modulesConfig = new FileBasedConfig(new File(
				db.getWorkTree()
		modulesConfig.setString(ConfigConstants.CONFIG_SUBMODULE_SECTION
				path1
		modulesConfig.setString(ConfigConstants.CONFIG_SUBMODULE_SECTION
				path1
		modulesConfig.save();

		try (Git git = new Git(db)) {
			writeTrashFile("file.txt"
			git.add().addFilepattern("file.txt").call();
			assertNotNull(git.commit().setMessage("create file").call());

			SubmoduleAddCommand command = new SubmoduleAddCommand(db);
			command.setPath(path2);
			String url2 = db.getDirectory().toURI().toString();
			command.setURI(url2);
			Repository r = command.call();
			assertNotNull(r);
			addRepoToClose(r);

			modulesConfig.load();
			assertEquals(path1
					ConfigConstants.CONFIG_SUBMODULE_SECTION
					ConfigConstants.CONFIG_KEY_PATH));
			assertEquals(url1
					ConfigConstants.CONFIG_SUBMODULE_SECTION
					ConfigConstants.CONFIG_KEY_URL));
			assertEquals(path2
					ConfigConstants.CONFIG_SUBMODULE_SECTION
					ConfigConstants.CONFIG_KEY_PATH));
			assertEquals(url2
					ConfigConstants.CONFIG_SUBMODULE_SECTION
					ConfigConstants.CONFIG_KEY_URL));
		}
	}

	@Test
	public void denySubmoduleWithDotDot() throws Exception {
		SubmoduleAddCommand command = new SubmoduleAddCommand(db);
		command.setName("dir/../");
		command.setPath("sub");
		command.setURI(db.getDirectory().toURI().toString());
		try {
			command.call();
			fail();
		} catch (IllegalArgumentException e) {
		}
	}
}
