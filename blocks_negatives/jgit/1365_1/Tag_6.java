	public Tag(final Repository db, final ObjectId id, String refName, final byte[] raw) {
		objdb = db;
		if (raw != null) {
			tagId = id;
			objId = ObjectId.fromString(raw, 7);
		} else
			objId = id;
		if (refName != null && refName.startsWith("refs/tags/"))
			refName = refName.substring(10);
		tag = refName;
		this.raw = raw;
