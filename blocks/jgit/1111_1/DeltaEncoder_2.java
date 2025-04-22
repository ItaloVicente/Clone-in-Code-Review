	public boolean insert(byte[] text
			throws IOException {
		if (cnt <= 0)
			return true;
		if (0 < limit) {
			int hdrs = cnt / MAX_INSERT_DATA_SIZE;
			if (cnt % MAX_INSERT_DATA_SIZE != 0)
				hdrs++;
			if (limit < size + hdrs + cnt)
				return false;
		}
		do {
