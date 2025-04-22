				return;
			} catch (FileNotFoundException noFile) {
				if (retries < maxRetries && configFile.exists()) {
					if (LOG.isDebugEnabled()) {
						LOG.debug(MessageFormat.format(
								JGitText.get().configHandleMayBeLocked,
								Integer.valueOf(retries)), noFile);
					}
					try {
						Thread.sleep(retryDelayMillis);
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
					}
					retries++;
					continue;
				}
				if (configFile.exists()) {
					throw noFile;
				}
