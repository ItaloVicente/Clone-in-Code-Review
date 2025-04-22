package org.eclipse.jgit.util.http;

import java.net.HttpCookie;
import java.util.LinkedList;
import java.util.List;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.collection.IsIterableContainingInOrder;

public final class HttpCookiesMatcher {
	public static Matcher<Iterable<? extends HttpCookie>> containsInOrder(
			Iterable<HttpCookie> expectedCookies) {
		return containsInOrder(expectedCookies
	}

	public static Matcher<Iterable<? extends HttpCookie>> containsInOrder(
			Iterable<HttpCookie> expectedCookies
		final List<Matcher<? super HttpCookie>> cookieMatchers = new LinkedList<>();
		for (HttpCookie cookie : expectedCookies) {
			cookieMatchers
					.add(new HttpCookieMatcher(cookie
		}
		return new IsIterableContainingInOrder<>(cookieMatchers);
	}

	private static final class HttpCookieMatcher
			extends TypeSafeMatcher<HttpCookie> {

		private final HttpCookie cookie;

		private final int allowedMaxAgeDelta;

		public HttpCookieMatcher(HttpCookie cookie
			this.cookie = cookie;
			this.allowedMaxAgeDelta = allowedMaxAgeDelta;
		}

		@Override
		public void describeTo(Description description) {
			describeCookie(description
		}

		@Override
		protected void describeMismatchSafely(HttpCookie item
				Description mismatchDescription) {
			mismatchDescription.appendText("was ");
			describeCookie(mismatchDescription
		}

		@Override
		protected boolean matchesSafely(HttpCookie otherCookie) {
			return (equals(cookie.getName()
					&& equals(cookie.getValue()
					&& equals(cookie.getDomain()
					&& equals(cookie.getPath()
					&& (cookie.getMaxAge() >= otherCookie.getMaxAge()
							- allowedMaxAgeDelta)
					&& (cookie.getMaxAge() <= otherCookie.getMaxAge()
							+ allowedMaxAgeDelta)
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
