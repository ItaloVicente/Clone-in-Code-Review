		return newObjectChecker(fetchFsck);
	}

	@Nullable
	public ObjectChecker newReceiveObjectChecker() {
		return newObjectChecker(receiveFsck);
	}

	private ObjectChecker newObjectChecker(boolean check) {
		if (!check) {
