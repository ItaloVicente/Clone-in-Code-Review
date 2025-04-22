	@Test
	public void testIndexOfChangeId() {
		assertEquals(3
				+ "Change-Id: I3b7e4e16b503ce00f07ba6ad01d97a356dad7701\n"
				"\n"));
		assertEquals(5
				+ "Change-Id: I3b7e4e16b503ce00f07ba6ad01d97a356dad7701\r\n"
				"\r\n"));
		assertEquals(3
				+ "Change-Id: I3b7e4e16b503ce00f07ba6ad01d97a356dad7701\r"
				"\r"));
		assertEquals(8
				+ "Change-Id: I3b7e4e16b503ce00f07ba6ad01d97a356dad7701\n"
				"\n"));
	}

	@Test
	public void testIndexOfFirstFooterLine() {
		assertEquals(
				2
				ChangeIdUtil.indexOfFirstFooterLine(new String[] { "a"
						"Bug: 42"
		assertEquals(
				3
				ChangeIdUtil.indexOfFirstFooterLine(new String[] { "a"
						"Bug: 42"
	}

