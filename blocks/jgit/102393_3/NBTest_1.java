	@Test
	public void testEncodeInt24() {
		byte[] out = new byte[16];

		prepareOutput(out);
		NB.encodeInt24(out
		assertOutput(b(0

		prepareOutput(out);
		NB.encodeInt24(out
		assertOutput(b(0

		prepareOutput(out);
		NB.encodeInt24(out
		assertOutput(b(0

		prepareOutput(out);
		NB.encodeInt24(out
		assertOutput(b(0

		prepareOutput(out);
		NB.encodeInt24(out
		assertOutput(b(0xc0

		prepareOutput(out);
		NB.encodeInt24(out
		assertOutput(b(0xba

		prepareOutput(out);
		NB.encodeInt24(out
		assertOutput(b(0xff
	}

