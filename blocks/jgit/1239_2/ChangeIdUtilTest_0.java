	public void testHasChangeidWithReplacement() throws Exception {
		assertEquals(
				"has changeid\n\nBug: 33\nmore text\nSigned-off-by: me@you.too\nChange-Id: I988d2d7a6f2c0578fccabd4ebd3cec0768bc7f9f\nAnd then some\n"
				call("has changeid\n\nBug: 33\nmore text\nSigned-off-by: me@you.too\nChange-Id: I0123456789012345678901234567890123456789\nAnd then some\n"
						true));
	}

