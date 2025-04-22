	public Integer decode(CachedData d) {
		if (flags == d.getFlags()) {
			return tu.decodeInt(d.getData());
		} else {
			return null;
		}
	}
