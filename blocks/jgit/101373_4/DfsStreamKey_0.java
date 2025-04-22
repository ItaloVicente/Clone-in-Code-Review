		this.hash = Arrays.hashCode(name) * 31;
		this.name = name;
	}

	public DfsStreamKey derive(String suffix) {
		byte[] s = suffix.getBytes(UTF_8);
		byte[] n = Arrays.copyOf(name
		System.arraycopy(s
		return new DfsStreamKey(n);
	}

	@Override
	public int hashCode() {
		return hash;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof DfsStreamKey) {
			DfsStreamKey k = (DfsStreamKey) o;
			return hash == k.hash && Arrays.equals(name
		}
		return false;
	}

	@SuppressWarnings("boxing")
	@Override
	public String toString() {
		return String.format("DfsStreamKey[hash=%08x]"
