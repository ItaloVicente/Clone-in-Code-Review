	public void testNullSha1InTreeEntry() throws CorruptObjectException {
		byte[] data = concat(
				encodeASCII("100644 A")
				new byte[OBJECT_ID_LENGTH]);
		assertCorrupt("entry points to null SHA-1"
		assertSkipListAccepts(OBJ_TREE
		checker.setIgnore(NULL_SHA1
