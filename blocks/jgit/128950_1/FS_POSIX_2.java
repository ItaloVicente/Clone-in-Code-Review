			if (link != null) {
				Files.delete(link);
			}
		}
	}

	@Override
	public LockToken createNewFileAtomic(File file) throws IOException {
		if (!file.createNewFile()) {
			return token(false
		}
		if (supportsAtomicCreateNewFile() || !supportsUnixNLink) {
			return token(true
