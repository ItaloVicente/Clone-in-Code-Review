	@Test
	public void testIndexOfFirstFooterLine() {
		assertEquals(
				2,
				ChangeIdUtil.indexOfFirstFooterLine(new String[] { "a", "",
						"Bug: 42", "Signed-Off-By: j.developer@a.com" }));
		assertEquals(
				3,
				ChangeIdUtil.indexOfFirstFooterLine(new String[] { "a",
						"Bug: 42", "", "Signed-Off-By: j.developer@a.com" }));
	}

