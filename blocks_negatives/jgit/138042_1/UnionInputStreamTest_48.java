		u.add(new ByteArrayInputStream(new byte[] { 20, 30 }) {
			@Override
			@SuppressWarnings("UnsynchronizedOverridesSynchronized")
			public long skip(long n) {
				return 0;
			}
		});
		assertEquals(2, u.skip(8));
		assertEquals(-1, u.read());
