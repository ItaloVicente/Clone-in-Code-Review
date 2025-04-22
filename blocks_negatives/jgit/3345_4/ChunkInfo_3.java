	public static ChunkInfo fromBytes(ChunkKey chunkKey, byte[] raw) {
		ChunkInfo info = new ChunkInfo();
		info.chunkKey = chunkKey;

		TinyProtobuf.Decoder d = TinyProtobuf.decode(raw);
		PARSE: for (;;) {
			switch (d.next()) {
			case 0:
				break PARSE;
			case 1:
				info.source = d.intEnum(Source.values());
				continue;
			case 2:
				info.objectType = d.int32();
				continue;
			case 3:
				info.fragment = d.bool();
				continue;
			case 4:
				info.cachedPack = CachedPackKey.fromBytes(d);
				continue;

			case 5: {
				TinyProtobuf.Decoder m = d.message();
				for (;;) {
					switch (m.next()) {
					case 0:
						continue PARSE;
					case 1:
						info.objectsTotal = m.int32();
						continue;
					case 2:
						info.objectsWhole = m.int32();
						continue;
					case 3:
						info.objectsOfsDelta = m.int32();
						continue;
					case 4:
						info.objectsRefDelta = m.int32();
						continue;
					default:
						m.skip();
						continue;
					}
				}
			}
			case 6:
				info.chunkSize = d.int32();
				continue;
			case 7:
				info.indexSize = d.int32();
				continue;
			case 8:
				info.metaSize = d.int32();
				continue;
			default:
				d.skip();
				continue;
			}
		}
		return info;
	}

	private static byte[] asBytes(ChunkInfo info) {
		TinyProtobuf.Encoder objects = TinyProtobuf.encode(48);
		objects.int32IfNotZero(1, info.objectsTotal);
		objects.int32IfNotZero(2, info.objectsWhole);
		objects.int32IfNotZero(3, info.objectsOfsDelta);
		objects.int32IfNotZero(4, info.objectsRefDelta);

		TinyProtobuf.Encoder e = TinyProtobuf.encode(128);
		e.intEnum(1, info.source);
		e.int32IfNotNegative(2, info.objectType);
		e.boolIfTrue(3, info.fragment);
		e.string(4, info.cachedPack);
		e.message(5, objects);
		e.int32IfNotZero(6, info.chunkSize);
		e.int32IfNotZero(7, info.indexSize);
		e.int32IfNotZero(8, info.metaSize);
		return e.asByteArray();
