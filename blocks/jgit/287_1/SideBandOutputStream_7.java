		if (cnt == buffer.length)
			writeBuffer();
		buffer[cnt++] = (byte) b;
	}

	private void writeBuffer() throws IOException {
		PacketLineOut.formatLength(buffer
		out.write(buffer
		cnt = HDR_SIZE;
