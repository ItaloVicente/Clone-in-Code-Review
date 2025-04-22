
	public void testSetByte() {
		byte[] exp = new byte[20];
		for (int i = 0; i < 20; i++)
			exp[i] = (byte) (0xa0 + i);

		MutableObjectId id = new MutableObjectId();
		id.fromRaw(exp);
		assertEquals(ObjectId.fromRaw(exp).name()

		id.setByte(0
		assertEquals(0x10
		exp[0] = 0x10;
		assertEquals(ObjectId.fromRaw(exp).name()

		for (int p = 1; p < 20; p++) {
			id.setByte(p
			assertEquals(0x10 + p
			exp[p] = (byte) (0x10 + p);
			assertEquals(ObjectId.fromRaw(exp).name()
		}

		for (int p = 0; p < 20; p++) {
			id.setByte(p
			assertEquals(0x80 + p
			exp[p] = (byte) (0x80 + p);
			assertEquals(ObjectId.fromRaw(exp).name()
		}
	}
