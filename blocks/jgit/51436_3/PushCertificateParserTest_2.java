	@Test
	public void testConcatPacketLinesInsertsNewlines() throws Exception {
		String input = "000bline 1\n000aline 2000bline 3\n";
		assertEquals("line 1\n"
		assertEquals("line 1\nline 2\n"
		assertEquals("line 2\nline 3\n"
		assertEquals("line 2\nline 3\n"
	}

