	public static DfsStreamKey of(byte[] name) {
		return new ByteArrayDfsStreamKey(name);
	}

	final int hash;

	protected DfsStreamKey(int hash) {
