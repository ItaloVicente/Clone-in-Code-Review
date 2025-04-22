
package org.eclipse.jgit.util.sha1;

import static org.junit.Assert.assertEquals;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.eclipse.jgit.lib.ObjectId;
import org.junit.Test;

public class SHA1Test {
	private static final String TEST1 = "abc";

	private static final String TEST2a = "abcdbcdecdefdefgefghfghighijhi";
	private static final String TEST2b = "jkijkljklmklmnlmnomnopnopq";
	private static final String TEST2 = TEST2a + TEST2b;

	@Test
	public void test0() throws NoSuchAlgorithmException {
		ObjectId exp = ObjectId
				.fromString("da39a3ee5e6b4b0d3255bfef95601890afd80709");

		MessageDigest m = MessageDigest.getInstance("SHA-1");
		m.update(new byte[] {});
		ObjectId m1 = ObjectId.fromRaw(m.digest());

		SHA1 s = new SHA1();
		s.update(new byte[] {});
		ObjectId s1 = ObjectId.fromRaw(s.digest());

		assertEquals(m1
		assertEquals(exp
	}

	@Test
	public void test1() throws NoSuchAlgorithmException {
		ObjectId exp = ObjectId
				.fromString("a9993e364706816aba3e25717850c26c9cd0d89d");

		MessageDigest m = MessageDigest.getInstance("SHA-1");
		m.update(TEST1.getBytes(StandardCharsets.ISO_8859_1));
		ObjectId m1 = ObjectId.fromRaw(m.digest());

		SHA1 s = new SHA1();
		s.update(TEST1.getBytes(StandardCharsets.ISO_8859_1));
		ObjectId s1 = ObjectId.fromRaw(s.digest());

		assertEquals(m1
		assertEquals(exp
	}

	@Test
	public void test2() throws NoSuchAlgorithmException {
		ObjectId exp = ObjectId
				.fromString("84983e441c3bd26ebaae4aa1f95129e5e54670f1");

		MessageDigest m = MessageDigest.getInstance("SHA-1");
		m.update(TEST2.getBytes(StandardCharsets.ISO_8859_1));
		ObjectId m1 = ObjectId.fromRaw(m.digest());

		SHA1 s = new SHA1();
		s.update(TEST2.getBytes(StandardCharsets.ISO_8859_1));
		ObjectId s1 = ObjectId.fromRaw(s.digest());

		assertEquals(m1
		assertEquals(exp
	}
}
