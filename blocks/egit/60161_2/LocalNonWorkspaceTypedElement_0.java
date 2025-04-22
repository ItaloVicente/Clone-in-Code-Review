	@Override
	public long getModificationDate() {
		return timestamp;
	}

	@Override
	public boolean isSynchronized() {
		return path.toFile().lastModified() == timestamp;
	}

