package org.eclipse.jgit.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import org.eclipse.jgit.api.errors.ConcurrentRefUpdateException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.api.errors.NoFilepatternException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.api.errors.NoMessageException;
import org.eclipse.jgit.api.errors.WrongRepositoryStateException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.UnmergedPathException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.RepositoryTestCase;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.util.FileUtils;
import org.junit.Test;

public class CommitAndLogCommandTests extends RepositoryTestCase {
	public void testSomeCommits() throws NoHeadException
			UnmergedPathException
			JGitInternalException

		Git git = new Git(db);
		git.commit().setMessage("initial commit").call();
		git.commit().setMessage("second commit").setCommitter(committer).call();
		git.commit().setMessage("third commit").setAuthor(author).call();
		git.commit().setMessage("fourth commit").setAuthor(author)
				.setCommitter(committer).call();
		Iterable<RevCommit> commits = git.log().call();

		PersonIdent defaultCommitter = new PersonIdent(db);
		PersonIdent expectedAuthors[] = new PersonIdent[] { defaultCommitter
				committer
		PersonIdent expectedCommitters[] = new PersonIdent[] {
				defaultCommitter
		String expectedMessages[] = new String[] { "initial commit"
				"second commit"
		int l = expectedAuthors.length - 1;
		for (RevCommit c : commits) {
			assertEquals(expectedAuthors[l].getName()
					.getName());
			assertEquals(expectedCommitters[l].getName()
					.getName());
			assertEquals(c.getFullMessage()
			l--;
		}
		assertEquals(l
	}

	public void testWrongParams() throws UnmergedPathException
			NoHeadException
			JGitInternalException
		Git git = new Git(db);
		try {
			git.commit().setAuthor(author).call();
			fail("Didn't get the expected exception");
		} catch (NoMessageException e) {
		}
	}

	public void testMultipleInvocations() throws NoHeadException
			ConcurrentRefUpdateException
			UnmergedPathException
			WrongRepositoryStateException {
		Git git = new Git(db);
		CommitCommand commitCmd = git.commit();
		commitCmd.setMessage("initial commit").call();
		try {
			commitCmd.setAuthor(author);
			fail("didn't catch the expected exception");
		} catch (IllegalStateException e) {
		}
		LogCommand logCmd = git.log();
		logCmd.call();
		try {
			logCmd.call();
			fail("didn't catch the expected exception");
		} catch (IllegalStateException e) {
		}
	}

	public void testMergeEmptyBranches() throws IOException
			NoMessageException
			JGitInternalException
		Git git = new Git(db);
		git.commit().setMessage("initial commit").call();
		RefUpdate r = db.updateRef("refs/heads/side");
		r.setNewObjectId(db.resolve(Constants.HEAD));
		assertEquals(r.forceUpdate()
		RevCommit second = git.commit().setMessage("second commit").setCommitter(committer).call();
		db.updateRef(Constants.HEAD).link("refs/heads/side");
		RevCommit firstSide = git.commit().setMessage("first side commit").setAuthor(author).call();

		write(new File(db.getDirectory()
				.toString(db.resolve("refs/heads/master")));
		write(new File(db.getDirectory()

		RevCommit commit = git.commit().call();
		RevCommit[] parents = commit.getParents();
		assertEquals(parents[0]
		assertEquals(parents[1]
		assertTrue(parents.length==2);
	}

	public void testAddUnstagedChanges() throws IOException
			NoMessageException
			JGitInternalException
			NoFilepatternException {
		File file = new File(db.getWorkTree()
		FileUtils.createNewFile(file);
		PrintWriter writer = new PrintWriter(file);
		writer.print("content");
		writer.close();

		Git git = new Git(db);
		git.add().addFilepattern("a.txt").call();
		RevCommit commit = git.commit().setMessage("initial commit").call();
		TreeWalk tw = TreeWalk.forPath(db
		assertEquals("6b584e8ece562ebffc15d38808cd6b98fc3d97ea"
				tw.getObjectId(0).getName());

		writer = new PrintWriter(file);
		writer.print("content2");
		writer.close();
		commit = git.commit().setMessage("second commit").call();
		tw = TreeWalk.forPath(db
		assertEquals("6b584e8ece562ebffc15d38808cd6b98fc3d97ea"
				tw.getObjectId(0).getName());

		commit = git.commit().setAll(true).setMessage("third commit")
				.setAll(true).call();
		tw = TreeWalk.forPath(db
		assertEquals("db00fd65b218578127ea51f3dffac701f12f486a"
				tw.getObjectId(0).getName());
	}

	public void testCommitRange() throws NoHeadException
			UnmergedPathException
			JGitInternalException
			IncorrectObjectTypeException
		Git git = new Git(db);
		git.commit().setMessage("first commit").call();
		RevCommit second = git.commit().setMessage("second commit")
				.setCommitter(committer).call();
		git.commit().setMessage("third commit").setAuthor(author).call();
		RevCommit last = git.commit().setMessage("fourth commit").setAuthor(
				author)
				.setCommitter(committer).call();
		Iterable<RevCommit> commits = git.log().addRange(second.getId()
				last.getId()).call();

		PersonIdent defaultCommitter = new PersonIdent(db);
		PersonIdent expectedAuthors[] = new PersonIdent[] { author
		PersonIdent expectedCommitters[] = new PersonIdent[] {
				defaultCommitter
		String expectedMessages[] = new String[] { "third commit"
				"fourth commit" };
		int l = expectedAuthors.length - 1;
		for (RevCommit c : commits) {
			assertEquals(expectedAuthors[l].getName()
					.getName());
			assertEquals(expectedCommitters[l].getName()
					.getName());
			assertEquals(c.getFullMessage()
			l--;
		}
		assertEquals(l
	}

	@Test
	public void testCommitAmend() throws NoHeadException
			UnmergedPathException
			JGitInternalException
		Git git = new Git(db);
		git.commit().setAmending(true).setMessage("first commit").call();

		Iterable<RevCommit> commits = git.log().call();
		int c = 0;
		for (RevCommit commit : commits) {
			assertEquals("first commit"
			c++;
		}
		assertEquals(1
	}
}
