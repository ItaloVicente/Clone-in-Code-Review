package org.eclipse.jgit.merge;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.test.resources.SampleDataRepositoryTestCase;
import org.eclipse.jgit.util.GitDateFormatter;
import org.eclipse.jgit.util.GitDateFormatter.Format;
import org.junit.Before;
import org.junit.Test;

public class SquashMessageFormatterTest extends SampleDataRepositoryTestCase {
	private GitDateFormatter dateFormatter;
	private SquashMessageFormatter msgFormatter;
	private RevCommit revCommit;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		dateFormatter = new GitDateFormatter(Format.DEFAULT);
		msgFormatter = new SquashMessageFormatter();
	}

	@Test
	public void testCommit() throws Exception {
		try (Git git = new Git(db)) {
			revCommit = git.commit().setMessage("squash_me").call();

			Ref master = db.exactRef("refs/heads/master");
			String message = msgFormatter.format(Arrays.asList(revCommit)
			assertEquals(
					"Squashed commit of the following:\n\ncommit "
							+ revCommit.getName() + "\nAuthor: "
							+ revCommit.getAuthorIdent().getName() + " <"
							+ revCommit.getAuthorIdent().getEmailAddress()
							+ ">\nDate:   " + dateFormatter.formatDate(author)
							+ "\n\n\tsquash_me\n"
		}
	}
}
