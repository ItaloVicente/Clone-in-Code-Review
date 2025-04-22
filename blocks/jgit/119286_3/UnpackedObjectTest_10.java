		try (ObjectStream in = ol.openStream()) {
			assertNotNull("have stream"
			assertEquals(type
			assertEquals(data.length
			byte[] data2 = new byte[data.length];
			IO.readFully(in
			assertTrue("same content"
					Arrays.equals(data
		}
