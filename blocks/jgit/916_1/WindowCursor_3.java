	private final FileObjectDatabase db;

	WindowCursor(FileObjectDatabase db) {
		this.db = db;
	}

	public boolean hasObject(AnyObjectId objectId) throws IOException {
		return db.hasObject(objectId);
	}

	public ObjectLoader openObject(AnyObjectId objectId
			throws MissingObjectException
		final ObjectLoader ldr = db.openObject(this
		if (ldr == null) {
			if (typeHint == OBJ_ANY)
				throw new MissingObjectException(objectId.copy()
			throw new MissingObjectException(objectId.copy()
		}
		return ldr;
	}

	void openObjectInAllPacks(AnyObjectId otp
			Collection<PackedObjectLoader> reuseLoaders) throws IOException {
		db.openObjectInAllPacks(reuseLoaders
	}

