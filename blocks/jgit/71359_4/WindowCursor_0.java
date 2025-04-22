		this.createdFromInserter = null;
	}

	WindowCursor(FileObjectDatabase db
			@Nullable ObjectDirectoryInserter createdFromInserter) {
		this.db = db;
		this.createdFromInserter = createdFromInserter;
