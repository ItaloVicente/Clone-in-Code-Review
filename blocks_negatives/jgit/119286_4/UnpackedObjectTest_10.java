		ObjectStream in = ol.openStream();
		assertNotNull("have stream", in);
		assertEquals(type, in.getType());
		assertEquals(data.length, in.getSize());
		byte[] data2 = new byte[data.length];
		IO.readFully(in, data2, 0, data.length);
		assertTrue("same content", Arrays.equals(data2, data));
		assertEquals("stream at EOF", -1, in.read());
		in.close();
