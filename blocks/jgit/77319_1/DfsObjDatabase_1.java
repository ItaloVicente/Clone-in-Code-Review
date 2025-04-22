	protected void markDirty() {
		packList.get().markDirty();
	}

	long lastModified() {
		return packList.get().lastModified;
	}

