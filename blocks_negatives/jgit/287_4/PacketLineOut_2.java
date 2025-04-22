	void writeChannelPacket(final int channel, final byte[] buf, int off,
			int len) throws IOException {
		formatLength(len + 5);
		lenbuffer[4] = (byte) channel;
		out.write(lenbuffer, 0, 5);
		out.write(buf, off, len);
	}

