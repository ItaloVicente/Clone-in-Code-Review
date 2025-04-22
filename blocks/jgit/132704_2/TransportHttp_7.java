	private static Set<HttpCookie> getCookiesFromConfig(HttpConfig config) {
		if (!StringUtils.isEmptyOrNull(config.getCookieFile())) {
			try {
				Path cookieFile = Paths.get(config.getCookieFile());
				return NetscapeCookieFile.read(cookieFile
			} catch (IOException | IllegalArgumentException e) {
				LOG.warn(MessageFormat.format(
						JGitText.get().couldNotReadCookieFile
						config.getCookieFile()
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
			if (!cookieDomainMatches(cookie.getDomain()
				continue;
			}
			if (!cookiePathMatches(cookie.getPath()
				continue;
			}
				continue;
			}
			filteredCookies.add(cookie);
		}
		return filteredCookies;
	}

	static boolean cookieDomainMatches(String cookieDomain
		cookieDomain = cookieDomain.toLowerCase();
		host = host.toLowerCase();
		if (host.equals(cookieDomain)) {
			return true;
		} else {
			if (!host.endsWith(cookieDomain)) {
				return false;
			}
			String prefix = host.substring(0
					host.length() - cookieDomain.length());
		}
	}

	static boolean cookiePathMatches(String cookiePath
		if (cookiePath.equals(path)) {
			return true;
		}

		}
		return path.startsWith(cookiePath);
	}

