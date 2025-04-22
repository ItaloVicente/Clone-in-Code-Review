
	public void testGetByte() {
		byte[] raw = new byte[20];
		for (int i = 0; i < 20; i++)
			raw[i] = (byte) (0xa0 + i);
		ObjectId id = ObjectId.fromRaw(raw);

		assertEquals(raw[0] & 0xff
		assertEquals(raw[0] & 0xff
		assertEquals(raw[1] & 0xff

		for (int i = 2; i < 20; i++)
			assertEquals("index " + i
	}
