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
				try {
					Set<HttpCookie> cookies = cookieFile.getCookies(false);
					if (cookies != null) {
						cookies.addAll(foundCookies);
						cookieFile.write(baseUrl);
					} else {
						throw new IOException(MessageFormat.format(
								JGitText.get().couldNotReadCookieFile
								cookieFile.getFile()));
					}
					relevantCookies.addAll(foundCookies);
				} catch (IOException | IllegalArgumentException
						| InterruptedException e) {
					LOG.warn(MessageFormat.format(
							JGitText.get().couldNotPersistCookies
							cookieFile.getFile())
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

