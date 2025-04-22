
	private static class TestWithKeyTranscoder implements Transcoder<String> {
		private static final int flags=238885207;

		private final String key;

		TestWithKeyTranscoder(String k) {
			key = k;
		}

		public String decode(CachedData d) {
			assert d.getFlags() == flags
				: "expected " + flags + " got " + d.getFlags();

			ByteBuffer bb = ByteBuffer.wrap(d.getData());

			int keyLength = bb.getInt();
			byte[] keyBytes = new byte[keyLength];
			bb.get(keyBytes);
			String k = new String(keyBytes);

			assertEquals(key, k);

			int valueLength = bb.getInt();
			byte[] valueBytes = new byte[valueLength];
			bb.get(valueBytes);

			return new String(valueBytes);
		}

		public CachedData encode(String o) {
			byte[] keyBytes = key.getBytes();
			byte[] valueBytes = o.getBytes();
			int length = 4 + keyBytes.length + 4 + valueBytes.length;
			byte[] bytes = new byte[length];

			ByteBuffer bb = ByteBuffer.wrap(bytes);
			bb.putInt(keyBytes.length).put(keyBytes);
			bb.putInt(valueBytes.length).put(valueBytes);

			return new CachedData(flags, bytes, getMaxSize());
		}

		public int getMaxSize() {
			return CachedData.MAX_SIZE;
		}

		public boolean asyncDecode(CachedData d) {
			return false;
		}
	}
