	public boolean findFile(String name) throws CorruptObjectException {
		return findFile(Constants.encode(name));
	}

	public boolean findFile(byte[] name) throws CorruptObjectException {
		for (; !eof(); next(1)) {
			int cmp = pathCompare(name
			if (cmp == 0) {
				return true;
			} else if (cmp > 0) {
				return false;
			}
		}
		return false;
	}

