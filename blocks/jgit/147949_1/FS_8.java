						if (retries < max_retries) {
							Thread.sleep(100);
							LOG.debug("locking {} failed
									userConfig
									Integer.valueOf(max_retries));
						} else {
							LOG.warn(MessageFormat.format(
									JGitText.get().lockFailedRetry
									Integer.valueOf(retries)));
						}
