		assertNull(b.get(id(0x01), null));
		assertEquals(id(0x81), b.get(id(0x11), null));
		assertEquals(id(0x82), b.get(id(0x22), null));
		assertNull(b.get(id(0x33), null));
		assertEquals(id(0x84), b.get(id(0x44), null));
		assertEquals(id(0x85), b.get(id(0x55), null));
		assertNull(b.get(id(0x66), null));
