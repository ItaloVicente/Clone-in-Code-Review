
	private class Reader extends ObjectReader {
		private final DfsReader ctx = new DfsReader(db);

		@Override
		public ObjectReader newReader() {
			return db.newReader();
		}

		@Override
		public Collection<ObjectId> resolve(AbbreviatedObjectId id)
				throws IOException {
			Collection<ObjectId> stored = ctx.resolve(id);
			if (objectList == null)
				return stored;

			Set<ObjectId> r = new HashSet<ObjectId>(stored.size() + 2);
			r.addAll(stored);
			for (PackedObjectInfo obj : objectList) {
				if (id.prefixCompare(obj) == 0)
					r.add(obj.copy());
			}
			return r;
		}

		@Override
		public ObjectLoader open(AnyObjectId objectId
				throws IOException {
			if (objectMap == null)
				return ctx.open(objectId

			PackedObjectInfo obj = objectMap.get(objectId);
			if (obj == null)
				return ctx.open(objectId

			byte[] buf = buffer();
			int cnt = packOut.read(obj.getOffset()
			if (cnt <= 0)
					throw new EOFException(DfsText.get().unexpectedEofInPack);

			int c = buf[0] & 0xff;
			int type = (c >> 4) & 7;
			if (type == OBJ_OFS_DELTA || type == OBJ_REF_DELTA)
				throw new IOException(MessageFormat.format(
						DfsText.get().cannotReadBackDelta

			long sz = c & 0x0f;
			int ptr = 1;
			int shift = 4;
			while ((c & 0x80) != 0) {
				if (ptr >= cnt)
					throw new EOFException(DfsText.get().unexpectedEofInPack);
				c = buf[ptr++] & 0xff;
				sz += ((long) (c & 0x7f)) << shift;
				shift += 7;
			}

			long zpos = obj.getOffset() + ptr;
			if (sz < ctx.getStreamFileThreshold()) {
				byte[] data = inflate(obj
				if (data != null)
					return new ObjectLoader.SmallObject(type
			}
			return new StreamLoader(obj.copy()
		}

		private byte[] inflate(PackedObjectInfo obj
				throws IOException
			try {
				return packOut.inflate(ctx
			} catch (DataFormatException dfe) {
				CorruptObjectException coe = new CorruptObjectException(
						MessageFormat.format(
								JGitText.get().objectAtHasBadZlibStream
								Long.valueOf(obj.getOffset())
								packDsc.getFileName(PackExt.PACK)));
				coe.initCause(dfe);
				throw coe;
			}
		}

		@Override
		public Set<ObjectId> getShallowCommits() throws IOException {
			return ctx.getShallowCommits();
		}

		@Override
		public void release() {
			ctx.release();
		}
	}

	private class StreamLoader extends ObjectLoader {
		private final ObjectId id;
		private final int type;
		private final long size;

		private final DfsPackKey srcPack;
		private final long pos;

		StreamLoader(ObjectId id
				DfsPackKey key
			this.id = id;
			this.type = type;
			this.size = sz;
			this.srcPack = key;
			this.pos = pos;
		}

		@Override
		public ObjectStream openStream() throws IOException {
			final DfsReader ctx = new DfsReader(db);
			if (srcPack != packKey) {
				try {
					return ctx.open(id
				}finally {
					ctx.release();
				}
			}

			int bufsz = 8192;
			final Inflater inf = ctx.inflater();
			return new ObjectStream.Filter(type
					new InflaterInputStream(new InputStream() {
						private long p = pos;

						@Override
						public int read() throws IOException {
							byte[] b = new byte[1];
							int n = read(b);
							return n == 1 ? b[0] & 0xff : -1;
						}

						@Override
						public int read(byte[] buf
								throws IOException {
							int n = packOut.read(p
							if (n > 0)
								p += n;
							return n;
						}
					}
				@Override
				public void close() throws IOException {
					ctx.release();
					super.close();
				}
			};
		}

		@Override
		public int getType() {
			return type;
		}

		@Override
		public long getSize() {
			return size;
		}

		@Override
		public boolean isLarge() {
			return true;
		}

		@Override
		public byte[] getCachedBytes() throws LargeObjectException {
			throw new LargeObjectException.ExceedsLimit(
					db.getReaderOptions().getStreamFileThreshold()
		}
	}
