	@Override
	public void initialize() {
		int size=(1+keys.size()) * MIN_RECV_PACKET;
		for(byte[] b : bkeys.values()) {
			size += b.length;
		}
		ByteBuffer bb=ByteBuffer.allocate(size);
		for(Map.Entry<Integer, byte[]> me : bkeys.entrySet()) {
			final byte[] keyBytes=me.getValue();
			final String key = keys.get(me.getKey());
