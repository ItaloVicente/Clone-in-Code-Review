	@Test(expected = InvalidObjectIdException.class)
	public void testFromString_short() {
		ObjectId.fromString("cafe1234");
	}

	@Test(expected = InvalidObjectIdException.class)
	public void testFromString_nonHex() {
		ObjectId.fromString("0123456789abcdefghij0123456789abcdefghij");
	}

	@Test(expected = InvalidObjectIdException.class)
	public void testFromString_shortNonHex() {
		ObjectId.fromString("6789ghij");
	}

