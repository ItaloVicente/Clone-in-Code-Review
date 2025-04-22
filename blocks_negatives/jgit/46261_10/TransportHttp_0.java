		try {
			String uriString = uri.toString();
			baseUrl = new URL(uriString);
			objectsUrl = new URL(baseUrl, "objects/"); //$NON-NLS-1$
		} catch (MalformedURLException e) {
			throw new NotSupportedException(MessageFormat.format(JGitText.get().invalidURL, uri), e);
		}
