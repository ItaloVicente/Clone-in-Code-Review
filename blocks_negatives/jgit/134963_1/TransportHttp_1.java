					if (cookies != null) {
						cookies.addAll(foundCookies);
						cookieFile.write(baseUrl);
					} else {
						throw new IOException(MessageFormat.format(
								JGitText.get().couldNotReadCookieFile,
								cookieFile.getFile()));
					}
