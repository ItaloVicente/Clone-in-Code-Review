		@SuppressWarnings("resource" /* java 7 */)
		final UnionInputStream u = new UnionInputStream();

		assertTrue(u.isEmpty());
		u.add(new ByteArrayInputStream(new byte[] { 1, 0, 2 }));
		u.add(new ByteArrayInputStream(new byte[] { 3 }));
		u.add(new ByteArrayInputStream(new byte[] { 4, 5 }));
