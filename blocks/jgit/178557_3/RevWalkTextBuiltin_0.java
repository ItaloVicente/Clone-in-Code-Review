
package org.eclipse.jgit.pgm;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.CLIRepositoryTestCase;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.Before;
import org.junit.Test;

public class RevListTest extends CLIRepositoryTestCase {

	private Git git;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		git = new Git(db);
	}

	@Test
	public void testWithParentsFlag() throws Exception {
		List<RevCommit> commits = createCommitsForParentsFlag(git);
		String result = toString(
				execute("git rev-list HEAD --parents -- Test.txt"));

		String expect = toString(
				commits.get(3).name() + ' ' + commits.get(1).name()
				commits.get(1).name());

		assertEquals(expect
	}

	@Test
	public void testWithOutParentsFlag() throws Exception {
		List<RevCommit> commits = createCommitsForParentsFlag(git);
		String result = toString(execute("git rev-list HEAD -- Test.txt"));

		String expect = toString(commits.get(3).name()

		assertEquals(expect
	}

	private List<RevCommit> createCommitsForParentsFlag(Git git)
			throws Exception {
		List<RevCommit> commits = new ArrayList<>();
		writeTrashFile("Test1.txt"
		git.add().addFilepattern("Test1.txt").call();
		commits.add(git.commit().setMessage("commit#1").call());
		writeTrashFile("Test.txt"
		git.add().addFilepattern("Test.txt").call();
		commits.add(git.commit().setMessage("commit#2").call());
		writeTrashFile("Test1.txt"
		git.add().addFilepattern("Test1.txt").call();
		commits.add(git.commit().setMessage("commit#3").call());
		writeTrashFile("Test.txt"
		git.add().addFilepattern("Test.txt").call();
		commits.add(git.commit().setMessage("commit#4").call());
		return commits;
	}
}
