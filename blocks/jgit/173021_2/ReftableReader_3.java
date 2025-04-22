		RefCursorImpl i = new RefCursorImpl(refEnd
		i.block = seek(REF_BLOCK_TYPE
		return i;
	}

	@Override
	public RefCursor seekPastRef(String refName) throws IOException {
		refName = refName + LAST_UTF8_CHAR;
		initRefIndex();

		byte[] key = refName.getBytes(UTF_8);
		RefCursorImpl i = new RefCursorImpl(refEnd
