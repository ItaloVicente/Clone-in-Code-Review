		final byte[] meta;
		if (members.hasMeta())
			meta = members.getMeta().asBytes();
		else
			meta = null;

		ByteBuffer chunkData = members.getChunkDataAsByteBuffer();
		ByteBuffer chunkIndex = members.getChunkIndexAsByteBuffer();

		TinyProtobuf.Encoder sizer = TinyProtobuf.size();
		TinyProtobuf.Encoder e = sizer;
		do {
			e.bytes(1, chunkData);
			e.bytes(2, chunkIndex);
			e.bytes(3, meta);
			if (e == sizer)
				e = TinyProtobuf.encode(e.size());
			else
				return e.asByteArray();
		} while (true);
