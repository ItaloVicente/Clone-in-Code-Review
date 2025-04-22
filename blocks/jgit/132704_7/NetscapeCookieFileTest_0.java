package org.eclipse.jgit.transport;

import org.junit.Assert;
import org.junit.Test;

public class TransportHttpTest {

	@Test
	public void testCookieDomainMatches() {
		Assert.assertTrue(
				TransportHttp.cookieDomainMatches("example.com"
		Assert.assertTrue(
				TransportHttp.cookieDomainMatches("Example.Com"
		Assert.assertTrue(TransportHttp.cookieDomainMatches("example.com"
				"some.subdomain.example.com"));
		Assert.assertFalse(TransportHttp.cookieDomainMatches("example.com"
				"someotherexample.com"));
		Assert.assertFalse(
				TransportHttp.cookieDomainMatches("example.com"
		Assert.assertFalse(TransportHttp.cookieDomainMatches(".example.com"
				"sub.sub.example.com"));
	}

	@Test
	public void testCookiePathMatches() {
		Assert.assertTrue(
				TransportHttp.cookiePathMatches("/some/path"
		Assert.assertTrue(TransportHttp.cookiePathMatches("/some/path"
				"/some/path/child"));
		Assert.assertTrue(TransportHttp.cookiePathMatches("/some/path/"
				"/some/path/child"));
		Assert.assertFalse(TransportHttp.cookiePathMatches("/some/path"
				"/some/pathother"));
		Assert.assertFalse(
				TransportHttp.cookiePathMatches("/some/path"
	}
}
