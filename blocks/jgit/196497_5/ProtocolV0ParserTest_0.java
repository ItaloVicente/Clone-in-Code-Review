	@Test
	public void testRecvWantsWithSessionID()
			throws PackProtocolException
		PacketLineIn pckIn = formatAsPacketLine(String.join(" "
				"4624442d68ee402a94364191085b77137618633e"
				"agent=JGit.test/0.0.1"
				"want f900c8326a43303685c46b279b9f70411bff1a4b\n"
				PacketLineIn.end());
		ProtocolV0Parser parser = new ProtocolV0Parser(defaultConfig());
		FetchV0Request request = parser.recvWants(pckIn);
		assertTrue(request.getClientCapabilities()
				.contains(GitProtocolConstants.OPTION_THIN_PACK));
		assertEquals(1
		assertEquals("client-session-id"
		assertThat(request.getWantIds()
				hasOnlyObjectIds("4624442d68ee402a94364191085b77137618633e"
						"f900c8326a43303685c46b279b9f70411bff1a4b"));
	}

