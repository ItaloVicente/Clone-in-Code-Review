package org.eclipse.jgit.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;

import org.junit.Test;

public class RawParseUtilsTest {

	@Test
	public void testParseEncoding_ISO8859_1_encoding() {
		Charset result = RawParseUtils.parseEncoding("encoding ISO-8859-1\n"
				.getBytes());
		assertNotNull(result);
	}

	@Test
	public void testParseEncoding_Accept_Latin_One_AsISO8859_1() {
		Charset result = RawParseUtils.parseEncoding("encoding latin-1\n"
				.getBytes());
		assertNotNull(result);
		assertEquals("ISO-8859-1"
	}

	@Test
	public void testParseEncoding_badEncoding() {
		try {
			RawParseUtils.parseEncoding("encoding xyz\n".getBytes());
			fail("should throw an UnsupportedCharsetException: xyz");
		} catch (UnsupportedCharsetException e) {
			assertNotNull(e);
			assertEquals("xyz"
		}
	}

}
