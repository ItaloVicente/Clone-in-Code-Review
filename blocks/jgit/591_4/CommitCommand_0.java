package org.eclipse.jgit.api;

import org.eclipse.jgit.errors.UnmergedPathException;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.RepositoryTestCase;
import org.eclipse.jgit.revwalk.RevCommit;

public class CommitAndLogCommandTests extends RepositoryTestCase {
	public void testSomeCommits() throws NoHeadException
			UnmergedPathException

		Git git = new Git(db);
		git.commit().setMessage("initial commit").call();
		git.commit().setMessage("second commit").setCommitter(committer).call();
		git.commit().setMessage("third commit").setAuthor(author).call();
		git.commit().setMessage("fourth commit").setAuthor(author)
				.setCommitter(committer).call();
		Iterable<RevCommit> commits = git.log().call();

		PersonIdent defaultCommitter = new PersonIdent(db);
		PersonIdent expectedAuthors[] = new PersonIdent[] {
				defaultCommitter
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
		Git git = new Git(db);
		try {
			git.commit().setAuthor(author).call();
			fail("Didn't get the expected exception");
		} catch (NoMessageException e) {
		}
	}
}
