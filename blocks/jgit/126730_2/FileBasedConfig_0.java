				if (retries < maxRetries && configFile.exists()) {
					if (LOG.isDebugEnabled()) {
						LOG.debug(MessageFormat.format(
								JGitText.get().configHandleMayBeLocked
								Integer.valueOf(retries))
					}
					retries++;
					continue;
				}
