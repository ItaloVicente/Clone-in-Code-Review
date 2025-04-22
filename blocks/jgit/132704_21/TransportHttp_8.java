	void processResponseCookies(HttpConnection conn) {
		if (cookieFile != null && http.getSaveCookies()) {
			List<HttpCookie> foundCookies = new LinkedList<>();

			List<String> cookieHeaderValues = conn
					.getHeaderFields(HDR_SET_COOKIE);
			if (!cookieHeaderValues.isEmpty()) {
				foundCookies.addAll(
						extractCookies(HDR_SET_COOKIE
			}
			cookieHeaderValues = conn.getHeaderFields(HDR_SET_COOKIE2);
			if (!cookieHeaderValues.isEmpty()) {
				foundCookies.addAll(
						extractCookies(HDR_SET_COOKIE2
			}
			if (foundCookies.size() > 0) {
				try {
					Set<HttpCookie> cookies = cookieFile.getCookies(false);
					cookies.addAll(foundCookies);
					cookieFile.write(baseUrl);
					relevantCookies.addAll(foundCookies);
				} catch (IOException | IllegalArgumentException
						| InterruptedException e) {
					LOG.warn(MessageFormat.format(
							JGitText.get().couldNotPersistCookies
							cookieFile.getPath())
				}
			}
		}
	}

	private List<HttpCookie> extractCookies(String headerKey
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

