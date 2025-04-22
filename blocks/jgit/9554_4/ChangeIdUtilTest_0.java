				"has changeid\nmore text\n\nSigned-off-by: me@you.too\n"
						+ "Change-Id: I2178563fada5edb2c99a8d8c0d619471b050ec24\nBug: 33\n"
				call("has changeid\nmore text\n\nSigned-off-by: me@you.too\n"
						+ "Change-Id: I0123456789012345678901234567890123456789\nBug: 33\n"
						true));
	}

	@Test
	public void testHasChangeidWithReplacementInLastLine() throws Exception {
		assertEquals(
				"has changeid\nmore text\n\nBug: 33\nSigned-off-by: me@you.too\n"
						+ "Change-Id: I1d6578f4c96e3db4dd707705fe3d17bf658c4758\n"
				call("has changeid\nmore text\n\nBug: 33\nSigned-off-by: me@you.too\n"
						+ "Change-Id: I0123456789012345678901234567890123456789\n"
						true));
	}

	@Test
	public void testHasChangeidWithReplacementInLastLineNoLineBreak()
			throws Exception {
		assertEquals(
				"has changeid\nmore text\n\nBug: 33\nSigned-off-by: me@you.too\n"
						+ "Change-Id: I1d6578f4c96e3db4dd707705fe3d17bf658c4758"
				call("has changeid\nmore text\n\nBug: 33\nSigned-off-by: me@you.too\n"
						+ "Change-Id: I0123456789012345678901234567890123456789"
						true));
	}

	@Test
	public void testHasChangeidWithSpacesBeforeId() throws Exception {
		assertEquals(
				"has changeid\nmore text\n\nBug: 33\nSigned-off-by: me@you.too\n"
						+ "Change-Id: Ie7575eaf450fdd0002df2e642426faf251de3ad9\n"
				call("has changeid\nmore text\n\nBug: 33\nSigned-off-by: me@you.too\n"
						+ "Change-Id:    I0123456789012345678901234567890123456789\n"
						true));
	}

	@Test
	public void testHasChangeidWithReplacementWithChangeIdInCommitMessage()
			throws Exception {
		assertEquals(
				"has changeid\nmore text\n"
						+ "Change-Id: I0123456789012345678901234567890123456789\n\n"
						+ "Bug: 33\nSigned-off-by: me@you.too\n"
						+ "Change-Id: Ie48d10d59ef67995ca89688ac0171b88f10dd520\n"
				call("has changeid\nmore text\n"
						+ "Change-Id: I0123456789012345678901234567890123456789\n\n"
						+ "Bug: 33\nSigned-off-by: me@you.too\n"
						+ "Change-Id: I0123456789012345678901234567890123456789\n"
