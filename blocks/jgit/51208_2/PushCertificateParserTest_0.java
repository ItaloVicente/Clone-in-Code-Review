	@Test
	public void testMissingPusheeField() throws Exception {
		assertFalse(input.contains(PushCertificateParser.PUSHEE));

		PacketLineIn pckIn = newPacketLineIn(input);
		PushCertificateParser parser =
				new PushCertificateParser(db
		parser.receiveHeader(pckIn
		parser.addCommand(pckIn.readStringRaw());
		assertEquals(PushCertificateParser.BEGIN_SIGNATURE
		parser.receiveSignature(pckIn);

		PushCertificate cert = parser.build();
		assertEquals("0.1"
		assertNull(cert.getPushee());
	}

