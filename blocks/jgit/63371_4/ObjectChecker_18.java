	private boolean match(byte[] b
		int r = RawParseUtils.match(b
		if (r < 0) {
			return false;
		}
		bufPtr.value = r;
		return true;
	}

