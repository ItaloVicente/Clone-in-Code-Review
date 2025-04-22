	public static BlockSource from(ReadableChannel ch) {
		return new BlockSource() {
			@Override
			public ByteBuffer read(long pos
				ByteBuffer b = ByteBuffer.allocate(sz);
				ch.position(pos);
				int n;
				do {
					n = ch.read(b);
				} while (n > 0 && b.position() < sz);
				return b;
			}

			@Override
			public void adviseSequentialRead(long start
				try {
					ch.setReadAheadBytes((int) Math.max(end - start
				} catch (IOException e) {
				}
			}

			@Override
			public long size() throws IOException {
				return ch.size();
			}

			@Override
			public void close() {
				try {
					ch.close();
				} catch (IOException e) {
				}
			}
		};
	}

