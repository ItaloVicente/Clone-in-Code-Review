		int strategy = DIALOG_PERSISTLOCATION;
		if (persistSize)
			strategy |= DIALOG_PERSISTSIZE;
		return strategy;
	}

	@Override
	protected Point getInitialSize() {
		if (!persistSize) {
			Point size = super.getInitialSize();
			size.x = getPersistedSize().x;
			return size;
		}
		return super.getInitialSize();
	}

	private Point getPersistedSize() {
		boolean oldPersistSize = persistSize;
		persistSize = true;
		try {
			Point persistedSize = super.getInitialSize();
			return persistedSize;
		} finally {
			persistSize = oldPersistSize;
		}
