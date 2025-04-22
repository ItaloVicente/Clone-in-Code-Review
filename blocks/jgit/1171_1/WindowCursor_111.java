	final FileObjectDatabase db;

	WindowCursor(FileObjectDatabase db) {
		this.db = db;
	}

	@Override
	public ObjectReader newReader() {
		return new WindowCursor(db);
	}

	public boolean has(AnyObjectId objectId) throws IOException {
		return db.has(objectId);
	}

	public ObjectLoader open(AnyObjectId objectId
			throws MissingObjectException
			IOException {
		final ObjectLoader ldr = db.openObject(this
		if (ldr == null) {
			if (typeHint == OBJ_ANY)
				throw new MissingObjectException(objectId.copy()
			throw new MissingObjectException(objectId.copy()
		}
		if (typeHint != OBJ_ANY && ldr.getType() != typeHint)
			throw new IncorrectObjectTypeException(objectId.copy()
		return ldr;
	}

	public long getObjectSize(AnyObjectId objectId
			throws MissingObjectException
			IOException {
		long sz = db.getObjectSize(this
		if (sz < 0) {
			if (typeHint == OBJ_ANY)
				throw new MissingObjectException(objectId.copy()
			throw new MissingObjectException(objectId.copy()
		}
		return sz;
	}

	public LocalObjectToPack newObjectToPack(RevObject obj) {
		return new LocalObjectToPack(obj);
	}

	public void selectObjectRepresentation(PackWriter packer
			throws IOException
		db.selectObjectRepresentation(packer
	}

	public void copyObjectAsIs(PackOutputStream out
			throws IOException
		LocalObjectToPack src = (LocalObjectToPack) otp;
		src.pack.copyAsIs(out
	}

