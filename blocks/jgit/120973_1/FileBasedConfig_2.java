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
				final IOException e2 = new IOException(MessageFormat
						.format(JGitText.get().cannotReadFile
				e2.initCause(e);
				throw e2;
			} catch (ConfigInvalidException e) {
				throw new ConfigInvalidException(MessageFormat
						.format(JGitText.get().cannotReadFile
