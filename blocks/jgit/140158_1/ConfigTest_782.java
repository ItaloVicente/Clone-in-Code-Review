package org.eclipse.jgit.lib;

import static java.nio.charset.StandardCharsets.US_ASCII;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.MessageFormat;

import org.eclipse.jgit.internal.JGitText;
import org.junit.Test;

public class CommitBuilderTest {

	private void assertGpgSignatureStringOutcome(String signature
			String expectedOutcome) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		CommitBuilder.writeGpgSignatureString(signature
		String formatted_signature = new String(out.toByteArray()
		assertEquals(expectedOutcome
	}

	@Test
	public void writeGpgSignatureString_1() throws Exception {
		String signature = "-----BEGIN PGP SIGNATURE-----\n" +
				"Version: BCPG v1.60\n" +
				"\n" +
				"iQEcBAABCAAGBQJb9cVhAAoJEKX+6Axg/6TZeFsH/0CY0WX/z7U8+7S5giFX4wH4\n" +
				"opvBwqyt6OX8lgNwTwBGHFNt8LdmDCCmKoq/XwkNi3ARVjLhe3gBcKXNoavvPk2Z\n" +
				"gIg5ChevGkU4afWCOMLVEYnkCBGw2+86XhrK1P7gTHEk1Rd+Yv1ZRDJBY+fFO7yz\n" +
				"uSBuF5RpEY2sJiIvp27Gub/rY3B5NTR/feO/z+b9oiP/fMUhpRwG5KuWUsn9NPjw\n" +
				"3tvbgawYpU/2UnS+xnavMY4t2fjRYjsoxndPLb2MUX8X7vC7FgWLBlmI/rquLZVM\n" +
				"IQEKkjnA+lhejjK1rv+ulq4kGZJFKGYWYYhRDwFg5PTkzhudhN2SGUq5Wxq1Eg4=\n" +
				"=b9OI\n" +
				"-----END PGP SIGNATURE-----";
		String expectedOutcome = "-----BEGIN PGP SIGNATURE-----\n" +
				" Version: BCPG v1.60\n" +
				" \n" +
				" iQEcBAABCAAGBQJb9cVhAAoJEKX+6Axg/6TZeFsH/0CY0WX/z7U8+7S5giFX4wH4\n" +
				" opvBwqyt6OX8lgNwTwBGHFNt8LdmDCCmKoq/XwkNi3ARVjLhe3gBcKXNoavvPk2Z\n" +
				" gIg5ChevGkU4afWCOMLVEYnkCBGw2+86XhrK1P7gTHEk1Rd+Yv1ZRDJBY+fFO7yz\n" +
				" uSBuF5RpEY2sJiIvp27Gub/rY3B5NTR/feO/z+b9oiP/fMUhpRwG5KuWUsn9NPjw\n" +
				" 3tvbgawYpU/2UnS+xnavMY4t2fjRYjsoxndPLb2MUX8X7vC7FgWLBlmI/rquLZVM\n" +
				" IQEKkjnA+lhejjK1rv+ulq4kGZJFKGYWYYhRDwFg5PTkzhudhN2SGUq5Wxq1Eg4=\n" +
				" =b9OI\n" +
				" -----END PGP SIGNATURE-----";
		assertGpgSignatureStringOutcome(signature
	}

	@Test
	public void writeGpgSignatureString_failsForNonAscii() throws Exception {
		String signature = "Ã Ã";
		try {
			CommitBuilder.writeGpgSignatureString(signature
					new ByteArrayOutputStream());
			fail("Exception expected");
		} catch (IllegalArgumentException e) {
			String message = MessageFormat.format(JGitText.get().notASCIIString
					signature);
			assertEquals(message
		}
	}

	@Test
	public void writeGpgSignatureString_oneLineNotModified() throws Exception {
		String signature = "    A string   ";
		String expectedOutcome = signature;
		assertGpgSignatureStringOutcome(signature
	}

	@Test
	public void writeGpgSignatureString_preservesRandomWhitespace()
			throws Exception {
		String signature = "    String with    \n"
				+ "Line 2\n"
				+ " Line 3\n"
				+ "Line 4   \n"
				+ "  Line 5  ";
		String expectedOutcome = "    String with    \n"
				+ " Line 2\n"
				+ "  Line 3\n"
				+ " Line 4   \n"
				+ "   Line 5  ";
		assertGpgSignatureStringOutcome(signature
	}

	@Test
	public void writeGpgSignatureString_replaceCR() throws Exception {
		String signature = "String with \r"
				+ "Line 2\r"
				+ "Line 3\r"
				+ "Line 4\r"
				+ "Line 5";
		String expectedOutcome = "String with \n"
				+ " Line 2\n"
				+ " Line 3\n"
				+ " Line 4\n"
				+ " Line 5";
		assertGpgSignatureStringOutcome(signature
	}

	@Test
	public void writeGpgSignatureString_replaceCRLF() throws Exception {
		String signature = "String with \r\n"
				+ "Line 2\r\n"
				+ "Line 3\r\n"
				+ "Line 4\r\n"
				+ "Line 5";
		String expectedOutcome = "String with \n"
				+ " Line 2\n"
				+ " Line 3\n"
				+ " Line 4\n"
				+ " Line 5";
		assertGpgSignatureStringOutcome(signature
	}

	@Test
	public void writeGpgSignatureString_replaceCRLFMixed() throws Exception {
		String signature = "String with \r"
				+ "Line 2\r\n"
				+ "Line 3\r"
				+ "Line 4\r\n"
				+ "Line 5";
		String expectedOutcome = "String with \n"
				+ " Line 2\n"
				+ " Line 3\n"
				+ " Line 4\n"
				+ " Line 5";
		assertGpgSignatureStringOutcome(signature
	}

	@Test
	public void setGpgSignature() throws Exception {
		GpgSignature dummy = new GpgSignature(new byte[0]);

		CommitBuilder builder = new CommitBuilder();
		assertNull(builder.getGpgSignature());

		builder.setGpgSignature(dummy);
		assertSame(dummy

		builder.setGpgSignature(null);
		assertNull(builder.getGpgSignature());
	}
}
