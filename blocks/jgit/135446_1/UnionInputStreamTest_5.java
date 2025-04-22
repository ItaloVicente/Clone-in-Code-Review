		try (UnionInputStream u = new UnionInputStream()) {
			u.add(new ByteArrayInputStream(new byte[] { 1
			u.add(new ByteArrayInputStream(new byte[] { 3 }));
			u.add(new ByteArrayInputStream(new byte[] { 4
			assertEquals(0
			assertEquals(3
			assertEquals(3
			assertEquals(2
			assertEquals(0
			assertEquals(-1
