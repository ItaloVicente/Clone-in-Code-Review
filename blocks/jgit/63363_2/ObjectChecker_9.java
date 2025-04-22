
		ptr = ptrout.value;
		if (raw[ptr++] == '\n')
			return ptr;
		return -1;
	}

	public void checkCommit(byte[] raw) throws CorruptObjectException {
		checkCommit(idFor(OBJ_COMMIT
