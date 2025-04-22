		ByteBuffer data = members.getChunkDataAsByteBuffer();
		ByteBuffer index = members.getChunkIndexAsByteBuffer();
		ChunkMeta meta = members.getMeta();

		int sz = 0;
		if (data != null)
			sz += computeByteBufferSize(1
		if (index != null)
			sz += computeByteBufferSize(2
		if (meta != null)
			sz += CodedOutputStream.computeMessageSize(3

		byte[] r = new byte[sz];
		CodedOutputStream out = CodedOutputStream.newInstance(r);
		try {
			if (data != null)
				writeByteBuffer(out
			if (index != null)
				writeByteBuffer(out
			if (meta != null)
				out.writeMessage(3
		} catch (IOException err) {
			throw new RuntimeException("Cannot buffer chunk"
		}
		return r;
	}

	private static int computeByteBufferSize(int fieldNumber
		int n = data.remaining();
		return CodedOutputStream.computeTagSize(fieldNumber)
				+ CodedOutputStream.computeRawVarint32Size(n)
				+ n;
	}

	private static void writeByteBuffer(CodedOutputStream out
			ByteBuffer data) throws IOException {
		byte[] d = data.array();
		int p = data.arrayOffset() + data.position();
		out.writeTag(fieldNumber
		out.writeRawVarint32(data.remaining());
		out.writeRawBytes(d
