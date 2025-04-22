						if (retries < max_retries) {
							Thread.sleep(100);
						}
						LOG.warn(MessageFormat.format(
								JGitText.get().lockFailedRetry
								Integer.valueOf(retries)));
