					if (isAuthenticationCanceled(e)) {
						throw e;
					} else if (isAuthenticationFailed(e)
							&& credentialsProvider != null) {
						if (retries < 3) {
							credentialsProvider.reset(uri);
							session = createSession(credentialsProvider
									user
						} else
							throw e;
					} else if (retries >= hc.getConnectionAttempts()) {
