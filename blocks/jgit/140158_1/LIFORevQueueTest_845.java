
package org.eclipse.jgit.revwalk;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.junit.Test;

public class FooterLineTest extends RepositoryTestCase {
	@Test
	public void testNoFooters_EmptyBody() throws IOException {
		final RevCommit commit = parse("");
		final List<FooterLine> footers = commit.getFooterLines();
		assertNotNull(footers);
		assertEquals(0
	}

	@Test
	public void testNoFooters_NewlineOnlyBody1() throws IOException {
		final RevCommit commit = parse("\n");
		final List<FooterLine> footers = commit.getFooterLines();
		assertNotNull(footers);
		assertEquals(0
	}

	@Test
	public void testNoFooters_NewlineOnlyBody5() throws IOException {
		final RevCommit commit = parse("\n\n\n\n\n");
		final List<FooterLine> footers = commit.getFooterLines();
		assertNotNull(footers);
		assertEquals(0
	}

	@Test
	public void testNoFooters_OneLineBodyNoLF() throws IOException {
		final RevCommit commit = parse("this is a commit");
		final List<FooterLine> footers = commit.getFooterLines();
		assertNotNull(footers);
		assertEquals(0
	}

	@Test
	public void testNoFooters_OneLineBodyWithLF() throws IOException {
		final RevCommit commit = parse("this is a commit\n");
		final List<FooterLine> footers = commit.getFooterLines();
		assertNotNull(footers);
		assertEquals(0
	}

	@Test
	public void testNoFooters_ShortBodyNoLF() throws IOException {
		final RevCommit commit = parse("subject\n\nbody of commit");
		final List<FooterLine> footers = commit.getFooterLines();
		assertNotNull(footers);
		assertEquals(0
	}

	@Test
	public void testNoFooters_ShortBodyWithLF() throws IOException {
		final RevCommit commit = parse("subject\n\nbody of commit\n");
		final List<FooterLine> footers = commit.getFooterLines();
		assertNotNull(footers);
		assertEquals(0
	}

	@Test
	public void testSignedOffBy_OneUserNoLF() throws IOException {
		final RevCommit commit = parse("subject\n\nbody of commit\n" + "\n"
				+ "Signed-off-by: A. U. Thor <a@example.com>");
		final List<FooterLine> footers = commit.getFooterLines();
		FooterLine f;

		assertNotNull(footers);
		assertEquals(1

		f = footers.get(0);
		assertEquals("Signed-off-by"
		assertEquals("A. U. Thor <a@example.com>"
		assertEquals("a@example.com"
	}

	@Test
	public void testSignedOffBy_OneUserWithLF() throws IOException {
		final RevCommit commit = parse("subject\n\nbody of commit\n" + "\n"
				+ "Signed-off-by: A. U. Thor <a@example.com>\n");
		final List<FooterLine> footers = commit.getFooterLines();
		FooterLine f;

		assertNotNull(footers);
		assertEquals(1

		f = footers.get(0);
		assertEquals("Signed-off-by"
		assertEquals("A. U. Thor <a@example.com>"
		assertEquals("a@example.com"
	}

	@Test
	public void testSignedOffBy_IgnoreWhitespace() throws IOException {
		final RevCommit commit = parse("subject\n\nbody of commit\n" + "\n"
				+ "Signed-off-by:   A. U. Thor <a@example.com>  \n");
		final List<FooterLine> footers = commit.getFooterLines();
		FooterLine f;

		assertNotNull(footers);
		assertEquals(1

		f = footers.get(0);
		assertEquals("Signed-off-by"
		assertEquals("A. U. Thor <a@example.com>  "
		assertEquals("a@example.com"
	}

	@Test
	public void testEmptyValueNoLF() throws IOException {
		final RevCommit commit = parse("subject\n\nbody of commit\n" + "\n"
				+ "Signed-off-by:");
		final List<FooterLine> footers = commit.getFooterLines();
		FooterLine f;

		assertNotNull(footers);
		assertEquals(1

		f = footers.get(0);
		assertEquals("Signed-off-by"
		assertEquals(""
		assertNull(f.getEmailAddress());
	}

	@Test
	public void testEmptyValueWithLF() throws IOException {
		final RevCommit commit = parse("subject\n\nbody of commit\n" + "\n"
				+ "Signed-off-by:\n");
		final List<FooterLine> footers = commit.getFooterLines();
		FooterLine f;

		assertNotNull(footers);
		assertEquals(1

		f = footers.get(0);
		assertEquals("Signed-off-by"
		assertEquals(""
		assertNull(f.getEmailAddress());
	}

	@Test
	public void testShortKey() throws IOException {
		final RevCommit commit = parse("subject\n\nbody of commit\n" + "\n"
				+ "K:V\n");
		final List<FooterLine> footers = commit.getFooterLines();
		FooterLine f;

		assertNotNull(footers);
		assertEquals(1

		f = footers.get(0);
		assertEquals("K"
		assertEquals("V"
		assertNull(f.getEmailAddress());
	}

	@Test
	public void testNonDelimtedEmail() throws IOException {
		final RevCommit commit = parse("subject\n\nbody of commit\n" + "\n"
				+ "Acked-by: re@example.com\n");
		final List<FooterLine> footers = commit.getFooterLines();
		FooterLine f;

		assertNotNull(footers);
		assertEquals(1

		f = footers.get(0);
		assertEquals("Acked-by"
		assertEquals("re@example.com"
		assertEquals("re@example.com"
	}

	@Test
	public void testNotEmail() throws IOException {
		final RevCommit commit = parse("subject\n\nbody of commit\n" + "\n"
				+ "Acked-by: Main Tain Er\n");
		final List<FooterLine> footers = commit.getFooterLines();
		FooterLine f;

		assertNotNull(footers);
		assertEquals(1

		f = footers.get(0);
		assertEquals("Acked-by"
		assertEquals("Main Tain Er"
		assertNull(f.getEmailAddress());
	}

	@Test
	public void testSignedOffBy_ManyUsers() throws IOException {
		final RevCommit commit = parse("subject\n\nbody of commit\n"
				+ "Not-A-Footer-Line: this line must not be read as a footer\n"
				+ "Signed-off-by: A. U. Thor <a@example.com>\n"
				+ "CC:            <some.mailing.list@example.com>\n"
				+ "Acked-by: Some Reviewer <sr@example.com>\n"
				+ "Signed-off-by: Main Tain Er <mte@example.com>\n");
		final List<FooterLine> footers = commit.getFooterLines();
		FooterLine f;

		assertNotNull(footers);
		assertEquals(4

		f = footers.get(0);
		assertEquals("Signed-off-by"
		assertEquals("A. U. Thor <a@example.com>"
		assertEquals("a@example.com"

		f = footers.get(1);
		assertEquals("CC"
		assertEquals("<some.mailing.list@example.com>"
		assertEquals("some.mailing.list@example.com"

		f = footers.get(2);
		assertEquals("Acked-by"
		assertEquals("Some Reviewer <sr@example.com>"
		assertEquals("sr@example.com"

		f = footers.get(3);
		assertEquals("Signed-off-by"
		assertEquals("Main Tain Er <mte@example.com>"
		assertEquals("mte@example.com"
	}

	@Test
	public void testSignedOffBy_SkipNonFooter() throws IOException {
		final RevCommit commit = parse("subject\n\nbody of commit\n"
				+ "Not-A-Footer-Line: this line must not be read as a footer\n"
				+ "Signed-off-by: A. U. Thor <a@example.com>\n"
				+ "CC:            <some.mailing.list@example.com>\n"
				+ "not really a footer line but we'll skip it anyway\n"
				+ "Acked-by: Some Reviewer <sr@example.com>\n"
				+ "Signed-off-by: Main Tain Er <mte@example.com>\n");
		final List<FooterLine> footers = commit.getFooterLines();
		FooterLine f;

		assertNotNull(footers);
		assertEquals(4

		f = footers.get(0);
		assertEquals("Signed-off-by"
		assertEquals("A. U. Thor <a@example.com>"

		f = footers.get(1);
		assertEquals("CC"
		assertEquals("<some.mailing.list@example.com>"

		f = footers.get(2);
		assertEquals("Acked-by"
		assertEquals("Some Reviewer <sr@example.com>"

		f = footers.get(3);
		assertEquals("Signed-off-by"
		assertEquals("Main Tain Er <mte@example.com>"
	}

	@Test
	public void testFilterFootersIgnoreCase() throws IOException {
		final RevCommit commit = parse("subject\n\nbody of commit\n"
				+ "Not-A-Footer-Line: this line must not be read as a footer\n"
				+ "Signed-Off-By: A. U. Thor <a@example.com>\n"
				+ "CC:            <some.mailing.list@example.com>\n"
				+ "Acked-by: Some Reviewer <sr@example.com>\n"
				+ "signed-off-by: Main Tain Er <mte@example.com>\n");
		final List<String> footers = commit.getFooterLines("signed-off-by");

		assertNotNull(footers);
		assertEquals(2

		assertEquals("A. U. Thor <a@example.com>"
		assertEquals("Main Tain Er <mte@example.com>"
	}

	@Test
	public void testMatchesBugId() throws IOException {
		final RevCommit commit = parse("this is a commit subject for test\n"
				+ "Simple-Bug-Id: 42\n");
		final List<FooterLine> footers = commit.getFooterLines();

		assertNotNull(footers);
		assertEquals(1

		final FooterLine line = footers.get(0);
		assertNotNull(line);
		assertEquals("Simple-Bug-Id"
		assertEquals("42"

		final FooterKey bugid = new FooterKey("Simple-Bug-Id");
		assertTrue("matches Simple-Bug-Id"
		assertFalse("not Signed-off-by"
		assertFalse("not CC"
	}

	private RevCommit parse(String msg) throws IOException {
		final StringBuilder buf = new StringBuilder();
		buf.append("tree " + ObjectId.zeroId().name() + "\n");
		buf.append("author A. U. Thor <a@example.com> 1 +0000\n");
		buf.append("committer A. U. Thor <a@example.com> 1 +0000\n");
		buf.append("\n");
		buf.append(msg);

		try (RevWalk walk = new RevWalk(db)) {
			RevCommit c = new RevCommit(ObjectId.zeroId());
			c.parseCanonical(walk
			return c;
		}
	}
}
