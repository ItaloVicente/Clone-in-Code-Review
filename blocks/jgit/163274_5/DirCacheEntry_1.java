	public DirCacheEntry(String newPath
			FileMode fileMode) {
		this(Constants.encode(newPath)
		setObjectId(id);
		setFileMode(fileMode);
	}

	public DirCacheEntry(String newPath
			AnyObjectId id) {
		this(Constants.encode(newPath)
		setFileMode(fileMode);
		setObjectId(id);
	}

