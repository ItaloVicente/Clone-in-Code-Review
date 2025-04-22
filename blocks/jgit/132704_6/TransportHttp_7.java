	private static NetscapeCookieFile getCookieFileFromConfig(
			HttpConfig config) {
		if (!StringUtils.isEmptyOrNull(config.getCookieFile())) {
			try {
				Path cookieFile = Paths.get(config.getCookieFile());
				return NetscapeCookieFileCache.getEntry(cookieFile);
			} catch (InvalidPathException e) {
				LOG.warn(MessageFormat.format(
						JGitText.get().couldNotReadCookieFile
						config.getCookieFile()
			}
		}
		return null;
	}

	private static Set<HttpCookie> filterCookies(NetscapeCookieFile cookieFile
			URL url) {
		if (cookieFile != null) {
			return filterCookies(cookieFile.getCookies(true)
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
		cookieDomain = cookieDomain.toLowerCase(Locale.ROOT);
		host = host.toLowerCase(Locale.ROOT);
		if (host.equals(cookieDomain)) {
			return true;
		} else {
			if (!host.endsWith(cookieDomain)) {
				return false;
			}
			return host
					.charAt(host.length() - cookieDomain.length() - 1) == '.';
		}
	}

	static boolean cookiePathMatches(String cookiePath
		if (cookiePath.equals(path)) {
			return true;
		}

		}
		return path.startsWith(cookiePath);
	}

