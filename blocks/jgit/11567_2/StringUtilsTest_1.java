
	@Test
	public void testReplaceLineBreaks() {
		assertEquals("a b c "
				StringUtils.replaceLineBreaksWithSpace("a b\nc\r"));
		assertEquals("a b c "
				StringUtils.replaceLineBreaksWithSpace("a b\nc\n"));
		assertEquals("a b c "
				StringUtils.replaceLineBreaksWithSpace("a b\nc\r\n"));
		assertEquals("a b c d"
				StringUtils.replaceLineBreaksWithSpace("a\r\nb\nc d"));
	}
