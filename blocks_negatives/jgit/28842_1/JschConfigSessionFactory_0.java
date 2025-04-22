					if (credentialsProvider != null && e.getCause() == null
							&& retries < 3) {
						credentialsProvider.reset(uri);
						session = createSession(credentialsProvider, fs, user,
								pass, host, port, hc);
					} else {
