			if (size < wc.getStreamFileThreshold() || path == null) {
				in.reset();
				IO.skipFully(in
				Inflater inf = wc.inflater();
				InputStream zIn = inflate(in
				byte[] data = new byte[(int) size];
				IO.readFully(zIn
				checkValidEndOfStream(in
				return new ObjectLoader.SmallObject(type
			}
			return new LargeObject(type
