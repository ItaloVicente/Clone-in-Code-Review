package org.eclipse.jgit.api;

import org.eclipse.jgit.errors.UnmergedPathException;
import org.eclipse.jgit.lib.RepositoryTestCase;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTag;

public class TagCommandTest extends RepositoryTestCase {

	public void testTagging() throws NoHeadException
		Git git = new Git(db);
		RevCommit commit = git.commit().setMessage("initial commit").call();
		RevTag tag = git.tag().setName("tag").call();
		assertEquals(commit.getId()
	}

	public void testWrongParams() throws NoHeadException
		Git git = new Git(db);
		git.commit().setMessage("initial commit").call();
		try {
			git.tag().setMessage("some message").call();
			fail("We should have failed without a tag name");
		} catch (JGitInternalException e) {
		}
	}

	public void testFailureOnSignedTags() throws NoHeadException
		Git git = new Git(db);
		git.commit().setMessage("initial commit").call();
		try {
			git.tag().setSigned(true).setName("tag").call();
			fail("We should have failed with an UnsupportedOperationException due to signed tag");
		} catch (UnsupportedOperationException e) {
		}
	}

}
