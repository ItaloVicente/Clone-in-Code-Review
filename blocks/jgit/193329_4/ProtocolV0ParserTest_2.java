	@Test
	public void testRecvWantsDeepenSince()
			throws PackProtocolException
		PacketLineIn pckIn = formatAsPacketLine(
				"want 4624442d68ee402a94364191085b77137618633e\n"
				"want f900c8326a43303685c46b279b9f70411bff1a4b\n"
				"deepen-since 1652773020\n"
				PacketLineIn.end());
		ProtocolV0Parser parser = new ProtocolV0Parser(defaultConfig());
		FetchV0Request request = parser.recvWants(pckIn);
		assertTrue(request.getClientCapabilities().isEmpty());
		assertEquals(1652773020
		assertThat(request.getWantIds()
				   hasOnlyObjectIds("4624442d68ee402a94364191085b77137618633e"
									"f900c8326a43303685c46b279b9f70411bff1a4b"));
	}

	@Test
	public void testRecvWantsDeepenNots()
			throws PackProtocolException
		PacketLineIn pckIn = formatAsPacketLine(
				"want 4624442d68ee402a94364191085b77137618633e\n"
				"want f900c8326a43303685c46b279b9f70411bff1a4b\n"
				"deepen-not 856d5138d7269a483efe276d4a6b5c25b4fbb1a4\n"
				"deepen-not heads/refs/test\n"
				PacketLineIn.end());
		ProtocolV0Parser parser = new ProtocolV0Parser(defaultConfig());
		FetchV0Request request = parser.recvWants(pckIn);
		assertTrue(request.getClientCapabilities().isEmpty());
		assertThat(request.getDeepenNots()
													 "heads/refs/test"));
		assertThat(request.getWantIds()
				   hasOnlyObjectIds("4624442d68ee402a94364191085b77137618633e"
									"f900c8326a43303685c46b279b9f70411bff1a4b"));
	}

