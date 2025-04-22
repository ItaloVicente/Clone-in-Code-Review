
package org.eclipse.jgit.revwalk;

import static java.nio.charset.StandardCharsets.ISO_8859_1;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;
import java.util.TimeZone;

import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lib.CommitBuilder;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.PersonIdent;
import org.junit.Test;

public class RevCommitParseTest extends RepositoryTestCase {
	@Test
	public void testParse_NoParents() throws Exception {
		final ObjectId treeId = id("9788669ad918b6fcce64af8882fc9a81cb6aba67");
		final String authorName = "A U. Thor";
		final String authorEmail = "a_u_thor@example.com";
		final int authorTime = 1218123387;
		final String authorTimeZone = "+0700";

		final String committerName = "C O. Miter";
		final String committerEmail = "comiter@example.com";
		final int committerTime = 1218123390;
		final String committerTimeZone = "-0500";
		final StringBuilder body = new StringBuilder();

		body.append("tree ");
		body.append(treeId.name());
		body.append("\n");

		body.append("author ");
		body.append(authorName);
		body.append(" <");
		body.append(authorEmail);
		body.append("> ");
		body.append(authorTime);
		body.append(" ");
		body.append(authorTimeZone);
		body.append(" \n");

		body.append("committer ");
		body.append(committerName);
		body.append(" <");
		body.append(committerEmail);
		body.append("> ");
		body.append(committerTime);
		body.append(" ");
		body.append(committerTimeZone);
		body.append("\n");

		body.append("\n");

		final RevWalk rw = new RevWalk(db);
		final RevCommit c;

		c = new RevCommit(id("9473095c4cb2f12aefe1db8a355fe3fafba42f67"));
		assertNull(c.getTree());
		assertNull(c.parents);

		c.parseCanonical(rw
		assertNotNull(c.getTree());
		assertEquals(treeId
		assertSame(rw.lookupTree(treeId)

		assertNotNull(c.parents);
		assertEquals(0
		assertEquals(""

		final PersonIdent cAuthor = c.getAuthorIdent();
		assertNotNull(cAuthor);
		assertEquals(authorName
		assertEquals(authorEmail
		assertEquals((long)authorTime * 1000
		assertEquals(TimeZone.getTimeZone("GMT" + authorTimeZone)

		final PersonIdent cCommitter = c.getCommitterIdent();
		assertNotNull(cCommitter);
		assertEquals(committerName
		assertEquals(committerEmail
		assertEquals((long)committerTime * 1000
		assertEquals(TimeZone.getTimeZone("GMT" + committerTimeZone)
	}

	private RevCommit create(String msg) throws Exception {
		final StringBuilder b = new StringBuilder();
		b.append("tree 9788669ad918b6fcce64af8882fc9a81cb6aba67\n");
		b.append("author A U. Thor <a_u_thor@example.com> 1218123387 +0700\n");
		b.append("committer C O. Miter <c@example.com> 1218123390 -0500\n");
		b.append("\n");
		b.append(msg);

		final RevCommit c;
		c = new RevCommit(id("9473095c4cb2f12aefe1db8a355fe3fafba42f67"));
		c.parseCanonical(new RevWalk(db)
		return c;
	}

	@Test
	public void testParse_WeirdHeaderOnlyCommit() throws Exception {
		final StringBuilder b = new StringBuilder();
		b.append("tree 9788669ad918b6fcce64af8882fc9a81cb6aba67\n");
		b.append("author A U. Thor <a_u_thor@example.com> 1218123387 +0700\n");
		b.append("committer C O. Miter <c@example.com> 1218123390 -0500\n");

		final RevCommit c;
		c = new RevCommit(id("9473095c4cb2f12aefe1db8a355fe3fafba42f67"));
		c.parseCanonical(new RevWalk(db)

		assertEquals(""
		assertEquals(""
	}

	@Test
	public void testParse_incompleteAuthorAndCommitter() throws Exception {
		final StringBuilder b = new StringBuilder();
		b.append("tree 9788669ad918b6fcce64af8882fc9a81cb6aba67\n");
		b.append("author <a_u_thor@example.com> 1218123387 +0700\n");
		b.append("committer <> 1218123390 -0500\n");

		final RevCommit c;
		c = new RevCommit(id("9473095c4cb2f12aefe1db8a355fe3fafba42f67"));
		c.parseCanonical(new RevWalk(db)

		assertEquals(new PersonIdent(""
		assertEquals(new PersonIdent(""
	}

	@Test
	public void testParse_implicit_UTF8_encoded() throws Exception {
		final ByteArrayOutputStream b = new ByteArrayOutputStream();
		b.write("tree 9788669ad918b6fcce64af8882fc9a81cb6aba67\n".getBytes(UTF_8));
		b.write("author F\u00f6r fattare <a_u_thor@example.com> 1218123387 +0700\n".getBytes(UTF_8));
		b.write("committer C O. Miter <c@example.com> 1218123390 -0500\n".getBytes(UTF_8));
		b.write("\n".getBytes(UTF_8));
		b.write("Sm\u00f6rg\u00e5sbord\n".getBytes(UTF_8));
		b.write("\n".getBytes(UTF_8));
		b.write("\u304d\u308c\u3044\n".getBytes(UTF_8));
		final RevCommit c;
		c.parseCanonical(new RevWalk(db)

		assertSame(UTF_8
		assertEquals("F\u00f6r fattare"
		assertEquals("Sm\u00f6rg\u00e5sbord"
		assertEquals("Sm\u00f6rg\u00e5sbord\n\n\u304d\u308c\u3044\n"
	}

	@Test
	public void testParse_implicit_mixed_encoded() throws Exception {
		final ByteArrayOutputStream b = new ByteArrayOutputStream();
		b.write("tree 9788669ad918b6fcce64af8882fc9a81cb6aba67\n".getBytes(UTF_8));
		b.write("author F\u00f6r fattare <a_u_thor@example.com> 1218123387 +0700\n".getBytes(ISO_8859_1));
		b.write("committer C O. Miter <c@example.com> 1218123390 -0500\n".getBytes(UTF_8));
		b.write("\n".getBytes(UTF_8));
		b.write("Sm\u00f6rg\u00e5sbord\n".getBytes(UTF_8));
		b.write("\n".getBytes(UTF_8));
		b.write("\u304d\u308c\u3044\n".getBytes(UTF_8));
		final RevCommit c;
		c.parseCanonical(new RevWalk(db)

		assertSame(UTF_8
		assertEquals("F\u00f6r fattare"
		assertEquals("Sm\u00f6rg\u00e5sbord"
		assertEquals("Sm\u00f6rg\u00e5sbord\n\n\u304d\u308c\u3044\n"
	}

	@Test
	public void testParse_explicit_encoded() throws Exception {
		final ByteArrayOutputStream b = new ByteArrayOutputStream();
		b.write("tree 9788669ad918b6fcce64af8882fc9a81cb6aba67\n".getBytes("EUC-JP"));
		b.write("author F\u00f6r fattare <a_u_thor@example.com> 1218123387 +0700\n".getBytes("EUC-JP"));
		b.write("committer C O. Miter <c@example.com> 1218123390 -0500\n".getBytes("EUC-JP"));
		b.write("encoding euc_JP\n".getBytes("EUC-JP"));
		b.write("\n".getBytes("EUC-JP"));
		b.write("\u304d\u308c\u3044\n".getBytes("EUC-JP"));
		b.write("\n".getBytes("EUC-JP"));
		b.write("Hi\n".getBytes("EUC-JP"));
		final RevCommit c;
		c.parseCanonical(new RevWalk(db)

		assertEquals("EUC-JP"
		assertEquals("F\u00f6r fattare"
		assertEquals("\u304d\u308c\u3044"
		assertEquals("\u304d\u308c\u3044\n\nHi\n"
	}

	@Test
	public void testParse_explicit_bad_encoded() throws Exception {
		final ByteArrayOutputStream b = new ByteArrayOutputStream();
		b.write("tree 9788669ad918b6fcce64af8882fc9a81cb6aba67\n".getBytes(UTF_8));
		b.write("author F\u00f6r fattare <a_u_thor@example.com> 1218123387 +0700\n".getBytes(ISO_8859_1));
		b.write("committer C O. Miter <c@example.com> 1218123390 -0500\n".getBytes(UTF_8));
		b.write("encoding EUC-JP\n".getBytes(UTF_8));
		b.write("\n".getBytes(UTF_8));
		b.write("\u304d\u308c\u3044\n".getBytes(UTF_8));
		b.write("\n".getBytes(UTF_8));
		b.write("Hi\n".getBytes(UTF_8));
		final RevCommit c;
		c.parseCanonical(new RevWalk(db)

		assertEquals("EUC-JP"
		assertEquals("F\u00f6r fattare"
		assertEquals("\u304d\u308c\u3044"
		assertEquals("\u304d\u308c\u3044\n\nHi\n"
	}

	@Test
	public void testParse_explicit_bad_encoded2() throws Exception {
		final ByteArrayOutputStream b = new ByteArrayOutputStream();
		b.write("tree 9788669ad918b6fcce64af8882fc9a81cb6aba67\n".getBytes(UTF_8));
		b.write("author F\u00f6r fattare <a_u_thor@example.com> 1218123387 +0700\n".getBytes(UTF_8));
		b.write("committer C O. Miter <c@example.com> 1218123390 -0500\n".getBytes(UTF_8));
		b.write("encoding ISO-8859-1\n".getBytes(UTF_8));
		b.write("\n".getBytes(UTF_8));
		b.write("\u304d\u308c\u3044\n".getBytes(UTF_8));
		b.write("\n".getBytes(UTF_8));
		b.write("Hi\n".getBytes(UTF_8));
		final RevCommit c;
		c.parseCanonical(new RevWalk(db)

		assertEquals("ISO-8859-1"
		assertEquals("F\u00f6r fattare"
		assertEquals("\u304d\u308c\u3044"
		assertEquals("\u304d\u308c\u3044\n\nHi\n"
	}

	@Test
	public void testParse_incorrectUtf8Name() throws Exception {
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		b.write("tree 9788669ad918b6fcce64af8882fc9a81cb6aba67\n"
				.getBytes(UTF_8));
		b.write("author au <a@example.com> 1218123387 +0700\n".getBytes(UTF_8));
		b.write("committer co <c@example.com> 1218123390 -0500\n"
				.getBytes(UTF_8));
		b.write("encoding 'utf8'\n".getBytes(UTF_8));
		b.write("\n".getBytes(UTF_8));
		b.write("Sm\u00f6rg\u00e5sbord\n".getBytes(UTF_8));

		RevCommit c = new RevCommit(
				id("9473095c4cb2f12aefe1db8a355fe3fafba42f67"));
		c.parseCanonical(new RevWalk(db)
		assertEquals("'utf8'"
		assertEquals("Sm\u00f6rg\u00e5sbord\n"

		try {
			c.getEncoding();
			fail("Expected " + IllegalCharsetNameException.class);
		} catch (IllegalCharsetNameException badName) {
			assertEquals("'utf8'"
		}
	}

	@Test
	public void testParse_illegalEncoding() throws Exception {
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		b.write("tree 9788669ad918b6fcce64af8882fc9a81cb6aba67\n".getBytes(UTF_8));
		b.write("author au <a@example.com> 1218123387 +0700\n".getBytes(UTF_8));
		b.write("committer co <c@example.com> 1218123390 -0500\n".getBytes(UTF_8));
		b.write("encoding utf-8logoutputencoding=gbk\n".getBytes(UTF_8));
		b.write("\n".getBytes(UTF_8));
		b.write("message\n".getBytes(UTF_8));

		RevCommit c = new RevCommit(
				id("9473095c4cb2f12aefe1db8a355fe3fafba42f67"));
		c.parseCanonical(new RevWalk(db)
		assertEquals("utf-8logoutputencoding=gbk"
		assertEquals("message\n"
		assertEquals("message"
		assertTrue(c.getFooterLines().isEmpty());
		assertEquals("au"

		try {
			c.getEncoding();
			fail("Expected " + IllegalCharsetNameException.class);
		} catch (IllegalCharsetNameException badName) {
			assertEquals("utf-8logoutputencoding=gbk"
		}
	}

	@Test
	public void testParse_unsupportedEncoding() throws Exception {
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		b.write("tree 9788669ad918b6fcce64af8882fc9a81cb6aba67\n".getBytes(UTF_8));
		b.write("author au <a@example.com> 1218123387 +0700\n".getBytes(UTF_8));
		b.write("committer co <c@example.com> 1218123390 -0500\n".getBytes(UTF_8));
		b.write("encoding it_IT.UTF8\n".getBytes(UTF_8));
		b.write("\n".getBytes(UTF_8));
		b.write("message\n".getBytes(UTF_8));

		RevCommit c = new RevCommit(
				id("9473095c4cb2f12aefe1db8a355fe3fafba42f67"));
		c.parseCanonical(new RevWalk(db)
		assertEquals("it_IT.UTF8"
		assertEquals("message\n"
		assertEquals("message"
		assertTrue(c.getFooterLines().isEmpty());
		assertEquals("au"

		try {
			c.getEncoding();
			fail("Expected " + UnsupportedCharsetException.class);
		} catch (UnsupportedCharsetException badName) {
			assertEquals("it_IT.UTF8"
		}
	}

	@Test
	public void testParse_NoMessage() throws Exception {
		final String msg = "";
		final RevCommit c = create(msg);
		assertEquals(msg
		assertEquals(msg
	}

	@Test
	public void testParse_OnlyLFMessage() throws Exception {
		final RevCommit c = create("\n");
		assertEquals("\n"
		assertEquals(""
	}

	@Test
	public void testParse_ShortLineOnlyNoLF() throws Exception {
		final String shortMsg = "This is a short message.";
		final RevCommit c = create(shortMsg);
		assertEquals(shortMsg
		assertEquals(shortMsg
	}

	@Test
	public void testParse_ShortLineOnlyEndLF() throws Exception {
		final String shortMsg = "This is a short message.";
		final String fullMsg = shortMsg + "\n";
		final RevCommit c = create(fullMsg);
		assertEquals(fullMsg
		assertEquals(shortMsg
	}

	@Test
	public void testParse_ShortLineOnlyEmbeddedLF() throws Exception {
		final String fullMsg = "This is a\nshort message.";
		final String shortMsg = fullMsg.replace('\n'
		final RevCommit c = create(fullMsg);
		assertEquals(fullMsg
		assertEquals(shortMsg
	}

	@Test
	public void testParse_ShortLineOnlyEmbeddedAndEndingLF() throws Exception {
		final String fullMsg = "This is a\nshort message.\n";
		final String shortMsg = "This is a short message.";
		final RevCommit c = create(fullMsg);
		assertEquals(fullMsg
		assertEquals(shortMsg
	}

	@Test
	public void testParse_GitStyleMessage() throws Exception {
		final String shortMsg = "This fixes a bug.";
		final String body = "We do it with magic and pixie dust and stuff.\n"
				+ "\n" + "Signed-off-by: A U. Thor <author@example.com>\n";
		final String fullMsg = shortMsg + "\n" + "\n" + body;
		final RevCommit c = create(fullMsg);
		assertEquals(fullMsg
		assertEquals(shortMsg
	}

	@Test
	public void testParse_PublicParseMethod()
			throws UnsupportedEncodingException {
		CommitBuilder src = new CommitBuilder();
		try (ObjectInserter.Formatter fmt = new ObjectInserter.Formatter()) {
			src.setTreeId(fmt.idFor(Constants.OBJ_TREE
		}
		src.setAuthor(author);
		src.setCommitter(committer);
		src.setMessage("Test commit\n\nThis is a test.\n");

		RevCommit p = RevCommit.parse(src.build());
		assertEquals(src.getTreeId()
		assertEquals(0
		assertEquals(author
		assertEquals(committer
		assertEquals("Test commit"
		assertEquals(src.getMessage()
	}

	@Test
	public void testParse_GitStyleMessageWithCRLF() throws Exception {
		final String shortMsgIn = "This fixes a\r\nbug.\r\n\r\n";
		final String shortMsg = "This fixes a bug.";
		final String body = "We do it with magic and pixie dust\r\nand stuff.\r\n"
				+ "\r\n\r\n"
				+ "Signed-off-by: A U. Thor <author@example.com>\r\n";
		final String fullMsg = shortMsgIn + "\r\n" + "\r\n" + body;
		final RevCommit c = create(fullMsg);
		assertEquals(fullMsg
		assertEquals(shortMsg
	}

	private static ObjectId id(String str) {
		return ObjectId.fromString(str);
	}

	@Test
	public void testParse_gpgSig() throws Exception {
		String commit = "tree e3a1035abd2b319bb01e57d69b0ba6cab289297e\n" +
		"parent 54e895b87c0768d2317a2b17062e3ad9f76a8105\n" +
		"committer A U Thor <author@xample.com 1528968566 +0200\n" +
		"gpgsig -----BEGIN PGP SIGNATURE-----\n" +
		" \n" +
		" wsBcBAABCAAQBQJbGB4pCRBK7hj4Ov3rIwAAdHIIAENrvz23867ZgqrmyPemBEZP\n" +
		" U24B1Tlq/DWvce2buaxmbNQngKZ0pv2s8VMc11916WfTIC9EKvioatmpjduWvhqj\n" +
		" znQTFyiMor30pyYsfrqFuQZvqBW01o8GEWqLg8zjf9Rf0R3LlOEw86aT8CdHRlm6\n" +
		" wlb22xb8qoX4RB+LYfz7MhK5F+yLOPXZdJnAVbuyoMGRnDpwdzjL5Hj671+XJxN5\n" +
		" xXXyUpndEOmT0CIcKHrN/kbYoVL28OJaxoBuva3WYQaRrzEe3X02NMxZe9gkSqA=\n" +
		" =TClh\n" +
		" -----END PGP SIGNATURE-----\n" +
		"some other header\n\n" +
		"commit message";

		final RevCommit c;
		c = new RevCommit(id("9473095c4cb2f12aefe1db8a355fe3fafba42f67"));
		c.parseCanonical(new RevWalk(db)
		String gpgSig = new String(c.getRawGpgSignature()
		assertTrue(gpgSig.startsWith("-----BEGIN"));
		assertTrue(gpgSig.endsWith("END PGP SIGNATURE-----"));
	}

	@Test
	public void testParse_NoGpgSig() throws Exception {
		final RevCommit c = create("a message");
		assertNull(c.getRawGpgSignature());
	}
}
