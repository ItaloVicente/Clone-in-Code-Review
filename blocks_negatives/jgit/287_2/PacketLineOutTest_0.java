
	public void testWriteChannelPacket1() throws IOException {
		out.writeChannelPacket(1, new byte[] { 'a' }, 0, 1);
		assertBuffer("0006\001a");
	}

	public void testWriteChannelPacket2() throws IOException {
		out.writeChannelPacket(2, new byte[] { 'b' }, 0, 1);
		assertBuffer("0006\002b");
	}

	public void testWriteChannelPacket3() throws IOException {
		out.writeChannelPacket(3, new byte[] { 'c' }, 0, 1);
		assertBuffer("0006\003c");
	}

