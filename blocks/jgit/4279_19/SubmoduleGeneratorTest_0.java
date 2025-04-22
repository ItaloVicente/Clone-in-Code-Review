package org.eclipse.jgit.submodule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.api.SubmoduleAddCommand;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryTestCase;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.Test;

public class SubmoduleAddTest extends RepositoryTestCase {

	@Test(expected = IllegalArgumentException.class)
	public void commandWithNullPath() {
		new SubmoduleAddCommand(db).setURI("uri").call();
	}

	@Test(expected = IllegalArgumentException.class)
	public void commandWithEmptyPath() {
		new SubmoduleAddCommand(db).setPath("").setURI("uri").call();
	}

	@Test(expected = IllegalArgumentException.class)
	public void commandWithNullUri() {
		new SubmoduleAddCommand(db).setPath("sub").call();
	}

	@Test(expected = IllegalArgumentException.class)
	public void commandWithEmptyUri() {
		new SubmoduleAddCommand(db).setPath("sub").setURI("").call();
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

		SubmoduleGenerator generator = new SubmoduleGenerator(db);
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
}
