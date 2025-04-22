
package org.eclipse.jgit.api;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.eclipse.jgit.lib.CoreConfig.EolStreamType.AUTO_CRLF;
import static org.eclipse.jgit.lib.CoreConfig.EolStreamType.AUTO_LF;
import static org.eclipse.jgit.lib.CoreConfig.EolStreamType.DIRECT;
import static org.eclipse.jgit.lib.CoreConfig.EolStreamType.TEXT_CRLF;
import static org.eclipse.jgit.lib.CoreConfig.EolStreamType.TEXT_LF;
import static org.junit.Assert.assertArrayEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

import org.eclipse.jgit.lib.CoreConfig.EolStreamType;
import org.eclipse.jgit.util.IO;
import org.eclipse.jgit.util.io.EolStreamTypeUtil;
import org.junit.Test;

public class EolStreamTypeUtilTest {

	@Test
	public void testCheckoutDirect() throws Exception {
		testCheckout(DIRECT
		testCheckout(DIRECT
		testCheckout(DIRECT

		testCheckout(DIRECT
		testCheckout(DIRECT

		testCheckout(DIRECT
		testCheckout(DIRECT

		testCheckout(DIRECT
		testCheckout(DIRECT
		testCheckout(DIRECT
		testCheckout(DIRECT
	}

	@Test
	public void testCheckoutLF() throws Exception {
		testCheckout(TEXT_LF
		testCheckout(TEXT_LF
		testCheckout(TEXT_LF

		testCheckout(TEXT_LF
		testCheckout(TEXT_LF

		testCheckout(TEXT_LF
		testCheckout(TEXT_LF

		testCheckout(TEXT_LF
		testCheckout(TEXT_LF
		testCheckout(TEXT_LF
		testCheckout(TEXT_LF
	}

	@Test
	public void testCheckoutCRLF() throws Exception {
		testCheckout(TEXT_CRLF
		testCheckout(TEXT_CRLF
		testCheckout(TEXT_CRLF

		testCheckout(TEXT_CRLF
		testCheckout(TEXT_CRLF

		testCheckout(TEXT_CRLF
		testCheckout(TEXT_CRLF

		testCheckout(TEXT_CRLF
		testCheckout(TEXT_CRLF
		testCheckout(TEXT_CRLF
		testCheckout(TEXT_CRLF
	}

	private void testCheckout(EolStreamType streamTypeText
			EolStreamType streamTypeWithBinaryCheck
			String expectedConversion) throws Exception {
		ByteArrayOutputStream b;
		byte[] outputBytes = output.getBytes(UTF_8);
		byte[] expectedConversionBytes = expectedConversion.getBytes(UTF_8);

		b = new ByteArrayOutputStream();
		try (OutputStream out = EolStreamTypeUtil.wrapOutputStream(b
				streamTypeText)) {
			out.write(outputBytes);
		}
		assertArrayEquals(expectedConversionBytes

		b = new ByteArrayOutputStream();
		try (OutputStream out = EolStreamTypeUtil.wrapOutputStream(b
				streamTypeWithBinaryCheck)) {
			out.write(outputBytes);
		}
		assertArrayEquals(expectedConversionBytes

		outputBytes = extendWithBinaryData(outputBytes);
		expectedConversionBytes = extendWithBinaryData(expectedConversionBytes);

		b = new ByteArrayOutputStream();
		try (OutputStream out = EolStreamTypeUtil.wrapOutputStream(b
				streamTypeText)) {
			out.write(outputBytes);
		}
		assertArrayEquals(expectedConversionBytes

		b = new ByteArrayOutputStream();
		try (OutputStream out = EolStreamTypeUtil.wrapOutputStream(b
				streamTypeWithBinaryCheck)) {
			out.write(outputBytes);
		}
		assertArrayEquals(outputBytes
	}

	@Test
	public void testCheckinDirect() throws Exception {
		testCheckin(DIRECT
		testCheckin(DIRECT
		testCheckin(DIRECT

		testCheckin(DIRECT
		testCheckin(DIRECT

		testCheckin(DIRECT
		testCheckin(DIRECT

		testCheckin(DIRECT
		testCheckin(DIRECT
		testCheckin(DIRECT
		testCheckin(DIRECT
	}

	@Test
	public void testCheckinLF() throws Exception {
		testCheckin(TEXT_LF
		testCheckin(TEXT_LF
		testCheckin(TEXT_LF

		testCheckin(TEXT_LF
		testCheckin(TEXT_LF

		testCheckin(TEXT_LF
		testCheckin(TEXT_LF

		testCheckin(TEXT_LF
		testCheckin(TEXT_LF
		testCheckin(TEXT_LF
		testCheckin(TEXT_LF
	}

	@Test
	public void testCheckinCRLF() throws Exception {
		testCheckin(TEXT_CRLF
		testCheckin(TEXT_CRLF
		testCheckin(TEXT_CRLF

		testCheckin(TEXT_CRLF
		testCheckin(TEXT_CRLF

		testCheckin(TEXT_CRLF
		testCheckin(TEXT_CRLF

		testCheckin(TEXT_CRLF
		testCheckin(TEXT_CRLF
		testCheckin(TEXT_CRLF
		testCheckin(TEXT_CRLF
	}

	private void testCheckin(EolStreamType streamTypeText
			EolStreamType streamTypeWithBinaryCheck
			String expectedConversion) throws Exception {
		byte[] inputBytes = input.getBytes(UTF_8);
		byte[] expectedConversionBytes = expectedConversion.getBytes(UTF_8);

		try (InputStream in = EolStreamTypeUtil.wrapInputStream(
				new ByteArrayInputStream(inputBytes)
				streamTypeText)) {
			byte[] b = new byte[1024];
			int len = IO.readFully(in
			assertArrayEquals(expectedConversionBytes
		}

		try (InputStream in = EolStreamTypeUtil.wrapInputStream(
				new ByteArrayInputStream(inputBytes)
				streamTypeWithBinaryCheck)) {
			byte[] b = new byte[1024];
			int len = IO.readFully(in
			assertArrayEquals(expectedConversionBytes
		}

		inputBytes = extendWithBinaryData(inputBytes);
		expectedConversionBytes = extendWithBinaryData(expectedConversionBytes);

		try (InputStream in = EolStreamTypeUtil.wrapInputStream(
				new ByteArrayInputStream(inputBytes)
			byte[] b = new byte[1024];
			int len = IO.readFully(in
			assertArrayEquals(expectedConversionBytes
		}

		try (InputStream in = EolStreamTypeUtil.wrapInputStream(
				new ByteArrayInputStream(inputBytes)
				streamTypeWithBinaryCheck)) {
			byte[] b = new byte[1024];
			int len = IO.readFully(in
			assertArrayEquals(inputBytes
		}
	}

	private byte[] extendWithBinaryData(byte[] data) throws Exception {
		int n = 3;
		byte[] dataEx = new byte[data.length + n];
		System.arraycopy(data
		for (int i = 0; i < n; i++) {
			dataEx[data.length + i] = (byte) i;
		}
		return dataEx;
	}

}
