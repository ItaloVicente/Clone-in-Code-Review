		@SuppressWarnings("resource" /* java 7 */)
		final UnionInputStream u = new UnionInputStream();
		final boolean closed[] = new boolean[2];
		u.add(new ByteArrayInputStream(new byte[] { 1 }) {
			@Override
			public void close() {
				closed[0] = true;
			}
		});
		u.add(new ByteArrayInputStream(new byte[] { 2 }) {
			@Override
			public void close() {
				closed[1] = true;
			}
		});
