package org.eclipse.jgit.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RepositoryTestCase;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.Test;

public class LogCommandTest extends RepositoryTestCase {

	@Test
	public void logAllCommits() throws Exception {
		List<RevCommit> commits = new ArrayList<RevCommit>();
		Git git = Git.wrap(db);

		writeTrashFile("Test.txt"
		git.add().addFilepattern("Test.txt").call();
		commits.add(git.commit().setMessage("initial commit").call());

		git.branchCreate().setName("branch1").call();
		Ref checkedOut = git.checkout().setName("branch1").call();
		assertEquals("refs/heads/branch1"
		writeTrashFile("Test1.txt"
		git.add().addFilepattern("Test1.txt").call();
		commits.add(git.commit().setMessage("branch1 commit").call());

		checkedOut = git.checkout().setName("master").call();
		assertEquals("refs/heads/master"
		writeTrashFile("Test2.txt"
		git.add().addFilepattern("Test2.txt").call();
		commits.add(git.commit().setMessage("branch1 commit").call());

		Iterator<RevCommit> log = git.log().all().call().iterator();
		assertTrue(log.hasNext());
		assertTrue(commits.contains(log.next()));
		assertTrue(log.hasNext());
		assertTrue(commits.contains(log.next()));
		assertTrue(log.hasNext());
		assertTrue(commits.contains(log.next()));
		assertFalse(log.hasNext());
	}
}
