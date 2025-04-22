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

		ObjectId oldId = ObjectId.zeroId();
		ObjectId newId =
				ObjectId.fromString("deadbeefdeadbeefdeadbeefdeadbeefdeadbeef");
		String rawLine =
				oldId.name() + " " + newId.name() + " refs/heads/master";
		ReceiveCommand cmd = BaseReceivePack.parseCommand(rawLine);

		parser.addCommand(cmd
		parser.addCommand(rawLine);
		assertNull(parser.build());
	}
