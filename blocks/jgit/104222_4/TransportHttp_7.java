						authMethod = nextMethod;
						authenticator = nextMethod;
						CredentialsProvider credentialsProvider = getCredentialsProvider();
						if (credentialsProvider == null) {
							throw new TransportException(uri
									JGitText.get().noCredentialsProvider);
						}
						if (authAttempts > 1) {
							credentialsProvider.reset(currentUri);
						}
						if (3 < authAttempts || !authMethod
								.authorize(currentUri
							throw new TransportException(uri
									JGitText.get().notAuthorized);
						}
						authAttempts++;
						continue;
