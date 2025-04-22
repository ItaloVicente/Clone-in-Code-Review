	@Nullable
	LockFile lockPackedRefs() throws IOException {
		LockFile lck = new LockFile(packedRefsFile);
		for (int ms : getRetrySleepMs()) {
			sleep(ms);
			if (!lck.lock()) {
				continue;
			}
			return lck;
		}
		return null;
	}

	private LockFile lockPackedRefsOrThrow() throws IOException {
		LockFile lck = lockPackedRefs();
		if (lck == null) {
			throw new LockFailedException(packedRefsFile);
		}
		return lck;
	}

