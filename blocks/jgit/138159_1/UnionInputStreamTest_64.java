		try (UnionInputStream u = new UnionInputStream(
				new ByteArrayInputStream(new byte[] { 1
				errorReadStream)) {
			byte buf[] = new byte[10];
			assertEquals(3
			assertTrue(Arrays.equals(new byte[] { 1
			try {
				u.read(buf
				fail("Expected exception from errorReadStream");
			} catch (IOException e) {
				assertEquals("Expected"
			}
