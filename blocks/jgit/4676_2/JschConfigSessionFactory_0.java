			int retries = 0;
			while (!session.isConnected() && retries < 3) {
				try {
					retries++;
					session.connect(tms);
				} catch (JSchException e) {
					session.disconnect();
					session = null;
					if (credentialsProvider != null && e.getCause() == null
							&& e.getMessage().equals("Auth fail")) {
						credentialsProvider.reset(uri);
						session = createSession(credentialsProvider
								pass
					} else {
						throw e;
					}
				}
			}
