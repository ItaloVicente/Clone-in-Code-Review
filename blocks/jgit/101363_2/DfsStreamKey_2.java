		this.hash = hash * 31;
	}

	public abstract DfsStreamKey derive(String suffix);

	@Override
	public int hashCode() {
		return hash;
	}

	@Override
	public abstract boolean equals(Object o);

	@SuppressWarnings("boxing")
	@Override
	public String toString() {
		return String.format("DfsStreamKey[hash=%08x]"
	}

	private static final class ByteArrayDfsStreamKey extends DfsStreamKey {
		private final byte[] name;

		ByteArrayDfsStreamKey(byte[] name) {
			super(Arrays.hashCode(name));
			this.name = name;
		}

		@Override
		public DfsStreamKey derive(String suffix) {
			byte[] s = suffix.getBytes(UTF_8);
			byte[] n = Arrays.copyOf(name
			System.arraycopy(s
			return new ByteArrayDfsStreamKey(n);
		}

		@Override
		public boolean equals(Object o) {
			if (o instanceof ByteArrayDfsStreamKey) {
				ByteArrayDfsStreamKey k = (ByteArrayDfsStreamKey) o;
				return hash == k.hash && Arrays.equals(name
			}
			return false;
		}
