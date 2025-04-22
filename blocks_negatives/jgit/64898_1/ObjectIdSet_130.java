	public SymlinkTreeEntry(final Tree parent, final ObjectId id,
			final byte[] nameUTF8) {
		super(parent, id, nameUTF8);
	}

	public FileMode getMode() {
		return FileMode.SYMLINK;
	}

	public String toString() {
		final StringBuilder r = new StringBuilder();
		r.append(ObjectId.toString(getId()));
		r.append(getFullName());
		return r.toString();
	}
