	public static BlockSource from(DfsReaderOptions opts
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
				int sz = opts.getStreamPackBufferSize();
				if (sz > 0) {
					try {
						ch.setReadAheadBytes((int) Math.min(end - start
					} catch (IOException e) {
					}
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

