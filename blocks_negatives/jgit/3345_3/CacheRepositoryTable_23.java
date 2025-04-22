			List<CachedPackInfo> r = new ArrayList<CachedPackInfo>();
			TinyProtobuf.Decoder d = TinyProtobuf.decode(data);
			for (;;) {
				switch (d.next()) {
				case 0:
					return r;
				case 1:
					r.add(CachedPackInfo.fromBytes(d.message()));
					continue;
				default:
					d.skip();
				}
