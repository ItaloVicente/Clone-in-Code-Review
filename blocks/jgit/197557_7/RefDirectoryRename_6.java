		if (!refCache.isPresent()) {
			return doRenameImpl();
		}

		Lock cacheLock = refCache.get().getLock().writeLock();
		cacheLock.lock();
		try {
			Result result = doRenameImpl();
			if (result.updateSucceeded()) {
				refCache.get().rename(source.getRef()
						destination.getUpdatedRef());
			}
			return result;
		} finally {
			cacheLock.unlock();
		}
	}

	private Result doRenameImpl() throws IOException {
		if (source.getRef().isSymbolic()) {
