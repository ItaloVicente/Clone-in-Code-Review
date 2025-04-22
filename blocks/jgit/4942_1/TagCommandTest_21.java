package org.eclipse.jgit.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Iterator;

import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.RefUpdate.Result;
import org.eclipse.jgit.lib.RepositoryTestCase;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.Test;

public class StashListCommandTest extends RepositoryTestCase {

	@Test
	public void noStashRef() throws Exception {
		StashListCommand command = Git.wrap(db).stashList();
		Collection<RevCommit> stashed = command.call();
		assertNotNull(stashed);
		assertTrue(stashed.isEmpty());
	}

	@Test
	public void emptyStashReflog() throws Exception {
		Git git = Git.wrap(db);
		writeTrashFile("file.txt"
		git.add().addFilepattern("file.txt").call();
		RevCommit commit = git.commit().setMessage("create file").call();

		RefUpdate update = db.updateRef(Constants.R_STASH);
		update.setNewObjectId(commit);
		update.disableRefLog();
		assertEquals(Result.NEW

		StashListCommand command = Git.wrap(db).stashList();
		Collection<RevCommit> stashed = command.call();
		assertNotNull(stashed);
		assertTrue(stashed.isEmpty());
	}

	@Test
	public void singleStashedCommit() throws Exception {
		Git git = Git.wrap(db);
		writeTrashFile("file.txt"
		git.add().addFilepattern("file.txt").call();
		RevCommit commit = git.commit().setMessage("create file").call();

		RefUpdate update = db.updateRef(Constants.R_STASH);
		update.setNewObjectId(commit);
		assertEquals(Result.NEW

		StashListCommand command = git.stashList();
		Collection<RevCommit> stashed = command.call();
		assertNotNull(stashed);
		assertEquals(1
		assertEquals(commit
	}

	@Test
	public void multipleStashedCommits() throws Exception {
		Git git = Git.wrap(db);

		writeTrashFile("file.txt"
		git.add().addFilepattern("file.txt").call();
		RevCommit commit1 = git.commit().setMessage("create file").call();

		writeTrashFile("file.txt"
		git.add().addFilepattern("file.txt").call();
		RevCommit commit2 = git.commit().setMessage("edit file").call();

		RefUpdate create = db.updateRef(Constants.R_STASH);
		create.setNewObjectId(commit1);
		assertEquals(Result.NEW

		RefUpdate update = db.updateRef(Constants.R_STASH);
		update.setNewObjectId(commit2);
		assertEquals(Result.FAST_FORWARD

		StashListCommand command = git.stashList();
		Collection<RevCommit> stashed = command.call();
		assertNotNull(stashed);
		assertEquals(2
		Iterator<RevCommit> iter = stashed.iterator();
		assertEquals(commit2
		assertEquals(commit1
	}
}
