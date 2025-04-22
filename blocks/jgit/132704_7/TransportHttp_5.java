	private void processResponseCookies(
			Map<String
		if (cookieFile != null && http.getSaveCookies()) {
			List<HttpCookie> foundCookies = new LinkedList<>();

			for (Map.Entry<String
					.entrySet()) {
				if (headerField.getValue() == null
						|| headerField.getValue().size() == 0) {
					continue;
				}
				if (headerField.getKey().equalsIgnoreCase(HDR_SET_COOKIE)) {
					foundCookies.addAll(processCookieHeader(HDR_SET_COOKIE
							headerField.getValue()));
				} else if (headerField.getKey()
						.equalsIgnoreCase(HDR_SET_COOKIE2)) {
					foundCookies.addAll(processCookieHeader(HDR_SET_COOKIE2
							headerField.getValue()));
				}
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
		return foundCookies;
	}

