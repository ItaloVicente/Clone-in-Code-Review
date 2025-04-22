	private static NetscapeCookieFile getCookieFileFromConfig(
			HttpConfig config) {
		if (!StringUtils.isEmptyOrNull(config.getCookieFile())) {
			try {
				Path cookieFilePath = Paths.get(config.getCookieFile());
				return NetscapeCookieFileCache.getInstance(config)
						.getEntry(cookieFilePath);
			} catch (InvalidPathException e) {
				LOG.warn(MessageFormat.format(
						JGitText.get().couldNotReadCookieFile
						config.getCookieFile())
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
			if (!matchesCookieDomain(url.getHost()
				continue;
			}
			if (!matchesCookiePath(url.getPath()
				continue;
			}
				continue;
			}
			filteredCookies.add(cookie);
		}
		return filteredCookies;
	}

	static boolean matchesCookieDomain(String host
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

	static boolean matchesCookiePath(String path
		if (cookiePath.equals(path)) {
			return true;
		}
		}
		return path.startsWith(cookiePath);
	}

