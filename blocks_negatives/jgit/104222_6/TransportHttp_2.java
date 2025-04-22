					currentUri = redirect(
							conn.getHeaderField(HDR_LOCATION),
							'/' + serviceName, redirects++);
					try {
						baseUrl = toURL(currentUri);
					} catch (MalformedURLException e) {
						throw new TransportException(uri, MessageFormat.format(
								JGitText.get().invalidRedirectLocation,
								baseUrl, currentUri), e);
