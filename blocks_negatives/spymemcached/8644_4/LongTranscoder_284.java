	public Long decode(CachedData d) {
		if (flags == d.getFlags()) {
			return tu.decodeLong(d.getData());
		} else {
			getLogger().error("Unexpected flags for long:  "
				+ d.getFlags() + " wanted " + flags);
			return null;
		}
	}
