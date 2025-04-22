	public void testLongDecode() {
		assertEquals(4294967296L,
				decodeLong(new byte[]{0, 0, 0, 1, 0, 0, 0, 0}, 0));
		assertEquals(1L,
				decodeLong(new byte[]{0, 0, 0, 0, 0, 0, 0, 1}, 0));
}

