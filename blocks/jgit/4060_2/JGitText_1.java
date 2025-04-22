package org.eclipse.jgit.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.RepositoryTestCase;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.junit.Test;

public class DescribeCommandTest extends RepositoryTestCase {

	@Test
	public void testDescribeCommandFirstCommit() throws Exception {
		Git git = Git.wrap(db);

		writeTrashFile("Test.txt"
		git.add().addFilepattern("Test.txt").call();
		RevCommit initialCommit = git.commit().setMessage("initial commit")
				.call();

		git.tag()
				.setName("TEST")
				.setObjectId(initialCommit)
				.setTagger(
						new PersonIdent("Test Tagger"
				.setMessage("Tag to test DescribeCommand").call();

		{
			String actual = git.describe().call();
			String expected = "TEST";
			assertEquals(expected
		}
	}

	@Test
	public void testDescribeCommandLaterCommit() throws Exception {
		Git git = Git.wrap(db);

		writeTrashFile("Test.txt"
		git.add().addFilepattern("Test.txt").call();
		RevCommit initialCommit = git.commit().setMessage("initial commit")
				.call();

		git.tag()
				.setName("TEST")
				.setObjectId(initialCommit)
				.setTagger(
						new PersonIdent("Test Tagger"
				.setMessage("Tag to test DescribeCommand").call();

		writeTrashFile("Test1.txt"
		git.add().addFilepattern("Test1.txt").call();
		RevCommit secondCommit = git.commit().setMessage("new commit").call();

		String defaultAbbreviationLength = Integer.toString(DescribeCommand
				.getDefaultAbbreviationLength());
		String actual = git.describe().setObjectId(secondCommit).call();
		String expectedRegex = "TEST-1-g[0-9a-f]{" + defaultAbbreviationLength
				+ "}";
		assertTrue("Describe String matches expected regex"
				actual.matches(expectedRegex));
	}

	@Test
	public void testDescribeCommandVariableAbbreviationLength()
			throws Exception {
		Git git = Git.wrap(db);

		writeTrashFile("Test.txt"
		git.add().addFilepattern("Test.txt").call();
		RevCommit initialCommit = git.commit().setMessage("initial commit")
				.call();

		git.tag()
				.setName("TEST")
				.setObjectId(initialCommit)
				.setTagger(
						new PersonIdent("Test Tagger"
				.setMessage("Tag to test DescribeCommand").call();

		writeTrashFile("Test1.txt"
		git.add().addFilepattern("Test1.txt").call();
		RevCommit secondCommit = git.commit().setMessage("new commit").call();

		{
			DescribeCommand dc = git.describe().setAbbrev(0)
					.setObjectId(secondCommit);
			String actual = dc.call();
			String expected = "TEST";
			assertEquals(expected
		}

		int lengthsToTest[] = { 2
		for (int length : lengthsToTest) {
			DescribeCommand dc = git.describe().setAbbrev(length)
					.setObjectId(secondCommit);
			String actual = dc.call();
			String expectedRegex = "TEST-1-g[0-9a-f]{"
					+ Integer.toString(length) + "}";
			assertTrue("Describe String matches expected regex"
					actual.matches(expectedRegex));

		}
	}

	@Test
	public void testDescribeThrowsWithInvalidArgs() throws Exception {
		Git git = Git.wrap(db);

		writeTrashFile("Test.txt"
		git.add().addFilepattern("Test.txt").call();
		git.commit().setMessage("initial commit").call();
		try {
			DescribeCommand dc = git.describe();
			dc.setAbbrev(1).call();
			throw new IllegalStateException(
					"Expected throw when given invalid arguments");
		} catch (IllegalArgumentException e) {
		}
	}

	@Test
	public void testDescribeCommandAfterMerge() throws Exception {

		Git git = Git.wrap(db);

		writeTrashFile("File_A.txt"
		git.add().addFilepattern("File_A.txt").call();
		RevCommit commitA = git.commit().setMessage("Commit A")
				.call();
		git.branchCreate().setName("A").call();

		git.tag()
				.setName("TEST")
				.setObjectId(commitA)
				.setTagger(
						new PersonIdent("Test Tagger"
				.setMessage("Tag to test DescribeCommand").call();

		git.branchCreate().setName("B").call();
		git.checkout().setName("B").call();
		writeTrashFile("File_B.txt"
		git.add().addFilepattern("File_B.txt").call();
		RevCommit secondCommit = git.commit().setMessage("Commit B").call();

		git.checkout().setName("A").call();

		git.branchCreate().setName("C").call();
		git.checkout().setName("C").call();
		writeTrashFile("File_C.txt"
		git.add().addFilepattern("File_C.txt").call();
		RevCommit thirdCommit = git.commit().setMessage("Commit C").call();

		git.branchCreate().setName("D").call();
		git.checkout().setName("D").call();
		writeTrashFile("File_D.txt"
		git.add().addFilepattern("File_D.txt").call();
		RevCommit fourthCommit = git.commit().setMessage("Commit D").call();

		git.checkout().setName("B").call();

		git.branchCreate().setName("E").call();
		git.checkout().setName("E").call();
		MergeResult mr = git.merge().include(fourthCommit).call();

		RevWalk w = new RevWalk(db);
		RevCommit fifthCommit = w.parseCommit(mr.getNewHead());
		w.release();

		String defaultAbbreviationLength = Integer.toString(DescribeCommand
				.getDefaultAbbreviationLength());

		{
			String actual = git.describe().setObjectId(secondCommit).call();
			String expectedRegex = "TEST-1-g[0-9a-f]{"
					+ defaultAbbreviationLength + "}";
			assertTrue("Describe String matches expected regex"
					actual.matches(expectedRegex));
		}
		{
			String actual = git.describe().setObjectId(thirdCommit).call();
			String expectedRegex = "TEST-1-g[0-9a-f]{"
					+ defaultAbbreviationLength + "}";
			assertTrue("Describe String matches expected regex"
					actual.matches(expectedRegex));
		}
		{
			String actual = git.describe().setObjectId(fourthCommit).call();
			String expectedRegex = "TEST-2-g[0-9a-f]{"
					+ defaultAbbreviationLength + "}";
			assertTrue("Describe String matches expected regex"
					actual.matches(expectedRegex));
		}
		{
			String actual = git.describe().setObjectId(fifthCommit).call();
			String expectedRegex = "TEST-4-g[0-9a-f]{"
					+ defaultAbbreviationLength
				+ "}";
			assertTrue("Describe String matches expected regex"
					actual.matches(expectedRegex));
		}



	}
}
