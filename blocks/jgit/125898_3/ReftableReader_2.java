		RefCursorImpl i = new RefCursorImpl(refEnd
		i.block = seek(REF_BLOCK_TYPE
		return i;
	}

	@Override
	public RefCursor seekPrefix(String prefix) throws IOException {
		initRefIndex();
