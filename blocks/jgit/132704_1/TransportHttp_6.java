	private static Set<HttpCookie> getCookiesFromConfig(HttpConfig config) {
		if (!StringUtils.isEmptyOrNull(config.getCookieFile())) {
			Path cookieFile = Paths.get(config.getCookieFile());
			if (Files.exists(cookieFile)) {
				try {
					return NetscapeCookieFile.read(cookieFile
				} catch (IllegalArgumentException | IOException e) {
					LOG.warn("Could not read cookie file '{}'"
				}
			}
		}
		return Collections.emptySet();
	}

	private static Set<HttpCookie> filterCookies(Set<HttpCookie> allCookies
			URL url) {
		Set<HttpCookie> filteredCookies = new HashSet<>();
		for (HttpCookie cookie : allCookies) {
			if (cookie.hasExpired()) {
				continue;
			}
			if (!HttpCookie.domainMatches(cookie.getDomain()
				continue;
			}
			if (!cookie.getPath().equals(url.getPath())) {
				String cookiePath = cookie.getPath();
				}
				if (!url.getPath().startsWith(cookiePath)) {
					continue;
				}
			}
				continue;
			}
			filteredCookies.add(cookie);
		}
		return filteredCookies;
	}

