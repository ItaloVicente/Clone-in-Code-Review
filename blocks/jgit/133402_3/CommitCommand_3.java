package org.eclipse.jgit.lib;

import static java.nio.charset.StandardCharsets.US_ASCII;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.MessageFormat;

import org.bouncycastle.util.encoders.Base64;
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
		String signature = new String(Base64.decode(
				"LS0tLS1CRUdJTiBQR1AgU0lHTkFUVVJFLS0tLS0KVmVyc2lvbjogQkNQRyB2"
						+ "MS42MAoKaVFFY0JBQUJDQUFHQlFKYjljVmhBQW9KRUtYKzZBeGcvNlRaZUZz"
						+ "SC8wQ1kwV1gvejdVOCs3UzVnaUZYNHdINApvcHZCd3F5dDZPWDhsZ053VHdC"
						+ "R0hGTnQ4TGRtRENDbUtvcS9Yd2tOaTNBUlZqTGhlM2dCY0tYTm9hdnZQazJa"
						+ "CmdJZzVDaGV2R2tVNGFmV0NPTUxWRVlua0NCR3cyKzg2WGhySzFQN2dUSEVr"
						+ "MVJkK1l2MVpSREpCWStmRk83eXoKdVNCdUY1UnBFWTJzSmlJdnAyN0d1Yi9y"
						+ "WTNCNU5UUi9mZU8veitiOW9pUC9mTVVocFJ3RzVLdVdVc245TlBqdwozdHZi"
						+ "Z2F3WXBVLzJVblMreG5hdk1ZNHQyZmpSWWpzb3huZFBMYjJNVVg4WDd2QzdG"
						+ "Z1dMQmxtSS9ycXVMWlZNCklRRUtram5BK2xoZWpqSzFydit1bHE0a0daSkZL"
						+ "R1lXWVloUkR3Rmc1UFRremh1ZGhOMlNHVXE1V3hxMUVnND0KPWI5T0kKLS0t"
						+ "LS1FTkQgUEdQIFNJR05BVFVSRS0tLS0t")
				US_ASCII);
		String expectedOutcome = new String(Base64.decode(
			"LS0tLS1CRUdJTiBQR1AgU0lHTkFUVVJFLS0tLS0KIFZlcnNpb246IEJDUEcg"
					+ "djEuNjAKIAogaVFFY0JBQUJDQUFHQlFKYjljVmhBQW9KRUtYKzZBeGcvNlRa"
					+ "ZUZzSC8wQ1kwV1gvejdVOCs3UzVnaUZYNHdINAogb3B2QndxeXQ2T1g4bGdO"
					+ "d1R3QkdIRk50OExkbURDQ21Lb3EvWHdrTmkzQVJWakxoZTNnQmNLWE5vYXZ2"
					+ "UGsyWgogZ0lnNUNoZXZHa1U0YWZXQ09NTFZFWW5rQ0JHdzIrODZYaHJLMVA3"
					+ "Z1RIRWsxUmQrWXYxWlJESkJZK2ZGTzd5egogdVNCdUY1UnBFWTJzSmlJdnAy"
					+ "N0d1Yi9yWTNCNU5UUi9mZU8veitiOW9pUC9mTVVocFJ3RzVLdVdVc245TlBq"
					+ "dwogM3R2Ymdhd1lwVS8yVW5TK3huYXZNWTR0MmZqUllqc294bmRQTGIyTVVY"
					+ "OFg3dkM3RmdXTEJsbUkvcnF1TFpWTQogSVFFS2tqbkErbGhlampLMXJ2K3Vs"
					+ "cTRrR1pKRktHWVdZWWhSRHdGZzVQVGt6aHVkaE4yU0dVcTVXeHExRWc0PQog"
						+ "PWI5T0kKIC0tLS0tRU5EIFBHUCBTSUdOQVRVUkUtLS0tLQ==")
				US_ASCII);
		assertGpgSignatureStringOutcome(signature
	}

	@Test
	public void writeGpgSignatureString_failsForNonAscii() throws Exception {
		String signature = "Ü Ä";
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
		GpgSignature dummy = new GpgSignature();

		CommitBuilder builder = new CommitBuilder();
		assertNull(builder.getGpgSignature());

		builder.setGpgSignature(dummy);
		assertSame(dummy

		builder.setGpgSignature(null);
		assertNull(builder.getGpgSignature());
	}
}
