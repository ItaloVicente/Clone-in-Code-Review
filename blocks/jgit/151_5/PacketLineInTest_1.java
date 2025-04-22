		String s = "ACK fcfcfb1fd94829c1a1704f894fc111d14770d34e neverhappen";
		init("003d" + s + "\n");
		try {
			in.readACK(new MutableObjectId());
			fail("incorrectly accepted unsupported ACK status");
		} catch (IOException e) {
			assertEquals("Expected ACK/NAK
		}
	}

	public void testReadACK_Invalid4() {
