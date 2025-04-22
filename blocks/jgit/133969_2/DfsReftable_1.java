					return -1;
				}
			}

			@Override
			public long position() throws IOException {
				return open().position();
			}

			@Override
			public void position(long newPosition) throws IOException {
				open().position(newPosition);
			}

			@Override
			public void setReadAheadBytes(int bufferSize) throws IOException {
				open().setReadAheadBytes(bufferSize);
			}

			@Override
			public long size() throws IOException {
				return open().size();
			}

			@Override
			public int read(ByteBuffer dst) throws IOException {
				return open().read(dst);
			}

			@Override
			public boolean isOpen() {
				return ch != null;
			}

			@Override
			public void close() throws IOException {
				if (ch != null) {
					try {
						ch.close();
					} finally {
						ch = null;
					}
