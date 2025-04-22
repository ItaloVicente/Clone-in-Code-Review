	private void checkPersonIdent(byte[] raw
			throws CorruptObjectException {
		if (allowInvalidPersonIdent) {
			bufPtr.value = nextLF(raw
			return;
		}
