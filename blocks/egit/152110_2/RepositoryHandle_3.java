	@Override
	public final void close() {
		if (delegate instanceof CachingRepository) {
			((CachingRepository) delegate).clearConfigCache();
		}
		super.close();
	}

