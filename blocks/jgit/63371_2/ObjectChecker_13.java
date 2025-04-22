	private boolean match(byte[] b
		int r = RawParseUtils.match(b
		if (r < 0) {
			return false;
		}
		ptr.value = r;
		return true;
	}

