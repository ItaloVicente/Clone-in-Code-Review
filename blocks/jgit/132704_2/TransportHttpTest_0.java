package org.eclipse.jgit.transport;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpCookie;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
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

	@Before
	public void setUp() throws IOException {
		tmpFile = folder.newFile().toPath();
	}

	@Test
	public void testWrite() throws IOException {
		List<HttpCookie> cookies = new LinkedList<>();
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
				CoreMatchers.equalTo(Arrays.asList(
						"domain.com\tTRUE\t/my/path\tFALSE\t0\tkey1\tvalue"
						"mydomain.com\tTRUE\t/\tTRUE\t" + expectedExpiration
								+ "\tkey2\tvalue")));
	}

	@Test
	public void testWriteToExistingFile() throws IOException {
		try (InputStream input = this.getClass()
				.getResourceAsStream("cookies-simple.txt")) {
			Files.copy(input
		}

		List<HttpCookie> cookies = new LinkedList<>();
		cookies.add(new HttpCookie("key2"
		Date creationDate = new Date();
		NetscapeCookieFile.write(tmpFile

		Assert.assertThat(
				Files.readAllLines(tmpFile
				CoreMatchers.equalTo(Arrays.asList(
						"domain.com\tTRUE\t/my/path\tFALSE\t0\tkey2\tvalue2")));
	}

	@Test
	public void testWriteAndReadCycle() throws IOException {
		List<HttpCookie> cookies = new LinkedList<>();

		HttpCookie cookie = new HttpCookie("key1"
		cookie.setPath("/some/path1");
		cookie.setDomain("some-domain1");
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
				new HttpCookiesMatcher(cookies));
	}

	@Test
	public void testReadWithEmptyAndCommentLines() throws IOException {
		try (InputStream input = this.getClass().getResourceAsStream(
				"cookies-with-empty-and-comment-lines.txt")) {
			Files.copy(input
		}

		Date creationDate = new Date();
		List<HttpCookie> cookies = new LinkedList<>();
		HttpCookie cookie = new HttpCookie("key1"
		cookie.setDomain("some-domain1");
		cookie.setPath("/some/path1");
		cookie.setMaxAge(-1);
		cookies.add(cookie);

		cookie = new HttpCookie("key2"
		cookie.setDomain("some-domain2");
		cookie.setPath("/some/path2");
		cookie.setMaxAge((1893456000000L - creationDate.getTime()) / 1000);
		cookie.setSecure(true);
		cookie.setHttpOnly(true);
		cookies.add(cookie);

		Assert.assertThat(NetscapeCookieFile.read(tmpFile
				new HttpCookiesMatcher(cookies));
	}

	@Test(expected=IllegalArgumentException.class)
	public void testReadInvalidFile() throws IOException {
		try (InputStream input = this.getClass().getResourceAsStream(
				"cookies-invalid.txt")) {
			Files.copy(input
		}

		Date creationDate = new Date();
		NetscapeCookieFile.read(tmpFile
	}

	private final static class HttpCookiesMatcher
			extends TypeSafeMatcher<Collection<HttpCookie>> {

		private final List<HttpCookieMatcher> cookieMatchers;

		public HttpCookiesMatcher(Collection<HttpCookie> cookies) {
			cookieMatchers = new ArrayList<>(cookies.size());
			for (HttpCookie cookie : cookies) {
				cookieMatchers.add(new HttpCookieMatcher(cookie));
			}
		}

		@Override
		public void describeTo(Description description) {
			description.appendText("a List of the following cookies ");
			for (HttpCookieMatcher cookieMatcher : cookieMatchers) {
				description.appendText("\n");
				cookieMatcher.describeTo(description);
			}
		}

		@Override
		protected boolean matchesSafely(Collection<HttpCookie> cookies) {
			if (cookies.size() != cookieMatchers.size()) {
				return false;
			}
			int index = 0;
			for (HttpCookie cookie : cookies) {
				if (!cookieMatchers.get(index++).matchesSafely(cookie)) {
					return false;
				}
			}
			return true;
		}

		@Override
		protected void describeMismatchSafely(Collection<HttpCookie> cookies
				Description mismatchDescription) {
			mismatchDescription
					.appendValue("a List of the following cookies: ");
			for (HttpCookie cookie : cookies) {
				mismatchDescription.appendText("\n");
				HttpCookieMatcher.describeCookie(mismatchDescription
			}
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
			description.appendText(
					"HttpCookie[");
			description.appendText("name: ").appendValue(cookie.getName()).appendText("
			description.appendText("value: ").appendValue(cookie.getValue()).appendText("
			description.appendText("domain: ").appendValue(cookie.getDomain()).appendText("
			description.appendText("path: ").appendValue(cookie.getPath()).appendText("
			description.appendText("maxAge: ").appendValue(cookie.getMaxAge()).appendText("
			description.appendText("httpOnly: ").appendValue(cookie.isHttpOnly()).appendText("
			description.appendText("secure: ").appendValue(cookie.getSecure()).appendText("
			description.appendText("version: ").appendValue(cookie.getVersion()).appendText("
			description.appendText("]");
		}
	}
}
