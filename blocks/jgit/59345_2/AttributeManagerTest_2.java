
package org.eclipse.jgit.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;
import static org.eclipse.jgit.lib.CoreConfig.StreamType.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import org.eclipse.jgit.lib.CoreConfig.StreamType;
import org.eclipse.jgit.util.IO;
import org.eclipse.jgit.util.io.StreamConversionFactory;
import org.junit.Test;

public class StreamConversionFactoryTest {

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

	private void testCheckout(StreamType streamTypeText
			StreamType streamTypeWithBinaryCheck
			String expectedOutput) throws Exception {
		ByteArrayOutputStream b;

		b = new ByteArrayOutputStream();
		try (OutputStream out = StreamConversionFactory.checkOutStream(b
				streamTypeText)) {
			out.write(input.getBytes(StandardCharsets.UTF_8));
		}
		assertEquals(expectedOutput
				new String(b.toByteArray()

		b = new ByteArrayOutputStream();
		try (OutputStream out = StreamConversionFactory.checkOutStream(b
				streamTypeWithBinaryCheck)) {
			out.write(input.getBytes(StandardCharsets.UTF_8));
		}
		assertEquals(expectedOutput
				new String(b.toByteArray()

		b = new ByteArrayOutputStream();
		b.write(input.getBytes(StandardCharsets.UTF_8));
		b.write(0x00);
		b.write(0x01);
		b.write(0x02);
		byte[] expectedBytes = b.toByteArray();
		byte[] actualBytes;
		b = new ByteArrayOutputStream();
		try (OutputStream out = StreamConversionFactory.checkOutStream(b
				streamTypeWithBinaryCheck)) {
			out.write(input.getBytes(StandardCharsets.UTF_8));
			out.write(0x00);
			out.write(0x01);
			out.write(0x02);
		}
		actualBytes = b.toByteArray();
		assertArrayEquals(expectedBytes
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

	private void testCheckin(StreamType streamTypeText
			StreamType streamTypeWithBinaryCheck
			String expectedInput) throws Exception {
		byte[] inputBytes = input.getBytes(StandardCharsets.UTF_8);

		try (InputStream in = StreamConversionFactory.checkInStream(
				new ByteArrayInputStream(inputBytes)
				streamTypeText)) {
			byte[] b = new byte[1024];
			int len = IO.readFully(in
			assertEquals(expectedInput
					new String(b
		}

		try (InputStream in = StreamConversionFactory.checkInStream(
				new ByteArrayInputStream(inputBytes)
				streamTypeWithBinaryCheck)) {
			byte[] b = new byte[1024];
			int len = IO.readFully(in
			assertEquals(expectedInput
					new String(b
		}

		byte[] inputBytesWithBinary = new byte[inputBytes.length+3];
		System.arraycopy(inputBytes
				inputBytes.length);
		inputBytesWithBinary[inputBytes.length+1]=0x00;
		inputBytesWithBinary[inputBytes.length+1]=0x01;
		inputBytesWithBinary[inputBytes.length+1]=0x02;
		try (InputStream in = StreamConversionFactory.checkInStream(
				new ByteArrayInputStream(inputBytesWithBinary)
				streamTypeWithBinaryCheck)) {
			byte[] b = new byte[1024];
			int len = IO.readFully(in
			assertArrayEquals(inputBytesWithBinary
		}
	}

}
