	public void testUnsignedIntegerDecode() {
		assertEquals(129,
			decodeUnsignedInt(new byte[]{0, 0, 0, (byte)0x81}, 0));
		assertEquals(129 * 256,
				decodeUnsignedInt(new byte[]{0, 0, (byte)0x81, 0}, 0));
		assertEquals(129 * 256 * 256,
				decodeUnsignedInt(new byte[]{0, (byte)0x81, 0, 0}, 0));
		assertEquals(129L * 256L * 256L * 256L,
				decodeUnsignedInt(new byte[]{(byte)0x81, 0, 0, 0}, 0));
	}
