		int readAttempts = 0;
		while (readAttempts < MAX_LOOSE_OBJECT_STALE_READ_ATTEMPTS) {
			readAttempts++;
			File path = fileFor(id);
			try {
				return getObjectLoader(curs
			} catch (FileNotFoundException noFile) {
				if (path.exists()) {
					throw noFile;
				}
				break;
			} catch (IOException e) {
				if (!FileUtils.isStaleFileHandleInCausalChain(e)) {
					throw e;
				}
				if (LOG.isDebugEnabled()) {
					LOG.debug(MessageFormat.format(
							JGitText.get().looseObjectHandleIsStale
							Integer.valueOf(readAttempts)
									MAX_LOOSE_OBJECT_STALE_READ_ATTEMPTS)));
				}
			}
		}
		unpackedObjectCache().remove(id);
		return null;
	}

	ObjectLoader getObjectLoader(WindowCursor curs
			throws IOException {
