	@Test
	public void testReadString_Len0001() {
		init("0001");
		try {
			in.readString();
			fail("incorrectly accepted invalid packet header");
		} catch (IOException e) {
			assertEquals("Invalid packet line header: 0001", e.getMessage());
		}
	}

