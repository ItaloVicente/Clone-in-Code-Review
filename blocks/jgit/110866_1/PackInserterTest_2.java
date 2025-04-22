		try (ObjectStream s = loader.openStream()) {
			int n = (int) s.getSize();
			byte[] actual = new byte[n];
			assertEquals(n
			assertArrayEquals(expected
		}
