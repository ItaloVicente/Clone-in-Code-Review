		return SignedPushConfig.KEY.parse(cfg);
	}

	private static SignedPushConfig newDisabledConfig() {
		return SignedPushConfig.KEY.parse(new Config());
	}

	@Test
	public void noCert() throws Exception {
		PushCertificateParser parser =
				new PushCertificateParser(db
		assertTrue(parser.enabled());
		assertNull(parser.build());
	}

	@Test
	public void disabled() throws Exception {
		PacketLineIn pckIn = newPacketLineIn(INPUT);
		PushCertificateParser parser =
				new PushCertificateParser(db
		assertFalse(parser.enabled());
		assertNull(parser.build());

		parser.receiveHeader(pckIn
		parser.addCommand(pckIn.readStringRaw());
		assertEquals(PushCertificateParser.BEGIN_SIGNATURE
		parser.receiveSignature(pckIn);
		assertNull(parser.build());
	}
