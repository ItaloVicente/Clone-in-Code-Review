		CodedInputStream in = CodedInputStream.newInstance(raw);
		try {
			int tag = in.readTag();
			for (;;) {
				switch (WireFormat.getTagFieldNumber(tag)) {
				case 0:
					return members;
				case 1: {
					int cnt = in.readRawVarint32();
					int ptr = in.getTotalBytesRead();
					members.setChunkData(raw
					in.skipRawBytes(cnt);
					tag = in.readTag();
					if (WireFormat.getTagFieldNumber(tag) != 2)
						continue;
				}
				case 2: {
					int cnt = in.readRawVarint32();
					int ptr = in.getTotalBytesRead();
					members.setChunkIndex(raw
					in.skipRawBytes(cnt);
					tag = in.readTag();
					if (WireFormat.getTagFieldNumber(tag) != 3)
						continue;
				}
				case 3: {
					int cnt = in.readRawVarint32();
					int oldLimit = in.pushLimit(cnt);
					members.setMeta(ChunkMeta.parseFrom(in));
					in.popLimit(oldLimit);
					continue;
				}
				default:
					in.skipField(tag);
				}
