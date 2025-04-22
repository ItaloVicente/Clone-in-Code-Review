	public static BlockSource from(byte[] content) {
		return new BlockSource() {
			@Override
			public ByteBuffer read(long pos
				ByteBuffer buf = ByteBuffer.allocate(cnt);
				if (pos < content.length) {
					int p = (int) pos;
					int n = Math.min(cnt
					buf.put(content
				}
				return buf;
			}

			@Override
			public long size() {
				return content.length;
			}

			@Override
			public void close() {
			}
		};
	}

