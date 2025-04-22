				return;
			} catch (IOException e) {
				if (FileUtils.isStaleFileHandle(e)
						&& retries < maxStaleRetries) {
					if (LOG.isDebugEnabled()) {
						LOG.debug(MessageFormat.format(
								JGitText.get().configHandleIsStale
								Integer.valueOf(retries))
					}
					retries++;
					continue;
				}
				throw new IOException(MessageFormat
						.format(JGitText.get().cannotReadFile
			} catch (ConfigInvalidException e) {
				throw new ConfigInvalidException(MessageFormat
						.format(JGitText.get().cannotReadFile
