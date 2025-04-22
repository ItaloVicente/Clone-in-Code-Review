	public void testReadACK_ACKcommon1() throws IOException {
		final ObjectId expid = ObjectId
				.fromString("fcfcfb1fd94829c1a1704f894fc111d14770d34e");
		final MutableObjectId actid = new MutableObjectId();

		init("0038ACK fcfcfb1fd94829c1a1704f894fc111d14770d34e common\n");
		assertSame(PacketLineIn.AckNackResult.ACK_COMMON
		assertTrue(actid.equals(expid));
		assertEOF();
	}

	public void testReadACK_ACKready1() throws IOException {
		final ObjectId expid = ObjectId
				.fromString("fcfcfb1fd94829c1a1704f894fc111d14770d34e");
		final MutableObjectId actid = new MutableObjectId();

		init("0037ACK fcfcfb1fd94829c1a1704f894fc111d14770d34e ready\n");
		assertSame(PacketLineIn.AckNackResult.ACK_READY
		assertTrue(actid.equals(expid));
		assertEOF();
	}

