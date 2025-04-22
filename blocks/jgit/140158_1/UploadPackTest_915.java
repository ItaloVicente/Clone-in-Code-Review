
package org.eclipse.jgit.transport;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.Ignore;
import org.junit.Test;

public class URIishTest {


	@SuppressWarnings("unused")
	@Test(expected = URISyntaxException.class)
	public void shouldRaiseErrorOnEmptyURI() throws Exception {
		new URIish("");
	}

	@SuppressWarnings("unused")
	@Test(expected = URISyntaxException.class)
	public void shouldRaiseErrorOnNullURI() throws Exception {
		new URIish((String) null);
	}

	@Test
	public void testUnixFile() throws Exception {
		final String str = "/home/m y";
		URIish u = new URIish(str);
		assertNull(u.getScheme());
		assertFalse(u.isRemote());
		assertEquals(str
		assertEquals(str
		assertEquals(str
		assertEquals(str
		assertEquals(u
	}

	@Test
	public void testWindowsFile() throws Exception {
		final String str = "D:/m y";
		URIish u = new URIish(str);
		assertNull(u.getScheme());
		assertFalse(u.isRemote());
		assertEquals(str
		assertEquals(str
		assertEquals(str
		assertEquals(str
		assertEquals(u
	}

	@Test
	public void testWindowsFile2() throws Exception {
		final String str = "D:\\m y";
		URIish u = new URIish(str);
		assertNull(u.getScheme());
		assertFalse(u.isRemote());
		assertEquals("D:\\m y"
		assertEquals("D:\\m y"
		assertEquals("D:\\m y"
		assertEquals("D:\\m y"
		assertEquals(u
	}

	@Test
	public void testRelativePath() throws Exception {
		final String str = "../../foo/bar";
		URIish u = new URIish(str);
		assertNull(u.getScheme());
		assertFalse(u.isRemote());
		assertEquals(str
		assertEquals(str
		assertEquals(str
		assertEquals(str
		assertEquals(u
	}

	@Test
	public void testUNC() throws Exception {
		final String str = "\\\\some\\place";
		URIish u = new URIish(str);
		assertNull(u.getScheme());
		assertFalse(u.isRemote());
		assertEquals("\\\\some\\place"
		assertEquals("\\\\some\\place"
		assertEquals("\\\\some\\place"
		assertEquals("\\\\some\\place"
		assertEquals(u
	}

	@Test
	public void testFileProtoUnix() throws Exception {
		URIish u = new URIish(str);
		assertEquals("file"
		assertFalse(u.isRemote());
		assertEquals("/home/m y"
		assertEquals("/home/m y"
		assertEquals(u
	}

	@Test
	public void testURIEncode_00() throws Exception {
		URIish u = new URIish(str);
		assertEquals("file"
		assertFalse(u.isRemote());
		assertEquals("/home/m%00y"
		assertEquals("/home/m\u0000y"
		assertEquals(u
	}

	@Test
	public void testURIEncode_0a() throws Exception {
		URIish u = new URIish(str);
		assertEquals("file"
		assertFalse(u.isRemote());
		assertEquals("/home/m%0ay"
		assertEquals("/home/m\ny"
		assertEquals(u
	}

	@Test
	public void testURIEncode_unicode() throws Exception {
		URIish u = new URIish(str);
		assertEquals("file"
		assertFalse(u.isRemote());
		assertEquals("/home/m%c3%a5y"
		assertEquals("/home/m\u00e5y"
		assertEquals(u
	}

	@Test
	public void testFileProtoWindows() throws Exception {
		URIish u = new URIish(str);
		assertEquals("file"
		assertFalse(u.isRemote());
		assertEquals(null
		assertEquals(-1
		assertEquals(null
		assertEquals(null
		assertEquals("D:/m y"
		assertEquals("D:/m y"
		assertEquals(u
	}

	@Test
	public void testFileProtoWindowsWithHost() throws Exception {
		URIish u = new URIish(str);
		assertEquals("file"
		assertTrue(u.isRemote());
		assertEquals("localhost"
		assertEquals(-1
		assertEquals(null
		assertEquals(null
		assertEquals("D:/m y"
		assertEquals("D:/m y"
		assertEquals(u
	}

	@Test
	public void testFileProtoWindowsWithHostAndPort() throws Exception {
		URIish u = new URIish(str);
		assertEquals("file"
		assertTrue(u.isRemote());
		assertEquals("localhost"
		assertEquals(80
		assertEquals(null
		assertEquals(null
		assertEquals("D:/m y"
		assertEquals("D:/m y"
		assertEquals(u
	}

	@Test
	public void testFileProtoWindowsWithHostAndEmptyPortIsAmbiguous()
			throws Exception {
		URIish u = new URIish(str);
		assertEquals("file"
		assertFalse(u.isRemote());
		assertEquals(null
		assertEquals(-1
		assertEquals(null
		assertEquals(null
		assertEquals("localhost:/D:/m y"
		assertEquals("localhost:/D:/m y"
		assertEquals(u
	}

	@Test
	public void testFileProtoWindowsMissingHostSlash() throws Exception {
		URIish u = new URIish(str);
		assertEquals("file"
		assertFalse(u.isRemote());
		assertEquals("D:/m y"
		assertEquals("D:/m y"
		assertEquals(u
	}

	@Test
	public void testFileProtoWindowsMissingHostSlash2() throws Exception {
		URIish u = new URIish(str);
		assertEquals("file"
		assertFalse(u.isRemote());
		assertEquals("D: /m y"
		assertEquals("D: /m y"
		assertEquals(u
	}

	@Test
	public void testGitProtoUnix() throws Exception {
		URIish u = new URIish(str);
		assertEquals("git"
		assertTrue(u.isRemote());
		assertEquals("example.com"
		assertEquals("/home/m y"
		assertEquals("/home/m y"
		assertEquals(u
	}

	@Test
	public void testGitProtoUnixPort() throws Exception {
		URIish u = new URIish(str);
		assertEquals("git"
		assertTrue(u.isRemote());
		assertEquals("example.com"
		assertEquals("/home/m y"
		assertEquals("/home/m y"
		assertEquals(333
		assertEquals(u
	}

	@Test
	public void testGitProtoWindowsPort() throws Exception {
		URIish u = new URIish(str);
		assertEquals("git"
		assertTrue(u.isRemote());
		assertEquals("D:/m y"
		assertEquals("D:/m y"
		assertEquals(338
		assertEquals("example.com"
		assertEquals(u
	}

	@Test
	public void testGitProtoWindows() throws Exception {
		URIish u = new URIish(str);
		assertEquals("git"
		assertTrue(u.isRemote());
		assertEquals("D:/m y"
		assertEquals("D:/m y"
		assertEquals("example.com"
		assertEquals(-1
		assertEquals(u
	}

	@Test
	public void testScpStyleNoURIDecoding() throws Exception {
		final String str = "example.com:some/p%20ath";
		URIish u = new URIish(str);
		assertNull(u.getScheme());
		assertTrue(u.isRemote());
		assertEquals("some/p%20ath"
		assertEquals("some/p%20ath"
		assertEquals("example.com"
		assertEquals(-1
		assertEquals(str
		assertEquals(str
		assertEquals(u
	}

	@Test
	public void testScpStyleWithoutUserRelativePath() throws Exception {
		final String str = "example.com:some/p ath";
		URIish u = new URIish(str);
		assertNull(u.getScheme());
		assertTrue(u.isRemote());
		assertEquals("some/p ath"
		assertEquals("some/p ath"
		assertEquals("example.com"
		assertEquals(-1
		assertEquals(str
		assertEquals(str
		assertEquals(u
	}

	@Test
	public void testScpStyleWithoutUserAbsolutePath() throws Exception {
		final String str = "example.com:/some/p ath";
		URIish u = new URIish(str);
		assertNull(u.getScheme());
		assertTrue(u.isRemote());
		assertEquals("/some/p ath"
		assertEquals("/some/p ath"
		assertEquals("example.com"
		assertEquals(-1
		assertEquals(str
		assertEquals(str
		assertEquals(u
	}

	@Test
	public void testScpStyleWithUser() throws Exception {
		final String str = "user@example.com:some/p ath";
		URIish u = new URIish(str);
		assertNull(u.getScheme());
		assertTrue(u.isRemote());
		assertEquals("some/p ath"
		assertEquals("some/p ath"
		assertEquals("user"
		assertEquals("example.com"
		assertEquals(-1
		assertEquals(str
		assertEquals(str
		assertEquals(u
	}

	@Test
	public void testGitSshProto() throws Exception {
		URIish u = new URIish(str);
		assertEquals("git+ssh"
		assertTrue(u.isRemote());
		assertEquals("/some/p ath"
		assertEquals("/some/p ath"
		assertEquals("example.com"
		assertEquals(-1
		assertEquals(u
	}

	@Test
	public void testSshGitProto() throws Exception {
		URIish u = new URIish(str);
		assertEquals("ssh+git"
		assertTrue(u.isRemote());
		assertEquals("/some/p ath"
		assertEquals("/some/p ath"
		assertEquals("example.com"
		assertEquals(-1
		assertEquals(u
	}

	@Test
	public void testSshProto() throws Exception {
		URIish u = new URIish(str);
		assertEquals("ssh"
		assertTrue(u.isRemote());
		assertEquals("/some/p ath"
		assertEquals("/some/p ath"
		assertEquals("example.com"
		assertEquals(-1
		assertEquals(u
	}

	@Test
	public void testSshProtoHostOnly() throws Exception {
		URIish u = new URIish(str);
		assertEquals("ssh"
		assertTrue(u.isRemote());
		assertEquals("/"
		assertEquals("/"
		assertEquals("example.com"
		assertEquals(-1
		assertEquals("example.com"
		assertEquals(u
	}

	@Test
	public void testSshProtoHostWithAuthentication() throws Exception {
		URIish u = new URIish(str);
		assertEquals("ssh"
		assertTrue(u.isRemote());
		assertEquals("/"
		assertEquals("/"
		assertEquals("example.com"
		assertEquals(-1
		assertEquals("example.com"
		assertEquals("user"
		assertEquals("secret@pass"
		assertEquals(u
	}

	@Test
	public void testSshProtoHostWithPort() throws Exception {
		URIish u = new URIish(str);
		assertEquals("ssh"
		assertTrue(u.isRemote());
		assertEquals("/"
		assertEquals("/"
		assertEquals("example.com"
		assertEquals(2222
		assertEquals("example.com"
		assertEquals(u
	}

	@Test
	public void testSshProtoHostWithEmptyPortAndPath() throws Exception {
		URIish u = new URIish(str);
		assertEquals("ssh"
		assertTrue(u.isRemote());
		assertEquals("/path"
		assertEquals("/path"
		assertEquals("example.com"
		assertEquals(-1
		assertEquals(u
		assertEquals(u
	}

	@Test
	public void testSshProtoWithUserAndPort() throws Exception {
		URIish u = new URIish(str);
		assertEquals("ssh"
		assertTrue(u.isRemote());
		assertEquals("/some/p ath"
		assertEquals("/some/p ath"
		assertEquals("example.com"
		assertEquals("user"
		assertNull(u.getPass());
		assertEquals(33
				u.toASCIIString());
		assertEquals(u
	}

	@Test
	public void testSshProtoWithUserPassAndPort() throws Exception {
		URIish u = new URIish(str);
		assertEquals("ssh"
		assertTrue(u.isRemote());
		assertEquals("/some/p ath"
		assertEquals("/some/p ath"
		assertEquals("example.com"
		assertEquals("user"
		assertEquals("pass"
		assertEquals(33
				u.toPrivateString());
				u.toPrivateASCIIString());
		assertEquals(u.setPass(null).toPrivateString()
		assertEquals(u.setPass(null).toPrivateASCIIString()
		assertEquals(u
	}

	@Test
	public void testSshProtoWithEmailUserAndPort() throws Exception {
		URIish u = new URIish(str);
		assertEquals("ssh"
		assertTrue(u.isRemote());
		assertEquals("/some/p ath"
		assertEquals("/some/p ath"
		assertEquals("example.com"
		assertEquals("user.name@email.com"
		assertNull(u.getPass());
		assertEquals(33
				u.toPrivateString());
				u.toPrivateASCIIString());
		assertEquals(u.setPass(null).toPrivateString()
		assertEquals(u.setPass(null).toPrivateASCIIString()
		assertEquals(u
	}

	@Test
	public void testSshProtoWithEmailUserPassAndPort() throws Exception {
		URIish u = new URIish(str);
		assertEquals("ssh"
		assertTrue(u.isRemote());
		assertEquals("/some/p ath"
		assertEquals("/some/p ath"
		assertEquals("example.com"
		assertEquals("user.name@email.com"
		assertEquals("pass@wor:d"
		assertEquals(33
				u.toPrivateString());
				u.toPrivateASCIIString());
		assertEquals(u.setPass(null).toPrivateString()
		assertEquals(u.setPass(null).toPrivateASCIIString()
		assertEquals(u
	}

	@Test
	public void testSshProtoWithADUserPassAndPort() throws Exception {
		URIish u = new URIish(str);
		assertEquals("ssh"
		assertTrue(u.isRemote());
		assertEquals("/some/p ath"
		assertEquals("/some/p ath"
		assertEquals("example.com"
		assertEquals("DOMAIN\\user"
		assertEquals("pass"
		assertEquals(33
				u.toPrivateString());
				u.toPrivateASCIIString());
		assertEquals(u.setPass(null).toPrivateString()
		assertEquals(u.setPass(null).toPrivateASCIIString()
		assertEquals(u
	}

	@Test
	public void testSshProtoWithEscapedADUserPassAndPort() throws Exception {
		URIish u = new URIish(str);
		assertEquals("ssh"
		assertTrue(u.isRemote());
		assertEquals("/some/p ath"
		assertEquals("/some/p ath"
		assertEquals("example.com"
		assertEquals("DOMAIN\\\u00fcser"
		assertEquals("pass"
		assertEquals(33
				u.toPrivateString());
		assertEquals(
				u.toPrivateASCIIString());
		assertEquals(u.setPass(null).toPrivateString()
		assertEquals(u.setPass(null).toPrivateASCIIString()
		assertEquals(u
	}

	@Test
	public void testURIEncodeDecode() throws Exception {
		URIish u = new URIish(str);
		assertEquals("ssh"
		assertTrue(u.isRemote());
		assertEquals("/some%c3%a5/p%20a th"
		assertEquals("/some\u00e5/p a th"
		assertEquals("example.com"
		assertEquals(":x%"
		assertEquals("@Ax"
		assertEquals(33
				u.toPrivateString());
		assertEquals(
				u.toPrivateASCIIString());
		assertEquals(u.setPass(null).toPrivateString()
		assertEquals(u.setPass(null).toPrivateASCIIString()
		assertEquals(u
	}

	@Test
	public void testGitWithUserHome() throws Exception {
		URIish u = new URIish(str);
		assertEquals("git"
		assertTrue(u.isRemote());
		assertEquals("~some/p ath"
		assertEquals("~some/p ath"
		assertEquals("example.com"
		assertNull(u.getUser());
		assertNull(u.getPass());
		assertEquals(-1
				u.toPrivateASCIIString());
		assertEquals(u.setPass(null).toPrivateString()
		assertEquals(u.setPass(null).toPrivateASCIIString()
		assertEquals(u
	}

	@Test
	@Ignore("Resolving ~user is beyond standard Java API and need more support")
	public void testFileWithUserHome() throws Exception {
		final String str = "~some/p ath";
		URIish u = new URIish(str);
		assertEquals("git"
		assertTrue(u.isRemote());
		assertEquals("~some/p ath"
		assertEquals("~some/p ath"
		assertEquals("example.com"
		assertNull(u.getUser());
		assertNull(u.getPass());
		assertEquals(-1
		assertEquals(str
		assertEquals(str
		assertEquals(u.setPass(null).toPrivateString()
		assertEquals(u.setPass(null).toPrivateASCIIString()
		assertEquals(u
	}

	@Test
	public void testFileWithNoneUserHomeWithTilde() throws Exception {
		final String str = "/~some/p ath";
		URIish u = new URIish(str);
		assertNull(u.getScheme());
		assertFalse(u.isRemote());
		assertEquals("/~some/p ath"
		assertEquals("/~some/p ath"
		assertNull(u.getHost());
		assertNull(u.getUser());
		assertNull(u.getPass());
		assertEquals(-1
		assertEquals(str
		assertEquals(str
		assertEquals(u.setPass(null).toPrivateString()
		assertEquals(u.setPass(null).toPrivateASCIIString()
		assertEquals(u
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetNullHumanishName() {
		new URIish().getHumanishName();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetEmptyHumanishName() throws URISyntaxException {
		new URIish(GIT_SCHEME).getHumanishName();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetAbsEmptyHumanishName() {
		new URIish().getHumanishName();
	}

	@Test
	public void testGetSet() throws Exception {
		URIish u = new URIish(str);
		u = u.setHost(u.getHost());
		u = u.setPass(u.getPass());
		u = u.setPort(u.getPort());
		assertEquals("ssh"
		assertTrue(u.isRemote());
		u = u.setRawPath(u.getRawPath());
		assertEquals("/some/p ath%20"
		u = u.setPath(u.getPath());
		assertEquals("/some/p ath "
		assertEquals("/some/p ath "
		assertEquals("example.com"
		assertEquals("DOMAIN\\user"
		assertEquals("pass"
		assertEquals(33
				u.toPrivateString());
				u.toPrivateASCIIString());
		assertEquals(u.setPass(null).toPrivateString()
		assertEquals(u.setPass(null).toPrivateASCIIString()
		assertEquals(u
	}

	@Test
	public void testGetValidWithEmptySlashDotGitHumanishName()
			throws IllegalArgumentException
		String humanishName = new URIish("/a/b/.git").getHumanishName();
		assertEquals("b"
	}

	@Test
	public void testGetWithSlashDotGitHumanishName() throws URISyntaxException {
		assertEquals(""
	}

	@Test
	public void testGetTwoSlashesDotGitHumanishName() throws URISyntaxException {
		assertEquals(""
	}

	@Test
	public void testGetValidHumanishName() throws IllegalArgumentException
			URISyntaxException {
		String humanishName = new URIish(GIT_SCHEME + "abc").getHumanishName();
		assertEquals("abc"
	}

	@Test
	public void testGetEmptyHumanishNameWithAuthorityOnly() throws IllegalArgumentException
			URISyntaxException {
		String humanishName = new URIish(GIT_SCHEME + "abc").getHumanishName();
		assertEquals("abc"
	}

	@Test
	public void testGetValidSlashHumanishName()
			throws IllegalArgumentException
		String humanishName = new URIish(GIT_SCHEME + "host/abc/")
				.getHumanishName();
		assertEquals("abc"
	}

	@Test
	public void testGetSlashValidSlashHumanishName()
			throws IllegalArgumentException
		String humanishName = new URIish("/abc/").getHumanishName();
		assertEquals("abc"
	}

	@Test
	public void testGetSlashValidSlashDotGitSlashHumanishName()
			throws IllegalArgumentException
		String humanishName = new URIish("/abc/.git").getHumanishName();
		assertEquals("abc"
	}

	@Test
	public void testGetSlashSlashDotGitSlashHumanishName()
			throws IllegalArgumentException
		final String humanishName = new URIish(GIT_SCHEME + "/.git")
				.getHumanishName();
		assertEquals("may return an empty humanish name"
	}

	@Test
	public void testGetSlashesValidSlashHumanishName()
			throws IllegalArgumentException
		String humanishName = new URIish("/a/b/c/").getHumanishName();
		assertEquals("c"
	}

	@Test
	public void testGetValidDotGitHumanishName()
			throws IllegalArgumentException
		String humanishName = new URIish(GIT_SCHEME + "abc.git")
				.getHumanishName();
		assertEquals("abc"
	}

	@Test
	public void testGetValidDotGitSlashHumanishName()
			throws IllegalArgumentException
		String humanishName = new URIish(GIT_SCHEME + "host.xy/abc.git/")
				.getHumanishName();
		assertEquals("abc"
	}

	@Test
	public void testGetValidWithSlashDotGitHumanishName()
			throws IllegalArgumentException
		String humanishName = new URIish("/abc.git").getHumanishName();
		assertEquals("abc"
	}

	@Test
	public void testGetValidWithSlashDotGitSlashHumanishName()
			throws IllegalArgumentException
		String humanishName = new URIish("/abc.git/").getHumanishName();
		assertEquals("abc"
	}

	@Test
	public void testGetValidWithSlashesDotGitHumanishName()
			throws IllegalArgumentException
		String humanishName = new URIish("/a/b/c.git").getHumanishName();
		assertEquals("c"
	}

	@Test
	public void testGetValidWithSlashesDotGitSlashHumanishName()
			throws IllegalArgumentException
		String humanishName = new URIish("/a/b/c.git/").getHumanishName();
		assertEquals("c"
	}

	@Test
	public void testGetValidLocalWithTwoSlashesHumanishName()
			throws IllegalArgumentException
		assertEquals("c"
	}

	@Test
	public void testGetValidGitSchemeWithTwoSlashesHumanishName()
			throws IllegalArgumentException
				.getHumanishName();
		assertEquals("c"
	}

	@Test
	public void testGetWindowsPathHumanishName()
			throws IllegalArgumentException
			URISyntaxException {
		if (File.separatorChar == '\\') {
					.getHumanishName();
			assertEquals("c"
		}
	}

	@Test
	public void testUserPasswordAndPort() throws URISyntaxException {
		URIish u = new URIish(str);
		assertEquals("http"
		assertTrue(u.isRemote());
		assertEquals("/some/path"
		assertEquals("/some/path"
		assertEquals("host.xy"
		assertEquals(80
		assertEquals("user"
		assertEquals("secret"
		assertEquals(u

		u = new URIish(str);
		assertEquals("http"
		assertTrue(u.isRemote());
		assertEquals("/some/path"
		assertEquals("host.xy"
		assertEquals(80
		assertEquals("user"
		assertEquals("secret@pass"
		assertEquals(u
	}

	@Test
	public void testPathSeparator() throws URISyntaxException {
		URIish u = new URIish(str);
		assertEquals("http"
		assertTrue(u.isRemote());
		assertEquals("/some%2Fpath"
		assertEquals("/some/path"
		assertEquals("host.xy"
		assertEquals(80
		assertEquals("user"
		assertEquals("secret"
		assertEquals(u
	}

	@Test
	public void testFileProtocol() throws IllegalArgumentException
			URISyntaxException
		assertEquals("file"
		assertFalse(u.isRemote());
		assertNull(u.getHost());
		assertNull(u.getPass());
		assertEquals("/a/b.txt"
		assertEquals("/a/b.txt"
		assertEquals(-1
		assertNull(u.getUser());
		assertEquals("b.txt"

		File tmp = File.createTempFile("jgitUnitTest"
		u = new URIish(tmp.toURI().toString());
		assertEquals("file"
		assertFalse(u.isRemote());
		assertNull(u.getHost());
		assertNull(u.getPass());
		assertTrue(u.getPath().contains("jgitUnitTest"));
		assertEquals(-1
		assertNull(u.getUser());
		assertTrue(u.getHumanishName().startsWith("jgitUnitTest"));

		u = new URIish("file:/a/b.txt");
		assertEquals("file"
		assertFalse(u.isRemote());
		assertNull(u.getHost());
		assertNull(u.getPass());
		assertEquals("/a/b.txt"
		assertEquals("/a/b.txt"
		assertEquals(-1
		assertNull(u.getUser());
		assertEquals("b.txt"
	}

	@Test
	public void testALot() throws URISyntaxException {
		String[][] tests = {
						"%2$s"
						null
						"%4$s"
						null
		String[] schemes = new String[] { "ssh"
		String[] users = new String[] { "me"
				"lusr\\example" };
		String[] passes = new String[] { "wtf"
		String[] hosts = new String[] { "example.com"
		String[] ports = new String[] { "1234"
		String[] paths = new String[] { "/"
		for (String[] test : tests) {
			String fmt = test[0];
			for (String scheme : schemes) {
				for (String user : users) {
					for (String pass : passes) {
						for (String host : hosts) {
							for (String port : ports) {
								for (String path : paths) {
									String url = String.format(fmt
											user
									String[] expect = new String[test.length];
									for (int i = 1; i < expect.length; ++i)
										if (test[i] != null)
											expect[i] = String.format(test[i]
													scheme
													port
									URIish urIish = new URIish(url);
									assertEquals(url
											urIish.getScheme());
									assertEquals(url
											urIish.getUser());
								}
							}
						}
					}
				}
			}
		}
	}

	@Test
	public void testStringConstructor() throws Exception {
		URIish u = new URIish(str);
		assertEquals("example.com"
		assertEquals("/"
		assertEquals(str

		u = new URIish(str);
		assertEquals("example.com"
		assertEquals(""
		assertEquals(str
	}

	@Test
	public void testEqualsHashcode() throws Exception
	{
				"\\\\some\\place"
				"user@example.com:some/p ath"
		for (String s : urls) {
			URIish u = new URIish(s);
			URIish v = new URIish(s);
			assertTrue(u.equals(v));
			assertTrue(v.equals(u));

			assertFalse(u.equals(null));
			assertFalse(u.equals(new Object()));
			assertFalse(new Object().equals(u));
			assertFalse(u.equals(w));
			assertFalse(w.equals(u));

			assertTrue(u.hashCode() == v.hashCode());
			assertFalse(u.hashCode() == new Object().hashCode());
		}
	}
}
