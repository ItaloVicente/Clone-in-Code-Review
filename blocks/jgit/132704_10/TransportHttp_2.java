	private void processResponseCookies(HttpConnection conn) {
		if (cookieFile != null && http.getSaveCookies()) {
			List<HttpCookie> foundCookies = new LinkedList<>();

			List<String> cookieHeaderValues = conn
					.getHeaderFields(HDR_SET_COOKIE);
			if (!cookieHeaderValues.isEmpty()) {
				processCookieHeader(HDR_SET_COOKIE
			}
			cookieHeaderValues = conn.getHeaderFields(HDR_SET_COOKIE2);
			if (!cookieHeaderValues.isEmpty()) {
				processCookieHeader(HDR_SET_COOKIE2
			}
			if (foundCookies.size() > 0) {
				cookieFile.getCookies(false).addAll(foundCookies);
				relevantCookies.addAll(foundCookies);
				try {
					cookieFile.write(baseUrl);
				} catch (IOException e) {
					LOG.warn(JGitText.get().couldNotPersistCookies
				}
			}
		}
	}

	private List<HttpCookie> processCookieHeader(String headerKey
			List<String> headerValues) {
		List<HttpCookie> foundCookies = new LinkedList<>();
		for (String headerValue : headerValues) {
			foundCookies
					.addAll(HttpCookie.parse(headerKey + ':' + headerValue));
		}
		for (HttpCookie foundCookie : foundCookies) {
			String domain = foundCookie.getDomain();
				foundCookie.setDomain(domain.substring(1));
			}
		}
		return foundCookies;
	}

