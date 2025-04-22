	@Test
	public void chomp() {
		assertEquals("foo", BaseReceivePack.chomp("foo"));
		assertEquals("foo", BaseReceivePack.chomp("foo\n"));
		assertEquals("foo\n", BaseReceivePack.chomp("foo\n\n"));
	}

