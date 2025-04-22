	ObjectLoader openLooseObject(WindowCursor curs
		int attempts = 0;

		while (attempts < MAX_LOOSE_OBJECT_STALE_READ_ATTEMPTS) {
			attempts++;
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
				LOG.debug(
						MessageFormat.format(
								JGitText.get().looseObjectHandleIsStale
								id.name()
								attempts
								MAX_LOOSE_OBJECT_STALE_READ_ATTEMPTS));
			}
		}
		unpackedObjectCache().remove(id);
		return null;
	}

	ObjectLoader getObjectLoader(WindowCursor curs
