	private static byte[] encode(Collection<ObjectInfo> list) {
		TinyProtobuf.Encoder e = TinyProtobuf.encode(128);
		for (ObjectInfo info : list) {
			TinyProtobuf.Encoder m = TinyProtobuf.encode(128);
			m.bytes(1, info.getChunkKey().asBytes());
			m.bytes(2, info.asBytes());
			m.fixed64(3, info.getTime());
			e.message(1, m);
		}
		return e.asByteArray();
	}

	private static ObjectInfo decodeItem(TinyProtobuf.Decoder m) {
		ChunkKey key = null;
		TinyProtobuf.Decoder data = null;
		long time = -1;

		for (;;) {
			switch (m.next()) {
			case 0:
				return ObjectInfo.fromBytes(key, data, time);
			case 1:
				key = ChunkKey.fromBytes(m);
				continue;
			case 2:
				data = m.message();
				continue;
			case 3:
				time = m.fixed64();
				continue;
			default:
				m.skip();
			}
		}
	}

	private static Collection<ObjectInfo> decode(byte[] raw) {
		List<ObjectInfo> res = new ArrayList<ObjectInfo>(1);
		TinyProtobuf.Decoder d = TinyProtobuf.decode(raw);
		for (;;) {
			switch (d.next()) {
			case 0:
				return res;
			case 1:
				res.add(decodeItem(d.message()));
				continue;
			default:
				d.skip();
			}
		}
	}

