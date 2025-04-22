		final URL u;
		try {
			final StringBuilder b = new StringBuilder();
			b.append(baseUrl);

			if (b.charAt(b.length() - 1) != '/')
				b.append('/');
			b.append(Constants.INFO_REFS);

			if (useSmartHttp) {
				b.append(service);
			}

			u = new URL(b.toString());
		} catch (MalformedURLException e) {
			throw new NotSupportedException(MessageFormat.format(JGitText.get().invalidURL, uri), e);
		}

