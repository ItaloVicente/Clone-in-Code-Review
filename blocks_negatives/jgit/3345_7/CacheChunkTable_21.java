		TinyProtobuf.Decoder d = TinyProtobuf.decode(raw);
		for (;;) {
			switch (d.next()) {
			case 0:
				return members;
			case 1: {
				int cnt = d.bytesLength();
				int ptr = d.bytesOffset();
				byte[] buf = d.bytesArray();
				members.setChunkData(buf, ptr, cnt);
				continue;
			}
			case 2: {
				int cnt = d.bytesLength();
				int ptr = d.bytesOffset();
				byte[] buf = d.bytesArray();
				members.setChunkIndex(buf, ptr, cnt);
				continue;
			}
			case 3:
				members.setMeta(ChunkMeta.fromBytes(key, d.message()));
				continue;
			default:
				d.skip();
