	@Test
	public void testParseReader() throws Exception {
		Reader reader = new InputStreamReader(
				new ByteArrayInputStream(
						Constants.encode(concatPacketLines(INPUT
		PushCertificate streamCert = PushCertificateParser.fromReader(reader);

		PacketLineIn pckIn = newPacketLineIn(INPUT);
		PushCertificateParser pckParser =
				new PushCertificateParser(db
		pckParser.receiveHeader(pckIn
		pckParser.addCommand(pckIn.readString());
		assertEquals(PushCertificateParser.BEGIN_SIGNATURE
		pckParser.receiveSignature(pckIn);
		PushCertificate pckCert = pckParser.build();

		assertEquals(pckCert.getVersion()
		assertEquals(pckCert.getPusherIdent().getName()
				streamCert.getPusherIdent().getName());
		assertEquals(pckCert.getPusherIdent().getEmailAddress()
				streamCert.getPusherIdent().getEmailAddress());
		assertEquals(pckCert.getPusherIdent().getWhen().getTime()
				streamCert.getPusherIdent().getWhen().getTime());
		assertEquals(pckCert.getPusherIdent().getTimeZoneOffset()
				streamCert.getPusherIdent().getTimeZoneOffset());
		assertEquals(pckCert.getPushee()
		assertEquals(pckCert.getNonce()
		assertEquals(pckCert.getSignature()
		assertEquals(pckCert.toText()

		assertEquals(pckCert.getCommands().size()
		ReceiveCommand pckCmd = pckCert.getCommands().get(0);
		ReceiveCommand streamCmd = streamCert.getCommands().get(0);
		assertEquals(pckCmd.getRefName()
		assertEquals(pckCmd.getOldId()
		assertEquals(pckCmd.getNewId().name()

		assertNull(streamCert.getNonceStatus());
	}

	@Test
	public void testParseMultipleFromStream() throws Exception {
		String input = concatPacketLines(INPUT
		assertFalse(input.contains(PushCertificateParser.END_CERT));
		input += input;
		Reader reader = new InputStreamReader(
				new ByteArrayInputStream(Constants.encode(input)));

		assertNotNull(PushCertificateParser.fromReader(reader));
		assertNotNull(PushCertificateParser.fromReader(reader));
		assertEquals(-1
		assertNull(PushCertificateParser.fromReader(reader));
	}

