	private URIish redirect(String location
			throws TransportException {
		if (location == null || location.isEmpty()) {
			throw new TransportException(MessageFormat.format(
					JGitText.get().redirectLocationMissing
		}
		LOG.debug(JGitText.get().redirectHttp
		if (redirects >= http.maxRedirects) {
			throw new TransportException(MessageFormat.format(
					JGitText.get().redirectLimitExceeded
					Integer.valueOf(http.maxRedirects)
		}
		try {
			if (!isValidRedirect(baseUrl
				throw new TransportException(
						MessageFormat.format(JGitText.get().redirectBlocked
								uri
			}
			location = location.substring(0
			return new URIish(location);
		} catch (URISyntaxException e) {
			throw new TransportException(MessageFormat.format(
					JGitText.get().invalidRedirectLocation
					uri
		}
	}

	private boolean isValidRedirect(URL current
		String oldProtocol = current.getProtocol().toLowerCase(Locale.ROOT);
		if (schemeEnd < 0) {
			return false;
		}
		String newProtocol = next.substring(0
				.toLowerCase(Locale.ROOT);
		if (!oldProtocol.equals(newProtocol)) {
				return false;
			}
		}
		if (next.indexOf(checkFor) < 0) {
			return false;
		}
		return true;
	}

	private URL getServiceURL(final String service)
			throws NotSupportedException {
		try {
			final StringBuilder b = new StringBuilder();
			b.append(baseUrl);

			if (b.charAt(b.length() - 1) != '/')
				b.append('/');
			b.append(Constants.INFO_REFS);

			if (useSmartHttp) {
				b.append(service);
			}

			return new URL(b.toString());
		} catch (MalformedURLException e) {
			throw new NotSupportedException(MessageFormat.format(JGitText.get().invalidURL
		}
	}

