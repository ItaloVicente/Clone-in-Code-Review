package org.eclipse.jgit.merge;

import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Locale;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.SampleDataRepositoryTestCase;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.Before;
import org.junit.Test;

public class SquashMessageFormatterTest extends SampleDataRepositoryTestCase {
	private SquashMessageFormatter formatter;

	private RevCommit revCommit;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();

		formatter = new SquashMessageFormatter();
	}

	@Test
	public void testCommit() throws Exception {
		Git git = new Git(db);
		revCommit = git.commit().setMessage("squash_me").call();

		Ref master = db.getRef("refs/heads/master");
		String message = formatter.format(Arrays.asList(revCommit)
		assertEquals(
				"Squashed commit of the following:\n\ncommit "
						+ revCommit.getName() + "\nAuthor: "
						+ revCommit.getAuthorIdent().getName() + " <"
						+ revCommit.getAuthorIdent().getEmailAddress()
						+ ">\nDate:   " + formatCommitTime(author)
						+ "\n\n    squash_me\n"
	}

	private String formatCommitTime(PersonIdent a) {
		SimpleDateFormat dtfmt = new SimpleDateFormat(
				"EEE MMM d HH:mm:ss yyyy Z"
		dtfmt.setTimeZone(a.getTimeZone());
		return dtfmt.format(a.getWhen());
	}
}
