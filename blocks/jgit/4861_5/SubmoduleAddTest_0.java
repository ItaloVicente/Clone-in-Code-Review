package org.eclipse.jgit.submodule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.MessageFormat;

import org.eclipse.jgit.JGitText;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.api.SubmoduleAddCommand;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheEditor;
import org.eclipse.jgit.dircache.DirCacheEditor.PathEdit;
import org.eclipse.jgit.dircache.DirCacheEntry;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryTestCase;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.Test;

public class SubmoduleAddTest extends RepositoryTestCase {

	@Test
	public void commandWithNullPath() {
		try {
			new SubmoduleAddCommand(db).setURI("uri").call();
			fail("Exception not thrown");
		} catch (IllegalArgumentException e) {
			assertEquals(JGitText.get().pathNotConfigured
		}
	}

	@Test
	public void commandWithEmptyPath() {
		try {
			new SubmoduleAddCommand(db).setPath("").setURI("uri").call();
			fail("Exception not thrown");
		} catch (IllegalArgumentException e) {
			assertEquals(JGitText.get().pathNotConfigured
		}
	}

	@Test
	public void commandWithNullUri() {
		try {
			new SubmoduleAddCommand(db).setPath("sub").call();
			fail("Exception not thrown");
		} catch (IllegalArgumentException e) {
			assertEquals(JGitText.get().uriNotConfigured
		}
	}

	@Test
	public void commandWithEmptyUri() {
		try {
			new SubmoduleAddCommand(db).setPath("sub").setURI("").call();
			fail("Exception not thrown");
		} catch (IllegalArgumentException e) {
			assertEquals(JGitText.get().uriNotConfigured
		}
	}

	@Test
	public void addSubmodule() throws Exception {
		Git git = new Git(db);
		writeTrashFile("file.txt"
		git.add().addFilepattern("file.txt").call();
		RevCommit commit = git.commit().setMessage("create file").call();

		SubmoduleAddCommand command = new SubmoduleAddCommand(db);
		String path = "sub";
		command.setPath(path);
		String uri = db.getDirectory().toURI().toString();
		command.setURI(uri);
		Repository repo = command.call();
		assertNotNull(repo);

		SubmoduleWalk generator = SubmoduleWalk.forIndex(db);
		assertTrue(generator.next());
		assertEquals(path
		assertEquals(commit
		assertEquals(uri
		assertEquals(path
		assertEquals(uri
		assertNotNull(generator.getRepository());
		assertEquals(commit

		Status status = Git.wrap(db).status().call();
		assertTrue(status.getAdded().contains(Constants.DOT_GIT_MODULES));
		assertTrue(status.getAdded().contains(path));
	}

	@Test
	public void addExistentSubmodule() throws Exception {
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

		SubmoduleAddCommand command = new SubmoduleAddCommand(db);
		command.setPath(path);
		try {
			command.call();
			fail("Exception not thrown");
		} catch (JGitInternalException e) {
			assertEquals(
					MessageFormat.format(JGitText.get().submoduleExists
					e.getMessage());
		}
	}
}
