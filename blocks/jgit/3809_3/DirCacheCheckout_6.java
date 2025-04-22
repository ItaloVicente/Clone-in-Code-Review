	public static void checkoutEntry(final Repository repository
			DirCacheEntry entry) throws IOException {
		ObjectReader or = repository.newObjectReader();
		try {
			checkoutEntry(repository
		} finally {
			or.release();
		}
	}

