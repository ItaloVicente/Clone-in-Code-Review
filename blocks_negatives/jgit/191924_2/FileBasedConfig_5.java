				snapshot = newSnapshot;
				return;
			} catch (IOException e) {
				if (FileUtils.isStaleFileHandle(e)
						&& retries < maxRetries) {
					if (LOG.isDebugEnabled()) {
						LOG.debug(MessageFormat.format(
								JGitText.get().configHandleIsStale,
								Integer.valueOf(retries)), e);
					}
					retries++;
					continue;
				}
				throw new IOException(MessageFormat
						.format(JGitText.get().cannotReadFile, getFile()), e);
			} catch (ConfigInvalidException e) {
				throw new ConfigInvalidException(MessageFormat
						.format(JGitText.get().cannotReadFile, getFile()), e);
