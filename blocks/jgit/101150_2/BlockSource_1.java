	public static BlockSource from(byte[] table) {
		return new BlockSource() {
			@Override
			public ByteBuffer read(long position
					throws IOException {
				ByteBuffer buf = ByteBuffer.allocate(blockSize);
				if (position < table.length) {
					int p = (int) position;
					int n = Math.min(blockSize
					buf.put(table
				}
				return buf;
			}

			@Override
			public long size() throws IOException {
				return table.length;
			}

			@Override
			public void close() {
			}
		};
	}

