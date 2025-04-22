package org.eclipse.jgit.transport;

import java.io.File;
import java.io.IOException;
import java.net.HttpCookie;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.jgit.internal.transport.http.NetscapeCookieFile;
import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.test.resources.SampleDataRepositoryTestCase;
import org.eclipse.jgit.transport.http.HttpConnection;
import org.eclipse.jgit.util.http.HttpCookiesMatcher;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

public class TransportHttpTest extends SampleDataRepositoryTestCase {
	private URIish uri;
	private File cookieFile;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();

		final Config config = db.getConfig();
		config.setBoolean("http"
		cookieFile = createTempFile();
		config.setString("http"
				cookieFile.getAbsolutePath());
	}

	@Test
	public void testMatchesCookieDomain() {
		Assert.assertTrue(TransportHttp.matchesCookieDomain("example.com"
				"example.com"));
		Assert.assertTrue(TransportHttp.matchesCookieDomain("Example.Com"
				"example.cOM"));
		Assert.assertTrue(TransportHttp.matchesCookieDomain(
				"some.subdomain.example.com"
		Assert.assertFalse(TransportHttp
				.matchesCookieDomain("someotherexample.com"
		Assert.assertFalse(TransportHttp.matchesCookieDomain("example.com"
				"example1.com"));
		Assert.assertFalse(TransportHttp
				.matchesCookieDomain("sub.sub.example.com"
		Assert.assertTrue(TransportHttp.matchesCookieDomain("host.example.com"
				"example.com"));
		Assert.assertTrue(TransportHttp.matchesCookieDomain(
				"something.example.com"
		Assert.assertTrue(TransportHttp.matchesCookieDomain(
				"host.something.example.com"
	}

	@Test
	public void testMatchesCookiePath() {
		Assert.assertTrue(
				TransportHttp.matchesCookiePath("/some/path"
		Assert.assertTrue(TransportHttp.matchesCookiePath("/some/path/child"
				"/some/path"));
		Assert.assertTrue(TransportHttp.matchesCookiePath("/some/path/child"
				"/some/path/"));
		Assert.assertFalse(TransportHttp.matchesCookiePath("/some/pathother"
				"/some/path"));
		Assert.assertFalse(
				TransportHttp.matchesCookiePath("otherpath"
	}

	@Test
	public void testProcessResponseCookies() throws IOException {
		HttpConnection connection = Mockito.mock(HttpConnection.class);
		Mockito.when(
				connection.getHeaderFields(ArgumentMatchers.eq("Set-Cookie")))
				.thenReturn(Arrays.asList(
						"id=a3fWa; Expires=Fri
						"sessionid=38afes7a8; HttpOnly; Path=/"));
		Mockito.when(
				connection.getHeaderFields(ArgumentMatchers.eq("Set-Cookie2")))
				.thenReturn(Collections
						.singletonList("cookie2=some value; Max-Age=1234; Path=/"));

		try (TransportHttp transportHttp = new TransportHttp(db
			Date creationDate = new Date();
			transportHttp.processResponseCookies(connection);

			Set<HttpCookie> expectedCookies = new LinkedHashSet<>();

			HttpCookie cookie = new HttpCookie("id"
			cookie.setDomain("everyones.loves.git");
			cookie.setPath("/u/2/");

			cookie.setMaxAge(
					(Instant.parse("2100-01-01T11:00:00.000Z").toEpochMilli()
							- creationDate.getTime()) / 1000);
			cookie.setSecure(true);
			cookie.setHttpOnly(true);
			expectedCookies.add(cookie);

			cookie = new HttpCookie("cookie2"
			cookie.setDomain("everyones.loves.git");
			cookie.setPath("/");
			cookie.setMaxAge(1234);
			expectedCookies.add(cookie);

			Assert.assertThat(
					new NetscapeCookieFile(cookieFile.toPath())
							.getCookies(true)
					HttpCookiesMatcher.containsInOrder(expectedCookies
		}
	}

	@Test
	public void testProcessResponseCookiesNotPersistingWithSaveCookiesFalse()
			throws IOException {
		HttpConnection connection = Mockito.mock(HttpConnection.class);
		Mockito.when(
				connection.getHeaderFields(ArgumentMatchers.eq("Set-Cookie")))
				.thenReturn(Arrays.asList(
						"id=a3fWa; Expires=Thu
						"sessionid=38afes7a8; HttpOnly; Path=/"));
		Mockito.when(
				connection.getHeaderFields(ArgumentMatchers.eq("Set-Cookie2")))
				.thenReturn(Collections.singletonList(
						"cookie2=some value; Max-Age=1234; Path=/"));

		final Config config = db.getConfig();
		config.setBoolean("http"

		try (TransportHttp transportHttp = new TransportHttp(db
			transportHttp.processResponseCookies(connection);

			Assert.assertFalse("Cookie file was not supposed to be written!"
					cookieFile.exists());
		}
	}
}
