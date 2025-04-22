
	public void testThrowsIllegalArgumentExceptionIfAskedToConvertNonString()
			throws Exception {
		try {
			converter.convert(new Integer(1));
			fail("exception should have been thrown");
		} catch (IllegalArgumentException e) {
		}
	}
