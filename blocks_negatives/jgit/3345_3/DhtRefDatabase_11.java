		ObjectId oId = null;
		boolean peeled = false;
		ObjectId pId = null;

		TinyProtobuf.Decoder d = data.decode();
		DECODE: for (;;) {
			switch (d.next()) {
			case 0:
				break DECODE;

			case RefData.TAG_SYMREF: {
				String symref = d.string();
				Ref leaf = new Unpeeled(NEW, symref, null);
				return new SymbolicRef(name, leaf);
			}

			case RefData.TAG_TARGET:
				oId = RefData.IdWithChunk.decode(d.message());
				continue;
			case RefData.TAG_IS_PEELED:
				peeled = d.bool();
				continue;
			case RefData.TAG_PEELED:
				pId = RefData.IdWithChunk.decode(d.message());
				continue;
			default:
				d.skip();
				continue;
			}
