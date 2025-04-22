	@Test
	public void testParseString() throws Exception {
		String str = concatPacketLines(INPUT
		assertEquals(
				PushCertificateParser.fromReader(new StringReader(str))
				PushCertificateParser.fromString(str));
	}

