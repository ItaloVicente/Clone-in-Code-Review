	private Delta objRefDeltaFor(long pos
		readFully(pos + p
		long base = findDeltaBase(ObjectId.fromRaw(ib));
		delta = new Delta(delta
		return delta;
	}

	private Delta objOfsDeltaFor(long pos
		int c = ib[p++] & 0xff;
		long offset = c & 127;
		while ((c & 128) != 0) {
			offset += 1;
			c = ib[p++] & 0xff;
			offset <<= 7;
			offset += (c & 127);
		}
		return new Delta(baseDelta
	}

	private ObjectLoader applyDeltas(Delta delta
				boolean cached
			throws IOException
		
		if (data == null)
			return delta.large(this

		do {
			if (cached)
				cached = false;
			else if (delta.next == null)
				DeltaBaseCache.store(this

			final byte[] cmds = decompress(delta.payloadPos()
					delta.deltaSize
			if (cmds == null) {
				return delta.large(this
			}

			final long sz = BinaryDelta.getResultSize(cmds);
			if (Integer.MAX_VALUE <= sz)
				return delta.large(this

			try {
				data = BinaryDelta.apply(data
			} catch (OutOfMemoryError tooBig) {
				return delta.large(this
			}

			delta = delta.next;
		} while (delta != null);

		return new ObjectLoader.SmallObject(type
	}

