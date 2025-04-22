	public final boolean isTree() {
		return (mode & TYPE_MASK) == TYPE_TREE;
	}

	public final Subtree enter(ObjectReader reader) throws IOException {
		return enter(reader
	}

	public final Subtree enter(ObjectReader reader
			throws IOException {
		idBuf.fromRaw(raw
		byte[] tree = reader.open(idBuf
		return new Subtree(this
	}

	public RawTreeIterator exit() {
		return this;
	}

