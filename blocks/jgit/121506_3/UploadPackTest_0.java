
	private static ByteArrayInputStream send(String... lines) throws Exception {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		PacketLineOut pckOut = new PacketLineOut(os);
		for (String line : lines) {
			if (line == PacketLineIn.END) {
				pckOut.end();
			} else if (line == PacketLineIn.DELIM) {
				pckOut.writeDelim();
			} else {
				pckOut.writeString(line);
			}
		}
		byte[] a = os.toByteArray();
		return new ByteArrayInputStream(a);
	}

	private ByteArrayInputStream uploadPackV2(String... inputLines) throws Exception {
		ByteArrayOutputStream send = new ByteArrayOutputStream();
		PacketLineOut pckOut = new PacketLineOut(send);
		for (String line : inputLines) {
			if (line == PacketLineIn.END) {
				pckOut.end();
			} else if (line == PacketLineIn.DELIM) {
				pckOut.writeDelim();
			} else {
				pckOut.writeString(line);
			}
		}

		server.getConfig().setString("protocol"
		UploadPack up = new UploadPack(server);
		up.setExtraParameters(Sets.of("version=2"));

		ByteArrayOutputStream recv = new ByteArrayOutputStream();
		up.upload(new ByteArrayInputStream(send.toByteArray())

		ByteArrayInputStream recvStream = new ByteArrayInputStream(recv.toByteArray());
		PacketLineIn pckIn = new PacketLineIn(recvStream);

		assertThat(pckIn.readString()
		assertTrue(pckIn.readString() == PacketLineIn.END);
		return recvStream;
	}

	@Test
	public void testV2EmptyRequest() throws Exception {
		ByteArrayInputStream recvStream = uploadPackV2(PacketLineIn.END);
		assertThat(recvStream.available()
	}

