
	@Test
	public void testFooterOffsetNoFooter() {
		assertEquals(-1, CommonUtils.getFooterOffset(""));
		assertEquals(-1, CommonUtils.getFooterOffset("line 1"));
		assertEquals(-1, CommonUtils.getFooterOffset("line 1\nFoobar"));
		assertEquals(-1, CommonUtils.getFooterOffset("line 1\n\nFoobar"));
		assertEquals(-1, CommonUtils.getFooterOffset("line 1\nFoo:bar"));
		assertEquals(-1, CommonUtils.getFooterOffset("line 1\n_\nFoo:bar"));
	}

	@Test
	public void testFooterOffset() {
		assertEquals(8, CommonUtils.getFooterOffset("line 1\n\nFoo:bar"));
		assertEquals(8, CommonUtils.getFooterOffset("line 1\n\nFoo:bar   "));
		assertEquals(8, CommonUtils.getFooterOffset("line 1\n\nFoo:bar\n   "));
		assertEquals(8, CommonUtils.getFooterOffset("line 1\n\nFoo:bar\n  \n"));
		assertEquals(8, CommonUtils
				.getFooterOffset("line 1\n\nFoo:bar\nFoobar: barbar"));
		assertEquals(8, CommonUtils
				.getFooterOffset("line 1\n\nFoo:bar\nFoobar: barbar   "));
		assertEquals(8, CommonUtils
				.getFooterOffset("line 1\n\nFoo:bar\nFoobar: barbar\n   "));
		assertEquals(10,
				CommonUtils.getFooterOffset("line 1\n  \nFoo:bar\n  \n"));
		assertEquals(17, CommonUtils
				.getFooterOffset("line 1\n\nFoo:bar\n\nFoobar: barbar\n   "));
	}
