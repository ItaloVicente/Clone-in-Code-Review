		return new ByteArrayInputStream(recv.toByteArray());
	}

	private ByteArrayInputStream uploadPackV2(RequestPolicy requestPolicy
			RefFilter refFilter
		ByteArrayInputStream recvStream =
			uploadPackV2Setup(requestPolicy
		PacketLineIn pckIn = new PacketLineIn(recvStream);

		while (pckIn.readString() != PacketLineIn.END) {
		}
		return recvStream;
	}

	private ByteArrayInputStream uploadPackV2(String... inputLines) throws Exception {
		return uploadPackV2(null
	}

	@Test
	public void testV2Capabilities() throws Exception {
		ByteArrayInputStream recvStream =
			uploadPackV2Setup(null
