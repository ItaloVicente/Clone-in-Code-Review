		hash = name.hashCode() * 31;
		nameBin = name.getBytes(UTF_8);
	}

	@Override
	public int hashCode() {
		return hash;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof DfsStreamKey) {
			DfsStreamKey k = (DfsStreamKey) o;
			return hash == k.hash && Arrays.equals(nameBin
		}
		return false;
	}

	public DfsStreamKey derive(String s) {
		String name = new String(nameBin
		return new DfsStreamKey(name + s);
