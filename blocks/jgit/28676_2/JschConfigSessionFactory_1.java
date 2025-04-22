						if (retries >= hc.getConnectionAttempts())
							throw e;
						try {
							Thread.sleep(1000);
							session = createSession(credentialsProvider
									user
						} catch (InterruptedException e1) {
							throw new TransportException(
									"Interrupt during wait for retry"
						}
