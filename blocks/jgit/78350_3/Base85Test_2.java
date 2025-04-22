	@Test
	public void failOnBadLength() {
		try {
			byte[] bytes = "hi".getBytes();
			decode85(bytes
			fail("Accepted string with length not multiple of 5");
		} catch (IllegalArgumentException fail) {
		}
	}
