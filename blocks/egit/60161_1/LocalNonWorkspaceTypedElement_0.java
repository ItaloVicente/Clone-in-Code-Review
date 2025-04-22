	@Override
	public long getModificationDate() {
		if (getResource().isAccessible()) {
			return super.getModificationDate();
		}
		return timestamp;
	}

	@Override
	public boolean isSynchronized() {
		if (getResource().isAccessible()) {
			return super.isSynchronized();
		}
		return path.toFile().lastModified() == timestamp;
	}

