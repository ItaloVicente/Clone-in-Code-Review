package org.eclipse.jgit.transport.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpCookie;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.eclipse.jgit.internal.storage.file.LockFile;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.collection.IsIterableContainingInOrder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class NetscapeCookieFileTest {

	@Rule
	public TemporaryFolder folder = new TemporaryFolder();

	private Path tmpFile;

	private URL baseUrl;

	private static final long FIRST_OF_JANUARY_2030_AT_NOON_IN_MS_SINCE_EPOCH = 1893456000000L;

	@Before
	public void setUp() throws IOException {
		tmpFile = folder.newFile().toPath();
	}

	@Test
	public void testMergeCookies() {
		Set<HttpCookie> cookieSet1 = new LinkedHashSet<>();
		HttpCookie cookie = new HttpCookie("key1"
		cookieSet1.add(cookie);
		cookie = new HttpCookie("key2"
		cookieSet1.add(cookie);

		Set<HttpCookie> cookieSet2 = new LinkedHashSet<>();
		cookie = new HttpCookie("key1"
		cookieSet2.add(cookie);
		cookie = new HttpCookie("key3"
		cookieSet2.add(cookie);

		Set<HttpCookie> cookiesExpectedMergedSet = new LinkedHashSet<>();
		cookie = new HttpCookie("key1"
		cookiesExpectedMergedSet.add(cookie);
		cookie = new HttpCookie("key2"
		cookiesExpectedMergedSet.add(cookie);
		cookie = new HttpCookie("key3"
		cookiesExpectedMergedSet.add(cookie);

		Assert.assertThat(
				NetscapeCookieFile.mergeCookies(cookieSet1
				HttpCookiesMatcher.containsInOrder(cookiesExpectedMergedSet));

		Assert.assertThat(NetscapeCookieFile.mergeCookies(cookieSet1
				HttpCookiesMatcher.containsInOrder(cookieSet1));
	}

	@Test
	public void testWriteToNewFile() throws IOException {
		Set<HttpCookie> cookies = new LinkedHashSet<>();
		cookies.add(new HttpCookie("key1"

		HttpCookie cookie = new HttpCookie("key2"
		cookie.setSecure(true);
		cookie.setDomain("mydomain.com");
		cookie.setPath("/");
		cookie.setMaxAge(1000);
		cookies.add(cookie);
		Date creationDate = new Date();
		NetscapeCookieFile.write(tmpFile

		String expectedExpiration = String
				.valueOf(creationDate.getTime() + (cookie.getMaxAge() * 1000));

		Assert.assertThat(
				Files.readAllLines(tmpFile
				CoreMatchers
						.equalTo(Arrays.asList("mydomain.com\tTRUE\t/\tTRUE\t"
								+ expectedExpiration + "\tkey2\tvalue")));
	}

	@Test
	public void testWriteToExistingFile() throws IOException {
		try (InputStream input = this.getClass()
				.getResourceAsStream("cookies-simple1.txt")) {
			Files.copy(input
		}

		Set<HttpCookie> cookies = new LinkedHashSet<>();
		HttpCookie cookie = new HttpCookie("key2"
		cookie.setMaxAge(1000);
		cookies.add(cookie);
		Date creationDate = new Date();
		NetscapeCookieFile.write(tmpFile

		String expectedExpiration = String
				.valueOf(creationDate.getTime() + (cookie.getMaxAge() * 1000));

		Assert.assertThat(
				Files.readAllLines(tmpFile
				CoreMatchers.equalTo(
						Arrays.asList("domain.com\tTRUE\t/my/path\tFALSE\t"
								+ expectedExpiration + "\tkey2\tvalue2")));
	}

	@Test
	public void testReadWhileSomeoneIsHoldingTheLock()
			throws IllegalArgumentException
		try (InputStream input = this.getClass()
				.getResourceAsStream("cookies-simple1.txt")) {
			Files.copy(input
		}
		NetscapeCookieFile cookieFile = new NetscapeCookieFile(tmpFile);
		LockFile lockFile = new LockFile(tmpFile.toFile());
		try {
			Assert.assertTrue("Could not acquire lock"
			Set<HttpCookie> cookies = cookieFile.getCookies(true);
			Assert.assertNull(cookies);
		} finally {
			lockFile.unlock();
		}
	}

	@Test(expected = IOException.class)
	public void testWriteWhileSomeoneIsHoldingTheLock()
			throws IllegalArgumentException
		try (InputStream input = this.getClass()
				.getResourceAsStream("cookies-simple1.txt")) {
			Files.copy(input
		}
		NetscapeCookieFile cookieFile = new NetscapeCookieFile(tmpFile);
		LockFile lockFile = new LockFile(tmpFile.toFile());
		try {
			Assert.assertTrue("Could not acquire lock"
			cookieFile.write(baseUrl);
		} finally {
			lockFile.unlock();
		}
	}

	@Test
	public void testWriteAfterAnotherJgitProcessModifiedTheFile()
			throws IOException
		try (InputStream input = this.getClass()
				.getResourceAsStream("cookies-simple1.txt")) {
			Files.copy(input
		}
		NetscapeCookieFile cookieFile = new NetscapeCookieFile(tmpFile);
		cookieFile.getCookies(true);
		try (InputStream input = this.getClass()
				.getResourceAsStream("cookies-simple2.txt")) {
			Files.copy(input
		}
		cookieFile.write(baseUrl);

		List<String> lines = Files.readAllLines(tmpFile
				StandardCharsets.US_ASCII);

		Assert.assertEquals("Expected 3 lines"
		assertStringMatchesPatternWithInexactNumber(lines.get(0)
				"some-domain1\tTRUE\t/some/path1\tFALSE\t(\\d*)\tkey1\tvalueFromSimple2"
				FIRST_OF_JANUARY_2030_AT_NOON_IN_MS_SINCE_EPOCH
		assertStringMatchesPatternWithInexactNumber(lines.get(1)
				"some-domain1\tTRUE\t/some/path1\tFALSE\t(\\d*)\tkey3\tvalueFromSimple2"
				FIRST_OF_JANUARY_2030_AT_NOON_IN_MS_SINCE_EPOCH
		assertStringMatchesPatternWithInexactNumber(lines.get(2)
				"some-domain1\tTRUE\t/some/path1\tFALSE\t(\\d*)\tkey2\tvalueFromSimple1"
				FIRST_OF_JANUARY_2030_AT_NOON_IN_MS_SINCE_EPOCH
	}

	@SuppressWarnings("boxing")
	private static final void assertStringMatchesPatternWithInexactNumber(
			String string
			long delta) {
		java.util.regex.Matcher matcher = Pattern.compile(pattern)
				.matcher(string);
		Assert.assertTrue("Given string '" + string + "' does not match '"
				+ pattern + "'"
		Long actualNumericValue = Long.decode(matcher.group(1));

		Assert.assertTrue(
				"Value is supposed to be close to " + expectedNumericValue
						+ " but is " + actualNumericValue + "."
				Math.abs(expectedNumericValue - actualNumericValue) <= delta);
	}

	@Test
	public void testWriteAndReadCycle() throws IOException {
		Set<HttpCookie> cookies = new LinkedHashSet<>();

		HttpCookie cookie = new HttpCookie("key1"
		cookie.setPath("/some/path1");
		cookie.setDomain("some-domain1");
		cookie.setMaxAge(1000);
		cookies.add(cookie);
		cookie = new HttpCookie("key2"
		cookie.setSecure(true);
		cookie.setPath("/some/path2");
		cookie.setDomain("some-domain2");
		cookie.setMaxAge(1000);
		cookie.setHttpOnly(true);
		cookies.add(cookie);

		Date creationDate = new Date();

		NetscapeCookieFile.write(tmpFile
		Assert.assertThat(NetscapeCookieFile.read(tmpFile
				HttpCookiesMatcher.containsInOrder(cookies));
	}

	@Test
	public void testReadAndWriteCycle() throws IOException {
		try (InputStream input = this.getClass()
				.getResourceAsStream("cookies-simple1.txt")) {
			Files.copy(input
		}
		Date creationDate = new Date(
				(System.currentTimeMillis() / 1000) * 1000);
		Set<HttpCookie> cookies = NetscapeCookieFile.read(tmpFile
				creationDate);
		Path tmpFile2 = folder.newFile().toPath();
		NetscapeCookieFile.write(tmpFile2
		Assert.assertEquals(Files.readAllLines(tmpFile)
				Files.readAllLines(tmpFile2));
	}

	@Test
	public void testReadWithEmptyAndCommentLines() throws IOException {
		try (InputStream input = this.getClass().getResourceAsStream(
				"cookies-with-empty-and-comment-lines.txt")) {
			Files.copy(input
		}

		Date creationDate = new Date();
		Set<HttpCookie> cookies = new LinkedHashSet<>();

		HttpCookie cookie = new HttpCookie("key2"
		cookie.setDomain("some-domain2");
		cookie.setPath("/some/path2");
		cookie.setMaxAge((FIRST_OF_JANUARY_2030_AT_NOON_IN_MS_SINCE_EPOCH
				- creationDate.getTime()) / 1000);
		cookie.setSecure(true);
		cookie.setHttpOnly(true);
		cookies.add(cookie);

		cookie = new HttpCookie("key3"
		cookie.setDomain("some-domain3");
		cookie.setPath("/some/path3");
		cookie.setMaxAge((FIRST_OF_JANUARY_2030_AT_NOON_IN_MS_SINCE_EPOCH
				- creationDate.getTime()) / 1000);
		cookies.add(cookie);

		Assert.assertThat(NetscapeCookieFile.read(tmpFile
				HttpCookiesMatcher.containsInOrder(cookies));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testReadInvalidFile() throws IOException {
		try (InputStream input = this.getClass()
				.getResourceAsStream("cookies-invalid.txt")) {
			Files.copy(input
		}

		Date creationDate = new Date();
		NetscapeCookieFile.read(tmpFile
	}

	private final static class HttpCookiesMatcher {
		public static Matcher<Iterable<? extends HttpCookie>> containsInOrder(
				Iterable<HttpCookie> expectedCookies) {
			final List<Matcher<? super HttpCookie>> cookieMatchers = new LinkedList<>();
			for (HttpCookie cookie : expectedCookies) {
				cookieMatchers.add(new HttpCookieMatcher(cookie));
			}
			return new IsIterableContainingInOrder<>(cookieMatchers);
		}
	}

	private final static class HttpCookieMatcher
			extends TypeSafeMatcher<HttpCookie> {

		private final HttpCookie cookie;

		public HttpCookieMatcher(HttpCookie cookie) {
			this.cookie = cookie;
		}

		@Override
		public void describeTo(Description description) {
			describeCookie(description
		}

		@Override
		protected boolean matchesSafely(HttpCookie otherCookie) {
			return (equals(cookie.getName()
					&& equals(cookie.getValue()
					&& equals(cookie.getDomain()
					&& equals(cookie.getPath()
					&& cookie.getMaxAge() == otherCookie.getMaxAge()
					&& cookie.isHttpOnly() == otherCookie.isHttpOnly()
					&& cookie.getSecure() == otherCookie.getSecure()
					&& cookie.getVersion() == otherCookie.getVersion());
		}

		private static boolean equals(String value1
			if (value1 == null && value2 == null) {
				return true;
			}
			if (value1 == null || value2 == null) {
				return false;
			}
			return value1.equals(value2);
		}

		@SuppressWarnings("boxing")
		protected static void describeCookie(Description description
				HttpCookie cookie) {
			description.appendText("HttpCookie[");
			description.appendText("name: ").appendValue(cookie.getName())
					.appendText("
			description.appendText("value: ").appendValue(cookie.getValue())
					.appendText("
			description.appendText("domain: ").appendValue(cookie.getDomain())
					.appendText("
			description.appendText("path: ").appendValue(cookie.getPath())
					.appendText("
			description.appendText("maxAge: ").appendValue(cookie.getMaxAge())
					.appendText("
			description.appendText("httpOnly: ")
					.appendValue(cookie.isHttpOnly()).appendText("
			description.appendText("secure: ").appendValue(cookie.getSecure())
					.appendText("
			description.appendText("version: ").appendValue(cookie.getVersion())
					.appendText("
			description.appendText("]");
		}
	}
}
