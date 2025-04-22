		parser.addCommand(pckIn.readString());
		assertEquals(PushCertificateParser.BEGIN_SIGNATURE
		parser.receiveSignature(pckIn);

		PushCertificate cert = parser.build();
		assertEquals("0.1"
		assertEquals("Dave Borowitz"
		assertEquals("dborowitz@google.com"
				cert.getPusherIdent().getEmailAddress());
		assertEquals(1433954361000L
		assertEquals(-7 * 60
		assertEquals("1433954361-bde756572d665bba81d8"

		assertNotEquals(cert.getNonce()
		assertEquals(PushCertificate.NonceStatus.BAD

		assertEquals(1
		ReceiveCommand cmd = cert.getCommands().get(0);
		assertEquals("refs/heads/master"
		assertEquals(ObjectId.zeroId()
		assertEquals("6c2b981a177396fb47345b7df3e4d3f854c6bea7"
				cmd.getNewId().name());

		assertEquals(concatPacketLines(INPUT

		String signature = concatPacketLines(INPUT
		assertTrue(signature.startsWith(PushCertificateParser.BEGIN_SIGNATURE));
		assertTrue(signature.endsWith(PushCertificateParser.END_SIGNATURE + "\n"));
		assertEquals(signature
	}

	@Test
	public void parseCertFromPktLineNoNewlines() throws Exception {
		PacketLineIn pckIn = newPacketLineIn(INPUT_NO_NEWLINES);
		PushCertificateParser parser =
				new PushCertificateParser(db
		parser.receiveHeader(pckIn
		parser.addCommand(pckIn.readString());
		assertEquals(PushCertificateParser.BEGIN_SIGNATURE
