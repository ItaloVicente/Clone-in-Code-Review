	@Test
	public void testCheckoutMixedAutoEolCrLf() throws Exception {
		checkoutLineEndings("first line\nsecond line\r\n"
				"first line\nsecond line\r\n"
	}

	@Test
	public void testCheckoutMixedAutoEolLf() throws Exception {
		checkoutLineEndings("first line\nsecond line\r\n"
				"first line\nsecond line\r\n"
	}

	@Test
	public void testCheckoutMixedTextCrLf() throws Exception {
		checkoutLineEndings("first line\nsecond line\r\n"
				"first line\r\nsecond line\r\n"
	}

	@Test
	public void testCheckoutMixedTextLf() throws Exception {
		checkoutLineEndings("first line\nsecond line\r\nfoo"
				"first line\nsecond line\r\nfoo"
	}

