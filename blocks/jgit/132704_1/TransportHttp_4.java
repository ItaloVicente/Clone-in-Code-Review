	private void processResponseCookies(Map<String
		if (http.getCookieFile() != null && http.getSaveCookies()) {
			List<HttpCookie> foundCookies = new LinkedList<>();

			for (Map.Entry<String
				if (headerField.getValue() == null
						|| headerField.getValue().size() == 0) {
					continue;
				}
				if (headerField.getKey().equalsIgnoreCase(HDR_SET_COOKIE)) {
					foundCookies.addAll(processCookieHeader(HDR_SET_COOKIE
				} else if (headerField.getKey().equalsIgnoreCase(HDR_SET_COOKIE2)) {
					foundCookies.addAll(processCookieHeader(HDR_SET_COOKIE2
				}
			}

			if (foundCookies.size() > 0) {
				cookies.addAll(foundCookies);
				relevantCookies.addAll(foundCookies);
				try {
					NetscapeCookieFile.write(Paths.get(http.getCookieFile())
							cookies
				} catch (IOException e) {
					LOG.warn("Could not persist received cookies."
				}
			}
		}
	}

	private List<HttpCookie> processCookieHeader(String headerKey
			List<String> headerValues) {
		List<HttpCookie> foundCookies = new LinkedList<>();
		for (String headerValue : headerValues) {
			foundCookies
		}
		return foundCookies;
	}

