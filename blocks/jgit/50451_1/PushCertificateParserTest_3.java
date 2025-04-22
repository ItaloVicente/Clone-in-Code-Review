	@Test
	public void disabledParserStillRequiresCorrectSyntax() throws Exception {
		PacketLineIn pckIn = newPacketLineIn("001ccertificate version XYZ\n");
		PushCertificateParser parser =
				new PushCertificateParser(db
		assertFalse(parser.enabled());
		try {
			parser.receiveHeader(pckIn
			fail("Expected PackProtocolException");
		} catch (PackProtocolException e) {
			assertEquals(
					"Push certificate has missing or invalid value for certificate"
						+ " version: XYZ"
					e.getMessage());
		}
		assertNull(parser.build());
	}

	@Test
	public void parseCertFromPktLine() throws Exception {
		PacketLineIn pckIn = newPacketLineIn(INPUT);
		PushCertificateParser parser =
				new PushCertificateParser(db
