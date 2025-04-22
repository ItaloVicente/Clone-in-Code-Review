						return;
					} catch (IOException e) {
						if (FileUtils.isStaleFileHandle(e) && retries >= 0) {
							if (LOG.isDebugEnabled()) {
								LOG.debug(MessageFormat.format(
										JGitText.get().configHandleIsStale
										Integer.valueOf(retries))
							}
							retries--;
							continue;
						}
						throw new IOException(MessageFormat.format(
								JGitText.get().cannotReadFile
					} finally {
						lf.unlock();
