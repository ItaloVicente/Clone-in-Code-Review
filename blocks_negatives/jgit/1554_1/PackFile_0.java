	private final UnpackedObjectCache.Entry readCache(final long position) {
		return UnpackedObjectCache.get(this, position);
	}

	private final void saveCache(final long position, final byte[] data, final int type) {
		UnpackedObjectCache.store(this, position, data, type);
	}

