		ObjectStream in = ol.openStream();
		assertNotNull("have stream", in);
		assertEquals(type, in.getType());
		assertEquals(data.length, in.getSize());
		byte[] data2 = new byte[data.length];
		IO.readFully(in, data2, 0, data.length);
		assertTrue("same content", Arrays.equals(data, ol.getCachedBytes()));
		in.close();
