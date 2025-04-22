	@Override
	public ObjectId insert(int type
			throws IOException {
		ObjectId id = idFor(type
		if (db.has(id)) {
			return id;
		} else {
			File tmp = toTemp(type
			return insertOneObject(tmp
		}
	}

