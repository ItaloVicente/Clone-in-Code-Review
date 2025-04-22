					return -1;
				}
			}

			@Override
			public long position() throws IOException {
				return channel().position();
			}

			@Override
			public void position(long newPosition) throws IOException {
				channel().position(newPosition);
			}

			@Override
			public void setReadAheadBytes(int bufferSize) throws IOException {
				channel().setReadAheadBytes(bufferSize);
			}

			@Override
			public long size() throws IOException {
				return channel().size();
			}

			@Override
			public int read(ByteBuffer dst) throws IOException {
				return channel().read(dst);
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
