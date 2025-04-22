	public static CachedPackKey fromInfo(CachedPackInfo info) {
		ObjectId name = ObjectId.fromString(info.getName());
		ObjectId vers = ObjectId.fromString(info.getVersion());
		return new CachedPackKey(name
	}

