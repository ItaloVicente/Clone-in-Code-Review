	@Override
	public boolean hasId() {
		if (contentIdFromPtr == ptr)
			return true;
		return (mode & FileMode.TYPE_MASK) == FileMode.TYPE_FILE;
	}

