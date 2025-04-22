
	@Test
	public void testParseAckV2_NAK() throws IOException {
		final ObjectId expid = ObjectId
				.fromString("fcfcfb1fd94829c1a1704f894fc111d14770d34e");
		final MutableObjectId actid = new MutableObjectId();
		actid.fromString(expid.name());

		assertSame(PacketLineIn.AckNackResult.NAK
				PacketLineIn.parseACKv2("NAK"
		assertEquals(expid
	}

	@Test
	public void testParseAckV2_ACK() throws IOException {
		final ObjectId expid = ObjectId
				.fromString("fcfcfb1fd94829c1a1704f894fc111d14770d34e");
		final MutableObjectId actid = new MutableObjectId();

		assertSame(PacketLineIn.AckNackResult.ACK_COMMON
				PacketLineIn.parseACKv2(
						"ACK fcfcfb1fd94829c1a1704f894fc111d14770d34e"
		assertEquals(expid
	}

	@Test
	public void testParseAckV2_Ready() throws IOException {
		final ObjectId expid = ObjectId
				.fromString("fcfcfb1fd94829c1a1704f894fc111d14770d34e");
		final MutableObjectId actid = new MutableObjectId();
		actid.fromString(expid.name());

		assertSame(PacketLineIn.AckNackResult.ACK_READY
				PacketLineIn.parseACKv2("ready"
		assertEquals(expid
	}

	@Test
	public void testParseAckV2_ERR() {
		IOException e = assertThrows(IOException.class
				.parseACKv2("ERR want is not valid"
		assertTrue(e.getMessage().contains("want is not valid"));
	}

	@Test
	public void testParseAckV2_Invalid() {
		IOException e = assertThrows(IOException.class
				() -> PacketLineIn.parseACKv2("HELO"
		assertTrue(e.getMessage().contains("xpected ACK/NAK"));
	}

