				if (retries < maxRetries && configFile.exists()) {
					if (LOG.isDebugEnabled()) {
						LOG.debug(MessageFormat.format(
								JGitText.get().configHandleMayBeLocked
								Integer.valueOf(retries))
					}
					try {
						Thread.sleep(retryDelayMillis);
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
					}
					retries++;
					continue;
				}
