	public final ObjectId insert(int objectType
			throws IOException {
		ObjectId id = doInsert(objectType
		if (insReader != null)
			insReader.put(id
		return id;
	}

	protected abstract ObjectId doInsert(int objectType
			InputStream in) throws IOException;

	public final ObjectReader insertedObjectReader(ObjectReader fallback) {
		return insertedObjectReader(fallback
	}

	public InsertedObjectReader insertedObjectReader(ObjectReader fallback
			int objectLimit
		return new InsertedObjectReader(this
	}
